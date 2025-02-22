
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
package com.enjoyiot.module.infra.service.file;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import com.enjoyiot.framework.common.pojo.PageResult;
import com.enjoyiot.module.infra.framework.file.core.client.FileClient;
import com.enjoyiot.module.infra.framework.file.core.client.FileClientConfig;
import com.enjoyiot.module.infra.framework.file.core.client.FileClientFactory;
import com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient;
import com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClientConfig;
import com.enjoyiot.module.infra.framework.file.core.enums.FileStorageEnum;
import com.enjoyiot.framework.test.core.ut.BaseDbUnitTest;
import com.enjoyiot.module.infra.controller.admin.file.vo.config.FileConfigPageReqVO;
import com.enjoyiot.module.infra.controller.admin.file.vo.config.FileConfigSaveReqVO;
import com.enjoyiot.module.infra.dal.dataobject.file.FileConfigDO;
import com.enjoyiot.module.infra.dal.mysql.file.FileConfigMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import static cn.hutool.core.util.RandomUtil.randomEle;
import static com.enjoyiot.framework.common.util.date.LocalDateTimeUtils.buildTime;
import static com.enjoyiot.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static com.enjoyiot.framework.test.core.util.AssertUtils.assertPojoEquals;
import static com.enjoyiot.framework.test.core.util.AssertUtils.assertServiceException;
import static com.enjoyiot.framework.test.core.util.RandomUtils.randomLongId;
import static com.enjoyiot.framework.test.core.util.RandomUtils.randomPojo;
import static com.enjoyiot.module.infra.enums.ErrorCodeConstants.FILE_CONFIG_DELETE_FAIL_MASTER;
import static com.enjoyiot.module.infra.enums.ErrorCodeConstants.FILE_CONFIG_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * {@link FileConfigServiceImpl} 的单元测试类
 *
 * @author EnjoyIot
 */
@Import(FileConfigServiceImpl.class)
public class FileConfigServiceImplTest extends BaseDbUnitTest {

    @Resource
    private FileConfigServiceImpl fileConfigService;

    @Resource
    private FileConfigMapper fileConfigMapper;

    @MockBean
    private Validator validator;
    @MockBean
    private FileClientFactory fileClientFactory;

    @Test
    public void testCreateFileConfig_success() {
        // 准备参数
        Map<String, Object> config = MapUtil.<String, Object>builder().put("basePath", "/ENJOY")
                .put("domain", "http://www.enjoy-iot.cn").build();
        FileConfigSaveReqVO reqVO = randomPojo(FileConfigSaveReqVO.class,
                o -> o.setStorage(FileStorageEnum.LOCAL.getStorage()).setConfig(config))
                .setId(null); // 避免 id 被赋值

        // 调用
        Long fileConfigId = fileConfigService.createFileConfig(reqVO);
        // 断言
        assertNotNull(fileConfigId);
        // 校验记录的属性是否正确
        FileConfigDO fileConfig = fileConfigMapper.selectById(fileConfigId);
        assertPojoEquals(reqVO, fileConfig, "id", "config");
        assertFalse(fileConfig.getMaster());
        assertEquals("/ENJOY", ((LocalFileClientConfig) fileConfig.getConfig()).getBasePath());
        assertEquals("http://www.enjoy-iot.cn", ((LocalFileClientConfig) fileConfig.getConfig()).getDomain());
        // 验证 cache
        assertNull(fileConfigService.getClientCache().getIfPresent(fileConfigId));
    }

