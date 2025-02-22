
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
package com.enjoyiot.module.infra.service.codegen.inner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.framework.test.core.ut.BaseMockitoUnitTest;
import com.enjoyiot.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import com.enjoyiot.module.infra.dal.dataobject.codegen.CodegenTableDO;
import com.enjoyiot.module.infra.framework.codegen.config.CodegenProperties;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link CodegenEngine} 的单元测试抽象基类
 *
 * @author EnjoyIot
 */
public abstract class CodegenEngineAbstractTest extends BaseMockitoUnitTest {

    /**
     * 测试文件资源目录
     */
    private String resourcesPath = "";

    @InjectMocks
    protected CodegenEngine codegenEngine;

    @Spy
    protected CodegenProperties codegenProperties = new CodegenProperties()
            .setBasePackage("com.enjoyiot");

    @BeforeEach
    public void setUp() {
        codegenEngine.setJakartaEnable(true); // 强制使用 jakarta，保证单测可以基于 jakarta 断言
        codegenEngine.initGlobalBindingMap();
        // 单测强制使用
        // 获取测试文件 resources 路径
        String absolutePath = FileUtil.getAbsolutePath("application-unit-test.yaml");
        // 系统不一样生成的文件也有差异，那就各自生成各自的
        resourcesPath = absolutePath.split("/target")[0] + "/src/test/resources/codegen/";
    }

    protected static CodegenTableDO getTable(String name) {
        String content = ResourceUtil.readUtf8Str("codegen/table/" + name + ".json");
        return JsonUtils.parseObject(content, "table", CodegenTableDO.class);
    }

    protected static List<CodegenColumnDO> getColumnList(String name) {
        String content = ResourceUtil.readUtf8Str("codegen/table/" + name + ".json");
        List<CodegenColumnDO> list = JsonUtils.parseArray(content, "columns", CodegenColumnDO.class);
        list.forEach(column -> {
            if (column.getNullable() == null) {
                column.setNullable(false);
            }
            if (column.getCreateOperation() == null) {
                column.setCreateOperation(false);
            }
            if (column.getUpdateOperation() == null) {
                column.setUpdateOperation(false);
            }
            if (column.getListOperation() == null) {
                column.setListOperation(false);
            }
            if (column.getListOperationResult() == null) {
                column.setListOperationResult(false);
            }
        });
        return list;
    }

    @SuppressWarnings("rawtypes")
    protected static void assertResult(Map<String, String> result, String path) {
        String assertContent = ResourceUtil.readUtf8Str("codegen/" + path + "/assert.json");
        List<HashMap> asserts = JsonUtils.parseArray(assertContent, HashMap.class);
        assertEquals(asserts.size(), result.size());
        // 校验每个文件
        asserts.forEach(assertMap -> {
            String contentPath = (String) assertMap.get("contentPath");
            String filePath = (String) assertMap.get("filePath");
            String content = ResourceUtil.readUtf8Str("codegen/" + path + "/" + contentPath);
            assertEquals(content, result.get(filePath), filePath + "：不匹配");
        });
    }

    // ==================== 调试专用 ====================

    /**
     * 【调试使用】将生成的代码，写入到文件
     *
     * @param result 生成的代码
     * @param path   写入文件的路径
     */
    protected void writeFile(Map<String, String> result, String path) {
        // 生成压缩包
        String[] paths = result.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = result.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // 写入文件
        FileUtil.writeBytes(outputStream.toByteArray(), path);
    }

    /**
     * 【调试使用】将生成的结果，写入到文件
     *
     * @param result   生成的代码
     * @param basePath 写入文件的路径（绝对路径）
     */
    protected void writeResult(Map<String, String> result, String basePath) {
        // 写入文件内容
        List<Map<String, String>> asserts = new ArrayList<>();
        result.forEach((filePath, fileContent) -> {
            String lastFilePath = StrUtil.subAfter(filePath, '/', true);
            String contentPath = StrUtil.subAfter(lastFilePath, '.', true)
                    + '/' + StrUtil.subBefore(lastFilePath, '.', true);
            asserts.add(MapUtil.<String, String>builder().put("filePath", filePath)
                    .put("contentPath", contentPath).build());
            FileUtil.writeUtf8String(fileContent, basePath + "/" + contentPath);
        });
        // 写入 assert.json 文件
        FileUtil.writeUtf8String(JsonUtils.toJsonPrettyString(asserts), basePath + "/assert.json");
    }

}
