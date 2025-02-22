
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
package com.enjoyiot.module.ai.controller.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AlgorithmBaseVO {

    @Schema(description = "算法名称", required = true, example = "人脸识别算法")
    @NotEmpty(message = "算法名称不能为空")
    private String name;

    @Schema(description = "模型框架", required = true, example = "TensorFlow")
    @NotEmpty(message = "模型框架不能为空")
    private String framework;

    @Schema(description = "模型格式", required = true, example = "SavedModel")
    @NotEmpty(message = "模型格式不能为空")
    private String format;

    @Schema(description = "模型类别", required = true, example = "图像识别")
    @NotEmpty(message = "模型类别不能为空")
    private String category;

    @Schema(description = "模型文件", required = true)
    @NotEmpty(message = "模型文件不能为空")
    private String modelFile;

    @Schema(description = "算法描述", example = "基于深度学习的人脸识别算法")
    private String description;
}