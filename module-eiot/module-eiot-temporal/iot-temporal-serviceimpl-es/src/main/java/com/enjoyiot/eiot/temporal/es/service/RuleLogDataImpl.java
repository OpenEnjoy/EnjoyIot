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
package com.enjoyiot.eiot.temporal.es.service;


import com.enjoyiot.eiot.IRuleLogData;
import com.enjoyiot.eiot.temporal.es.dao.RuleLogRepository;
import com.enjoyiot.eiot.temporal.es.convert.EsRuleLogConvert;
import com.enjoyiot.eiot.temporal.es.document.DocRuleLog;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.rule.dto.RuleLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RuleLogDataImpl implements IRuleLogData {

    @Autowired
    private RuleLogRepository ruleLogRepository;

    @Override
    public void deleteByRuleId(Long ruleId) {
        ruleLogRepository.deleteByRuleId(ruleId);
    }

    @Override
    public PageResult<RuleLog> findByRuleId(Long ruleId, int page, int size) {
        Page<DocRuleLog> paged = ruleLogRepository.findByRuleIdOrderByLogAtDesc(ruleId, Pageable.ofSize(size).withPage(page - 1));
        return new PageResult<>(
                paged.getContent().stream().map(o -> EsRuleLogConvert.INSTANCE.convert(o))
                        .collect(Collectors.toList()),paged.getTotalElements());
    }

    @Override
    public void add(RuleLog log) {
        ruleLogRepository.save(EsRuleLogConvert.INSTANCE.convertDoc(log));
    }
}
