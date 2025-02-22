
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
package com.enjoyiot.framework.datapermission.core.aop;

import cn.hutool.core.collection.CollUtil;
import com.enjoyiot.framework.datapermission.core.annotation.DataPermission;
import com.enjoyiot.framework.test.core.ut.BaseMockitoUnitTest;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * {@link DataPermissionAnnotationInterceptor} 的单元测试
 *
 * @author EnjoyIot
 */
public class DataPermissionAnnotationInterceptorTest extends BaseMockitoUnitTest {

    @InjectMocks
    private DataPermissionAnnotationInterceptor interceptor;

    @Mock
    private MethodInvocation methodInvocation;

    @BeforeEach
    public void setUp() {
        interceptor.getDataPermissionCache().clear();
    }

    @Test // 无 @DataPermission 注解
    public void testInvoke_none() throws Throwable {
        // 参数
        mockMethodInvocation(TestNone.class);

        // 调用
        Object result = interceptor.invoke(methodInvocation);
        // 断言
        assertEquals("none", result);
        assertEquals(1, interceptor.getDataPermissionCache().size());
        assertTrue(CollUtil.getFirst(interceptor.getDataPermissionCache().values()).enable());
    }

    @Test // 在 Method 上有 @DataPermission 注解
    public void testInvoke_method() throws Throwable {
        // 参数
        mockMethodInvocation(TestMethod.class);

        // 调用
        Object result = interceptor.invoke(methodInvocation);
        // 断言
        assertEquals("method", result);
        assertEquals(1, interceptor.getDataPermissionCache().size());
        assertFalse(CollUtil.getFirst(interceptor.getDataPermissionCache().values()).enable());
    }

    @Test // 在 Class 上有 @DataPermission 注解
    public void testInvoke_class() throws Throwable {
        // 参数
        mockMethodInvocation(TestClass.class);

        // 调用
        Object result = interceptor.invoke(methodInvocation);
        // 断言
        assertEquals("class", result);
        assertEquals(1, interceptor.getDataPermissionCache().size());
        assertFalse(CollUtil.getFirst(interceptor.getDataPermissionCache().values()).enable());
    }

    private void mockMethodInvocation(Class<?> clazz) throws Throwable {
        Object targetObject = clazz.newInstance();
        Method method = targetObject.getClass().getMethod("echo");
        when(methodInvocation.getThis()).thenReturn(targetObject);
        when(methodInvocation.getMethod()).thenReturn(method);
        when(methodInvocation.proceed()).then(invocationOnMock -> method.invoke(targetObject));
    }

    static class TestMethod {

        @DataPermission(enable = false)
        public String echo() {
            return "method";
        }

    }

    @DataPermission(enable = false)
    static class TestClass {

        public String echo() {
            return "class";
        }

    }

    static class TestNone {

        public String echo() {
            return "none";
        }

    }

}
