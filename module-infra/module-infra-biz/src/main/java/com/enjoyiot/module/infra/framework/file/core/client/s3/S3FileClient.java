
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
package com.enjoyiot.module.infra.framework.file.core.client.s3;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.enjoyiot.module.infra.framework.file.core.client.AbstractFileClient;
import lombok.SneakyThrows;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

import java.net.URI;
import java.time.Duration;

/**
 * 基于 S3 协议的文件客户端，实现 MinIO、阿里云、腾讯云、七牛云、华为云等云服务
 * <p>
 * S3 协议的客户端，采用亚马逊提供的 software.amazon.awssdk.s3 库
 *
 * @author EnjoyIot
 */
public class S3FileClient extends AbstractFileClient<S3FileClientConfig> {

    private S3Client client;

    public S3FileClient(Long id, S3FileClientConfig config) {
        super(id, config);
    }

    @SneakyThrows
    @Override
    protected void doInit() {
        // 补全 domain
        if (StrUtil.isEmpty(config.getDomain())) {
            config.setDomain(buildDomain());
        }
        // 初始化客户端
        client = S3Client.builder().endpointOverride(new URI(config.getEndpoint()))
                .credentialsProvider(buildCredentials())
                .region(Region.of(config.getDomain()))
                .httpClient(ApacheHttpClient.builder().build())  // Explicitly specify HTTP client
                .serviceConfiguration(b -> b.pathStyleAccessEnabled(true))
                .build();
    }

    /**
     * 基于 config 秘钥，构建 S3 客户端的认证信息
     *
     * @return S3 客户端的认证信息
     */
    private StaticCredentialsProvider buildCredentials() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(config.getAccessKey(), config.getAccessSecret()));
    }

    /**
     * 基于 bucket + endpoint 构建访问的 Domain 地址
     *
     * @return Domain 地址
     */
    private String buildDomain() {
        // 如果已经是 http 或者 https，则不进行拼接.主要适配 MinIO
        if (HttpUtil.isHttp(config.getEndpoint()) || HttpUtil.isHttps(config.getEndpoint())) {
            return StrUtil.format("{}/{}", config.getEndpoint(), config.getBucket());
        }
        // 阿里云、腾讯云、华为云都适合。七牛云比较特殊，必须有自定义域名
        return StrUtil.format("https://{}.{}", config.getBucket(), config.getEndpoint());
    }

    @Override
    public String upload(byte[] content, String path, String type) throws Exception {
        // 执行上传
        client.putObject(
                PutObjectRequest.builder()
                        .bucket(config.getBucket())
                        .key(path)
                        .build(),
                RequestBody.fromBytes(content)
        );

        // 拼接返回路径
        return config.getDomain() + "/" + path;
    }

    @Override
    public void delete(String path) throws Exception {
        client.deleteObject(DeleteObjectRequest.builder()
                .bucket(config.getBucket())
                .key(path)
                .build());
    }

    @Override
    public byte[] getContent(String path) throws Exception {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(config.getBucket())
                .key(path)
                .build();

        // 获取对象并读取内容
        ResponseBytes<GetObjectResponse> objectBytes = client.getObjectAsBytes(getObjectRequest);
        return objectBytes.asByteArray();
    }

    @Override
    public FilePresignedUrlRespDTO getPresignedObjectUrl(String path) throws Exception {
        // 生成上传 URL
        // 创建 S3Presigner
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(config.getDomain()))
                .credentialsProvider(buildCredentials())
                .build();

        // 构建 PUT 请求
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10)) // URL 有效期10分钟
                .putObjectRequest(PutObjectRequest.builder()
                        .bucket(config.getBucket())
                        .key(path)
                        .build())
                .build();

        // 生成预签名 URL
        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
        String uploadUrl = presignedRequest.url().toString();

        // 关闭 presigner
        presigner.close();

        return new FilePresignedUrlRespDTO(uploadUrl, config.getDomain() + "/" + path);
    }

}
