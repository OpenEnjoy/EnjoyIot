
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
package com.enjoyiot.framework.datapermission.core.rule;

import com.enjoyiot.framework.datapermission.core.annotation.DataPermission;
import com.enjoyiot.framework.datapermission.core.aop.DataPermissionContextHolder;
import com.enjoyiot.framework.test.core.ut.BaseMockitoUnitTest;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.enjoyiot.framework.test.core.util.RandomUtils.randomString;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link DataPermissionRuleFactoryImpl} 单元测试
 *
 * @author EnjoyIot
 */
class DataPermissionRuleFactoryImplTest extends BaseMockitoUnitTest {

    @InjectMocks
    private DataPermissionRuleFactoryImpl dataPermissionRuleFactory;

    @Spy
    private List<DataPermissionRule> rules = Arrays.asList(new DataPermissionRule01(),
            new DataPermissionRule02());

    @BeforeEach
    public void setUp() {
        DataPermissionContextHolder.clear();
    }

    @Test
    public void testGetDataPermissionRule_02() {
        // 准备参数
        String mappedStatementId = randomString();

        // 调用
        List<DataPermissionRule> result = dataPermissionRuleFactory.getDataPermissionRule(mappedStatementId);
        // 断言
        assertSame(rules, result);
    }

    @Test
    public void testGetDataPermissionRule_03() {
        // 准备参数
        String mappedStatementId = randomString();
        // mock 方法
        DataPermissionContextHolder.add(AnnotationUtils.findAnnotation(TestClass03.class, DataPermission.class));

        // 调用
        List<DataPermissionRule> result = dataPermissionRuleFactory.getDataPermissionRule(mappedStatementId);
        // 断言
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetDataPermissionRule_04() {
        // 准备参数
        String mappedStatementId = randomString();
        // mock 方法
        DataPermissionContextHolder.add(AnnotationUtils.findAnnotation(TestClass04.class, DataPermission.class));

        // 调用
        List<DataPermissionRule> result = dataPermissionRuleFactory.getDataPermissionRule(mappedStatementId);
        // 断言
        assertEquals(1, result.size());
        assertEquals(DataPermissionRule01.class, result.get(0).getClass());
    }

    @Test
    public void testGetDataPermissionRule_05() {
        // 准备参数
        String mappedStatementId = randomString();
        // mock 方法
        DataPermissionContextHolder.add(AnnotationUtils.findAnnotation(TestClass05.class, DataPermission.class));

        // 调用
        List<DataPermissionRule> result = dataPermissionRuleFactory.getDataPermissionRule(mappedStatementId);
        // 断言
        assertEquals(1, result.size());
        assertEquals(DataPermissionRule02.class, result.get(0).getClass());
    }

    @Test
    public void testGetDataPermissionRule_06() {
        // 准备参数
        String mappedStatementId = randomString();
        // mock 方法
        DataPermissionContextHolder.add(AnnotationUtils.findAnnotation(TestClass06.class, DataPermission.class));

        // 调用
        List<DataPermissionRule> result = dataPermissionRuleFactory.getDataPermissionRule(mappedStatementId);
        // 断言
        assertSame(rules, result);
    }

    @DataPermission(enable = false)
    static class TestClass03 {}

    @DataPermission(includeRules = DataPermissionRule01.class)
    static class TestClass04 {}

    @DataPermission(excludeRules = DataPermissionRule01.class)
    static class TestClass05 {}

    @DataPermission
    static class TestClass06 {}

    static class DataPermissionRule01 implements DataPermissionRule {

        @Override
        public Set<String> getTableNames() {
            return null;
        }

        @Override
        public Expression getExpression(String tableName, Alias tableAlias) {
            return null;
        }

    }

    static class DataPermissionRule02 implements DataPermissionRule {

        @Override
        public Set<String> getTableNames() {
            return null;
        }

        @Override
        public Expression getExpression(String tableName, Alias tableAlias) {
            return null;
        }

    }

}