    @Test
    public void testUpdateFileConfig_success() {
        // mock 数据
        FileConfigDO dbFileConfig = randomPojo(FileConfigDO.class, o -> o.setStorage(FileStorageEnum.LOCAL.getStorage())
                .setConfig(new LocalFileClientConfig().setBasePath("/ENJOY").setDomain("http://www.enjoy-iot.cn")));
        fileConfigMapper.insert(dbFileConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        FileConfigSaveReqVO reqVO = randomPojo(FileConfigSaveReqVO.class, o -> {
            o.setId(dbFileConfig.getId()); // 设置更新的 ID
            o.setStorage(FileStorageEnum.LOCAL.getStorage());
            Map<String, Object> config = MapUtil.<String, Object>builder().put("basePath", "/ENJOY")
                    .put("domain", "http://doc.enjoy-iot.cn").build();
            o.setConfig(config);
        });

        // 调用
        fileConfigService.updateFileConfig(reqVO);
        // 校验是否更新正确
        FileConfigDO fileConfig = fileConfigMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, fileConfig, "config");
        assertEquals("/ENJOY", ((LocalFileClientConfig) fileConfig.getConfig()).getBasePath());
        assertEquals("http://doc.enjoy-iot.cn", ((LocalFileClientConfig) fileConfig.getConfig()).getDomain());
        // 验证 cache
        assertNull(fileConfigService.getClientCache().getIfPresent(fileConfig.getId()));
    }

    @Test
    public void testUpdateFileConfig_notExists() {
        // 准备参数
        FileConfigSaveReqVO reqVO = randomPojo(FileConfigSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> fileConfigService.updateFileConfig(reqVO), FILE_CONFIG_NOT_EXISTS);
    }

    @Test
    public void testUpdateFileConfigMaster_success() {
        // mock 数据
        FileConfigDO dbFileConfig = randomFileConfigDO().setMaster(false);
        fileConfigMapper.insert(dbFileConfig);// @Sql: 先插入出一条存在的数据
        FileConfigDO masterFileConfig = randomFileConfigDO().setMaster(true);
        fileConfigMapper.insert(masterFileConfig);// @Sql: 先插入出一条存在的数据

        // 调用
        fileConfigService.updateFileConfigMaster(dbFileConfig.getId());
        // 断言数据
        assertTrue(fileConfigMapper.selectById(dbFileConfig.getId()).getMaster());
        assertFalse(fileConfigMapper.selectById(masterFileConfig.getId()).getMaster());
        // 验证 cache
        assertNull(fileConfigService.getClientCache().getIfPresent(0L));
    }

    @Test
    public void testUpdateFileConfigMaster_notExists() {
        // 调用, 并断言异常
        assertServiceException(() -> fileConfigService.updateFileConfigMaster(randomLongId()), FILE_CONFIG_NOT_EXISTS);
    }

