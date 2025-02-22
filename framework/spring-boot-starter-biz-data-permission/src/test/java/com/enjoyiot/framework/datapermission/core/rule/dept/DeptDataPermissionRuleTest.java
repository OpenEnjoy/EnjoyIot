
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
package com.enjoyiot.framework.datapermission.core.rule.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.enjoyiot.framework.common.enums.UserTypeEnum;
import com.enjoyiot.framework.common.util.collection.SetUtils;
import com.enjoyiot.framework.security.core.LoginUser;
import com.enjoyiot.framework.security.core.util.SecurityFrameworkUtils;
import com.enjoyiot.framework.test.core.ut.BaseMockitoUnitTest;
import com.enjoyiot.module.system.api.permission.PermissionApi;
import com.enjoyiot.module.system.api.permission.dto.DeptDataPermissionRespDTO;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.util.Map;

import static com.enjoyiot.framework.datapermission.core.rule.dept.DeptDataPermissionRule.EXPRESSION_NULL;
import static com.enjoyiot.framework.test.core.util.RandomUtils.randomPojo;
import static com.enjoyiot.framework.test.core.util.RandomUtils.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

/**
 * {@link DeptDataPermissionRule} 的单元测试
 *
 * @author EnjoyIot
 */
class DeptDataPermissionRuleTest extends BaseMockitoUnitTest {

    @InjectMocks
    private DeptDataPermissionRule rule;

