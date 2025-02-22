
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
package com.enjoyiot.module.infra.framework.file.core.s3;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import com.enjoyiot.framework.common.util.validation.ValidationUtils;
import com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClient;
import com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;

public class S3FileClientTest {

    @Test
    @Disabled // MinIO，如果要集成测试，可以注释本行
    public void testMinIO() throws Exception {
        S3FileClientConfig config = new S3FileClientConfig();
        // 配置成你自己的
        config.setAccessKey("admin");
        config.setAccessSecret("password");
        config.setBucket("enjoyiot");
        config.setDomain(null);
        // 默认 9000 endpoint
        config.setEndpoint("http://127.0.0.1:9000");

        // 执行上传
        testExecuteUpload(config);
    }

    @Test
    @Disabled // 阿里云 OSS，如果要集成测试，可以注释本行
    public void testAliyun() throws Exception {
        S3FileClientConfig config = new S3FileClientConfig();
        // 配置成你自己的
        config.setAccessKey(System.getenv("ALIYUN_ACCESS_KEY"));
        config.setAccessSecret(System.getenv("ALIYUN_SECRET_KEY"));
        config.setBucket("test-aoteman");
        config.setDomain(null); // 如果有自定义域名，则可以设置。http://ali-oss.enjoy-iot.cn
        // 默认北京的 endpoint
        config.setEndpoint("oss-cn-beijing.aliyuncs.com");

        // 执行上传
        testExecuteUpload(config);
    }

    @Test
    @Disabled // 腾讯云 COS，如果要集成测试，可以注释本行
    public void testQCloud() throws Exception {
        S3FileClientConfig config = new S3FileClientConfig();
        // 配置成你自己的
        config.setAccessKey(System.getenv("QCLOUD_ACCESS_KEY"));
        config.setAccessSecret(System.getenv("QCLOUD_SECRET_KEY"));
        config.setBucket("aoteman-1255880240");
        config.setDomain(null); // 如果有自定义域名，则可以设置。http://tengxun-oss.enjoy-iot.cn
        // 默认上海的 endpoint
        config.setEndpoint("cos.ap-shanghai.myqcloud.com");

        // 执行上传
        testExecuteUpload(config);
    }

    @Test
    @Disabled // 七牛云存储，如果要集成测试，可以注释本行
    public void testQiniu() throws Exception {
        S3FileClientConfig config = new S3FileClientConfig();
        // 配置成你自己的
//        config.setAccessKey(System.getenv("QINIU_ACCESS_KEY"));
//        config.setAccessSecret(System.getenv("QINIU_SECRET_KEY"));
        config.setAccessKey("b7yvuhBSAGjmtPhMFcn9iMOxUOY_I06cA_p0ZUx8");
        config.setAccessSecret("kXM1l5ia1RvSX3QaOEcwI3RLz3Y2rmNszWonKZtP");
        config.setBucket("ruoyi-vue-pro");
        config.setDomain("http://test.enjoy-iot.cn");
        // 默认上海的 endpoint
        config.setEndpoint("s3-cn-south-1.qiniucs.com");

        // 执行上传
        testExecuteUpload(config);
    }

    @Test
    @Disabled // 华为云存储，如果要集成测试，可以注释本行
    public void testHuaweiCloud() throws Exception {
        S3FileClientConfig config = new S3FileClientConfig();
        // 配置成你自己的
//        config.setAccessKey(System.getenv("HUAWEI_CLOUD_ACCESS_KEY"));
//        config.setAccessSecret(System.getenv("HUAWEI_CLOUD_SECRET_KEY"));
        config.setBucket("enjoy-iot");
        config.setDomain(null); // 如果有自定义域名，则可以设置。
        // 默认上海的 endpoint
        config.setEndpoint("obs.cn-east-3.myhuaweicloud.com");

        // 执行上传
        testExecuteUpload(config);
    }

    private void testExecuteUpload(S3FileClientConfig config) throws Exception {
        // 校验配置
        ValidationUtils.validate(Validation.buildDefaultValidatorFactory().getValidator(), config);
        // 创建 Client
        S3FileClient client = new S3FileClient(0L, config);
        client.init();
        // 上传文件
        String path = IdUtil.fastSimpleUUID() + ".jpg";
        byte[] content = ResourceUtil.readBytes("file/erweima.jpg");
        String fullPath = client.upload(content, path, "image/jpeg");
        System.out.println("访问地址：" + fullPath);
        // 读取文件
        if (true) {
            byte[] bytes = client.getContent(path);
            System.out.println("文件内容：" + bytes.length);
        }
        // 删除文件
        if (false) {
            client.delete(path);
        }
    }

}
