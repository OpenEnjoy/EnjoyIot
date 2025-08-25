/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: dreamswang2020@foxmail.com
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
package com.enjoyiot.eiot.temporal.kw.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.enjoyiot.eiot.IRuleLogData;
import com.enjoyiot.eiot.temporal.kw.dao.KwRuleLogMapper;
import com.enjoyiot.eiot.temporal.kw.model.KwRuleLog;
import com.enjoyiot.framework.common.pojo.PageParam;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.rule.dto.RuleLog;
import com.kaiwudb.util.KWTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RuleLogDataImpl implements IRuleLogData {

    @Autowired
    private KwRuleLogMapper ruleLogMapper;

    @Override
    public void deleteByRuleId(Long ruleId) {
        ruleLogMapper.delete(KwRuleLog::getRuleId, ruleId);
    }

    @Override
    public PageResult<RuleLog> findByRuleId(Long ruleId, int page, int size) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNo(page);
        pageParam.setPageSize(size);
        PageResult<KwRuleLog> result = ruleLogMapper.selectPage(pageParam,
                Wrappers.lambdaQuery(KwRuleLog.class).eq(KwRuleLog::getRuleId, ruleId).orderByDesc(KwRuleLog::getTime)
        );
        return new PageResult<>(result.getList().stream().map(r ->
                        new RuleLog(r.getTime().getTime(), ruleId, r.getState1(),
                                r.getContent(), r.getSuccess(), r.getTime().getTime()))
                .collect(Collectors.toList()), result.getTotal());
    }

    @Override
    public void add(RuleLog log) {
        KwRuleLog ruleLog = BeanUtils.toBean(log, KwRuleLog.class);
        ruleLog.setState1(log.getState());
        ruleLog.setTime(new KWTimestamp(System.currentTimeMillis()));
        ruleLogMapper.insert(ruleLog);
    }
}
