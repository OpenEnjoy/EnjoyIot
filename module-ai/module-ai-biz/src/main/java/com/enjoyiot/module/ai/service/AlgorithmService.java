
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
package com.enjoyiot.module.ai.service;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmCreateReqVO;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmPageReqVO;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmUpdateReqVO;
import com.enjoyiot.module.ai.dal.dataobject.AlgorithmDO;

import javax.validation.Valid;

public interface AlgorithmService {

    /**
     * 创建算法模型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAlgorithm(@Valid AlgorithmCreateReqVO createReqVO);

    /**
     * 更新算法模型
     *
     * @param updateReqVO 更新信息
     */
    void updateAlgorithm(@Valid AlgorithmUpdateReqVO updateReqVO);

    /**
     * 删除算法模型
     *
     * @param id 编号
     */
    void deleteAlgorithm(Long id);

    /**
     * 获得算法模型
     *
     * @param id 编号
     * @return 算法模型
     */
    AlgorithmDO getAlgorithm(Long id);

    /**
     * 获得算法模型分页
     *
     * @param pageReqVO 分页查询
     * @return 算法模型分页
     */
    PageResult<AlgorithmDO> getAlgorithmPage(AlgorithmPageReqVO pageReqVO);

}