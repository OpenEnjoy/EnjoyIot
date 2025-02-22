
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

import com.enjoyiot.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import com.enjoyiot.module.infra.dal.dataobject.codegen.CodegenTableDO;
import com.enjoyiot.module.infra.enums.codegen.CodegenFrontTypeEnum;
import com.enjoyiot.module.infra.enums.codegen.CodegenTemplateTypeEnum;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * {@link CodegenEngine} 的 Vue2 + Element UI 单元测试
 *
 * @author EnjoyIot
 */
@Disabled
public class CodegenEngineVue2Test extends CodegenEngineAbstractTest {

    @Test
    public void testExecute_vue2_one() {
        // 准备参数
        CodegenTableDO table = getTable("student")
                .setFrontType(CodegenFrontTypeEnum.VUE2.getType())
                .setTemplateType(CodegenTemplateTypeEnum.ONE.getType());
        List<CodegenColumnDO> columns = getColumnList("student");

        // 调用
        Map<String, String> result = codegenEngine.execute(table, columns, null, null);
        // 生成测试文件
        //writeResult(result, resourcesPath + "/vue2_one");
        // 断言
        assertResult(result, "/vue2_one");
    }

    @Test
    public void testExecute_vue2_tree() {
        // 准备参数
        CodegenTableDO table = getTable("category")
                .setFrontType(CodegenFrontTypeEnum.VUE2.getType())
                .setTemplateType(CodegenTemplateTypeEnum.TREE.getType());
        List<CodegenColumnDO> columns = getColumnList("category");

        // 调用
        Map<String, String> result = codegenEngine.execute(table, columns, null, null);
        // 生成测试文件
        //writeResult(result, resourcesPath + "/vue2_tree");
        // 断言
        assertResult(result, "/vue2_tree");
    }

    @Test
    public void testExecute_vue2_master_normal() {
        testExecute_vue2_master(CodegenTemplateTypeEnum.MASTER_NORMAL, "/vue2_master_normal");
    }

    @Test
    public void testExecute_vue2_master_erp() {
        testExecute_vue2_master(CodegenTemplateTypeEnum.MASTER_ERP, "/vue2_master_erp");
    }

    @Test
    public void testExecute_vue2_master_inner() {
        testExecute_vue2_master(CodegenTemplateTypeEnum.MASTER_INNER, "/vue2_master_inner");
    }

    private void testExecute_vue2_master(CodegenTemplateTypeEnum templateType,
                                         String path) {
        // 准备参数
        CodegenTableDO table = getTable("student")
                .setFrontType(CodegenFrontTypeEnum.VUE2.getType())
                .setTemplateType(templateType.getType());
        List<CodegenColumnDO> columns = getColumnList("student");
        // 准备参数（子表）
        CodegenTableDO contactTable = getTable("contact")
                .setTemplateType(CodegenTemplateTypeEnum.SUB.getType())
                .setFrontType(CodegenFrontTypeEnum.VUE2.getType())
                .setSubJoinColumnId(100L).setSubJoinMany(true);
        List<CodegenColumnDO> contactColumns = getColumnList("contact");
        // 准备参数（班主任）
        CodegenTableDO teacherTable = getTable("teacher")
                .setTemplateType(CodegenTemplateTypeEnum.SUB.getType())
                .setFrontType(CodegenFrontTypeEnum.VUE2.getType())
                .setSubJoinColumnId(200L).setSubJoinMany(false);
        List<CodegenColumnDO> teacherColumns = getColumnList("teacher");

        // 调用
        Map<String, String> result = codegenEngine.execute(table, columns,
                Arrays.asList(contactTable, teacherTable), Arrays.asList(contactColumns, teacherColumns));
        // 生成测试文件
        //writeResult(result, resourcesPath + path);
        // 断言
        assertResult(result, path);
    }

}
