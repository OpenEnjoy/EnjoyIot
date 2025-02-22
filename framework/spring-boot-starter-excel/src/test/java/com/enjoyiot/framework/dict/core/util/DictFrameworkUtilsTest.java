
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
package com.enjoyiot.framework.dict.core.util;

import com.enjoyiot.framework.common.enums.CommonStatusEnum;
import com.enjoyiot.framework.dict.core.DictFrameworkUtils;
import com.enjoyiot.framework.test.core.ut.BaseMockitoUnitTest;
import com.enjoyiot.module.system.api.dict.DictDataApi;
import com.enjoyiot.module.system.api.dict.dto.DictDataRespDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.enjoyiot.framework.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * {@link DictFrameworkUtils} 的单元测试
 */
public class DictFrameworkUtilsTest extends BaseMockitoUnitTest {

    @Mock
    private DictDataApi dictDataApi;

    @BeforeEach
    public void setUp() {
        DictFrameworkUtils.init(dictDataApi);
    }

    @Test
    public void testGetDictDataLabel() {
        // mock 数据
        DictDataRespDTO dataRespDTO = randomPojo(DictDataRespDTO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        // mock 方法
        when(dictDataApi.getDictData(dataRespDTO.getDictType(), dataRespDTO.getValue())).thenReturn(dataRespDTO);

        // 断言返回值
        assertEquals(dataRespDTO.getLabel(), DictFrameworkUtils.getDictDataLabel(dataRespDTO.getDictType(), dataRespDTO.getValue()));
    }

    @Test
    public void testParseDictDataValue() {
        // mock 数据
        DictDataRespDTO resp = randomPojo(DictDataRespDTO.class, o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        // mock 方法
        when(dictDataApi.parseDictData(resp.getDictType(), resp.getLabel())).thenReturn(resp);

        // 断言返回值
        assertEquals(resp.getValue(), DictFrameworkUtils.parseDictDataValue(resp.getDictType(), resp.getLabel()));
    }

}
