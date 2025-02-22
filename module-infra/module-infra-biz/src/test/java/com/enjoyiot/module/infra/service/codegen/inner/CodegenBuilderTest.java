
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

import com.enjoyiot.framework.test.core.ut.BaseMockitoUnitTest;
import com.enjoyiot.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import com.enjoyiot.module.infra.dal.dataobject.codegen.CodegenTableDO;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Collections;
import java.util.List;

import static com.enjoyiot.framework.test.core.util.RandomUtils.randomLongId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CodegenBuilderTest extends BaseMockitoUnitTest {

    @InjectMocks
    private CodegenBuilder codegenBuilder;

    @Test
    public void testBuildTable() {
        // 准备参数
        TableInfo tableInfo = mock(TableInfo.class);
        // mock 方法
        when(tableInfo.getName()).thenReturn("system_user");
        when(tableInfo.getComment()).thenReturn("用户");

        // 调用
        CodegenTableDO table = codegenBuilder.buildTable(tableInfo);
        // 断言
        assertEquals("system_user", table.getTableName());
        assertEquals("用户", table.getTableComment());
        assertEquals("system", table.getModuleName());
        assertEquals("user", table.getBusinessName());
        assertEquals("User", table.getClassName());
        assertEquals("用户", table.getClassComment());
    }

    @Test
    public void testBuildColumns() {
        // 准备参数
        Long tableId = randomLongId();
        TableField tableField = mock(TableField.class);
        List<TableField> tableFields = Collections.singletonList(tableField);
        // mock 方法
        TableField.MetaInfo metaInfo = mock(TableField.MetaInfo.class);
        when(tableField.getMetaInfo()).thenReturn(metaInfo);
        when(metaInfo.getJdbcType()).thenReturn(JdbcType.BIGINT);
        when(tableField.getComment()).thenReturn("编号");
        when(tableField.isKeyFlag()).thenReturn(true);
        IColumnType columnType = mock(IColumnType.class);
        when(tableField.getColumnType()).thenReturn(columnType);
        when(columnType.getType()).thenReturn("Long");
        when(tableField.getName()).thenReturn("id2");
        when(tableField.getPropertyName()).thenReturn("id");

        // 调用
        List<CodegenColumnDO> columns = codegenBuilder.buildColumns(tableId, tableFields);
        // 断言
        assertEquals(1, columns.size());
        CodegenColumnDO column = columns.get(0);
        assertEquals(tableId, column.getTableId());
        assertEquals("id2", column.getColumnName());
        assertEquals("BIGINT", column.getDataType());
        assertEquals("编号", column.getColumnComment());
        assertFalse(column.getNullable());
        assertTrue(column.getPrimaryKey());
        assertEquals(1, column.getOrdinalPosition());
        assertEquals("Long", column.getJavaType());
        assertEquals("id", column.getJavaField());
        assertNull(column.getDictType());
        assertNotNull(column.getExample());
        assertFalse(column.getCreateOperation());
        assertTrue(column.getUpdateOperation());
        assertFalse(column.getListOperation());
        assertEquals("=", column.getListOperationCondition());
        assertTrue(column.getListOperationResult());
        assertEquals("input", column.getHtmlType());
    }

}
