
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

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 算法 Response VO")
@Data
public class AlgorithmVO {

    @Schema(description = "算法编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "算法名称", required = true, example = "人脸识别算法")
    private String name;

    @Schema(description = "模型框架", required = true, example = "TensorFlow")
    private String framework;

    @Schema(description = "模型格式", required = true, example = "SavedModel")
    private String format;

    @Schema(description = "模型类别", required = true, example = "图像识别")
    private String category;

    @Schema(description = "模型文件", required = true, example = "face_recognition.pb")
    private String modelFile;

    @Schema(description = "算法描述", example = "基于深度学习的人脸识别算法")
    private String description;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;
}