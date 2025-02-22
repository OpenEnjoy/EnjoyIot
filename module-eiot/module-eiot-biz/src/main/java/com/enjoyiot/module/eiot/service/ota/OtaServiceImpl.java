/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
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


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.ota.dto.DeviceOtaDetail;
import com.enjoyiot.module.eiot.controller.admin.ota.vo.*;
import com.enjoyiot.module.eiot.convert.OtaConvert;
import com.enjoyiot.module.eiot.dal.dataobject.DeviceOtaInfoDO;
import com.enjoyiot.module.eiot.dal.dataobject.OtaPackageDO;
import com.enjoyiot.module.eiot.dal.mysql.DeviceOtaInfoMapper;
import com.enjoyiot.module.eiot.dal.mysql.OtaDetailMapper;
import com.enjoyiot.module.eiot.dal.mysql.OtaPackageMapper;
import com.enjoyiot.module.eiot.service.device.DeviceCtrlService;
import com.enjoyiot.module.eiot.service.device.DeviceInfoService;
import com.enjoyiot.module.infra.api.file.FileApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @Author: Enjoy-iot
 * @Date: 2023/5/19 20:49
 * @Description:
 */
@Slf4j
@Service
public class OtaServiceImpl implements OtaService {

    @Resource
    private DeviceCtrlService deviceCtrlService;

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private OtaPackageMapper otaPackageMapper;

    @Resource
    private DeviceOtaInfoMapper deviceOtaInfoMapper;

    @Resource
    private OtaDetailMapper otaDetailMapper;

    @Resource
    private FileApi fileApi;

    public OtaPackageUploadVo uploadFile(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new ServiceException(400, "文件名为空，获取失败");
        }
        String avatar = fileApi.createFile(IoUtil.readBytes(file.getInputStream()));

        String md5 = md5OfFile(file);
        OtaPackageUploadVo otaPackageUploadVo = new OtaPackageUploadVo();
        otaPackageUploadVo.setUrl(avatar);
        otaPackageUploadVo.setSize(file.getSize());
        otaPackageUploadVo.setMd5(md5);
        otaPackageUploadVo.setOriginalName(originalFileName);
//        otaPackageUploadVo.setOssId(oss.getId());
        return otaPackageUploadVo;
    }

    public static String md5OfFile(MultipartFile multipartFile) throws Exception {
        // TODO: 得删除
        File file = null;
        if (multipartFile.isEmpty()) {
            return "";
        }
        try {
            //本质上还是在项目根路径创建文件
            file = new File(multipartFile.getOriginalFilename());
            //将MultipartFile的byte[]写入到file中
            FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
            byte[] bytes = DigestUtils.md5(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fs = new FileInputStream(file);
        BufferedInputStream bs = new BufferedInputStream(fs);
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = bs.read(buffer, 0, buffer.length)) != -1) {
            md.update(buffer, 0, bytesRead);
        }
        byte[] digest = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte bite : digest) {
            sb.append(String.format("%02x", bite & 0xff));
        }
        return sb.toString();
    }

    public Long addOtaPackage(OtaPackageBo req) {
        OtaPackageDO obj = OtaConvert.INSTANCE.convert(req);
        if (Objects.isNull(obj.getId())) {
            otaPackageMapper.insert(obj);
            return obj.getId();
        }
        otaPackageMapper.updateById(obj);
        return obj.getId();
    }

    public Boolean delOtaPackageById(Long id) {
        otaPackageMapper.deleteById(id);
        return Boolean.TRUE;
    }

    public PageResult<OtaPackage> getOtaPackagePageList(OtaPackagePageReq request) {
        return OtaConvert.INSTANCE.convertPage(otaPackageMapper.selectPage(request));
    }

    /**
     * 开始升级
     */
    public String startUpgrade(Long otaPackageId, List<Long> deviceIds) {
        OtaPackageDO otaPackageDO = otaPackageMapper.selectById(otaPackageId);
        if (Objects.isNull(otaPackageDO)) {
            throw new ServiceException(400, "ota包不存在");
        }

        DeviceOtaInfoDO deviceOtaInfo = DeviceOtaInfoDO.builder()
                .total(deviceIds.size())
                .packageId(otaPackageId)
                .productKey(otaPackageDO.getProductKey())
                .module(otaPackageDO.getModule())
                .remark(otaPackageDO.getRemark())
                .version(otaPackageDO.getVersion())
                .build();
        deviceOtaInfoMapper.insert(deviceOtaInfo);

        List<DeviceOtaDetail> deviceOtaDetails = new ArrayList<>();
        AtomicReference<Integer> success = new AtomicReference<>(0);
        AtomicReference<Integer> fail = new AtomicReference<>(0);
        deviceIds.forEach(deviceId -> {
            try {
                DeviceInfo deviceInfo = deviceInfoService.getDeviceInfo(deviceId);
                deviceCtrlService.otaUpgrade(deviceId, true, otaPackageDO);
                deviceOtaDetails.add(DeviceOtaDetail.builder()
                        .taskId(IdUtil.fastSimpleUUID())
                        .deviceName(deviceInfo.getName())
                        .otaInfoId(deviceOtaInfo.getId())
                        .module(otaPackageDO.getModule())
                        .version(otaPackageDO.getVersion())
                        .step(0)
                        .deviceId(deviceId)
                        .build());
                success.getAndSet(success.get() + 1);
            } catch (Exception ex) {
                log.error("add device upgrade error deviceId:{} ", deviceId, ex);
                fail.getAndSet(fail.get() + 1);
            }
        });

        //todo
//        deviceOtaDetailData.batchSave(deviceOtaDetails);
        deviceOtaInfo.setSuccess(success.get());
        deviceOtaInfo.setFail(fail.get());
        deviceOtaInfoMapper.updateById(deviceOtaInfo);
        return "发起升级【" + success.get() + "】条,失败【" + fail.get() + "】条";
    }

    public PageResult<DeviceOtaDetailVo> otaDeviceDetail(DeviceOtaDetailPageReq request) {
        return OtaConvert.INSTANCE.convertDetailPage(otaDetailMapper.selectPage(request));
    }

    public PageResult<DeviceOtaInfoVo> otaDeviceInfo(DeviceOtaPageReq request) {
        return OtaConvert.INSTANCE.convertInfoVoPage(deviceOtaInfoMapper.selectPage(request));
    }

    public void testStartUpgrade() {
        Long deviceId = 1L;
        OtaPackage otaPackage = OtaPackage.builder()
                .remark("升级测试")
                .md5("AAAABCC")
                .sign("AAAAAAAA")
                .isDiff(false)
                .size(1024L)
                .url("http://www.baidu.com/resource/test.jpg")
                .version("1.2.1")
                .build();
        deviceCtrlService.otaUpgrade(deviceId, true, otaPackage);
    }
}
