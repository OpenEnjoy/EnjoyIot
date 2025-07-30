/*
 Navicat Premium Data Transfer

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 23/02/2025 21:27:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ai_algorithm
-- ----------------------------
DROP TABLE IF EXISTS `ai_algorithm`;
CREATE TABLE `ai_algorithm`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '算法编号',
                                 `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '算法名称',
                                 `framework` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型框架',
                                 `format` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型格式',
                                 `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型类别',
                                 `model_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型文件',
                                 `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '算法描述',
                                 `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                 `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI算法表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_algorithm
-- ----------------------------

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel`  (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                            `code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '通道名称',
                            `icon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图标',
                            `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
                            `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                            `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                            `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '通道' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES (1, 'DingTalk', NULL, '钉钉', '', '2025-02-06 21:43:14', '', '2025-02-07 20:53:01', b'0', 1, 0);
INSERT INTO `channel` VALUES (2, 'QyWechat', NULL, '企业微信', '', '2025-02-06 21:43:36', '', '2025-02-07 20:53:02', b'0', 1, 0);
INSERT INTO `channel` VALUES (3, 'Email', NULL, '邮箱', '', '2025-02-06 21:43:55', '', '2025-02-07 20:53:03', b'0', 1, 0);

-- ----------------------------
-- Table structure for device_ota_detail
-- ----------------------------
DROP TABLE IF EXISTS `device_ota_detail`;
CREATE TABLE `device_ota_detail`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                      `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                      `device_id` bigint NULL DEFAULT NULL COMMENT '设备id',
                                      `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备名称',
                                      `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模块',
                                      `ota_info_id` bigint NULL DEFAULT NULL COMMENT 'ota信息id',
                                      `product_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '产品key',
                                      `step` int NULL DEFAULT NULL COMMENT '步骤',
                                      `task_id` bigint NULL DEFAULT NULL COMMENT '任务id',
                                      `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '版本',
                                      `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                      `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                      `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备ota详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of device_ota_detail
-- ----------------------------
INSERT INTO `device_ota_detail` VALUES (1, NULL, 1892227827321597952, '2', NULL, 15, NULL, 1, NULL, '2', '', '2025-02-21 00:53:38', '', '2025-02-21 01:04:13', b'0', 1, 0);

-- ----------------------------
-- Table structure for device_ota_info
-- ----------------------------
DROP TABLE IF EXISTS `device_ota_info`;
CREATE TABLE `device_ota_info`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                                    `fail` int NULL DEFAULT NULL COMMENT '识别数量',
                                    `module` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '模块',
                                    `package_id` bigint NULL DEFAULT NULL COMMENT '升级包id',
                                    `product_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '产品key',
                                    `success` int NULL DEFAULT NULL COMMENT '成功数量',
                                    `total` int NULL DEFAULT NULL COMMENT '总数',
                                    `version` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '版本',
                                    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                    `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '设备ota信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of device_ota_info
-- ----------------------------
INSERT INTO `device_ota_info` VALUES (1, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:44:20', '1', '2025-02-20 23:44:20', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (2, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:44:30', '1', '2025-02-20 23:44:30', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (3, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:45:36', '1', '2025-02-20 23:45:36', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (4, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:45:41', '1', '2025-02-20 23:45:41', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (5, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:45:58', '1', '2025-02-20 23:45:58', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (6, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:46:26', '1', '2025-02-20 23:46:26', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (7, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:46:53', '1', '2025-02-20 23:46:53', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (8, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-21 00:13:26', '1', '2025-02-21 00:13:26', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (9, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-21 00:17:15', '1', '2025-02-21 00:17:15', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (10, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-21 00:18:07', '1', '2025-02-21 00:18:07', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (11, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-21 00:18:22', '1', '2025-02-21 00:18:22', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (12, NULL, 1, 'j', 2, 'R755G5Wb3jst4tD7', 0, 1, 'jj', '1', '2025-02-21 00:19:06', '1', '2025-02-21 00:19:06', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (13, NULL, 2, 'j', 2, 'R755G5Wb3jst4tD7', 0, 2, 'jj', '1', '2025-02-21 00:19:41', '1', '2025-02-21 00:19:41', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (14, NULL, 2, 'j', 2, 'R755G5Wb3jst4tD7', 0, 2, 'jj', '1', '2025-02-21 00:20:06', '1', '2025-02-21 00:20:06', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (15, NULL, 1, 'j', 2, 'R755G5Wb3jst4tD7', 0, 1, 'jj', '1', '2025-02-21 00:21:52', '1', '2025-02-21 00:21:52', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (16, NULL, 0, 'j', 2, 'R755G5Wb3jst4tD7', 1, 1, 'jj', '1', '2025-02-21 00:32:15', '1', '2025-02-21 00:32:15', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (17, NULL, 0, 'j', 2, 'R755G5Wb3jst4tD7', 1, 1, 'jj', '1', '2025-02-21 00:33:17', '1', '2025-02-21 00:33:17', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (18, NULL, 0, 'j', 2, 'R755G5Wb3jst4tD7', 1, 1, 'jj', '1', '2025-02-21 00:33:36', '1', '2025-02-21 00:33:36', b'0', 1, 0);
INSERT INTO `device_ota_info` VALUES (19, NULL, 0, 'j', 2, 'R755G5Wb3jst4tD7', 1, 1, 'jj', '1', '2025-02-21 02:24:49', '1', '2025-02-21 02:24:49', b'0', 1, 0);

-- ----------------------------
-- Table structure for eiot_alert_config
-- ----------------------------
DROP TABLE IF EXISTS `eiot_alert_config`;
CREATE TABLE `eiot_alert_config`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '告警配置id',
                                      `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '告警名称',
                                      `message_template_id` bigint NULL DEFAULT NULL COMMENT '关联消息转发模板ID',
                                      `rule_info_id` bigint NOT NULL COMMENT '规则引擎id',
                                      `level` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '告警等级',
                                      `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                                      `status` tinyint NULL DEFAULT NULL COMMENT '状态(0启动 1禁用)',
                                      `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                      `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                      `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '报警配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_alert_config
-- ----------------------------
INSERT INTO `eiot_alert_config` VALUES (1, '测试配置1', 4, 15, '2', NULL, 0, '1', '2025-02-10 09:13:44', '1', '2025-02-19 21:55:22', b'0', 1, 0);

-- ----------------------------
-- Table structure for eiot_alert_record
-- ----------------------------
DROP TABLE IF EXISTS `eiot_alert_record`;
CREATE TABLE `eiot_alert_record`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                      `alert_time` bigint NOT NULL COMMENT '告警时间',
                                      `details` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '告警详情',
                                      `level` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '告警等级',
                                      `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '告警名称',
                                      `read_flg` bit(1) NULL DEFAULT NULL COMMENT '是否已读',
                                      `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                      `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                      `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '告警记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_alert_record
-- ----------------------------
INSERT INTO `eiot_alert_record` VALUES (1, 1739090292, '测试告警记录', '1', '测试告警', b'1', '1', '2025-02-09 16:38:16', '', '2025-02-16 17:55:37', b'0', 1, 0);
INSERT INTO `eiot_alert_record` VALUES (2, 1739975116106, '您的地块【${title}】<font color=\"warning\">今天气温过高，请做好保湿作业</font>', '2', '测试配置1', b'0', NULL, '2025-02-19 22:25:16', NULL, '2025-02-19 22:25:16', b'0', 1, 0);
INSERT INTO `eiot_alert_record` VALUES (3, 1739975231862, '您的地块【${title}】<font color=\"warning\">今天气温过高，请做好保湿作业</font>', '2', '测试配置1', b'0', NULL, '2025-02-19 22:27:12', NULL, '2025-02-19 22:27:12', b'0', 1, 0);
INSERT INTO `eiot_alert_record` VALUES (4, 1739975282563, '您的地块【${title}】<font color=\"warning\">今天气温过高，请做好保湿作业</font>', '2', '测试配置1', b'0', NULL, '2025-02-19 22:28:03', NULL, '2025-02-19 22:28:03', b'0', 1, 0);
INSERT INTO `eiot_alert_record` VALUES (5, 1739975330836, '您的地块【${title}】<font color=\"warning\">今天气温过高，请做好保湿作业</font>', '2', '测试配置1', b'0', NULL, '2025-02-19 22:28:51', NULL, '2025-02-19 22:28:51', b'0', 1, 0);

-- ----------------------------
-- Table structure for eiot_category
-- ----------------------------
DROP TABLE IF EXISTS `eiot_category`;
CREATE TABLE `eiot_category`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
                                  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父分类id',
                                  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
                                  `sort` int NULL DEFAULT 0 COMMENT '分类排序',
                                  `status` tinyint NOT NULL COMMENT '开启状态',
                                  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片地址',
                                  `is_sys` tinyint NOT NULL DEFAULT 0 COMMENT '是否系统通用（0-否，1-是）',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'eiot产品分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_category
-- ----------------------------
INSERT INTO `eiot_category` VALUES (91, 0, '测试分类1', 0, 0, '1', '2025-02-10 11:12:03', '1', '2025-02-10 14:35:21', b'0', 1, NULL, 1);
INSERT INTO `eiot_category` VALUES (92, 91, '测试子分类1', 0, 0, '1', '2025-02-16 18:04:05', '1', '2025-02-16 18:04:05', b'0', 1, NULL, 0);
INSERT INTO `eiot_category` VALUES (93, 0, '摄像头', 2, 0, '1', '2025-02-16 18:04:26', '1', '2025-02-16 18:04:26', b'0', 1, NULL, 0);
INSERT INTO `eiot_category` VALUES (94, 0, '网关', 3, 0, '1', '2025-02-16 18:09:08', '1', '2025-02-16 18:09:17', b'0', 1, NULL, 0);
INSERT INTO `eiot_category` VALUES (95, 0, '传感器', 3, 0, '1', '2025-02-16 18:11:27', '1', '2025-02-16 18:11:27', b'0', 1, NULL, 0);
INSERT INTO `eiot_category` VALUES (96, 0, '水泵', 5, 0, '1', '2025-02-19 22:54:06', '1', '2025-02-19 22:54:06', b'0', 1, NULL, 0);
INSERT INTO `eiot_category` VALUES (97, 0, '猫砂盆', 7, 0, '1', '2025-02-19 22:55:16', '1', '2025-02-19 22:55:16', b'0', 1, NULL, 0);

-- ----------------------------
-- Table structure for eiot_channel_config
-- ----------------------------
DROP TABLE IF EXISTS `eiot_channel_config`;
CREATE TABLE `eiot_channel_config`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '通道配置id',
                                        `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '配置名称',
                                        `code` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '通道编码',
                                        `param` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '通道配置参数',
                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                        `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                        `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '通道配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_channel_config
-- ----------------------------
INSERT INTO `eiot_channel_config` VALUES (1, '钉钉通道', 'DingTalk', '{\"dingTalkSecret\":\"xxx\",\"dingTalkWebhook\":\"http://xxx.com\"}', '1', '2025-02-10 09:13:20', '1', '2025-02-10 09:13:20', b'0', 1, 0);
INSERT INTO `eiot_channel_config` VALUES (2, '企业微信通道', 'QyWechat', '{\"qyWechatWebhook\":\"http://xxxx.com\"}', '1', '2025-02-16 17:58:25', '1', '2025-02-16 17:58:25', b'0', 1, 0);
INSERT INTO `eiot_channel_config` VALUES (3, '邮箱通道', 'Email', '{\"qyWechatWebhook\":\"http://xxxx.com\",\"from\":\"xxxx@163.com\",\"host\":\"smtp.163.com\",\"userName\":\"xxx@163.com\",\"passWord\":\"xxx1\",\"to\":\"xxxx@163.com\",\"mailSmtpAuth\":true}', '1', '2025-02-16 17:59:50', '1', '2025-02-16 17:59:50', b'0', 1, 0);

-- ----------------------------
-- Table structure for eiot_channel_template
-- ----------------------------
DROP TABLE IF EXISTS `eiot_channel_template`;
CREATE TABLE `eiot_channel_template`  (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '通道模板id',
                                          `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '通道模板名称',
                                          `channel_config_id` bigint NOT NULL COMMENT '通道配置id',
                                          `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '通道模板内容',
                                          `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                          `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '通道模板' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_channel_template
-- ----------------------------
INSERT INTO `eiot_channel_template` VALUES (3, '企业微信模板', 2, '您的地块【${pressure}】<font color=\"warning\">水分过低，请尽快浇水作业</font>\n', '1', '2025-02-09 23:07:46', '1', '2025-02-16 18:01:08', b'0', 1, 0);
INSERT INTO `eiot_channel_template` VALUES (4, '钉钉通道模板', 1, '您的地块【${title}】<font color=\"warning\">今天气温过高，请做好保湿作业</font>', '1', '2025-02-16 18:00:51', '1', '2025-02-16 18:00:51', b'0', 1, 0);

-- ----------------------------
-- Table structure for eiot_component
-- ----------------------------
DROP TABLE IF EXISTS `eiot_component`;
CREATE TABLE `eiot_component`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '组件编号',
                                   `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组件名称',
                                   `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组件类型',
                                   `config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '组件配置(JSON格式)',
                                   `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(0-停用, 1-启用)',
                                   `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                   `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '组件配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_component
-- ----------------------------
INSERT INTO `eiot_component` VALUES (1, '内置官方mqtt协议组件', 'mqtt', '{\"port\":18831}', 1, NULL, NULL, '2025-02-19 01:01:54', '1', '2025-02-20 11:25:26', b'0');
INSERT INTO `eiot_component` VALUES (2, '内置emqx协议组件', 'emqx', '{\n     \"port\": 1883,\n     \"host\": \"127.0.0.1\",\n     \"topics\": \"/sys/#\",\n     \"authPort\": 8104\n}', 0, NULL, NULL, '2025-02-19 01:01:54', '1', '2025-02-28 10:29:51', b'0');
INSERT INTO `eiot_component` VALUES (3, '内置TCP协议组件', 'tcp', '{\n     \"port\": 6666,\n     \"host\": \"127.0.0.1\"\n     \}', 1, null, null, '2025-02-19 01:01:54', '1', '2025-03-23 15:59:27', b'0');
INSERT INTO `eiot_component` VALUES (4, '内置官方http协议组件', 'http', '{\n     \"port\":18834,\n     \"ssl\": false,\n     \"sslKey\": \"\",\n     \"sslCert": \"\"\n     \}', 1, NULL, NULL, '2025-02-19 01:01:54', '1', '2025-02-20 11:25:26', b'0');
INSERT INTO `eiot_component` VALUES (5, '有人云的USR-G770数传终端的modbus-tcp协议组件', 'modbus-custom', '{\n    \"port\":18835,\n    \"timer\": 10\n}', 0, '产品手册：https://www.usr.cn/wiki/puba/p2xSMaXS4#top', NULL, '2025-02-19 01:01:54', '1', '2025-04-02 10:13:36', b'0');

-- ----------------------------
-- Table structure for eiot_dept_product
-- ----------------------------
DROP TABLE IF EXISTS `eiot_dept_product`;
CREATE TABLE `eiot_dept_product`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '客户产品id',
                                      `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '客户产品名称',
                                      `propertys` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '产品属性',
                                      `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                                      `dept_id` bigint NULL DEFAULT NULL COMMENT '机构id',
                                      `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1禁用 2未绑定）',
                                      `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                      `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                      `category_id` bigint NOT NULL COMMENT '分类id',
                                      `product_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '内部产品key',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '机构产品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_dept_product
-- ----------------------------

-- ----------------------------
-- Table structure for eiot_dept_product_category
-- ----------------------------
DROP TABLE IF EXISTS `eiot_dept_product_category`;
CREATE TABLE `eiot_dept_product_category`  (
                                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
                                               `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父分类id',
                                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
                                               `sort` int NULL DEFAULT 0 COMMENT '分类排序',
                                               `status` tinyint NOT NULL COMMENT '开启状态',
                                               `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                               `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                               `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                               `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                               `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片地址',
                                               `is_sys` tinyint NOT NULL DEFAULT 0 COMMENT '是否系统通用（0-否，1-是）',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'eiot产品分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_dept_product_category
-- ----------------------------

-- ----------------------------
-- Table structure for eiot_device_config
-- ----------------------------
DROP TABLE IF EXISTS `eiot_device_config`;
CREATE TABLE `eiot_device_config`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置id',
                                       `config` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '配置内容',
                                       `product_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '产品key',
                                       `dn` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '设备唯一编码',
                                       `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                       `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_device_config
-- ----------------------------

-- ----------------------------
-- Table structure for eiot_device_info
-- ----------------------------
DROP TABLE IF EXISTS `eiot_device_info`;
CREATE TABLE `eiot_device_info`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备id',
                                     `dn` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '设备唯一标识',
                                     `product_key` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '产品key',
                                     `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                     `properties` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '设备属性',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                     `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '别名',
                                     `state` tinyint NULL DEFAULT NULL COMMENT '(0:否, 1:在线, 2-未激活，3-禁用)设备状态',
                                     `offline_time` bigint NULL DEFAULT NULL COMMENT '离线时间',
                                     `online_time` bigint NULL DEFAULT NULL COMMENT '在线时间',
                                     `serial_no` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '设备序列号',
                                     `remark` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                                     `lat` double NULL DEFAULT NULL COMMENT '经纬度',
                                     `lon` double NULL DEFAULT NULL COMMENT '经纬度',
                                     `model` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型',
                                     `parent_id` bigint NULL DEFAULT NULL COMMENT '父id',
                                     `active_time` datetime NULL DEFAULT NULL COMMENT '激活时间',
                                     `secret` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '设备密钥',
                                     `firm_version` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '1.0.0' COMMENT '固件版本',
                                     `addr` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '位置信息',
                                     `transparent` bit(1) NULL DEFAULT NULL COMMENT '是否透传',
                                     `node_type` int NOT NULL COMMENT '设备类型',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1892227827321597953 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '设备主控表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_device_info
-- ----------------------------
INSERT INTO `eiot_device_info` VALUES (10, 'test1111', 'nechCiDrhjzzA2A5', 0, NULL, '1', '2025-02-16 12:36:54', '1', '2025-02-16 21:16:14', b'0', 1, '测试设备1', 0, 1739709001905, 1739711770952, 'A792BB92096F5493D92A3090C4AFDDB31', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '地址1111111', NULL, 0);
INSERT INTO `eiot_device_info` VALUES (11, '133434', 'pfxt7ymAmc5TCdNE', 0, NULL, '1', '2025-02-16 20:31:03', '1', '2025-02-16 20:31:03', b'0', 1, '测试设备2', 2, NULL, NULL, '133434', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (12, '1234', 'nechCiDrhjzzA2A5', 0, NULL, '1', '2025-02-16 20:31:48', '1', '2025-02-16 22:32:17', b'0', 1, '测试产品-设备2', 0, 1739714515571, 1739716335656, 'A322A3B3427D34299B1DBA96686D2182A', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1891112439958269954, '2221212', 'hJEGr8DKPGnWmm5a', 0, NULL, '1', '2025-02-16 21:08:36', '1', '2025-02-16 21:08:36', b'0', 1, '测试设备5', 2, NULL, NULL, 'A575C6CE2867945C99BDD450D113298B0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053956972531712, 'TEST:GW:000007', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:20:44', b'0', 1, '测试设备1', 2, NULL, NULL, 'TEST:GW:000007', NULL, NULL, NULL, 'GW01', NULL, NULL, 'ltsBjVpytoPfRICM', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053957870112768, 'TEST:GW:000005', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:20:44', b'0', 1, '测试设备2', 2, NULL, NULL, 'TEST:GW:000005', NULL, NULL, NULL, 'GW01', NULL, NULL, 'oQgdkVVxQOhrtrmZ', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053958574755840, 'TEST:GW:000006', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053958574755840', 2, NULL, NULL, 'TEST:GW:000006', NULL, NULL, NULL, 'GW01', NULL, NULL, 'tbgpeKdcVxHrjbMw', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053959220678656, 'TEST:GW:000001', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053959220678656', 2, NULL, NULL, 'TEST:GW:000001', NULL, NULL, NULL, 'GW01', NULL, NULL, 'PjCejtTEhzcuGCQQ', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053960084705280, 'TEST:GW:000009', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053960084705280', 2, NULL, NULL, 'TEST:GW:000009', NULL, NULL, NULL, 'GW01', NULL, NULL, 'JBaDJHpogBIoDHsI', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053960931954688, 'TEST:GW:000008', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053960931954688', 2, NULL, NULL, 'TEST:GW:000008', NULL, NULL, NULL, 'GW01', NULL, NULL, 'sgYqiISAkkMfFDPn', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053961762426880, 'TEST:GW:000002', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053961762426880', 2, NULL, NULL, 'TEST:GW:000002', NULL, NULL, NULL, 'GW01', NULL, NULL, 'UwkfMlrMzSpsVOOI', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053962605481984, 'TEST:GW:000004', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053962605481984', 2, NULL, NULL, 'TEST:GW:000004', NULL, NULL, NULL, 'GW01', NULL, NULL, 'fuyAEQuIaRGxUjqf', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053963456925696, 'TEST:GW:000000', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053963456925696', 2, NULL, NULL, 'TEST:GW:000000', NULL, NULL, NULL, 'GW01', NULL, NULL, 'GskQCHIhZKBhCuCl', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053964220289024, 'TEST:GW:000003', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053964220289024', 2, NULL, NULL, 'TEST:GW:000003', NULL, NULL, NULL, 'GW01', NULL, NULL, 'XwpNRVPezulweFhC', '1.0.0', NULL, NULL, 0);
INSERT INTO `eiot_device_info` VALUES (1892053966527156224, 'TEST_SW_000007', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:53', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053966527156224', 2, NULL, NULL, 'TEST_SW_000007', NULL, NULL, NULL, 'S01', 1892053956972531712, NULL, 'gEkXdOtxKkHMEZyO', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053968246820864, 'TEST_SW_000005', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:53', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053968246820864', 2, NULL, NULL, 'TEST_SW_000005', NULL, NULL, NULL, 'S01', 1892053957870112768, NULL, 'QbiNVjllGPVUpiWP', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053969400254464, 'TEST_SW_000006', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:54', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053969400254464', 2, NULL, NULL, 'TEST_SW_000006', NULL, NULL, NULL, 'S01', 1892053958574755840, NULL, 'pZASQxssgpLZnlVu', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053970805346304, 'TEST_SW_000001', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:54', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053970805346304', 2, NULL, NULL, 'TEST_SW_000001', NULL, NULL, NULL, 'S01', 1892053959220678656, NULL, 'rxBQzjBpOTQKBpYc', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053972487262208, 'TEST_SW_000009', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:54', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053972487262208', 2, NULL, NULL, 'TEST_SW_000009', NULL, NULL, NULL, 'S01', 1892053960084705280, NULL, 'JqwLanBlvfhzLAHk', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053973711998976, 'TEST_SW_000008', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:55', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053973711998976', 2, NULL, NULL, 'TEST_SW_000008', NULL, NULL, NULL, 'S01', 1892053960931954688, NULL, 'wpsesUEuemtZKzkW', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053974882209792, 'TEST_SW_000002', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:55', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053974882209792', 2, NULL, NULL, 'TEST_SW_000002', NULL, NULL, NULL, 'S01', 1892053961762426880, NULL, 'NzGEexKedPgdvBLJ', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053976098557952, 'TEST_SW_000004', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:55', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053976098557952', 2, NULL, NULL, 'TEST_SW_000004', NULL, NULL, NULL, 'S01', 1892053962605481984, NULL, 'nmqnXTsUhSScIJwN', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053977256185856, 'TEST_SW_000000', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:55', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053977256185856', 2, NULL, NULL, 'TEST_SW_000000', NULL, NULL, NULL, 'S01', 1892053963456925696, NULL, 'pyQCJzvfPXMLRIEL', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892053979932151808, 'TEST_SW_000003', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:56', NULL, '2025-02-19 15:22:10', b'0', 1, '设备1892053979932151808', 2, NULL, NULL, 'TEST_SW_000003', NULL, NULL, NULL, 'S01', 1892053964220289024, NULL, 'UebFTNIxilNbncDt', '1.0.0', NULL, NULL, 1);
INSERT INTO `eiot_device_info` VALUES (1892227036623990784, 'CC68', 'R755G5Wb3jst4tD7', 0, NULL, '1', '2025-02-19 22:57:36', '1', '2025-02-23 18:24:26', b'0', 1, '我的猫砂盆', 0, 1740306266093, 1740263646879, 'CC68', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 2);
INSERT INTO `eiot_device_info` VALUES (1892227827321597952, 'C18338', 'R755G5Wb3jst4tD7', 0, NULL, '1', '2025-02-19 23:00:45', '1', '2025-02-23 20:00:15', b'0', 1, '猫砂盆', 0, 1740306265974, 1740263646876, 'C18338', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 2);

-- ----------------------------
-- Table structure for eiot_iot_device_group
-- ----------------------------
DROP TABLE IF EXISTS `eiot_iot_device_group`;
CREATE TABLE `eiot_iot_device_group`  (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                          `device_id` bigint NOT NULL COMMENT '设备ID',
                                          `group_id` bigint NOT NULL COMMENT '分组ID',
                                          `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          `tenant_id` bigint NULL DEFAULT 0 COMMENT '租户编号',
                                          `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '设备分组' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_iot_device_group
-- ----------------------------

-- ----------------------------
-- Table structure for eiot_iot_group
-- ----------------------------
DROP TABLE IF EXISTS `eiot_iot_group`;
CREATE TABLE `eiot_iot_group`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组ID',
                                   `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分组名称',
                                   `group_order` tinyint NOT NULL DEFAULT 0 COMMENT '分组排序',
                                   `uid` bigint NOT NULL COMMENT '用户ID',
                                   `user_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
                                   `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                                   `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                   `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                   `typ` tinyint NOT NULL DEFAULT 0 COMMENT '分组类型(0系统, 1用户, 字典)',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `iot_group_index_user_id`(`uid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '设备分组' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_iot_group
-- ----------------------------
INSERT INTO `eiot_iot_group` VALUES (23, 'qqq', 0, 1, '', 'aa', '1', '2025-02-16 13:02:23', '1', '2025-02-16 13:02:23', b'0', 1, 0, 0);

-- ----------------------------
-- Table structure for eiot_modbus_info
-- ----------------------------
DROP TABLE IF EXISTS `eiot_modbus_info`;
CREATE TABLE `eiot_modbus_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品名称',
  `product_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'productKey',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Modbus产品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for eiot_modbus_thing_model
-- ----------------------------
DROP TABLE IF EXISTS `eiot_modbus_thing_model`;
CREATE TABLE `eiot_modbus_thing_model`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `product_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'productKey',
  `model` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '模型内容',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for eiot_product
-- ----------------------------
DROP TABLE IF EXISTS `eiot_product`;
CREATE TABLE `eiot_product`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '产品id',
                                 `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '产品名称',
                                 `category_id` bigint NOT NULL COMMENT '产品分类id',
                                 `product_key` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'productKey',
                                 `mcu_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'mcu code',
                                 `remark1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '功能介绍',
                                 `img_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片url',
                                 `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                                 `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0启用 1禁用）',
                                 `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                 `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                 `node_type` int NOT NULL DEFAULT 0 COMMENT '设备类型(0 网关设备, 1 网关子设备, 2 直连设备, 3 非联网设备 )',
                                 `protocol_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '协议code',
                                 `keep_alive_time` bigint NOT NULL DEFAULT 0 COMMENT '保活时间',
                                 `product_secret` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '产品密钥',
                                 `transparent` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否透传',
                                 `locate_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '定位方式(0无定位, 1自动上报 2手动定位)',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'eiot产品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_product
-- ----------------------------
INSERT INTO `eiot_product` VALUES (7, '测试产品1', 91, 'nechCiDrhjzzA2A5', 'N', NULL, NULL, NULL, 0, '1', '2025-02-10 14:36:14', '1', '2025-02-16 22:12:33', b'0', 1, 0, 'GB28181', 100000, '343976f78a33462a9ff28a607d0f1b96', b'1', 2);
INSERT INTO `eiot_product` VALUES (8, '测试网关1', 94, 'pfxt7ymAmc5TCdNE', 'N', NULL, NULL, NULL, 0, '1', '2025-02-16 18:16:29', '1', '2025-02-16 18:16:29', b'0', 1, 0, 'N', 0, '8ba846aa1031415c944ff66bc8a8d1ac', b'0', 2);
INSERT INTO `eiot_product` VALUES (9, '海康摄像头', 93, 'hJEGr8DKPGnWmm5a', 'N', NULL, NULL, NULL, 0, '1', '2025-02-16 18:17:04', '1', '2025-02-16 18:17:04', b'0', 1, 0, 'GB28181', 0, '085adc41fb5d4b149884dbfece37072d', b'1', 2);
INSERT INTO `eiot_product` VALUES (10, '测试网关2', 94, 'hbtgIA0SuVw9lxjB', 'N', NULL, NULL, NULL, 0, '1', '2025-02-19 11:23:07', '1', '2025-02-19 11:23:25', b'0', 1, 0, 'N', 60, '2793551687324e48a3963902e9750ec2', b'0', 0);
INSERT INTO `eiot_product` VALUES (11, '测试开关1', 91, 'Rf4QSjbm65X45753', 'N', NULL, NULL, NULL, 0, '1', '2025-02-19 11:24:44', '1', '2025-02-19 11:24:44', b'0', 1, 1, 'N', 60, '6c7319ae198b475787b3101e26d85f82', b'0', 0);
INSERT INTO `eiot_product` VALUES (12, '有陪猫砂盆', 97, 'R755G5Wb3jst4tD7', 'N', NULL, NULL, NULL, 0, '1', '2025-02-19 22:56:23', '1', '2025-02-20 22:04:19', b'0', 1, 2, 'N', 100, 'd8e035ad84d9449f9f19a0c0f3861f73', b'0', 0);

-- ----------------------------
-- Table structure for eiot_product_config
-- ----------------------------
DROP TABLE IF EXISTS `eiot_product_config`;
CREATE TABLE `eiot_product_config`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `product_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'productKey',
                                        `config_items` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '配置项目',
                                        `config` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '配置内容',
                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_product_config
-- ----------------------------

-- ----------------------------
-- Table structure for eiot_rule_info
-- ----------------------------
DROP TABLE IF EXISTS `eiot_rule_info`;
CREATE TABLE `eiot_rule_info`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规则id',
                                   `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '规则名称',
                                   `listeners` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '监听器',
                                   `filters` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '过滤器',
                                   `actions` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '动作',
                                   `typ` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '类型(scene数据流转 flow场景联动)',
                                   `state` tinyint NULL DEFAULT NULL COMMENT '状态(0启用 1禁用)',
                                   `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '描述',
                                   `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                   `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '规则引擎' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_rule_info
-- ----------------------------
INSERT INTO `eiot_rule_info` VALUES (15, '测试规则1', '[{\"type\":\"device\",\"config\":\"{\\\"type\\\":\\\"device\\\",\\\"pk\\\":\\\"nechCiDrhjzzA2A5\\\",\\\"dn\\\":\\\"test1111\\\",\\\"conditions\\\":[{\\\"parameters\\\":[{\\\"identifier\\\":\\\"temp\\\",\\\"comparator\\\":\\\">\\\",\\\"value\\\":\\\"1\\\"}],\\\"identifier\\\":\\\"report\\\",\\\"type\\\":\\\"property\\\"}]}\"}]', '[]', '[{\"type\":\"alert\",\"config\":\"{\\\"services\\\":[{\\\"script\\\":\\\"this.translate = function(msg, device) {\\\\n        return {\\\\n                \\\\\\\"deviceName\\\\\\\": device.deviceName,\\\\n                \\\\\\\"reason\\\\\\\": \\\\\\\"电压异常\\\\\\\",\\\\n                \\\\\\\"currVal\\\\\\\": msg.data[\'temp\'],\\\\n                \\\\\\\"alertVal\\\\\\\": 1\\\\n        }\\\\n}\\\"}],\\\"type\\\":\\\"alert\\\",\\\"saved\\\":true}\"}]', 'scene', 1, NULL, '1', '2025-02-10 09:11:21', '1', '2025-02-19 22:41:54', b'0', 1, 0);
INSERT INTO `eiot_rule_info` VALUES (16, 'test1', '[{\"type\":\"device\",\"config\":\"{\\\"type\\\":\\\"device\\\",\\\"pk\\\":\\\"nechCiDrhjzzA2A5\\\",\\\"conditions\\\":[{\\\"parameters\\\":[{\\\"identifier\\\":\\\"switch\\\",\\\"comparator\\\":\\\"==\\\",\\\"value\\\":\\\"1\\\"}],\\\"identifier\\\":\\\"report\\\",\\\"type\\\":\\\"property\\\"}]}\"}]', '[]', '[{\"type\":\"alert\",\"config\":\"{\\\"services\\\":[{\\\"script\\\":\\\"this.translate=function(msg,device){\\\\nreturn {a:1};\\\\n}\\\"}],\\\"type\\\":\\\"alert\\\",\\\"saved\\\":true}\"}]', 'flow', NULL, NULL, '1', '2025-02-16 19:25:26', '1', '2025-02-19 22:40:28', b'1', 1, 0);
INSERT INTO `eiot_rule_info` VALUES (17, '联动测试1', '[{\"type\":\"device\",\"config\":\"{\\\"type\\\":\\\"device\\\",\\\"pk\\\":\\\"nechCiDrhjzzA2A5\\\",\\\"conditions\\\":[{\\\"parameters\\\":[{\\\"identifier\\\":\\\"spd\\\",\\\"comparator\\\":\\\">\\\",\\\"value\\\":\\\"1\\\"}],\\\"identifier\\\":\\\"report\\\",\\\"type\\\":\\\"property\\\"}]}\"}]', '[]', '[{\"type\":\"device\",\"config\":\"{\\\"services\\\":[{\\\"device\\\":\\\"nechCiDrhjzzA2A5/test1111\\\",\\\"identifier\\\":\\\"set\\\",\\\"inputData\\\":[{\\\"identifier\\\":\\\"switch\\\",\\\"value\\\":\\\"1\\\"}]}],\\\"type\\\":\\\"device\\\",\\\"saved\\\":true}\"}]', 'scene', 0, NULL, '1', '2025-02-19 22:41:33', '1', '2025-02-19 22:42:27', b'0', 1, 0);

-- ----------------------------
-- Table structure for eiot_show_model
-- ----------------------------
DROP TABLE IF EXISTS `eiot_show_model`;
CREATE TABLE `eiot_show_model`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '显示id',
                                    `cnf` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '显示内容',
                                    `typ` int NOT NULL COMMENT '类型 0:详情页, 1配置页',
                                    `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '配置名称',
                                    `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                                    `model_code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '模型code',
                                    `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1禁用）',
                                    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                    `product_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'productKey',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '产品显示模型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_show_model
-- ----------------------------

-- ----------------------------
-- Table structure for eiot_thing_model
-- ----------------------------
DROP TABLE IF EXISTS `eiot_thing_model`;
CREATE TABLE `eiot_thing_model`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '显示id',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                     `product_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '产品key',
                                     `model` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '物模型',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '产品物模型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_thing_model
-- ----------------------------
INSERT INTO `eiot_thing_model` VALUES (7, '1', '2025-02-16 13:52:58', '1', '2025-02-16 20:38:38', b'0', 1, 'nechCiDrhjzzA2A5', '{\"properties\":[{\"identifier\":\"switch\",\"dataType\":{\"type\":\"bool\",\"specs\":{\"0\":\"关\",\"1\":\"开\"}},\"name\":\"开关\",\"accessMode\":\"rw\",\"description\":\"\",\"unit\":null,\"iconId\":null,\"icon\":null,\"proData\":null},{\"identifier\":\"temp\",\"dataType\":{\"type\":\"int32\",\"specs\":{}},\"name\":\"温度\",\"accessMode\":\"r\",\"description\":\"\",\"unit\":null,\"iconId\":null,\"icon\":null,\"proData\":null},{\"identifier\":\"spd\",\"dataType\":{\"type\":\"int32\",\"specs\":{\"min\":\"0\",\"max\":\"50000\"}},\"name\":\"转速\",\"accessMode\":\"r\",\"description\":\"\",\"unit\":null,\"iconId\":null,\"icon\":null,\"proData\":null},{\"description\":\"\",\"identifier\":\"pressure\",\"name\":\"压力\",\"dataType\":{\"specs\":{},\"type\":\"int32\"},\"accessMode\":\"r\"}],\"services\":[],\"events\":[]}');
INSERT INTO `eiot_thing_model` VALUES (8, '1', '2025-02-19 22:58:53', '1', '2025-02-19 22:59:48', b'0', 1, 'R755G5Wb3jst4tD7', '{\"properties\":[{\"identifier\":\"model\",\"dataType\":{\"type\":\"enum\",\"specs\":{\"0\":\"自动\",\"1\":\"手动\",\"2\":\"定时\"}},\"name\":\"模式\",\"accessMode\":\"rw\",\"description\":\"\",\"unit\":null,\"iconId\":null,\"icon\":null,\"proData\":null},{\"description\":\"\",\"identifier\":\"remain\",\"name\":\"剩余猫砂\",\"dataType\":{\"specs\":{\"min\":\"0\",\"max\":\"100\"},\"type\":\"int32\"},\"accessMode\":\"r\"}],\"services\":[],\"events\":[]}');

-- ----------------------------
-- Table structure for eiot_virtual_device
-- ----------------------------
DROP TABLE IF EXISTS `eiot_virtual_device`;
CREATE TABLE `eiot_virtual_device`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '虚拟设备名称',
                                        `product_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '产品key',
                                        `type` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '虚拟类型\nthingModel:物模型 protocol: 基于协议',
                                        `script` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '脚本',
                                        `trigger` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '触发执行方式\nnone: 手动 cron:定时执行 random:随机执行',
                                        `trigger_expression` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '触发表达式',
                                        `state` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '运行状态 running: 运行中 stopped:已暂停',
                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                        `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1892398879810187267 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '虚拟设备信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_virtual_device
-- ----------------------------
INSERT INTO `eiot_virtual_device` VALUES (1, '2', 'pfxt7ymAmc5TCdNE', 'thingModel', '\nvar mid=1000;\n\nfunction getMid(){\n  mid++;\n  if(mid>9999){\n	mid=1;\n  }\n  return mid+\"\";\n}\n\nfunction getRequestId(){\n  return \"RID\"+new Date().getTime()+getMid();\n}\n\n\nthis.receive=function(service,device){\n  return [];\n}\n\nthis.report=function(device){\n  return {\n    \"mid\":getRequestId(),\n    \"productKey\":device.productKey,  \n    \"deviceName\":device.deviceName,\n  \"deviceId\": device.deviceId,\n  \"type\":\"property\",\n    \"identifier\":\"report\",\n    \"occurred\":new Date().getTime(),	//时间戳，设备上的事件或数据产生的本地时间\n    \"time\":new Date().getTime(),		//时间戳，消息上报时间\n   // 根据你设备属性修改 \n \"data\":{\n	  \"rssi\":127-parseInt(Math.random()*127),\n	  \"powerstate_1\":Math.random()>0.5?1:0,\n	  \"powerstate_2\":Math.random()>0.5?1:0,\n	  \"powerstate_3\":Math.random()>0.5?1:0\n    }\n  }\n}', 'cron', '0 * * * * ?', 'running', '1', '2025-02-19 14:43:14', '1', '2025-02-19 17:09:32', b'1', 1);
INSERT INTO `eiot_virtual_device` VALUES (2, '1', 'pfxt7ymAmc5TCdNE', 'thingModel', '\nvar mid=1000;\n\nfunction getMid(){\n  mid++;\n  if(mid>9999){\n	mid=1;\n  }\n  return mid+\"\";\n}\n\nfunction getRequestId(){\n  return \"RID\"+new Date().getTime()+getMid();\n}\n\n\nthis.receive=function(service,device){\n  return [];\n}\n\nthis.report=function(device){\n  return {\n    \"mid\":getRequestId(),\n    \"productKey\":device.productKey,  \n    \"deviceName\":device.deviceName,\n  \"deviceId\": device.deviceId,\n  \"type\":\"property\",\n    \"identifier\":\"report\",\n    \"occurred\":new Date().getTime(),	//时间戳，设备上的事件或数据产生的本地时间\n    \"time\":new Date().getTime(),		//时间戳，消息上报时间\n   // 根据你设备属性修改 \n \"data\":{\n	  \"rssi\":127-parseInt(Math.random()*127),\n	  \"powerstate_1\":Math.random()>0.5?1:0,\n	  \"powerstate_2\":Math.random()>0.5?1:0,\n	  \"powerstate_3\":Math.random()>0.5?1:0\n    }\n  }\n}', 'cron', '0 0 0 L * ?', 'running', '1', '2025-02-19 14:44:38', '1', '2025-02-19 17:09:32', b'1', 1);
INSERT INTO `eiot_virtual_device` VALUES (3, '2', 'pfxt7ymAmc5TCdNE', 'thingModel', '\nvar mid=1000;\n\nfunction getMid(){\n  mid++;\n  if(mid>9999){\n	mid=1;\n  }\n  return mid+\"\";\n}\n\nfunction getRequestId(){\n  return \"RID\"+new Date().getTime()+getMid();\n}\n\n\nthis.receive=function(service,device){\n  return [];\n}\n\nthis.report=function(device){\n  return {\n    \"mid\":getRequestId(),\n    \"productKey\":device.productKey,  \n    \"deviceName\":device.deviceName,\n  \"deviceId\": device.deviceId,\n  \"type\":\"property\",\n    \"identifier\":\"report\",\n    \"occurred\":new Date().getTime(),	//时间戳，设备上的事件或数据产生的本地时间\n    \"time\":new Date().getTime(),		//时间戳，消息上报时间\n   // 根据你设备属性修改 \n \"data\":{\n	  \"rssi\":127-parseInt(Math.random()*127),\n	  \"powerstate_1\":Math.random()>0.5?1:0,\n	  \"powerstate_2\":Math.random()>0.5?1:0,\n	  \"powerstate_3\":Math.random()>0.5?1:0\n    }\n  }\n}', 'cron', NULL, 'stopped', '1', '2025-02-19 17:12:48', '1', '2025-02-19 17:12:51', b'1', 1);
INSERT INTO `eiot_virtual_device` VALUES (4, '虚拟设备', 'nechCiDrhjzzA2A5', 'thingModel', '\nvar mid=1000;\n\nfunction getMid(){\n  mid++;\n  if(mid>9999){\n	mid=1;\n  }\n  return mid+\"\";\n}\n\nfunction getRequestId(){\n  return \"RID\"+new Date().getTime()+getMid();\n}\n\n\nthis.receive=function(service,device){\n  return [];\n}\n\nthis.report=function(device){\n  return {\n    \"mid\":getRequestId(),\n    \"productKey\":device.productKey,  \n    \"deviceName\":device.deviceName,\n  \"deviceId\": device.id,\n  \"type\":\"property\",\n    \"identifier\":\"report\",\n    \"occurred\":new Date().getTime(),	//时间戳，设备上的事件或数据产生的本地时间\n    \"time\":new Date().getTime(),		//时间戳，消息上报时间\n   // 根据你设备属性修改 \n \"data\":{\n	  \"rssi\":127-parseInt(Math.random()*127),\n	  \"powerstate_1\":Math.random()>0.5?1:0,\n	  \"powerstate_2\":Math.random()>0.5?1:0,\n	  \"powerstate_3\":Math.random()>0.5?1:0\n    }\n  }\n}', 'cron', NULL, 'running', '1', '2025-02-19 19:10:30', '1', '2025-02-20 11:00:59', b'1', 1);
INSERT INTO `eiot_virtual_device` VALUES (1892396467372355586, '2', 'pfxt7ymAmc5TCdNE', 'thingModel', '\nvar mid=1000;\n\nfunction getMid(){\n  mid++;\n  if(mid>9999){\n	mid=1;\n  }\n  return mid+\"\";\n}\n\nfunction getRequestId(){\n  return \"RID\"+new Date().getTime()+getMid();\n}\n\n\nthis.receive=function(service,device){\n  return [];\n}\n\nthis.report=function(device){\n  return {\n    \"mid\":getRequestId(),\n    \"productKey\":device.productKey,  \n    \"dn\":device.name,\n  \"deviceId\": device.id,\n  \"type\":\"property\",\n    \"identifier\":\"report\",\n    \"occurred\":new Date().getTime(),	//时间戳，设备上的事件或数据产生的本地时间\n    \"time\":new Date().getTime(),		//时间戳，消息上报时间\n   // 根据你设备属性修改 \n \"data\":{\n	  \"rssi\":127-parseInt(Math.random()*127),\n	  \"powerstate_1\":Math.random()>0.5?1:0,\n	  \"powerstate_2\":Math.random()>0.5?1:0,\n	  \"powerstate_3\":Math.random()>0.5?1:0\n    }\n  }\n}', 'cron', '0 * * * * ?', 'stopped', '1', '2025-02-20 10:10:52', '1', '2025-02-23 21:10:53', b'0', 1);
INSERT INTO `eiot_virtual_device` VALUES (1892398879810187266, 'ces', 'pfxt7ymAmc5TCdNE', 'thingModel', '\nvar mid=1000;\n\nfunction getMid(){\n  mid++;\n  if(mid>9999){\n	mid=1;\n  }\n  return mid+\"\";\n}\n\nfunction getRequestId(){\n  return \"RID\"+new Date().getTime()+getMid();\n}\n\n\nthis.receive=function(service,device){\n  return [];\n}\n\nthis.report=function(device){\n  return {\n    \"mid\":getRequestId(),\n    \"productKey\":device.productKey,  \n    \"deviceName\":device.deviceName,\n  \"deviceId\": device.deviceId,\n  \"type\":\"property\",\n    \"identifier\":\"report\",\n    \"occurred\":new Date().getTime(),	//时间戳，设备上的事件或数据产生的本地时间\n    \"time\":new Date().getTime(),		//时间戳，消息上报时间\n   // 根据你设备属性修改 \n \"data\":{\n	  \"rssi\":127-parseInt(Math.random()*127),\n	  \"powerstate_1\":Math.random()>0.5?1:0,\n	  \"powerstate_2\":Math.random()>0.5?1:0,\n	  \"powerstate_3\":Math.random()>0.5?1:0\n    }\n  }\n}', 'cron', '0 * * * * ?', 'stopped', '1', '2025-02-20 10:20:27', '1', '2025-02-20 10:20:27', b'0', 1);

-- ----------------------------
-- Table structure for eiot_virtual_device_mapping
-- ----------------------------
DROP TABLE IF EXISTS `eiot_virtual_device_mapping`;
CREATE TABLE `eiot_virtual_device_mapping`  (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                                `virtual_device_id` bigint NULL DEFAULT NULL COMMENT '虚拟设备id',
                                                `device_id` bigint NULL DEFAULT NULL COMMENT '设备id',
                                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
                                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
                                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                                `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1892610417427156994 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '虚拟设备信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of eiot_virtual_device_mapping
-- ----------------------------
INSERT INTO `eiot_virtual_device_mapping` VALUES (1, 2, 1892053979932151808, '1', '2025-02-19 16:10:54', '1', '2025-02-19 16:19:15', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (2, 1, 1892053979932151808, '1', '2025-02-19 16:18:26', '1', '2025-02-19 17:09:32', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (3, 2, 1892053979932151808, '1', '2025-02-19 16:19:15', '1', '2025-02-19 16:32:14', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (4, 2, 1892053979932151808, '1', '2025-02-19 16:32:14', '1', '2025-02-19 16:32:48', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (5, 2, 1892053977256185856, '1', '2025-02-19 16:32:14', '1', '2025-02-19 16:32:48', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (6, 2, 1892053979932151808, '1', '2025-02-19 16:32:49', '1', '2025-02-19 17:09:32', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (7, 2, 1892053977256185856, '1', '2025-02-19 16:32:49', '1', '2025-02-19 17:09:32', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (8, 2, 1892053976098557952, '1', '2025-02-19 16:32:49', '1', '2025-02-19 17:09:32', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (9, 4, 1892053979932151808, '1', '2025-02-19 19:10:40', '1', '2025-02-19 19:10:40', b'0', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (1892409417151741954, 1892396467372355586, 1892227827321597952, '1', '2025-02-20 11:02:19', '1', '2025-02-21 00:21:01', b'1', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (1892610417414574081, 1892396467372355586, 1892227827321597952, '1', '2025-02-21 00:21:01', '1', '2025-02-21 00:21:01', b'0', 1);
INSERT INTO `eiot_virtual_device_mapping` VALUES (1892610417427156993, 1892396467372355586, 1892227036623990784, '1', '2025-02-21 00:21:01', '1', '2025-02-21 00:21:01', b'0', 1);

-- ----------------------------
-- Table structure for infra_api_access_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_api_access_log`;
CREATE TABLE `infra_api_access_log`  (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
                                         `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
                                         `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
                                         `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
                                         `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
                                         `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求方法名',
                                         `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求地址',
                                         `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '请求参数',
                                         `response_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '响应结果',
                                         `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
                                         `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
                                         `operate_module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作模块',
                                         `operate_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作名',
                                         `operate_type` tinyint NULL DEFAULT 0 COMMENT '操作分类',
                                         `begin_time` datetime NOT NULL COMMENT '开始请求时间',
                                         `end_time` datetime NOT NULL COMMENT '结束请求时间',
                                         `duration` int NOT NULL COMMENT '执行时长',
                                         `result_code` int NOT NULL DEFAULT 0 COMMENT '结果码',
                                         `result_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果提示',
                                         `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                         `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                         PRIMARY KEY (`id`) USING BTREE,
                                         INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35942 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'API 访问日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_api_access_log
-- ----------------------------

-- ----------------------------
-- Table structure for infra_api_error_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_api_error_log`;
CREATE TABLE `infra_api_error_log`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                        `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '链路追踪编号',
                                        `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
                                        `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
                                        `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
                                        `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求方法名',
                                        `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求地址',
                                        `request_params` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
                                        `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
                                        `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
                                        `exception_time` datetime NOT NULL COMMENT '异常发生时间',
                                        `exception_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常名',
                                        `exception_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常导致的消息',
                                        `exception_root_cause_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常导致的根消息',
                                        `exception_stack_trace` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常的栈轨迹',
                                        `exception_class_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的类全名',
                                        `exception_file_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的类文件',
                                        `exception_method_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的方法名',
                                        `exception_line_number` int NOT NULL COMMENT '异常发生的方法所在行',
                                        `process_status` tinyint NOT NULL COMMENT '处理状态',
                                        `process_time` datetime NULL DEFAULT NULL COMMENT '处理时间',
                                        `process_user_id` int NULL DEFAULT 0 COMMENT '处理用户编号',
                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                        `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21307 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统异常日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_api_error_log
-- ----------------------------
INSERT INTO `infra_api_error_log` VALUES (21292, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:35:05', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:35:05', NULL, '2025-02-23 06:35:05', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21293, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:35:15', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:35:15', NULL, '2025-02-23 06:35:15', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21294, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:35:25', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:35:25', NULL, '2025-02-23 06:35:25', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21295, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:35:34', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:35:34', NULL, '2025-02-23 06:35:34', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21296, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:36:40', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:36:40', NULL, '2025-02-23 06:36:40', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21297, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:38:27', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:38:27', NULL, '2025-02-23 06:38:27', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21298, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:39:13', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:39:13', NULL, '2025-02-23 06:39:13', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21299, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:46:13', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:46:13', NULL, '2025-02-23 06:46:13', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21300, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:49:58', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:49:58', NULL, '2025-02-23 06:49:58', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21301, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:51:44', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:51:44', NULL, '2025-02-23 06:51:44', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21302, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 07:59:15', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 07:59:15', NULL, '2025-02-23 07:59:15', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21303, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 07:59:23', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 07:59:23', NULL, '2025-02-23 07:59:23', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21304, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 08:00:07', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 08:00:07', NULL, '2025-02-23 08:00:07', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21305, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 08:03:28', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 08:03:28', NULL, '2025-02-23 08:03:28', b'0', 0);
INSERT INTO `infra_api_error_log` VALUES (21306, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{\"query\":{},\"body\":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 08:04:28', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 08:04:28', NULL, '2025-02-23 08:04:28', b'0', 0);

-- ----------------------------
-- Table structure for infra_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_column`;
CREATE TABLE `infra_codegen_column`  (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                         `table_id` bigint NOT NULL COMMENT '表编号',
                                         `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段名',
                                         `data_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段类型',
                                         `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段描述',
                                         `nullable` bit(1) NOT NULL COMMENT '是否允许为空',
                                         `primary_key` bit(1) NOT NULL COMMENT '是否主键',
                                         `ordinal_position` int NOT NULL COMMENT '排序',
                                         `java_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Java 属性类型',
                                         `java_field` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Java 属性名',
                                         `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
                                         `example` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据示例',
                                         `create_operation` bit(1) NOT NULL COMMENT '是否为 Create 创建操作的字段',
                                         `update_operation` bit(1) NOT NULL COMMENT '是否为 Update 更新操作的字段',
                                         `list_operation` bit(1) NOT NULL COMMENT '是否为 List 查询操作的字段',
                                         `list_operation_condition` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '=' COMMENT 'List 查询操作的条件类型',
                                         `list_operation_result` bit(1) NOT NULL COMMENT '是否为 List 查询操作的返回字段',
                                         `html_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '显示类型',
                                         `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2483 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成表字段定义' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_codegen_column
-- ----------------------------

-- ----------------------------
-- Table structure for infra_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_table`;
CREATE TABLE `infra_codegen_table`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                        `data_source_config_id` bigint NOT NULL COMMENT '数据源配置的编号',
                                        `scene` tinyint NOT NULL DEFAULT 1 COMMENT '生成场景',
                                        `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名称',
                                        `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表描述',
                                        `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                        `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块名',
                                        `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务名',
                                        `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类名称',
                                        `class_comment` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类描述',
                                        `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '作者',
                                        `template_type` tinyint NOT NULL DEFAULT 1 COMMENT '模板类型',
                                        `front_type` tinyint NOT NULL COMMENT '前端类型',
                                        `parent_menu_id` bigint NULL DEFAULT NULL COMMENT '父菜单编号',
                                        `master_table_id` bigint NULL DEFAULT NULL COMMENT '主表的编号',
                                        `sub_join_column_id` bigint NULL DEFAULT NULL COMMENT '子表关联主表的字段编号',
                                        `sub_join_many` bit(1) NULL DEFAULT NULL COMMENT '主表与子表是否一对多',
                                        `tree_parent_column_id` bigint NULL DEFAULT NULL COMMENT '树表的父字段编号',
                                        `tree_name_column_id` bigint NULL DEFAULT NULL COMMENT '树表的名字字段编号',
                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 187 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成表定义' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_codegen_table
-- ----------------------------

-- ----------------------------
-- Table structure for infra_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_config`;
CREATE TABLE `infra_config`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '参数主键',
                                 `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数分组',
                                 `type` tinyint NOT NULL COMMENT '参数类型',
                                 `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
                                 `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键名',
                                 `value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键值',
                                 `visible` bit(1) NOT NULL COMMENT '是否可见',
                                 `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                 `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_config
-- ----------------------------
INSERT INTO `infra_config` VALUES (2, 'biz', 1, '用户管理-账号初始密码', 'system.user.init-password', '123456', b'0', '初始化密码 123456', 'admin', '2021-01-05 17:03:48', '1', '2024-07-20 17:22:47', b'0');
INSERT INTO `infra_config` VALUES (7, 'url', 2, 'MySQL 监控的地址', 'url.druid', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:33:38', b'0');
INSERT INTO `infra_config` VALUES (8, 'url', 2, 'SkyWalking 监控的地址', 'url.skywalking', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:57:03', b'0');
INSERT INTO `infra_config` VALUES (9, 'url', 2, 'Spring Boot Admin 监控的地址', 'url.spring-boot-admin', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:52:07', b'0');
INSERT INTO `infra_config` VALUES (10, 'url', 2, 'Swagger 接口文档的地址', 'url.swagger', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:59:00', b'0');
INSERT INTO `infra_config` VALUES (11, 'ui', 2, '腾讯地图 key', 'tencent.lbs.key', 'TVDBZ-TDILD-4ON4B-PFDZA-RNLKH-VVF6E', b'1', '腾讯地图 key', '1', '2023-06-03 19:16:27', '1', '2023-06-03 19:16:27', b'0');
INSERT INTO `infra_config` VALUES (12, 'test2', 2, 'test3', 'test4', 'test5', b'1', 'test6', '1', '2023-12-03 09:55:16', '1', '2023-12-03 09:55:27', b'0');

-- ----------------------------
-- Table structure for infra_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_data_source_config`;
CREATE TABLE `infra_data_source_config`  (
                                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键编号',
                                             `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
                                             `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
                                             `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                                             `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
                                             `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                             `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                             `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据源配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_data_source_config
-- ----------------------------

-- ----------------------------
-- Table structure for infra_file
-- ----------------------------
DROP TABLE IF EXISTS `infra_file`;
CREATE TABLE `infra_file`  (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件编号',
                               `config_id` bigint NULL DEFAULT NULL COMMENT '配置编号',
                               `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名',
                               `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
                               `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件 URL',
                               `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型',
                               `size` int NOT NULL COMMENT '文件大小',
                               `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1593 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_file
-- ----------------------------

-- ----------------------------
-- Table structure for infra_file_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_config`;
CREATE TABLE `infra_file_config`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                      `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名',
                                      `storage` tinyint NOT NULL COMMENT '存储器',
                                      `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                      `master` bit(1) NOT NULL COMMENT '是否为主配置',
                                      `config` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储配置',
                                      `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_file_config
-- ----------------------------
INSERT INTO `infra_file_config` VALUES (4, '数据库（示例）', 1, '我是数据库', b'0', '{\"@class\":\"com.enjoyiot.module.infra.framework.file.core.client.db.DBFileClientConfig\",\"domain\":\"http://127.0.0.1:48080\"}', '1', '2022-03-15 23:56:24', '1', '2025-02-20 21:29:45', b'0');
INSERT INTO `infra_file_config` VALUES (22, '七牛存储器（示例）', 20, '请换成你自己的密钥！！！', b'0', '{\"@class\":\"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"s3.cn-south-1.qiniucs.com\",\"domain\":\"http://test.enjoy-iot.cn\",\"bucket\":\"ENJOY\",\"accessKey\":\"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS\",\"accessSecret\":\"wd0tbVBEiotp0S-ihA8Qg2hPLncoP83wyrIq24OZuY\"}', '1', '2024-01-13 22:11:12', '1', '2025-02-20 21:29:45', b'0');
INSERT INTO `infra_file_config` VALUES (24, '腾讯云存储（示例）', 20, '请换成你的密钥！！！', b'0', '{\"@class\":\"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"https://cos.ap-shanghai.myqcloud.com\",\"domain\":\"http://tengxun-oss.enjoy-iot.cn\",\"bucket\":\"aoteman-1255880240\",\"accessKey\":\"AKIDAF6WSh1uiIjwqtrOsGSN3WryqTM6cTMt\",\"accessSecret\":\"X\"}', '1', '2024-11-09 16:03:22', '1', '2025-02-20 21:29:45', b'0');
INSERT INTO `infra_file_config` VALUES (25, '阿里云存储（示例）', 20, '', b'0', '{\"@class\":\"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"oss-cn-beijing.aliyuncs.com\",\"domain\":\"http://ali-oss.enjoy-iot.cn\",\"bucket\":\"ENJOY\",\"accessKey\":\"LTAI5tEQLgnDyjh3WpNcdMKA\",\"accessSecret\":\"X\"}', '1', '2024-11-09 16:47:08', '1', '2025-02-20 21:29:45', b'0');
INSERT INTO `infra_file_config` VALUES (26, '火山云存储（示例）', 20, '', b'0', '{\"@class\":\"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"tos-s3-cn-beijing.volces.com\",\"domain\":null,\"bucket\":\"ENJOY\",\"accessKey\":\"AKLTZjc3Zjc4MzZmMjU3NDk0ZTgxYmIyMmFkNTIwMDI1ZGE\",\"accessSecret\":\"X==\"}', '1', '2024-11-09 16:56:42', '1', '2025-02-20 21:29:45', b'0');
INSERT INTO `infra_file_config` VALUES (27, '华为云存储（示例）', 20, '', b'0', '{\"@class\":\"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"obs.cn-east-3.myhuaweicloud.com\",\"domain\":\"\",\"bucket\":\"enjoy-iot\",\"accessKey\":\"PVDONDEIOTW88LF8DC4U\",\"accessSecret\":\"X\"}', '1', '2024-11-09 17:18:41', '1', '2025-02-20 21:29:45', b'0');
INSERT INTO `infra_file_config` VALUES (28, 'MinIO 存储（示例）', 20, '', b'0', '{\"@class\":\"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"http://eiot-server-minio:9000\",\"domain\":\"main\",\"bucket\":\"eiot\",\"accessKey\":\"minio\",\"accessSecret\":\"xxxx\"}', '1', '2024-11-09 17:43:10', '1', '2025-02-20 21:29:45', b'0');
INSERT INTO `infra_file_config` VALUES (29, '本地存储', 10, '', b'1', '{\"@class\":\"com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClientConfig\",\"basePath\":\"./\",\"domain\":\"http://127.0.0.1:48080\"}', '1', '2025-02-20 21:29:40', '1', '2025-02-20 23:45:05', b'0');

-- ----------------------------
-- Table structure for infra_file_content
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_content`;
CREATE TABLE `infra_file_content`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                       `config_id` bigint NOT NULL COMMENT '配置编号',
                                       `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
                                       `content` mediumblob NOT NULL COMMENT '文件内容',
                                       `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 283 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_file_content
-- ----------------------------

-- ----------------------------
-- Table structure for infra_job
-- ----------------------------
DROP TABLE IF EXISTS `infra_job`;
CREATE TABLE `infra_job`  (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务编号',
                              `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
                              `status` tinyint NOT NULL COMMENT '任务状态',
                              `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
                              `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理器的参数',
                              `cron_expression` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'CRON 表达式',
                              `retry_count` int NOT NULL DEFAULT 0 COMMENT '重试次数',
                              `retry_interval` int NOT NULL DEFAULT 0 COMMENT '重试间隔',
                              `monitor_timeout` int NOT NULL DEFAULT 0 COMMENT '监控超时时间',
                              `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '定时任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_job
-- ----------------------------
INSERT INTO `infra_job` VALUES (5, '支付通知 Job', 2, 'payNotifyJob', NULL, '* * * * * ?', 0, 0, 0, '1', '2021-10-27 08:34:42', '1', '2024-09-12 13:32:48', b'0');
INSERT INTO `infra_job` VALUES (17, '支付订单同步 Job', 2, 'payOrderSyncJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-22 14:36:26', '1', '2023-07-22 15:39:08', b'0');
INSERT INTO `infra_job` VALUES (18, '支付订单过期 Job', 2, 'payOrderExpireJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-22 15:36:23', '1', '2023-07-22 15:39:54', b'0');
INSERT INTO `infra_job` VALUES (19, '退款订单的同步 Job', 2, 'payRefundSyncJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-23 21:03:44', '1', '2023-07-23 21:09:00', b'0');
INSERT INTO `infra_job` VALUES (21, '交易订单的自动过期 Job', 2, 'tradeOrderAutoCancelJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-25 23:43:26', '1', '2023-09-26 19:23:30', b'0');
INSERT INTO `infra_job` VALUES (22, '交易订单的自动收货 Job', 2, 'tradeOrderAutoReceiveJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-26 19:23:53', '1', '2023-09-26 23:38:08', b'0');
INSERT INTO `infra_job` VALUES (23, '交易订单的自动评论 Job', 2, 'tradeOrderAutoCommentJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-26 23:38:29', '1', '2023-09-27 11:03:10', b'0');
INSERT INTO `infra_job` VALUES (24, '佣金解冻 Job', 2, 'brokerageRecordUnfreezeJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-28 22:01:46', '1', '2023-09-28 22:01:56', b'0');
INSERT INTO `infra_job` VALUES (25, '访问日志清理 Job', 2, 'accessLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 10:59:41', '1', '2023-10-03 11:01:10', b'0');
INSERT INTO `infra_job` VALUES (26, '错误日志清理 Job', 2, 'errorLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 11:00:43', '1', '2023-10-03 11:01:12', b'0');
INSERT INTO `infra_job` VALUES (27, '任务日志清理 Job', 2, 'jobLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 11:01:33', '1', '2024-09-12 13:40:34', b'0');
INSERT INTO `infra_job` VALUES (33, 'demoJob', 2, 'demoJob', '', '0 * * * * ?', 1, 1, 0, '1', '2024-10-27 19:38:46', '1', '2024-10-27 19:40:23', b'0');

-- ----------------------------
-- Table structure for infra_job_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_job_log`;
CREATE TABLE `infra_job_log`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志编号',
                                  `job_id` bigint NOT NULL COMMENT '任务编号',
                                  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
                                  `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理器的参数',
                                  `execute_index` tinyint NOT NULL DEFAULT 1 COMMENT '第几次执行',
                                  `begin_time` datetime NOT NULL COMMENT '开始执行时间',
                                  `end_time` datetime NULL DEFAULT NULL COMMENT '结束执行时间',
                                  `duration` int NULL DEFAULT NULL COMMENT '执行时长',
                                  `status` tinyint NOT NULL COMMENT '任务状态',
                                  `result` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果数据',
                                  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 638 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '定时任务日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for notify_message
-- ----------------------------
DROP TABLE IF EXISTS `notify_message`;
CREATE TABLE `notify_message`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `content` varchar(2048) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '内容',
                                   `status` tinyint NULL DEFAULT NULL COMMENT '状态',
                                   `messageType` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '消息类型',
                                   `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                   `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'iot通知消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notify_message
-- ----------------------------

-- ----------------------------
-- Table structure for ota_package
-- ----------------------------
DROP TABLE IF EXISTS `ota_package`;
CREATE TABLE `ota_package`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `product_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '产品key',
                                `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名称',
                                `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '升级包地址',
                                `version` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '版本',
                                `size` bigint NOT NULL COMMENT '升级包大小',
                                `sign_method` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '签名方式',
                                `sign` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '签名内容',
                                `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                                `ext_data` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '额外内容',
                                `is_diff` bit(1) NULL DEFAULT NULL COMMENT '是否差分包',
                                `md5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'md5',
                                `module` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '模块',
                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'ota包' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ota_package
-- ----------------------------
INSERT INTO `ota_package` VALUES (1, 'pfxt7ymAmc5TCdNE', 'j', 'http://127.0.0.1/admin-api/infra/file/29/get/eec070c604d18d7370a28de1de304af880c0e2ea52615dee4259ce4543c95def.jpg', '2', 253, 'md5', '2', NULL, NULL, b'0', NULL, '22', '1', '2025-02-20 23:44:14', '1', '2025-02-20 23:44:14', b'0', 1);
INSERT INTO `ota_package` VALUES (2, 'R755G5Wb3jst4tD7', 'jj', 'http://127.0.0.1:48080/admin-api/infra/file/29/get/551f234aa0f2d9c7aa4ab0fd75f200be5935e220879d05fb47ed62ea3b3523b3.jpg', 'jj', 252, 'md5', 'j', NULL, NULL, b'0', NULL, 'j', '1', '2025-02-21 00:18:59', '1', '2025-02-21 00:18:59', b'0', 1);

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
                                `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
                                `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父部门id',
                                `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
                                `leader_user_id` bigint NULL DEFAULT NULL COMMENT '负责人',
                                `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
                                `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
                                `status` tinyint NOT NULL COMMENT '部门状态（0正常 1停用）',
                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_dept
-- ----------------------------
INSERT INTO `system_dept` VALUES (100, '平台', 0, 0, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-11-14 23:30:36', b'0', 1);
INSERT INTO `system_dept` VALUES (101, '深圳总公司', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-12-02 09:53:35', b'0', 1);
INSERT INTO `system_dept` VALUES (102, '长沙分公司', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:40', b'0', 1);
INSERT INTO `system_dept` VALUES (103, '研发部门', 101, 1, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2024-10-02 10:22:03', b'0', 1);
INSERT INTO `system_dept` VALUES (104, '市场部门', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:38', b'0', 1);
INSERT INTO `system_dept` VALUES (105, '测试部门', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-05-16 20:25:15', b'0', 1);
INSERT INTO `system_dept` VALUES (106, '财务部门', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-15 21:32:22', b'0', 1);
INSERT INTO `system_dept` VALUES (107, '运维部门', 101, 5, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-12-02 09:28:22', b'0', 1);
INSERT INTO `system_dept` VALUES (108, '市场部门', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-02-16 08:35:45', b'0', 1);
INSERT INTO `system_dept` VALUES (109, '财务部门', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:29', b'0', 1);
INSERT INTO `system_dept` VALUES (110, '新部门', 0, 1, NULL, NULL, NULL, 0, '110', '2022-02-23 20:46:30', '110', '2022-02-23 20:46:30', b'0', 121);
INSERT INTO `system_dept` VALUES (111, '顶级部门', 0, 1, NULL, NULL, NULL, 0, '113', '2022-03-07 21:44:50', '113', '2022-03-07 21:44:50', b'0', 122);
INSERT INTO `system_dept` VALUES (112, '产品部门', 101, 100, 1, NULL, NULL, 1, '1', '2023-12-02 09:45:13', '1', '2023-12-02 09:45:31', b'0', 1);
INSERT INTO `system_dept` VALUES (113, '支持部门', 102, 3, 104, NULL, NULL, 1, '1', '2023-12-02 09:47:38', '1', '2023-12-02 09:47:38', b'0', 1);

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
                                     `sort` int NOT NULL DEFAULT 0 COMMENT '字典排序',
                                     `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
                                     `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
                                     `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
                                     `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
                                     `color_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '颜色类型',
                                     `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'css 样式',
                                     `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1710 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
INSERT INTO `system_dict_data` VALUES (1, 1, '男', '1', 'system_user_sex', 0, 'default', 'A', '性别男', 'admin', '2021-01-05 17:03:48', '1', '2022-03-29 00:14:39', b'0');
INSERT INTO `system_dict_data` VALUES (2, 2, '女', '2', 'system_user_sex', 0, 'success', '', '性别女', 'admin', '2021-01-05 17:03:48', '1', '2023-11-15 23:30:37', b'0');
INSERT INTO `system_dict_data` VALUES (8, 1, '正常', '1', 'infra_job_status', 0, 'success', '', '正常状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:33:38', b'0');
INSERT INTO `system_dict_data` VALUES (9, 2, '暂停', '2', 'infra_job_status', 0, 'danger', '', '停用状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:33:45', b'0');
INSERT INTO `system_dict_data` VALUES (12, 1, '系统内置', '1', 'infra_config_type', 0, 'danger', '', '参数类型 - 系统内置', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:06:02', b'0');
INSERT INTO `system_dict_data` VALUES (13, 2, '自定义', '2', 'infra_config_type', 0, 'primary', '', '参数类型 - 自定义', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:06:07', b'0');
INSERT INTO `system_dict_data` VALUES (14, 1, '通知', '1', 'system_notice_type', 0, 'success', '', '通知', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:05:57', b'0');
INSERT INTO `system_dict_data` VALUES (15, 2, '公告', '2', 'system_notice_type', 0, 'info', '', '公告', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:06:01', b'0');
INSERT INTO `system_dict_data` VALUES (16, 0, '其它', '0', 'infra_operate_type', 0, 'default', '', '其它操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:19', b'0');
INSERT INTO `system_dict_data` VALUES (17, 1, '查询', '1', 'infra_operate_type', 0, 'info', '', '查询操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:20', b'0');
INSERT INTO `system_dict_data` VALUES (18, 2, '新增', '2', 'infra_operate_type', 0, 'primary', '', '新增操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:21', b'0');
INSERT INTO `system_dict_data` VALUES (19, 3, '修改', '3', 'infra_operate_type', 0, 'warning', '', '修改操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:22', b'0');
INSERT INTO `system_dict_data` VALUES (20, 4, '删除', '4', 'infra_operate_type', 0, 'danger', '', '删除操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:23', b'0');
INSERT INTO `system_dict_data` VALUES (22, 5, '导出', '5', 'infra_operate_type', 0, 'default', '', '导出操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:24', b'0');
INSERT INTO `system_dict_data` VALUES (23, 6, '导入', '6', 'infra_operate_type', 0, 'default', '', '导入操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:25', b'0');
INSERT INTO `system_dict_data` VALUES (27, 1, '开启', '0', 'common_status', 0, 'primary', '', '开启状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 08:00:39', b'0');
INSERT INTO `system_dict_data` VALUES (28, 2, '关闭', '1', 'common_status', 0, 'info', '', '关闭状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 08:00:44', b'0');
INSERT INTO `system_dict_data` VALUES (29, 1, '目录', '1', 'system_menu_type', 0, '', '', '目录', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:45', b'0');
INSERT INTO `system_dict_data` VALUES (30, 2, '菜单', '2', 'system_menu_type', 0, '', '', '菜单', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:41', b'0');
INSERT INTO `system_dict_data` VALUES (31, 3, '按钮', '3', 'system_menu_type', 0, '', '', '按钮', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:39', b'0');
INSERT INTO `system_dict_data` VALUES (32, 1, '内置', '1', 'system_role_type', 0, 'danger', '', '内置角色', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:02:08', b'0');
INSERT INTO `system_dict_data` VALUES (33, 2, '自定义', '2', 'system_role_type', 0, 'primary', '', '自定义角色', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:02:12', b'0');
INSERT INTO `system_dict_data` VALUES (34, 1, '全部数据权限', '1', 'system_data_scope', 0, '', '', '全部数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:17', b'0');
INSERT INTO `system_dict_data` VALUES (35, 2, '指定部门数据权限', '2', 'system_data_scope', 0, '', '', '指定部门数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:18', b'0');
INSERT INTO `system_dict_data` VALUES (36, 3, '本部门数据权限', '3', 'system_data_scope', 0, '', '', '本部门数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:16', b'0');
INSERT INTO `system_dict_data` VALUES (37, 4, '本部门及以下数据权限', '4', 'system_data_scope', 0, '', '', '本部门及以下数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:21', b'0');
INSERT INTO `system_dict_data` VALUES (38, 5, '仅本人数据权限', '5', 'system_data_scope', 0, '', '', '仅本人数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:23', b'0');
INSERT INTO `system_dict_data` VALUES (39, 0, '成功', '0', 'system_login_result', 0, 'success', '', '登陆结果 - 成功', '', '2021-01-18 06:17:36', '1', '2022-02-16 13:23:49', b'0');
INSERT INTO `system_dict_data` VALUES (40, 10, '账号或密码不正确', '10', 'system_login_result', 0, 'primary', '', '登陆结果 - 账号或密码不正确', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:27', b'0');
INSERT INTO `system_dict_data` VALUES (41, 20, '用户被禁用', '20', 'system_login_result', 0, 'warning', '', '登陆结果 - 用户被禁用', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:23:57', b'0');
INSERT INTO `system_dict_data` VALUES (42, 30, '验证码不存在', '30', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不存在', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:07', b'0');
INSERT INTO `system_dict_data` VALUES (43, 31, '验证码不正确', '31', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不正确', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:11', b'0');
INSERT INTO `system_dict_data` VALUES (44, 100, '未知异常', '100', 'system_login_result', 0, 'danger', '', '登陆结果 - 未知异常', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:23', b'0');
INSERT INTO `system_dict_data` VALUES (45, 1, '是', 'true', 'infra_boolean_string', 0, 'danger', '', 'Boolean 是否类型 - 是', '', '2021-01-19 03:20:55', '1', '2022-03-15 23:01:45', b'0');
INSERT INTO `system_dict_data` VALUES (46, 1, '否', 'false', 'infra_boolean_string', 0, 'info', '', 'Boolean 是否类型 - 否', '', '2021-01-19 03:20:55', '1', '2022-03-15 23:09:45', b'0');
INSERT INTO `system_dict_data` VALUES (50, 1, '单表（增删改查）', '1', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:09:06', '', '2022-03-10 16:33:15', b'0');
INSERT INTO `system_dict_data` VALUES (51, 2, '树表（增删改查）', '2', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:14:46', '', '2022-03-10 16:33:19', b'0');
INSERT INTO `system_dict_data` VALUES (53, 0, '初始化中', '0', 'infra_job_status', 0, 'primary', '', NULL, '', '2021-02-07 07:46:49', '1', '2022-02-16 19:33:29', b'0');
INSERT INTO `system_dict_data` VALUES (57, 0, '运行中', '0', 'infra_job_log_status', 0, 'primary', '', 'RUNNING', '', '2021-02-08 10:04:24', '1', '2022-02-16 19:07:48', b'0');
INSERT INTO `system_dict_data` VALUES (58, 1, '成功', '1', 'infra_job_log_status', 0, 'success', '', NULL, '', '2021-02-08 10:06:57', '1', '2022-02-16 19:07:52', b'0');
INSERT INTO `system_dict_data` VALUES (59, 2, '失败', '2', 'infra_job_log_status', 0, 'warning', '', '失败', '', '2021-02-08 10:07:38', '1', '2022-02-16 19:07:56', b'0');
INSERT INTO `system_dict_data` VALUES (60, 1, '会员', '1', 'user_type', 0, 'primary', '', NULL, '', '2021-02-26 00:16:27', '1', '2022-02-16 10:22:19', b'0');
INSERT INTO `system_dict_data` VALUES (61, 2, '管理员', '2', 'user_type', 0, 'success', '', NULL, '', '2021-02-26 00:16:34', '1', '2022-02-16 10:22:22', b'0');
INSERT INTO `system_dict_data` VALUES (62, 0, '未处理', '0', 'infra_api_error_log_process_status', 0, 'primary', '', NULL, '', '2021-02-26 07:07:19', '1', '2022-02-16 20:14:17', b'0');
INSERT INTO `system_dict_data` VALUES (63, 1, '已处理', '1', 'infra_api_error_log_process_status', 0, 'success', '', NULL, '', '2021-02-26 07:07:26', '1', '2022-02-16 20:14:08', b'0');
INSERT INTO `system_dict_data` VALUES (64, 2, '已忽略', '2', 'infra_api_error_log_process_status', 0, 'danger', '', NULL, '', '2021-02-26 07:07:34', '1', '2022-02-16 20:14:14', b'0');
INSERT INTO `system_dict_data` VALUES (66, 1, '阿里云', 'ALIYUN', 'system_sms_channel_code', 0, 'primary', '', NULL, '1', '2021-04-05 01:05:26', '1', '2024-07-22 22:23:25', b'0');
INSERT INTO `system_dict_data` VALUES (67, 1, '验证码', '1', 'system_sms_template_type', 0, 'warning', '', NULL, '1', '2021-04-05 21:50:57', '1', '2022-02-16 12:48:30', b'0');
INSERT INTO `system_dict_data` VALUES (68, 2, '通知', '2', 'system_sms_template_type', 0, 'primary', '', NULL, '1', '2021-04-05 21:51:08', '1', '2022-02-16 12:48:27', b'0');
INSERT INTO `system_dict_data` VALUES (69, 0, '营销', '3', 'system_sms_template_type', 0, 'danger', '', NULL, '1', '2021-04-05 21:51:15', '1', '2022-02-16 12:48:22', b'0');
INSERT INTO `system_dict_data` VALUES (70, 0, '初始化', '0', 'system_sms_send_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:18:33', '1', '2022-02-16 10:26:07', b'0');
INSERT INTO `system_dict_data` VALUES (71, 1, '发送成功', '10', 'system_sms_send_status', 0, 'success', '', NULL, '1', '2021-04-11 20:18:43', '1', '2022-02-16 10:25:56', b'0');
INSERT INTO `system_dict_data` VALUES (72, 2, '发送失败', '20', 'system_sms_send_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:18:49', '1', '2022-02-16 10:26:03', b'0');
INSERT INTO `system_dict_data` VALUES (73, 3, '不发送', '30', 'system_sms_send_status', 0, 'info', '', NULL, '1', '2021-04-11 20:19:44', '1', '2022-02-16 10:26:10', b'0');
INSERT INTO `system_dict_data` VALUES (74, 0, '等待结果', '0', 'system_sms_receive_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:27:43', '1', '2022-02-16 10:28:24', b'0');
INSERT INTO `system_dict_data` VALUES (75, 1, '接收成功', '10', 'system_sms_receive_status', 0, 'success', '', NULL, '1', '2021-04-11 20:29:25', '1', '2022-02-16 10:28:28', b'0');
INSERT INTO `system_dict_data` VALUES (76, 2, '接收失败', '20', 'system_sms_receive_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:29:31', '1', '2022-02-16 10:28:32', b'0');
INSERT INTO `system_dict_data` VALUES (77, 0, '调试(钉钉)', 'DEBUG_DING_TALK', 'system_sms_channel_code', 0, 'info', '', NULL, '1', '2021-04-13 00:20:37', '1', '2022-02-16 10:10:00', b'0');
INSERT INTO `system_dict_data` VALUES (80, 100, '账号登录', '100', 'system_login_type', 0, 'primary', '', '账号登录', '1', '2021-10-06 00:52:02', '1', '2022-02-16 13:11:34', b'0');
INSERT INTO `system_dict_data` VALUES (81, 101, '社交登录', '101', 'system_login_type', 0, 'info', '', '社交登录', '1', '2021-10-06 00:52:17', '1', '2022-02-16 13:11:40', b'0');
INSERT INTO `system_dict_data` VALUES (83, 200, '主动登出', '200', 'system_login_type', 0, 'primary', '', '主动登出', '1', '2021-10-06 00:52:58', '1', '2022-02-16 13:11:49', b'0');
INSERT INTO `system_dict_data` VALUES (85, 202, '强制登出', '202', 'system_login_type', 0, 'danger', '', '强制退出', '1', '2021-10-06 00:53:41', '1', '2022-02-16 13:11:57', b'0');
INSERT INTO `system_dict_data` VALUES (86, 0, '病假', '1', 'bpm_oa_leave_type', 0, 'primary', '', NULL, '1', '2021-09-21 22:35:28', '1', '2022-02-16 10:00:41', b'0');
INSERT INTO `system_dict_data` VALUES (87, 1, '事假', '2', 'bpm_oa_leave_type', 0, 'info', '', NULL, '1', '2021-09-21 22:36:11', '1', '2022-02-16 10:00:49', b'0');
INSERT INTO `system_dict_data` VALUES (88, 2, '婚假', '3', 'bpm_oa_leave_type', 0, 'warning', '', NULL, '1', '2021-09-21 22:36:38', '1', '2022-02-16 10:00:53', b'0');
INSERT INTO `system_dict_data` VALUES (112, 0, '微信 Wap 网站支付', 'wx_wap', 'pay_channel_code', 0, 'success', '', '微信 Wap 网站支付', '1', '2023-07-19 20:08:06', '1', '2023-07-19 20:09:08', b'0');
INSERT INTO `system_dict_data` VALUES (113, 1, '微信公众号支付', 'wx_pub', 'pay_channel_code', 0, 'success', '', '微信公众号支付', '1', '2021-12-03 10:40:24', '1', '2023-07-19 20:08:47', b'0');
INSERT INTO `system_dict_data` VALUES (114, 2, '微信小程序支付', 'wx_lite', 'pay_channel_code', 0, 'success', '', '微信小程序支付', '1', '2021-12-03 10:41:06', '1', '2023-07-19 20:08:50', b'0');
INSERT INTO `system_dict_data` VALUES (115, 3, '微信 App 支付', 'wx_app', 'pay_channel_code', 0, 'success', '', '微信 App 支付', '1', '2021-12-03 10:41:20', '1', '2023-07-19 20:08:56', b'0');
INSERT INTO `system_dict_data` VALUES (116, 10, '支付宝 PC 网站支付', 'alipay_pc', 'pay_channel_code', 0, 'primary', '', '支付宝 PC 网站支付', '1', '2021-12-03 10:42:09', '1', '2023-07-19 20:09:12', b'0');
INSERT INTO `system_dict_data` VALUES (117, 11, '支付宝 Wap 网站支付', 'alipay_wap', 'pay_channel_code', 0, 'primary', '', '支付宝 Wap 网站支付', '1', '2021-12-03 10:42:26', '1', '2023-07-19 20:09:16', b'0');
INSERT INTO `system_dict_data` VALUES (118, 12, '支付宝 App 支付', 'alipay_app', 'pay_channel_code', 0, 'primary', '', '支付宝 App 支付', '1', '2021-12-03 10:42:55', '1', '2023-07-19 20:09:20', b'0');
INSERT INTO `system_dict_data` VALUES (119, 14, '支付宝扫码支付', 'alipay_qr', 'pay_channel_code', 0, 'primary', '', '支付宝扫码支付', '1', '2021-12-03 10:43:10', '1', '2023-07-19 20:09:28', b'0');
INSERT INTO `system_dict_data` VALUES (120, 10, '通知成功', '10', 'pay_notify_status', 0, 'success', '', '通知成功', '1', '2021-12-03 11:02:41', '1', '2023-07-19 10:08:19', b'0');
INSERT INTO `system_dict_data` VALUES (121, 20, '通知失败', '20', 'pay_notify_status', 0, 'danger', '', '通知失败', '1', '2021-12-03 11:02:59', '1', '2023-07-19 10:08:21', b'0');
INSERT INTO `system_dict_data` VALUES (122, 0, '等待通知', '0', 'pay_notify_status', 0, 'info', '', '未通知', '1', '2021-12-03 11:03:10', '1', '2023-07-19 10:08:24', b'0');
INSERT INTO `system_dict_data` VALUES (123, 10, '支付成功', '10', 'pay_order_status', 0, 'success', '', '支付成功', '1', '2021-12-03 11:18:29', '1', '2023-07-19 18:04:28', b'0');
INSERT INTO `system_dict_data` VALUES (124, 30, '支付关闭', '30', 'pay_order_status', 0, 'info', '', '支付关闭', '1', '2021-12-03 11:18:42', '1', '2023-07-19 18:05:07', b'0');
INSERT INTO `system_dict_data` VALUES (125, 0, '等待支付', '0', 'pay_order_status', 0, 'info', '', '未支付', '1', '2021-12-03 11:18:18', '1', '2023-07-19 18:04:15', b'0');
INSERT INTO `system_dict_data` VALUES (600, 5, '首页', '1', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (601, 4, '秒杀活动页', '2', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (602, 3, '砍价活动页', '3', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (603, 2, '限时折扣页', '4', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (604, 1, '满减送页', '5', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (1118, 0, '等待退款', '0', 'pay_refund_status', 0, 'info', '', '等待退款', '1', '2021-12-10 16:44:59', '1', '2023-07-19 10:14:39', b'0');
INSERT INTO `system_dict_data` VALUES (1119, 20, '退款失败', '20', 'pay_refund_status', 0, 'danger', '', '退款失败', '1', '2021-12-10 16:45:10', '1', '2023-07-19 10:15:10', b'0');
INSERT INTO `system_dict_data` VALUES (1124, 10, '退款成功', '10', 'pay_refund_status', 0, 'success', '', '退款成功', '1', '2021-12-10 16:46:26', '1', '2023-07-19 10:15:00', b'0');
INSERT INTO `system_dict_data` VALUES (1127, 1, '审批中', '1', 'bpm_process_instance_status', 0, 'default', '', '流程实例的状态 - 进行中', '1', '2022-01-07 23:47:22', '1', '2024-03-16 16:11:45', b'0');
INSERT INTO `system_dict_data` VALUES (1128, 2, '审批通过', '2', 'bpm_process_instance_status', 0, 'success', '', '流程实例的状态 - 已完成', '1', '2022-01-07 23:47:49', '1', '2024-03-16 16:11:54', b'0');
INSERT INTO `system_dict_data` VALUES (1129, 1, '审批中', '1', 'bpm_task_status', 0, 'primary', '', '流程实例的结果 - 处理中', '1', '2022-01-07 23:48:32', '1', '2024-03-08 22:41:37', b'0');
INSERT INTO `system_dict_data` VALUES (1130, 2, '审批通过', '2', 'bpm_task_status', 0, 'success', '', '流程实例的结果 - 通过', '1', '2022-01-07 23:48:45', '1', '2024-03-08 22:41:38', b'0');
INSERT INTO `system_dict_data` VALUES (1131, 3, '审批不通过', '3', 'bpm_task_status', 0, 'danger', '', '流程实例的结果 - 不通过', '1', '2022-01-07 23:48:55', '1', '2024-03-08 22:41:38', b'0');
INSERT INTO `system_dict_data` VALUES (1132, 4, '已取消', '4', 'bpm_task_status', 0, 'info', '', '流程实例的结果 - 撤销', '1', '2022-01-07 23:49:06', '1', '2024-03-08 22:41:39', b'0');
INSERT INTO `system_dict_data` VALUES (1133, 10, '流程表单', '10', 'bpm_model_form_type', 0, '', '', '流程的表单类型 - 流程表单', '103', '2022-01-11 23:51:30', '103', '2022-01-11 23:51:30', b'0');
INSERT INTO `system_dict_data` VALUES (1134, 20, '业务表单', '20', 'bpm_model_form_type', 0, '', '', '流程的表单类型 - 业务表单', '103', '2022-01-11 23:51:47', '103', '2022-01-11 23:51:47', b'0');
INSERT INTO `system_dict_data` VALUES (1135, 10, '角色', '10', 'bpm_task_candidate_strategy', 0, 'info', '', '任务分配规则的类型 - 角色', '103', '2022-01-12 23:21:22', '1', '2024-03-06 02:53:16', b'0');
INSERT INTO `system_dict_data` VALUES (1136, 20, '部门的成员', '20', 'bpm_task_candidate_strategy', 0, 'primary', '', '任务分配规则的类型 - 部门的成员', '103', '2022-01-12 23:21:47', '1', '2024-03-06 02:53:17', b'0');
INSERT INTO `system_dict_data` VALUES (1137, 21, '部门的负责人', '21', 'bpm_task_candidate_strategy', 0, 'primary', '', '任务分配规则的类型 - 部门的负责人', '103', '2022-01-12 23:33:36', '1', '2024-03-06 02:53:18', b'0');
INSERT INTO `system_dict_data` VALUES (1138, 30, '用户', '30', 'bpm_task_candidate_strategy', 0, 'info', '', '任务分配规则的类型 - 用户', '103', '2022-01-12 23:34:02', '1', '2024-03-06 02:53:19', b'0');
INSERT INTO `system_dict_data` VALUES (1139, 40, '用户组', '40', 'bpm_task_candidate_strategy', 0, 'warning', '', '任务分配规则的类型 - 用户组', '103', '2022-01-12 23:34:21', '1', '2024-03-06 02:53:20', b'0');
INSERT INTO `system_dict_data` VALUES (1140, 60, '流程表达式', '60', 'bpm_task_candidate_strategy', 0, 'danger', '', '任务分配规则的类型 - 流程表达式', '103', '2022-01-12 23:34:43', '1', '2024-03-06 02:53:20', b'0');
INSERT INTO `system_dict_data` VALUES (1141, 22, '岗位', '22', 'bpm_task_candidate_strategy', 0, 'success', '', '任务分配规则的类型 - 岗位', '103', '2022-01-14 18:41:55', '1', '2024-03-06 02:53:21', b'0');
INSERT INTO `system_dict_data` VALUES (1145, 1, '管理后台', '1', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 管理后台', '1', '2022-02-02 13:15:06', '1', '2022-03-10 16:32:59', b'0');
INSERT INTO `system_dict_data` VALUES (1146, 2, '用户 APP', '2', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 用户 APP', '1', '2022-02-02 13:15:19', '1', '2022-03-10 16:33:03', b'0');
INSERT INTO `system_dict_data` VALUES (1150, 1, '数据库', '1', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:28', '1', '2022-03-15 00:25:28', b'0');
INSERT INTO `system_dict_data` VALUES (1151, 10, '本地磁盘', '10', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:41', '1', '2022-03-15 00:25:56', b'0');
INSERT INTO `system_dict_data` VALUES (1152, 11, 'FTP 服务器', '11', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:06', '1', '2022-03-15 00:26:10', b'0');
INSERT INTO `system_dict_data` VALUES (1153, 12, 'SFTP 服务器', '12', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:22', '1', '2022-03-15 00:26:22', b'0');
INSERT INTO `system_dict_data` VALUES (1154, 20, 'S3 对象存储', '20', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:31', '1', '2022-03-15 00:26:45', b'0');
INSERT INTO `system_dict_data` VALUES (1155, 103, '短信登录', '103', 'system_login_type', 0, 'default', '', NULL, '1', '2022-05-09 23:57:58', '1', '2022-05-09 23:58:09', b'0');
INSERT INTO `system_dict_data` VALUES (1156, 1, 'password', 'password', 'system_oauth2_grant_type', 0, 'default', '', '密码模式', '1', '2022-05-12 00:22:05', '1', '2022-05-11 16:26:01', b'0');
INSERT INTO `system_dict_data` VALUES (1157, 2, 'authorization_code', 'authorization_code', 'system_oauth2_grant_type', 0, 'primary', '', '授权码模式', '1', '2022-05-12 00:22:59', '1', '2022-05-11 16:26:02', b'0');
INSERT INTO `system_dict_data` VALUES (1158, 3, 'implicit', 'implicit', 'system_oauth2_grant_type', 0, 'success', '', '简化模式', '1', '2022-05-12 00:23:40', '1', '2022-05-11 16:26:05', b'0');
INSERT INTO `system_dict_data` VALUES (1159, 4, 'client_credentials', 'client_credentials', 'system_oauth2_grant_type', 0, 'default', '', '客户端模式', '1', '2022-05-12 00:23:51', '1', '2022-05-11 16:26:08', b'0');
INSERT INTO `system_dict_data` VALUES (1160, 5, 'refresh_token', 'refresh_token', 'system_oauth2_grant_type', 0, 'info', '', '刷新模式', '1', '2022-05-12 00:24:02', '1', '2022-05-11 16:26:11', b'0');
INSERT INTO `system_dict_data` VALUES (1162, 1, '销售中', '1', 'product_spu_status', 0, 'success', '', '商品 SPU 状态 - 销售中', '1', '2022-10-24 21:19:47', '1', '2022-10-24 21:20:38', b'0');
INSERT INTO `system_dict_data` VALUES (1163, 0, '仓库中', '0', 'product_spu_status', 0, 'info', '', '商品 SPU 状态 - 仓库中', '1', '2022-10-24 21:20:54', '1', '2022-10-24 21:21:22', b'0');
INSERT INTO `system_dict_data` VALUES (1164, 0, '回收站', '-1', 'product_spu_status', 0, 'default', '', '商品 SPU 状态 - 回收站', '1', '2022-10-24 21:21:11', '1', '2022-10-24 21:21:11', b'0');
INSERT INTO `system_dict_data` VALUES (1165, 1, '满减', '1', 'promotion_discount_type', 0, 'success', '', '优惠类型 - 满减', '1', '2022-11-01 12:46:41', '1', '2022-11-01 12:50:11', b'0');
INSERT INTO `system_dict_data` VALUES (1166, 2, '折扣', '2', 'promotion_discount_type', 0, 'primary', '', '优惠类型 - 折扣', '1', '2022-11-01 12:46:51', '1', '2022-11-01 12:50:08', b'0');
INSERT INTO `system_dict_data` VALUES (1167, 1, '固定日期', '1', 'promotion_coupon_template_validity_type', 0, 'default', '', '优惠劵模板的有限期类型 - 固定日期', '1', '2022-11-02 00:07:34', '1', '2022-11-04 00:07:49', b'0');
INSERT INTO `system_dict_data` VALUES (1168, 2, '领取之后', '2', 'promotion_coupon_template_validity_type', 0, 'default', '', '优惠劵模板的有限期类型 - 领取之后', '1', '2022-11-02 00:07:54', '1', '2022-11-04 00:07:52', b'0');
INSERT INTO `system_dict_data` VALUES (1169, 1, '通用劵', '1', 'promotion_product_scope', 0, 'default', '', '营销的商品范围 - 全部商品参与', '1', '2022-11-02 00:28:22', '1', '2023-09-28 00:27:42', b'0');
INSERT INTO `system_dict_data` VALUES (1170, 2, '商品劵', '2', 'promotion_product_scope', 0, 'default', '', '营销的商品范围 - 指定商品参与', '1', '2022-11-02 00:28:34', '1', '2023-09-28 00:27:44', b'0');
INSERT INTO `system_dict_data` VALUES (1171, 1, '未使用', '1', 'promotion_coupon_status', 0, 'primary', '', '优惠劵的状态 - 已领取', '1', '2022-11-04 00:15:08', '1', '2023-10-03 12:54:38', b'0');
INSERT INTO `system_dict_data` VALUES (1172, 2, '已使用', '2', 'promotion_coupon_status', 0, 'success', '', '优惠劵的状态 - 已使用', '1', '2022-11-04 00:15:21', '1', '2022-11-04 19:16:08', b'0');
INSERT INTO `system_dict_data` VALUES (1173, 3, '已过期', '3', 'promotion_coupon_status', 0, 'info', '', '优惠劵的状态 - 已过期', '1', '2022-11-04 00:15:43', '1', '2022-11-04 19:16:12', b'0');
INSERT INTO `system_dict_data` VALUES (1174, 1, '直接领取', '1', 'promotion_coupon_take_type', 0, 'primary', '', '优惠劵的领取方式 - 直接领取', '1', '2022-11-04 19:13:00', '1', '2022-11-04 19:13:25', b'0');
INSERT INTO `system_dict_data` VALUES (1175, 2, '指定发放', '2', 'promotion_coupon_take_type', 0, 'success', '', '优惠劵的领取方式 - 指定发放', '1', '2022-11-04 19:13:13', '1', '2022-11-04 19:14:48', b'0');
INSERT INTO `system_dict_data` VALUES (1176, 10, '未开始', '10', 'promotion_activity_status', 0, 'primary', '', '促销活动的状态枚举 - 未开始', '1', '2022-11-04 22:54:49', '1', '2022-11-04 22:55:53', b'0');
INSERT INTO `system_dict_data` VALUES (1177, 20, '进行中', '20', 'promotion_activity_status', 0, 'success', '', '促销活动的状态枚举 - 进行中', '1', '2022-11-04 22:55:06', '1', '2022-11-04 22:55:20', b'0');
INSERT INTO `system_dict_data` VALUES (1178, 30, '已结束', '30', 'promotion_activity_status', 0, 'info', '', '促销活动的状态枚举 - 已结束', '1', '2022-11-04 22:55:41', '1', '2022-11-04 22:55:41', b'0');
INSERT INTO `system_dict_data` VALUES (1179, 40, '已关闭', '40', 'promotion_activity_status', 0, 'warning', '', '促销活动的状态枚举 - 已关闭', '1', '2022-11-04 22:56:10', '1', '2022-11-04 22:56:18', b'0');
INSERT INTO `system_dict_data` VALUES (1180, 10, '满 N 元', '10', 'promotion_condition_type', 0, 'primary', '', '营销的条件类型 - 满 N 元', '1', '2022-11-04 22:59:45', '1', '2022-11-04 22:59:45', b'0');
INSERT INTO `system_dict_data` VALUES (1181, 20, '满 N 件', '20', 'promotion_condition_type', 0, 'success', '', '营销的条件类型 - 满 N 件', '1', '2022-11-04 23:00:02', '1', '2022-11-04 23:00:02', b'0');
INSERT INTO `system_dict_data` VALUES (1182, 10, '申请售后', '10', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 申请售后', '1', '2022-11-19 20:53:33', '1', '2022-11-19 20:54:42', b'0');
INSERT INTO `system_dict_data` VALUES (1183, 20, '商品待退货', '20', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 商品待退货', '1', '2022-11-19 20:54:36', '1', '2022-11-19 20:58:58', b'0');
INSERT INTO `system_dict_data` VALUES (1184, 30, '商家待收货', '30', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 商家待收货', '1', '2022-11-19 20:56:56', '1', '2022-11-19 20:59:20', b'0');
INSERT INTO `system_dict_data` VALUES (1185, 40, '等待退款', '40', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 等待退款', '1', '2022-11-19 20:59:54', '1', '2022-11-19 21:00:01', b'0');
INSERT INTO `system_dict_data` VALUES (1186, 50, '退款成功', '50', 'trade_after_sale_status', 0, 'default', '', '交易售后状态 - 退款成功', '1', '2022-11-19 21:00:33', '1', '2022-11-19 21:00:33', b'0');
INSERT INTO `system_dict_data` VALUES (1187, 61, '买家取消', '61', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 买家取消', '1', '2022-11-19 21:01:29', '1', '2022-11-19 21:01:29', b'0');
INSERT INTO `system_dict_data` VALUES (1188, 62, '商家拒绝', '62', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 商家拒绝', '1', '2022-11-19 21:02:17', '1', '2022-11-19 21:02:17', b'0');
INSERT INTO `system_dict_data` VALUES (1189, 63, '商家拒收货', '63', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 商家拒收货', '1', '2022-11-19 21:02:37', '1', '2022-11-19 21:03:07', b'0');
INSERT INTO `system_dict_data` VALUES (1190, 10, '售中退款', '10', 'trade_after_sale_type', 0, 'success', '', '交易售后的类型 - 售中退款', '1', '2022-11-19 21:05:05', '1', '2022-11-19 21:38:23', b'0');
INSERT INTO `system_dict_data` VALUES (1191, 20, '售后退款', '20', 'trade_after_sale_type', 0, 'primary', '', '交易售后的类型 - 售后退款', '1', '2022-11-19 21:05:32', '1', '2022-11-19 21:38:32', b'0');
INSERT INTO `system_dict_data` VALUES (1192, 10, '仅退款', '10', 'trade_after_sale_way', 0, 'primary', '', '交易售后的方式 - 仅退款', '1', '2022-11-19 21:39:19', '1', '2022-11-19 21:39:19', b'0');
INSERT INTO `system_dict_data` VALUES (1193, 20, '退货退款', '20', 'trade_after_sale_way', 0, 'success', '', '交易售后的方式 - 退货退款', '1', '2022-11-19 21:39:38', '1', '2022-11-19 21:39:49', b'0');
INSERT INTO `system_dict_data` VALUES (1194, 10, '微信小程序', '10', 'terminal', 0, 'default', '', '终端 - 微信小程序', '1', '2022-12-10 10:51:11', '1', '2022-12-10 10:51:57', b'0');
INSERT INTO `system_dict_data` VALUES (1195, 20, 'H5 网页', '20', 'terminal', 0, 'default', '', '终端 - H5 网页', '1', '2022-12-10 10:51:30', '1', '2022-12-10 10:51:59', b'0');
INSERT INTO `system_dict_data` VALUES (1196, 11, '微信公众号', '11', 'terminal', 0, 'default', '', '终端 - 微信公众号', '1', '2022-12-10 10:54:16', '1', '2022-12-10 10:52:01', b'0');
INSERT INTO `system_dict_data` VALUES (1197, 31, '苹果 App', '31', 'terminal', 0, 'default', '', '终端 - 苹果 App', '1', '2022-12-10 10:54:42', '1', '2022-12-10 10:52:18', b'0');
INSERT INTO `system_dict_data` VALUES (1198, 32, '安卓 App', '32', 'terminal', 0, 'default', '', '终端 - 安卓 App', '1', '2022-12-10 10:55:02', '1', '2022-12-10 10:59:17', b'0');
INSERT INTO `system_dict_data` VALUES (1199, 0, '普通订单', '0', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 普通订单', '1', '2022-12-10 16:34:14', '1', '2022-12-10 16:34:14', b'0');
INSERT INTO `system_dict_data` VALUES (1200, 1, '秒杀订单', '1', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 秒杀订单', '1', '2022-12-10 16:34:26', '1', '2022-12-10 16:34:26', b'0');
INSERT INTO `system_dict_data` VALUES (1201, 2, '砍价订单', '2', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 拼团订单', '1', '2022-12-10 16:34:36', '1', '2024-09-07 14:18:39', b'0');
INSERT INTO `system_dict_data` VALUES (1202, 3, '拼团订单', '3', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 砍价订单', '1', '2022-12-10 16:34:48', '1', '2024-09-07 14:18:32', b'0');
INSERT INTO `system_dict_data` VALUES (1203, 0, '待支付', '0', 'trade_order_status', 0, 'default', '', '交易订单状态 - 待支付', '1', '2022-12-10 16:49:29', '1', '2022-12-10 16:49:29', b'0');
INSERT INTO `system_dict_data` VALUES (1204, 10, '待发货', '10', 'trade_order_status', 0, 'primary', '', '交易订单状态 - 待发货', '1', '2022-12-10 16:49:53', '1', '2022-12-10 16:51:17', b'0');
INSERT INTO `system_dict_data` VALUES (1205, 20, '已发货', '20', 'trade_order_status', 0, 'primary', '', '交易订单状态 - 已发货', '1', '2022-12-10 16:50:13', '1', '2022-12-10 16:51:31', b'0');
INSERT INTO `system_dict_data` VALUES (1206, 30, '已完成', '30', 'trade_order_status', 0, 'success', '', '交易订单状态 - 已完成', '1', '2022-12-10 16:50:30', '1', '2022-12-10 16:51:06', b'0');
INSERT INTO `system_dict_data` VALUES (1207, 40, '已取消', '40', 'trade_order_status', 0, 'danger', '', '交易订单状态 - 已取消', '1', '2022-12-10 16:50:50', '1', '2022-12-10 16:51:00', b'0');
INSERT INTO `system_dict_data` VALUES (1208, 0, '未售后', '0', 'trade_order_item_after_sale_status', 0, 'info', '', '交易订单项的售后状态 - 未售后', '1', '2022-12-10 20:58:42', '1', '2022-12-10 20:59:29', b'0');
INSERT INTO `system_dict_data` VALUES (1209, 10, '售后中', '10', 'trade_order_item_after_sale_status', 0, 'primary', '', '交易订单项的售后状态 - 售后中', '1', '2022-12-10 20:59:21', '1', '2024-07-21 17:01:24', b'0');
INSERT INTO `system_dict_data` VALUES (1210, 20, '已退款', '20', 'trade_order_item_after_sale_status', 0, 'success', '', '交易订单项的售后状态 - 已退款', '1', '2022-12-10 20:59:46', '1', '2024-07-21 17:01:35', b'0');
INSERT INTO `system_dict_data` VALUES (1211, 1, '完全匹配', '1', 'mp_auto_reply_request_match', 0, 'primary', '', '公众号自动回复的请求关键字匹配模式 - 完全匹配', '1', '2023-01-16 23:30:39', '1', '2023-01-16 23:31:00', b'0');
INSERT INTO `system_dict_data` VALUES (1212, 2, '半匹配', '2', 'mp_auto_reply_request_match', 0, 'success', '', '公众号自动回复的请求关键字匹配模式 - 半匹配', '1', '2023-01-16 23:30:55', '1', '2023-01-16 23:31:10', b'0');
INSERT INTO `system_dict_data` VALUES (1213, 1, '文本', 'text', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 文本', '1', '2023-01-17 22:17:32', '1', '2023-01-17 22:17:39', b'0');
INSERT INTO `system_dict_data` VALUES (1214, 2, '图片', 'image', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 图片', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:19:47', b'0');
INSERT INTO `system_dict_data` VALUES (1215, 3, '语音', 'voice', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 语音', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:20:08', b'0');
INSERT INTO `system_dict_data` VALUES (1216, 4, '视频', 'video', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 视频', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:21:08', b'0');
INSERT INTO `system_dict_data` VALUES (1217, 5, '小视频', 'shortvideo', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 小视频', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:19:59', b'0');
INSERT INTO `system_dict_data` VALUES (1218, 6, '图文', 'news', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 图文', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:22:54', b'0');
INSERT INTO `system_dict_data` VALUES (1219, 7, '音乐', 'music', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 音乐', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:22:54', b'0');
INSERT INTO `system_dict_data` VALUES (1220, 8, '地理位置', 'location', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 地理位置', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:23:51', b'0');
INSERT INTO `system_dict_data` VALUES (1221, 9, '链接', 'link', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 链接', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:24:49', b'0');
INSERT INTO `system_dict_data` VALUES (1222, 10, '事件', 'event', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 事件', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:24:49', b'0');
INSERT INTO `system_dict_data` VALUES (1223, 0, '初始化', '0', 'system_mail_send_status', 0, 'primary', '', '邮件发送状态 - 初始化\n', '1', '2023-01-26 09:53:49', '1', '2023-01-26 16:36:14', b'0');
INSERT INTO `system_dict_data` VALUES (1224, 10, '发送成功', '10', 'system_mail_send_status', 0, 'success', '', '邮件发送状态 - 发送成功', '1', '2023-01-26 09:54:28', '1', '2023-01-26 16:36:22', b'0');
INSERT INTO `system_dict_data` VALUES (1225, 20, '发送失败', '20', 'system_mail_send_status', 0, 'danger', '', '邮件发送状态 - 发送失败', '1', '2023-01-26 09:54:50', '1', '2023-01-26 16:36:26', b'0');
INSERT INTO `system_dict_data` VALUES (1226, 30, '不发送', '30', 'system_mail_send_status', 0, 'info', '', '邮件发送状态 -  不发送', '1', '2023-01-26 09:55:06', '1', '2023-01-26 16:36:36', b'0');
INSERT INTO `system_dict_data` VALUES (1227, 1, '通知公告', '1', 'system_notify_template_type', 0, 'primary', '', '站内信模版的类型 - 通知公告', '1', '2023-01-28 10:35:59', '1', '2023-01-28 10:35:59', b'0');
INSERT INTO `system_dict_data` VALUES (1228, 2, '系统消息', '2', 'system_notify_template_type', 0, 'success', '', '站内信模版的类型 - 系统消息', '1', '2023-01-28 10:36:20', '1', '2023-01-28 10:36:25', b'0');
INSERT INTO `system_dict_data` VALUES (1230, 13, '支付宝条码支付', 'alipay_bar', 'pay_channel_code', 0, 'primary', '', '支付宝条码支付', '1', '2023-02-18 23:32:24', '1', '2023-07-19 20:09:23', b'0');
INSERT INTO `system_dict_data` VALUES (1231, 10, 'Vue2 Element UI 标准模版', '10', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:03:55', '1', '2023-04-13 00:03:55', b'0');
INSERT INTO `system_dict_data` VALUES (1232, 20, 'Vue3 Element Plus 标准模版', '20', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:08', '1', '2023-04-13 00:04:08', b'0');
INSERT INTO `system_dict_data` VALUES (1234, 30, 'Vue3 vben 模版', '30', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:26', '1', '2023-04-13 00:04:26', b'0');
INSERT INTO `system_dict_data` VALUES (1244, 0, '按件', '1', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:46:40', '1', '2023-05-21 22:46:40', b'0');
INSERT INTO `system_dict_data` VALUES (1245, 1, '按重量', '2', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:46:58', '1', '2023-05-21 22:46:58', b'0');
INSERT INTO `system_dict_data` VALUES (1246, 2, '按体积', '3', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:47:18', '1', '2023-05-21 22:47:18', b'0');
INSERT INTO `system_dict_data` VALUES (1335, 11, '订单积分抵扣', '11', 'member_point_biz_type', 0, '', '', '', '1', '2023-06-10 12:15:27', '1', '2023-10-11 07:41:43', b'0');
INSERT INTO `system_dict_data` VALUES (1336, 1, '签到', '1', 'member_point_biz_type', 0, '', '', '', '1', '2023-06-10 12:15:48', '1', '2023-08-20 11:59:53', b'0');
INSERT INTO `system_dict_data` VALUES (1341, 20, '已退款', '20', 'pay_order_status', 0, 'danger', '', '已退款', '1', '2023-07-19 18:05:37', '1', '2023-07-19 18:05:37', b'0');
INSERT INTO `system_dict_data` VALUES (1342, 21, '请求成功，但是结果失败', '21', 'pay_notify_status', 0, 'warning', '', '请求成功，但是结果失败', '1', '2023-07-19 18:10:47', '1', '2023-07-19 18:11:38', b'0');
INSERT INTO `system_dict_data` VALUES (1343, 22, '请求失败', '22', 'pay_notify_status', 0, 'warning', '', NULL, '1', '2023-07-19 18:11:05', '1', '2023-07-19 18:11:27', b'0');
INSERT INTO `system_dict_data` VALUES (1344, 4, '微信扫码支付', 'wx_native', 'pay_channel_code', 0, 'success', '', '微信扫码支付', '1', '2023-07-19 20:07:47', '1', '2023-07-19 20:09:03', b'0');
INSERT INTO `system_dict_data` VALUES (1345, 5, '微信条码支付', 'wx_bar', 'pay_channel_code', 0, 'success', '', '微信条码支付\n', '1', '2023-07-19 20:08:06', '1', '2023-07-19 20:09:08', b'0');
INSERT INTO `system_dict_data` VALUES (1346, 1, '支付单', '1', 'pay_notify_type', 0, 'primary', '', '支付单', '1', '2023-07-20 12:23:17', '1', '2023-07-20 12:23:17', b'0');
INSERT INTO `system_dict_data` VALUES (1347, 2, '退款单', '2', 'pay_notify_type', 0, 'danger', '', NULL, '1', '2023-07-20 12:23:26', '1', '2023-07-20 12:23:26', b'0');
INSERT INTO `system_dict_data` VALUES (1348, 20, '模拟支付', 'mock', 'pay_channel_code', 0, 'default', '', '模拟支付', '1', '2023-07-29 11:10:51', '1', '2023-07-29 03:14:10', b'0');
INSERT INTO `system_dict_data` VALUES (1349, 12, '订单积分抵扣（整单取消）', '12', 'member_point_biz_type', 0, '', '', '', '1', '2023-08-20 12:00:03', '1', '2023-10-11 07:42:01', b'0');
INSERT INTO `system_dict_data` VALUES (1350, 0, '管理员调整', '0', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0');
INSERT INTO `system_dict_data` VALUES (1351, 1, '邀新奖励', '1', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0');
INSERT INTO `system_dict_data` VALUES (1352, 11, '下单奖励', '11', 'member_experience_biz_type', 0, 'success', '', NULL, '', '2023-08-22 12:41:01', '1', '2023-10-11 07:45:09', b'0');
INSERT INTO `system_dict_data` VALUES (1353, 12, '下单奖励（整单取消）', '12', 'member_experience_biz_type', 0, 'warning', '', NULL, '', '2023-08-22 12:41:01', '1', '2023-10-11 07:45:01', b'0');
INSERT INTO `system_dict_data` VALUES (1354, 4, '签到奖励', '4', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0');
INSERT INTO `system_dict_data` VALUES (1355, 5, '抽奖奖励', '5', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0');
INSERT INTO `system_dict_data` VALUES (1356, 1, '快递发货', '1', 'trade_delivery_type', 0, '', '', '', '1', '2023-08-23 00:04:55', '1', '2023-08-23 00:04:55', b'0');
INSERT INTO `system_dict_data` VALUES (1357, 2, '用户自提', '2', 'trade_delivery_type', 0, '', '', '', '1', '2023-08-23 00:05:05', '1', '2023-08-23 00:05:05', b'0');
INSERT INTO `system_dict_data` VALUES (1358, 3, '品类劵', '3', 'promotion_product_scope', 0, 'default', '', '', '1', '2023-09-01 23:43:07', '1', '2023-09-28 00:27:47', b'0');
INSERT INTO `system_dict_data` VALUES (1359, 1, '人人分销', '1', 'brokerage_enabled_condition', 0, '', '', '所有用户都可以分销', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1360, 2, '指定分销', '2', 'brokerage_enabled_condition', 0, '', '', '仅可后台手动设置推广员', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1361, 1, '首次绑定', '1', 'brokerage_bind_mode', 0, '', '', '只要用户没有推广人，随时都可以绑定推广关系', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1362, 2, '注册绑定', '2', 'brokerage_bind_mode', 0, '', '', '仅新用户注册时才能绑定推广关系', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1363, 3, '覆盖绑定', '3', 'brokerage_bind_mode', 0, '', '', '如果用户已经有推广人，推广人会被变更', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1364, 1, '钱包', '1', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1365, 2, '银行卡', '2', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1366, 3, '微信', '3', 'brokerage_withdraw_type', 0, '', '', '手动打款', '', '2023-09-28 02:46:05', '1', '2024-10-13 11:06:54', b'0');
INSERT INTO `system_dict_data` VALUES (1367, 4, '支付宝', '4', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1368, 1, '订单返佣', '1', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1369, 2, '申请提现', '2', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1370, 3, '申请提现驳回', '3', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1371, 0, '待结算', '0', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1372, 1, '已结算', '1', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1373, 2, '已取消', '2', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1374, 0, '审核中', '0', 'brokerage_withdraw_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1375, 10, '审核通过', '10', 'brokerage_withdraw_status', 0, 'success', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1376, 11, '提现成功', '11', 'brokerage_withdraw_status', 0, 'success', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1377, 20, '审核不通过', '20', 'brokerage_withdraw_status', 0, 'danger', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1378, 21, '提现失败', '21', 'brokerage_withdraw_status', 0, 'danger', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1379, 0, '工商银行', '0', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1380, 1, '建设银行', '1', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1381, 2, '农业银行', '2', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1382, 3, '中国银行', '3', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1383, 4, '交通银行', '4', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1384, 5, '招商银行', '5', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1385, 21, '钱包', 'wallet', 'pay_channel_code', 0, 'primary', '', '', '1', '2023-10-01 21:46:19', '1', '2023-10-01 21:48:01', b'0');
INSERT INTO `system_dict_data` VALUES (1386, 1, '砍价中', '1', 'promotion_bargain_record_status', 0, 'default', '', '', '1', '2023-10-05 10:41:26', '1', '2023-10-05 10:41:26', b'0');
INSERT INTO `system_dict_data` VALUES (1387, 2, '砍价成功', '2', 'promotion_bargain_record_status', 0, 'success', '', '', '1', '2023-10-05 10:41:39', '1', '2023-10-05 10:41:39', b'0');
INSERT INTO `system_dict_data` VALUES (1388, 3, '砍价失败', '3', 'promotion_bargain_record_status', 0, 'warning', '', '', '1', '2023-10-05 10:41:57', '1', '2023-10-05 10:41:57', b'0');
INSERT INTO `system_dict_data` VALUES (1389, 0, '拼团中', '0', 'promotion_combination_record_status', 0, '', '', '', '1', '2023-10-08 07:24:44', '1', '2024-10-13 10:08:17', b'0');
INSERT INTO `system_dict_data` VALUES (1390, 1, '拼团成功', '1', 'promotion_combination_record_status', 0, 'success', '', '', '1', '2023-10-08 07:24:56', '1', '2024-10-13 10:08:20', b'0');
INSERT INTO `system_dict_data` VALUES (1391, 2, '拼团失败', '2', 'promotion_combination_record_status', 0, 'warning', '', '', '1', '2023-10-08 07:25:11', '1', '2024-10-13 10:08:24', b'0');
INSERT INTO `system_dict_data` VALUES (1392, 2, '管理员修改', '2', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:41:34', '1', '2023-10-11 07:41:34', b'0');
INSERT INTO `system_dict_data` VALUES (1393, 13, '订单积分抵扣（单个退款）', '13', 'member_point_biz_type', 0, '', '', '', '1', '2023-10-11 07:42:29', '1', '2023-10-11 07:42:29', b'0');
INSERT INTO `system_dict_data` VALUES (1394, 21, '订单积分奖励', '21', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:42:44', '1', '2023-10-11 07:42:44', b'0');
INSERT INTO `system_dict_data` VALUES (1395, 22, '订单积分奖励（整单取消）', '22', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:42:55', '1', '2023-10-11 07:43:01', b'0');
INSERT INTO `system_dict_data` VALUES (1396, 23, '订单积分奖励（单个退款）', '23', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:43:16', '1', '2023-10-11 07:43:16', b'0');
INSERT INTO `system_dict_data` VALUES (1397, 13, '下单奖励（单个退款）', '13', 'member_experience_biz_type', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (1398, 5, '网上转账', '5', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:24', '1', '2023-10-18 21:55:24', b'0');
INSERT INTO `system_dict_data` VALUES (1399, 6, '支付宝', '6', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:38', '1', '2023-10-18 21:55:38', b'0');
INSERT INTO `system_dict_data` VALUES (1400, 7, '微信支付', '7', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:53', '1', '2023-10-18 21:55:53', b'0');
INSERT INTO `system_dict_data` VALUES (1401, 8, '其他', '8', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:56:06', '1', '2023-10-18 21:56:06', b'0');
INSERT INTO `system_dict_data` VALUES (1402, 1, 'IT', '1', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:15', '1', '2024-02-18 23:30:38', b'0');
INSERT INTO `system_dict_data` VALUES (1403, 2, '金融业', '2', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:29', '1', '2024-02-18 23:30:43', b'0');
INSERT INTO `system_dict_data` VALUES (1404, 3, '房地产', '3', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:41', '1', '2024-02-18 23:30:48', b'0');
INSERT INTO `system_dict_data` VALUES (1405, 4, '商业服务', '4', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:54', '1', '2024-02-18 23:30:54', b'0');
INSERT INTO `system_dict_data` VALUES (1406, 5, '运输/物流', '5', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:03', '1', '2024-02-18 23:31:00', b'0');
INSERT INTO `system_dict_data` VALUES (1407, 6, '生产', '6', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:13', '1', '2024-02-18 23:31:08', b'0');
INSERT INTO `system_dict_data` VALUES (1408, 7, '政府', '7', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:27', '1', '2024-02-18 23:31:13', b'0');
INSERT INTO `system_dict_data` VALUES (1409, 8, '文化传媒', '8', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:37', '1', '2024-02-18 23:31:20', b'0');
INSERT INTO `system_dict_data` VALUES (1422, 1, 'A （重点客户）', '1', 'crm_customer_level', 0, 'primary', '', '', '1', '2023-10-28 23:07:13', '1', '2023-10-28 23:07:13', b'0');
INSERT INTO `system_dict_data` VALUES (1423, 2, 'B （普通客户）', '2', 'crm_customer_level', 0, 'info', '', '', '1', '2023-10-28 23:07:35', '1', '2023-10-28 23:07:35', b'0');
INSERT INTO `system_dict_data` VALUES (1424, 3, 'C （非优先客户）', '3', 'crm_customer_level', 0, 'default', '', '', '1', '2023-10-28 23:07:53', '1', '2023-10-28 23:07:53', b'0');
INSERT INTO `system_dict_data` VALUES (1425, 1, '促销', '1', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:29', '1', '2023-10-28 23:08:29', b'0');
INSERT INTO `system_dict_data` VALUES (1426, 2, '搜索引擎', '2', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:39', '1', '2023-10-28 23:08:39', b'0');
INSERT INTO `system_dict_data` VALUES (1427, 3, '广告', '3', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:47', '1', '2023-10-28 23:08:47', b'0');
INSERT INTO `system_dict_data` VALUES (1428, 4, '转介绍', '4', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:58', '1', '2023-10-28 23:08:58', b'0');
INSERT INTO `system_dict_data` VALUES (1429, 5, '线上注册', '5', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:12', '1', '2023-10-28 23:09:12', b'0');
INSERT INTO `system_dict_data` VALUES (1430, 6, '线上咨询', '6', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:22', '1', '2023-10-28 23:09:22', b'0');
INSERT INTO `system_dict_data` VALUES (1431, 7, '预约上门', '7', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:39', '1', '2023-10-28 23:09:39', b'0');
INSERT INTO `system_dict_data` VALUES (1432, 8, '陌拜', '8', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:04', '1', '2023-10-28 23:10:04', b'0');
INSERT INTO `system_dict_data` VALUES (1433, 9, '电话咨询', '9', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:18', '1', '2023-10-28 23:10:18', b'0');
INSERT INTO `system_dict_data` VALUES (1434, 10, '邮件咨询', '10', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:33', '1', '2023-10-28 23:10:33', b'0');
INSERT INTO `system_dict_data` VALUES (1435, 10, 'Gitee', '10', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:04:42', '1', '2023-11-04 13:04:42', b'0');
INSERT INTO `system_dict_data` VALUES (1436, 20, '钉钉', '20', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:04:54', '1', '2023-11-04 13:04:54', b'0');
INSERT INTO `system_dict_data` VALUES (1437, 30, '企业微信', '30', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:09', '1', '2023-11-04 13:05:09', b'0');
INSERT INTO `system_dict_data` VALUES (1438, 31, '微信公众平台', '31', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:18', '1', '2023-11-04 13:05:18', b'0');
INSERT INTO `system_dict_data` VALUES (1439, 32, '微信开放平台', '32', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:30', '1', '2023-11-04 13:05:30', b'0');
INSERT INTO `system_dict_data` VALUES (1440, 34, '微信小程序', '34', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:38', '1', '2023-11-04 13:07:16', b'0');
INSERT INTO `system_dict_data` VALUES (1441, 1, '上架', '1', 'crm_product_status', 0, 'success', '', '', '1', '2023-10-30 21:49:34', '1', '2023-10-30 21:49:34', b'0');
INSERT INTO `system_dict_data` VALUES (1442, 0, '下架', '0', 'crm_product_status', 0, 'success', '', '', '1', '2023-10-30 21:49:13', '1', '2023-10-30 21:49:13', b'0');
INSERT INTO `system_dict_data` VALUES (1443, 15, '子表', '15', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-13 23:06:16', '1', '2023-11-13 23:06:16', b'0');
INSERT INTO `system_dict_data` VALUES (1444, 10, '主表（标准模式）', '10', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-14 12:32:49', '1', '2023-11-14 12:32:49', b'0');
INSERT INTO `system_dict_data` VALUES (1445, 11, '主表（ERP 模式）', '11', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-14 12:33:05', '1', '2023-11-14 12:33:05', b'0');
INSERT INTO `system_dict_data` VALUES (1446, 12, '主表（内嵌模式）', '12', 'infra_codegen_template_type', 0, '', '', '', '1', '2023-11-14 12:33:31', '1', '2023-11-14 12:33:31', b'0');
INSERT INTO `system_dict_data` VALUES (1447, 1, '负责人', '1', 'crm_permission_level', 0, 'default', '', '', '1', '2023-11-30 09:53:12', '1', '2023-11-30 09:53:12', b'0');
INSERT INTO `system_dict_data` VALUES (1448, 2, '只读', '2', 'crm_permission_level', 0, '', '', '', '1', '2023-11-30 09:53:29', '1', '2023-11-30 09:53:29', b'0');
INSERT INTO `system_dict_data` VALUES (1449, 3, '读写', '3', 'crm_permission_level', 0, '', '', '', '1', '2023-11-30 09:53:36', '1', '2023-11-30 09:53:36', b'0');
INSERT INTO `system_dict_data` VALUES (1450, 0, '未提交', '0', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:56:59', '1', '2023-11-30 18:56:59', b'0');
INSERT INTO `system_dict_data` VALUES (1451, 10, '审批中', '10', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:10', '1', '2023-11-30 18:57:10', b'0');
INSERT INTO `system_dict_data` VALUES (1452, 20, '审核通过', '20', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:24', '1', '2023-11-30 18:57:24', b'0');
INSERT INTO `system_dict_data` VALUES (1453, 30, '审核不通过', '30', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:32', '1', '2023-11-30 18:57:32', b'0');
INSERT INTO `system_dict_data` VALUES (1454, 40, '已取消', '40', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:42', '1', '2023-11-30 18:57:42', b'0');
INSERT INTO `system_dict_data` VALUES (1456, 1, '支票', '1', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:29', '1', '2023-10-18 21:54:29', b'0');
INSERT INTO `system_dict_data` VALUES (1457, 2, '现金', '2', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:41', '1', '2023-10-18 21:54:41', b'0');
INSERT INTO `system_dict_data` VALUES (1458, 3, '邮政汇款', '3', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:53', '1', '2023-10-18 21:54:53', b'0');
INSERT INTO `system_dict_data` VALUES (1459, 4, '电汇', '4', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:07', '1', '2023-10-18 21:55:07', b'0');
INSERT INTO `system_dict_data` VALUES (1461, 1, '个', '1', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:26', '1', '2023-12-05 23:02:26', b'0');
INSERT INTO `system_dict_data` VALUES (1462, 2, '块', '2', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:34', '1', '2023-12-05 23:02:34', b'0');
INSERT INTO `system_dict_data` VALUES (1463, 3, '只', '3', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:57', '1', '2023-12-05 23:02:57', b'0');
INSERT INTO `system_dict_data` VALUES (1464, 4, '把', '4', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:05', '1', '2023-12-05 23:03:05', b'0');
INSERT INTO `system_dict_data` VALUES (1465, 5, '枚', '5', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:14', '1', '2023-12-05 23:03:14', b'0');
INSERT INTO `system_dict_data` VALUES (1466, 6, '瓶', '6', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:20', '1', '2023-12-05 23:03:20', b'0');
INSERT INTO `system_dict_data` VALUES (1467, 7, '盒', '7', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:30', '1', '2023-12-05 23:03:30', b'0');
INSERT INTO `system_dict_data` VALUES (1468, 8, '台', '8', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:41', '1', '2023-12-05 23:03:41', b'0');
INSERT INTO `system_dict_data` VALUES (1469, 9, '吨', '9', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:48', '1', '2023-12-05 23:03:48', b'0');
INSERT INTO `system_dict_data` VALUES (1470, 10, '千克', '10', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:03', '1', '2023-12-05 23:04:03', b'0');
INSERT INTO `system_dict_data` VALUES (1471, 11, '米', '11', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:12', '1', '2023-12-05 23:04:12', b'0');
INSERT INTO `system_dict_data` VALUES (1472, 12, '箱', '12', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:25', '1', '2023-12-05 23:04:25', b'0');
INSERT INTO `system_dict_data` VALUES (1473, 13, '套', '13', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:34', '1', '2023-12-05 23:04:34', b'0');
INSERT INTO `system_dict_data` VALUES (1474, 1, '打电话', '1', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:48:20', '1', '2024-01-15 20:48:20', b'0');
INSERT INTO `system_dict_data` VALUES (1475, 2, '发短信', '2', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:48:31', '1', '2024-01-15 20:48:31', b'0');
INSERT INTO `system_dict_data` VALUES (1476, 3, '上门拜访', '3', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:49:07', '1', '2024-01-15 20:49:07', b'0');
INSERT INTO `system_dict_data` VALUES (1477, 4, '微信沟通', '4', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:49:15', '1', '2024-01-15 20:49:15', b'0');
INSERT INTO `system_dict_data` VALUES (1478, 4, '钱包余额', '4', 'pay_transfer_type', 0, 'info', '', '', '1', '2023-10-28 16:28:37', '1', '2023-10-28 16:28:37', b'0');
INSERT INTO `system_dict_data` VALUES (1479, 3, '银行卡', '3', 'pay_transfer_type', 0, 'default', '', '', '1', '2023-10-28 16:28:21', '1', '2023-10-28 16:28:21', b'0');
INSERT INTO `system_dict_data` VALUES (1480, 2, '微信余额', '2', 'pay_transfer_type', 0, 'info', '', '', '1', '2023-10-28 16:28:07', '1', '2023-10-28 16:28:07', b'0');
INSERT INTO `system_dict_data` VALUES (1481, 1, '支付宝余额', '1', 'pay_transfer_type', 0, 'default', '', '', '1', '2023-10-28 16:27:44', '1', '2023-10-28 16:27:44', b'0');
INSERT INTO `system_dict_data` VALUES (1482, 4, '转账失败', '30', 'pay_transfer_status', 0, 'warning', '', '', '1', '2023-10-28 16:24:16', '1', '2023-10-28 16:24:16', b'0');
INSERT INTO `system_dict_data` VALUES (1483, 3, '转账成功', '20', 'pay_transfer_status', 0, 'success', '', '', '1', '2023-10-28 16:23:50', '1', '2023-10-28 16:23:50', b'0');
INSERT INTO `system_dict_data` VALUES (1484, 2, '转账进行中', '10', 'pay_transfer_status', 0, 'info', '', '', '1', '2023-10-28 16:23:12', '1', '2023-10-28 16:23:12', b'0');
INSERT INTO `system_dict_data` VALUES (1485, 1, '等待转账', '0', 'pay_transfer_status', 0, 'default', '', '', '1', '2023-10-28 16:21:43', '1', '2023-10-28 16:23:22', b'0');
INSERT INTO `system_dict_data` VALUES (1486, 10, '其它入库', '10', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-05 18:07:25', '1', '2024-02-05 18:07:43', b'0');
INSERT INTO `system_dict_data` VALUES (1487, 11, '其它入库（作废）', '11', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-05 18:08:07', '1', '2024-02-05 19:20:16', b'0');
INSERT INTO `system_dict_data` VALUES (1488, 20, '其它出库', '20', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-05 18:08:51', '1', '2024-02-05 18:08:51', b'0');
INSERT INTO `system_dict_data` VALUES (1489, 21, '其它出库（作废）', '21', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-05 18:09:00', '1', '2024-02-05 19:20:10', b'0');
INSERT INTO `system_dict_data` VALUES (1490, 10, '未审核', '10', 'erp_audit_status', 0, 'default', '', '', '1', '2024-02-06 00:00:21', '1', '2024-02-06 00:00:21', b'0');
INSERT INTO `system_dict_data` VALUES (1491, 20, '已审核', '20', 'erp_audit_status', 0, 'success', '', '', '1', '2024-02-06 00:00:35', '1', '2024-02-06 00:00:35', b'0');
INSERT INTO `system_dict_data` VALUES (1492, 30, '调拨入库', '30', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-07 20:34:19', '1', '2024-02-07 12:36:31', b'0');
INSERT INTO `system_dict_data` VALUES (1493, 31, '调拨入库（作废）', '31', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-07 20:34:29', '1', '2024-02-07 20:37:11', b'0');
INSERT INTO `system_dict_data` VALUES (1494, 32, '调拨出库', '32', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-07 20:34:38', '1', '2024-02-07 12:36:33', b'0');
INSERT INTO `system_dict_data` VALUES (1495, 33, '调拨出库（作废）', '33', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-07 20:34:49', '1', '2024-02-07 20:37:06', b'0');
INSERT INTO `system_dict_data` VALUES (1496, 40, '盘盈入库', '40', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-08 08:53:00', '1', '2024-02-08 08:53:09', b'0');
INSERT INTO `system_dict_data` VALUES (1497, 41, '盘盈入库（作废）', '41', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-08 08:53:39', '1', '2024-02-16 19:40:54', b'0');
INSERT INTO `system_dict_data` VALUES (1498, 42, '盘亏出库', '42', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-08 08:54:16', '1', '2024-02-08 08:54:16', b'0');
INSERT INTO `system_dict_data` VALUES (1499, 43, '盘亏出库（作废）', '43', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-08 08:54:31', '1', '2024-02-16 19:40:46', b'0');
INSERT INTO `system_dict_data` VALUES (1500, 50, '销售出库', '50', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-11 21:47:25', '1', '2024-02-11 21:50:40', b'0');
INSERT INTO `system_dict_data` VALUES (1501, 51, '销售出库（作废）', '51', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-11 21:47:37', '1', '2024-02-11 21:51:12', b'0');
INSERT INTO `system_dict_data` VALUES (1502, 60, '销售退货入库', '60', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-12 06:51:05', '1', '2024-02-12 06:51:05', b'0');
INSERT INTO `system_dict_data` VALUES (1503, 61, '销售退货入库（作废）', '61', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-12 06:51:18', '1', '2024-02-12 06:51:18', b'0');
INSERT INTO `system_dict_data` VALUES (1504, 70, '采购入库', '70', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-16 13:10:02', '1', '2024-02-16 13:10:02', b'0');
INSERT INTO `system_dict_data` VALUES (1505, 71, '采购入库（作废）', '71', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-16 13:10:10', '1', '2024-02-16 19:40:40', b'0');
INSERT INTO `system_dict_data` VALUES (1506, 80, '采购退货出库', '80', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-16 13:10:17', '1', '2024-02-16 13:10:17', b'0');
INSERT INTO `system_dict_data` VALUES (1507, 81, '采购退货出库（作废）', '81', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-16 13:10:26', '1', '2024-02-16 19:40:33', b'0');
INSERT INTO `system_dict_data` VALUES (1509, 3, '审批不通过', '3', 'bpm_process_instance_status', 0, 'danger', '', '', '1', '2024-03-16 16:12:06', '1', '2024-03-16 16:12:06', b'0');
INSERT INTO `system_dict_data` VALUES (1510, 4, '已取消', '4', 'bpm_process_instance_status', 0, 'warning', '', '', '1', '2024-03-16 16:12:22', '1', '2024-03-16 16:12:22', b'0');
INSERT INTO `system_dict_data` VALUES (1511, 5, '已退回', '5', 'bpm_task_status', 0, 'warning', '', '', '1', '2024-03-16 19:10:46', '1', '2024-03-08 22:41:40', b'0');
INSERT INTO `system_dict_data` VALUES (1512, 6, '委派中', '6', 'bpm_task_status', 0, 'primary', '', '', '1', '2024-03-17 10:06:22', '1', '2024-03-08 22:41:40', b'0');
INSERT INTO `system_dict_data` VALUES (1513, 7, '审批通过中', '7', 'bpm_task_status', 0, 'success', '', '', '1', '2024-03-17 10:06:47', '1', '2024-03-08 22:41:41', b'0');
INSERT INTO `system_dict_data` VALUES (1514, 0, '待审批', '0', 'bpm_task_status', 0, 'info', '', '', '1', '2024-03-17 10:07:11', '1', '2024-03-08 22:41:42', b'0');
INSERT INTO `system_dict_data` VALUES (1515, 35, '发起人自选', '35', 'bpm_task_candidate_strategy', 0, '', '', '', '1', '2024-03-22 19:45:16', '1', '2024-03-22 19:45:16', b'0');
INSERT INTO `system_dict_data` VALUES (1516, 1, '执行监听器', 'execution', 'bpm_process_listener_type', 0, 'primary', '', '', '1', '2024-03-23 12:54:03', '1', '2024-03-23 19:14:19', b'0');
INSERT INTO `system_dict_data` VALUES (1517, 1, '任务监听器', 'task', 'bpm_process_listener_type', 0, 'success', '', '', '1', '2024-03-23 12:54:13', '1', '2024-03-23 19:14:24', b'0');
INSERT INTO `system_dict_data` VALUES (1526, 1, 'Java 类', 'class', 'bpm_process_listener_value_type', 0, 'primary', '', '', '1', '2024-03-23 15:08:45', '1', '2024-03-23 19:14:32', b'0');
INSERT INTO `system_dict_data` VALUES (1527, 2, '表达式', 'expression', 'bpm_process_listener_value_type', 0, 'success', '', '', '1', '2024-03-23 15:09:06', '1', '2024-03-23 19:14:38', b'0');
INSERT INTO `system_dict_data` VALUES (1528, 3, '代理表达式', 'delegateExpression', 'bpm_process_listener_value_type', 0, 'info', '', '', '1', '2024-03-23 15:11:23', '1', '2024-03-23 19:14:41', b'0');
INSERT INTO `system_dict_data` VALUES (1529, 1, '天', '1', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:50:26', '1', '2024-03-29 22:50:26', b'0');
INSERT INTO `system_dict_data` VALUES (1530, 2, '周', '2', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:50:36', '1', '2024-03-29 22:50:36', b'0');
INSERT INTO `system_dict_data` VALUES (1531, 3, '月', '3', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:50:46', '1', '2024-03-29 22:50:54', b'0');
INSERT INTO `system_dict_data` VALUES (1532, 4, '季度', '4', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:51:01', '1', '2024-03-29 22:51:01', b'0');
INSERT INTO `system_dict_data` VALUES (1533, 5, '年', '5', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:51:07', '1', '2024-03-29 22:51:07', b'0');
INSERT INTO `system_dict_data` VALUES (1534, 1, '赢单', '1', 'crm_business_end_status_type', 0, 'success', '', '', '1', '2024-04-13 23:26:57', '1', '2024-04-13 23:26:57', b'0');
INSERT INTO `system_dict_data` VALUES (1535, 2, '输单', '2', 'crm_business_end_status_type', 0, 'primary', '', '', '1', '2024-04-13 23:27:31', '1', '2024-04-13 23:27:31', b'0');
INSERT INTO `system_dict_data` VALUES (1536, 3, '无效', '3', 'crm_business_end_status_type', 0, 'info', '', '', '1', '2024-04-13 23:27:59', '1', '2024-04-13 23:27:59', b'0');
INSERT INTO `system_dict_data` VALUES (1537, 1, 'OpenAI', 'OpenAI', 'ai_platform', 0, '', '', '', '1', '2024-05-09 22:33:47', '1', '2024-05-09 22:58:46', b'0');
INSERT INTO `system_dict_data` VALUES (1538, 2, 'Ollama', 'Ollama', 'ai_platform', 0, '', '', '', '1', '2024-05-17 23:02:55', '1', '2024-05-17 23:02:55', b'0');
INSERT INTO `system_dict_data` VALUES (1539, 3, '文心一言', 'YiYan', 'ai_platform', 0, '', '', '', '1', '2024-05-18 09:24:20', '1', '2024-05-18 09:29:01', b'0');
INSERT INTO `system_dict_data` VALUES (1540, 4, '讯飞星火', 'XingHuo', 'ai_platform', 0, '', '', '', '1', '2024-05-18 10:08:56', '1', '2024-05-18 10:08:56', b'0');
INSERT INTO `system_dict_data` VALUES (1541, 5, '通义千问', 'TongYi', 'ai_platform', 0, '', '', '', '1', '2024-05-18 10:32:29', '1', '2024-07-06 15:42:29', b'0');
INSERT INTO `system_dict_data` VALUES (1542, 6, 'StableDiffusion', 'StableDiffusion', 'ai_platform', 0, '', '', '', '1', '2024-06-01 15:09:31', '1', '2024-06-01 15:10:25', b'0');
INSERT INTO `system_dict_data` VALUES (1543, 10, '进行中', '10', 'ai_image_status', 0, 'primary', '', '', '1', '2024-06-26 20:51:41', '1', '2024-06-26 20:52:48', b'0');
INSERT INTO `system_dict_data` VALUES (1544, 20, '已完成', '20', 'ai_image_status', 0, 'success', '', '', '1', '2024-06-26 20:52:07', '1', '2024-06-26 20:52:41', b'0');
INSERT INTO `system_dict_data` VALUES (1545, 30, '已失败', '30', 'ai_image_status', 0, 'warning', '', '', '1', '2024-06-26 20:52:25', '1', '2024-06-26 20:52:35', b'0');
INSERT INTO `system_dict_data` VALUES (1546, 7, 'Midjourney', 'Midjourney', 'ai_platform', 0, '', '', '', '1', '2024-06-26 22:14:46', '1', '2024-06-26 22:14:46', b'0');
INSERT INTO `system_dict_data` VALUES (1547, 10, '进行中', '10', 'ai_music_status', 0, 'primary', '', '', '1', '2024-06-27 22:45:22', '1', '2024-06-28 00:56:17', b'0');
INSERT INTO `system_dict_data` VALUES (1548, 20, '已完成', '20', 'ai_music_status', 0, 'success', '', '', '1', '2024-06-27 22:45:33', '1', '2024-06-28 00:56:18', b'0');
INSERT INTO `system_dict_data` VALUES (1549, 30, '已失败', '30', 'ai_music_status', 0, 'danger', '', '', '1', '2024-06-27 22:45:44', '1', '2024-06-28 00:56:19', b'0');
INSERT INTO `system_dict_data` VALUES (1550, 1, '歌词模式', '1', 'ai_generate_mode', 0, '', '', '', '1', '2024-06-27 22:46:31', '1', '2024-06-28 01:22:25', b'0');
INSERT INTO `system_dict_data` VALUES (1551, 2, '描述模式', '2', 'ai_generate_mode', 0, '', '', '', '1', '2024-06-27 22:46:37', '1', '2024-06-28 01:22:24', b'0');
INSERT INTO `system_dict_data` VALUES (1552, 8, 'Suno', 'Suno', 'ai_platform', 0, '', '', '', '1', '2024-06-29 09:13:36', '1', '2024-06-29 09:13:41', b'0');
INSERT INTO `system_dict_data` VALUES (1553, 9, 'DeepSeek', 'DeepSeek', 'ai_platform', 0, '', '', '', '1', '2024-07-06 12:04:30', '1', '2024-07-06 12:05:20', b'0');
INSERT INTO `system_dict_data` VALUES (1554, 10, '智谱', 'ZhiPu', 'ai_platform', 0, '', '', '', '1', '2024-07-06 18:00:35', '1', '2024-07-06 18:00:35', b'0');
INSERT INTO `system_dict_data` VALUES (1555, 4, '长', '4', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:49:03', '1', '2024-07-07 15:49:03', b'0');
INSERT INTO `system_dict_data` VALUES (1556, 5, '段落', '5', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:49:54', '1', '2024-07-07 15:49:54', b'0');
INSERT INTO `system_dict_data` VALUES (1557, 6, '文章', '6', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:50:05', '1', '2024-07-07 15:50:05', b'0');
INSERT INTO `system_dict_data` VALUES (1558, 7, '博客文章', '7', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:50:23', '1', '2024-07-07 15:50:23', b'0');
INSERT INTO `system_dict_data` VALUES (1559, 8, '想法', '8', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:50:31', '1', '2024-07-07 15:50:31', b'0');
INSERT INTO `system_dict_data` VALUES (1560, 9, '大纲', '9', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:50:37', '1', '2024-07-07 15:50:37', b'0');
INSERT INTO `system_dict_data` VALUES (1561, 1, '自动', '1', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:06', '1', '2024-07-07 15:51:06', b'0');
INSERT INTO `system_dict_data` VALUES (1562, 2, '友善', '2', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:19', '1', '2024-07-07 15:51:19', b'0');
INSERT INTO `system_dict_data` VALUES (1563, 3, '随意', '3', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:27', '1', '2024-07-07 15:51:27', b'0');
INSERT INTO `system_dict_data` VALUES (1564, 4, '友好', '4', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:37', '1', '2024-07-07 15:51:37', b'0');
INSERT INTO `system_dict_data` VALUES (1565, 5, '专业', '5', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:49', '1', '2024-07-07 15:52:02', b'0');
INSERT INTO `system_dict_data` VALUES (1566, 6, '诙谐', '6', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:52:15', '1', '2024-07-07 15:52:15', b'0');
INSERT INTO `system_dict_data` VALUES (1567, 7, '有趣', '7', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:52:24', '1', '2024-07-07 15:52:24', b'0');
INSERT INTO `system_dict_data` VALUES (1568, 8, '正式', '8', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:54:33', '1', '2024-07-07 15:54:33', b'0');
INSERT INTO `system_dict_data` VALUES (1569, 5, '段落', '5', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:49:54', '1', '2024-07-07 15:49:54', b'0');
INSERT INTO `system_dict_data` VALUES (1570, 1, '自动', '1', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:19:34', '1', '2024-07-07 15:19:34', b'0');
INSERT INTO `system_dict_data` VALUES (1571, 2, '电子邮件', '2', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:19:50', '1', '2024-07-07 15:49:30', b'0');
INSERT INTO `system_dict_data` VALUES (1572, 3, '消息', '3', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:20:01', '1', '2024-07-07 15:49:38', b'0');
INSERT INTO `system_dict_data` VALUES (1573, 4, '评论', '4', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:20:13', '1', '2024-07-07 15:49:45', b'0');
INSERT INTO `system_dict_data` VALUES (1574, 1, '自动', '1', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:44:18', '1', '2024-07-07 15:44:18', b'0');
INSERT INTO `system_dict_data` VALUES (1575, 2, '中文', '2', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:44:28', '1', '2024-07-07 15:44:28', b'0');
INSERT INTO `system_dict_data` VALUES (1576, 3, '英文', '3', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:44:37', '1', '2024-07-07 15:44:37', b'0');
INSERT INTO `system_dict_data` VALUES (1577, 4, '韩语', '4', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:46:28', '1', '2024-07-07 15:46:28', b'0');
INSERT INTO `system_dict_data` VALUES (1578, 5, '日语', '5', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:46:44', '1', '2024-07-07 15:46:44', b'0');
INSERT INTO `system_dict_data` VALUES (1579, 1, '自动', '1', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:48:34', '1', '2024-07-07 15:48:34', b'0');
INSERT INTO `system_dict_data` VALUES (1580, 2, '短', '2', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:48:44', '1', '2024-07-07 15:48:44', b'0');
INSERT INTO `system_dict_data` VALUES (1581, 3, '中等', '3', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:48:52', '1', '2024-07-07 15:48:52', b'0');
INSERT INTO `system_dict_data` VALUES (1582, 4, '长', '4', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:49:03', '1', '2024-07-07 15:49:03', b'0');
INSERT INTO `system_dict_data` VALUES (1584, 1, '撰写', '1', 'ai_write_type', 0, '', '', '', '1', '2024-07-10 21:26:00', '1', '2024-07-10 21:26:00', b'0');
INSERT INTO `system_dict_data` VALUES (1585, 2, '回复', '2', 'ai_write_type', 0, '', '', '', '1', '2024-07-10 21:26:06', '1', '2024-07-10 21:26:06', b'0');
INSERT INTO `system_dict_data` VALUES (1586, 2, '腾讯云', 'TENCENT', 'system_sms_channel_code', 0, '', '', '', '1', '2024-07-22 22:23:16', '1', '2024-07-22 22:23:16', b'0');
INSERT INTO `system_dict_data` VALUES (1587, 3, '华为云', 'HUAWEI', 'system_sms_channel_code', 0, '', '', '', '1', '2024-07-22 22:23:46', '1', '2024-07-22 22:23:53', b'0');
INSERT INTO `system_dict_data` VALUES (1588, 1, 'OpenAI 微软', 'AzureOpenAI', 'ai_platform', 0, '', '', '', '1', '2024-08-10 14:07:41', '1', '2024-08-10 14:07:41', b'0');
INSERT INTO `system_dict_data` VALUES (1589, 10, 'BPMN 设计器', '10', 'bpm_model_type', 0, 'primary', '', '', '1', '2024-08-26 15:22:17', '1', '2024-08-26 16:46:02', b'0');
INSERT INTO `system_dict_data` VALUES (1590, 20, 'SIMPLE 设计器', '20', 'bpm_model_type', 0, 'success', '', '', '1', '2024-08-26 15:22:27', '1', '2024-08-26 16:45:58', b'0');
INSERT INTO `system_dict_data` VALUES (1591, 4, '七牛云', 'QINIU', 'system_sms_channel_code', 0, '', '', '', '1', '2024-08-31 08:45:03', '1', '2024-08-31 08:45:24', b'0');
INSERT INTO `system_dict_data` VALUES (1592, 3, '新人券', '3', 'promotion_coupon_take_type', 0, 'info', '', '新人注册后，自动发放', '1', '2024-09-03 11:57:16', '1', '2024-09-03 11:57:28', b'0');
INSERT INTO `system_dict_data` VALUES (1593, 5, '微信零钱', '5', 'brokerage_withdraw_type', 0, '', '', '自动打款', '1', '2024-10-13 11:06:48', '1', '2024-10-13 11:06:59', b'0');
INSERT INTO `system_dict_data` VALUES (1655, 0, '标准数据格式（JSON）', '0', 'iot_data_format', 0, 'default', '', '', '1', '2024-08-10 11:53:26', '1', '2024-09-06 14:31:02', b'0');
INSERT INTO `system_dict_data` VALUES (1656, 1, '透传/自定义', '1', 'iot_data_format', 0, 'default', '', '', '1', '2024-08-10 11:53:37', '1', '2024-09-06 14:30:54', b'0');
INSERT INTO `system_dict_data` VALUES (1657, 0, '直连设备', '0', 'iot_product_device_type', 0, 'default', '', '', '1', '2024-08-10 11:54:58', '1', '2024-09-06 21:57:01', b'0');
INSERT INTO `system_dict_data` VALUES (1658, 2, '网关设备', '2', 'iot_product_device_type', 0, 'default', '', '', '1', '2024-08-10 11:55:08', '1', '2024-09-06 21:56:46', b'0');
INSERT INTO `system_dict_data` VALUES (1659, 1, '网关子设备', '1', 'iot_product_device_type', 0, 'default', '', '', '1', '2024-08-10 11:55:20', '1', '2024-09-06 21:57:10', b'0');
INSERT INTO `system_dict_data` VALUES (1661, 1, '已发布', '1', 'iot_product_status', 0, 'success', '', '', '1', '2024-08-10 12:10:33', '1', '2024-09-06 22:06:22', b'0');
INSERT INTO `system_dict_data` VALUES (1663, 0, '开发中', '0', 'iot_product_status', 0, 'default', '', '', '1', '2024-08-10 14:19:18', '1', '2024-09-07 10:58:07', b'0');
INSERT INTO `system_dict_data` VALUES (1665, 0, '弱校验', '0', 'iot_validate_type', 0, '', '', '', '1', '2024-09-06 20:05:48', '1', '2024-09-06 22:02:44', b'0');
INSERT INTO `system_dict_data` VALUES (1666, 1, '免校验', '1', 'iot_validate_type', 0, '', '', '', '1', '2024-09-06 20:06:03', '1', '2024-09-06 22:02:51', b'0');
INSERT INTO `system_dict_data` VALUES (1667, 0, 'Wi-Fi', '0', 'iot_net_type', 0, '', '', '', '1', '2024-09-06 22:04:47', '1', '2024-09-06 22:04:47', b'0');
INSERT INTO `system_dict_data` VALUES (1668, 1, '蜂窝（2G / 3G / 4G / 5G）', '1', 'iot_net_type', 0, '', '', '', '1', '2024-09-06 22:05:14', '1', '2024-09-06 22:05:14', b'0');
INSERT INTO `system_dict_data` VALUES (1669, 2, '以太网', '2', 'iot_net_type', 0, '', '', '', '1', '2024-09-06 22:05:35', '1', '2024-09-06 22:05:35', b'0');
INSERT INTO `system_dict_data` VALUES (1670, 3, '其他', '3', 'iot_net_type', 0, '', '', '', '1', '2024-09-06 22:05:52', '1', '2024-09-06 22:05:52', b'0');
INSERT INTO `system_dict_data` VALUES (1671, 0, '自定义', '0', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:10', '1', '2024-09-06 22:26:10', b'0');
INSERT INTO `system_dict_data` VALUES (1672, 1, 'Modbus', '1', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:21', '1', '2024-09-06 22:26:21', b'0');
INSERT INTO `system_dict_data` VALUES (1673, 2, 'OPC UA', '2', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:31', '1', '2024-09-06 22:26:31', b'0');
INSERT INTO `system_dict_data` VALUES (1674, 3, 'ZigBee', '3', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:39', '1', '2024-09-06 22:26:39', b'0');
INSERT INTO `system_dict_data` VALUES (1675, 4, 'BLE', '4', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:48', '1', '2024-09-06 22:26:48', b'0');
INSERT INTO `system_dict_data` VALUES (1676, 0, '未激活', '0', 'iot_device_status', 0, '', '', '', '1', '2024-09-21 08:13:34', '1', '2024-09-21 08:13:34', b'0');
INSERT INTO `system_dict_data` VALUES (1677, 1, '在线', '1', 'iot_device_status', 0, '', '', '', '1', '2024-09-21 08:13:48', '1', '2024-09-21 08:13:48', b'0');
INSERT INTO `system_dict_data` VALUES (1678, 2, '离线', '2', 'iot_device_status', 0, '', '', '', '1', '2024-09-21 08:13:59', '1', '2024-09-21 08:13:59', b'0');
INSERT INTO `system_dict_data` VALUES (1679, 3, '已禁用', '3', 'iot_device_status', 0, '', '', '', '1', '2024-09-21 08:14:13', '1', '2024-09-21 08:14:13', b'0');
INSERT INTO `system_dict_data` VALUES (1680, 1, '属性', '1', 'iot_product_function_type', 0, '', '', '', '1', '2024-09-29 20:03:01', '1', '2024-09-29 20:09:41', b'0');
INSERT INTO `system_dict_data` VALUES (1681, 2, '服务', '2', 'iot_product_function_type', 0, '', '', '', '1', '2024-09-29 20:03:11', '1', '2024-09-29 20:08:23', b'0');
INSERT INTO `system_dict_data` VALUES (1682, 3, '事件', '3', 'iot_product_function_type', 0, '', '', '', '1', '2024-09-29 20:03:20', '1', '2024-09-29 20:08:20', b'0');
INSERT INTO `system_dict_data` VALUES (1683, 2, '蓝牙mac', '2', 'eiot_dn_typ', 0, '', '', '', '1', '2025-01-07 11:57:17', '1', '2025-01-07 11:57:17', b'0');
INSERT INTO `system_dict_data` VALUES (1684, 0, '序列号', '1', 'eiot_dn_typ', 0, '', '', '', '1', '2025-01-07 11:53:10', '1', '2025-01-07 11:56:46', b'0');
INSERT INTO `system_dict_data` VALUES (1685, 2, 'GB28181', 'GB28181', 'eiot_protocol_code', 0, '', '', '', '1', '2025-01-07 11:10:14', '1', '2025-01-07 11:10:14', b'0');
INSERT INTO `system_dict_data` VALUES (1686, 0, '无', 'N', 'eiot_mcu_code', 0, '', '', '', '1', '2025-01-07 11:09:49', '1', '2025-01-07 11:09:49', b'0');
INSERT INTO `system_dict_data` VALUES (1687, 9, '非联网设备', '9', 'eiot_node_type', 0, '', '', '', '1', '2025-01-03 14:53:51', '1', '2025-01-03 14:53:51', b'0');
INSERT INTO `system_dict_data` VALUES (1688, 1, '报警控制器', '117', 'channel_type', 0, '', '', '', '1', '2025-01-03 11:46:27', '1', '2025-01-03 11:46:27', b'0');
INSERT INTO `system_dict_data` VALUES (1689, 0, 'IPC', '132', 'channel_type', 0, '', '', '', '1', '2025-01-03 11:46:12', '1', '2025-01-03 11:46:12', b'0');
INSERT INTO `system_dict_data` VALUES (1690, 5, 'IPC', '132', 'video_type', 0, '', '', '', '1', '2025-01-03 11:31:35', '1', '2025-01-03 11:31:35', b'0');
INSERT INTO `system_dict_data` VALUES (1691, 4, '摄像机', '131', 'video_type', 0, '', '', '', '1', '2025-01-03 11:31:20', '1', '2025-01-03 11:31:20', b'0');
INSERT INTO `system_dict_data` VALUES (1692, 4, '报警控制器', '117', 'video_type', 0, '', '', '', '1', '2025-01-03 11:30:26', '1', '2025-01-03 11:30:26', b'0');
INSERT INTO `system_dict_data` VALUES (1693, 1, 'NVR', '118', 'video_type', 0, '', '', '', '1', '2025-01-03 11:30:07', '1', '2025-01-03 11:30:42', b'0');
INSERT INTO `system_dict_data` VALUES (1694, 0, 'DVR', '111', 'video_type', 0, '', '', '', '1', '2025-01-03 11:29:57', '1', '2025-01-03 11:30:34', b'0');
INSERT INTO `system_dict_data` VALUES (1695, 2, '在线', '2', 'sip_gen_status', 0, '', '', '', '1', '2025-01-03 11:22:46', '1', '2025-01-03 11:29:12', b'0');
INSERT INTO `system_dict_data` VALUES (1696, 1, '未使用', '1', 'sip_gen_status', 0, '', '', '', '1', '2025-01-03 11:22:34', '1', '2025-01-03 11:28:41', b'0');
INSERT INTO `system_dict_data` VALUES (1697, 1, '是', '1', 'common_boolean_int', 0, '', '', '', '1', '2025-02-10 14:33:26', '1', '2025-02-10 14:33:32', b'0');
INSERT INTO `system_dict_data` VALUES (1698, 2, '否', '0', 'common_boolean_int', 0, '', '', '', '1', '2025-02-10 14:33:38', '1', '2025-02-10 14:33:38', b'0');
INSERT INTO `system_dict_data` VALUES (1699, 0, '无', 'N', 'eiot_protocol_code', 0, '', '', '', '1', '2025-02-16 18:14:37', '1', '2025-02-16 18:14:37', b'0');
INSERT INTO `system_dict_data` VALUES (1706, 3, '监控设备', '3', 'eiot_node_type', 0, '', '', '', '1', '2024-12-05 12:14:39', '1', '2025-01-03 14:53:31', b'0');
INSERT INTO `system_dict_data` VALUES (1707, 2, '直连设备', '2', 'eiot_node_type', 0, '', '', '', '1', '2024-12-05 12:13:08', '1', '2024-12-05 12:14:18', b'0');
INSERT INTO `system_dict_data` VALUES (1708, 1, '网关子设备', '1', 'eiot_node_type', 0, '', '', '', '1', '2024-12-05 12:12:52', '1', '2024-12-05 12:14:06', b'0');
INSERT INTO `system_dict_data` VALUES (1709, 0, '网关', '0', 'eiot_node_type', 0, '', '', '', '1', '2024-12-05 12:12:34', '1', '2024-12-05 12:13:56', b'0');
INSERT INTO `system_dict_data` VALUES (1710, 1, 'Modbus Tcp', 'modbus-tcp', 'eiot_protocol_code', 0, '', '', '', '1', '2025-03-21 00:23:29', '1', '2025-03-21 00:23:41', b'0');


-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
                                     `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典名称',
                                     `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
                                     `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
                                     `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `deleted_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 650 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_dict_type
-- ----------------------------
INSERT INTO `system_dict_type` VALUES (1, '用户性别', 'system_user_sex', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2022-05-16 20:29:32', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (6, '参数类型', 'infra_config_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:36:54', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (7, '通知类型', 'system_notice_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:35:26', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (9, '操作类型', 'infra_operate_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:01', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (10, '系统状态', 'common_status', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:21:28', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (11, 'Boolean 是否类型', 'infra_boolean_string', 0, 'boolean 转是否', '', '2021-01-19 03:20:08', '', '2022-02-01 16:37:10', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (104, '登陆结果', 'system_login_result', 0, '登陆结果', '', '2021-01-18 06:17:11', '', '2022-02-01 16:36:00', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (106, '代码生成模板类型', 'infra_codegen_template_type', 0, NULL, '', '2021-02-05 07:08:06', '1', '2022-05-16 20:26:50', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (107, '定时任务状态', 'infra_job_status', 0, NULL, '', '2021-02-07 07:44:16', '', '2022-02-01 16:51:11', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (108, '定时任务日志状态', 'infra_job_log_status', 0, NULL, '', '2021-02-08 10:03:51', '', '2022-02-01 16:50:43', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (109, '用户类型', 'user_type', 0, NULL, '', '2021-02-26 00:15:51', '', '2021-02-26 00:15:51', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (110, 'API 异常数据的处理状态', 'infra_api_error_log_process_status', 0, NULL, '', '2021-02-26 07:07:01', '', '2022-02-01 16:50:53', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (111, '短信渠道编码', 'system_sms_channel_code', 0, NULL, '1', '2021-04-05 01:04:50', '1', '2022-02-16 02:09:08', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (112, '短信模板的类型', 'system_sms_template_type', 0, NULL, '1', '2021-04-05 21:50:43', '1', '2022-02-01 16:35:06', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (113, '短信发送状态', 'system_sms_send_status', 0, NULL, '1', '2021-04-11 20:18:03', '1', '2022-02-01 16:35:09', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (114, '短信接收状态', 'system_sms_receive_status', 0, NULL, '1', '2021-04-11 20:27:14', '1', '2022-02-01 16:35:14', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (116, '登陆日志的类型', 'system_login_type', 0, '登陆日志的类型', '1', '2021-10-06 00:50:46', '1', '2022-02-01 16:35:56', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (117, 'OA 请假类型', 'bpm_oa_leave_type', 0, NULL, '1', '2021-09-21 22:34:33', '1', '2022-01-22 10:41:37', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (130, '支付渠道编码类型', 'pay_channel_code', 0, '支付渠道的编码', '1', '2021-12-03 10:35:08', '1', '2023-07-10 10:11:39', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (131, '支付回调状态', 'pay_notify_status', 0, '支付回调状态（包括退款回调）', '1', '2021-12-03 10:53:29', '1', '2023-07-19 18:09:43', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (132, '支付订单状态', 'pay_order_status', 0, '支付订单状态', '1', '2021-12-03 11:17:50', '1', '2021-12-03 11:17:50', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (134, '退款订单状态', 'pay_refund_status', 0, '退款订单状态', '1', '2021-12-10 16:42:50', '1', '2023-07-19 10:13:17', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (139, '流程实例的状态', 'bpm_process_instance_status', 0, '流程实例的状态', '1', '2022-01-07 23:46:42', '1', '2022-01-07 23:46:42', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (140, '流程实例的结果', 'bpm_task_status', 0, '流程实例的结果', '1', '2022-01-07 23:48:10', '1', '2024-03-08 22:42:03', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (141, '流程的表单类型', 'bpm_model_form_type', 0, '流程的表单类型', '103', '2022-01-11 23:50:45', '103', '2022-01-11 23:50:45', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (142, '任务分配规则的类型', 'bpm_task_candidate_strategy', 0, 'BPM 任务的候选人的策略', '103', '2022-01-12 23:21:04', '103', '2024-03-06 02:53:59', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (144, '代码生成的场景枚举', 'infra_codegen_scene', 0, '代码生成的场景枚举', '1', '2022-02-02 13:14:45', '1', '2022-03-10 16:33:46', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (145, '角色类型', 'system_role_type', 0, '角色类型', '1', '2022-02-16 13:01:46', '1', '2022-02-16 13:01:46', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (146, '文件存储器', 'infra_file_storage', 0, '文件存储器', '1', '2022-03-15 00:24:38', '1', '2022-03-15 00:24:38', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (147, 'OAuth 2.0 授权类型', 'system_oauth2_grant_type', 0, 'OAuth 2.0 授权类型（模式）', '1', '2022-05-12 00:20:52', '1', '2022-05-11 16:25:49', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (149, '商品 SPU 状态', 'product_spu_status', 0, '商品 SPU 状态', '1', '2022-10-24 21:19:04', '1', '2022-10-24 21:19:08', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (150, '优惠类型', 'promotion_discount_type', 0, '优惠类型', '1', '2022-11-01 12:46:06', '1', '2022-11-01 12:46:06', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (151, '优惠劵模板的有限期类型', 'promotion_coupon_template_validity_type', 0, '优惠劵模板的有限期类型', '1', '2022-11-02 00:06:20', '1', '2022-11-04 00:08:26', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (152, '营销的商品范围', 'promotion_product_scope', 0, '营销的商品范围', '1', '2022-11-02 00:28:01', '1', '2022-11-02 00:28:01', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (153, '优惠劵的状态', 'promotion_coupon_status', 0, '优惠劵的状态', '1', '2022-11-04 00:14:49', '1', '2022-11-04 00:14:49', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (154, '优惠劵的领取方式', 'promotion_coupon_take_type', 0, '优惠劵的领取方式', '1', '2022-11-04 19:12:27', '1', '2022-11-04 19:12:27', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (155, '促销活动的状态', 'promotion_activity_status', 0, '促销活动的状态', '1', '2022-11-04 22:54:23', '1', '2022-11-04 22:54:23', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (156, '营销的条件类型', 'promotion_condition_type', 0, '营销的条件类型', '1', '2022-11-04 22:59:23', '1', '2022-11-04 22:59:23', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (157, '交易售后状态', 'trade_after_sale_status', 0, '交易售后状态', '1', '2022-11-19 20:52:56', '1', '2022-11-19 20:52:56', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (158, '交易售后的类型', 'trade_after_sale_type', 0, '交易售后的类型', '1', '2022-11-19 21:04:09', '1', '2022-11-19 21:04:09', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (159, '交易售后的方式', 'trade_after_sale_way', 0, '交易售后的方式', '1', '2022-11-19 21:39:04', '1', '2022-11-19 21:39:04', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (160, '终端', 'terminal', 0, '终端', '1', '2022-12-10 10:50:50', '1', '2022-12-10 10:53:11', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (161, '交易订单的类型', 'trade_order_type', 0, '交易订单的类型', '1', '2022-12-10 16:33:54', '1', '2022-12-10 16:33:54', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (162, '交易订单的状态', 'trade_order_status', 0, '交易订单的状态', '1', '2022-12-10 16:48:44', '1', '2022-12-10 16:48:44', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (163, '交易订单项的售后状态', 'trade_order_item_after_sale_status', 0, '交易订单项的售后状态', '1', '2022-12-10 20:58:08', '1', '2022-12-10 20:58:08', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (164, '公众号自动回复的请求关键字匹配模式', 'mp_auto_reply_request_match', 0, '公众号自动回复的请求关键字匹配模式', '1', '2023-01-16 23:29:56', '1', '2023-01-16 23:29:56', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (165, '公众号的消息类型', 'mp_message_type', 0, '公众号的消息类型', '1', '2023-01-17 22:17:09', '1', '2023-01-17 22:17:09', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (166, '邮件发送状态', 'system_mail_send_status', 0, '邮件发送状态', '1', '2023-01-26 09:53:13', '1', '2023-01-26 09:53:13', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (167, '站内信模版的类型', 'system_notify_template_type', 0, '站内信模版的类型', '1', '2023-01-28 10:35:10', '1', '2023-01-28 10:35:10', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (168, '代码生成的前端类型', 'infra_codegen_front_type', 0, '', '1', '2023-04-12 23:57:52', '1', '2023-04-12 23:57:52', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (170, '快递计费方式', 'trade_delivery_express_charge_mode', 0, '用于商城交易模块配送管理', '1', '2023-05-21 22:45:03', '1', '2023-05-21 22:45:03', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (171, '积分业务类型', 'member_point_biz_type', 0, '', '1', '2023-06-10 12:15:00', '1', '2023-06-28 13:48:20', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (173, '支付通知类型', 'pay_notify_type', 0, NULL, '1', '2023-07-20 12:23:03', '1', '2023-07-20 12:23:03', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (174, '会员经验业务类型', 'member_experience_biz_type', 0, NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (175, '交易配送类型', 'trade_delivery_type', 0, '', '1', '2023-08-23 00:03:14', '1', '2023-08-23 00:03:14', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (176, '分佣模式', 'brokerage_enabled_condition', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (177, '分销关系绑定模式', 'brokerage_bind_mode', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (178, '佣金提现类型', 'brokerage_withdraw_type', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (179, '佣金记录业务类型', 'brokerage_record_biz_type', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (180, '佣金记录状态', 'brokerage_record_status', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (181, '佣金提现状态', 'brokerage_withdraw_status', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (182, '佣金提现银行', 'brokerage_bank_name', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (183, '砍价记录的状态', 'promotion_bargain_record_status', 0, '', '1', '2023-10-05 10:41:08', '1', '2023-10-05 10:41:08', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (184, '拼团记录的状态', 'promotion_combination_record_status', 0, '', '1', '2023-10-08 07:24:25', '1', '2023-10-08 07:24:25', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (185, '回款-回款方式', 'crm_receivable_return_type', 0, '回款-回款方式', '1', '2023-10-18 21:54:10', '1', '2023-10-18 21:54:10', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (186, 'CRM 客户行业', 'crm_customer_industry', 0, 'CRM 客户所属行业', '1', '2023-10-28 22:57:07', '1', '2024-02-18 23:30:22', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (187, '客户等级', 'crm_customer_level', 0, 'CRM 客户等级', '1', '2023-10-28 22:59:12', '1', '2023-10-28 15:11:16', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (188, '客户来源', 'crm_customer_source', 0, 'CRM 客户来源', '1', '2023-10-28 23:00:34', '1', '2023-10-28 15:11:16', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (600, 'Banner 位置', 'promotion_banner_position', 0, '', '1', '2023-10-08 07:24:25', '1', '2023-11-04 13:04:02', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (601, '社交类型', 'system_social_type', 0, '', '1', '2023-11-04 13:03:54', '1', '2023-11-04 13:03:54', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (604, '产品状态', 'crm_product_status', 0, '', '1', '2023-10-30 21:47:59', '1', '2023-10-30 21:48:45', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (605, 'CRM 数据权限的级别', 'crm_permission_level', 0, '', '1', '2023-11-30 09:51:59', '1', '2023-11-30 09:51:59', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (606, 'CRM 审批状态', 'crm_audit_status', 0, '', '1', '2023-11-30 18:56:23', '1', '2023-11-30 18:56:23', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (607, 'CRM 产品单位', 'crm_product_unit', 0, '', '1', '2023-12-05 23:01:51', '1', '2023-12-05 23:01:51', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (608, 'CRM 跟进方式', 'crm_follow_up_type', 0, '', '1', '2024-01-15 20:48:05', '1', '2024-01-15 20:48:05', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (609, '支付转账类型', 'pay_transfer_type', 0, '', '1', '2023-10-28 16:27:18', '1', '2023-10-28 16:27:18', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (610, '转账订单状态', 'pay_transfer_status', 0, '', '1', '2023-10-28 16:18:32', '1', '2023-10-28 16:18:32', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (611, 'ERP 库存明细的业务类型', 'erp_stock_record_biz_type', 0, 'ERP 库存明细的业务类型', '1', '2024-02-05 18:07:02', '1', '2024-02-05 18:07:02', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (612, 'ERP 审批状态', 'erp_audit_status', 0, '', '1', '2024-02-06 00:00:07', '1', '2024-02-06 00:00:07', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (613, 'BPM 监听器类型', 'bpm_process_listener_type', 0, '', '1', '2024-03-23 12:52:24', '1', '2024-03-09 15:54:28', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (615, 'BPM 监听器值类型', 'bpm_process_listener_value_type', 0, '', '1', '2024-03-23 13:00:31', '1', '2024-03-23 13:00:31', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (616, '时间间隔', 'date_interval', 0, '', '1', '2024-03-29 22:50:09', '1', '2024-03-29 22:50:09', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (619, 'CRM 商机结束状态类型', 'crm_business_end_status_type', 0, '', '1', '2024-04-13 23:23:00', '1', '2024-04-13 23:23:00', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (620, 'AI 模型平台', 'ai_platform', 0, '', '1', '2024-05-09 22:27:38', '1', '2024-05-09 22:27:38', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (621, 'AI 绘画状态', 'ai_image_status', 0, '', '1', '2024-06-26 20:51:23', '1', '2024-06-26 20:51:23', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (622, 'AI 音乐状态', 'ai_music_status', 0, '', '1', '2024-06-27 22:45:07', '1', '2024-06-28 00:56:27', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (623, 'AI 音乐生成模式', 'ai_generate_mode', 0, '', '1', '2024-06-27 22:46:21', '1', '2024-06-28 01:22:29', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (624, '写作语气', 'ai_write_tone', 0, '', '1', '2024-07-07 15:19:02', '1', '2024-07-07 15:19:02', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (625, '写作语言', 'ai_write_language', 0, '', '1', '2024-07-07 15:18:52', '1', '2024-07-07 15:18:52', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (626, '写作长度', 'ai_write_length', 0, '', '1', '2024-07-07 15:18:41', '1', '2024-07-07 15:18:41', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (627, '写作格式', 'ai_write_format', 0, '', '1', '2024-07-07 15:14:34', '1', '2024-07-07 15:14:34', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (628, 'AI 写作类型', 'ai_write_type', 0, '', '1', '2024-07-10 21:25:29', '1', '2024-07-10 21:25:29', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (629, 'BPM 流程模型类型', 'bpm_model_type', 0, '', '1', '2024-08-26 15:21:43', '1', '2024-08-26 15:21:43', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (630, 'IOT 接入网关协议', 'iot_protocol_type', 0, '', '1', '2024-09-06 22:20:17', '1', '2024-09-06 22:20:17', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (631, 'IOT 设备状态', 'iot_device_status', 0, '', '1', '2024-09-21 08:12:55', '1', '2024-09-21 08:12:55', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (632, 'IOT 物模型功能类型', 'iot_product_function_type', 0, '', '1', '2024-09-29 20:02:36', '1', '2024-09-29 20:09:26', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (634, 'IOT 数据格式', 'iot_data_format', 0, '', '1', '2024-08-10 11:52:58', '1', '2024-09-06 14:30:14', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (635, 'IOT 产品设备类型', 'iot_product_device_type', 0, '', '1', '2024-08-10 11:54:30', '1', '2024-08-10 04:06:56', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (637, 'IOT 产品状态', 'iot_product_status', 0, '', '1', '2024-08-10 12:06:09', '1', '2024-08-10 12:06:09', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (638, 'IOT 数据校验级别', 'iot_validate_type', 0, '', '1', '2024-09-06 20:05:13', '1', '2024-09-06 20:05:13', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (639, 'IOT 联网方式', 'iot_net_type', 0, '', '1', '2024-09-06 22:04:13', '1', '2024-09-06 22:04:13', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (640, '是否int', 'common_boolen_int', 0, '', '1', '2025-02-10 11:08:01', '1', '2025-02-10 11:10:02', b'1', '2025-02-10 11:10:03');
INSERT INTO `system_dict_type` VALUES (641, '是否ini', 'common_boolen_int', 0, '', '1', '2025-02-10 11:10:19', '1', '2025-02-10 11:10:26', b'1', '2025-02-10 11:10:26');
INSERT INTO `system_dict_type` VALUES (642, '是否int', 'common_boolean_int', 0, '', '1', '2025-02-10 11:10:35', '1', '2025-02-10 14:33:12', b'1', '2025-02-10 14:33:13');
INSERT INTO `system_dict_type` VALUES (643, 'SipID状态', 'sip_gen_status', 0, '1=未使用，2=使用中', '1', '2025-01-03 11:22:07', '1', '2025-01-03 11:28:18', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (644, '芯片编码', 'eiot_mcu_code', 0, '', '1', '2024-12-25 11:00:30', '1', '2024-12-25 11:07:55', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (645, 'eiot显示模型类型', 'eiot_show_model_typ', 0, '', '1', '2024-12-11 12:04:02', '1', '2024-12-11 12:04:02', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (646, 'eiot设备状态', 'eiot_device_status', 0, '', '1', '2024-12-06 16:01:00', '1', '2024-12-06 16:01:00', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (647, '数字布尔', 'common_boolean_int', 0, '', '1', '2024-12-05 13:49:40', '1', '2024-12-05 13:49:40', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (648, 'eiot协议code', 'eiot_protocol_code', 0, '', '1', '2024-12-05 12:15:21', '1', '2024-12-05 12:15:21', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (649, 'eiot设备节点类型', 'eiot_node_type', 0, '', '1', '2024-12-05 12:11:56', '1', '2025-02-16 18:39:51', b'0', '1970-01-01 00:00:00');

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `system_login_log`;
CREATE TABLE `system_login_log`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
                                     `log_type` bigint NOT NULL COMMENT '日志类型',
                                     `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
                                     `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
                                     `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
                                     `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
                                     `result` tinyint NOT NULL COMMENT '登陆结果',
                                     `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
                                     `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3427 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_login_log
-- ----------------------------
INSERT INTO `system_login_log` VALUES (3426, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 06:35:01', NULL, '2025-02-23 06:35:01', b'0', 1);
INSERT INTO `system_login_log` VALUES (3427, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 19:59:22', NULL, '2025-02-23 19:59:22', b'0', 1);
INSERT INTO `system_login_log` VALUES (3428, 100, '', 1, 2, 'admin', 0, '222.247.113.4', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:00:21', NULL, '2025-02-23 20:00:21', b'0', 1);
INSERT INTO `system_login_log` VALUES (3429, 200, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '1', '2025-02-23 20:11:40', '1', '2025-02-23 20:11:40', b'0', 1);
INSERT INTO `system_login_log` VALUES (3430, 100, '', 104, 2, 'test', 10, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:11:48', NULL, '2025-02-23 20:11:48', b'0', 1);
INSERT INTO `system_login_log` VALUES (3431, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:12:00', NULL, '2025-02-23 20:12:00', b'0', 1);
INSERT INTO `system_login_log` VALUES (3432, 200, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '1', '2025-02-23 20:12:43', '1', '2025-02-23 20:12:43', b'0', 1);
INSERT INTO `system_login_log` VALUES (3433, 100, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:12:50', NULL, '2025-02-23 20:12:50', b'0', 1);
INSERT INTO `system_login_log` VALUES (3434, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 NetType/WIFI MicroMessenger/7.0.20.1781(0x6700143B) WindowsWechat(0x63090c2d) XWEB/13307 Flue', NULL, '2025-02-23 20:17:49', NULL, '2025-02-23 20:17:49', b'0', 1);
INSERT INTO `system_login_log` VALUES (3435, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:25:07', NULL, '2025-02-23 20:25:07', b'0', 1);
INSERT INTO `system_login_log` VALUES (3436, 200, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '1', '2025-02-23 21:17:37', '1', '2025-02-23 21:17:37', b'0', 1);
INSERT INTO `system_login_log` VALUES (3437, 100, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 21:18:02', NULL, '2025-02-23 21:18:02', b'0', 1);
INSERT INTO `system_login_log` VALUES (3438, 200, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '104', '2025-02-23 21:21:04', '104', '2025-02-23 21:21:04', b'0', 1);
INSERT INTO `system_login_log` VALUES (3439, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 21:21:13', NULL, '2025-02-23 21:21:13', b'0', 1);
INSERT INTO `system_login_log` VALUES (3440, 200, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '1', '2025-02-23 21:25:26', '1', '2025-02-23 21:25:26', b'0', 1);
INSERT INTO `system_login_log` VALUES (3441, 100, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 21:25:34', NULL, '2025-02-23 21:25:34', b'0', 1);
INSERT INTO `system_login_log` VALUES (3442, 200, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '104', '2025-02-23 21:26:40', '104', '2025-02-23 21:26:40', b'0', 1);
INSERT INTO `system_login_log` VALUES (3443, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 21:26:47', NULL, '2025-02-23 21:26:47', b'0', 1);

-- ----------------------------
-- Table structure for system_mail_account
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_account`;
CREATE TABLE `system_mail_account`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                        `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
                                        `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                                        `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                                        `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SMTP 服务器域名',
                                        `port` int NOT NULL COMMENT 'SMTP 服务器端口',
                                        `ssl_enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启 SSL',
                                        `starttls_enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启 STARTTLS',
                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮箱账号表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_mail_account
-- ----------------------------
INSERT INTO `system_mail_account` VALUES (1, '7684413@qq.com', '7684413@qq.com', '1234576', '127.0.0.1', 8080, b'0', b'0', '1', '2023-01-25 17:39:52', '1', '2024-07-27 22:39:12', b'0');
INSERT INTO `system_mail_account` VALUES (2, 'ydym_test@163.com', 'ydym_test@163.com', 'WBZTEINMIFVRYSOE', 'smtp.163.com', 465, b'1', b'0', '1', '2023-01-26 01:26:03', '1', '2023-04-12 22:39:38', b'0');
INSERT INTO `system_mail_account` VALUES (3, '76854114@qq.com', '3335', '11234', 'enjoy-iot.cn', 466, b'0', b'0', '1', '2023-01-27 15:06:38', '1', '2023-01-27 07:08:36', b'1');
INSERT INTO `system_mail_account` VALUES (4, '7685413x@qq.com', '2', '3', '4', 5, b'1', b'0', '1', '2023-04-12 23:05:06', '1', '2023-04-12 15:05:11', b'1');

-- ----------------------------
-- Table structure for system_mail_log
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_log`;
CREATE TABLE `system_mail_log`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                    `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
                                    `user_type` tinyint NULL DEFAULT NULL COMMENT '用户类型',
                                    `to_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接收邮箱地址',
                                    `account_id` bigint NOT NULL COMMENT '邮箱账号编号',
                                    `from_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送邮箱地址',
                                    `template_id` bigint NOT NULL COMMENT '模板编号',
                                    `template_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
                                    `template_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模版发送人名称',
                                    `template_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件标题',
                                    `template_content` varchar(10240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件内容',
                                    `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件参数',
                                    `send_status` tinyint NOT NULL DEFAULT 0 COMMENT '发送状态',
                                    `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
                                    `send_message_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送返回的消息 ID',
                                    `send_exception` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送异常',
                                    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 359 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮件日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_mail_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_mail_template
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_template`;
CREATE TABLE `system_mail_template`  (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                         `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
                                         `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
                                         `account_id` bigint NOT NULL COMMENT '发送的邮箱账号编号',
                                         `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送人名称',
                                         `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板标题',
                                         `content` varchar(10240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
                                         `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数数组',
                                         `status` tinyint NOT NULL COMMENT '开启状态',
                                         `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                         `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮件模版表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_mail_template
-- ----------------------------
INSERT INTO `system_mail_template` VALUES (13, '后台用户短信登录', 'admin-sms-login', 1, '奥特曼', '你猜我猜', '<p>您的验证码是{code}，名字是{name}</p>', '[\"code\",\"name\"]', 0, '3', '1', '2021-10-11 08:10:00', '1', '2023-12-02 19:51:14', b'0');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                                `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
                                `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
                                `type` tinyint NOT NULL COMMENT '菜单类型',
                                `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
                                `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父菜单ID',
                                `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
                                `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
                                `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
                                `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件名',
                                `status` tinyint NOT NULL DEFAULT 0 COMMENT '菜单状态',
                                `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
                                `keep_alive` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否缓存',
                                `always_show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否总是显示',
                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2932 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES (1, '系统管理', '', 1, 8, 0, '/system', 'ep:tools', NULL, NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2025-02-18 21:45:12', b'0');
INSERT INTO `system_menu` VALUES (2, '基础设施', '', 1, 9, 0, '/infra', 'ep:monitor', NULL, NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2025-02-18 21:45:19', b'0');
INSERT INTO `system_menu` VALUES (100, '用户管理', 'system:user:list', 2, 1, 1, 'user', 'ep:avatar', 'system/user/index', 'SystemUser', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:02:04', b'0');
INSERT INTO `system_menu` VALUES (101, '角色管理', '', 2, 2, 1, 'role', 'ep:user', 'system/role/index', 'SystemRole', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-05-01 18:35:29', b'0');
INSERT INTO `system_menu` VALUES (102, '菜单管理', '', 2, 3, 1, 'menu', 'ep:menu', 'system/menu/index', 'SystemMenu', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:03:50', b'0');
INSERT INTO `system_menu` VALUES (103, '部门管理', '', 2, 4, 1, 'dept', 'fa:address-card', 'system/dept/index', 'SystemDept', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:06:28', b'0');
INSERT INTO `system_menu` VALUES (104, '岗位管理', '', 2, 5, 1, 'post', 'fa:address-book-o', 'system/post/index', 'SystemPost', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:06:39', b'0');
INSERT INTO `system_menu` VALUES (105, '字典管理', '', 2, 6, 1, 'dict', 'ep:collection', 'system/dict/index', 'SystemDictType', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:07:12', b'0');
INSERT INTO `system_menu` VALUES (106, '配置管理', '', 2, 8, 2, 'config', 'fa:connectdevelop', 'infra/config/index', 'InfraConfig', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:02:45', b'0');
INSERT INTO `system_menu` VALUES (107, '通知公告', '', 2, 4, 2739, 'notice', 'ep:takeaway-box', 'system/notice/index', 'SystemNotice', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-22 23:56:17', b'0');
INSERT INTO `system_menu` VALUES (108, '审计日志', '', 1, 9, 1, 'log', 'ep:document-copy', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:08:30', b'0');
INSERT INTO `system_menu` VALUES (109, '令牌管理', '', 2, 2, 1261, 'token', 'fa:key', 'system/oauth2/token/index', 'SystemTokenClient', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:13:48', b'0');
INSERT INTO `system_menu` VALUES (110, '定时任务', '', 2, 7, 2, 'job', 'fa-solid:tasks', 'infra/job/index', 'InfraJob', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 08:57:36', b'0');
INSERT INTO `system_menu` VALUES (111, 'MySQL 监控', '', 2, 1, 2740, 'druid', 'fa-solid:box', 'infra/druid/index', 'InfraDruid', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:05:58', b'0');
INSERT INTO `system_menu` VALUES (112, 'Java 监控', '', 2, 3, 2740, 'admin-server', 'ep:coffee-cup', 'infra/server/index', 'InfraAdminServer', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:06:57', b'0');
INSERT INTO `system_menu` VALUES (113, 'Redis 监控', '', 2, 2, 2740, 'redis', 'fa:reddit-square', 'infra/redis/index', 'InfraRedis', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:06:09', b'0');
INSERT INTO `system_menu` VALUES (114, '表单构建', 'infra:build:list', 2, 2, 2, 'build', 'fa:wpforms', 'infra/build/index', 'InfraBuild', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 08:51:35', b'0');
INSERT INTO `system_menu` VALUES (115, '代码生成', 'infra:codegen:query', 2, 1, 2, 'codegen', 'ep:document-copy', 'infra/codegen/index', 'InfraCodegen', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 08:51:06', b'0');
INSERT INTO `system_menu` VALUES (116, 'API 接口', 'infra:swagger:list', 2, 3, 2, 'swagger', 'fa:fighter-jet', 'infra/swagger/index', 'InfraSwagger', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:01:24', b'0');
INSERT INTO `system_menu` VALUES (500, '操作日志', '', 2, 1, 108, 'operate-log', 'ep:position', 'system/operatelog/index', 'SystemOperateLog', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:09:59', b'0');
INSERT INTO `system_menu` VALUES (501, '登录日志', '', 2, 2, 108, 'login-log', 'ep:promotion', 'system/loginlog/index', 'SystemLoginLog', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:10:29', b'0');
INSERT INTO `system_menu` VALUES (1001, '用户查询', 'system:user:query', 3, 1, 100, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1002, '用户新增', 'system:user:create', 3, 2, 100, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1003, '用户修改', 'system:user:update', 3, 3, 100, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1004, '用户删除', 'system:user:delete', 3, 4, 100, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1005, '用户导出', 'system:user:export', 3, 5, 100, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1006, '用户导入', 'system:user:import', 3, 6, 100, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1007, '重置密码', 'system:user:update-password', 3, 7, 100, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1008, '角色查询', 'system:role:query', 3, 1, 101, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1009, '角色新增', 'system:role:create', 3, 2, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1010, '角色修改', 'system:role:update', 3, 3, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1011, '角色删除', 'system:role:delete', 3, 4, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1012, '角色导出', 'system:role:export', 3, 5, 101, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1013, '菜单查询', 'system:menu:query', 3, 1, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1014, '菜单新增', 'system:menu:create', 3, 2, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1015, '菜单修改', 'system:menu:update', 3, 3, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1016, '菜单删除', 'system:menu:delete', 3, 4, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1017, '部门查询', 'system:dept:query', 3, 1, 103, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1018, '部门新增', 'system:dept:create', 3, 2, 103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1019, '部门修改', 'system:dept:update', 3, 3, 103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1020, '部门删除', 'system:dept:delete', 3, 4, 103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1021, '岗位查询', 'system:post:query', 3, 1, 104, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1022, '岗位新增', 'system:post:create', 3, 2, 104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1023, '岗位修改', 'system:post:update', 3, 3, 104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1024, '岗位删除', 'system:post:delete', 3, 4, 104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1025, '岗位导出', 'system:post:export', 3, 5, 104, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1026, '字典查询', 'system:dict:query', 3, 1, 105, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1027, '字典新增', 'system:dict:create', 3, 2, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1028, '字典修改', 'system:dict:update', 3, 3, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1029, '字典删除', 'system:dict:delete', 3, 4, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1030, '字典导出', 'system:dict:export', 3, 5, 105, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1031, '配置查询', 'infra:config:query', 3, 1, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1032, '配置新增', 'infra:config:create', 3, 2, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1033, '配置修改', 'infra:config:update', 3, 3, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1034, '配置删除', 'infra:config:delete', 3, 4, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1035, '配置导出', 'infra:config:export', 3, 5, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1036, '公告查询', 'system:notice:query', 3, 1, 107, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1037, '公告新增', 'system:notice:create', 3, 2, 107, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1038, '公告修改', 'system:notice:update', 3, 3, 107, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1039, '公告删除', 'system:notice:delete', 3, 4, 107, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1040, '操作查询', 'system:operate-log:query', 3, 1, 500, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1042, '日志导出', 'system:operate-log:export', 3, 2, 500, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1043, '登录查询', 'system:login-log:query', 3, 1, 501, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1045, '日志导出', 'system:login-log:export', 3, 3, 501, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1046, '令牌列表', 'system:oauth2-token:page', 3, 1, 109, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-05-09 23:54:42', b'0');
INSERT INTO `system_menu` VALUES (1048, '令牌删除', 'system:oauth2-token:delete', 3, 2, 109, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-05-09 23:54:53', b'0');
INSERT INTO `system_menu` VALUES (1050, '任务新增', 'infra:job:create', 3, 2, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1051, '任务修改', 'infra:job:update', 3, 3, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1052, '任务删除', 'infra:job:delete', 3, 4, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1053, '状态修改', 'infra:job:update', 3, 5, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1054, '任务导出', 'infra:job:export', 3, 7, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1056, '生成修改', 'infra:codegen:update', 3, 2, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1057, '生成删除', 'infra:codegen:delete', 3, 3, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1058, '导入代码', 'infra:codegen:create', 3, 2, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1059, '预览代码', 'infra:codegen:preview', 3, 4, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1060, '生成代码', 'infra:codegen:download', 3, 5, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1063, '设置角色菜单权限', 'system:permission:assign-role-menu', 3, 6, 101, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-06 17:53:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1064, '设置角色数据权限', 'system:permission:assign-role-data-scope', 3, 7, 101, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-06 17:56:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1065, '设置用户角色', 'system:permission:assign-user-role', 3, 8, 101, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-07 10:23:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1066, '获得 Redis 监控信息', 'infra:redis:get-monitor-info', 3, 1, 113, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-26 01:02:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1067, '获得 Redis Key 列表', 'infra:redis:get-key-list', 3, 2, 113, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-26 01:02:52', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1070, '代码生成案例', '', 1, 1, 2, 'demo', 'ep:aim', 'infra/testDemo/index', NULL, 0, b'1', b'1', b'1', '', '2021-02-06 12:42:49', '1', '2023-11-15 23:45:53', b'0');
INSERT INTO `system_menu` VALUES (1075, '任务触发', 'infra:job:trigger', 3, 8, 110, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-07 13:03:10', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1077, '链路追踪', '', 2, 4, 2740, 'skywalking', 'fa:eye', 'infra/skywalking/index', 'InfraSkyWalking', 0, b'1', b'1', b'1', '', '2021-02-08 20:41:31', '1', '2024-04-23 00:07:15', b'0');
INSERT INTO `system_menu` VALUES (1078, '访问日志', '', 2, 1, 1083, 'api-access-log', 'ep:place', 'infra/apiAccessLog/index', 'InfraApiAccessLog', 0, b'1', b'1', b'1', '', '2021-02-26 01:32:59', '1', '2024-02-29 08:54:57', b'0');
INSERT INTO `system_menu` VALUES (1082, '日志导出', 'infra:api-access-log:export', 3, 2, 1078, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 01:32:59', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1083, 'API 日志', '', 2, 4, 2, 'log', 'fa:tasks', NULL, NULL, 0, b'1', b'1', b'1', '', '2021-02-26 02:18:24', '1', '2024-04-22 23:58:36', b'0');
INSERT INTO `system_menu` VALUES (1084, '错误日志', 'infra:api-error-log:query', 2, 2, 1083, 'api-error-log', 'ep:warning-filled', 'infra/apiErrorLog/index', 'InfraApiErrorLog', 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2024-02-29 08:55:17', b'0');
INSERT INTO `system_menu` VALUES (1085, '日志处理', 'infra:api-error-log:update-status', 3, 2, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1086, '日志导出', 'infra:api-error-log:export', 3, 3, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1087, '任务查询', 'infra:job:query', 3, 1, 110, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-03-10 01:26:19', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1088, '日志查询', 'infra:api-access-log:query', 3, 1, 1078, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-03-10 01:28:04', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1089, '日志查询', 'infra:api-error-log:query', 3, 1, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-03-10 01:29:09', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1090, '文件列表', '', 2, 5, 1243, 'file', 'ep:upload-filled', 'infra/file/index', 'InfraFile', 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '1', '2024-02-29 08:53:02', b'0');
INSERT INTO `system_menu` VALUES (1091, '文件查询', 'infra:file:query', 3, 1, 1090, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1092, '文件删除', 'infra:file:delete', 3, 4, 1090, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1093, '短信管理', '', 1, 1, 2739, 'sms', 'ep:message', NULL, NULL, 0, b'1', b'1', b'1', '1', '2021-04-05 01:10:16', '1', '2024-04-22 23:56:03', b'0');
INSERT INTO `system_menu` VALUES (1094, '短信渠道', '', 2, 0, 1093, 'sms-channel', 'fa:stack-exchange', 'system/sms/channel/index', 'SystemSmsChannel', 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '1', '2024-02-29 01:15:54', b'0');
INSERT INTO `system_menu` VALUES (1095, '短信渠道查询', 'system:sms-channel:query', 3, 1, 1094, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1096, '短信渠道创建', 'system:sms-channel:create', 3, 2, 1094, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1097, '短信渠道更新', 'system:sms-channel:update', 3, 3, 1094, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1098, '短信渠道删除', 'system:sms-channel:delete', 3, 4, 1094, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1100, '短信模板', '', 2, 1, 1093, 'sms-template', 'ep:connection', 'system/sms/template/index', 'SystemSmsTemplate', 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '1', '2024-02-29 01:16:18', b'0');
INSERT INTO `system_menu` VALUES (1101, '短信模板查询', 'system:sms-template:query', 3, 1, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1102, '短信模板创建', 'system:sms-template:create', 3, 2, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1103, '短信模板更新', 'system:sms-template:update', 3, 3, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1104, '短信模板删除', 'system:sms-template:delete', 3, 4, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1105, '短信模板导出', 'system:sms-template:export', 3, 5, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1106, '发送测试短信', 'system:sms-template:send-sms', 3, 6, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-04-11 00:26:40', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1107, '短信日志', '', 2, 2, 1093, 'sms-log', 'fa:edit', 'system/sms/log/index', 'SystemSmsLog', 0, b'1', b'1', b'1', '', '2021-04-11 08:37:05', '1', '2024-02-29 08:49:02', b'0');
INSERT INTO `system_menu` VALUES (1108, '短信日志查询', 'system:sms-log:query', 3, 1, 1107, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-11 08:37:05', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1109, '短信日志导出', 'system:sms-log:export', 3, 5, 1107, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-11 08:37:05', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1138, '租户列表', '', 2, 0, 1224, 'list', 'ep:house', 'system/tenant/index', 'SystemTenant', 0, b'1', b'1', b'1', '', '2021-12-14 12:31:43', '1', '2024-02-29 01:01:10', b'0');
INSERT INTO `system_menu` VALUES (1139, '租户查询', 'system:tenant:query', 3, 1, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1140, '租户创建', 'system:tenant:create', 3, 2, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1141, '租户更新', 'system:tenant:update', 3, 3, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1142, '租户删除', 'system:tenant:delete', 3, 4, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1143, '租户导出', 'system:tenant:export', 3, 5, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1224, '租户管理', '', 2, 0, 1, 'tenant', 'fa-solid:house-user', NULL, NULL, 0, b'1', b'1', b'1', '1', '2022-02-20 01:41:13', '1', '2024-02-29 00:59:29', b'0');
INSERT INTO `system_menu` VALUES (1225, '租户套餐', '', 2, 0, 1224, 'package', 'fa:bars', 'system/tenantPackage/index', 'SystemTenantPackage', 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '1', '2024-02-29 01:01:43', b'0');
INSERT INTO `system_menu` VALUES (1226, '租户套餐查询', 'system:tenant-package:query', 3, 1, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1227, '租户套餐创建', 'system:tenant-package:create', 3, 2, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1228, '租户套餐更新', 'system:tenant-package:update', 3, 3, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1229, '租户套餐删除', 'system:tenant-package:delete', 3, 4, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1237, '文件配置', '', 2, 0, 1243, 'file-config', 'fa-solid:file-signature', 'infra/fileConfig/index', 'InfraFileConfig', 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '1', '2024-02-29 08:52:54', b'0');
INSERT INTO `system_menu` VALUES (1238, '文件配置查询', 'infra:file-config:query', 3, 1, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1239, '文件配置创建', 'infra:file-config:create', 3, 2, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1240, '文件配置更新', 'infra:file-config:update', 3, 3, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1241, '文件配置删除', 'infra:file-config:delete', 3, 4, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1242, '文件配置导出', 'infra:file-config:export', 3, 5, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1243, '文件管理', '', 2, 6, 2, 'file', 'ep:files', NULL, '', 0, b'1', b'1', b'1', '1', '2022-03-16 23:47:40', '1', '2024-04-23 00:02:11', b'0');
INSERT INTO `system_menu` VALUES (1254, '作者动态', '', 1, 0, 0, 'http://www.enjoy-iot.cn', 'ep:avatar', NULL, NULL, 0, b'1', b'1', b'1', '1', '2022-04-23 01:03:15', '1', '2025-02-04 11:15:24', b'1');
INSERT INTO `system_menu` VALUES (1255, '数据源配置', '', 2, 1, 2, 'data-source-config', 'ep:data-analysis', 'infra/dataSourceConfig/index', 'InfraDataSourceConfig', 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '1', '2024-02-29 08:51:25', b'0');
INSERT INTO `system_menu` VALUES (1256, '数据源配置查询', 'infra:data-source-config:query', 3, 1, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1257, '数据源配置创建', 'infra:data-source-config:create', 3, 2, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1258, '数据源配置更新', 'infra:data-source-config:update', 3, 3, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1259, '数据源配置删除', 'infra:data-source-config:delete', 3, 4, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1260, '数据源配置导出', 'infra:data-source-config:export', 3, 5, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1261, 'OAuth 2.0', '', 2, 10, 1, 'oauth2', 'fa:dashcube', NULL, NULL, 0, b'1', b'1', b'1', '1', '2022-05-09 23:38:17', '1', '2024-02-29 01:12:08', b'0');
INSERT INTO `system_menu` VALUES (1263, '应用管理', '', 2, 0, 1261, 'oauth2/application', 'fa:hdd-o', 'system/oauth2/client/index', 'SystemOAuth2Client', 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2024-02-29 01:13:14', b'0');
INSERT INTO `system_menu` VALUES (1264, '客户端查询', 'system:oauth2-client:query', 3, 1, 1263, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:06', b'0');
INSERT INTO `system_menu` VALUES (1265, '客户端创建', 'system:oauth2-client:create', 3, 2, 1263, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:23', b'0');
INSERT INTO `system_menu` VALUES (1266, '客户端更新', 'system:oauth2-client:update', 3, 3, 1263, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:28', b'0');
INSERT INTO `system_menu` VALUES (1267, '客户端删除', 'system:oauth2-client:delete', 3, 4, 1263, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:33', b'0');
INSERT INTO `system_menu` VALUES (2083, '地区管理', '', 2, 14, 1, 'area', 'fa:map-marker', 'system/area/index', 'SystemArea', 0, b'1', b'1', b'1', '1', '2022-12-23 17:35:05', '1', '2024-02-29 08:50:28', b'0');
INSERT INTO `system_menu` VALUES (2130, '邮箱管理', '', 2, 2, 2739, 'mail', 'fa-solid:mail-bulk', NULL, NULL, 0, b'1', b'1', b'1', '1', '2023-01-25 17:27:44', '1', '2024-04-22 23:56:08', b'0');
INSERT INTO `system_menu` VALUES (2131, '邮箱账号', '', 2, 0, 2130, 'mail-account', 'fa:universal-access', 'system/mail/account/index', 'SystemMailAccount', 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '1', '2024-02-29 08:48:16', b'0');
INSERT INTO `system_menu` VALUES (2132, '账号查询', 'system:mail-account:query', 3, 1, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2133, '账号创建', 'system:mail-account:create', 3, 2, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2134, '账号更新', 'system:mail-account:update', 3, 3, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2135, '账号删除', 'system:mail-account:delete', 3, 4, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2136, '邮件模版', '', 2, 0, 2130, 'mail-template', 'fa:tag', 'system/mail/template/index', 'SystemMailTemplate', 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '1', '2024-02-29 08:48:41', b'0');
INSERT INTO `system_menu` VALUES (2137, '模版查询', 'system:mail-template:query', 3, 1, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2138, '模版创建', 'system:mail-template:create', 3, 2, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2139, '模版更新', 'system:mail-template:update', 3, 3, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2140, '模版删除', 'system:mail-template:delete', 3, 4, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2141, '邮件记录', '', 2, 0, 2130, 'mail-log', 'fa:edit', 'system/mail/log/index', 'SystemMailLog', 0, b'1', b'1', b'1', '', '2023-01-26 02:16:50', '1', '2024-02-29 08:48:51', b'0');
INSERT INTO `system_menu` VALUES (2142, '日志查询', 'system:mail-log:query', 3, 1, 2141, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-26 02:16:50', '', '2023-01-26 02:16:50', b'0');
INSERT INTO `system_menu` VALUES (2143, '发送测试邮件', 'system:mail-template:send-mail', 3, 5, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-26 23:29:15', '1', '2023-01-26 23:29:15', b'0');
INSERT INTO `system_menu` VALUES (2144, '站内信管理', '', 1, 3, 2739, 'notify', 'ep:message-box', NULL, NULL, 0, b'1', b'1', b'1', '1', '2023-01-28 10:25:18', '1', '2024-04-22 23:56:12', b'0');
INSERT INTO `system_menu` VALUES (2145, '模板管理', '', 2, 0, 2144, 'notify-template', 'fa:archive', 'system/notify/template/index', 'SystemNotifyTemplate', 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '1', '2024-02-29 08:49:14', b'0');
INSERT INTO `system_menu` VALUES (2146, '站内信模板查询', 'system:notify-template:query', 3, 1, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', b'0');
INSERT INTO `system_menu` VALUES (2147, '站内信模板创建', 'system:notify-template:create', 3, 2, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', b'0');
INSERT INTO `system_menu` VALUES (2148, '站内信模板更新', 'system:notify-template:update', 3, 3, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', b'0');
INSERT INTO `system_menu` VALUES (2149, '站内信模板删除', 'system:notify-template:delete', 3, 4, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', b'0');
INSERT INTO `system_menu` VALUES (2150, '发送测试站内信', 'system:notify-template:send-notify', 3, 5, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-28 10:54:43', '1', '2023-01-28 10:54:43', b'0');
INSERT INTO `system_menu` VALUES (2151, '消息记录', '', 2, 0, 2144, 'notify-message', 'fa:edit', 'system/notify/message/index', 'SystemNotifyMessage', 0, b'1', b'1', b'1', '', '2023-01-28 04:28:22', '1', '2024-02-29 08:49:22', b'0');
INSERT INTO `system_menu` VALUES (2152, '站内信消息查询', 'system:notify-message:query', 3, 1, 2151, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 04:28:22', '', '2023-01-28 04:28:22', b'0');
INSERT INTO `system_menu` VALUES (2159, 'Boot 开发文档', '', 1, 1, 0, 'http://doc.enjoy-iot.cn/', 'ep:document', NULL, NULL, 0, b'1', b'1', b'1', '1', '2023-02-10 22:46:28', '1', '2025-02-04 11:15:32', b'1');
INSERT INTO `system_menu` VALUES (2160, 'Cloud 开发文档', '', 1, 2, 0, 'https://cloud.enjoy-iot.cn', 'ep:document-copy', NULL, NULL, 0, b'1', b'1', b'1', '1', '2023-02-10 22:47:07', '1', '2025-02-04 11:15:29', b'1');
INSERT INTO `system_menu` VALUES (2447, '三方登录', '', 1, 10, 1, 'social', 'fa:rocket', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:12:01', '1', '2024-02-29 01:14:05', b'0');
INSERT INTO `system_menu` VALUES (2448, '三方应用', '', 2, 1, 2447, 'client', 'ep:set-up', 'system/social/client/index.vue', 'SocialClient', 0, b'1', b'1', b'1', '1', '2023-11-04 12:17:19', '1', '2024-05-04 19:09:54', b'0');
INSERT INTO `system_menu` VALUES (2449, '三方应用查询', 'system:social-client:query', 3, 1, 2448, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:43:12', '1', '2023-11-04 12:43:33', b'0');
INSERT INTO `system_menu` VALUES (2450, '三方应用创建', 'system:social-client:create', 3, 2, 2448, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:43:58', '1', '2023-11-04 12:43:58', b'0');
INSERT INTO `system_menu` VALUES (2451, '三方应用更新', 'system:social-client:update', 3, 3, 2448, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:44:27', '1', '2023-11-04 12:44:27', b'0');
INSERT INTO `system_menu` VALUES (2452, '三方应用删除', 'system:social-client:delete', 3, 4, 2448, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:44:43', '1', '2023-11-04 12:44:43', b'0');
INSERT INTO `system_menu` VALUES (2453, '三方用户', 'system:social-user:query', 2, 2, 2447, 'user', 'ep:avatar', 'system/social/user/index.vue', 'SocialUser', 0, b'1', b'1', b'1', '1', '2023-11-04 14:01:05', '1', '2023-11-04 14:01:05', b'0');
INSERT INTO `system_menu` VALUES (2472, '主子表（内嵌）', '', 2, 12, 1070, 'demo03-inner', 'fa:power-off', 'infra/demo/demo03/inner/index', 'Demo03StudentInner', 0, b'1', b'1', b'1', '', '2023-11-13 04:39:51', '1', '2023-11-16 23:53:46', b'0');
INSERT INTO `system_menu` VALUES (2478, '单表（增删改查）', '', 2, 1, 1070, 'demo01-contact', 'ep:bicycle', 'infra/demo/demo01/index', 'Demo01Contact', 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '1', '2023-11-16 20:34:40', b'0');
INSERT INTO `system_menu` VALUES (2479, '示例联系人查询', 'infra:demo01-contact:query', 3, 1, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2480, '示例联系人创建', 'infra:demo01-contact:create', 3, 2, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2481, '示例联系人更新', 'infra:demo01-contact:update', 3, 3, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2482, '示例联系人删除', 'infra:demo01-contact:delete', 3, 4, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2483, '示例联系人导出', 'infra:demo01-contact:export', 3, 5, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2484, '树表（增删改查）', '', 2, 2, 1070, 'demo02-category', 'fa:tree', 'infra/demo/demo02/index', 'Demo02Category', 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '1', '2023-11-16 20:35:01', b'0');
INSERT INTO `system_menu` VALUES (2485, '示例分类查询', 'infra:demo02-category:query', 3, 1, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2486, '示例分类创建', 'infra:demo02-category:create', 3, 2, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2487, '示例分类更新', 'infra:demo02-category:update', 3, 3, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2488, '示例分类删除', 'infra:demo02-category:delete', 3, 4, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2489, '示例分类导出', 'infra:demo02-category:export', 3, 5, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2490, '主子表（标准）', '', 2, 10, 1070, 'demo03-normal', 'fa:battery-3', 'infra/demo/demo03/normal/index', 'Demo03StudentNormal', 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '1', '2023-11-16 23:10:03', b'0');
INSERT INTO `system_menu` VALUES (2491, '学生查询', 'infra:demo03-student:query', 3, 1, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2492, '学生创建', 'infra:demo03-student:create', 3, 2, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2493, '学生更新', 'infra:demo03-student:update', 3, 3, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2494, '学生删除', 'infra:demo03-student:delete', 3, 4, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2495, '学生导出', 'infra:demo03-student:export', 3, 5, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2497, '主子表（ERP）', '', 2, 11, 1070, 'demo03-erp', 'ep:calendar', 'infra/demo/demo03/erp/index', 'Demo03StudentERP', 0, b'1', b'1', b'1', '', '2023-11-16 15:50:59', '1', '2023-11-17 13:19:56', b'0');
INSERT INTO `system_menu` VALUES (2525, 'WebSocket', '', 2, 5, 2, 'websocket', 'ep:connection', 'infra/webSocket/index', 'InfraWebSocket', 0, b'1', b'1', b'1', '1', '2023-11-23 19:41:55', '1', '2024-04-23 00:02:00', b'0');
INSERT INTO `system_menu` VALUES (2739, '消息中心', '', 1, 7, 1, 'messages', 'ep:chat-dot-round', '', '', 0, b'1', b'1', b'1', '1', '2024-04-22 23:54:30', '1', '2024-04-23 09:36:35', b'0');
INSERT INTO `system_menu` VALUES (2740, '监控中心', '', 1, 10, 2, 'monitors', 'ep:monitor', '', '', 0, b'1', b'1', b'1', '1', '2024-04-23 00:04:44', '1', '2024-04-23 00:04:44', b'0');
INSERT INTO `system_menu` VALUES (2814, '设备中心', '', 1, 1, 0, '/device', 'ep:grid', '', '', 0, b'1', b'1', b'1', '1', '2024-12-04 13:59:59', '1', '2025-02-04 11:55:31', b'0');
INSERT INTO `system_menu` VALUES (2815, '品类管理', '', 2, 0, 2814, 'category', 'ep:cherry', 'eiot/category/index', 'Category', 0, b'1', b'1', b'1', '', '2024-12-04 18:48:09', '1', '2025-02-04 11:57:31', b'0');
INSERT INTO `system_menu` VALUES (2816, 'IOT产品分类查询', 'iot:category:query', 3, 1, 2815, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:29', b'0');
INSERT INTO `system_menu` VALUES (2817, 'IOT产品分类创建', 'iot:category:create', 3, 2, 2815, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:35', b'0');
INSERT INTO `system_menu` VALUES (2818, 'IOT产品分类更新', 'iot:category:update', 3, 3, 2815, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:43', b'0');
INSERT INTO `system_menu` VALUES (2819, 'IOT产品分类删除', 'iot:category:delete', 3, 4, 2815, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:48', b'0');
INSERT INTO `system_menu` VALUES (2820, 'IOT产品分类导出', 'iot:category:export', 3, 5, 2815, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:55', b'0');
INSERT INTO `system_menu` VALUES (2821, '产品管理', '', 2, 0, 2814, 'eiotproduct', 'ep:credit-card', 'eiot/product/index', '', 0, b'1', b'1', b'1', '', '2024-12-04 19:31:42', '1', '2025-02-04 11:57:57', b'0');
INSERT INTO `system_menu` VALUES (2822, '物联网产品查询', 'iot:product:query', 3, 1, 2821, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:30:02', b'0');
INSERT INTO `system_menu` VALUES (2823, '物联网产品创建', 'iot:product:create', 3, 2, 2821, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:30:10', b'0');
INSERT INTO `system_menu` VALUES (2824, '物联网产品更新', 'iot:product:update', 3, 3, 2821, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:30:16', b'0');
INSERT INTO `system_menu` VALUES (2825, '物联网产品删除', 'iot:product:delete', 3, 4, 2821, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:31:21', b'0');
INSERT INTO `system_menu` VALUES (2826, '物联网产品导出', 'iot:product:export', 3, 5, 2821, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:31:26', b'0');
INSERT INTO `system_menu` VALUES (2827, '设备管理', '', 2, 0, 2814, 'device-info', 'ep:monitor', 'eiot/deviceinfo/index', '', 0, b'1', b'1', b'1', '', '2024-12-06 14:17:52', '1', '2025-02-04 11:58:59', b'0');
INSERT INTO `system_menu` VALUES (2828, '设备信息查询', 'iot:device-info:query', 3, 1, 2827, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:36:53', b'0');
INSERT INTO `system_menu` VALUES (2829, '设备信息创建', 'iot:device-info:create', 3, 2, 2827, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:36:58', b'0');
INSERT INTO `system_menu` VALUES (2830, '设备信息更新', 'iot:device-info:update', 3, 3, 2827, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:37:07', b'0');
INSERT INTO `system_menu` VALUES (2831, '设备信息删除', 'iot:device-info:delete', 3, 4, 2827, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:37:13', b'0');
INSERT INTO `system_menu` VALUES (2832, '设备信息导出', 'iot:device-info:export', 3, 5, 2827, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:37:20', b'0');
INSERT INTO `system_menu` VALUES (2833, '产品显示模型管理', '', 2, 0, 2814, 'show-model', '', 'eiot/showmodel/index', 'ShowModel', 0, b'1', b'1', b'1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:38:27', b'1');
INSERT INTO `system_menu` VALUES (2834, '产品显示模型查询', 'eiot:show-model:query', 3, 1, 2833, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:37:44', b'1');
INSERT INTO `system_menu` VALUES (2835, '产品显示模型创建', 'eiot:show-model:create', 3, 2, 2833, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:37:48', b'1');
INSERT INTO `system_menu` VALUES (2836, '产品显示模型更新', 'eiot:show-model:update', 3, 3, 2833, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:37:53', b'1');
INSERT INTO `system_menu` VALUES (2837, '产品显示模型删除', 'eiot:show-model:delete', 3, 4, 2833, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:37:57', b'1');
INSERT INTO `system_menu` VALUES (2838, '产品显示模型导出', 'eiot:show-model:export', 3, 5, 2833, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:38:02', b'1');
INSERT INTO `system_menu` VALUES (2839, '产品物模型管理', '', 2, 0, 2814, 'thing-model', '', 'eiot/thingmodel/index', 'ThingModel', 0, b'1', b'1', b'1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:43', b'1');
INSERT INTO `system_menu` VALUES (2840, '产品物模型查询', 'eiot:thing-model:query', 3, 1, 2839, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:13', b'1');
INSERT INTO `system_menu` VALUES (2841, '产品物模型创建', 'eiot:thing-model:create', 3, 2, 2839, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:18', b'1');
INSERT INTO `system_menu` VALUES (2842, '产品物模型更新', 'eiot:thing-model:update', 3, 3, 2839, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:21', b'1');
INSERT INTO `system_menu` VALUES (2843, '产品物模型删除', 'eiot:thing-model:delete', 3, 4, 2839, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:33', b'1');
INSERT INTO `system_menu` VALUES (2844, '产品物模型导出', 'eiot:thing-model:export', 3, 5, 2839, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:38', b'1');
INSERT INTO `system_menu` VALUES (2845, '规则引擎', '', 1, 3, 0, '/rule', 'ep:operation', '', '', 0, b'1', b'1', b'1', '1', '2024-12-25 14:25:34', '1', '2025-02-04 12:00:53', b'0');
INSERT INTO `system_menu` VALUES (2846, '规则引擎管理', '', 2, 0, 2845, 'rule-info', 'ep:credit-card', 'eiot/ruleinfo/index', 'EiotRuleInfo', 0, b'1', b'1', b'1', '', '2024-12-25 15:04:19', '1', '2025-02-04 12:01:13', b'0');
INSERT INTO `system_menu` VALUES (2847, '规则引擎查询', 'iot:rule-info:query', 3, 1, 2846, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:11', b'0');
INSERT INTO `system_menu` VALUES (2848, '规则引擎创建', 'iot:rule-info:create', 3, 2, 2846, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:20', b'0');
INSERT INTO `system_menu` VALUES (2849, '规则引擎更新', 'iot:rule-info:update', 3, 3, 2846, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:27', b'0');
INSERT INTO `system_menu` VALUES (2850, '规则引擎删除', 'iot:rule-info:remove', 3, 4, 2846, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:33', b'0');
INSERT INTO `system_menu` VALUES (2851, '规则引擎导出', 'iot:rule-info:export', 3, 5, 2846, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:38', b'0');
INSERT INTO `system_menu` VALUES (2852, '告警中心', '', 1, 4, 0, '/notify', 'ep:message', '', '', 0, b'1', b'1', b'1', '1', '2024-12-25 19:21:08', '1', '2025-02-04 12:02:43', b'0');
INSERT INTO `system_menu` VALUES (2853, '通道模板管理', '', 2, 0, 2914, 'channel-template', '', 'eiot/channel/template', 'ChannelTemplate', 0, b'1', b'1', b'1', '', '2024-12-26 14:25:40', '1', '2025-02-04 12:05:07', b'0');
INSERT INTO `system_menu` VALUES (2854, '通道模板查询', 'iot:channel:query', 3, 1, 2853, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:05:01', b'0');
INSERT INTO `system_menu` VALUES (2855, '通道模板创建', 'iot:channel:add', 3, 2, 2853, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:05:57', b'0');
INSERT INTO `system_menu` VALUES (2856, '通道模板更新', 'iot:channel:update', 3, 3, 2853, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:06:06', b'0');
INSERT INTO `system_menu` VALUES (2857, '通道模板删除', 'iot:channel:remove', 3, 4, 2853, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:06:31', b'0');
INSERT INTO `system_menu` VALUES (2858, '通道模板导出', 'iot:channel:query', 3, 5, 2853, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:06:51', b'0');
INSERT INTO `system_menu` VALUES (2859, '通道配置管理', '', 2, 0, 2914, 'channel-config', '', 'eiot/channel/config', 'ChannelConfig', 0, b'1', b'1', b'1', '', '2024-12-26 14:26:29', '1', '2025-02-04 12:05:14', b'0');
INSERT INTO `system_menu` VALUES (2860, '通道配置查询', 'iot:channel:query', 3, 1, 2859, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:26:30', '1', '2025-02-23 21:07:00', b'0');
INSERT INTO `system_menu` VALUES (2861, '通道配置创建', 'iot:channel:add', 3, 2, 2859, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:26:30', '1', '2025-02-23 21:07:09', b'0');
INSERT INTO `system_menu` VALUES (2862, '通道配置更新', 'iot:channel:update', 3, 3, 2859, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:26:30', '1', '2025-02-23 21:07:21', b'0');
INSERT INTO `system_menu` VALUES (2863, '通道配置删除', 'iot:channel:remove', 3, 4, 2859, '', '', '', '', 0, b'1', b'1', b'1', '', '2024-12-26 14:26:30', '1', '2025-02-23 21:07:30', b'0');
INSERT INTO `system_menu` VALUES (2864, '通道配置导出', 'eiot:channel-config:export', 3, 5, 2859, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2024-12-26 14:26:30', '', '2024-12-26 14:26:30', b'0');
INSERT INTO `system_menu` VALUES (2865, '设备分组', 'iot:deviceGroup:list', 2, 3, 2814, 'deviceGroup', 'ep:connection', 'eiot/deviceinfo/deviceGroup', '', 0, b'1', b'1', b'1', '1', '2025-01-14 13:35:37', '1', '2025-02-04 11:59:31', b'0');
INSERT INTO `system_menu` VALUES (2866, '设备分组详情', 'iot:device:query', 2, 5, 2814, 'deviceGroupDetail/:id', '', 'eiot/deviceinfo/deviceGroupDetail', '', 0, b'0', b'0', b'0', '1', '2025-01-14 14:26:36', '1', '2025-01-14 14:27:17', b'0');
INSERT INTO `system_menu` VALUES (2867, '定时任务', 'iot:task:query', 2, 2, 2845, 'scheduledTask', 'ep:compass', 'eiot/scheduledTask/index', '', 0, b'1', b'1', b'1', '1', '2025-01-21 15:18:09', '1', '2025-02-23 20:58:38', b'0');
INSERT INTO `system_menu` VALUES (2868, 'OTA', 'iot:ota:query', 2, 6, 2814, 'deviceOta', 'ep:mostly-cloudy', 'eiot/ota/upgradePack/index', '', 0, b'1', b'1', b'1', '1', '2025-01-21 19:06:31', '1', '2025-01-22 11:26:37', b'0');
INSERT INTO `system_menu` VALUES (2869, 'OTA查询', 'iot:ota:query', 3, 1, 2868, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-01-21 19:08:56', '1', '2025-01-21 19:08:56', b'0');
INSERT INTO `system_menu` VALUES (2913, 'EIOT物联网', '', 1, 4, 0, '/eiot', 'ep:apple', '', '', 0, b'1', b'1', b'1', '1', '2025-02-02 21:20:13', '1', '2025-02-04 11:54:48', b'1');
INSERT INTO `system_menu` VALUES (2914, '告警推送', '', 1, 2, 2852, 'alarm-setting', 'ep:operation', '', '', 0, b'1', b'1', b'1', '1', '2025-02-04 12:04:41', '1', '2025-02-04 12:06:21', b'0');
INSERT INTO `system_menu` VALUES (2915, '告警消息', 'iot:alert:query', 2, 1, 2852, 'alarm-messages', 'ep:bell', 'eiot/alarm/list', '', 0, b'1', b'1', b'1', '1', '2025-02-04 12:06:13', '1', '2025-02-23 21:25:17', b'0');
INSERT INTO `system_menu` VALUES (2916, '告警工单', '', 2, 3, 2852, 'alarm-work-order', 'ep:checked', '', '', 0, b'1', b'1', b'1', '1', '2025-02-04 12:07:17', '1', '2025-02-04 12:07:17', b'0');
INSERT INTO `system_menu` VALUES (2917, '算法管理', '', 2, 6, 0, '/algorithms', 'ep:cpu', 'ai/algorithm/index', '', 0, b'1', b'1', b'1', '1', '2025-02-04 12:08:46', '1', '2025-02-18 21:44:58', b'0');
INSERT INTO `system_menu` VALUES (2918, '组件中心', 'eiot:component:query', 2, 5, 0, '/components', 'ep:paperclip', 'eiot/component/index', '', 0, b'1', b'1', b'1', '1', '2025-02-04 12:13:42', '1', '2025-02-23 20:46:10', b'0');
INSERT INTO `system_menu` VALUES (2919, '数据中心', '', 1, 7, 0, '/data-center', 'ep:data-line', '', '', 0, b'1', b'1', b'1', '1', '2025-02-04 12:16:26', '1', '2025-02-18 21:45:04', b'0');
INSERT INTO `system_menu` VALUES (2920, '算法模型添加', 'ai:algorithm:create', 3, 1, 2917, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-06 10:10:28', '1', '2025-02-06 10:13:35', b'0');
INSERT INTO `system_menu` VALUES (2921, '算法模型修改', ' ai:algorithm:update', 3, 2, 2917, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-06 10:10:50', '1', '2025-02-06 10:13:28', b'0');
INSERT INTO `system_menu` VALUES (2922, '算法模型删除', 'ai:algorithm:delete', 3, 3, 2917, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-06 10:13:01', '1', '2025-02-06 10:13:01', b'0');
INSERT INTO `system_menu` VALUES (2923, '告警配置', 'iot:alertConfig:query', 2, 2, 2852, 'alarmconfig', 'ep:basketball', 'eiot/alarm/config', '', 0, b'1', b'1', b'1', '1', '2025-02-09 15:40:26', '1', '2025-02-23 21:24:43', b'0');
INSERT INTO `system_menu` VALUES (2924, '更新组件', 'eiot:component:update', 3, 1, 2918, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-19 01:19:59', '1', '2025-02-19 01:19:59', b'0');
INSERT INTO `system_menu` VALUES (2925, '虚拟设备', 'eiot:virtual-device:query', 2, 10, 2814, 'virtualDeviceList', 'ep:dish-dot', 'eiot/virtualDevice/index', '', 0, b'1', b'0', b'1', '1', '2025-02-19 14:30:29', '1', '2025-02-19 16:44:58', b'0');
INSERT INTO `system_menu` VALUES (2926, '虚拟设备详情', 'eiot:virtual-device:query', 2, 10, 2814, 'virtualDeviceConfig/:id', '', 'eiot/virtualDevice/virtualDeviceConfig', '', 0, b'0', b'0', b'0', '1', '2025-02-19 15:10:05', '1', '2025-02-19 16:43:22', b'0');
INSERT INTO `system_menu` VALUES (2927, '虚拟设备查询', 'eiot:virtual-device:query', 3, 1, 2925, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-19 16:45:28', '1', '2025-02-19 16:45:28', b'0');
INSERT INTO `system_menu` VALUES (2928, '虚拟设备创建', 'eiot:virtual-device:create', 3, 2, 2925, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-19 16:45:45', '1', '2025-02-19 16:45:45', b'0');
INSERT INTO `system_menu` VALUES (2929, '虚拟设备更新', 'eiot:virtual-device:update', 3, 3, 2925, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-19 16:45:59', '1', '2025-02-19 16:46:38', b'0');
INSERT INTO `system_menu` VALUES (2930, '虚拟设备删除', 'eiot:virtual-device:delete', 3, 4, 2925, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-19 16:46:15', '1', '2025-02-19 16:46:15', b'0');
INSERT INTO `system_menu` VALUES (2931, '设备详情', '', 2, 10, 2814, 'deviceDetail/:id', '', 'eiot/deviceinfo/detail', '', 0, b'0', b'0', b'0', '1', '2025-02-21 01:02:15', '1', '2025-02-21 01:03:08', b'0');
INSERT INTO `system_menu` VALUES (2932, '物模型修改', 'iot:thing-model:update', 3, 6, 2821, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:34:19', '1', '2025-02-23 20:34:19', b'0');
INSERT INTO `system_menu` VALUES (2933, '物模型查询', 'iot:thing-model:query', 3, 7, 2821, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:34:58', '1', '2025-02-23 20:34:58', b'0');
INSERT INTO `system_menu` VALUES (2934, '设备控制', 'iot:device:ctrl', 3, 6, 2827, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:36:20', '1', '2025-02-23 20:36:20', b'0');
INSERT INTO `system_menu` VALUES (2935, '设备日志', 'iot:deviceLog:query', 3, 7, 2827, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:39:51', '1', '2025-02-23 20:39:51', b'0');
INSERT INTO `system_menu` VALUES (2936, '设备分组添加', 'iot:deviceGroup:add', 3, 8, 2827, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:41:08', '1', '2025-02-23 20:41:08', b'0');
INSERT INTO `system_menu` VALUES (2937, '设备组修改', 'iot:deviceGroup:edit', 3, 9, 2827, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:41:45', '1', '2025-02-23 20:41:45', b'0');
INSERT INTO `system_menu` VALUES (2938, '设备组查询', 'iot:deviceGroup:query', 3, 10, 2827, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:42:13', '1', '2025-02-23 20:42:13', b'0');
INSERT INTO `system_menu` VALUES (2939, '设备组删除', 'iot:deviceGroup:remove', 3, 11, 2827, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:42:45', '1', '2025-02-23 20:42:45', b'0');
INSERT INTO `system_menu` VALUES (2940, '新增组件', 'eiot:component:create', 3, 2, 2918, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:46:45', '1', '2025-02-23 20:46:45', b'0');
INSERT INTO `system_menu` VALUES (2941, '组件删除', 'eiot:component:delete', 3, 3, 2918, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:47:06', '1', '2025-02-23 20:47:06', b'0');
INSERT INTO `system_menu` VALUES (2942, '暂停规则', 'iot:rule-info:pause', 3, 6, 2846, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:55:16', '1', '2025-02-23 20:55:22', b'0');
INSERT INTO `system_menu` VALUES (2943, '恢复规则', 'iot:rule-info:resume', 3, 7, 2846, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:55:56', '1', '2025-02-23 20:55:56', b'0');
INSERT INTO `system_menu` VALUES (2944, '编辑定时任务', 'iot:task:edit', 3, 1, 2867, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:57:50', '1', '2025-02-23 20:57:50', b'0');
INSERT INTO `system_menu` VALUES (2945, '删除定时任务', 'iot:task:remove', 3, 2, 2867, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 20:58:10', '1', '2025-02-23 20:58:10', b'0');
INSERT INTO `system_menu` VALUES (2946, '新增告警配置', 'iot:alertConfig:add', 3, 1, 2923, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 21:08:32', '1', '2025-02-23 21:08:32', b'0');
INSERT INTO `system_menu` VALUES (2947, '查询告警配置', 'iot:alertConfig:query', 3, 2, 2923, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 21:08:56', '1', '2025-02-23 21:08:56', b'0');
INSERT INTO `system_menu` VALUES (2948, '更新告警配置', 'iot:alertConfig:edit', 3, 3, 2923, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 21:09:26', '1', '2025-02-23 21:10:22', b'0');
INSERT INTO `system_menu` VALUES (2949, '删除告警配置', 'iot:alertConfig:remove', 3, 4, 2923, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 21:09:42', '1', '2025-02-23 21:09:42', b'0');
INSERT INTO `system_menu` VALUES (2950, '新增OTA', 'iot:ota:add', 3, 2, 2868, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 21:14:28', '1', '2025-02-23 21:14:28', b'0');
INSERT INTO `system_menu` VALUES (2951, '移除OTA', 'iot:ota:remove', 3, 3, 2868, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 21:14:52', '1', '2025-02-23 21:14:52', b'0');
INSERT INTO `system_menu` VALUES (2952, '执行ota', 'iot:ota:upgrade', 3, 4, 2868, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-02-23 21:15:31', '1', '2025-02-23 21:15:31', b'0');
INSERT INTO `system_menu` VALUES (2953, 'Modbus管理', '', 1, 2, 0, '/modbus', 'fa:empire', '', '', 0, b'1', b'1', b'1', '1', '2025-03-28 00:26:55', '1', '2025-03-28 00:27:15', b'0');
INSERT INTO `system_menu` VALUES (2954, '产品点位', 'iot:modbus:list', 2, 1, 2953, 'modbusinfo', 'fa:bullseye', 'eiot/modbus/index', '', 0, b'1', b'1', b'1', '1', '2025-03-28 00:32:21', '1', '2025-03-28 00:33:56', b'0');
INSERT INTO `system_menu` VALUES (2955, '新建ModbusInfo', 'iot:modbus:add', 3, 1, 2954, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-03-28 00:35:47', '1', '2025-03-28 00:35:47', b'0');
INSERT INTO `system_menu` VALUES (2956, '编辑ModbusInfo', 'iot:modbus:edit', 3, 2, 2954, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-03-28 00:36:23', '1', '2025-03-28 00:36:23', b'0');
INSERT INTO `system_menu` VALUES (2957, '查看ModbusInfo详情', 'iot:modbus:query', 3, 3, 2954, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-03-28 00:36:48', '1', '2025-03-28 00:36:48', b'0');
INSERT INTO `system_menu` VALUES (2958, '删除ModbusInfo', 'iot:modbus:remove', 3, 4, 2954, '', '', '', '', 0, b'1', b'1', b'1', '1', '2025-03-28 00:37:12', '1', '2025-03-28 00:37:12', b'0');


-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
                                  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
                                  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告内容',
                                  `type` tinyint NOT NULL COMMENT '公告类型（1通知 2公告）',
                                  `status` tinyint NOT NULL DEFAULT 0 COMMENT '公告状态（0正常 1关闭）',
                                  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_notice
-- ----------------------------
INSERT INTO `system_notice` VALUES (1, '新版本发布通知', '<p>新版本内容133</p>', 1, 0, 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 21:00:20', b'0', 1);

-- ----------------------------
-- Table structure for system_notify_message
-- ----------------------------
DROP TABLE IF EXISTS `system_notify_message`;
CREATE TABLE `system_notify_message`  (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                                          `user_id` bigint NOT NULL COMMENT '用户id',
                                          `user_type` tinyint NOT NULL COMMENT '用户类型',
                                          `template_id` bigint NOT NULL COMMENT '模版编号',
                                          `template_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
                                          `template_nickname` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版发送人名称',
                                          `template_content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版内容',
                                          `template_type` int NOT NULL COMMENT '模版类型',
                                          `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版参数',
                                          `read_status` bit(1) NOT NULL COMMENT '是否已读',
                                          `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
                                          `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站内信消息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_notify_message
-- ----------------------------

-- ----------------------------
-- Table structure for system_notify_template
-- ----------------------------
DROP TABLE IF EXISTS `system_notify_template`;
CREATE TABLE `system_notify_template`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                           `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
                                           `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版编码',
                                           `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送人名称',
                                           `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版内容',
                                           `type` tinyint NOT NULL COMMENT '类型',
                                           `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '参数数组',
                                           `status` tinyint NOT NULL COMMENT '状态',
                                           `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                           `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                           `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站内信模板表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_notify_template
-- ----------------------------

-- ----------------------------
-- Table structure for system_oauth2_access_token
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_access_token`;
CREATE TABLE `system_oauth2_access_token`  (
                                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                               `user_id` bigint NOT NULL COMMENT '用户编号',
                                               `user_type` tinyint NOT NULL COMMENT '用户类型',
                                               `user_info` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户信息',
                                               `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问令牌',
                                               `refresh_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '刷新令牌',
                                               `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
                                               `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授权范围',
                                               `expires_time` datetime NOT NULL COMMENT '过期时间',
                                               `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                               `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                               `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                               `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                               PRIMARY KEY (`id`) USING BTREE,
                                               INDEX `idx_access_token`(`access_token` ASC) USING BTREE,
                                               INDEX `idx_refresh_token`(`refresh_token` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1891133221308539079 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 访问令牌' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_oauth2_access_token
-- ----------------------------
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539075, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '671c2e52d6a848098aa1bde826378977', '3697fe2caa3c42fbbf14b25167d27e5d', 'default', NULL, '2025-02-23 07:05:01', NULL, '2025-02-23 06:35:01', NULL, '2025-02-23 07:05:52', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539076, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', 'a6b320293435426bb9c4fd1b4c474598', '3697fe2caa3c42fbbf14b25167d27e5d', 'default', NULL, '2025-02-23 07:35:52', NULL, '2025-02-23 07:05:52', NULL, '2025-02-23 07:37:51', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539077, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '0fb206b6e0b148b5822c7f9e0d1a06e3', '3697fe2caa3c42fbbf14b25167d27e5d', 'default', NULL, '2025-02-23 08:07:52', NULL, '2025-02-23 07:37:52', NULL, '2025-02-23 08:08:28', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539078, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', 'd18dc53722ce4810a196c16fa288bfee', '3697fe2caa3c42fbbf14b25167d27e5d', 'default', NULL, '2025-02-23 08:38:28', NULL, '2025-02-23 08:08:28', NULL, '2025-02-23 08:08:28', b'0', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539079, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '2151ff0f3b8146fb9d9f4d1a126864f5', '27522033a27342b2b04752e3a2647392', 'default', NULL, '2025-02-23 20:29:22', NULL, '2025-02-23 19:59:22', '1', '2025-02-23 20:11:40', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539080, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '960fb22b30cc4141822e4467db4079aa', '1dc5295cbbbe4d5ea4665b751cd34208', 'default', NULL, '2025-02-23 20:30:21', NULL, '2025-02-23 20:00:21', NULL, '2025-02-23 20:30:22', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539081, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '78301042b7a14645b4eed54850aac14b', 'c05966dd8a6c461f9375979a786e502f', 'default', NULL, '2025-02-23 20:42:00', NULL, '2025-02-23 20:12:00', '1', '2025-02-23 20:12:43', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539082, 104, 2, '{\"nickname\":\"测试号\",\"deptId\":\"107\"}', '85c60a6c823b4d769f7e4520ad1c346e', '5e8bf32b6bc147128e24781562e7ee2f', 'default', NULL, '2025-02-23 20:42:50', NULL, '2025-02-23 20:12:50', NULL, '2025-02-23 20:12:50', b'0', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539083, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '548881f0c4cf4050ab6ede06aea141b4', '6c630caa533246d8a48c50e36ba6af11', 'default', NULL, '2025-02-23 20:47:49', NULL, '2025-02-23 20:17:49', NULL, '2025-02-23 20:17:49', b'0', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539084, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '252840e2e4ad46bd8881ea36ab1d56ec', '15871af084ad4726a25386b6fafd90e1', 'default', NULL, '2025-02-23 20:55:08', NULL, '2025-02-23 20:25:08', NULL, '2025-02-23 20:55:16', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539085, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '0eeabc414ee84d12af6ec27267ca034d', '1dc5295cbbbe4d5ea4665b751cd34208', 'default', NULL, '2025-02-23 21:00:22', NULL, '2025-02-23 20:30:22', NULL, '2025-02-23 21:00:32', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539086, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '1828a8d71a794745912592980a4b658a', '15871af084ad4726a25386b6fafd90e1', 'default', NULL, '2025-02-23 21:25:16', NULL, '2025-02-23 20:55:16', '1', '2025-02-23 21:17:37', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539087, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '89fa5b026d7f4bbbab9baf5927cb1b42', '1dc5295cbbbe4d5ea4665b751cd34208', 'default', NULL, '2025-02-23 21:30:32', NULL, '2025-02-23 21:00:32', NULL, '2025-02-23 21:00:32', b'0', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539088, 104, 2, '{\"nickname\":\"测试号\",\"deptId\":\"107\"}', '7dc77d72b7f7449186f24a41a8426eed', '4df0a1cd23674557aab2cd2ad45ca5c1', 'default', NULL, '2025-02-23 21:48:02', NULL, '2025-02-23 21:18:02', '104', '2025-02-23 21:21:04', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539089, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', 'f02057564b6f477ba4dcc3e9d5eaceb2', 'cc72a55ee66d405d882a228640f4dd53', 'default', NULL, '2025-02-23 21:51:13', NULL, '2025-02-23 21:21:13', '1', '2025-02-23 21:25:26', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539090, 104, 2, '{\"nickname\":\"测试号\",\"deptId\":\"107\"}', '294e5776aa454e83af6774d205aa5f5f', 'ce7de432a49643718a9bfb4f12561f8b', 'default', NULL, '2025-02-23 21:55:34', NULL, '2025-02-23 21:25:34', '104', '2025-02-23 21:26:40', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (1891133221308539091, 1, 2, '{\"nickname\":\"EnjoyIot\",\"deptId\":\"103\"}', '1093dea5ac3e42d080ed43623400f1b8', '5523e085b00a4bd982e6ecf4a89424a7', 'default', NULL, '2025-02-23 21:56:47', NULL, '2025-02-23 21:26:47', NULL, '2025-02-23 21:26:47', b'0', 1);

-- ----------------------------
-- Table structure for system_oauth2_approve
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_approve`;
CREATE TABLE `system_oauth2_approve`  (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                          `user_id` bigint NOT NULL COMMENT '用户编号',
                                          `user_type` tinyint NOT NULL COMMENT '用户类型',
                                          `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
                                          `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '授权范围',
                                          `approved` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否接受',
                                          `expires_time` datetime NOT NULL COMMENT '过期时间',
                                          `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 批准表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_oauth2_approve
-- ----------------------------

-- ----------------------------
-- Table structure for system_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_client`;
CREATE TABLE `system_oauth2_client`  (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                         `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
                                         `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端密钥',
                                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
                                         `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用图标',
                                         `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '应用描述',
                                         `status` tinyint NOT NULL COMMENT '状态',
                                         `access_token_validity_seconds` int NOT NULL COMMENT '访问令牌的有效期',
                                         `refresh_token_validity_seconds` int NOT NULL COMMENT '刷新令牌的有效期',
                                         `redirect_uris` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '可重定向的 URI 地址',
                                         `authorized_grant_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '授权类型',
                                         `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授权范围',
                                         `auto_approve_scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '自动通过的授权范围',
                                         `authorities` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限',
                                         `resource_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源',
                                         `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附加信息',
                                         `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 客户端表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_oauth2_client
-- ----------------------------
INSERT INTO `system_oauth2_client` VALUES (1, 'default', 'admin123', 'EnjoyIot开源物联网平台', '', '我是描述', 0, 1800, 2592000, '[\"http://www.enjoy-iot.cn\"]', '[\"password\",\"authorization_code\",\"implicit\",\"refresh_token\"]', '[\"user.read\",\"user.write\"]', '[]', '[\"user.read\",\"user.write\"]', '[]', '{}', '1', '2022-05-11 21:47:12', '1', '2024-02-22 16:31:52', b'0');

-- ----------------------------
-- Table structure for system_oauth2_code
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_code`;
CREATE TABLE `system_oauth2_code`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                       `user_id` bigint NOT NULL COMMENT '用户编号',
                                       `user_type` tinyint NOT NULL COMMENT '用户类型',
                                       `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '授权码',
                                       `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
                                       `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '授权范围',
                                       `expires_time` datetime NOT NULL COMMENT '过期时间',
                                       `redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '可重定向的 URI 地址',
                                       `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '状态',
                                       `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                       `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 147 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 授权码表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_oauth2_code
-- ----------------------------

-- ----------------------------
-- Table structure for system_oauth2_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_refresh_token`;
CREATE TABLE `system_oauth2_refresh_token`  (
                                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                                `user_id` bigint NOT NULL COMMENT '用户编号',
                                                `refresh_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '刷新令牌',
                                                `user_type` tinyint NOT NULL COMMENT '用户类型',
                                                `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
                                                `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授权范围',
                                                `expires_time` datetime NOT NULL COMMENT '过期时间',
                                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                                `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1721 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 刷新令牌' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_oauth2_refresh_token
-- ----------------------------
INSERT INTO `system_oauth2_refresh_token` VALUES (1720, 1, '3697fe2caa3c42fbbf14b25167d27e5d', 2, 'default', NULL, '2025-03-25 06:35:01', NULL, '2025-02-23 06:35:01', NULL, '2025-02-23 06:35:01', b'0', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1721, 1, '27522033a27342b2b04752e3a2647392', 2, 'default', NULL, '2025-03-25 19:59:22', NULL, '2025-02-23 19:59:22', NULL, '2025-02-23 20:11:39', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1722, 1, '1dc5295cbbbe4d5ea4665b751cd34208', 2, 'default', NULL, '2025-03-25 20:00:21', NULL, '2025-02-23 20:00:21', NULL, '2025-02-23 20:00:21', b'0', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1723, 1, 'c05966dd8a6c461f9375979a786e502f', 2, 'default', NULL, '2025-03-25 20:12:00', NULL, '2025-02-23 20:12:00', NULL, '2025-02-23 20:12:43', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1724, 104, '5e8bf32b6bc147128e24781562e7ee2f', 2, 'default', NULL, '2025-03-25 20:12:50', NULL, '2025-02-23 20:12:50', NULL, '2025-02-23 20:12:50', b'0', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1725, 1, '6c630caa533246d8a48c50e36ba6af11', 2, 'default', NULL, '2025-03-25 20:17:49', NULL, '2025-02-23 20:17:49', NULL, '2025-02-23 20:17:49', b'0', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1726, 1, '15871af084ad4726a25386b6fafd90e1', 2, 'default', NULL, '2025-03-25 20:25:08', NULL, '2025-02-23 20:25:08', NULL, '2025-02-23 21:17:36', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1727, 104, '4df0a1cd23674557aab2cd2ad45ca5c1', 2, 'default', NULL, '2025-03-25 21:18:02', NULL, '2025-02-23 21:18:02', NULL, '2025-02-23 21:21:03', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1728, 1, 'cc72a55ee66d405d882a228640f4dd53', 2, 'default', NULL, '2025-03-25 21:21:13', NULL, '2025-02-23 21:21:13', NULL, '2025-02-23 21:25:26', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1729, 104, 'ce7de432a49643718a9bfb4f12561f8b', 2, 'default', NULL, '2025-03-25 21:25:34', NULL, '2025-02-23 21:25:34', NULL, '2025-02-23 21:26:39', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1730, 1, '5523e085b00a4bd982e6ecf4a89424a7', 2, 'default', NULL, '2025-03-25 21:26:47', NULL, '2025-02-23 21:26:47', NULL, '2025-02-23 21:26:47', b'0', 1);

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
                                       `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
                                       `user_id` bigint NOT NULL COMMENT '用户编号',
                                       `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
                                       `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作模块类型',
                                       `sub_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作名',
                                       `biz_id` bigint NOT NULL COMMENT '操作数据模块编号',
                                       `action` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
                                       `success` bit(1) NOT NULL DEFAULT b'1' COMMENT '操作结果',
                                       `extra` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
                                       `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方法名',
                                       `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求地址',
                                       `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户 IP',
                                       `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '浏览器 UA',
                                       `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                       `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9064 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录 V2 版本' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------
INSERT INTO `system_operate_log` VALUES (9064, '', 1, 2, 'SYSTEM 角色', '创建角色', 154, '创建了角色【只读用户】', b'1', '', 'POST', '/admin-api/system/role/create', '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:05:09', NULL, '2025-02-23 20:05:09', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9065, '', 1, 2, 'SYSTEM 用户', '重置用户密码', 104, '将用户【测试号】的密码从【$2a$04$jDFLttgfik0QqJKAbfhMa.2A9xXoZmAIxakdFJUzkX.MgBKT6ddo6】重置为【$2a$04$KG9vBvKLO5PP9Tfd8ttF0.CDObtXPhMQb8I7vPEdVBSuI7uOx51Ey】', b'1', '', 'PUT', '/admin-api/system/user/update-password', '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:12:38', NULL, '2025-02-23 20:12:38', b'0', 1);

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS `system_post`;
CREATE TABLE `system_post`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
                                `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
                                `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
                                `sort` int NOT NULL COMMENT '显示顺序',
                                `status` tinyint NOT NULL COMMENT '状态（0正常 1停用）',
                                `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_post
-- ----------------------------
INSERT INTO `system_post` VALUES (1, 'ceo', '董事长', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2023-02-11 15:19:04', b'0', 1);
INSERT INTO `system_post` VALUES (2, 'se', '项目经理', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2023-11-15 09:18:20', b'0', 1);
INSERT INTO `system_post` VALUES (4, 'user', '普通员工', 4, 0, '111', 'admin', '2021-01-05 17:03:48', '1', '2023-12-02 10:04:37', b'0', 1);
INSERT INTO `system_post` VALUES (5, 'HR', '人力资源', 5, 0, '', '1', '2024-03-24 20:45:40', '1', '2024-03-24 20:45:40', b'0', 1);

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                                `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
                                `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
                                `sort` int NOT NULL COMMENT '显示顺序',
                                `data_scope` tinyint NOT NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
                                `data_scope_dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
                                `status` tinyint NOT NULL COMMENT '角色状态（0正常 1停用）',
                                `type` tinyint NOT NULL COMMENT '角色类型',
                                `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21', b'0', 1);
INSERT INTO `system_role` VALUES (154, '只读用户', 'reader', 2, 1, '', 0, 2, '', '1', '2025-02-23 20:05:08', '1', '2025-02-23 20:05:08', b'0', 1);

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
                                     `role_id` bigint NOT NULL COMMENT '角色ID',
                                     `menu_id` bigint NOT NULL COMMENT '菜单ID',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5793 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES (5793, 154, 2816, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5794, 154, 2817, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5795, 154, 1025, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5796, 154, 1, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5797, 154, 1026, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5798, 154, 2, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5799, 154, 2820, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5800, 154, 2821, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5801, 154, 2822, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5802, 154, 1030, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5803, 154, 2823, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5804, 154, 1031, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5805, 154, 2826, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5806, 154, 1035, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5807, 154, 2827, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5808, 154, 2828, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5809, 154, 1036, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5810, 154, 2829, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5811, 154, 2832, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5812, 154, 1040, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5813, 154, 1042, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5814, 154, 1043, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5815, 154, 1045, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5816, 154, 1046, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5817, 154, 2845, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5818, 154, 1054, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5819, 154, 2846, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5820, 154, 2847, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5821, 154, 2848, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5822, 154, 1056, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5823, 154, 1057, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5824, 154, 1058, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5825, 154, 2851, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5826, 154, 2083, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5827, 154, 1059, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5828, 154, 1060, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5829, 154, 2852, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5830, 154, 2853, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5831, 154, 2854, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5832, 154, 2855, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5833, 154, 2858, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5834, 154, 1066, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5835, 154, 1067, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5836, 154, 2859, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5837, 154, 2860, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5838, 154, 2861, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5839, 154, 1070, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5840, 154, 2864, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5841, 154, 2865, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5842, 154, 2866, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5843, 154, 2867, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5844, 154, 2868, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5845, 154, 2869, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5846, 154, 1077, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5847, 154, 1078, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5848, 154, 1082, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5849, 154, 1083, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5850, 154, 1084, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5851, 154, 1085, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5852, 154, 1086, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5853, 154, 1087, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5854, 154, 1088, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5855, 154, 1089, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5856, 154, 1090, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5857, 154, 1091, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5858, 154, 1093, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5859, 154, 1094, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5860, 154, 1095, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5861, 154, 1100, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5862, 154, 1101, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5863, 154, 1105, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5864, 154, 1106, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5865, 154, 2130, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5866, 154, 1107, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5867, 154, 2131, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5868, 154, 1108, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5869, 154, 2132, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5870, 154, 1109, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5871, 154, 2136, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5872, 154, 2137, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5873, 154, 2141, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5874, 154, 2142, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5875, 154, 2143, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5876, 154, 2144, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5877, 154, 2145, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5878, 154, 2146, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5879, 154, 2914, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5880, 154, 2915, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5881, 154, 2916, '1', '2025-02-23 20:11:14', '1', '2025-02-23 21:27:29', b'1', 1);
INSERT INTO `system_role_menu` VALUES (5882, 154, 100, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5883, 154, 2917, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5884, 154, 101, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5885, 154, 2918, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5886, 154, 2150, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5887, 154, 102, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5888, 154, 2151, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5889, 154, 103, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5890, 154, 2920, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5891, 154, 2152, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5892, 154, 104, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5893, 154, 105, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5894, 154, 106, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5895, 154, 2923, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5896, 154, 107, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5897, 154, 2924, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5898, 154, 108, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5899, 154, 2925, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5900, 154, 109, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5901, 154, 2926, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5902, 154, 110, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5903, 154, 2927, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5904, 154, 111, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5905, 154, 2928, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5906, 154, 112, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5907, 154, 2929, '1', '2025-02-23 20:11:14', '1', '2025-02-23 21:17:29', b'1', 1);
INSERT INTO `system_role_menu` VALUES (5908, 154, 113, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5909, 154, 2930, '1', '2025-02-23 20:11:14', '1', '2025-02-23 21:17:29', b'1', 1);
INSERT INTO `system_role_menu` VALUES (5910, 154, 114, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5911, 154, 1138, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5912, 154, 2931, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5913, 154, 1139, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5914, 154, 115, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5915, 154, 116, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5916, 154, 1143, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5917, 154, 2447, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5918, 154, 2448, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5919, 154, 2449, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5920, 154, 2453, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5921, 154, 2472, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5922, 154, 2478, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5923, 154, 2479, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5924, 154, 2480, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5925, 154, 2481, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5926, 154, 2482, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5927, 154, 2483, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5928, 154, 2739, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5929, 154, 2484, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5930, 154, 2740, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5931, 154, 2485, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5932, 154, 2486, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5933, 154, 2487, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5934, 154, 2488, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5935, 154, 2489, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5936, 154, 2490, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5937, 154, 2491, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5938, 154, 2492, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5939, 154, 2493, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5940, 154, 2494, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5941, 154, 2495, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5942, 154, 2497, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5943, 154, 1224, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5944, 154, 1225, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5945, 154, 1226, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5946, 154, 1237, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5947, 154, 1238, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5948, 154, 1242, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5949, 154, 1243, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5950, 154, 2525, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5951, 154, 1255, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5952, 154, 1256, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5953, 154, 1001, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5954, 154, 1260, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5955, 154, 1005, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5956, 154, 1261, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5957, 154, 1006, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5958, 154, 1263, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5959, 154, 1008, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5960, 154, 1264, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5961, 154, 1012, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5962, 154, 500, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5963, 154, 1013, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5964, 154, 501, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5965, 154, 1017, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5966, 154, 1021, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5967, 154, 2814, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5968, 154, 2815, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5969, 154, 2933, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5970, 154, 2934, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5971, 154, 2935, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5972, 154, 2936, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5973, 154, 2938, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5974, 154, 2944, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5975, 154, 2947, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', b'0', 1);
INSERT INTO `system_role_menu` VALUES (5976, 154, 2948, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', b'0', 1);

-- ----------------------------
-- Table structure for system_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_channel`;
CREATE TABLE `system_sms_channel`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                       `signature` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信签名',
                                       `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
                                       `status` tinyint NOT NULL COMMENT '开启状态',
                                       `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                       `api_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信 API 的账号',
                                       `api_secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 的秘钥',
                                       `callback_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信发送回调 URL',
                                       `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短信渠道' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_sms_channel
-- ----------------------------
INSERT INTO `system_sms_channel` VALUES (2, 'Ballcat', 'ALIYUN', 0, '你要改哦，只有我可以用！！！！', 'LTAI5tCnKso2uG3kJ5gRav88', 'fGJ5SNXL7P1NHNRmJ7DJaMJGPyE55C', NULL, '', '2021-03-31 11:53:10', '1', '2024-08-04 08:53:26', b'0');
INSERT INTO `system_sms_channel` VALUES (4, '测试渠道', 'DEBUG_DING_TALK', 0, '123', '696b5d8ead48071237e4aa5861ff08dbadb2b4ded1c688a7b7c9afc615579859', 'SEC5c4e5ff888bc8a9923ae47f59e7ccd30af1f14d93c55b4e2c9cb094e35aeed67', NULL, '1', '2021-04-13 00:23:14', '1', '2022-03-27 20:29:49', b'0');
INSERT INTO `system_sms_channel` VALUES (7, 'mock腾讯云', 'TENCENT', 0, '', '1 2', '2 3', '', '1', '2024-09-30 08:53:45', '1', '2024-09-30 08:55:01', b'0');

-- ----------------------------
-- Table structure for system_sms_code
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_code`;
CREATE TABLE `system_sms_code`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                    `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
                                    `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码',
                                    `create_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建 IP',
                                    `scene` tinyint NOT NULL COMMENT '发送场景',
                                    `today_index` tinyint NOT NULL COMMENT '今日发送的第几条',
                                    `used` tinyint NOT NULL COMMENT '是否使用',
                                    `used_time` datetime NULL DEFAULT NULL COMMENT '使用时间',
                                    `used_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '使用 IP',
                                    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `idx_mobile`(`mobile` ASC) USING BTREE COMMENT '手机号'
) ENGINE = InnoDB AUTO_INCREMENT = 645 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '手机验证码' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_sms_code
-- ----------------------------

-- ----------------------------
-- Table structure for system_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_log`;
CREATE TABLE `system_sms_log`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                   `channel_id` bigint NOT NULL COMMENT '短信渠道编号',
                                   `channel_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信渠道编码',
                                   `template_id` bigint NOT NULL COMMENT '模板编号',
                                   `template_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
                                   `template_type` tinyint NOT NULL COMMENT '短信类型',
                                   `template_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信内容',
                                   `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信参数',
                                   `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信 API 的模板编号',
                                   `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
                                   `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
                                   `user_type` tinyint NULL DEFAULT NULL COMMENT '用户类型',
                                   `send_status` tinyint NOT NULL DEFAULT 0 COMMENT '发送状态',
                                   `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
                                   `api_send_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 发送结果的编码',
                                   `api_send_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 发送失败的提示',
                                   `api_request_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 发送返回的唯一请求 ID',
                                   `api_serial_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 发送返回的序号',
                                   `receive_status` tinyint NOT NULL DEFAULT 0 COMMENT '接收状态',
                                   `receive_time` datetime NULL DEFAULT NULL COMMENT '接收时间',
                                   `api_receive_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'API 接收结果的编码',
                                   `api_receive_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'API 接收结果的说明',
                                   `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1241 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短信日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_template`;
CREATE TABLE `system_sms_template`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                        `type` tinyint NOT NULL COMMENT '模板类型',
                                        `status` tinyint NOT NULL COMMENT '开启状态',
                                        `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
                                        `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
                                        `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
                                        `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数数组',
                                        `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                        `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信 API 的模板编号',
                                        `channel_id` bigint NOT NULL COMMENT '短信渠道编号',
                                        `channel_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信渠道编码',
                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短信模板' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_sms_template
-- ----------------------------
INSERT INTO `system_sms_template` VALUES (2, 1, 0, 'test_01', '测试验证码短信', '正在进行登录操作{operation}，您的验证码是{code}', '[\"operation\",\"code\"]', '测试备注', '4383920', 4, 'DEBUG_DING_TALK', '', '2021-03-31 10:49:38', '1', '2024-08-18 11:57:18', b'0');
INSERT INTO `system_sms_template` VALUES (3, 1, 0, 'test_02', '公告通知', '您的验证码{code}，该验证码5分钟内有效，请勿泄漏于他人！', '[\"code\"]', NULL, 'SMS_207945135', 2, 'ALIYUN', '', '2021-03-31 11:56:30', '1', '2021-04-10 01:22:02', b'0');
INSERT INTO `system_sms_template` VALUES (6, 3, 0, 'test-01', '测试模板', '哈哈哈 {name}', '[\"name\"]', 'f哈哈哈', '4383920', 4, 'DEBUG_DING_TALK', '1', '2021-04-10 01:07:21', '1', '2024-08-18 11:57:07', b'0');
INSERT INTO `system_sms_template` VALUES (7, 3, 0, 'test-04', '测试下', '老鸡{name}，牛逼{code}', '[\"name\",\"code\"]', '哈哈哈哈', 'suibian', 7, 'DEBUG_DING_TALK', '1', '2021-04-13 00:29:53', '1', '2024-09-30 00:56:24', b'0');
INSERT INTO `system_sms_template` VALUES (8, 1, 0, 'user-sms-login', '前台用户短信登录', '您的验证码是{code}', '[\"code\"]', NULL, '4372216', 4, 'DEBUG_DING_TALK', '1', '2021-10-11 08:10:00', '1', '2024-08-18 11:57:06', b'0');
INSERT INTO `system_sms_template` VALUES (9, 2, 0, 'bpm_task_assigned', '【工作流】任务被分配', '您收到了一条新的待办任务：{processInstanceName}-{taskName}，申请人：{startUserNickname}，处理链接：{detailUrl}', '[\"processInstanceName\",\"taskName\",\"startUserNickname\",\"detailUrl\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-21 22:31:19', '1', '2022-01-22 00:03:36', b'0');
INSERT INTO `system_sms_template` VALUES (10, 2, 0, 'bpm_process_instance_reject', '【工作流】流程被不通过', '您的流程被审批不通过：{processInstanceName}，原因：{reason}，查看链接：{detailUrl}', '[\"processInstanceName\",\"reason\",\"detailUrl\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-22 00:03:31', '1', '2022-05-01 12:33:14', b'0');
INSERT INTO `system_sms_template` VALUES (11, 2, 0, 'bpm_process_instance_approve', '【工作流】流程被通过', '您的流程被审批通过：{processInstanceName}，查看链接：{detailUrl}', '[\"processInstanceName\",\"detailUrl\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-22 00:04:31', '1', '2022-03-27 20:32:21', b'0');
INSERT INTO `system_sms_template` VALUES (12, 2, 0, 'demo', '演示模板', '我就是测试一下下', '[]', NULL, 'biubiubiu', 4, 'DEBUG_DING_TALK', '1', '2022-04-10 23:22:49', '1', '2024-08-18 11:57:04', b'0');
INSERT INTO `system_sms_template` VALUES (14, 1, 0, 'user-update-mobile', '会员用户 - 修改手机', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '[\"code\"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-08-19 11:34:04', b'0');
INSERT INTO `system_sms_template` VALUES (15, 1, 0, 'user-update-password', '会员用户 - 修改密码', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '[\"code\"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-08-19 11:34:18', b'0');
INSERT INTO `system_sms_template` VALUES (16, 1, 0, 'user-reset-password', '会员用户 - 重置密码', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '[\"code\"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-12-02 22:35:27', b'0');
INSERT INTO `system_sms_template` VALUES (17, 2, 0, 'bpm_task_timeout', '【工作流】任务审批超时', '您收到了一条超时的待办任务：{processInstanceName}-{taskName}，处理链接：{detailUrl}', '[\"processInstanceName\",\"taskName\",\"detailUrl\"]', '', 'X', 4, 'DEBUG_DING_TALK', '1', '2024-08-16 21:59:15', '1', '2024-08-16 21:59:34', b'0');

-- ----------------------------
-- Table structure for system_social_client
-- ----------------------------
DROP TABLE IF EXISTS `system_social_client`;
CREATE TABLE `system_social_client`  (
                                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                         `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
                                         `social_type` tinyint NOT NULL COMMENT '社交平台的类型',
                                         `user_type` tinyint NOT NULL COMMENT '用户类型',
                                         `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
                                         `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端密钥',
                                         `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '代理编号',
                                         `status` tinyint NOT NULL COMMENT '状态',
                                         `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                         `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                         `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交客户端表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_social_client
-- ----------------------------
INSERT INTO `system_social_client` VALUES (1, '钉钉', 20, 2, 'dingvrnreaje3yqvzhxg', 'i8E6iZyDvZj51JIb0tYsYfVQYOks9Cq1lgryEjFRqC79P3iJcrxEwT6Qk2QvLrLI', NULL, 0, '', '2023-10-18 11:21:18', '1', '2023-12-20 21:28:26', b'1', 1);
INSERT INTO `system_social_client` VALUES (2, '钉钉（王土豆）', 20, 2, 'dingtsu9hpepjkbmthhw', 'FP_bnSq_HAHKCSncmJjw5hxhnzs6vaVDSZZn3egj6rdqTQ_hu5tQVJyLMpgCakdP', NULL, 0, '', '2023-10-18 11:21:18', '', '2023-12-20 21:28:26', b'1', 121);
INSERT INTO `system_social_client` VALUES (3, '微信公众号', 31, 1, 'wx5b23ba7a5589ecbb', '2a7b3b20c537e52e74afd395eb85f61f', NULL, 0, '', '2023-10-18 16:07:46', '1', '2023-12-20 21:28:23', b'1', 1);
INSERT INTO `system_social_client` VALUES (43, '微信小程序', 34, 1, 'wx63c280fe3248a3e7', '6f270509224a7ae1296bbf1c8cb97aed', NULL, 0, '', '2023-10-19 13:37:41', '1', '2023-12-20 21:28:25', b'1', 1);

-- ----------------------------
-- Table structure for system_social_user
-- ----------------------------
DROP TABLE IF EXISTS `system_social_user`;
CREATE TABLE `system_social_user`  (
                                       `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
                                       `type` tinyint NOT NULL COMMENT '社交平台的类型',
                                       `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交 openid',
                                       `token` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交 token',
                                       `raw_token_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始 Token 数据，一般是 JSON 格式',
                                       `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
                                       `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
                                       `raw_user_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始用户数据，一般是 JSON 格式',
                                       `code` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最后一次的认证 code',
                                       `state` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后一次的认证 state',
                                       `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                       `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_social_user
-- ----------------------------

-- ----------------------------
-- Table structure for system_social_user_bind
-- ----------------------------
DROP TABLE IF EXISTS `system_social_user_bind`;
CREATE TABLE `system_social_user_bind`  (
                                            `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
                                            `user_id` bigint NOT NULL COMMENT '用户编号',
                                            `user_type` tinyint NOT NULL COMMENT '用户类型',
                                            `social_type` tinyint NOT NULL COMMENT '社交平台的类型',
                                            `social_user_id` bigint NOT NULL COMMENT '社交用户的编号',
                                            `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                            `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                            `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交绑定表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_social_user_bind
-- ----------------------------

-- ----------------------------
-- Table structure for system_tenant
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant`;
CREATE TABLE `system_tenant`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '租户编号',
                                  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名',
                                  `contact_user_id` bigint NULL DEFAULT NULL COMMENT '联系人的用户编号',
                                  `contact_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人',
                                  `contact_mobile` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系手机',
                                  `status` tinyint NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
                                  `website` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '绑定域名',
                                  `package_id` bigint NOT NULL COMMENT '租户套餐编号',
                                  `expire_time` datetime NOT NULL COMMENT '过期时间',
                                  `account_count` int NOT NULL COMMENT '账号数量',
                                  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 162 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_tenant
-- ----------------------------
INSERT INTO `system_tenant` VALUES (1, 'EnjoyIot', NULL, '小E', '17321315478', 0, 'www.enjoy-iot.cn', 0, '2099-02-19 17:14:16', 9999, '1', '2021-01-05 17:03:47', '1', '2025-02-19 14:23:54', b'0');

-- ----------------------------
-- Table structure for system_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant_package`;
CREATE TABLE `system_tenant_package`  (
                                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '套餐编号',
                                          `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '套餐名',
                                          `status` tinyint NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
                                          `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
                                          `menu_ids` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联的菜单编号',
                                          `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                          `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户套餐表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_tenant_package
-- ----------------------------
INSERT INTO `system_tenant_package` VALUES (111, '普通套餐', 0, '小功能', '[1,2,5,1031,1032,1033,1034,1035,1036,1037,1038,1039,1050,1051,1052,1053,1054,1056,1057,1058,1059,1060,1063,1064,1065,1066,1067,1070,1075,1077,1078,1082,1083,1084,1085,1086,1087,1088,1089,1090,1091,1092,1118,1119,1120,100,101,102,103,106,107,110,111,112,113,1138,114,1139,115,1140,116,1141,1142,1143,2713,2714,2715,2716,2717,2718,2720,1185,2721,1186,2722,1187,2723,1188,2724,1189,2725,1190,2726,1191,2727,2472,1192,2728,1193,2729,1194,2730,1195,2731,1196,2732,1197,2733,2478,1198,2734,2479,1199,2735,2480,1200,2481,1201,2482,1202,2483,2739,2484,2740,2485,2486,2487,1207,2488,1208,2489,1209,2490,1210,2491,1211,2492,1212,2493,1213,2494,2495,1215,1216,2497,1217,1218,1219,1220,1221,1222,1224,1225,1226,1227,1228,1229,1237,1238,1239,1240,1241,1242,1243,2525,1255,1256,1001,1257,1002,1258,1003,1259,1004,1260,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020]', '1', '2022-02-22 00:54:00', '1', '2024-07-13 22:37:24', b'0');

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS `system_user_post`;
CREATE TABLE `system_user_post`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户ID',
                                     `post_id` bigint NOT NULL DEFAULT 0 COMMENT '岗位ID',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                     `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 126 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户岗位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user_post
-- ----------------------------
INSERT INTO `system_user_post` VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0', 1);
INSERT INTO `system_user_post` VALUES (113, 100, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0', 1);
INSERT INTO `system_user_post` VALUES (115, 104, 1, '1', '2022-05-16 19:36:28', '1', '2022-05-16 19:36:28', b'0', 1);
INSERT INTO `system_user_post` VALUES (116, 117, 2, '1', '2022-07-09 17:40:26', '1', '2022-07-09 17:40:26', b'0', 1);
INSERT INTO `system_user_post` VALUES (117, 118, 1, '1', '2022-07-09 17:44:44', '1', '2022-07-09 17:44:44', b'0', 1);
INSERT INTO `system_user_post` VALUES (119, 114, 5, '1', '2024-03-24 20:45:51', '1', '2024-03-24 20:45:51', b'0', 1);
INSERT INTO `system_user_post` VALUES (123, 115, 1, '1', '2024-04-04 09:37:14', '1', '2024-04-04 09:37:14', b'0', 1);
INSERT INTO `system_user_post` VALUES (124, 115, 2, '1', '2024-04-04 09:37:14', '1', '2024-04-04 09:37:14', b'0', 1);
INSERT INTO `system_user_post` VALUES (125, 1, 2, '1', '2024-07-13 22:31:39', '1', '2024-07-13 22:31:39', b'0', 1);

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
                                     `user_id` bigint NOT NULL COMMENT '用户ID',
                                     `role_id` bigint NOT NULL COMMENT '角色ID',
                                     `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                     `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                     `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
                                     `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:17', b'0', 1);
INSERT INTO `system_user_role` VALUES (2, 2, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', b'0', 1);
INSERT INTO `system_user_role` VALUES (4, 100, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', b'0', 1);
INSERT INTO `system_user_role` VALUES (5, 100, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:12', b'0', 1);
INSERT INTO `system_user_role` VALUES (6, 100, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', b'0', 1);
INSERT INTO `system_user_role` VALUES (10, 103, 1, '1', '2022-01-11 13:19:45', '1', '2022-01-11 13:19:45', b'0', 1);
INSERT INTO `system_user_role` VALUES (14, 110, 109, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', b'0', 121);
INSERT INTO `system_user_role` VALUES (15, 111, 110, '110', '2022-02-23 13:14:38', '110', '2022-02-23 13:14:38', b'0', 121);
INSERT INTO `system_user_role` VALUES (16, 113, 111, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_user_role` VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2022-05-12 20:39:29', b'0', 1);
INSERT INTO `system_user_role` VALUES (20, 104, 101, '1', '2022-05-28 15:43:57', '1', '2025-02-23 20:11:30', b'1', 1);
INSERT INTO `system_user_role` VALUES (22, 115, 2, '1', '2022-07-21 22:08:30', '1', '2022-07-21 22:08:30', b'0', 1);
INSERT INTO `system_user_role` VALUES (35, 112, 1, '1', '2024-03-15 20:00:24', '1', '2024-03-15 20:00:24', b'0', 1);
INSERT INTO `system_user_role` VALUES (36, 118, 1, '1', '2024-03-17 09:12:08', '1', '2024-03-17 09:12:08', b'0', 1);
INSERT INTO `system_user_role` VALUES (38, 114, 101, '1', '2024-03-24 22:23:03', '1', '2024-03-24 22:23:03', b'0', 1);
INSERT INTO `system_user_role` VALUES (46, 117, 1, '1', '2024-10-02 10:16:11', '1', '2024-10-02 10:16:11', b'0', 1);
INSERT INTO `system_user_role` VALUES (47, 104, 154, '1', '2025-02-23 20:11:31', '1', '2025-02-23 20:11:31', b'0', 1);

-- ----------------------------
-- Table structure for system_users
-- ----------------------------
DROP TABLE IF EXISTS `system_users`;
CREATE TABLE `system_users`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                                 `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
                                 `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
                                 `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
                                 `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                                 `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
                                 `post_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '岗位编号数组',
                                 `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
                                 `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
                                 `sex` tinyint NULL DEFAULT 0 COMMENT '用户性别',
                                 `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
                                 `status` tinyint NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
                                 `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
                                 `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
                                 `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                 `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 140 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_users
-- ----------------------------
INSERT INTO `system_users` VALUES (1, 'admin', '$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm', 'EnjoyIot', '管理员', 103, '[1,2]', 'xw2sy@163.com', '19918996474', 1, 'http://127.0.0.1:48080/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 0, '27.38.247.37', '2025-02-23 21:26:47', 'admin', '2021-01-05 17:03:47', NULL, '2025-02-23 21:26:47', b'0', 1);
INSERT INTO `system_users` VALUES (104, 'test', '$2a$04$KG9vBvKLO5PP9Tfd8ttF0.CDObtXPhMQb8I7vPEdVBSuI7uOx51Ey', '测试号', NULL, 107, '[1,2]', '111@qq.com', '15601691200', 1, '', 0, '27.38.247.37', '2025-02-23 21:25:34', '', '2021-01-21 02:13:53', NULL, '2025-02-23 21:25:34', b'0', 1);

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info`  (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id',
                              `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '任务名称',
                              `actions` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '任务输出',
                              `state` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '状态',
                              `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '任务类型',
                              `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                              `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                              `dept_id` bigint NULL DEFAULT 0 COMMENT '机构id',
                              `expression` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '表达式',
                              `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'iot任务' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of task_info
-- ----------------------------
INSERT INTO `task_info` VALUES (2, 'test1', '[{\"type\":\"device\",\"config\":\"{\\\"services\\\":[{\\\"device\\\":\\\"nechCiDrhjzzA2A5/test1111\\\",\\\"identifier\\\":\\\"set\\\",\\\"inputData\\\":[{\\\"identifier\\\":\\\"switch\\\",\\\"value\\\":\\\"1\\\"}]}],\\\"type\\\":\\\"device\\\"}\"}]', '0', 'timer', '1', '2025-02-16 19:26:23', '1', '2025-02-16 19:36:34', b'0', 1, 0, '', NULL);

SET FOREIGN_KEY_CHECKS = 1;
