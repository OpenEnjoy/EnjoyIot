
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

import com.enjoyiot.module.eiot.api.component.dto.ComponentInfo;
import com.enjoyiot.module.eiot.controller.admin.component.vo.ComponentCreateReqVO;
import com.enjoyiot.module.eiot.controller.admin.component.vo.ComponentUpdateReqVO;
import com.enjoyiot.module.eiot.controller.admin.component.vo.ComponentPageReqVO;
import com.enjoyiot.module.eiot.dal.dataobject.component.ComponentDO;
import com.enjoyiot.framework.common.pojo.PageResult;

import javax.validation.Valid;

public interface ComponentService {

    /**
     * 创建组件配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createComponent(@Valid ComponentCreateReqVO createReqVO);

    /**
     * 更新组件配置
     *
     * @param updateReqVO 更新信息
     */
    void updateComponent(@Valid ComponentUpdateReqVO updateReqVO);

    /**
     * 删除组件配置
     *
     * @param id 编号
     */
    void deleteComponent(Long id);

    /**
     * 获得组件配置
     *
     * @param id 编号
     * @return 组件配置
     */
    ComponentDO getComponent(Long id);

    /**
     * 获得组件配置分页
     *
     * @param pageReqVO 分页查询
     * @return 组件配置分页
     */
    PageResult<ComponentDO> getComponentPage(ComponentPageReqVO pageReqVO);

    /**
     * 按组件类型获取
     *
     * @param type 组件类型
     * @return 组件信息
     */
    ComponentInfo getComponent(String type);

}