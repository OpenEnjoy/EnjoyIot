
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
package com.enjoyiot.module.eiot.service.product;


import com.enjoyiot.module.eiot.controller.admin.showmodel.vo.ShowModelRespVO;
import com.enjoyiot.module.eiot.controller.admin.showmodel.vo.ShowModelSaveReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 产品显示模型 Service 接口
 *
 * @author EnjoyIot
 */
public interface ShowModelService {

    /**
     * 创建产品显示模型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createShowModel(@Valid ShowModelSaveReqVO createReqVO);

    /**
     * 更新产品显示模型
     *
     * @param updateReqVO 更新信息
     */
    void saveShowModel(@Valid ShowModelSaveReqVO updateReqVO);

    /**
     * 删除产品显示模型
     *
     * @param id 编号
     */
    void deleteShowModel(Long id);

    /**
     * 获得产品显示模型
     *
     * @param id 编号
     * @return 产品显示模型
     */
    ShowModelRespVO getShowModel(Long id);


    List<ShowModelRespVO> getShowModelByProductKey(String productKey);
}