    @Mock
    private PermissionApi permissionApi;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        // 清空 rule
        rule.getTableNames().clear();
        ((Map<String, String>) ReflectUtil.getFieldValue(rule, "deptColumns")).clear();
        ((Map<String, String>) ReflectUtil.getFieldValue(rule, "deptColumns")).clear();
    }

    @Test // 无 LoginUser
    public void testGetExpression_noLoginUser() {
        // 准备参数
        String tableName = randomString();
        Alias tableAlias = new Alias(randomString());
        // mock 方法

        // 调用
        Expression expression = rule.getExpression(tableName, tableAlias);
        // 断言
        assertNull(expression);
    }

    @Test // 无数据权限时
    public void testGetExpression_noDeptDataPermission() {
        try (MockedStatic<SecurityFrameworkUtils> securityFrameworkUtilsMock
                     = mockStatic(SecurityFrameworkUtils.class)) {
            // 准备参数
            String tableName = "t_user";
            Alias tableAlias = new Alias("u");
            // mock 方法
            LoginUser loginUser = randomPojo(LoginUser.class, o -> o.setId(1L)
                    .setUserType(UserTypeEnum.ADMIN.getValue()));
            securityFrameworkUtilsMock.when(SecurityFrameworkUtils::getLoginUser).thenReturn(loginUser);
            // mock 方法（permissionApi 返回 null）
            when(permissionApi.getDeptDataPermission(eq(loginUser.getId()))).thenReturn(null);

            // 调用
            NullPointerException exception = assertThrows(NullPointerException.class,
                    () -> rule.getExpression(tableName, tableAlias));
            // 断言
            assertEquals("LoginUser(1) Table(t_user/u) 未返回数据权限", exception.getMessage());
        }
    }

    @Test // 全部数据权限
    public void testGetExpression_allDeptDataPermission() {
        try (MockedStatic<SecurityFrameworkUtils> securityFrameworkUtilsMock
                     = mockStatic(SecurityFrameworkUtils.class)) {
            // 准备参数
            String tableName = "t_user";
            Alias tableAlias = new Alias("u");
            // mock 方法（LoginUser）
            LoginUser loginUser = randomPojo(LoginUser.class, o -> o.setId(1L)
                    .setUserType(UserTypeEnum.ADMIN.getValue()));
            securityFrameworkUtilsMock.when(SecurityFrameworkUtils::getLoginUser).thenReturn(loginUser);
            // mock 方法（DeptDataPermissionRespDTO）
            DeptDataPermissionRespDTO deptDataPermission = new DeptDataPermissionRespDTO().setAll(true);
            when(permissionApi.getDeptDataPermission(same(1L))).thenReturn(deptDataPermission);

            // 调用
            Expression expression = rule.getExpression(tableName, tableAlias);
            // 断言
            assertNull(expression);
            assertSame(deptDataPermission, loginUser.getContext(DeptDataPermissionRule.CONTEXT_KEY, DeptDataPermissionRespDTO.class));
        }
    }

    @Test // 即不能查看部门，又不能查看自己，则说明 100% 无权限
    public void testGetExpression_noDept_noSelf() {
        try (MockedStatic<SecurityFrameworkUtils> securityFrameworkUtilsMock
                     = mockStatic(SecurityFrameworkUtils.class)) {
            // 准备参数
            String tableName = "t_user";
            Alias tableAlias = new Alias("u");
            // mock 方法（LoginUser）
            LoginUser loginUser = randomPojo(LoginUser.class, o -> o.setId(1L)
                    .setUserType(UserTypeEnum.ADMIN.getValue()));
            securityFrameworkUtilsMock.when(SecurityFrameworkUtils::getLoginUser).thenReturn(loginUser);
            // mock 方法（DeptDataPermissionRespDTO）
            DeptDataPermissionRespDTO deptDataPermission = new DeptDataPermissionRespDTO();
            when(permissionApi.getDeptDataPermission(same(1L))).thenReturn(deptDataPermission);

            // 调用
            Expression expression = rule.getExpression(tableName, tableAlias);
            // 断言
            assertEquals("null = null", expression.toString());
            assertSame(deptDataPermission, loginUser.getContext(DeptDataPermissionRule.CONTEXT_KEY, DeptDataPermissionRespDTO.class));
        }
    }

    @Test // 拼接 Dept 和 User 的条件（字段都不符合）
    public void testGetExpression_noDeptColumn_noSelfColumn() {
        try (MockedStatic<SecurityFrameworkUtils> securityFrameworkUtilsMock
                     = mockStatic(SecurityFrameworkUtils.class)) {
            // 准备参数
            String tableName = "t_user";
            Alias tableAlias = new Alias("u");
            // mock 方法（LoginUser）
            LoginUser loginUser = randomPojo(LoginUser.class, o -> o.setId(1L)
                    .setUserType(UserTypeEnum.ADMIN.getValue()));
            securityFrameworkUtilsMock.when(SecurityFrameworkUtils::getLoginUser).thenReturn(loginUser);
            // mock 方法（DeptDataPermissionRespDTO）
            DeptDataPermissionRespDTO deptDataPermission = new DeptDataPermissionRespDTO()
                    .setDeptIds(SetUtils.asSet(10L, 20L)).setSelf(true);
            when(permissionApi.getDeptDataPermission(same(1L))).thenReturn(deptDataPermission);

            // 调用
            Expression expression = rule.getExpression(tableName, tableAlias);
            // 断言
            assertSame(EXPRESSION_NULL, expression);
            assertSame(deptDataPermission, loginUser.getContext(DeptDataPermissionRule.CONTEXT_KEY, DeptDataPermissionRespDTO.class));
        }
    }

    @Test // 拼接 Dept 和 User 的条件（self 符合）
    public void testGetExpression_noDeptColumn_yesSelfColumn() {
        try (MockedStatic<SecurityFrameworkUtils> securityFrameworkUtilsMock
                     = mockStatic(SecurityFrameworkUtils.class)) {
            // 准备参数
            String tableName = "t_user";
            Alias tableAlias = new Alias("u");
            // mock 方法（LoginUser）
            LoginUser loginUser = randomPojo(LoginUser.class, o -> o.setId(1L)
                    .setUserType(UserTypeEnum.ADMIN.getValue()));
            securityFrameworkUtilsMock.when(SecurityFrameworkUtils::getLoginUser).thenReturn(loginUser);
            // mock 方法（DeptDataPermissionRespDTO）
            DeptDataPermissionRespDTO deptDataPermission = new DeptDataPermissionRespDTO()
                    .setSelf(true);
            when(permissionApi.getDeptDataPermission(same(1L))).thenReturn(deptDataPermission);
            // 添加 user 字段配置
            rule.addUserColumn("t_user", "id");

            // 调用
            Expression expression = rule.getExpression(tableName, tableAlias);
            // 断言
            assertEquals("u.id = 1", expression.toString());
            assertSame(deptDataPermission, loginUser.getContext(DeptDataPermissionRule.CONTEXT_KEY, DeptDataPermissionRespDTO.class));
        }
    }

    @Test // 拼接 Dept 和 User 的条件（dept 符合）
    public void testGetExpression_yesDeptColumn_noSelfColumn() {
        try (MockedStatic<SecurityFrameworkUtils> securityFrameworkUtilsMock
                     = mockStatic(SecurityFrameworkUtils.class)) {
            // 准备参数
            String tableName = "t_user";
            Alias tableAlias = new Alias("u");
            // mock 方法（LoginUser）
            LoginUser loginUser = randomPojo(LoginUser.class, o -> o.setId(1L)
                    .setUserType(UserTypeEnum.ADMIN.getValue()));
            securityFrameworkUtilsMock.when(SecurityFrameworkUtils::getLoginUser).thenReturn(loginUser);
            // mock 方法（DeptDataPermissionRespDTO）
            DeptDataPermissionRespDTO deptDataPermission = new DeptDataPermissionRespDTO()
                    .setDeptIds(CollUtil.newLinkedHashSet(10L, 20L));
            when(permissionApi.getDeptDataPermission(same(1L))).thenReturn(deptDataPermission);
            // 添加 dept 字段配置
            rule.addDeptColumn("t_user", "dept_id");

            // 调用
            Expression expression = rule.getExpression(tableName, tableAlias);
            // 断言
            assertEquals("u.dept_id IN (10, 20)", expression.toString());
            assertSame(deptDataPermission, loginUser.getContext(DeptDataPermissionRule.CONTEXT_KEY, DeptDataPermissionRespDTO.class));
        }
    }

    @Test // 拼接 Dept 和 User 的条件（dept + self 符合）
    public void testGetExpression_yesDeptColumn_yesSelfColumn() {
        try (MockedStatic<SecurityFrameworkUtils> securityFrameworkUtilsMock
                     = mockStatic(SecurityFrameworkUtils.class)) {
            // 准备参数
            String tableName = "t_user";
            Alias tableAlias = new Alias("u");
            // mock 方法（LoginUser）
            LoginUser loginUser = randomPojo(LoginUser.class, o -> o.setId(1L)
                    .setUserType(UserTypeEnum.ADMIN.getValue()));
            securityFrameworkUtilsMock.when(SecurityFrameworkUtils::getLoginUser).thenReturn(loginUser);
            // mock 方法（DeptDataPermissionRespDTO）
            DeptDataPermissionRespDTO deptDataPermission = new DeptDataPermissionRespDTO()
                    .setDeptIds(CollUtil.newLinkedHashSet(10L, 20L)).setSelf(true);
            when(permissionApi.getDeptDataPermission(same(1L))).thenReturn(deptDataPermission);
            // 添加 user 字段配置
            rule.addUserColumn("t_user", "id");
            // 添加 dept 字段配置
            rule.addDeptColumn("t_user", "dept_id");

            // 调用
            Expression expression = rule.getExpression(tableName, tableAlias);
            // 断言
            assertEquals("(u.dept_id IN (10, 20) OR u.id = 1)", expression.toString());
            assertSame(deptDataPermission, loginUser.getContext(DeptDataPermissionRule.CONTEXT_KEY, DeptDataPermissionRespDTO.class));
        }
    }

}
