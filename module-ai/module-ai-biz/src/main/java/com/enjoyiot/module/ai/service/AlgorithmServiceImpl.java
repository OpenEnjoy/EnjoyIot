
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
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmCreateReqVO;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmPageReqVO;
import com.enjoyiot.module.ai.controller.admin.vo.AlgorithmUpdateReqVO;
import com.enjoyiot.module.ai.dal.dataobject.AlgorithmDO;
import com.enjoyiot.module.ai.dal.mysql.AlgorithmMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.enjoyiot.module.ai.enums.ErrorCodeConstants.ALGORITHM_NOT_EXISTS;

@Service
@Validated
public class AlgorithmServiceImpl implements AlgorithmService {

    @Resource
    private AlgorithmMapper algorithmMapper;

    @Override
    public Long createAlgorithm(AlgorithmCreateReqVO createReqVO) {
        // 插入
        AlgorithmDO algorithm = BeanUtils.toBean(createReqVO, AlgorithmDO.class);
        algorithmMapper.insert(algorithm);
        // 返回
        return algorithm.getId();
    }

    @Override
    public void updateAlgorithm(AlgorithmUpdateReqVO updateReqVO) {
        // 校验存在
        validateAlgorithmExists(updateReqVO.getId());
        // 更新
        AlgorithmDO updateObj = BeanUtils.toBean(updateReqVO, AlgorithmDO.class);
        algorithmMapper.updateById(updateObj);
    }

    @Override
    public void deleteAlgorithm(Long id) {
        // 校验存在
        validateAlgorithmExists(id);
        // 删除
        algorithmMapper.deleteById(id);
    }

    private void validateAlgorithmExists(Long id) {
        if (algorithmMapper.selectById(id) == null) {
            throw exception(ALGORITHM_NOT_EXISTS);
        }
    }

    @Override
    public AlgorithmDO getAlgorithm(Long id) {
        return algorithmMapper.selectById(id);
    }

    @Override
    public PageResult<AlgorithmDO> getAlgorithmPage(AlgorithmPageReqVO pageReqVO) {
        return algorithmMapper.selectPage(pageReqVO);
    }

}