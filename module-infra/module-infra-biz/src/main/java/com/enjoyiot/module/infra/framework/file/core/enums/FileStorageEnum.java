
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
package com.enjoyiot.module.infra.framework.file.core.enums;

import cn.hutool.core.util.ArrayUtil;
import com.enjoyiot.module.infra.framework.file.core.client.FileClient;
import com.enjoyiot.module.infra.framework.file.core.client.FileClientConfig;
import com.enjoyiot.module.infra.framework.file.core.client.db.DBFileClient;
import com.enjoyiot.module.infra.framework.file.core.client.db.DBFileClientConfig;
import com.enjoyiot.module.infra.framework.file.core.client.ftp.FtpFileClient;
import com.enjoyiot.module.infra.framework.file.core.client.ftp.FtpFileClientConfig;
import com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient;
import com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClientConfig;
import com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClient;
import com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig;
import com.enjoyiot.module.infra.framework.file.core.client.sftp.SftpFileClient;
import com.enjoyiot.module.infra.framework.file.core.client.sftp.SftpFileClientConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件存储器枚举
 *
 * @author EnjoyIot
 */
@AllArgsConstructor
@Getter
public enum FileStorageEnum {

    DB(1, DBFileClientConfig.class, DBFileClient.class),

    LOCAL(10, LocalFileClientConfig.class, LocalFileClient.class),
    FTP(11, FtpFileClientConfig.class, FtpFileClient.class),
    SFTP(12, SftpFileClientConfig.class, SftpFileClient.class),

    S3(20, S3FileClientConfig.class, S3FileClient.class),
    ;

    /**
     * 存储器
     */
    private final Integer storage;

    /**
     * 配置类
     */
    private final Class<? extends FileClientConfig> configClass;
    /**
     * 客户端类
     */
    private final Class<? extends FileClient> clientClass;

    public static FileStorageEnum getByStorage(Integer storage) {
        return ArrayUtil.firstMatch(o -> o.getStorage().equals(storage), values());
    }

}
