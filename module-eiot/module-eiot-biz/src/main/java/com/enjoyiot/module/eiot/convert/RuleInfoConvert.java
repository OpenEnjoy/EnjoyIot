
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
package com.enjoyiot.module.eiot.convert;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.rule.dto.FilterConfig;
import com.enjoyiot.module.eiot.api.rule.dto.RuleInfo;
import com.enjoyiot.module.eiot.api.task.dto.RuleAction;
import com.enjoyiot.module.eiot.controller.admin.rule.vo.EiotRuleInfoSaveReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.ruleinfo.EiotRuleInfoDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

/**
 * @Author: EnjoyIot
 * @Date: 2024/12/25 16:17
 * @Version: V1.0
 * @Description: 规则引擎转化
 */
@Mapper(builder = @Builder(disableBuilder = true))

public interface RuleInfoConvert {

    RuleInfoConvert INSTANCE = Mappers.getMapper(RuleInfoConvert.class);

    @Mappings({
            @Mapping(source = "listeners", target = "listeners", qualifiedByName = {"stringToFilterConfigList"}),
            @Mapping(source = "actions", target = "actions", qualifiedByName = {"stringToRuleActionList"}),
//            @Mapping(source = "actions", target = "actions", ignore = true),
            @Mapping(source = "filters", target = "filters", qualifiedByName = {"stringToFilterConfigList"}),
    })
    RuleInfo convert(EiotRuleInfoDO ylRuleInfoDO);

    PageResult<RuleInfo> convertPage(PageResult<EiotRuleInfoDO> selectPage);

    @Named("stringToFilterConfigList")
    default List<FilterConfig> stringToFilterConfigList(String jsonString) {
        if (StrUtil.isBlank(jsonString)) {
            return Collections.emptyList();
        }
        return JsonUtils.parseArray(jsonString, FilterConfig.class);
    }

    @Named("stringToRuleActionList")
    default List<RuleAction> stringToRuleActionList(String jsonString) {
        if (StrUtil.isBlank(jsonString)) {
            return Collections.emptyList();
        }
        return JsonUtils.parseArray(jsonString, RuleAction.class);
    }


    RuleInfo edit2Info(EiotRuleInfoSaveReqVO createReqVO);

    @Mappings({
            @Mapping(source = "listeners", target = "listeners", qualifiedByName = {"filterConfigList2string"}),
            @Mapping(source = "actions", target = "actions", qualifiedByName = {"ruleActionList2string"}),
            @Mapping(source = "filters", target = "filters", qualifiedByName = {"filterConfigList2string"}),
    })
    EiotRuleInfoDO toDo(RuleInfo ruleInfo);

    @Named("filterConfigList2string")
    default String filterConfigList2string(List<FilterConfig> listeners) {
        if (CollectionUtil.isEmpty(listeners)) {
            return "[]";
        }
        return JsonUtils.toJsonString(listeners);
    }

    @Named("ruleActionList2string")
    default String ruleActionList2string(List<RuleAction> listeners) {
        if (CollectionUtil.isEmpty(listeners)) {
            return "[]";
        }
        return JsonUtils.toJsonString(listeners);
    }

}
