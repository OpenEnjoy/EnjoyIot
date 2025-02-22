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

package com.enjoyiot.module.eiot.controller.admin.product.vo;


import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "OpenPropertyVo")
@Data
public class PropertyVO {
    private String identifier;
    private ThingModel.DataType dataType;
    private String name;
    private String accessMode = "rw";

    // 描述
    private String description;

    // 单位
    private String unit;

    private String time;

    private String value;

    public PropertyVO() {
    }

    public PropertyVO(String identifier, ThingModel.DataType dataType, String name, String accessMode, String description, String unit) {
        this.identifier = identifier;
        this.dataType = dataType;
        this.name = name;
        this.accessMode = accessMode;
        this.description = description;
        this.unit = unit;
    }
}