    @Test
    public void testDeleteFileConfig_success() {
        // mock 数据
        FileConfigDO dbFileConfig = randomFileConfigDO().setMaster(false);
        fileConfigMapper.insert(dbFileConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFileConfig.getId();

        // 调用
        fileConfigService.deleteFileConfig(id);
        // 校验数据不存在了
        assertNull(fileConfigMapper.selectById(id));
        // 验证 cache
        assertNull(fileConfigService.getClientCache().getIfPresent(id));
    }

    @Test
    public void testDeleteFileConfig_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> fileConfigService.deleteFileConfig(id), FILE_CONFIG_NOT_EXISTS);
    }

    @Test
    public void testDeleteFileConfig_master() {
        // mock 数据
        FileConfigDO dbFileConfig = randomFileConfigDO().setMaster(true);
        fileConfigMapper.insert(dbFileConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFileConfig.getId();

        // 调用, 并断言异常
        assertServiceException(() -> fileConfigService.deleteFileConfig(id), FILE_CONFIG_DELETE_FAIL_MASTER);
    }

    @Test
    public void testGetFileConfigPage() {
        // mock 数据
        FileConfigDO dbFileConfig = randomFileConfigDO().setName("EnjoyIot")
                .setStorage(FileStorageEnum.LOCAL.getStorage());
        dbFileConfig.setCreateTime(LocalDateTimeUtil.parse("2020-01-23", DatePattern.NORM_DATE_PATTERN));// 等会查询到
        fileConfigMapper.insert(dbFileConfig);
        // 测试 name 不匹配
        fileConfigMapper.insert(cloneIgnoreId(dbFileConfig, o -> o.setName("源码")));
        // 测试 storage 不匹配
        fileConfigMapper.insert(cloneIgnoreId(dbFileConfig, o -> o.setStorage(FileStorageEnum.DB.getStorage())));
        // 测试 createTime 不匹配
        fileConfigMapper.insert(cloneIgnoreId(dbFileConfig, o -> o.setCreateTime(LocalDateTimeUtil.parse("2020-11-23", DatePattern.NORM_DATE_PATTERN))));
        // 准备参数
        FileConfigPageReqVO reqVO = new FileConfigPageReqVO();
        reqVO.setName("test");
        reqVO.setStorage(FileStorageEnum.LOCAL.getStorage());
        reqVO.setCreateTime((new LocalDateTime[]{buildTime(2020, 1, 1),
                buildTime(2020, 1, 24)}));

        // 调用
        PageResult<FileConfigDO> pageResult = fileConfigService.getFileConfigPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbFileConfig, pageResult.getList().get(0));
    }

    @Test
    public void testFileConfig() throws Exception {
        // mock 数据
        FileConfigDO dbFileConfig = randomFileConfigDO().setMaster(false);
        fileConfigMapper.insert(dbFileConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFileConfig.getId();
        // mock 获得 Client
        FileClient fileClient = mock(FileClient.class);
        when(fileClientFactory.getFileClient(eq(id))).thenReturn(fileClient);
        when(fileClient.upload(any(), any(), any())).thenReturn("http://www.enjoy-iot.cn");

        // 调用，并断言
        assertEquals("http://www.enjoy-iot.cn", fileConfigService.testFileConfig(id));
    }

    @Test
    public void testGetFileConfig() {
        // mock 数据
        FileConfigDO dbFileConfig = randomFileConfigDO().setMaster(false);
        fileConfigMapper.insert(dbFileConfig);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbFileConfig.getId();

        // 调用，并断言
        assertPojoEquals(dbFileConfig, fileConfigService.getFileConfig(id));
    }

    @Test
    public void testGetFileClient() {
        // mock 数据
        FileConfigDO fileConfig = randomFileConfigDO().setMaster(false);
        fileConfigMapper.insert(fileConfig);
        // 准备参数
        Long id = fileConfig.getId();
        // mock 获得 Client
        FileClient fileClient = new LocalFileClient(id, new LocalFileClientConfig());
        when(fileClientFactory.getFileClient(eq(id))).thenReturn(fileClient);

        // 调用，并断言
        assertSame(fileClient, fileConfigService.getFileClient(id));
        // 断言缓存
        verify(fileClientFactory).createOrUpdateFileClient(eq(id), eq(fileConfig.getStorage()),
                eq(fileConfig.getConfig()));
    }

    @Test
    public void testGetMasterFileClient() {
        // mock 数据
        FileConfigDO fileConfig = randomFileConfigDO().setMaster(true);
        fileConfigMapper.insert(fileConfig);
        // 准备参数
        Long id = fileConfig.getId();
        // mock 获得 Client
        FileClient fileClient = new LocalFileClient(id, new LocalFileClientConfig());
        when(fileClientFactory.getFileClient(eq(fileConfig.getId()))).thenReturn(fileClient);

        // 调用，并断言
        assertSame(fileClient, fileConfigService.getMasterFileClient());
        // 断言缓存
        verify(fileClientFactory).createOrUpdateFileClient(eq(fileConfig.getId()), eq(fileConfig.getStorage()),
                eq(fileConfig.getConfig()));
    }

    private FileConfigDO randomFileConfigDO() {
        return randomPojo(FileConfigDO.class).setStorage(randomEle(FileStorageEnum.values()).getStorage())
                .setConfig(new EmptyFileClientConfig());
    }

    @Data
    public static class EmptyFileClientConfig implements FileClientConfig, Serializable {

    }

}
