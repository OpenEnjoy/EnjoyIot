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
package com.enjoyiot.eiot.temporal.td.service;

import com.enjoyiot.eiot.IRuleLogData;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.eiot.temporal.td.dao.TdTemplate;
import com.enjoyiot.eiot.temporal.td.dm.TableManager;
import com.enjoyiot.eiot.temporal.td.model.TbRuleLog;
import com.enjoyiot.module.eiot.api.rule.dto.RuleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleLogDataImpl implements IRuleLogData {

    @Autowired
    private TdTemplate tdTemplate;

    @Override
    public void deleteByRuleId(Long ruleId) {
        tdTemplate.update("delete from rule_log where rule_id=? and time<=NOW()", ruleId);
    }

    @Override
    public PageResult<RuleLog> findByRuleId(Long ruleId, int page, int size) {
        String sql = "select time,state1,content,success,rule_id from rule_log where rule_id=? " +
                "order by time desc limit %d offset %d";
        sql = String.format(sql, size, (page - 1) * size);
        List<TbRuleLog> ruleLogs = tdTemplate.query(sql, new BeanPropertyRowMapper<>(TbRuleLog.class), ruleId);

        sql = "select count(*) from rule_log where rule_id=?";
        List<Long> counts = tdTemplate.queryForList(sql, Long.class, ruleId);

        return new PageResult<RuleLog>(ruleLogs.stream().map(r ->
                        new RuleLog(r.getTime(), ruleId, r.getState1(),
                                r.getContent(), r.getSuccess(), r.getTime()))
                .collect(Collectors.toList()),!counts.isEmpty() ? counts.get(0) : 0L);
    }

    @Override
    public void add(RuleLog log) {
        //使用ruleId作表名
        String sql = String.format("INSERT INTO %s (%s) USING %s TAGS ('%s') VALUES (%s);",
                "rule_log_" + TableManager.rightTbName(String.valueOf(log.getRuleId())),
                "time,state1,content,success",
                "rule_log",
                log.getRuleId(),
                "?,?,?,?"
        );
        tdTemplate.update(sql, System.currentTimeMillis(), log.getState(), log.getContent(), log.getSuccess());
    }
}
