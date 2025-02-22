
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
package com.enjoyiot.module.eiot.service.ota;

import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.controller.admin.ota.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: EnjoyIot
 * @Date: 2025/1/17 11:58
 * @Version: V1.0
 * @Description: ota接口
 */
public interface OtaService {
    OtaPackageUploadVo uploadFile(MultipartFile file) throws Exception;

    Long addOtaPackage(OtaPackageBo request);

    Boolean delOtaPackageById(Long id);

    PageResult<OtaPackage> getOtaPackagePageList(OtaPackagePageReq request);

    String startUpgrade(Long otaId, List<Long> deviceIds);

    PageResult<DeviceOtaDetailVo> otaDeviceDetail(DeviceOtaDetailPageReq request);

    PageResult<DeviceOtaInfoVo> otaDeviceInfo(DeviceOtaPageReq request);

    void testStartUpgrade();
}
