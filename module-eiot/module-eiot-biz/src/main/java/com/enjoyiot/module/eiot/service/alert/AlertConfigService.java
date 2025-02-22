
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
package com.enjoyiot.module.eiot.service.alert;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfig;
import com.enjoyiot.module.eiot.api.alert.dto.AlertConfigPageReqVO;
import com.enjoyiot.module.eiot.controller.admin.alertconfig.vo.AlertConfigSaveReqVO;

import javax.validation.Valid;

/**
 * 报警配置 Service 接口
 *
 * @author EnjoyIot
 */
public interface AlertConfigService {

    /**
     * 创建报警配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAlertConfig(@Valid AlertConfigSaveReqVO createReqVO);

    /**
     * 更新报警配置
     *
     * @param updateReqVO 更新信息
     */
    void updateAlertConfig(@Valid AlertConfigSaveReqVO updateReqVO);

    /**
     * 删除报警配置
     *
     * @param id 编号
     */
    void deleteAlertConfig(Long id);

    /**
     * 获得报警配置
     *
     * @param id 编号
     * @return 报警配置
     */
    AlertConfig getAlertConfig(Long id);

    /**
     * 获得报警配置分页
     *
     * @param pageReqVO 分页查询
     * @return 报警配置分页
     */
    PageResult<AlertConfig> getAlertConfigPage(AlertConfigPageReqVO pageReqVO);

    void addAlertRecord(AlertConfig config, String content);
}
