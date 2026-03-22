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

package com.enjoyiot.eiot;


import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;

/**
 * 数据结构接口
 */
public interface IDbStructureData {

    /**
     * 定义物模型，根据物模型定义表
     */
    void defineThingModel(ThingModel thingModel);

    /**
     * 更新物模型定义
     */
    void updateThingModel(ThingModel thingModel);

    /**
     * 初始化数据库结构
     */
    void initDbStructure();

}
