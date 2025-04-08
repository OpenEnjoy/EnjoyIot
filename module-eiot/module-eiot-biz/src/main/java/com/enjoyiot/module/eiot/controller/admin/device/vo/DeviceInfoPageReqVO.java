
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
package com.enjoyiot.module.eiot.controller.admin.device.vo;

import com.enjoyiot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.enjoyiot.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;


@Schema(description = "管理后台 - 设备信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceInfoPageReqVO extends PageParam {

    @Schema(description = "设备唯一标识")
    private String dn;

    @Schema(description = "产品key")
    private String productKey;

    @Schema(description = "机构id", example = "13057")
    private Long deptId;

    @Schema(description = "设备类型", example = "2")
    private Integer nodeType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "别名", example = "李四")
    private String name;

    @Schema(description = "设备状态(0:否, 1:在线, 2-未激活，3-禁用)", example = "2")
    private Integer state;



    @Schema(description = "设备序列号")
    private String serialNo;


    @Schema(description = "位置信息")

    private String addr;
    @Schema(description = "固件版本")

    private String firmVersion;

    @Schema(description = "分组id")

    private Long groupId;

}
