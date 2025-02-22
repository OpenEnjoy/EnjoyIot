
/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com | Tel: 19918996474
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot] | Tel: 19918996474
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package com.enjoyiot.eiot.ruleengine.rule;


import cn.hutool.core.collection.CollectionUtil;
import com.enjoyiot.eiot.common.utils.CodecUtil;
import com.alibaba.fastjson.JSON;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.rule.RuleApi;
import com.enjoyiot.module.eiot.api.rule.dto.RuleInfo;
import com.enjoyiot.module.eiot.api.rule.dto.RuleInfoPageReqVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RuleLoadManager {

    @Autowired
    private RuleManager ruleManager;

    @Resource
    private RuleApi ruleApi;

    final private Map<Long, String> ruleMd5Map = new HashMap<>();

    public RuleLoadManager() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::initRules, 1, 30, TimeUnit.SECONDS);
    }

    @SneakyThrows
    public void initRules() {
        int idx = 1;
        int pageSize = 100;
        while (true) {
            RuleInfoPageReqVO pageRequest = new RuleInfoPageReqVO();
            pageRequest.setPageNo(idx);
            pageRequest.setPageSize(pageSize);
            PageResult<RuleInfo> all = ruleApi.selectPage(pageRequest);
            List<RuleInfo> rules = all.getList();
            if (CollectionUtil.isEmpty(rules)) {
                return;
            }

            for (RuleInfo rule : rules) {
                Long ruleId = rule.getId();
                String oldMd5 = ruleMd5Map.get(ruleId);
                String md5 = CodecUtil.md5Str(JSON.toJSONString(rule));
                if (oldMd5 != null && oldMd5.equals(md5)) {
                    continue;
                }

                log.info("rule {} has changed", ruleId);
                ruleMd5Map.put(ruleId, md5);
                refreshRule(rule);
            }
            if(all.getTotal()<pageSize){
                return;
            }
            idx++;
        }
    }

    private void refreshRule(RuleInfo ruleInfo) {
        ruleManager.remove(ruleInfo.getId());
        ruleManager.add(ruleInfo);
    }

}
