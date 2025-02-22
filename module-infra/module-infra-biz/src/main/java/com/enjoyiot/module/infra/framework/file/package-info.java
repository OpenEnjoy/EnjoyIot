
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
/**
 * 文件客户端，支持多种存储器
 *
 * 1. local：本地磁盘
 * 2. ftp：FTP 服务器
 * 3. sftp：SFTP 服务器
 * 4. db：数据库
 * 5. s3：支持 S3 协议的云存储服务，例如说 MinIO、阿里云、华为云、腾讯云、七牛云等等
 *
 * @author EnjoyIot
 */
package com.enjoyiot.module.infra.framework.file;