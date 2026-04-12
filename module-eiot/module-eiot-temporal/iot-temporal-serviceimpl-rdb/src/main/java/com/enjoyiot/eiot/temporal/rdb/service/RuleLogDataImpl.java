/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
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
package com.enjoyiot.eiot.temporal.rdb.service;


import com.enjoyiot.eiot.IRuleLogData;
import com.enjoyiot.eiot.temporal.rdb.dao.RdbTemplate;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.rule.dto.RuleLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RuleLogDataImpl implements IRuleLogData {

    @Autowired
    private RdbTemplate rdbTemplate;

    @Override
    public void deleteByRuleId(Long ruleId) {
        rdbTemplate.update("delete from rule_log where rule_id=?", ruleId);
    }

    @Override
    public PageResult<RuleLog> findByRuleId(Long ruleId, int page, int size) {
        String sql = "select time,state1,content,success,rule_id from rule_log where rule_id=? order by time desc limit ? offset ?";
        List<Map<String, Object>> ruleLogs = rdbTemplate.queryForList(sql, ruleId, size, (page - 1) * size);

        List<RuleLog> logs = ruleLogs.stream().map(r -> {
            Long time = ((Number) r.get("time")).longValue();
            return new RuleLog(
                    time,
                    ruleId,
                    (String) r.get("state1"),
                    (String) r.get("content"),
                    ((Number) r.get("success")).intValue() == 1,
                    time
            );
        }).collect(Collectors.toList());

        sql = "select count(*) from rule_log where rule_id=?";
        Long count = rdbTemplate.queryForObject(sql, Long.class, ruleId);

        return new PageResult<>(logs, count != null ? count : 0L);
    }

    @Override
    public void add(RuleLog log) {
        String sql = "INSERT INTO rule_log (time, state1, content, success, rule_id) VALUES (?, ?, ?, ?, ?)";
        rdbTemplate.update(sql, System.currentTimeMillis(), log.getState(), log.getContent(), log.getSuccess() ? 1 : 0, log.getRuleId());
    }
}
