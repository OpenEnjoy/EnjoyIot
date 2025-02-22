
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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.enjoyiot.framework.common.exception.util.ServiceExceptionUtil;
import com.enjoyiot.framework.common.util.object.BeanUtils;
import com.enjoyiot.module.eiot.api.enums.ErrorCodeConstants;
import com.enjoyiot.module.eiot.controller.admin.showmodel.vo.ShowModelRespVO;
import com.enjoyiot.module.eiot.controller.admin.showmodel.vo.ShowModelSaveReqVO;
import com.enjoyiot.module.eiot.convert.ShowModelConvert;
import com.enjoyiot.module.eiot.dal.dataobject.showmodel.ShowModelDO;
import com.enjoyiot.module.eiot.dal.mysql.showmodel.ShowModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;


/**
 * 产品显示模型 Service 实现类
 *
 * @author EnjoyIot
 */
@Service
@Validated
public class ShowModelServiceImpl implements ShowModelService {

    @Resource
    private ShowModelMapper showModelMapper;

    @Override
    public Long createShowModel(ShowModelSaveReqVO createReqVO) {
        // 插入
        ShowModelDO showModel = BeanUtils.toBean(createReqVO, ShowModelDO.class);
        showModelMapper.insert(showModel);
        // 返回
        return showModel.getId();
    }

    @Override
    public void saveShowModel(ShowModelSaveReqVO updateReqVO) {
        // 校验存在
        String productKey = updateReqVO.getProductKey();
        List<ShowModelRespVO> showModelByProductKey = getShowModelByProductKey(productKey);
        boolean exist = false;
        if (CollUtil.isNotEmpty(showModelByProductKey)) {
            for (ShowModelRespVO s : showModelByProductKey) {
                if (s.getTyp().equals(updateReqVO.getTyp())) {
                    exist = true;
                    break;
                }
            }
        }

        if (exist) {
            // 更新
            LambdaUpdateWrapper updateWrapper = new LambdaUpdateWrapper<ShowModelDO>().set(ShowModelDO::getCnf, updateReqVO.getCnf())
                    .eq(ShowModelDO::getProductKey, updateReqVO.getProductKey()).eq(ShowModelDO::getTyp, updateReqVO.getTyp());
            showModelMapper.update(null, updateWrapper);
        } else {
            ShowModelDO updateObj = BeanUtils.toBean(updateReqVO, ShowModelDO.class);

            showModelMapper.insert(updateObj);
        }

    }

    @Override
    public void deleteShowModel(Long id) {
        // 校验存在
        validateShowModelExists(id);
        // 删除
        showModelMapper.deleteById(id);
    }

    private void validateShowModelExists(Long id) {
        if (showModelMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.NOT_EXISTS);
        }
    }

    @Override
    public ShowModelRespVO getShowModel(Long id) {
        return ShowModelConvert.INSTANCE.convert(showModelMapper.selectById(id));
    }

    @Override
    public List<ShowModelRespVO> getShowModelByProductKey(String productKey) {
        return ShowModelConvert.INSTANCE.convertList(showModelMapper.selectList(ShowModelDO::getProductKey, productKey));
    }

}
