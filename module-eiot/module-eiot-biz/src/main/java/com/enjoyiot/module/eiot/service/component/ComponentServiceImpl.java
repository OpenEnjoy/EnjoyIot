
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
package com.enjoyiot.module.eiot.service.component;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.component.dto.ComponentInfo;
import com.enjoyiot.module.eiot.controller.admin.component.vo.ComponentCreateReqVO;
import com.enjoyiot.module.eiot.controller.admin.component.vo.ComponentPageReqVO;
import com.enjoyiot.module.eiot.controller.admin.component.vo.ComponentUpdateReqVO;
import com.enjoyiot.module.eiot.convert.ComponentConvert;
import com.enjoyiot.module.eiot.dal.dataobject.component.ComponentDO;
import com.enjoyiot.module.eiot.dal.mysql.component.ComponentMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.enjoyiot.eiot.common.enums.ErrorCodeConstants.COMPONENT_NOT_EXISTS;
import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 组件配置 Service 实现类
 */
@Service
@Validated
public class ComponentServiceImpl implements ComponentService {

    @Resource
    private ComponentMapper componentMapper;

    @Override
    public Long createComponent(ComponentCreateReqVO createReqVO) {
        // 插入
        ComponentDO component = ComponentConvert.INSTANCE.convert(createReqVO);
        componentMapper.insert(component);
        // 返回
        return component.getId();
    }

    @Override
    public void updateComponent(ComponentUpdateReqVO updateReqVO) {
        // 校验存在
        validateComponentExists(updateReqVO.getId());
        // 更新
        ComponentDO updateObj = ComponentConvert.INSTANCE.convert(updateReqVO);
        componentMapper.updateById(updateObj);
    }

    @Override
    public void deleteComponent(Long id) {
        // 校验存在
        validateComponentExists(id);
        // 删除
        componentMapper.deleteById(id);
    }

    private void validateComponentExists(Long id) {
        if (componentMapper.selectById(id) == null) {
            throw exception(COMPONENT_NOT_EXISTS);
        }
    }

    @Override
    public ComponentDO getComponent(Long id) {
        return componentMapper.selectById(id);
    }

    @Override
    public PageResult<ComponentDO> getComponentPage(ComponentPageReqVO pageReqVO) {
        return componentMapper.selectPage(pageReqVO);
    }

    @Override
    public ComponentInfo getComponent(String type) {
        return ComponentConvert.INSTANCE.convertInfo(componentMapper.selectOne(ComponentDO::getType, type));
    }

}