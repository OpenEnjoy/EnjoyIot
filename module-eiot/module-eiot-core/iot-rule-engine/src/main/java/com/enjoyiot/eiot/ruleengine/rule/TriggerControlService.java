package com.enjoyiot.eiot.ruleengine.rule;

import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.framework.tenant.core.context.TenantContextHolder;
import com.enjoyiot.module.eiot.api.rule.dto.TriggerOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RMap;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * 触发控制：限频、延时、告警解除（基于 Redisson）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TriggerControlService implements InitializingBean {

    private static final String STATE_KEY = "rule:trigger:state";
    private static final String ACTION_QUEUE = "rule:trigger:action:q";
    private static final String RECOVER_QUEUE = "rule:trigger:recover:q";
    private static final String RATE_LIMITER_KEY_PREFIX = "rule:trigger:limit:";

    private final RedissonClient redissonClient;
    private final RuleManager ruleManager;
    private final RuleExecutor ruleExecutor;

    private RBlockingQueue<TriggerJob> actionQueue;
    private RDelayedQueue<TriggerJob> actionDelayedQueue;
    private RBlockingQueue<TriggerJob> recoverQueue;
    private RDelayedQueue<TriggerJob> recoverDelayedQueue;
    private ExecutorService consumerPool;

    @Override
    public void afterPropertiesSet() {
        // 队列初始化
        actionQueue = redissonClient.getBlockingQueue(ACTION_QUEUE);
        actionDelayedQueue = redissonClient.getDelayedQueue(actionQueue);
        recoverQueue = redissonClient.getBlockingQueue(RECOVER_QUEUE);
        recoverDelayedQueue = redissonClient.getDelayedQueue(recoverQueue);


        ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setName("rule-trigger-consumer");
            return t;
        });
        consumerPool = TtlExecutors.getTtlExecutorService(executor);
        consumerPool.submit(() -> consume(actionQueue, false));
        consumerPool.submit(() -> consume(recoverQueue, true));
    }

    private void consume(RBlockingQueue<TriggerJob> queue, boolean recovery) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TriggerJob job = queue.poll(1, TimeUnit.SECONDS);
                if (job == null) {
                    continue;
                }
                TenantContextHolder.setTenantId(job.getTenantId());
                processJob(job, recovery);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (org.redisson.RedissonShutdownException e) {
                log.info("Redisson is shutdown, stop trigger queue consumer. recovery={}", recovery);
                break;
            } catch (Throwable e) {
                log.error("Trigger queue consumer error, recovery={}", recovery, e);
            }
        }
    }

    private void processJob(TriggerJob job, boolean recoveryJob) {
        Rule rule = ruleManager.getRule(job.getRuleId());
        if (rule == null) {
            log.warn("rule {} not found in cache, skip {} job", job.getRuleId(), recoveryJob ? "recover" : "action");
            return;
        }
        ThingModelMessage msg = JsonUtils.parseObject(job.getMessageJson(), ThingModelMessage.class);
        if (recoveryJob) {
            // token 校验，避免旧任务误恢复
            TriggerState state = getState(rule.getId());
            if (state == null || !state.isAlerting()) {
                log.info("rule {} recover skipped, not alerting", rule.getId());
                return;
            }
            if (job.getRecoverToken() != null && !job.getRecoverToken().equals(state.getRecoverToken())) {
                log.info("rule {} recover skipped, token mismatch", rule.getId());
                return;
            }
            // executeActions 内部会调用 markRecovered
            ruleExecutor.executeActions(rule, msg, true);
        } else {
            // 延时任务执行：直接执行动作并标记触发
            ruleExecutor.executeActions(rule, msg, false);
            markTriggered(rule.getId());
        }
    }

    public boolean passRateLimit(Long ruleId, TriggerOptions options) {
        Integer minIntervalSec = options == null ? null : options.getMinIntervalSec();
        if (minIntervalSec == null || minIntervalSec <= 0) {
            return true;
        }
        RRateLimiter limiter = redissonClient.getRateLimiter(RATE_LIMITER_KEY_PREFIX + ruleId);
        limiter.trySetRate(RateType.OVERALL, 1, minIntervalSec, RateIntervalUnit.SECONDS);
        return limiter.tryAcquire();
    }

    public void markTriggered(Long ruleId) {
        TriggerState state = getState(ruleId);
        if (state == null) {
            state = new TriggerState();
        }
        state.setAlerting(true);
        state.setRecoverToken(null);
        saveState(ruleId, state);
    }

    public void markRecovered(Long ruleId) {
        TriggerState state = getState(ruleId);
        if (state == null) {
            state = new TriggerState();
        }
        state.setAlerting(false);
        state.setRecoverToken(null);
        saveState(ruleId, state);
    }

    /**
     * 执行动作：有延时则入队列，无延时则直接执行
     */
    public void executeAction(Rule rule, ThingModelMessage msg, TriggerOptions options, RuleExecutor executor) {
        long delayMs = options != null && options.getDelaySec() != null && options.getDelaySec() > 0
                ? options.getDelaySec() * 1000L : 0L;
        if (delayMs > 0) {
            // 只有延时的情况才入队列
            TriggerJob job = new TriggerJob(rule.getId(), JsonUtils.toJsonString(msg), false, null,rule.getTenantId());
            actionDelayedQueue.offer(job, delayMs, TimeUnit.MILLISECONDS);
        } else {
            // 无延时直接执行
            executor.executeActions(rule, msg, false);
            markTriggered(rule.getId());
        }
    }

    public void scheduleRecoverIfNeeded(Rule rule, ThingModelMessage msg, TriggerOptions options) {
        if (options == null || options.getEnableAlertRecover() == null || !options.getEnableAlertRecover()) {
            return;
        }
        TriggerState state = getState(rule.getId());
        if (state == null || !state.isAlerting()) {
            return;
        }
        long delayMs = options.getRecoverQuietSec() != null && options.getRecoverQuietSec() > 0
                ? options.getRecoverQuietSec() * 1000L : 0L;
        String token = UUID.randomUUID().toString();
        state.setRecoverToken(token);
        saveState(rule.getId(), state);
        if (delayMs > 0) {
            // 有延时则入队列
            TriggerJob job = new TriggerJob(rule.getId(), JsonUtils.toJsonString(msg), true, token,rule.getTenantId());
            recoverDelayedQueue.offer(job, delayMs, TimeUnit.MILLISECONDS);
        } else {
            // 无延时直接执行恢复（executeActions 内部会调用 markRecovered）
            ruleExecutor.executeActions(rule, msg, true);
        }
    }

    public void cancelRecover(Long ruleId) {
        // 通过重置 token 来“作废”已投递的恢复任务
        TriggerState state = getState(ruleId);
        if (state != null) {
            state.setRecoverToken(UUID.randomUUID().toString());
            saveState(ruleId, state);
        }
    }

    private TriggerState getState(Long ruleId) {
        RMap<Long, String> map = redissonClient.getMap(STATE_KEY);
        String json = map.get(ruleId);
        if (json == null) {
            return null;
        }
        return JsonUtils.parseObject(json, TriggerState.class);
    }

    private void saveState(Long ruleId, TriggerState state) {
        RMap<Long, String> map = redissonClient.getMap(STATE_KEY);
        map.put(ruleId, JsonUtils.toJsonString(state));
    }

    /**
     * 清理规则的 Redis 缓存（限流器、触发状态）
     */
    public void clearRuleCache(Long ruleId) {
        try {
            // 清理限流器
            RRateLimiter limiter = redissonClient.getRateLimiter(RATE_LIMITER_KEY_PREFIX + ruleId);
            limiter.delete();
            // 清理触发状态
            RMap<Long, String> map = redissonClient.getMap(STATE_KEY);
            map.remove(ruleId);
        } catch (Throwable e) {
            log.warn("clear rule cache failed, ruleId: {}", ruleId, e);
        }
    }


}

