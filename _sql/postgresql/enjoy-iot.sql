/*
 Yudao Database Transfer Tool

 Source Server Type    : MySQL

 Target Server Type    : PostgreSQL

 Date: 2025-07-10 11:47:35
*/


-- ----------------------------
-- Table structure for dual
-- ----------------------------
DROP TABLE IF EXISTS dual;
CREATE TABLE dual
(
    id int2
);

COMMENT ON TABLE dual IS '数据库连接的表';

-- ----------------------------
-- Records of dual
-- ----------------------------
-- @formatter:off
INSERT INTO dual VALUES (1);
-- @formatter:on

-- ----------------------------
-- Table structure for ai_algorithm
-- ----------------------------
DROP TABLE IF EXISTS ai_algorithm;
CREATE TABLE ai_algorithm (
    id int8 NOT NULL,
  name varchar(100) NOT NULL,
  framework varchar(50) NOT NULL,
  format varchar(50) NOT NULL,
  category varchar(50) NOT NULL,
  model_file varchar(255) NOT NULL,
  description text NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE ai_algorithm ADD CONSTRAINT pk_ai_algorithm PRIMARY KEY (id);

COMMENT ON COLUMN ai_algorithm.id IS '算法编号';
COMMENT ON COLUMN ai_algorithm.name IS '算法名称';
COMMENT ON COLUMN ai_algorithm.framework IS '模型框架';
COMMENT ON COLUMN ai_algorithm.format IS '模型格式';
COMMENT ON COLUMN ai_algorithm.category IS '模型类别';
COMMENT ON COLUMN ai_algorithm.model_file IS '模型文件';
COMMENT ON COLUMN ai_algorithm.description IS '算法描述';
COMMENT ON COLUMN ai_algorithm.creator IS '创建者';
COMMENT ON COLUMN ai_algorithm.create_time IS '创建时间';
COMMENT ON COLUMN ai_algorithm.updater IS '更新者';
COMMENT ON COLUMN ai_algorithm.update_time IS '更新时间';
COMMENT ON COLUMN ai_algorithm.deleted IS '是否删除';
COMMENT ON COLUMN ai_algorithm.tenant_id IS '租户编号';
COMMENT ON TABLE ai_algorithm IS 'AI算法表';

DROP SEQUENCE IF EXISTS ai_algorithm_seq;
CREATE SEQUENCE ai_algorithm_seq
    START 1;

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS channel;
CREATE TABLE channel (
    id int8 NOT NULL,
  code varchar(255) NOT NULL,
  icon varchar(255) NULL DEFAULT NULL,
  title varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE channel ADD CONSTRAINT pk_channel PRIMARY KEY (id);

COMMENT ON COLUMN channel.id IS 'id';
COMMENT ON COLUMN channel.code IS '通道名称';
COMMENT ON COLUMN channel.icon IS '图标';
COMMENT ON COLUMN channel.title IS '标题';
COMMENT ON COLUMN channel.creator IS '创建者';
COMMENT ON COLUMN channel.create_time IS '创建时间';
COMMENT ON COLUMN channel.updater IS '更新者';
COMMENT ON COLUMN channel.update_time IS '更新时间';
COMMENT ON COLUMN channel.deleted IS '是否删除';
COMMENT ON COLUMN channel.tenant_id IS '租户编号';
COMMENT ON COLUMN channel.dept_id IS '机构id';
COMMENT ON TABLE channel IS '通道';

-- ----------------------------
-- Records of channel
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO channel  VALUES (1, 'DingTalk', NULL, '钉钉', '', '2025-02-06 21:43:14', '', '2025-02-07 20:53:01', '0', 1, 0);
INSERT INTO channel  VALUES (2, 'QyWechat', NULL, '企业微信', '', '2025-02-06 21:43:36', '', '2025-02-07 20:53:02', '0', 1, 0);
INSERT INTO channel  VALUES (3, 'Email', NULL, '邮箱', '', '2025-02-06 21:43:55', '', '2025-02-07 20:53:03', '0', 1, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS channel_seq;
CREATE SEQUENCE channel_seq
    START 4;

-- ----------------------------
-- Table structure for device_ota_detail
-- ----------------------------
DROP TABLE IF EXISTS device_ota_detail;
CREATE TABLE device_ota_detail (
    id int8 NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  device_id int8 NULL DEFAULT NULL,
  device_name varchar(255) NULL DEFAULT NULL,
  module varchar(255) NULL DEFAULT NULL,
  ota_info_id int8 NULL DEFAULT NULL,
  product_key varchar(255) NULL DEFAULT NULL,
  step int4 NULL DEFAULT NULL,
  task_id int8 NULL DEFAULT NULL,
  version varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE device_ota_detail ADD CONSTRAINT pk_device_ota_detail PRIMARY KEY (id);

COMMENT ON COLUMN device_ota_detail.id IS 'id';
COMMENT ON COLUMN device_ota_detail.remark IS '备注';
COMMENT ON COLUMN device_ota_detail.device_id IS '设备id';
COMMENT ON COLUMN device_ota_detail.device_name IS '设备名称';
COMMENT ON COLUMN device_ota_detail.module IS '模块';
COMMENT ON COLUMN device_ota_detail.ota_info_id IS 'ota信息id';
COMMENT ON COLUMN device_ota_detail.product_key IS '产品key';
COMMENT ON COLUMN device_ota_detail.step IS '步骤';
COMMENT ON COLUMN device_ota_detail.task_id IS '任务id';
COMMENT ON COLUMN device_ota_detail.version IS '版本';
COMMENT ON COLUMN device_ota_detail.creator IS '创建者';
COMMENT ON COLUMN device_ota_detail.create_time IS '创建时间';
COMMENT ON COLUMN device_ota_detail.updater IS '更新者';
COMMENT ON COLUMN device_ota_detail.update_time IS '更新时间';
COMMENT ON COLUMN device_ota_detail.deleted IS '是否删除';
COMMENT ON COLUMN device_ota_detail.tenant_id IS '租户编号';
COMMENT ON COLUMN device_ota_detail.dept_id IS '机构id';
COMMENT ON TABLE device_ota_detail IS '设备ota详情';

-- ----------------------------
-- Records of device_ota_detail
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO device_ota_detail  VALUES (1, NULL, 1892227827321597952, '2', NULL, 15, NULL, 1, NULL, '2', '', '2025-02-21 00:53:38', '', '2025-02-21 01:04:13', '0', 1, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS device_ota_detail_seq;
CREATE SEQUENCE device_ota_detail_seq
    START 2;

-- ----------------------------
-- Table structure for device_ota_info
-- ----------------------------
DROP TABLE IF EXISTS device_ota_info;
CREATE TABLE device_ota_info (
    id int8 NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  fail int4 NULL DEFAULT NULL,
  module varchar(255) NULL DEFAULT NULL,
  package_id int8 NULL DEFAULT NULL,
  product_key varchar(255) NULL DEFAULT NULL,
  success int4 NULL DEFAULT NULL,
  total int4 NULL DEFAULT NULL,
  version varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE device_ota_info ADD CONSTRAINT pk_device_ota_info PRIMARY KEY (id);

COMMENT ON COLUMN device_ota_info.id IS 'id';
COMMENT ON COLUMN device_ota_info.remark IS '备注';
COMMENT ON COLUMN device_ota_info.fail IS '识别数量';
COMMENT ON COLUMN device_ota_info.module IS '模块';
COMMENT ON COLUMN device_ota_info.package_id IS '升级包id';
COMMENT ON COLUMN device_ota_info.product_key IS '产品key';
COMMENT ON COLUMN device_ota_info.success IS '成功数量';
COMMENT ON COLUMN device_ota_info.total IS '总数';
COMMENT ON COLUMN device_ota_info.version IS '版本';
COMMENT ON COLUMN device_ota_info.creator IS '创建者';
COMMENT ON COLUMN device_ota_info.create_time IS '创建时间';
COMMENT ON COLUMN device_ota_info.updater IS '更新者';
COMMENT ON COLUMN device_ota_info.update_time IS '更新时间';
COMMENT ON COLUMN device_ota_info.deleted IS '是否删除';
COMMENT ON COLUMN device_ota_info.tenant_id IS '租户编号';
COMMENT ON COLUMN device_ota_info.dept_id IS '机构id';
COMMENT ON TABLE device_ota_info IS '设备ota信息';

-- ----------------------------
-- Records of device_ota_info
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO device_ota_info  VALUES (1, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:44:20', '1', '2025-02-20 23:44:20', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (2, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:44:30', '1', '2025-02-20 23:44:30', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (3, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:45:36', '1', '2025-02-20 23:45:36', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (4, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:45:41', '1', '2025-02-20 23:45:41', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (5, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:45:58', '1', '2025-02-20 23:45:58', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (6, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:46:26', '1', '2025-02-20 23:46:26', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (7, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-20 23:46:53', '1', '2025-02-20 23:46:53', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (8, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-21 00:13:26', '1', '2025-02-21 00:13:26', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (9, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-21 00:17:15', '1', '2025-02-21 00:17:15', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (10, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-21 00:18:07', '1', '2025-02-21 00:18:07', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (11, NULL, 1, '22', 1, 'pfxt7ymAmc5TCdNE', 0, 1, '2', '1', '2025-02-21 00:18:22', '1', '2025-02-21 00:18:22', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (12, NULL, 1, 'j', 2, 'R755G5Wb3jst4tD7', 0, 1, 'jj', '1', '2025-02-21 00:19:06', '1', '2025-02-21 00:19:06', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (13, NULL, 2, 'j', 2, 'R755G5Wb3jst4tD7', 0, 2, 'jj', '1', '2025-02-21 00:19:41', '1', '2025-02-21 00:19:41', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (14, NULL, 2, 'j', 2, 'R755G5Wb3jst4tD7', 0, 2, 'jj', '1', '2025-02-21 00:20:06', '1', '2025-02-21 00:20:06', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (15, NULL, 1, 'j', 2, 'R755G5Wb3jst4tD7', 0, 1, 'jj', '1', '2025-02-21 00:21:52', '1', '2025-02-21 00:21:52', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (16, NULL, 0, 'j', 2, 'R755G5Wb3jst4tD7', 1, 1, 'jj', '1', '2025-02-21 00:32:15', '1', '2025-02-21 00:32:15', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (17, NULL, 0, 'j', 2, 'R755G5Wb3jst4tD7', 1, 1, 'jj', '1', '2025-02-21 00:33:17', '1', '2025-02-21 00:33:17', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (18, NULL, 0, 'j', 2, 'R755G5Wb3jst4tD7', 1, 1, 'jj', '1', '2025-02-21 00:33:36', '1', '2025-02-21 00:33:36', '0', 1, 0);
INSERT INTO device_ota_info  VALUES (19, NULL, 0, 'j', 2, 'R755G5Wb3jst4tD7', 1, 1, 'jj', '1', '2025-02-21 02:24:49', '1', '2025-02-21 02:24:49', '0', 1, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS device_ota_info_seq;
CREATE SEQUENCE device_ota_info_seq
    START 20;

-- ----------------------------
-- Table structure for eiot_alert_config
-- ----------------------------
DROP TABLE IF EXISTS eiot_alert_config;
CREATE TABLE eiot_alert_config (
    id int8 NOT NULL,
  name varchar(255) NOT NULL,
  message_template_id int8 NULL DEFAULT NULL,
  rule_info_id int8 NOT NULL,
  level varchar(255) NULL DEFAULT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  status int2 NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE eiot_alert_config ADD CONSTRAINT pk_eiot_alert_config PRIMARY KEY (id);

COMMENT ON COLUMN eiot_alert_config.id IS '告警配置id';
COMMENT ON COLUMN eiot_alert_config.name IS '告警名称';
COMMENT ON COLUMN eiot_alert_config.message_template_id IS '关联消息转发模板ID';
COMMENT ON COLUMN eiot_alert_config.rule_info_id IS '规则引擎id';
COMMENT ON COLUMN eiot_alert_config.level IS '告警等级';
COMMENT ON COLUMN eiot_alert_config.remark IS '备注';
COMMENT ON COLUMN eiot_alert_config.status IS '状态 ( 0启动 1禁用)';
COMMENT ON COLUMN eiot_alert_config.creator IS '创建者';
COMMENT ON COLUMN eiot_alert_config.create_time IS '创建时间';
COMMENT ON COLUMN eiot_alert_config.updater IS '更新者';
COMMENT ON COLUMN eiot_alert_config.update_time IS '更新时间';
COMMENT ON COLUMN eiot_alert_config.deleted IS '是否删除';
COMMENT ON COLUMN eiot_alert_config.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_alert_config.dept_id IS '机构id';
COMMENT ON TABLE eiot_alert_config IS '报警配置';

-- ----------------------------
-- Records of eiot_alert_config
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_alert_config  VALUES (1, '测试配置1', 4, 15, '2', NULL, 0, '1', '2025-02-10 09:13:44', '1', '2025-02-19 21:55:22', '0', 1, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_alert_config_seq;
CREATE SEQUENCE eiot_alert_config_seq
    START 2;

-- ----------------------------
-- Table structure for eiot_alert_record
-- ----------------------------
DROP TABLE IF EXISTS eiot_alert_record;
CREATE TABLE eiot_alert_record (
    id int8 NOT NULL,
  alert_time int8 NOT NULL,
  details varchar(255) NULL DEFAULT NULL,
  level varchar(255) NULL DEFAULT NULL,
  name varchar(255) NULL DEFAULT NULL,
  read_flg bool NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE eiot_alert_record ADD CONSTRAINT pk_eiot_alert_record PRIMARY KEY (id);

COMMENT ON COLUMN eiot_alert_record.id IS 'id';
COMMENT ON COLUMN eiot_alert_record.alert_time IS '告警时间';
COMMENT ON COLUMN eiot_alert_record.details IS '告警详情';
COMMENT ON COLUMN eiot_alert_record.level IS '告警等级';
COMMENT ON COLUMN eiot_alert_record.name IS '告警名称';
COMMENT ON COLUMN eiot_alert_record.read_flg IS '是否已读';
COMMENT ON COLUMN eiot_alert_record.creator IS '创建者';
COMMENT ON COLUMN eiot_alert_record.create_time IS '创建时间';
COMMENT ON COLUMN eiot_alert_record.updater IS '更新者';
COMMENT ON COLUMN eiot_alert_record.update_time IS '更新时间';
COMMENT ON COLUMN eiot_alert_record.deleted IS '是否删除';
COMMENT ON COLUMN eiot_alert_record.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_alert_record.dept_id IS '机构id';
COMMENT ON TABLE eiot_alert_record IS '告警记录';

-- ----------------------------
-- Records of eiot_alert_record
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_alert_record  VALUES (1, 1739090292, '测试告警记录', '1', '测试告警', '1', '1', '2025-02-09 16:38:16', '', '2025-02-16 17:55:37', '0', 1, 0);
INSERT INTO eiot_alert_record  VALUES (2, 1739975116106, '您的地块【${title}】<font color="warning">今天气温过高，请做好保湿作业</font>', '2', '测试配置1', '0', NULL, '2025-02-19 22:25:16', NULL, '2025-02-19 22:25:16', '0', 1, 0);
INSERT INTO eiot_alert_record  VALUES (3, 1739975231862, '您的地块【${title}】<font color="warning">今天气温过高，请做好保湿作业</font>', '2', '测试配置1', '0', NULL, '2025-02-19 22:27:12', NULL, '2025-02-19 22:27:12', '0', 1, 0);
INSERT INTO eiot_alert_record  VALUES (4, 1739975282563, '您的地块【${title}】<font color="warning">今天气温过高，请做好保湿作业</font>', '2', '测试配置1', '0', NULL, '2025-02-19 22:28:03', NULL, '2025-02-19 22:28:03', '0', 1, 0);
INSERT INTO eiot_alert_record  VALUES (5, 1739975330836, '您的地块【${title}】<font color="warning">今天气温过高，请做好保湿作业</font>', '2', '测试配置1', '0', NULL, '2025-02-19 22:28:51', NULL, '2025-02-19 22:28:51', '0', 1, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_alert_record_seq;
CREATE SEQUENCE eiot_alert_record_seq
    START 6;

-- ----------------------------
-- Table structure for eiot_category
-- ----------------------------
DROP TABLE IF EXISTS eiot_category;
CREATE TABLE eiot_category (
    id int8 NOT NULL,
  parent_id int8 NOT NULL DEFAULT 0,
  name varchar(255) NOT NULL,
  sort int4 NULL DEFAULT 0,
  status int2 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  img_url varchar(255) NULL DEFAULT NULL,
  is_sys int2 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_category ADD CONSTRAINT pk_eiot_category PRIMARY KEY (id);

COMMENT ON COLUMN eiot_category.id IS '分类id';
COMMENT ON COLUMN eiot_category.parent_id IS '父分类id';
COMMENT ON COLUMN eiot_category.name IS '分类名称';
COMMENT ON COLUMN eiot_category.sort IS '分类排序';
COMMENT ON COLUMN eiot_category.status IS '开启状态';
COMMENT ON COLUMN eiot_category.creator IS '创建者';
COMMENT ON COLUMN eiot_category.create_time IS '创建时间';
COMMENT ON COLUMN eiot_category.updater IS '更新者';
COMMENT ON COLUMN eiot_category.update_time IS '更新时间';
COMMENT ON COLUMN eiot_category.deleted IS '是否删除';
COMMENT ON COLUMN eiot_category.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_category.img_url IS '图片地址';
COMMENT ON COLUMN eiot_category.is_sys IS '是否系统通用（0-否，1-是）';
COMMENT ON TABLE eiot_category IS 'eiot产品分类';

-- ----------------------------
-- Records of eiot_category
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_category  VALUES (91, 0, '测试分类1', 0, 0, '1', '2025-02-10 11:12:03', '1', '2025-02-10 14:35:21', '0', 1, NULL, 1);
INSERT INTO eiot_category  VALUES (92, 91, '测试子分类1', 0, 0, '1', '2025-02-16 18:04:05', '1', '2025-02-16 18:04:05', '0', 1, NULL, 0);
INSERT INTO eiot_category  VALUES (93, 0, '摄像头', 2, 0, '1', '2025-02-16 18:04:26', '1', '2025-02-16 18:04:26', '0', 1, NULL, 0);
INSERT INTO eiot_category  VALUES (94, 0, '网关', 3, 0, '1', '2025-02-16 18:09:08', '1', '2025-02-16 18:09:17', '0', 1, NULL, 0);
INSERT INTO eiot_category  VALUES (95, 0, '传感器', 3, 0, '1', '2025-02-16 18:11:27', '1', '2025-02-16 18:11:27', '0', 1, NULL, 0);
INSERT INTO eiot_category  VALUES (96, 0, '水泵', 5, 0, '1', '2025-02-19 22:54:06', '1', '2025-02-19 22:54:06', '0', 1, NULL, 0);
INSERT INTO eiot_category  VALUES (97, 0, '猫砂盆', 7, 0, '1', '2025-02-19 22:55:16', '1', '2025-02-19 22:55:16', '0', 1, NULL, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_category_seq;
CREATE SEQUENCE eiot_category_seq
    START 98;

-- ----------------------------
-- Table structure for eiot_channel_config
-- ----------------------------
DROP TABLE IF EXISTS eiot_channel_config;
CREATE TABLE eiot_channel_config (
    id int8 NOT NULL,
  title varchar(255) NOT NULL,
  code varchar(255) NOT NULL,
  param text NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE eiot_channel_config ADD CONSTRAINT pk_eiot_channel_config PRIMARY KEY (id);

COMMENT ON COLUMN eiot_channel_config.id IS '通道配置id';
COMMENT ON COLUMN eiot_channel_config.title IS '配置名称';
COMMENT ON COLUMN eiot_channel_config.code IS '通道编码';
COMMENT ON COLUMN eiot_channel_config.param IS '通道配置参数';
COMMENT ON COLUMN eiot_channel_config.creator IS '创建者';
COMMENT ON COLUMN eiot_channel_config.create_time IS '创建时间';
COMMENT ON COLUMN eiot_channel_config.updater IS '更新者';
COMMENT ON COLUMN eiot_channel_config.update_time IS '更新时间';
COMMENT ON COLUMN eiot_channel_config.deleted IS '是否删除';
COMMENT ON COLUMN eiot_channel_config.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_channel_config.dept_id IS '机构id';
COMMENT ON TABLE eiot_channel_config IS '通道配置';

-- ----------------------------
-- Records of eiot_channel_config
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_channel_config  VALUES (1, '钉钉通道', 'DingTalk', '{"dingTalkSecret":"xxx","dingTalkWebhook":"http://xxx.com"}', '1', '2025-02-10 09:13:20', '1', '2025-02-10 09:13:20', '0', 1, 0);
INSERT INTO eiot_channel_config  VALUES (2, '企业微信通道', 'QyWechat', '{"qyWechatWebhook":"http://xxxx.com"}', '1', '2025-02-16 17:58:25', '1', '2025-02-16 17:58:25', '0', 1, 0);
INSERT INTO eiot_channel_config  VALUES (3, '邮箱通道', 'Email', '{"qyWechatWebhook":"http://xxxx.com","from":"xxxx@163.com","host":"smtp.163.com","userName":"xxx@163.com","passWord":"xxx1","to":"xxxx@163.com","mailSmtpAuth":true}', '1', '2025-02-16 17:59:50', '1', '2025-02-16 17:59:50', '0', 1, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_channel_config_seq;
CREATE SEQUENCE eiot_channel_config_seq
    START 4;

-- ----------------------------
-- Table structure for eiot_channel_template
-- ----------------------------
DROP TABLE IF EXISTS eiot_channel_template;
CREATE TABLE eiot_channel_template (
    id int8 NOT NULL,
  title varchar(255) NOT NULL,
  channel_config_id int8 NOT NULL,
  content varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE eiot_channel_template ADD CONSTRAINT pk_eiot_channel_template PRIMARY KEY (id);

COMMENT ON COLUMN eiot_channel_template.id IS '通道模板id';
COMMENT ON COLUMN eiot_channel_template.title IS '通道模板名称';
COMMENT ON COLUMN eiot_channel_template.channel_config_id IS '通道配置id';
COMMENT ON COLUMN eiot_channel_template.content IS '通道模板内容';
COMMENT ON COLUMN eiot_channel_template.creator IS '创建者';
COMMENT ON COLUMN eiot_channel_template.create_time IS '创建时间';
COMMENT ON COLUMN eiot_channel_template.updater IS '更新者';
COMMENT ON COLUMN eiot_channel_template.update_time IS '更新时间';
COMMENT ON COLUMN eiot_channel_template.deleted IS '是否删除';
COMMENT ON COLUMN eiot_channel_template.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_channel_template.dept_id IS '机构id';
COMMENT ON TABLE eiot_channel_template IS '通道模板';

-- ----------------------------
-- Records of eiot_channel_template
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_channel_template  VALUES (3, '企业微信模板', 2, '您的地块【${pressure}】<font color="warning">水分过低，请尽快浇水作业</font>\n', '1', '2025-02-09 23:07:46', '1', '2025-02-16 18:01:08', '0', 1, 0);
INSERT INTO eiot_channel_template  VALUES (4, '钉钉通道模板', 1, '您的地块【${title}】<font color="warning">今天气温过高，请做好保湿作业</font>', '1', '2025-02-16 18:00:51', '1', '2025-02-16 18:00:51', '0', 1, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_channel_template_seq;
CREATE SEQUENCE eiot_channel_template_seq
    START 5;

-- ----------------------------
-- Table structure for eiot_component
-- ----------------------------
DROP TABLE IF EXISTS eiot_component;
CREATE TABLE eiot_component (
    id int8 NOT NULL,
  name varchar(100) NOT NULL,
  type varchar(100) NOT NULL,
  config text NULL,
  status int2 NOT NULL DEFAULT 1,
  remark varchar(500) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_component ADD CONSTRAINT pk_eiot_component PRIMARY KEY (id);

COMMENT ON COLUMN eiot_component.id IS '组件编号';
COMMENT ON COLUMN eiot_component.name IS '组件名称';
COMMENT ON COLUMN eiot_component.type IS '组件类型';
COMMENT ON COLUMN eiot_component.config IS '组件配置 ( JSON格式)';
COMMENT ON COLUMN eiot_component.status IS '状态 ( 0-停用 ,  1-启用)';
COMMENT ON COLUMN eiot_component.remark IS '备注';
COMMENT ON COLUMN eiot_component.creator IS '创建者';
COMMENT ON COLUMN eiot_component.create_time IS '创建时间';
COMMENT ON COLUMN eiot_component.updater IS '更新者';
COMMENT ON COLUMN eiot_component.update_time IS '更新时间';
COMMENT ON COLUMN eiot_component.deleted IS '是否删除';
COMMENT ON TABLE eiot_component IS '组件配置';

-- ----------------------------
-- Records of eiot_component
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_component VALUES (1, '内置官方mqtt协议组件', 'mqtt', E'{\"port\":18831}', 1, NULL, NULL, '2025-02-19 01:01:54', '1', '2025-02-20 11:25:26', b'0');
INSERT INTO eiot_component VALUES (2, '内置emqx协议组件', 'emqx', E'{\n     \"port\": 1883,\n     \"host\": \"127.0.0.1\",\n     \"topics\": \"/sys/#\",\n     \"authPort\": 8104\n}', 0, NULL, NULL, '2025-02-19 01:01:54', '1', '2025-02-28 10:29:51', '0');
INSERT INTO eiot_component VALUES (3, '内置TCP协议组件', 'tcp', E'{\n     \"port\": 6666,\n     \"host\": \"127.0.0.1\"\n     \}', 1, null, null, '2025-02-19 01:01:54', '1', '2025-03-23 15:59:27', '0');
INSERT INTO eiot_component VALUES (4, '内置官方http协议组件', 'http', E'{\n     \"port\":18834,\n     \"ssl\": false,\n     \"sslKey\": \"\",\n     \"sslCert": \"\"\n     \}', 1, NULL, NULL, '2025-02-19 01:01:54', '1', '2025-02-20 11:25:26', '0');
INSERT INTO eiot_component VALUES (5, '有人云的USR-G770数传终端的modbus-tcp协议组件', 'modbus-custom', E'{\n    \"port\":18835,\n    \"timer\": 10\n}', 0, '产品手册：https://www.usr.cn/wiki/puba/p2xSMaXS4#top', NULL, '2025-02-19 01:01:54', '1', '2025-04-02 10:13:36', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_component_seq;
CREATE SEQUENCE eiot_component_seq
    START 6;

-- ----------------------------
-- Table structure for eiot_dept_product
-- ----------------------------
DROP TABLE IF EXISTS eiot_dept_product;
CREATE TABLE eiot_dept_product (
    id int8 NOT NULL,
  name varchar(64) NOT NULL,
  propertys text NULL,
  remark varchar(255) NULL DEFAULT NULL,
  dept_id int8 NULL DEFAULT NULL,
  status int2 NOT NULL DEFAULT 0,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  category_id int8 NOT NULL,
  product_key varchar(255) NULL DEFAULT NULL
);

ALTER TABLE eiot_dept_product ADD CONSTRAINT pk_eiot_dept_product PRIMARY KEY (id);

COMMENT ON COLUMN eiot_dept_product.id IS '客户产品id';
COMMENT ON COLUMN eiot_dept_product.name IS '客户产品名称';
COMMENT ON COLUMN eiot_dept_product.propertys IS '产品属性';
COMMENT ON COLUMN eiot_dept_product.remark IS '备注';
COMMENT ON COLUMN eiot_dept_product.dept_id IS '机构id';
COMMENT ON COLUMN eiot_dept_product.status IS '状态（0正常 1禁用 2未绑定）';
COMMENT ON COLUMN eiot_dept_product.creator IS '创建者';
COMMENT ON COLUMN eiot_dept_product.create_time IS '创建时间';
COMMENT ON COLUMN eiot_dept_product.updater IS '更新者';
COMMENT ON COLUMN eiot_dept_product.update_time IS '更新时间';
COMMENT ON COLUMN eiot_dept_product.deleted IS '是否删除';
COMMENT ON COLUMN eiot_dept_product.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_dept_product.category_id IS '分类id';
COMMENT ON COLUMN eiot_dept_product.product_key IS '内部产品key';
COMMENT ON TABLE eiot_dept_product IS '机构产品信息表';

DROP SEQUENCE IF EXISTS eiot_dept_product_seq;
CREATE SEQUENCE eiot_dept_product_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_dept_product_category
-- ----------------------------
DROP TABLE IF EXISTS eiot_dept_product_category;
CREATE TABLE eiot_dept_product_category (
    id int8 NOT NULL,
  parent_id int8 NOT NULL DEFAULT 0,
  name varchar(255) NOT NULL,
  sort int4 NULL DEFAULT 0,
  status int2 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  img_url varchar(255) NULL DEFAULT NULL,
  is_sys int2 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_dept_product_category ADD CONSTRAINT pk_eiot_dept_product_category PRIMARY KEY (id);

COMMENT ON COLUMN eiot_dept_product_category.id IS '分类id';
COMMENT ON COLUMN eiot_dept_product_category.parent_id IS '父分类id';
COMMENT ON COLUMN eiot_dept_product_category.name IS '分类名称';
COMMENT ON COLUMN eiot_dept_product_category.sort IS '分类排序';
COMMENT ON COLUMN eiot_dept_product_category.status IS '开启状态';
COMMENT ON COLUMN eiot_dept_product_category.creator IS '创建者';
COMMENT ON COLUMN eiot_dept_product_category.create_time IS '创建时间';
COMMENT ON COLUMN eiot_dept_product_category.updater IS '更新者';
COMMENT ON COLUMN eiot_dept_product_category.update_time IS '更新时间';
COMMENT ON COLUMN eiot_dept_product_category.deleted IS '是否删除';
COMMENT ON COLUMN eiot_dept_product_category.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_dept_product_category.img_url IS '图片地址';
COMMENT ON COLUMN eiot_dept_product_category.is_sys IS '是否系统通用（0-否，1-是）';
COMMENT ON TABLE eiot_dept_product_category IS 'eiot产品分类';

DROP SEQUENCE IF EXISTS eiot_dept_product_category_seq;
CREATE SEQUENCE eiot_dept_product_category_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_device_config
-- ----------------------------
DROP TABLE IF EXISTS eiot_device_config;
CREATE TABLE eiot_device_config (
    id int8 NOT NULL,
  config text NULL,
  product_key varchar(255) NOT NULL,
  dn varchar(255) NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_device_config ADD CONSTRAINT pk_eiot_device_config PRIMARY KEY (id);

COMMENT ON COLUMN eiot_device_config.id IS '配置id';
COMMENT ON COLUMN eiot_device_config.config IS '配置内容';
COMMENT ON COLUMN eiot_device_config.product_key IS '产品key';
COMMENT ON COLUMN eiot_device_config.dn IS '设备唯一编码';
COMMENT ON COLUMN eiot_device_config.creator IS '创建者';
COMMENT ON COLUMN eiot_device_config.create_time IS '创建时间';
COMMENT ON COLUMN eiot_device_config.updater IS '更新者';
COMMENT ON COLUMN eiot_device_config.update_time IS '更新时间';
COMMENT ON COLUMN eiot_device_config.deleted IS '是否删除';
COMMENT ON COLUMN eiot_device_config.tenant_id IS '租户编号';

DROP SEQUENCE IF EXISTS eiot_device_config_seq;
CREATE SEQUENCE eiot_device_config_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_device_info
-- ----------------------------
DROP TABLE IF EXISTS eiot_device_info;
CREATE TABLE eiot_device_info (
    id int8 NOT NULL,
  dn varchar(64) NOT NULL,
  product_key varchar(64) NOT NULL,
  dept_id int8 NULL DEFAULT 0,
  properties text NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  name varchar(64) NULL DEFAULT NULL,
  state int2 NULL DEFAULT NULL,
  offline_time int8 NULL DEFAULT NULL,
  online_time int8 NULL DEFAULT NULL,
  serial_no varchar(64) NOT NULL,
  remark varchar(64) NULL DEFAULT NULL,
  lat double precision NULL DEFAULT NULL,
  lon double precision NULL DEFAULT NULL,
  model varchar(255) NULL DEFAULT NULL,
  parent_id int8 NULL DEFAULT NULL,
  active_time timestamp NULL DEFAULT NULL,
  secret varchar(255) NULL DEFAULT NULL,
  firm_version varchar(255) NULL DEFAULT '1.0.0',
  addr varchar(255) NULL DEFAULT NULL,
  transparent bool NULL DEFAULT NULL,
  node_type int4 NOT NULL
);

ALTER TABLE eiot_device_info ADD CONSTRAINT pk_eiot_device_info PRIMARY KEY (id);

COMMENT ON COLUMN eiot_device_info.id IS '设备id';
COMMENT ON COLUMN eiot_device_info.dn IS '设备唯一标识';
COMMENT ON COLUMN eiot_device_info.product_key IS '产品key';
COMMENT ON COLUMN eiot_device_info.dept_id IS '机构id';
COMMENT ON COLUMN eiot_device_info.properties IS '设备属性';
COMMENT ON COLUMN eiot_device_info.creator IS '创建者';
COMMENT ON COLUMN eiot_device_info.create_time IS '创建时间';
COMMENT ON COLUMN eiot_device_info.updater IS '更新者';
COMMENT ON COLUMN eiot_device_info.update_time IS '更新时间';
COMMENT ON COLUMN eiot_device_info.deleted IS '是否删除';
COMMENT ON COLUMN eiot_device_info.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_device_info.name IS '别名';
COMMENT ON COLUMN eiot_device_info.state IS ' ( 0:否 ,  1:在线 ,  2-未激活，3-禁用 ) 设备状态';
COMMENT ON COLUMN eiot_device_info.offline_time IS '离线时间';
COMMENT ON COLUMN eiot_device_info.online_time IS '在线时间';
COMMENT ON COLUMN eiot_device_info.serial_no IS '设备序列号';
COMMENT ON COLUMN eiot_device_info.remark IS '备注';
COMMENT ON COLUMN eiot_device_info.lat IS '经纬度';
COMMENT ON COLUMN eiot_device_info.lon IS '经纬度';
COMMENT ON COLUMN eiot_device_info.model IS '类型';
COMMENT ON COLUMN eiot_device_info.parent_id IS '父id';
COMMENT ON COLUMN eiot_device_info.active_time IS '激活时间';
COMMENT ON COLUMN eiot_device_info.secret IS '设备密钥';
COMMENT ON COLUMN eiot_device_info.firm_version IS '固件版本';
COMMENT ON COLUMN eiot_device_info.addr IS '位置信息';
COMMENT ON COLUMN eiot_device_info.transparent IS '是否透传';
COMMENT ON COLUMN eiot_device_info.node_type IS '设备类型';
COMMENT ON TABLE eiot_device_info IS '设备主控表';

-- ----------------------------
-- Records of eiot_device_info
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_device_info  VALUES (10, 'test1111', 'nechCiDrhjzzA2A5', 0, NULL, '1', '2025-02-16 12:36:54', '1', '2025-02-16 21:16:14', '0', 1, '测试设备1', 0, 1739709001905, 1739711770952, 'A792BB92096F5493D92A3090C4AFDDB31', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '地址1111111', NULL, 0);
INSERT INTO eiot_device_info  VALUES (11, '133434', 'pfxt7ymAmc5TCdNE', 0, NULL, '1', '2025-02-16 20:31:03', '1', '2025-02-16 20:31:03', '0', 1, '测试设备2', 2, NULL, NULL, '133434', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (12, '1234', 'nechCiDrhjzzA2A5', 0, NULL, '1', '2025-02-16 20:31:48', '1', '2025-02-16 22:32:17', '0', 1, '测试产品-设备2', 0, 1739714515571, 1739716335656, 'A322A3B3427D34299B1DBA96686D2182A', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1891112439958269954, '2221212', 'hJEGr8DKPGnWmm5a', 0, NULL, '1', '2025-02-16 21:08:36', '1', '2025-02-16 21:08:36', '0', 1, '测试设备5', 2, NULL, NULL, 'A575C6CE2867945C99BDD450D113298B0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053956972531712, 'TEST:GW:000007', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:20:44', '0', 1, '测试设备1', 2, NULL, NULL, 'TEST:GW:000007', NULL, NULL, NULL, 'GW01', NULL, NULL, 'ltsBjVpytoPfRICM', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053957870112768, 'TEST:GW:000005', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:20:44', '0', 1, '测试设备2', 2, NULL, NULL, 'TEST:GW:000005', NULL, NULL, NULL, 'GW01', NULL, NULL, 'oQgdkVVxQOhrtrmZ', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053958574755840, 'TEST:GW:000006', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053958574755840', 2, NULL, NULL, 'TEST:GW:000006', NULL, NULL, NULL, 'GW01', NULL, NULL, 'tbgpeKdcVxHrjbMw', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053959220678656, 'TEST:GW:000001', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053959220678656', 2, NULL, NULL, 'TEST:GW:000001', NULL, NULL, NULL, 'GW01', NULL, NULL, 'PjCejtTEhzcuGCQQ', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053960084705280, 'TEST:GW:000009', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:51', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053960084705280', 2, NULL, NULL, 'TEST:GW:000009', NULL, NULL, NULL, 'GW01', NULL, NULL, 'JBaDJHpogBIoDHsI', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053960931954688, 'TEST:GW:000008', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053960931954688', 2, NULL, NULL, 'TEST:GW:000008', NULL, NULL, NULL, 'GW01', NULL, NULL, 'sgYqiISAkkMfFDPn', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053961762426880, 'TEST:GW:000002', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053961762426880', 2, NULL, NULL, 'TEST:GW:000002', NULL, NULL, NULL, 'GW01', NULL, NULL, 'UwkfMlrMzSpsVOOI', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053962605481984, 'TEST:GW:000004', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053962605481984', 2, NULL, NULL, 'TEST:GW:000004', NULL, NULL, NULL, 'GW01', NULL, NULL, 'fuyAEQuIaRGxUjqf', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053963456925696, 'TEST:GW:000000', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053963456925696', 2, NULL, NULL, 'TEST:GW:000000', NULL, NULL, NULL, 'GW01', NULL, NULL, 'GskQCHIhZKBhCuCl', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053964220289024, 'TEST:GW:000003', 'hbtgIA0SuVw9lxjB', 0, NULL, NULL, '2025-02-19 11:29:52', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053964220289024', 2, NULL, NULL, 'TEST:GW:000003', NULL, NULL, NULL, 'GW01', NULL, NULL, 'XwpNRVPezulweFhC', '1.0.0', NULL, NULL, 0);
INSERT INTO eiot_device_info  VALUES (1892053966527156224, 'TEST_SW_000007', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:53', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053966527156224', 2, NULL, NULL, 'TEST_SW_000007', NULL, NULL, NULL, 'S01', 1892053956972531712, NULL, 'gEkXdOtxKkHMEZyO', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053968246820864, 'TEST_SW_000005', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:53', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053968246820864', 2, NULL, NULL, 'TEST_SW_000005', NULL, NULL, NULL, 'S01', 1892053957870112768, NULL, 'QbiNVjllGPVUpiWP', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053969400254464, 'TEST_SW_000006', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:54', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053969400254464', 2, NULL, NULL, 'TEST_SW_000006', NULL, NULL, NULL, 'S01', 1892053958574755840, NULL, 'pZASQxssgpLZnlVu', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053970805346304, 'TEST_SW_000001', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:54', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053970805346304', 2, NULL, NULL, 'TEST_SW_000001', NULL, NULL, NULL, 'S01', 1892053959220678656, NULL, 'rxBQzjBpOTQKBpYc', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053972487262208, 'TEST_SW_000009', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:54', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053972487262208', 2, NULL, NULL, 'TEST_SW_000009', NULL, NULL, NULL, 'S01', 1892053960084705280, NULL, 'JqwLanBlvfhzLAHk', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053973711998976, 'TEST_SW_000008', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:55', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053973711998976', 2, NULL, NULL, 'TEST_SW_000008', NULL, NULL, NULL, 'S01', 1892053960931954688, NULL, 'wpsesUEuemtZKzkW', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053974882209792, 'TEST_SW_000002', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:55', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053974882209792', 2, NULL, NULL, 'TEST_SW_000002', NULL, NULL, NULL, 'S01', 1892053961762426880, NULL, 'NzGEexKedPgdvBLJ', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053976098557952, 'TEST_SW_000004', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:55', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053976098557952', 2, NULL, NULL, 'TEST_SW_000004', NULL, NULL, NULL, 'S01', 1892053962605481984, NULL, 'nmqnXTsUhSScIJwN', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053977256185856, 'TEST_SW_000000', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:55', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053977256185856', 2, NULL, NULL, 'TEST_SW_000000', NULL, NULL, NULL, 'S01', 1892053963456925696, NULL, 'pyQCJzvfPXMLRIEL', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892053979932151808, 'TEST_SW_000003', 'Rf4QSjbm65X45753', 0, NULL, NULL, '2025-02-19 11:29:56', NULL, '2025-02-19 15:22:10', '0', 1, '设备1892053979932151808', 2, NULL, NULL, 'TEST_SW_000003', NULL, NULL, NULL, 'S01', 1892053964220289024, NULL, 'UebFTNIxilNbncDt', '1.0.0', NULL, NULL, 1);
INSERT INTO eiot_device_info  VALUES (1892227036623990784, 'CC68', 'R755G5Wb3jst4tD7', 0, NULL, '1', '2025-02-19 22:57:36', '1', '2025-02-23 18:24:26', '0', 1, '我的猫砂盆', 0, 1740306266093, 1740263646879, 'CC68', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 2);
INSERT INTO eiot_device_info  VALUES (1892227827321597952, 'C18338', 'R755G5Wb3jst4tD7', 0, NULL, '1', '2025-02-19 23:00:45', '1', '2025-02-23 20:00:15', '0', 1, '猫砂盆', 0, 1740306265974, 1740263646876, 'C18338', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, 2);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_device_info_seq;
CREATE SEQUENCE eiot_device_info_seq
    START 1892227827321597953;

-- ----------------------------
-- Table structure for eiot_iot_device_group
-- ----------------------------
DROP TABLE IF EXISTS eiot_iot_device_group;
CREATE TABLE eiot_iot_device_group (
    id int8 NOT NULL,
  device_id int8 NOT NULL,
  group_id int8 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE eiot_iot_device_group ADD CONSTRAINT pk_eiot_iot_device_group PRIMARY KEY (id);

COMMENT ON COLUMN eiot_iot_device_group.id IS 'id';
COMMENT ON COLUMN eiot_iot_device_group.device_id IS '设备ID';
COMMENT ON COLUMN eiot_iot_device_group.group_id IS '分组ID';
COMMENT ON COLUMN eiot_iot_device_group.creator IS '创建者';
COMMENT ON COLUMN eiot_iot_device_group.create_time IS '创建时间';
COMMENT ON COLUMN eiot_iot_device_group.updater IS '更新者';
COMMENT ON COLUMN eiot_iot_device_group.update_time IS '更新时间';
COMMENT ON COLUMN eiot_iot_device_group.deleted IS '是否删除';
COMMENT ON COLUMN eiot_iot_device_group.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_iot_device_group.dept_id IS '机构id';
COMMENT ON TABLE eiot_iot_device_group IS '设备分组';

DROP SEQUENCE IF EXISTS eiot_iot_device_group_seq;
CREATE SEQUENCE eiot_iot_device_group_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_iot_group
-- ----------------------------
DROP TABLE IF EXISTS eiot_iot_group;
CREATE TABLE eiot_iot_group (
    id int8 NOT NULL,
  name varchar(64) NOT NULL,
  group_order int2 NOT NULL DEFAULT 0,
  uid int8 NOT NULL,
  user_name varchar(30) NOT NULL DEFAULT '',
  remark varchar(500) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0,
  typ int2 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_iot_group ADD CONSTRAINT pk_eiot_iot_group PRIMARY KEY (id);

CREATE INDEX idx_eiot_iot_group_01 ON eiot_iot_group (uid);

COMMENT ON COLUMN eiot_iot_group.id IS '分组ID';
COMMENT ON COLUMN eiot_iot_group.name IS '分组名称';
COMMENT ON COLUMN eiot_iot_group.group_order IS '分组排序';
COMMENT ON COLUMN eiot_iot_group.uid IS '用户ID';
COMMENT ON COLUMN eiot_iot_group.user_name IS '用户昵称';
COMMENT ON COLUMN eiot_iot_group.remark IS '备注';
COMMENT ON COLUMN eiot_iot_group.creator IS '创建者';
COMMENT ON COLUMN eiot_iot_group.create_time IS '创建时间';
COMMENT ON COLUMN eiot_iot_group.updater IS '更新者';
COMMENT ON COLUMN eiot_iot_group.update_time IS '更新时间';
COMMENT ON COLUMN eiot_iot_group.deleted IS '是否删除';
COMMENT ON COLUMN eiot_iot_group.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_iot_group.dept_id IS '机构id';
COMMENT ON COLUMN eiot_iot_group.typ IS '分组类型 ( 0系统 ,  1用户 ,  字典)';
COMMENT ON TABLE eiot_iot_group IS '设备分组';

-- ----------------------------
-- Records of eiot_iot_group
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_iot_group  VALUES (23, 'qqq', 0, 1, '', 'aa', '1', '2025-02-16 13:02:23', '1', '2025-02-16 13:02:23', '0', 1, 0, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_iot_group_seq;
CREATE SEQUENCE eiot_iot_group_seq
    START 24;

-- ----------------------------
-- Table structure for eiot_modbus_info
-- ----------------------------
DROP TABLE IF EXISTS eiot_modbus_info;
CREATE TABLE eiot_modbus_info (
    id int8 NOT NULL,
  name varchar(64) NOT NULL,
  product_key varchar(32) NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_modbus_info ADD CONSTRAINT pk_eiot_modbus_info PRIMARY KEY (id);

COMMENT ON COLUMN eiot_modbus_info.id IS '产品id';
COMMENT ON COLUMN eiot_modbus_info.name IS '产品名称';
COMMENT ON COLUMN eiot_modbus_info.product_key IS 'productKey';
COMMENT ON COLUMN eiot_modbus_info.remark IS '备注';
COMMENT ON COLUMN eiot_modbus_info.creator IS '创建者';
COMMENT ON COLUMN eiot_modbus_info.create_time IS '创建时间';
COMMENT ON COLUMN eiot_modbus_info.updater IS '更新者';
COMMENT ON COLUMN eiot_modbus_info.update_time IS '更新时间';
COMMENT ON COLUMN eiot_modbus_info.deleted IS '是否删除';
COMMENT ON COLUMN eiot_modbus_info.tenant_id IS '租户编号';
COMMENT ON TABLE eiot_modbus_info IS 'Modbus产品';

DROP SEQUENCE IF EXISTS eiot_modbus_info_seq;
CREATE SEQUENCE eiot_modbus_info_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_modbus_thing_model
-- ----------------------------
DROP TABLE IF EXISTS eiot_modbus_thing_model;
CREATE TABLE eiot_modbus_thing_model (
    id int8 NOT NULL,
  product_key varchar(32) NOT NULL,
  model text NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_modbus_thing_model ADD CONSTRAINT pk_eiot_modbus_thing_model PRIMARY KEY (id);

COMMENT ON COLUMN eiot_modbus_thing_model.id IS '产品id';
COMMENT ON COLUMN eiot_modbus_thing_model.product_key IS 'productKey';
COMMENT ON COLUMN eiot_modbus_thing_model.model IS '模型内容';
COMMENT ON COLUMN eiot_modbus_thing_model.creator IS '创建者';
COMMENT ON COLUMN eiot_modbus_thing_model.create_time IS '创建时间';
COMMENT ON COLUMN eiot_modbus_thing_model.updater IS '更新者';
COMMENT ON COLUMN eiot_modbus_thing_model.update_time IS '更新时间';
COMMENT ON COLUMN eiot_modbus_thing_model.deleted IS '是否删除';
COMMENT ON COLUMN eiot_modbus_thing_model.tenant_id IS '租户编号';

DROP SEQUENCE IF EXISTS eiot_modbus_thing_model_seq;
CREATE SEQUENCE eiot_modbus_thing_model_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_product
-- ----------------------------
DROP TABLE IF EXISTS eiot_product;
CREATE TABLE eiot_product (
    id int8 NOT NULL,
  name varchar(64) NOT NULL,
  category_id int8 NOT NULL,
  product_key varchar(32) NOT NULL,
  mcu_code varchar(32) NULL DEFAULT NULL,
  remark1 varchar(255) NULL DEFAULT NULL,
  img_url varchar(255) NULL DEFAULT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  status int2 NOT NULL DEFAULT 0,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  node_type int4 NOT NULL DEFAULT 0,
  protocol_code varchar(64) NOT NULL,
  keep_alive_time int8 NOT NULL DEFAULT 0,
  product_secret varchar(255) NOT NULL,
  transparent bool NOT NULL DEFAULT '0',
  locate_type int2 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_product ADD CONSTRAINT pk_eiot_product PRIMARY KEY (id);

COMMENT ON COLUMN eiot_product.id IS '产品id';
COMMENT ON COLUMN eiot_product.name IS '产品名称';
COMMENT ON COLUMN eiot_product.category_id IS '产品分类id';
COMMENT ON COLUMN eiot_product.product_key IS 'productKey';
COMMENT ON COLUMN eiot_product.mcu_code IS 'mcu code';
COMMENT ON COLUMN eiot_product.remark1 IS '功能介绍';
COMMENT ON COLUMN eiot_product.img_url IS '图片url';
COMMENT ON COLUMN eiot_product.remark IS '备注';
COMMENT ON COLUMN eiot_product.status IS '状态（0启用 1禁用）';
COMMENT ON COLUMN eiot_product.creator IS '创建者';
COMMENT ON COLUMN eiot_product.create_time IS '创建时间';
COMMENT ON COLUMN eiot_product.updater IS '更新者';
COMMENT ON COLUMN eiot_product.update_time IS '更新时间';
COMMENT ON COLUMN eiot_product.deleted IS '是否删除';
COMMENT ON COLUMN eiot_product.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_product.node_type IS '设备类型 ( 0 网关设备 ,  1 网关子设备 ,  2 直连设备 ,  3 非联网设备 )';
COMMENT ON COLUMN eiot_product.protocol_code IS '协议code';
COMMENT ON COLUMN eiot_product.keep_alive_time IS '保活时间';
COMMENT ON COLUMN eiot_product.product_secret IS '产品密钥';
COMMENT ON COLUMN eiot_product.transparent IS '是否透传';
COMMENT ON COLUMN eiot_product.locate_type IS '定位方式 ( 0无定位 ,  1自动上报 2手动定位)';
COMMENT ON TABLE eiot_product IS 'eiot产品';

-- ----------------------------
-- Records of eiot_product
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_product  VALUES (7, '测试产品1', 91, 'nechCiDrhjzzA2A5', 'N', NULL, NULL, NULL, 0, '1', '2025-02-10 14:36:14', '1', '2025-02-16 22:12:33', '0', 1, 0, 'GB28181', 100000, '343976f78a33462a9ff28a607d0f1b96', '1', 2);
INSERT INTO eiot_product  VALUES (8, '测试网关1', 94, 'pfxt7ymAmc5TCdNE', 'N', NULL, NULL, NULL, 0, '1', '2025-02-16 18:16:29', '1', '2025-02-16 18:16:29', '0', 1, 0, 'N', 0, '8ba846aa1031415c944ff66bc8a8d1ac', '0', 2);
INSERT INTO eiot_product  VALUES (9, '海康摄像头', 93, 'hJEGr8DKPGnWmm5a', 'N', NULL, NULL, NULL, 0, '1', '2025-02-16 18:17:04', '1', '2025-02-16 18:17:04', '0', 1, 0, 'GB28181', 0, '085adc41fb5d4b149884dbfece37072d', '1', 2);
INSERT INTO eiot_product  VALUES (10, '测试网关2', 94, 'hbtgIA0SuVw9lxjB', 'N', NULL, NULL, NULL, 0, '1', '2025-02-19 11:23:07', '1', '2025-02-19 11:23:25', '0', 1, 0, 'N', 60, '2793551687324e48a3963902e9750ec2', '0', 0);
INSERT INTO eiot_product  VALUES (11, '测试开关1', 91, 'Rf4QSjbm65X45753', 'N', NULL, NULL, NULL, 0, '1', '2025-02-19 11:24:44', '1', '2025-02-19 11:24:44', '0', 1, 1, 'N', 60, '6c7319ae198b475787b3101e26d85f82', '0', 0);
INSERT INTO eiot_product  VALUES (12, '有陪猫砂盆', 97, 'R755G5Wb3jst4tD7', 'N', NULL, NULL, NULL, 0, '1', '2025-02-19 22:56:23', '1', '2025-02-20 22:04:19', '0', 1, 2, 'N', 100, 'd8e035ad84d9449f9f19a0c0f3861f73', '0', 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_product_seq;
CREATE SEQUENCE eiot_product_seq
    START 13;

-- ----------------------------
-- Table structure for eiot_product_config
-- ----------------------------
DROP TABLE IF EXISTS eiot_product_config;
CREATE TABLE eiot_product_config (
    id int8 NOT NULL,
  product_key varchar(255) NOT NULL,
  config_items varchar(255) NOT NULL,
  config text NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_product_config ADD CONSTRAINT pk_eiot_product_config PRIMARY KEY (id);

COMMENT ON COLUMN eiot_product_config.id IS 'id';
COMMENT ON COLUMN eiot_product_config.product_key IS 'productKey';
COMMENT ON COLUMN eiot_product_config.config_items IS '配置项目';
COMMENT ON COLUMN eiot_product_config.config IS '配置内容';
COMMENT ON COLUMN eiot_product_config.creator IS '创建者';
COMMENT ON COLUMN eiot_product_config.create_time IS '创建时间';
COMMENT ON COLUMN eiot_product_config.updater IS '更新者';
COMMENT ON COLUMN eiot_product_config.update_time IS '更新时间';
COMMENT ON COLUMN eiot_product_config.deleted IS '是否删除';

DROP SEQUENCE IF EXISTS eiot_product_config_seq;
CREATE SEQUENCE eiot_product_config_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_rule_info
-- ----------------------------
DROP TABLE IF EXISTS eiot_rule_info;
CREATE TABLE eiot_rule_info (
    id int8 NOT NULL,
  name varchar(255) NOT NULL,
  listeners text NULL,
  filters text NULL,
  actions text NULL,
  typ varchar(255) NOT NULL,
  state int2 NULL DEFAULT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE eiot_rule_info ADD CONSTRAINT pk_eiot_rule_info PRIMARY KEY (id);

COMMENT ON COLUMN eiot_rule_info.id IS '规则id';
COMMENT ON COLUMN eiot_rule_info.name IS '规则名称';
COMMENT ON COLUMN eiot_rule_info.listeners IS '监听器';
COMMENT ON COLUMN eiot_rule_info.filters IS '过滤器';
COMMENT ON COLUMN eiot_rule_info.actions IS '动作';
COMMENT ON COLUMN eiot_rule_info.typ IS '类型 ( scene数据流转 flow场景联动)';
COMMENT ON COLUMN eiot_rule_info.state IS '状态 ( 0启用 1禁用)';
COMMENT ON COLUMN eiot_rule_info.remark IS '描述';
COMMENT ON COLUMN eiot_rule_info.creator IS '创建者';
COMMENT ON COLUMN eiot_rule_info.create_time IS '创建时间';
COMMENT ON COLUMN eiot_rule_info.updater IS '更新者';
COMMENT ON COLUMN eiot_rule_info.update_time IS '更新时间';
COMMENT ON COLUMN eiot_rule_info.deleted IS '是否删除';
COMMENT ON COLUMN eiot_rule_info.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_rule_info.dept_id IS '机构id';
COMMENT ON TABLE eiot_rule_info IS '规则引擎';

-- ----------------------------
-- Records of eiot_rule_info
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_rule_info VALUES (15, '测试规则1', '[{\"type\":\"device\",\"config\":\"{\\\"type\\\":\\\"device\\\",\\\"pk\\\":\\\"nechCiDrhjzzA2A5\\\",\\\"dn\\\":\\\"test1111\\\",\\\"conditions\\\":[{\\\"parameters\\\":[{\\\"identifier\\\":\\\"temp\\\",\\\"comparator\\\":\\\">\\\",\\\"value\\\":\\\"1\\\"}],\\\"identifier\\\":\\\"report\\\",\\\"type\\\":\\\"property\\\"}]}\"}]', '[]', '[{\"type\":\"alert\",\"config\":\"{\\\"services\\\":[{\\\"script\\\":\\\"this.translate = function(msg, device) {\\\\n        return {\\\\n                \\\\\\\"deviceName\\\\\\\": device.deviceName,\\\\n                \\\\\\\"reason\\\\\\\": \\\\\\\"电压异常\\\\\\\",\\\\n                \\\\\\\"currVal\\\\\\\": msg.data[\'temp\'],\\\\n                \\\\\\\"alertVal\\\\\\\": 1\\\\n        }\\\\n}\\\"}],\\\"type\\\":\\\"alert\\\",\\\"saved\\\":true}\"}]', 'scene', 1, NULL, '1', '2025-02-10 09:11:21', '1', '2025-02-19 22:41:54', b'0', 1, 0);
INSERT INTO eiot_rule_info VALUES (16, 'test1', '[{\"type\":\"device\",\"config\":\"{\\\"type\\\":\\\"device\\\",\\\"pk\\\":\\\"nechCiDrhjzzA2A5\\\",\\\"conditions\\\":[{\\\"parameters\\\":[{\\\"identifier\\\":\\\"switch\\\",\\\"comparator\\\":\\\"==\\\",\\\"value\\\":\\\"1\\\"}],\\\"identifier\\\":\\\"report\\\",\\\"type\\\":\\\"property\\\"}]}\"}]', '[]', '[{\"type\":\"alert\",\"config\":\"{\\\"services\\\":[{\\\"script\\\":\\\"this.translate=function(msg,device){\\\\nreturn {a:1};\\\\n}\\\"}],\\\"type\\\":\\\"alert\\\",\\\"saved\\\":true}\"}]', 'flow', NULL, NULL, '1', '2025-02-16 19:25:26', '1', '2025-02-19 22:40:28', b'1', 1, 0);
INSERT INTO eiot_rule_info VALUES (17, '联动测试1', '[{\"type\":\"device\",\"config\":\"{\\\"type\\\":\\\"device\\\",\\\"pk\\\":\\\"nechCiDrhjzzA2A5\\\",\\\"conditions\\\":[{\\\"parameters\\\":[{\\\"identifier\\\":\\\"spd\\\",\\\"comparator\\\":\\\">\\\",\\\"value\\\":\\\"1\\\"}],\\\"identifier\\\":\\\"report\\\",\\\"type\\\":\\\"property\\\"}]}\"}]', '[]', '[{\"type\":\"device\",\"config\":\"{\\\"services\\\":[{\\\"device\\\":\\\"nechCiDrhjzzA2A5/test1111\\\",\\\"identifier\\\":\\\"set\\\",\\\"inputData\\\":[{\\\"identifier\\\":\\\"switch\\\",\\\"value\\\":\\\"1\\\"}]}],\\\"type\\\":\\\"device\\\",\\\"saved\\\":true}\"}]', 'scene', 0, NULL, '1', '2025-02-19 22:41:33', '1', '2025-02-19 22:42:27', b'0', 1, 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_rule_info_seq;
CREATE SEQUENCE eiot_rule_info_seq
    START 18;

-- ----------------------------
-- Table structure for eiot_show_model
-- ----------------------------
DROP TABLE IF EXISTS eiot_show_model;
CREATE TABLE eiot_show_model (
    id int8 NOT NULL,
  cnf text NOT NULL,
  typ int4 NOT NULL,
  name varchar(64) NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  model_code varchar(64) NOT NULL,
  status int2 NOT NULL DEFAULT 0,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  product_key varchar(255) NULL DEFAULT NULL
);

ALTER TABLE eiot_show_model ADD CONSTRAINT pk_eiot_show_model PRIMARY KEY (id);

COMMENT ON COLUMN eiot_show_model.id IS '显示id';
COMMENT ON COLUMN eiot_show_model.cnf IS '显示内容';
COMMENT ON COLUMN eiot_show_model.typ IS '类型 0:详情页 ,  1配置页';
COMMENT ON COLUMN eiot_show_model.name IS '配置名称';
COMMENT ON COLUMN eiot_show_model.remark IS '备注';
COMMENT ON COLUMN eiot_show_model.model_code IS '模型code';
COMMENT ON COLUMN eiot_show_model.status IS '状态（0正常 1禁用）';
COMMENT ON COLUMN eiot_show_model.creator IS '创建者';
COMMENT ON COLUMN eiot_show_model.create_time IS '创建时间';
COMMENT ON COLUMN eiot_show_model.updater IS '更新者';
COMMENT ON COLUMN eiot_show_model.update_time IS '更新时间';
COMMENT ON COLUMN eiot_show_model.deleted IS '是否删除';
COMMENT ON COLUMN eiot_show_model.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_show_model.product_key IS 'productKey';
COMMENT ON TABLE eiot_show_model IS '产品显示模型';

DROP SEQUENCE IF EXISTS eiot_show_model_seq;
CREATE SEQUENCE eiot_show_model_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_thing_model
-- ----------------------------
DROP TABLE IF EXISTS eiot_thing_model;
CREATE TABLE eiot_thing_model (
    id int8 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  product_key varchar(255) NOT NULL,
  model text NULL
);

ALTER TABLE eiot_thing_model ADD CONSTRAINT pk_eiot_thing_model PRIMARY KEY (id);

COMMENT ON COLUMN eiot_thing_model.id IS '显示id';
COMMENT ON COLUMN eiot_thing_model.creator IS '创建者';
COMMENT ON COLUMN eiot_thing_model.create_time IS '创建时间';
COMMENT ON COLUMN eiot_thing_model.updater IS '更新者';
COMMENT ON COLUMN eiot_thing_model.update_time IS '更新时间';
COMMENT ON COLUMN eiot_thing_model.deleted IS '是否删除';
COMMENT ON COLUMN eiot_thing_model.tenant_id IS '租户编号';
COMMENT ON COLUMN eiot_thing_model.product_key IS '产品key';
COMMENT ON COLUMN eiot_thing_model.model IS '物模型';
COMMENT ON TABLE eiot_thing_model IS '产品物模型';

-- ----------------------------
-- Records of eiot_thing_model
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO eiot_thing_model  VALUES (7, '1', '2025-02-16 13:52:58', '1', '2025-02-16 20:38:38', '0', 1, 'nechCiDrhjzzA2A5', '{"properties":[{"identifier":"switch","dataType":{"type":"bool","specs":{"0":"关","1":"开"}},"name":"开关","accessMode":"rw","description":"","unit":null,"iconId":null,"icon":null,"proData":null},{"identifier":"temp","dataType":{"type":"int32","specs":{}},"name":"温度","accessMode":"r","description":"","unit":null,"iconId":null,"icon":null,"proData":null},{"identifier":"spd","dataType":{"type":"int32","specs":{"min":"0","max":"50000"}},"name":"转速","accessMode":"r","description":"","unit":null,"iconId":null,"icon":null,"proData":null},{"description":"","identifier":"pressure","name":"压力","dataType":{"specs":{},"type":"int32"},"accessMode":"r"}],"services":[],"events":[]}');
INSERT INTO eiot_thing_model  VALUES (8, '1', '2025-02-19 22:58:53', '1', '2025-02-19 22:59:48', '0', 1, 'R755G5Wb3jst4tD7', '{"properties":[{"identifier":"model","dataType":{"type":"enum","specs":{"0":"自动","1":"手动","2":"定时"}},"name":"模式","accessMode":"rw","description":"","unit":null,"iconId":null,"icon":null,"proData":null},{"description":"","identifier":"remain","name":"剩余猫砂","dataType":{"specs":{"min":"0","max":"100"},"type":"int32"},"accessMode":"r"}],"services":[],"events":[]}');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS eiot_thing_model_seq;
CREATE SEQUENCE eiot_thing_model_seq
    START 9;

-- ----------------------------
-- Table structure for eiot_virtual_device
-- ----------------------------
DROP TABLE IF EXISTS eiot_virtual_device;
CREATE TABLE eiot_virtual_device (
    id int8 NOT NULL,
  name varchar(255) NULL DEFAULT NULL,
  product_key varchar(255) NULL DEFAULT NULL,
  type varchar(32) NULL DEFAULT NULL,
  script text NULL,
  trigger varchar(32) NULL DEFAULT NULL,
  trigger_expression varchar(255) NULL DEFAULT NULL,
  state varchar(32) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_virtual_device ADD CONSTRAINT pk_eiot_virtual_device PRIMARY KEY (id);

COMMENT ON COLUMN eiot_virtual_device.id IS 'id';
COMMENT ON COLUMN eiot_virtual_device.name IS '虚拟设备名称';
COMMENT ON COLUMN eiot_virtual_device.product_key IS '产品key';
COMMENT ON COLUMN eiot_virtual_device.type IS '虚拟类型\ thingModel:物模型 protocol: 基于协议';
COMMENT ON COLUMN eiot_virtual_device.script IS '脚本';
COMMENT ON COLUMN eiot_virtual_device.trigger IS '触发执行方式\ none: 手动 cron:定时执行 random:随机执行';
COMMENT ON COLUMN eiot_virtual_device.trigger_expression IS '触发表达式';
COMMENT ON COLUMN eiot_virtual_device.state IS '运行状态 running: 运行中 stopped:已暂停';
COMMENT ON COLUMN eiot_virtual_device.creator IS '创建者';
COMMENT ON COLUMN eiot_virtual_device.create_time IS '创建时间';
COMMENT ON COLUMN eiot_virtual_device.updater IS '更新者';
COMMENT ON COLUMN eiot_virtual_device.update_time IS '更新时间';
COMMENT ON COLUMN eiot_virtual_device.deleted IS '是否删除';
COMMENT ON COLUMN eiot_virtual_device.tenant_id IS '租户编号';
COMMENT ON TABLE eiot_virtual_device IS '虚拟设备信息';

DROP SEQUENCE IF EXISTS eiot_virtual_device_seq;
CREATE SEQUENCE eiot_virtual_device_seq
    START 1;

-- ----------------------------
-- Table structure for eiot_virtual_device_mapping
-- ----------------------------
DROP TABLE IF EXISTS eiot_virtual_device_mapping;
CREATE TABLE eiot_virtual_device_mapping (
    id int8 NOT NULL,
  virtual_device_id int8 NULL DEFAULT NULL,
  device_id int8 NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT NULL,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE eiot_virtual_device_mapping ADD CONSTRAINT pk_eiot_virtual_device_mapping PRIMARY KEY (id);

COMMENT ON COLUMN eiot_virtual_device_mapping.id IS 'id';
COMMENT ON COLUMN eiot_virtual_device_mapping.virtual_device_id IS '虚拟设备id';
COMMENT ON COLUMN eiot_virtual_device_mapping.device_id IS '设备id';
COMMENT ON COLUMN eiot_virtual_device_mapping.creator IS '创建者';
COMMENT ON COLUMN eiot_virtual_device_mapping.create_time IS '创建时间';
COMMENT ON COLUMN eiot_virtual_device_mapping.updater IS '更新者';
COMMENT ON COLUMN eiot_virtual_device_mapping.update_time IS '更新时间';
COMMENT ON COLUMN eiot_virtual_device_mapping.deleted IS '是否删除';
COMMENT ON COLUMN eiot_virtual_device_mapping.tenant_id IS '租户编号';
COMMENT ON TABLE eiot_virtual_device_mapping IS '虚拟设备信息';

DROP SEQUENCE IF EXISTS eiot_virtual_device_mapping_seq;
CREATE SEQUENCE eiot_virtual_device_mapping_seq
    START 1;

-- ----------------------------
-- Table structure for infra_api_access_log
-- ----------------------------
DROP TABLE IF EXISTS infra_api_access_log;
CREATE TABLE infra_api_access_log (
    id int8 NOT NULL,
  trace_id varchar(64) NOT NULL DEFAULT '',
  user_id int8 NOT NULL DEFAULT 0,
  user_type int2 NOT NULL DEFAULT 0,
  application_name varchar(50) NOT NULL,
  request_method varchar(16) NOT NULL DEFAULT '',
  request_url varchar(255) NOT NULL DEFAULT '',
  request_params text NULL,
  response_body text NULL,
  user_ip varchar(50) NOT NULL,
  user_agent varchar(512) NOT NULL,
  operate_module varchar(50) NULL DEFAULT NULL,
  operate_name varchar(50) NULL DEFAULT NULL,
  operate_type int2 NULL DEFAULT 0,
  begin_time timestamp NOT NULL,
  end_time timestamp NOT NULL,
  duration int4 NOT NULL,
  result_code int4 NOT NULL DEFAULT 0,
  result_msg varchar(512) NULL DEFAULT '',
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE infra_api_access_log ADD CONSTRAINT pk_infra_api_access_log PRIMARY KEY (id);

CREATE INDEX idx_infra_api_access_log_01 ON infra_api_access_log (create_time);

COMMENT ON COLUMN infra_api_access_log.id IS '日志主键';
COMMENT ON COLUMN infra_api_access_log.trace_id IS '链路追踪编号';
COMMENT ON COLUMN infra_api_access_log.user_id IS '用户编号';
COMMENT ON COLUMN infra_api_access_log.user_type IS '用户类型';
COMMENT ON COLUMN infra_api_access_log.application_name IS '应用名';
COMMENT ON COLUMN infra_api_access_log.request_method IS '请求方法名';
COMMENT ON COLUMN infra_api_access_log.request_url IS '请求地址';
COMMENT ON COLUMN infra_api_access_log.request_params IS '请求参数';
COMMENT ON COLUMN infra_api_access_log.response_body IS '响应结果';
COMMENT ON COLUMN infra_api_access_log.user_ip IS '用户 IP';
COMMENT ON COLUMN infra_api_access_log.user_agent IS '浏览器 UA';
COMMENT ON COLUMN infra_api_access_log.operate_module IS '操作模块';
COMMENT ON COLUMN infra_api_access_log.operate_name IS '操作名';
COMMENT ON COLUMN infra_api_access_log.operate_type IS '操作分类';
COMMENT ON COLUMN infra_api_access_log.begin_time IS '开始请求时间';
COMMENT ON COLUMN infra_api_access_log.end_time IS '结束请求时间';
COMMENT ON COLUMN infra_api_access_log.duration IS '执行时长';
COMMENT ON COLUMN infra_api_access_log.result_code IS '结果码';
COMMENT ON COLUMN infra_api_access_log.result_msg IS '结果提示';
COMMENT ON COLUMN infra_api_access_log.creator IS '创建者';
COMMENT ON COLUMN infra_api_access_log.create_time IS '创建时间';
COMMENT ON COLUMN infra_api_access_log.updater IS '更新者';
COMMENT ON COLUMN infra_api_access_log.update_time IS '更新时间';
COMMENT ON COLUMN infra_api_access_log.deleted IS '是否删除';
COMMENT ON COLUMN infra_api_access_log.tenant_id IS '租户编号';
COMMENT ON TABLE infra_api_access_log IS 'API 访问日志表';

DROP SEQUENCE IF EXISTS infra_api_access_log_seq;
CREATE SEQUENCE infra_api_access_log_seq
    START 1;

-- ----------------------------
-- Table structure for infra_api_error_log
-- ----------------------------
DROP TABLE IF EXISTS infra_api_error_log;
CREATE TABLE infra_api_error_log (
    id int8 NOT NULL,
  trace_id varchar(64) NOT NULL,
  user_id int8 NOT NULL DEFAULT 0,
  user_type int2 NOT NULL DEFAULT 0,
  application_name varchar(50) NOT NULL,
  request_method varchar(16) NOT NULL,
  request_url varchar(255) NOT NULL,
  request_params varchar(8000) NOT NULL,
  user_ip varchar(50) NOT NULL,
  user_agent varchar(512) NOT NULL,
  exception_time timestamp NOT NULL,
  exception_name varchar(128) NOT NULL DEFAULT '',
  exception_message text NOT NULL,
  exception_root_cause_message text NOT NULL,
  exception_stack_trace text NOT NULL,
  exception_class_name varchar(512) NOT NULL,
  exception_file_name varchar(512) NOT NULL,
  exception_method_name varchar(512) NOT NULL,
  exception_line_number int4 NOT NULL,
  process_status int2 NOT NULL,
  process_time timestamp NULL DEFAULT NULL,
  process_user_id int4 NULL DEFAULT 0,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE infra_api_error_log ADD CONSTRAINT pk_infra_api_error_log PRIMARY KEY (id);

COMMENT ON COLUMN infra_api_error_log.id IS '编号';
COMMENT ON COLUMN infra_api_error_log.trace_id IS '链路追踪编号';
COMMENT ON COLUMN infra_api_error_log.user_id IS '用户编号';
COMMENT ON COLUMN infra_api_error_log.user_type IS '用户类型';
COMMENT ON COLUMN infra_api_error_log.application_name IS '应用名';
COMMENT ON COLUMN infra_api_error_log.request_method IS '请求方法名';
COMMENT ON COLUMN infra_api_error_log.request_url IS '请求地址';
COMMENT ON COLUMN infra_api_error_log.request_params IS '请求参数';
COMMENT ON COLUMN infra_api_error_log.user_ip IS '用户 IP';
COMMENT ON COLUMN infra_api_error_log.user_agent IS '浏览器 UA';
COMMENT ON COLUMN infra_api_error_log.exception_time IS '异常发生时间';
COMMENT ON COLUMN infra_api_error_log.exception_name IS '异常名';
COMMENT ON COLUMN infra_api_error_log.exception_message IS '异常导致的消息';
COMMENT ON COLUMN infra_api_error_log.exception_root_cause_message IS '异常导致的根消息';
COMMENT ON COLUMN infra_api_error_log.exception_stack_trace IS '异常的栈轨迹';
COMMENT ON COLUMN infra_api_error_log.exception_class_name IS '异常发生的类全名';
COMMENT ON COLUMN infra_api_error_log.exception_file_name IS '异常发生的类文件';
COMMENT ON COLUMN infra_api_error_log.exception_method_name IS '异常发生的方法名';
COMMENT ON COLUMN infra_api_error_log.exception_line_number IS '异常发生的方法所在行';
COMMENT ON COLUMN infra_api_error_log.process_status IS '处理状态';
COMMENT ON COLUMN infra_api_error_log.process_time IS '处理时间';
COMMENT ON COLUMN infra_api_error_log.process_user_id IS '处理用户编号';
COMMENT ON COLUMN infra_api_error_log.creator IS '创建者';
COMMENT ON COLUMN infra_api_error_log.create_time IS '创建时间';
COMMENT ON COLUMN infra_api_error_log.updater IS '更新者';
COMMENT ON COLUMN infra_api_error_log.update_time IS '更新时间';
COMMENT ON COLUMN infra_api_error_log.deleted IS '是否删除';
COMMENT ON COLUMN infra_api_error_log.tenant_id IS '租户编号';
COMMENT ON TABLE infra_api_error_log IS '系统异常日志';

-- ----------------------------
-- Records of infra_api_error_log
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO infra_api_error_log  VALUES (21292, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:35:05', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:35:05', NULL, '2025-02-23 06:35:05', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21293, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:35:15', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:35:15', NULL, '2025-02-23 06:35:15', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21294, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:35:25', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:35:25', NULL, '2025-02-23 06:35:25', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21295, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:35:34', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:35:34', NULL, '2025-02-23 06:35:34', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21296, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:36:40', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:36:40', NULL, '2025-02-23 06:36:40', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21297, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:38:27', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:38:27', NULL, '2025-02-23 06:38:27', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21298, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:39:13', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:39:13', NULL, '2025-02-23 06:39:13', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21299, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:46:13', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:46:13', NULL, '2025-02-23 06:46:13', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21300, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:49:58', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:49:58', NULL, '2025-02-23 06:49:58', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21301, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 06:51:44', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 06:51:44', NULL, '2025-02-23 06:51:44', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21302, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 07:59:15', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 07:59:15', NULL, '2025-02-23 07:59:15', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21303, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 07:59:23', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 07:59:23', NULL, '2025-02-23 07:59:23', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21304, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 08:00:07', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 08:00:07', NULL, '2025-02-23 08:00:07', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21305, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 08:03:28', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 08:03:28', NULL, '2025-02-23 08:03:28', '0', 0);
INSERT INTO infra_api_error_log  VALUES (21306, '', 0, 2, 'iot-server', 'GET', '/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', '{"query":{},"body":null}', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '2025-02-23 08:04:28', 'cn.hutool.core.io.IORuntimeException', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 'cn.hutool.core.io.IORuntimeException: File not exist: /Users/sjg/home/物联网/开源/enjoy-iot/server/target/classes/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png\n	at cn.hutool.core.io.file.FileReader.checkFile(FileReader.java:300)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:57)\n	at cn.hutool.core.io.file.FileReader.<init>(FileReader.java:93)\n	at cn.hutool.core.io.file.FileReader.create(FileReader.java:46)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2085)\n	at cn.hutool.core.io.FileUtil.readBytes(FileUtil.java:2098)\n	at com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClient.getContent(LocalFileClient.java:68)\n	at com.enjoyiot.module.infra.service.file.FileServiceImpl.getFileContent(FileServiceImpl.java:129)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController.getFileContent(FileController.java:109)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$FastClassBySpringCGLIB$$65c8df3b.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:792)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:762)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:707)\n	at com.enjoyiot.module.infra.controller.admin.file.FileController$$EnhancerBySpringCGLIB$$5502101e.getFileContent(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:903)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:809)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerM', 'cn.hutool.core.io.file.FileReader', 'FileReader.java', 'checkFile', 300, 0, NULL, 0, NULL, '2025-02-23 08:04:28', NULL, '2025-02-23 08:04:28', '0', 0);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS infra_api_error_log_seq;
CREATE SEQUENCE infra_api_error_log_seq
    START 21307;

-- ----------------------------
-- Table structure for infra_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS infra_codegen_column;
CREATE TABLE infra_codegen_column (
    id int8 NOT NULL,
  table_id int8 NOT NULL,
  column_name varchar(200) NOT NULL,
  data_type varchar(100) NOT NULL,
  column_comment varchar(500) NOT NULL,
  nullable bool NOT NULL,
  primary_key bool NOT NULL,
  ordinal_position int4 NOT NULL,
  java_type varchar(32) NOT NULL,
  java_field varchar(64) NOT NULL,
  dict_type varchar(200) NULL DEFAULT '',
  example varchar(64) NULL DEFAULT NULL,
  create_operation bool NOT NULL,
  update_operation bool NOT NULL,
  list_operation bool NOT NULL,
  list_operation_condition varchar(32) NOT NULL DEFAULT '=',
  list_operation_result bool NOT NULL,
  html_type varchar(32) NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_codegen_column ADD CONSTRAINT pk_infra_codegen_column PRIMARY KEY (id);

COMMENT ON COLUMN infra_codegen_column.id IS '编号';
COMMENT ON COLUMN infra_codegen_column.table_id IS '表编号';
COMMENT ON COLUMN infra_codegen_column.column_name IS '字段名';
COMMENT ON COLUMN infra_codegen_column.data_type IS '字段类型';
COMMENT ON COLUMN infra_codegen_column.column_comment IS '字段描述';
COMMENT ON COLUMN infra_codegen_column.nullable IS '是否允许为空';
COMMENT ON COLUMN infra_codegen_column.primary_key IS '是否主键';
COMMENT ON COLUMN infra_codegen_column.ordinal_position IS '排序';
COMMENT ON COLUMN infra_codegen_column.java_type IS 'Java 属性类型';
COMMENT ON COLUMN infra_codegen_column.java_field IS 'Java 属性名';
COMMENT ON COLUMN infra_codegen_column.dict_type IS '字典类型';
COMMENT ON COLUMN infra_codegen_column.example IS '数据示例';
COMMENT ON COLUMN infra_codegen_column.create_operation IS '是否为 Create 创建操作的字段';
COMMENT ON COLUMN infra_codegen_column.update_operation IS '是否为 Update 更新操作的字段';
COMMENT ON COLUMN infra_codegen_column.list_operation IS '是否为 List 查询操作的字段';
COMMENT ON COLUMN infra_codegen_column.list_operation_condition IS 'List 查询操作的条件类型';
COMMENT ON COLUMN infra_codegen_column.list_operation_result IS '是否为 List 查询操作的返回字段';
COMMENT ON COLUMN infra_codegen_column.html_type IS '显示类型';
COMMENT ON COLUMN infra_codegen_column.creator IS '创建者';
COMMENT ON COLUMN infra_codegen_column.create_time IS '创建时间';
COMMENT ON COLUMN infra_codegen_column.updater IS '更新者';
COMMENT ON COLUMN infra_codegen_column.update_time IS '更新时间';
COMMENT ON COLUMN infra_codegen_column.deleted IS '是否删除';
COMMENT ON TABLE infra_codegen_column IS '代码生成表字段定义';

DROP SEQUENCE IF EXISTS infra_codegen_column_seq;
CREATE SEQUENCE infra_codegen_column_seq
    START 1;

-- ----------------------------
-- Table structure for infra_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS infra_codegen_table;
CREATE TABLE infra_codegen_table (
    id int8 NOT NULL,
  data_source_config_id int8 NOT NULL,
  scene int2 NOT NULL DEFAULT 1,
  table_name varchar(200) NOT NULL DEFAULT '',
  table_comment varchar(500) NOT NULL DEFAULT '',
  remark varchar(500) NULL DEFAULT NULL,
  module_name varchar(30) NOT NULL,
  business_name varchar(30) NOT NULL,
  class_name varchar(100) NOT NULL DEFAULT '',
  class_comment varchar(50) NOT NULL,
  author varchar(50) NOT NULL,
  template_type int2 NOT NULL DEFAULT 1,
  front_type int2 NOT NULL,
  parent_menu_id int8 NULL DEFAULT NULL,
  master_table_id int8 NULL DEFAULT NULL,
  sub_join_column_id int8 NULL DEFAULT NULL,
  sub_join_many bool NULL DEFAULT NULL,
  tree_parent_column_id int8 NULL DEFAULT NULL,
  tree_name_column_id int8 NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_codegen_table ADD CONSTRAINT pk_infra_codegen_table PRIMARY KEY (id);

COMMENT ON COLUMN infra_codegen_table.id IS '编号';
COMMENT ON COLUMN infra_codegen_table.data_source_config_id IS '数据源配置的编号';
COMMENT ON COLUMN infra_codegen_table.scene IS '生成场景';
COMMENT ON COLUMN infra_codegen_table.table_name IS '表名称';
COMMENT ON COLUMN infra_codegen_table.table_comment IS '表描述';
COMMENT ON COLUMN infra_codegen_table.remark IS '备注';
COMMENT ON COLUMN infra_codegen_table.module_name IS '模块名';
COMMENT ON COLUMN infra_codegen_table.business_name IS '业务名';
COMMENT ON COLUMN infra_codegen_table.class_name IS '类名称';
COMMENT ON COLUMN infra_codegen_table.class_comment IS '类描述';
COMMENT ON COLUMN infra_codegen_table.author IS '作者';
COMMENT ON COLUMN infra_codegen_table.template_type IS '模板类型';
COMMENT ON COLUMN infra_codegen_table.front_type IS '前端类型';
COMMENT ON COLUMN infra_codegen_table.parent_menu_id IS '父菜单编号';
COMMENT ON COLUMN infra_codegen_table.master_table_id IS '主表的编号';
COMMENT ON COLUMN infra_codegen_table.sub_join_column_id IS '子表关联主表的字段编号';
COMMENT ON COLUMN infra_codegen_table.sub_join_many IS '主表与子表是否一对多';
COMMENT ON COLUMN infra_codegen_table.tree_parent_column_id IS '树表的父字段编号';
COMMENT ON COLUMN infra_codegen_table.tree_name_column_id IS '树表的名字字段编号';
COMMENT ON COLUMN infra_codegen_table.creator IS '创建者';
COMMENT ON COLUMN infra_codegen_table.create_time IS '创建时间';
COMMENT ON COLUMN infra_codegen_table.updater IS '更新者';
COMMENT ON COLUMN infra_codegen_table.update_time IS '更新时间';
COMMENT ON COLUMN infra_codegen_table.deleted IS '是否删除';
COMMENT ON TABLE infra_codegen_table IS '代码生成表定义';

DROP SEQUENCE IF EXISTS infra_codegen_table_seq;
CREATE SEQUENCE infra_codegen_table_seq
    START 1;

-- ----------------------------
-- Table structure for infra_config
-- ----------------------------
DROP TABLE IF EXISTS infra_config;
CREATE TABLE infra_config (
    id int8 NOT NULL,
  category varchar(50) NOT NULL,
  type int2 NOT NULL,
  name varchar(100) NOT NULL DEFAULT '',
  config_key varchar(100) NOT NULL DEFAULT '',
  value varchar(500) NOT NULL DEFAULT '',
  visible bool NOT NULL,
  remark varchar(500) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_config ADD CONSTRAINT pk_infra_config PRIMARY KEY (id);

COMMENT ON COLUMN infra_config.id IS '参数主键';
COMMENT ON COLUMN infra_config.category IS '参数分组';
COMMENT ON COLUMN infra_config.type IS '参数类型';
COMMENT ON COLUMN infra_config.name IS '参数名称';
COMMENT ON COLUMN infra_config.config_key IS '参数键名';
COMMENT ON COLUMN infra_config.value IS '参数键值';
COMMENT ON COLUMN infra_config.visible IS '是否可见';
COMMENT ON COLUMN infra_config.remark IS '备注';
COMMENT ON COLUMN infra_config.creator IS '创建者';
COMMENT ON COLUMN infra_config.create_time IS '创建时间';
COMMENT ON COLUMN infra_config.updater IS '更新者';
COMMENT ON COLUMN infra_config.update_time IS '更新时间';
COMMENT ON COLUMN infra_config.deleted IS '是否删除';
COMMENT ON TABLE infra_config IS '参数配置表';

-- ----------------------------
-- Records of infra_config
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO infra_config  VALUES (2, 'biz', 1, '用户管理-账号初始密码', 'system.user.init-password', '123456', '0', '初始化密码 123456', 'admin', '2021-01-05 17:03:48', '1', '2024-07-20 17:22:47', '0');
INSERT INTO infra_config  VALUES (7, 'url', 2, 'MySQL 监控的地址', 'url.druid', '', '1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:33:38', '0');
INSERT INTO infra_config  VALUES (8, 'url', 2, 'SkyWalking 监控的地址', 'url.skywalking', '', '1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:57:03', '0');
INSERT INTO infra_config  VALUES (9, 'url', 2, 'Spring Boot Admin 监控的地址', 'url.spring-boot-admin', '', '1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:52:07', '0');
INSERT INTO infra_config  VALUES (10, 'url', 2, 'Swagger 接口文档的地址', 'url.swagger', '', '1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:59:00', '0');
INSERT INTO infra_config  VALUES (11, 'ui', 2, '腾讯地图 key', 'tencent.lbs.key', 'TVDBZ-TDILD-4ON4B-PFDZA-RNLKH-VVF6E', '1', '腾讯地图 key', '1', '2023-06-03 19:16:27', '1', '2023-06-03 19:16:27', '0');
INSERT INTO infra_config  VALUES (12, 'test2', 2, 'test3', 'test4', 'test5', '1', 'test6', '1', '2023-12-03 09:55:16', '1', '2023-12-03 09:55:27', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS infra_config_seq;
CREATE SEQUENCE infra_config_seq
    START 13;

-- ----------------------------
-- Table structure for infra_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS infra_data_source_config;
CREATE TABLE infra_data_source_config (
    id int8 NOT NULL,
  name varchar(100) NOT NULL DEFAULT '',
  url varchar(1024) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL DEFAULT '',
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_data_source_config ADD CONSTRAINT pk_infra_data_source_config PRIMARY KEY (id);

COMMENT ON COLUMN infra_data_source_config.id IS '主键编号';
COMMENT ON COLUMN infra_data_source_config.name IS '参数名称';
COMMENT ON COLUMN infra_data_source_config.url IS '数据源连接';
COMMENT ON COLUMN infra_data_source_config.username IS '用户名';
COMMENT ON COLUMN infra_data_source_config.password IS '密码';
COMMENT ON COLUMN infra_data_source_config.creator IS '创建者';
COMMENT ON COLUMN infra_data_source_config.create_time IS '创建时间';
COMMENT ON COLUMN infra_data_source_config.updater IS '更新者';
COMMENT ON COLUMN infra_data_source_config.update_time IS '更新时间';
COMMENT ON COLUMN infra_data_source_config.deleted IS '是否删除';
COMMENT ON TABLE infra_data_source_config IS '数据源配置表';

DROP SEQUENCE IF EXISTS infra_data_source_config_seq;
CREATE SEQUENCE infra_data_source_config_seq
    START 1;

-- ----------------------------
-- Table structure for infra_file
-- ----------------------------
DROP TABLE IF EXISTS infra_file;
CREATE TABLE infra_file (
    id int8 NOT NULL,
  config_id int8 NULL DEFAULT NULL,
  name varchar(256) NULL DEFAULT NULL,
  path varchar(512) NOT NULL,
  url varchar(1024) NOT NULL,
  type varchar(128) NULL DEFAULT NULL,
  size int4 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_file ADD CONSTRAINT pk_infra_file PRIMARY KEY (id);

COMMENT ON COLUMN infra_file.id IS '文件编号';
COMMENT ON COLUMN infra_file.config_id IS '配置编号';
COMMENT ON COLUMN infra_file.name IS '文件名';
COMMENT ON COLUMN infra_file.path IS '文件路径';
COMMENT ON COLUMN infra_file.url IS '文件 URL';
COMMENT ON COLUMN infra_file.type IS '文件类型';
COMMENT ON COLUMN infra_file.size IS '文件大小';
COMMENT ON COLUMN infra_file.creator IS '创建者';
COMMENT ON COLUMN infra_file.create_time IS '创建时间';
COMMENT ON COLUMN infra_file.updater IS '更新者';
COMMENT ON COLUMN infra_file.update_time IS '更新时间';
COMMENT ON COLUMN infra_file.deleted IS '是否删除';
COMMENT ON TABLE infra_file IS '文件表';

DROP SEQUENCE IF EXISTS infra_file_seq;
CREATE SEQUENCE infra_file_seq
    START 1;

-- ----------------------------
-- Table structure for infra_file_config
-- ----------------------------
DROP TABLE IF EXISTS infra_file_config;
CREATE TABLE infra_file_config (
    id int8 NOT NULL,
  name varchar(63) NOT NULL,
  storage int2 NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  master bool NOT NULL,
  config varchar(4096) NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_file_config ADD CONSTRAINT pk_infra_file_config PRIMARY KEY (id);

COMMENT ON COLUMN infra_file_config.id IS '编号';
COMMENT ON COLUMN infra_file_config.name IS '配置名';
COMMENT ON COLUMN infra_file_config.storage IS '存储器';
COMMENT ON COLUMN infra_file_config.remark IS '备注';
COMMENT ON COLUMN infra_file_config.master IS '是否为主配置';
COMMENT ON COLUMN infra_file_config.config IS '存储配置';
COMMENT ON COLUMN infra_file_config.creator IS '创建者';
COMMENT ON COLUMN infra_file_config.create_time IS '创建时间';
COMMENT ON COLUMN infra_file_config.updater IS '更新者';
COMMENT ON COLUMN infra_file_config.update_time IS '更新时间';
COMMENT ON COLUMN infra_file_config.deleted IS '是否删除';
COMMENT ON TABLE infra_file_config IS '文件配置表';

-- ----------------------------
-- Records of infra_file_config
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO infra_file_config  VALUES (4, '数据库（示例）', 1, '我是数据库', '0', '{"@class":"com.enjoyiot.module.infra.framework.file.core.client.db.DBFileClientConfig","domain":"http://127.0.0.1:48080"}', '1', '2022-03-15 23:56:24', '1', '2025-02-20 21:29:45', '0');
INSERT INTO infra_file_config  VALUES (22, '七牛存储器（示例）', 20, '请换成你自己的密钥！！！', '0', '{"@class":"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig","endpoint":"s3.cn-south-1.qiniucs.com","domain":"http://test.enjoy-iot.cn","bucket":"ENJOY","accessKey":"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS","accessSecret":"wd0tbVBEiotp0S-ihA8Qg2hPLncoP83wyrIq24OZuY"}', '1', '2024-01-13 22:11:12', '1', '2025-02-20 21:29:45', '0');
INSERT INTO infra_file_config  VALUES (24, '腾讯云存储（示例）', 20, '请换成你的密钥！！！', '0', '{"@class":"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig","endpoint":"https://cos.ap-shanghai.myqcloud.com","domain":"http://tengxun-oss.enjoy-iot.cn","bucket":"aoteman-1255880240","accessKey":"AKIDAF6WSh1uiIjwqtrOsGSN3WryqTM6cTMt","accessSecret":"X"}', '1', '2024-11-09 16:03:22', '1', '2025-02-20 21:29:45', '0');
INSERT INTO infra_file_config  VALUES (25, '阿里云存储（示例）', 20, '', '0', '{"@class":"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig","endpoint":"oss-cn-beijing.aliyuncs.com","domain":"http://ali-oss.enjoy-iot.cn","bucket":"ENJOY","accessKey":"LTAI5tEQLgnDyjh3WpNcdMKA","accessSecret":"X"}', '1', '2024-11-09 16:47:08', '1', '2025-02-20 21:29:45', '0');
INSERT INTO infra_file_config  VALUES (26, '火山云存储（示例）', 20, '', '0', '{"@class":"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig","endpoint":"tos-s3-cn-beijing.volces.com","domain":null,"bucket":"ENJOY","accessKey":"AKLTZjc3Zjc4MzZmMjU3NDk0ZTgxYmIyMmFkNTIwMDI1ZGE","accessSecret":"X=="}', '1', '2024-11-09 16:56:42', '1', '2025-02-20 21:29:45', '0');
INSERT INTO infra_file_config  VALUES (27, '华为云存储（示例）', 20, '', '0', '{"@class":"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig","endpoint":"obs.cn-east-3.myhuaweicloud.com","domain":"","bucket":"enjoy-iot","accessKey":"PVDONDEIOTW88LF8DC4U","accessSecret":"X"}', '1', '2024-11-09 17:18:41', '1', '2025-02-20 21:29:45', '0');
INSERT INTO infra_file_config  VALUES (28, 'MinIO 存储（示例）', 20, '', '0', '{"@class":"com.enjoyiot.module.infra.framework.file.core.client.s3.S3FileClientConfig","endpoint":"http://eiot-server-minio:9000","domain":"main","bucket":"eiot","accessKey":"minio","accessSecret":"xxxx"}', '1', '2024-11-09 17:43:10', '1', '2025-02-20 21:29:45', '0');
INSERT INTO infra_file_config  VALUES (29, '本地存储', 10, '', '1', '{"@class":"com.enjoyiot.module.infra.framework.file.core.client.local.LocalFileClientConfig","basePath":"./","domain":"http://127.0.0.1:48080"}', '1', '2025-02-20 21:29:40', '1', '2025-02-20 23:45:05', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS infra_file_config_seq;
CREATE SEQUENCE infra_file_config_seq
    START 30;

-- ----------------------------
-- Table structure for infra_file_content
-- ----------------------------
DROP TABLE IF EXISTS infra_file_content;
CREATE TABLE infra_file_content (
    id int8 NOT NULL,
  config_id int8 NOT NULL,
  path varchar(512) NOT NULL,
  content bytea NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_file_content ADD CONSTRAINT pk_infra_file_content PRIMARY KEY (id);

COMMENT ON COLUMN infra_file_content.id IS '编号';
COMMENT ON COLUMN infra_file_content.config_id IS '配置编号';
COMMENT ON COLUMN infra_file_content.path IS '文件路径';
COMMENT ON COLUMN infra_file_content.content IS '文件内容';
COMMENT ON COLUMN infra_file_content.creator IS '创建者';
COMMENT ON COLUMN infra_file_content.create_time IS '创建时间';
COMMENT ON COLUMN infra_file_content.updater IS '更新者';
COMMENT ON COLUMN infra_file_content.update_time IS '更新时间';
COMMENT ON COLUMN infra_file_content.deleted IS '是否删除';
COMMENT ON TABLE infra_file_content IS '文件表';

DROP SEQUENCE IF EXISTS infra_file_content_seq;
CREATE SEQUENCE infra_file_content_seq
    START 1;

-- ----------------------------
-- Table structure for infra_job
-- ----------------------------
DROP TABLE IF EXISTS infra_job;
CREATE TABLE infra_job (
    id int8 NOT NULL,
  name varchar(32) NOT NULL,
  status int2 NOT NULL,
  handler_name varchar(64) NOT NULL,
  handler_param varchar(255) NULL DEFAULT NULL,
  cron_expression varchar(32) NOT NULL,
  retry_count int4 NOT NULL DEFAULT 0,
  retry_interval int4 NOT NULL DEFAULT 0,
  monitor_timeout int4 NOT NULL DEFAULT 0,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_job ADD CONSTRAINT pk_infra_job PRIMARY KEY (id);

COMMENT ON COLUMN infra_job.id IS '任务编号';
COMMENT ON COLUMN infra_job.name IS '任务名称';
COMMENT ON COLUMN infra_job.status IS '任务状态';
COMMENT ON COLUMN infra_job.handler_name IS '处理器的名字';
COMMENT ON COLUMN infra_job.handler_param IS '处理器的参数';
COMMENT ON COLUMN infra_job.cron_expression IS 'CRON 表达式';
COMMENT ON COLUMN infra_job.retry_count IS '重试次数';
COMMENT ON COLUMN infra_job.retry_interval IS '重试间隔';
COMMENT ON COLUMN infra_job.monitor_timeout IS '监控超时时间';
COMMENT ON COLUMN infra_job.creator IS '创建者';
COMMENT ON COLUMN infra_job.create_time IS '创建时间';
COMMENT ON COLUMN infra_job.updater IS '更新者';
COMMENT ON COLUMN infra_job.update_time IS '更新时间';
COMMENT ON COLUMN infra_job.deleted IS '是否删除';
COMMENT ON TABLE infra_job IS '定时任务表';

-- ----------------------------
-- Records of infra_job
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO infra_job  VALUES (5, '支付通知 Job', 2, 'payNotifyJob', NULL, '* * * * * ?', 0, 0, 0, '1', '2021-10-27 08:34:42', '1', '2024-09-12 13:32:48', '0');
INSERT INTO infra_job  VALUES (17, '支付订单同步 Job', 2, 'payOrderSyncJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-22 14:36:26', '1', '2023-07-22 15:39:08', '0');
INSERT INTO infra_job  VALUES (18, '支付订单过期 Job', 2, 'payOrderExpireJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-22 15:36:23', '1', '2023-07-22 15:39:54', '0');
INSERT INTO infra_job  VALUES (19, '退款订单的同步 Job', 2, 'payRefundSyncJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-23 21:03:44', '1', '2023-07-23 21:09:00', '0');
INSERT INTO infra_job  VALUES (21, '交易订单的自动过期 Job', 2, 'tradeOrderAutoCancelJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-25 23:43:26', '1', '2023-09-26 19:23:30', '0');
INSERT INTO infra_job  VALUES (22, '交易订单的自动收货 Job', 2, 'tradeOrderAutoReceiveJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-26 19:23:53', '1', '2023-09-26 23:38:08', '0');
INSERT INTO infra_job  VALUES (23, '交易订单的自动评论 Job', 2, 'tradeOrderAutoCommentJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-26 23:38:29', '1', '2023-09-27 11:03:10', '0');
INSERT INTO infra_job  VALUES (24, '佣金解冻 Job', 2, 'brokerageRecordUnfreezeJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-28 22:01:46', '1', '2023-09-28 22:01:56', '0');
INSERT INTO infra_job  VALUES (25, '访问日志清理 Job', 2, 'accessLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 10:59:41', '1', '2023-10-03 11:01:10', '0');
INSERT INTO infra_job  VALUES (26, '错误日志清理 Job', 2, 'errorLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 11:00:43', '1', '2023-10-03 11:01:12', '0');
INSERT INTO infra_job  VALUES (27, '任务日志清理 Job', 2, 'jobLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 11:01:33', '1', '2024-09-12 13:40:34', '0');
INSERT INTO infra_job  VALUES (33, 'demoJob', 2, 'demoJob', '', '0 * * * * ?', 1, 1, 0, '1', '2024-10-27 19:38:46', '1', '2024-10-27 19:40:23', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS infra_job_seq;
CREATE SEQUENCE infra_job_seq
    START 34;

-- ----------------------------
-- Table structure for infra_job_log
-- ----------------------------
DROP TABLE IF EXISTS infra_job_log;
CREATE TABLE infra_job_log (
    id int8 NOT NULL,
  job_id int8 NOT NULL,
  handler_name varchar(64) NOT NULL,
  handler_param varchar(255) NULL DEFAULT NULL,
  execute_index int2 NOT NULL DEFAULT 1,
  begin_time timestamp NOT NULL,
  end_time timestamp NULL DEFAULT NULL,
  duration int4 NULL DEFAULT NULL,
  status int2 NOT NULL,
  result varchar(4000) NULL DEFAULT '',
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE infra_job_log ADD CONSTRAINT pk_infra_job_log PRIMARY KEY (id);

COMMENT ON COLUMN infra_job_log.id IS '日志编号';
COMMENT ON COLUMN infra_job_log.job_id IS '任务编号';
COMMENT ON COLUMN infra_job_log.handler_name IS '处理器的名字';
COMMENT ON COLUMN infra_job_log.handler_param IS '处理器的参数';
COMMENT ON COLUMN infra_job_log.execute_index IS '第几次执行';
COMMENT ON COLUMN infra_job_log.begin_time IS '开始执行时间';
COMMENT ON COLUMN infra_job_log.end_time IS '结束执行时间';
COMMENT ON COLUMN infra_job_log.duration IS '执行时长';
COMMENT ON COLUMN infra_job_log.status IS '任务状态';
COMMENT ON COLUMN infra_job_log.result IS '结果数据';
COMMENT ON COLUMN infra_job_log.creator IS '创建者';
COMMENT ON COLUMN infra_job_log.create_time IS '创建时间';
COMMENT ON COLUMN infra_job_log.updater IS '更新者';
COMMENT ON COLUMN infra_job_log.update_time IS '更新时间';
COMMENT ON COLUMN infra_job_log.deleted IS '是否删除';
COMMENT ON TABLE infra_job_log IS '定时任务日志表';

DROP SEQUENCE IF EXISTS infra_job_log_seq;
CREATE SEQUENCE infra_job_log_seq
    START 1;

-- ----------------------------
-- Table structure for notify_message
-- ----------------------------
DROP TABLE IF EXISTS notify_message;
CREATE TABLE notify_message (
    id int8 NOT NULL,
  content varchar(2048) NULL DEFAULT NULL,
  status int2 NULL DEFAULT NULL,
  messagetype varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0
);

ALTER TABLE notify_message ADD CONSTRAINT pk_notify_message PRIMARY KEY (id);

COMMENT ON COLUMN notify_message.id IS 'id';
COMMENT ON COLUMN notify_message.content IS '内容';
COMMENT ON COLUMN notify_message.status IS '状态';
COMMENT ON COLUMN notify_message.messageType IS '消息类型';
COMMENT ON COLUMN notify_message.creator IS '创建者';
COMMENT ON COLUMN notify_message.create_time IS '创建时间';
COMMENT ON COLUMN notify_message.updater IS '更新者';
COMMENT ON COLUMN notify_message.update_time IS '更新时间';
COMMENT ON COLUMN notify_message.deleted IS '是否删除';
COMMENT ON COLUMN notify_message.tenant_id IS '租户编号';
COMMENT ON COLUMN notify_message.dept_id IS '机构id';
COMMENT ON TABLE notify_message IS 'iot通知消息';

DROP SEQUENCE IF EXISTS notify_message_seq;
CREATE SEQUENCE notify_message_seq
    START 1;

-- ----------------------------
-- Table structure for ota_package
-- ----------------------------
DROP TABLE IF EXISTS ota_package;
CREATE TABLE ota_package (
    id int8 NOT NULL,
  product_key varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  url varchar(255) NULL DEFAULT NULL,
  version varchar(255) NOT NULL,
  size int8 NOT NULL,
  sign_method varchar(255) NULL DEFAULT NULL,
  sign varchar(255) NULL DEFAULT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  ext_data varchar(255) NULL DEFAULT NULL,
  is_diff bool NULL DEFAULT NULL,
  md5 varchar(255) NULL DEFAULT NULL,
  module varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE ota_package ADD CONSTRAINT pk_ota_package PRIMARY KEY (id);

COMMENT ON COLUMN ota_package.id IS 'id';
COMMENT ON COLUMN ota_package.product_key IS '产品key';
COMMENT ON COLUMN ota_package.name IS '名称';
COMMENT ON COLUMN ota_package.url IS '升级包地址';
COMMENT ON COLUMN ota_package.version IS '版本';
COMMENT ON COLUMN ota_package.size IS '升级包大小';
COMMENT ON COLUMN ota_package.sign_method IS '签名方式';
COMMENT ON COLUMN ota_package.sign IS '签名内容';
COMMENT ON COLUMN ota_package.remark IS '备注';
COMMENT ON COLUMN ota_package.ext_data IS '额外内容';
COMMENT ON COLUMN ota_package.is_diff IS '是否差分包';
COMMENT ON COLUMN ota_package.md5 IS 'md5';
COMMENT ON COLUMN ota_package.module IS '模块';
COMMENT ON COLUMN ota_package.creator IS '创建者';
COMMENT ON COLUMN ota_package.create_time IS '创建时间';
COMMENT ON COLUMN ota_package.updater IS '更新者';
COMMENT ON COLUMN ota_package.update_time IS '更新时间';
COMMENT ON COLUMN ota_package.deleted IS '是否删除';
COMMENT ON COLUMN ota_package.tenant_id IS '租户编号';
COMMENT ON TABLE ota_package IS 'ota包';

-- ----------------------------
-- Records of ota_package
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO ota_package  VALUES (1, 'pfxt7ymAmc5TCdNE', 'j', 'http://127.0.0.1/admin-api/infra/file/29/get/eec070c604d18d7370a28de1de304af880c0e2ea52615dee4259ce4543c95def.jpg', '2', 253, 'md5', '2', NULL, NULL, '0', NULL, '22', '1', '2025-02-20 23:44:14', '1', '2025-02-20 23:44:14', '0', 1);
INSERT INTO ota_package  VALUES (2, 'R755G5Wb3jst4tD7', 'jj', 'http://127.0.0.1:48080/admin-api/infra/file/29/get/551f234aa0f2d9c7aa4ab0fd75f200be5935e220879d05fb47ed62ea3b3523b3.jpg', 'jj', 252, 'md5', 'j', NULL, NULL, '0', NULL, 'j', '1', '2025-02-21 00:18:59', '1', '2025-02-21 00:18:59', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS ota_package_seq;
CREATE SEQUENCE ota_package_seq
    START 3;

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS system_dept;
CREATE TABLE system_dept (
    id int8 NOT NULL,
  name varchar(30) NOT NULL DEFAULT '',
  parent_id int8 NOT NULL DEFAULT 0,
  sort int4 NOT NULL DEFAULT 0,
  leader_user_id int8 NULL DEFAULT NULL,
  phone varchar(11) NULL DEFAULT NULL,
  email varchar(50) NULL DEFAULT NULL,
  status int2 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_dept ADD CONSTRAINT pk_system_dept PRIMARY KEY (id);

COMMENT ON COLUMN system_dept.id IS '部门id';
COMMENT ON COLUMN system_dept.name IS '部门名称';
COMMENT ON COLUMN system_dept.parent_id IS '父部门id';
COMMENT ON COLUMN system_dept.sort IS '显示顺序';
COMMENT ON COLUMN system_dept.leader_user_id IS '负责人';
COMMENT ON COLUMN system_dept.phone IS '联系电话';
COMMENT ON COLUMN system_dept.email IS '邮箱';
COMMENT ON COLUMN system_dept.status IS '部门状态（0正常 1停用）';
COMMENT ON COLUMN system_dept.creator IS '创建者';
COMMENT ON COLUMN system_dept.create_time IS '创建时间';
COMMENT ON COLUMN system_dept.updater IS '更新者';
COMMENT ON COLUMN system_dept.update_time IS '更新时间';
COMMENT ON COLUMN system_dept.deleted IS '是否删除';
COMMENT ON COLUMN system_dept.tenant_id IS '租户编号';
COMMENT ON TABLE system_dept IS '部门表';

-- ----------------------------
-- Records of system_dept
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_dept  VALUES (100, '平台', 0, 0, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-11-14 23:30:36', '0', 1);
INSERT INTO system_dept  VALUES (101, '深圳总公司', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-12-02 09:53:35', '0', 1);
INSERT INTO system_dept  VALUES (102, '长沙分公司', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:40', '0', 1);
INSERT INTO system_dept  VALUES (103, '研发部门', 101, 1, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2024-10-02 10:22:03', '0', 1);
INSERT INTO system_dept  VALUES (104, '市场部门', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:38', '0', 1);
INSERT INTO system_dept  VALUES (105, '测试部门', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-05-16 20:25:15', '0', 1);
INSERT INTO system_dept  VALUES (106, '财务部门', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-15 21:32:22', '0', 1);
INSERT INTO system_dept  VALUES (107, '运维部门', 101, 5, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-12-02 09:28:22', '0', 1);
INSERT INTO system_dept  VALUES (108, '市场部门', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-02-16 08:35:45', '0', 1);
INSERT INTO system_dept  VALUES (109, '财务部门', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:29', '0', 1);
INSERT INTO system_dept  VALUES (110, '新部门', 0, 1, NULL, NULL, NULL, 0, '110', '2022-02-23 20:46:30', '110', '2022-02-23 20:46:30', '0', 121);
INSERT INTO system_dept  VALUES (111, '顶级部门', 0, 1, NULL, NULL, NULL, 0, '113', '2022-03-07 21:44:50', '113', '2022-03-07 21:44:50', '0', 122);
INSERT INTO system_dept  VALUES (112, '产品部门', 101, 100, 1, NULL, NULL, 1, '1', '2023-12-02 09:45:13', '1', '2023-12-02 09:45:31', '0', 1);
INSERT INTO system_dept  VALUES (113, '支持部门', 102, 3, 104, NULL, NULL, 1, '1', '2023-12-02 09:47:38', '1', '2023-12-02 09:47:38', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_dept_seq;
CREATE SEQUENCE system_dept_seq
    START 114;

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS system_dict_data;
CREATE TABLE system_dict_data (
    id int8 NOT NULL,
  sort int4 NOT NULL DEFAULT 0,
  label varchar(100) NOT NULL DEFAULT '',
  value varchar(100) NOT NULL DEFAULT '',
  dict_type varchar(100) NOT NULL DEFAULT '',
  status int2 NOT NULL DEFAULT 0,
  color_type varchar(100) NULL DEFAULT '',
  css_class varchar(100) NULL DEFAULT '',
  remark varchar(500) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_dict_data ADD CONSTRAINT pk_system_dict_data PRIMARY KEY (id);

COMMENT ON COLUMN system_dict_data.id IS '字典编码';
COMMENT ON COLUMN system_dict_data.sort IS '字典排序';
COMMENT ON COLUMN system_dict_data.label IS '字典标签';
COMMENT ON COLUMN system_dict_data.value IS '字典键值';
COMMENT ON COLUMN system_dict_data.dict_type IS '字典类型';
COMMENT ON COLUMN system_dict_data.status IS '状态（0正常 1停用）';
COMMENT ON COLUMN system_dict_data.color_type IS '颜色类型';
COMMENT ON COLUMN system_dict_data.css_class IS 'css 样式';
COMMENT ON COLUMN system_dict_data.remark IS '备注';
COMMENT ON COLUMN system_dict_data.creator IS '创建者';
COMMENT ON COLUMN system_dict_data.create_time IS '创建时间';
COMMENT ON COLUMN system_dict_data.updater IS '更新者';
COMMENT ON COLUMN system_dict_data.update_time IS '更新时间';
COMMENT ON COLUMN system_dict_data.deleted IS '是否删除';
COMMENT ON TABLE system_dict_data IS '字典数据表';

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_dict_data  VALUES (1, 1, '男', '1', 'system_user_sex', 0, 'default', 'A', '性别男', 'admin', '2021-01-05 17:03:48', '1', '2022-03-29 00:14:39', '0');
INSERT INTO system_dict_data  VALUES (2, 2, '女', '2', 'system_user_sex', 0, 'success', '', '性别女', 'admin', '2021-01-05 17:03:48', '1', '2023-11-15 23:30:37', '0');
INSERT INTO system_dict_data  VALUES (8, 1, '正常', '1', 'infra_job_status', 0, 'success', '', '正常状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:33:38', '0');
INSERT INTO system_dict_data  VALUES (9, 2, '暂停', '2', 'infra_job_status', 0, 'danger', '', '停用状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:33:45', '0');
INSERT INTO system_dict_data  VALUES (12, 1, '系统内置', '1', 'infra_config_type', 0, 'danger', '', '参数类型 - 系统内置', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:06:02', '0');
INSERT INTO system_dict_data  VALUES (13, 2, '自定义', '2', 'infra_config_type', 0, 'primary', '', '参数类型 - 自定义', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:06:07', '0');
INSERT INTO system_dict_data  VALUES (14, 1, '通知', '1', 'system_notice_type', 0, 'success', '', '通知', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:05:57', '0');
INSERT INTO system_dict_data  VALUES (15, 2, '公告', '2', 'system_notice_type', 0, 'info', '', '公告', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:06:01', '0');
INSERT INTO system_dict_data  VALUES (16, 0, '其它', '0', 'infra_operate_type', 0, 'default', '', '其它操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:19', '0');
INSERT INTO system_dict_data  VALUES (17, 1, '查询', '1', 'infra_operate_type', 0, 'info', '', '查询操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:20', '0');
INSERT INTO system_dict_data  VALUES (18, 2, '新增', '2', 'infra_operate_type', 0, 'primary', '', '新增操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:21', '0');
INSERT INTO system_dict_data  VALUES (19, 3, '修改', '3', 'infra_operate_type', 0, 'warning', '', '修改操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:22', '0');
INSERT INTO system_dict_data  VALUES (20, 4, '删除', '4', 'infra_operate_type', 0, 'danger', '', '删除操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:23', '0');
INSERT INTO system_dict_data  VALUES (22, 5, '导出', '5', 'infra_operate_type', 0, 'default', '', '导出操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:24', '0');
INSERT INTO system_dict_data  VALUES (23, 6, '导入', '6', 'infra_operate_type', 0, 'default', '', '导入操作', 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:25', '0');
INSERT INTO system_dict_data  VALUES (27, 1, '开启', '0', 'common_status', 0, 'primary', '', '开启状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 08:00:39', '0');
INSERT INTO system_dict_data  VALUES (28, 2, '关闭', '1', 'common_status', 0, 'info', '', '关闭状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 08:00:44', '0');
INSERT INTO system_dict_data  VALUES (29, 1, '目录', '1', 'system_menu_type', 0, '', '', '目录', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:45', '0');
INSERT INTO system_dict_data  VALUES (30, 2, '菜单', '2', 'system_menu_type', 0, '', '', '菜单', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:41', '0');
INSERT INTO system_dict_data  VALUES (31, 3, '按钮', '3', 'system_menu_type', 0, '', '', '按钮', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:39', '0');
INSERT INTO system_dict_data  VALUES (32, 1, '内置', '1', 'system_role_type', 0, 'danger', '', '内置角色', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:02:08', '0');
INSERT INTO system_dict_data  VALUES (33, 2, '自定义', '2', 'system_role_type', 0, 'primary', '', '自定义角色', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:02:12', '0');
INSERT INTO system_dict_data  VALUES (34, 1, '全部数据权限', '1', 'system_data_scope', 0, '', '', '全部数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:17', '0');
INSERT INTO system_dict_data  VALUES (35, 2, '指定部门数据权限', '2', 'system_data_scope', 0, '', '', '指定部门数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:18', '0');
INSERT INTO system_dict_data  VALUES (36, 3, '本部门数据权限', '3', 'system_data_scope', 0, '', '', '本部门数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:16', '0');
INSERT INTO system_dict_data  VALUES (37, 4, '本部门及以下数据权限', '4', 'system_data_scope', 0, '', '', '本部门及以下数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:21', '0');
INSERT INTO system_dict_data  VALUES (38, 5, '仅本人数据权限', '5', 'system_data_scope', 0, '', '', '仅本人数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:23', '0');
INSERT INTO system_dict_data  VALUES (39, 0, '成功', '0', 'system_login_result', 0, 'success', '', '登陆结果 - 成功', '', '2021-01-18 06:17:36', '1', '2022-02-16 13:23:49', '0');
INSERT INTO system_dict_data  VALUES (40, 10, '账号或密码不正确', '10', 'system_login_result', 0, 'primary', '', '登陆结果 - 账号或密码不正确', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:27', '0');
INSERT INTO system_dict_data  VALUES (41, 20, '用户被禁用', '20', 'system_login_result', 0, 'warning', '', '登陆结果 - 用户被禁用', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:23:57', '0');
INSERT INTO system_dict_data  VALUES (42, 30, '验证码不存在', '30', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不存在', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:07', '0');
INSERT INTO system_dict_data  VALUES (43, 31, '验证码不正确', '31', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不正确', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:11', '0');
INSERT INTO system_dict_data  VALUES (44, 100, '未知异常', '100', 'system_login_result', 0, 'danger', '', '登陆结果 - 未知异常', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:23', '0');
INSERT INTO system_dict_data  VALUES (45, 1, '是', 'true', 'infra_boolean_string', 0, 'danger', '', 'Boolean 是否类型 - 是', '', '2021-01-19 03:20:55', '1', '2022-03-15 23:01:45', '0');
INSERT INTO system_dict_data  VALUES (46, 1, '否', 'false', 'infra_boolean_string', 0, 'info', '', 'Boolean 是否类型 - 否', '', '2021-01-19 03:20:55', '1', '2022-03-15 23:09:45', '0');
INSERT INTO system_dict_data  VALUES (50, 1, '单表（增删改查）', '1', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:09:06', '', '2022-03-10 16:33:15', '0');
INSERT INTO system_dict_data  VALUES (51, 2, '树表（增删改查）', '2', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:14:46', '', '2022-03-10 16:33:19', '0');
INSERT INTO system_dict_data  VALUES (53, 0, '初始化中', '0', 'infra_job_status', 0, 'primary', '', NULL, '', '2021-02-07 07:46:49', '1', '2022-02-16 19:33:29', '0');
INSERT INTO system_dict_data  VALUES (57, 0, '运行中', '0', 'infra_job_log_status', 0, 'primary', '', 'RUNNING', '', '2021-02-08 10:04:24', '1', '2022-02-16 19:07:48', '0');
INSERT INTO system_dict_data  VALUES (58, 1, '成功', '1', 'infra_job_log_status', 0, 'success', '', NULL, '', '2021-02-08 10:06:57', '1', '2022-02-16 19:07:52', '0');
INSERT INTO system_dict_data  VALUES (59, 2, '失败', '2', 'infra_job_log_status', 0, 'warning', '', '失败', '', '2021-02-08 10:07:38', '1', '2022-02-16 19:07:56', '0');
INSERT INTO system_dict_data  VALUES (60, 1, '会员', '1', 'user_type', 0, 'primary', '', NULL, '', '2021-02-26 00:16:27', '1', '2022-02-16 10:22:19', '0');
INSERT INTO system_dict_data  VALUES (61, 2, '管理员', '2', 'user_type', 0, 'success', '', NULL, '', '2021-02-26 00:16:34', '1', '2022-02-16 10:22:22', '0');
INSERT INTO system_dict_data  VALUES (62, 0, '未处理', '0', 'infra_api_error_log_process_status', 0, 'primary', '', NULL, '', '2021-02-26 07:07:19', '1', '2022-02-16 20:14:17', '0');
INSERT INTO system_dict_data  VALUES (63, 1, '已处理', '1', 'infra_api_error_log_process_status', 0, 'success', '', NULL, '', '2021-02-26 07:07:26', '1', '2022-02-16 20:14:08', '0');
INSERT INTO system_dict_data  VALUES (64, 2, '已忽略', '2', 'infra_api_error_log_process_status', 0, 'danger', '', NULL, '', '2021-02-26 07:07:34', '1', '2022-02-16 20:14:14', '0');
INSERT INTO system_dict_data  VALUES (66, 1, '阿里云', 'ALIYUN', 'system_sms_channel_code', 0, 'primary', '', NULL, '1', '2021-04-05 01:05:26', '1', '2024-07-22 22:23:25', '0');
INSERT INTO system_dict_data  VALUES (67, 1, '验证码', '1', 'system_sms_template_type', 0, 'warning', '', NULL, '1', '2021-04-05 21:50:57', '1', '2022-02-16 12:48:30', '0');
INSERT INTO system_dict_data  VALUES (68, 2, '通知', '2', 'system_sms_template_type', 0, 'primary', '', NULL, '1', '2021-04-05 21:51:08', '1', '2022-02-16 12:48:27', '0');
INSERT INTO system_dict_data  VALUES (69, 0, '营销', '3', 'system_sms_template_type', 0, 'danger', '', NULL, '1', '2021-04-05 21:51:15', '1', '2022-02-16 12:48:22', '0');
INSERT INTO system_dict_data  VALUES (70, 0, '初始化', '0', 'system_sms_send_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:18:33', '1', '2022-02-16 10:26:07', '0');
INSERT INTO system_dict_data  VALUES (71, 1, '发送成功', '10', 'system_sms_send_status', 0, 'success', '', NULL, '1', '2021-04-11 20:18:43', '1', '2022-02-16 10:25:56', '0');
INSERT INTO system_dict_data  VALUES (72, 2, '发送失败', '20', 'system_sms_send_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:18:49', '1', '2022-02-16 10:26:03', '0');
INSERT INTO system_dict_data  VALUES (73, 3, '不发送', '30', 'system_sms_send_status', 0, 'info', '', NULL, '1', '2021-04-11 20:19:44', '1', '2022-02-16 10:26:10', '0');
INSERT INTO system_dict_data  VALUES (74, 0, '等待结果', '0', 'system_sms_receive_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:27:43', '1', '2022-02-16 10:28:24', '0');
INSERT INTO system_dict_data  VALUES (75, 1, '接收成功', '10', 'system_sms_receive_status', 0, 'success', '', NULL, '1', '2021-04-11 20:29:25', '1', '2022-02-16 10:28:28', '0');
INSERT INTO system_dict_data  VALUES (76, 2, '接收失败', '20', 'system_sms_receive_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:29:31', '1', '2022-02-16 10:28:32', '0');
INSERT INTO system_dict_data  VALUES (77, 0, '调试(钉钉)', 'DEBUG_DING_TALK', 'system_sms_channel_code', 0, 'info', '', NULL, '1', '2021-04-13 00:20:37', '1', '2022-02-16 10:10:00', '0');
INSERT INTO system_dict_data  VALUES (80, 100, '账号登录', '100', 'system_login_type', 0, 'primary', '', '账号登录', '1', '2021-10-06 00:52:02', '1', '2022-02-16 13:11:34', '0');
INSERT INTO system_dict_data  VALUES (81, 101, '社交登录', '101', 'system_login_type', 0, 'info', '', '社交登录', '1', '2021-10-06 00:52:17', '1', '2022-02-16 13:11:40', '0');
INSERT INTO system_dict_data  VALUES (83, 200, '主动登出', '200', 'system_login_type', 0, 'primary', '', '主动登出', '1', '2021-10-06 00:52:58', '1', '2022-02-16 13:11:49', '0');
INSERT INTO system_dict_data  VALUES (85, 202, '强制登出', '202', 'system_login_type', 0, 'danger', '', '强制退出', '1', '2021-10-06 00:53:41', '1', '2022-02-16 13:11:57', '0');
INSERT INTO system_dict_data  VALUES (86, 0, '病假', '1', 'bpm_oa_leave_type', 0, 'primary', '', NULL, '1', '2021-09-21 22:35:28', '1', '2022-02-16 10:00:41', '0');
INSERT INTO system_dict_data  VALUES (87, 1, '事假', '2', 'bpm_oa_leave_type', 0, 'info', '', NULL, '1', '2021-09-21 22:36:11', '1', '2022-02-16 10:00:49', '0');
INSERT INTO system_dict_data  VALUES (88, 2, '婚假', '3', 'bpm_oa_leave_type', 0, 'warning', '', NULL, '1', '2021-09-21 22:36:38', '1', '2022-02-16 10:00:53', '0');
INSERT INTO system_dict_data  VALUES (112, 0, '微信 Wap 网站支付', 'wx_wap', 'pay_channel_code', 0, 'success', '', '微信 Wap 网站支付', '1', '2023-07-19 20:08:06', '1', '2023-07-19 20:09:08', '0');
INSERT INTO system_dict_data  VALUES (113, 1, '微信公众号支付', 'wx_pub', 'pay_channel_code', 0, 'success', '', '微信公众号支付', '1', '2021-12-03 10:40:24', '1', '2023-07-19 20:08:47', '0');
INSERT INTO system_dict_data  VALUES (114, 2, '微信小程序支付', 'wx_lite', 'pay_channel_code', 0, 'success', '', '微信小程序支付', '1', '2021-12-03 10:41:06', '1', '2023-07-19 20:08:50', '0');
INSERT INTO system_dict_data  VALUES (115, 3, '微信 App 支付', 'wx_app', 'pay_channel_code', 0, 'success', '', '微信 App 支付', '1', '2021-12-03 10:41:20', '1', '2023-07-19 20:08:56', '0');
INSERT INTO system_dict_data  VALUES (116, 10, '支付宝 PC 网站支付', 'alipay_pc', 'pay_channel_code', 0, 'primary', '', '支付宝 PC 网站支付', '1', '2021-12-03 10:42:09', '1', '2023-07-19 20:09:12', '0');
INSERT INTO system_dict_data  VALUES (117, 11, '支付宝 Wap 网站支付', 'alipay_wap', 'pay_channel_code', 0, 'primary', '', '支付宝 Wap 网站支付', '1', '2021-12-03 10:42:26', '1', '2023-07-19 20:09:16', '0');
INSERT INTO system_dict_data  VALUES (118, 12, '支付宝 App 支付', 'alipay_app', 'pay_channel_code', 0, 'primary', '', '支付宝 App 支付', '1', '2021-12-03 10:42:55', '1', '2023-07-19 20:09:20', '0');
INSERT INTO system_dict_data  VALUES (119, 14, '支付宝扫码支付', 'alipay_qr', 'pay_channel_code', 0, 'primary', '', '支付宝扫码支付', '1', '2021-12-03 10:43:10', '1', '2023-07-19 20:09:28', '0');
INSERT INTO system_dict_data  VALUES (120, 10, '通知成功', '10', 'pay_notify_status', 0, 'success', '', '通知成功', '1', '2021-12-03 11:02:41', '1', '2023-07-19 10:08:19', '0');
INSERT INTO system_dict_data  VALUES (121, 20, '通知失败', '20', 'pay_notify_status', 0, 'danger', '', '通知失败', '1', '2021-12-03 11:02:59', '1', '2023-07-19 10:08:21', '0');
INSERT INTO system_dict_data  VALUES (122, 0, '等待通知', '0', 'pay_notify_status', 0, 'info', '', '未通知', '1', '2021-12-03 11:03:10', '1', '2023-07-19 10:08:24', '0');
INSERT INTO system_dict_data  VALUES (123, 10, '支付成功', '10', 'pay_order_status', 0, 'success', '', '支付成功', '1', '2021-12-03 11:18:29', '1', '2023-07-19 18:04:28', '0');
INSERT INTO system_dict_data  VALUES (124, 30, '支付关闭', '30', 'pay_order_status', 0, 'info', '', '支付关闭', '1', '2021-12-03 11:18:42', '1', '2023-07-19 18:05:07', '0');
INSERT INTO system_dict_data  VALUES (125, 0, '等待支付', '0', 'pay_order_status', 0, 'info', '', '未支付', '1', '2021-12-03 11:18:18', '1', '2023-07-19 18:04:15', '0');
INSERT INTO system_dict_data  VALUES (600, 5, '首页', '1', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', '0');
INSERT INTO system_dict_data  VALUES (601, 4, '秒杀活动页', '2', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', '0');
INSERT INTO system_dict_data  VALUES (602, 3, '砍价活动页', '3', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', '0');
INSERT INTO system_dict_data  VALUES (603, 2, '限时折扣页', '4', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', '0');
INSERT INTO system_dict_data  VALUES (604, 1, '满减送页', '5', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', '0');
INSERT INTO system_dict_data  VALUES (1118, 0, '等待退款', '0', 'pay_refund_status', 0, 'info', '', '等待退款', '1', '2021-12-10 16:44:59', '1', '2023-07-19 10:14:39', '0');
INSERT INTO system_dict_data  VALUES (1119, 20, '退款失败', '20', 'pay_refund_status', 0, 'danger', '', '退款失败', '1', '2021-12-10 16:45:10', '1', '2023-07-19 10:15:10', '0');
INSERT INTO system_dict_data  VALUES (1124, 10, '退款成功', '10', 'pay_refund_status', 0, 'success', '', '退款成功', '1', '2021-12-10 16:46:26', '1', '2023-07-19 10:15:00', '0');
INSERT INTO system_dict_data  VALUES (1127, 1, '审批中', '1', 'bpm_process_instance_status', 0, 'default', '', '流程实例的状态 - 进行中', '1', '2022-01-07 23:47:22', '1', '2024-03-16 16:11:45', '0');
INSERT INTO system_dict_data  VALUES (1128, 2, '审批通过', '2', 'bpm_process_instance_status', 0, 'success', '', '流程实例的状态 - 已完成', '1', '2022-01-07 23:47:49', '1', '2024-03-16 16:11:54', '0');
INSERT INTO system_dict_data  VALUES (1129, 1, '审批中', '1', 'bpm_task_status', 0, 'primary', '', '流程实例的结果 - 处理中', '1', '2022-01-07 23:48:32', '1', '2024-03-08 22:41:37', '0');
INSERT INTO system_dict_data  VALUES (1130, 2, '审批通过', '2', 'bpm_task_status', 0, 'success', '', '流程实例的结果 - 通过', '1', '2022-01-07 23:48:45', '1', '2024-03-08 22:41:38', '0');
INSERT INTO system_dict_data  VALUES (1131, 3, '审批不通过', '3', 'bpm_task_status', 0, 'danger', '', '流程实例的结果 - 不通过', '1', '2022-01-07 23:48:55', '1', '2024-03-08 22:41:38', '0');
INSERT INTO system_dict_data  VALUES (1132, 4, '已取消', '4', 'bpm_task_status', 0, 'info', '', '流程实例的结果 - 撤销', '1', '2022-01-07 23:49:06', '1', '2024-03-08 22:41:39', '0');
INSERT INTO system_dict_data  VALUES (1133, 10, '流程表单', '10', 'bpm_model_form_type', 0, '', '', '流程的表单类型 - 流程表单', '103', '2022-01-11 23:51:30', '103', '2022-01-11 23:51:30', '0');
INSERT INTO system_dict_data  VALUES (1134, 20, '业务表单', '20', 'bpm_model_form_type', 0, '', '', '流程的表单类型 - 业务表单', '103', '2022-01-11 23:51:47', '103', '2022-01-11 23:51:47', '0');
INSERT INTO system_dict_data  VALUES (1135, 10, '角色', '10', 'bpm_task_candidate_strategy', 0, 'info', '', '任务分配规则的类型 - 角色', '103', '2022-01-12 23:21:22', '1', '2024-03-06 02:53:16', '0');
INSERT INTO system_dict_data  VALUES (1136, 20, '部门的成员', '20', 'bpm_task_candidate_strategy', 0, 'primary', '', '任务分配规则的类型 - 部门的成员', '103', '2022-01-12 23:21:47', '1', '2024-03-06 02:53:17', '0');
INSERT INTO system_dict_data  VALUES (1137, 21, '部门的负责人', '21', 'bpm_task_candidate_strategy', 0, 'primary', '', '任务分配规则的类型 - 部门的负责人', '103', '2022-01-12 23:33:36', '1', '2024-03-06 02:53:18', '0');
INSERT INTO system_dict_data  VALUES (1138, 30, '用户', '30', 'bpm_task_candidate_strategy', 0, 'info', '', '任务分配规则的类型 - 用户', '103', '2022-01-12 23:34:02', '1', '2024-03-06 02:53:19', '0');
INSERT INTO system_dict_data  VALUES (1139, 40, '用户组', '40', 'bpm_task_candidate_strategy', 0, 'warning', '', '任务分配规则的类型 - 用户组', '103', '2022-01-12 23:34:21', '1', '2024-03-06 02:53:20', '0');
INSERT INTO system_dict_data  VALUES (1140, 60, '流程表达式', '60', 'bpm_task_candidate_strategy', 0, 'danger', '', '任务分配规则的类型 - 流程表达式', '103', '2022-01-12 23:34:43', '1', '2024-03-06 02:53:20', '0');
INSERT INTO system_dict_data  VALUES (1141, 22, '岗位', '22', 'bpm_task_candidate_strategy', 0, 'success', '', '任务分配规则的类型 - 岗位', '103', '2022-01-14 18:41:55', '1', '2024-03-06 02:53:21', '0');
INSERT INTO system_dict_data  VALUES (1145, 1, '管理后台', '1', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 管理后台', '1', '2022-02-02 13:15:06', '1', '2022-03-10 16:32:59', '0');
INSERT INTO system_dict_data  VALUES (1146, 2, '用户 APP', '2', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 用户 APP', '1', '2022-02-02 13:15:19', '1', '2022-03-10 16:33:03', '0');
INSERT INTO system_dict_data  VALUES (1150, 1, '数据库', '1', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:28', '1', '2022-03-15 00:25:28', '0');
INSERT INTO system_dict_data  VALUES (1151, 10, '本地磁盘', '10', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:41', '1', '2022-03-15 00:25:56', '0');
INSERT INTO system_dict_data  VALUES (1152, 11, 'FTP 服务器', '11', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:06', '1', '2022-03-15 00:26:10', '0');
INSERT INTO system_dict_data  VALUES (1153, 12, 'SFTP 服务器', '12', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:22', '1', '2022-03-15 00:26:22', '0');
INSERT INTO system_dict_data  VALUES (1154, 20, 'S3 对象存储', '20', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:31', '1', '2022-03-15 00:26:45', '0');
INSERT INTO system_dict_data  VALUES (1155, 103, '短信登录', '103', 'system_login_type', 0, 'default', '', NULL, '1', '2022-05-09 23:57:58', '1', '2022-05-09 23:58:09', '0');
INSERT INTO system_dict_data  VALUES (1156, 1, 'password', 'password', 'system_oauth2_grant_type', 0, 'default', '', '密码模式', '1', '2022-05-12 00:22:05', '1', '2022-05-11 16:26:01', '0');
INSERT INTO system_dict_data  VALUES (1157, 2, 'authorization_code', 'authorization_code', 'system_oauth2_grant_type', 0, 'primary', '', '授权码模式', '1', '2022-05-12 00:22:59', '1', '2022-05-11 16:26:02', '0');
INSERT INTO system_dict_data  VALUES (1158, 3, 'implicit', 'implicit', 'system_oauth2_grant_type', 0, 'success', '', '简化模式', '1', '2022-05-12 00:23:40', '1', '2022-05-11 16:26:05', '0');
INSERT INTO system_dict_data  VALUES (1159, 4, 'client_credentials', 'client_credentials', 'system_oauth2_grant_type', 0, 'default', '', '客户端模式', '1', '2022-05-12 00:23:51', '1', '2022-05-11 16:26:08', '0');
INSERT INTO system_dict_data  VALUES (1160, 5, 'refresh_token', 'refresh_token', 'system_oauth2_grant_type', 0, 'info', '', '刷新模式', '1', '2022-05-12 00:24:02', '1', '2022-05-11 16:26:11', '0');
INSERT INTO system_dict_data  VALUES (1162, 1, '销售中', '1', 'product_spu_status', 0, 'success', '', '商品 SPU 状态 - 销售中', '1', '2022-10-24 21:19:47', '1', '2022-10-24 21:20:38', '0');
INSERT INTO system_dict_data  VALUES (1163, 0, '仓库中', '0', 'product_spu_status', 0, 'info', '', '商品 SPU 状态 - 仓库中', '1', '2022-10-24 21:20:54', '1', '2022-10-24 21:21:22', '0');
INSERT INTO system_dict_data  VALUES (1164, 0, '回收站', '-1', 'product_spu_status', 0, 'default', '', '商品 SPU 状态 - 回收站', '1', '2022-10-24 21:21:11', '1', '2022-10-24 21:21:11', '0');
INSERT INTO system_dict_data  VALUES (1165, 1, '满减', '1', 'promotion_discount_type', 0, 'success', '', '优惠类型 - 满减', '1', '2022-11-01 12:46:41', '1', '2022-11-01 12:50:11', '0');
INSERT INTO system_dict_data  VALUES (1166, 2, '折扣', '2', 'promotion_discount_type', 0, 'primary', '', '优惠类型 - 折扣', '1', '2022-11-01 12:46:51', '1', '2022-11-01 12:50:08', '0');
INSERT INTO system_dict_data  VALUES (1167, 1, '固定日期', '1', 'promotion_coupon_template_validity_type', 0, 'default', '', '优惠劵模板的有限期类型 - 固定日期', '1', '2022-11-02 00:07:34', '1', '2022-11-04 00:07:49', '0');
INSERT INTO system_dict_data  VALUES (1168, 2, '领取之后', '2', 'promotion_coupon_template_validity_type', 0, 'default', '', '优惠劵模板的有限期类型 - 领取之后', '1', '2022-11-02 00:07:54', '1', '2022-11-04 00:07:52', '0');
INSERT INTO system_dict_data  VALUES (1169, 1, '通用劵', '1', 'promotion_product_scope', 0, 'default', '', '营销的商品范围 - 全部商品参与', '1', '2022-11-02 00:28:22', '1', '2023-09-28 00:27:42', '0');
INSERT INTO system_dict_data  VALUES (1170, 2, '商品劵', '2', 'promotion_product_scope', 0, 'default', '', '营销的商品范围 - 指定商品参与', '1', '2022-11-02 00:28:34', '1', '2023-09-28 00:27:44', '0');
INSERT INTO system_dict_data  VALUES (1171, 1, '未使用', '1', 'promotion_coupon_status', 0, 'primary', '', '优惠劵的状态 - 已领取', '1', '2022-11-04 00:15:08', '1', '2023-10-03 12:54:38', '0');
INSERT INTO system_dict_data  VALUES (1172, 2, '已使用', '2', 'promotion_coupon_status', 0, 'success', '', '优惠劵的状态 - 已使用', '1', '2022-11-04 00:15:21', '1', '2022-11-04 19:16:08', '0');
INSERT INTO system_dict_data  VALUES (1173, 3, '已过期', '3', 'promotion_coupon_status', 0, 'info', '', '优惠劵的状态 - 已过期', '1', '2022-11-04 00:15:43', '1', '2022-11-04 19:16:12', '0');
INSERT INTO system_dict_data  VALUES (1174, 1, '直接领取', '1', 'promotion_coupon_take_type', 0, 'primary', '', '优惠劵的领取方式 - 直接领取', '1', '2022-11-04 19:13:00', '1', '2022-11-04 19:13:25', '0');
INSERT INTO system_dict_data  VALUES (1175, 2, '指定发放', '2', 'promotion_coupon_take_type', 0, 'success', '', '优惠劵的领取方式 - 指定发放', '1', '2022-11-04 19:13:13', '1', '2022-11-04 19:14:48', '0');
INSERT INTO system_dict_data  VALUES (1176, 10, '未开始', '10', 'promotion_activity_status', 0, 'primary', '', '促销活动的状态枚举 - 未开始', '1', '2022-11-04 22:54:49', '1', '2022-11-04 22:55:53', '0');
INSERT INTO system_dict_data  VALUES (1177, 20, '进行中', '20', 'promotion_activity_status', 0, 'success', '', '促销活动的状态枚举 - 进行中', '1', '2022-11-04 22:55:06', '1', '2022-11-04 22:55:20', '0');
INSERT INTO system_dict_data  VALUES (1178, 30, '已结束', '30', 'promotion_activity_status', 0, 'info', '', '促销活动的状态枚举 - 已结束', '1', '2022-11-04 22:55:41', '1', '2022-11-04 22:55:41', '0');
INSERT INTO system_dict_data  VALUES (1179, 40, '已关闭', '40', 'promotion_activity_status', 0, 'warning', '', '促销活动的状态枚举 - 已关闭', '1', '2022-11-04 22:56:10', '1', '2022-11-04 22:56:18', '0');
INSERT INTO system_dict_data  VALUES (1180, 10, '满 N 元', '10', 'promotion_condition_type', 0, 'primary', '', '营销的条件类型 - 满 N 元', '1', '2022-11-04 22:59:45', '1', '2022-11-04 22:59:45', '0');
INSERT INTO system_dict_data  VALUES (1181, 20, '满 N 件', '20', 'promotion_condition_type', 0, 'success', '', '营销的条件类型 - 满 N 件', '1', '2022-11-04 23:00:02', '1', '2022-11-04 23:00:02', '0');
INSERT INTO system_dict_data  VALUES (1182, 10, '申请售后', '10', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 申请售后', '1', '2022-11-19 20:53:33', '1', '2022-11-19 20:54:42', '0');
INSERT INTO system_dict_data  VALUES (1183, 20, '商品待退货', '20', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 商品待退货', '1', '2022-11-19 20:54:36', '1', '2022-11-19 20:58:58', '0');
INSERT INTO system_dict_data  VALUES (1184, 30, '商家待收货', '30', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 商家待收货', '1', '2022-11-19 20:56:56', '1', '2022-11-19 20:59:20', '0');
INSERT INTO system_dict_data  VALUES (1185, 40, '等待退款', '40', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 等待退款', '1', '2022-11-19 20:59:54', '1', '2022-11-19 21:00:01', '0');
INSERT INTO system_dict_data  VALUES (1186, 50, '退款成功', '50', 'trade_after_sale_status', 0, 'default', '', '交易售后状态 - 退款成功', '1', '2022-11-19 21:00:33', '1', '2022-11-19 21:00:33', '0');
INSERT INTO system_dict_data  VALUES (1187, 61, '买家取消', '61', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 买家取消', '1', '2022-11-19 21:01:29', '1', '2022-11-19 21:01:29', '0');
INSERT INTO system_dict_data  VALUES (1188, 62, '商家拒绝', '62', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 商家拒绝', '1', '2022-11-19 21:02:17', '1', '2022-11-19 21:02:17', '0');
INSERT INTO system_dict_data  VALUES (1189, 63, '商家拒收货', '63', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 商家拒收货', '1', '2022-11-19 21:02:37', '1', '2022-11-19 21:03:07', '0');
INSERT INTO system_dict_data  VALUES (1190, 10, '售中退款', '10', 'trade_after_sale_type', 0, 'success', '', '交易售后的类型 - 售中退款', '1', '2022-11-19 21:05:05', '1', '2022-11-19 21:38:23', '0');
INSERT INTO system_dict_data  VALUES (1191, 20, '售后退款', '20', 'trade_after_sale_type', 0, 'primary', '', '交易售后的类型 - 售后退款', '1', '2022-11-19 21:05:32', '1', '2022-11-19 21:38:32', '0');
INSERT INTO system_dict_data  VALUES (1192, 10, '仅退款', '10', 'trade_after_sale_way', 0, 'primary', '', '交易售后的方式 - 仅退款', '1', '2022-11-19 21:39:19', '1', '2022-11-19 21:39:19', '0');
INSERT INTO system_dict_data  VALUES (1193, 20, '退货退款', '20', 'trade_after_sale_way', 0, 'success', '', '交易售后的方式 - 退货退款', '1', '2022-11-19 21:39:38', '1', '2022-11-19 21:39:49', '0');
INSERT INTO system_dict_data  VALUES (1194, 10, '微信小程序', '10', 'terminal', 0, 'default', '', '终端 - 微信小程序', '1', '2022-12-10 10:51:11', '1', '2022-12-10 10:51:57', '0');
INSERT INTO system_dict_data  VALUES (1195, 20, 'H5 网页', '20', 'terminal', 0, 'default', '', '终端 - H5 网页', '1', '2022-12-10 10:51:30', '1', '2022-12-10 10:51:59', '0');
INSERT INTO system_dict_data  VALUES (1196, 11, '微信公众号', '11', 'terminal', 0, 'default', '', '终端 - 微信公众号', '1', '2022-12-10 10:54:16', '1', '2022-12-10 10:52:01', '0');
INSERT INTO system_dict_data  VALUES (1197, 31, '苹果 App', '31', 'terminal', 0, 'default', '', '终端 - 苹果 App', '1', '2022-12-10 10:54:42', '1', '2022-12-10 10:52:18', '0');
INSERT INTO system_dict_data  VALUES (1198, 32, '安卓 App', '32', 'terminal', 0, 'default', '', '终端 - 安卓 App', '1', '2022-12-10 10:55:02', '1', '2022-12-10 10:59:17', '0');
INSERT INTO system_dict_data  VALUES (1199, 0, '普通订单', '0', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 普通订单', '1', '2022-12-10 16:34:14', '1', '2022-12-10 16:34:14', '0');
INSERT INTO system_dict_data  VALUES (1200, 1, '秒杀订单', '1', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 秒杀订单', '1', '2022-12-10 16:34:26', '1', '2022-12-10 16:34:26', '0');
INSERT INTO system_dict_data  VALUES (1201, 2, '砍价订单', '2', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 拼团订单', '1', '2022-12-10 16:34:36', '1', '2024-09-07 14:18:39', '0');
INSERT INTO system_dict_data  VALUES (1202, 3, '拼团订单', '3', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 砍价订单', '1', '2022-12-10 16:34:48', '1', '2024-09-07 14:18:32', '0');
INSERT INTO system_dict_data  VALUES (1203, 0, '待支付', '0', 'trade_order_status', 0, 'default', '', '交易订单状态 - 待支付', '1', '2022-12-10 16:49:29', '1', '2022-12-10 16:49:29', '0');
INSERT INTO system_dict_data  VALUES (1204, 10, '待发货', '10', 'trade_order_status', 0, 'primary', '', '交易订单状态 - 待发货', '1', '2022-12-10 16:49:53', '1', '2022-12-10 16:51:17', '0');
INSERT INTO system_dict_data  VALUES (1205, 20, '已发货', '20', 'trade_order_status', 0, 'primary', '', '交易订单状态 - 已发货', '1', '2022-12-10 16:50:13', '1', '2022-12-10 16:51:31', '0');
INSERT INTO system_dict_data  VALUES (1206, 30, '已完成', '30', 'trade_order_status', 0, 'success', '', '交易订单状态 - 已完成', '1', '2022-12-10 16:50:30', '1', '2022-12-10 16:51:06', '0');
INSERT INTO system_dict_data  VALUES (1207, 40, '已取消', '40', 'trade_order_status', 0, 'danger', '', '交易订单状态 - 已取消', '1', '2022-12-10 16:50:50', '1', '2022-12-10 16:51:00', '0');
INSERT INTO system_dict_data  VALUES (1208, 0, '未售后', '0', 'trade_order_item_after_sale_status', 0, 'info', '', '交易订单项的售后状态 - 未售后', '1', '2022-12-10 20:58:42', '1', '2022-12-10 20:59:29', '0');
INSERT INTO system_dict_data  VALUES (1209, 10, '售后中', '10', 'trade_order_item_after_sale_status', 0, 'primary', '', '交易订单项的售后状态 - 售后中', '1', '2022-12-10 20:59:21', '1', '2024-07-21 17:01:24', '0');
INSERT INTO system_dict_data  VALUES (1210, 20, '已退款', '20', 'trade_order_item_after_sale_status', 0, 'success', '', '交易订单项的售后状态 - 已退款', '1', '2022-12-10 20:59:46', '1', '2024-07-21 17:01:35', '0');
INSERT INTO system_dict_data  VALUES (1211, 1, '完全匹配', '1', 'mp_auto_reply_request_match', 0, 'primary', '', '公众号自动回复的请求关键字匹配模式 - 完全匹配', '1', '2023-01-16 23:30:39', '1', '2023-01-16 23:31:00', '0');
INSERT INTO system_dict_data  VALUES (1212, 2, '半匹配', '2', 'mp_auto_reply_request_match', 0, 'success', '', '公众号自动回复的请求关键字匹配模式 - 半匹配', '1', '2023-01-16 23:30:55', '1', '2023-01-16 23:31:10', '0');
INSERT INTO system_dict_data  VALUES (1213, 1, '文本', 'text', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 文本', '1', '2023-01-17 22:17:32', '1', '2023-01-17 22:17:39', '0');
INSERT INTO system_dict_data  VALUES (1214, 2, '图片', 'image', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 图片', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:19:47', '0');
INSERT INTO system_dict_data  VALUES (1215, 3, '语音', 'voice', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 语音', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:20:08', '0');
INSERT INTO system_dict_data  VALUES (1216, 4, '视频', 'video', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 视频', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:21:08', '0');
INSERT INTO system_dict_data  VALUES (1217, 5, '小视频', 'shortvideo', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 小视频', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:19:59', '0');
INSERT INTO system_dict_data  VALUES (1218, 6, '图文', 'news', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 图文', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:22:54', '0');
INSERT INTO system_dict_data  VALUES (1219, 7, '音乐', 'music', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 音乐', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:22:54', '0');
INSERT INTO system_dict_data  VALUES (1220, 8, '地理位置', 'location', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 地理位置', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:23:51', '0');
INSERT INTO system_dict_data  VALUES (1221, 9, '链接', 'link', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 链接', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:24:49', '0');
INSERT INTO system_dict_data  VALUES (1222, 10, '事件', 'event', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 事件', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:24:49', '0');
INSERT INTO system_dict_data  VALUES (1223, 0, '初始化', '0', 'system_mail_send_status', 0, 'primary', '', '邮件发送状态 - 初始化\n', '1', '2023-01-26 09:53:49', '1', '2023-01-26 16:36:14', '0');
INSERT INTO system_dict_data  VALUES (1224, 10, '发送成功', '10', 'system_mail_send_status', 0, 'success', '', '邮件发送状态 - 发送成功', '1', '2023-01-26 09:54:28', '1', '2023-01-26 16:36:22', '0');
INSERT INTO system_dict_data  VALUES (1225, 20, '发送失败', '20', 'system_mail_send_status', 0, 'danger', '', '邮件发送状态 - 发送失败', '1', '2023-01-26 09:54:50', '1', '2023-01-26 16:36:26', '0');
INSERT INTO system_dict_data  VALUES (1226, 30, '不发送', '30', 'system_mail_send_status', 0, 'info', '', '邮件发送状态 -  不发送', '1', '2023-01-26 09:55:06', '1', '2023-01-26 16:36:36', '0');
INSERT INTO system_dict_data  VALUES (1227, 1, '通知公告', '1', 'system_notify_template_type', 0, 'primary', '', '站内信模版的类型 - 通知公告', '1', '2023-01-28 10:35:59', '1', '2023-01-28 10:35:59', '0');
INSERT INTO system_dict_data  VALUES (1228, 2, '系统消息', '2', 'system_notify_template_type', 0, 'success', '', '站内信模版的类型 - 系统消息', '1', '2023-01-28 10:36:20', '1', '2023-01-28 10:36:25', '0');
INSERT INTO system_dict_data  VALUES (1230, 13, '支付宝条码支付', 'alipay_bar', 'pay_channel_code', 0, 'primary', '', '支付宝条码支付', '1', '2023-02-18 23:32:24', '1', '2023-07-19 20:09:23', '0');
INSERT INTO system_dict_data  VALUES (1231, 10, 'Vue2 Element UI 标准模版', '10', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:03:55', '1', '2023-04-13 00:03:55', '0');
INSERT INTO system_dict_data  VALUES (1232, 20, 'Vue3 Element Plus 标准模版', '20', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:08', '1', '2023-04-13 00:04:08', '0');
INSERT INTO system_dict_data  VALUES (1234, 30, 'Vue3 vben 模版', '30', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:26', '1', '2023-04-13 00:04:26', '0');
INSERT INTO system_dict_data  VALUES (1244, 0, '按件', '1', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:46:40', '1', '2023-05-21 22:46:40', '0');
INSERT INTO system_dict_data  VALUES (1245, 1, '按重量', '2', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:46:58', '1', '2023-05-21 22:46:58', '0');
INSERT INTO system_dict_data  VALUES (1246, 2, '按体积', '3', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:47:18', '1', '2023-05-21 22:47:18', '0');
INSERT INTO system_dict_data  VALUES (1335, 11, '订单积分抵扣', '11', 'member_point_biz_type', 0, '', '', '', '1', '2023-06-10 12:15:27', '1', '2023-10-11 07:41:43', '0');
INSERT INTO system_dict_data  VALUES (1336, 1, '签到', '1', 'member_point_biz_type', 0, '', '', '', '1', '2023-06-10 12:15:48', '1', '2023-08-20 11:59:53', '0');
INSERT INTO system_dict_data  VALUES (1341, 20, '已退款', '20', 'pay_order_status', 0, 'danger', '', '已退款', '1', '2023-07-19 18:05:37', '1', '2023-07-19 18:05:37', '0');
INSERT INTO system_dict_data  VALUES (1342, 21, '请求成功，但是结果失败', '21', 'pay_notify_status', 0, 'warning', '', '请求成功，但是结果失败', '1', '2023-07-19 18:10:47', '1', '2023-07-19 18:11:38', '0');
INSERT INTO system_dict_data  VALUES (1343, 22, '请求失败', '22', 'pay_notify_status', 0, 'warning', '', NULL, '1', '2023-07-19 18:11:05', '1', '2023-07-19 18:11:27', '0');
INSERT INTO system_dict_data  VALUES (1344, 4, '微信扫码支付', 'wx_native', 'pay_channel_code', 0, 'success', '', '微信扫码支付', '1', '2023-07-19 20:07:47', '1', '2023-07-19 20:09:03', '0');
INSERT INTO system_dict_data  VALUES (1345, 5, '微信条码支付', 'wx_bar', 'pay_channel_code', 0, 'success', '', '微信条码支付\n', '1', '2023-07-19 20:08:06', '1', '2023-07-19 20:09:08', '0');
INSERT INTO system_dict_data  VALUES (1346, 1, '支付单', '1', 'pay_notify_type', 0, 'primary', '', '支付单', '1', '2023-07-20 12:23:17', '1', '2023-07-20 12:23:17', '0');
INSERT INTO system_dict_data  VALUES (1347, 2, '退款单', '2', 'pay_notify_type', 0, 'danger', '', NULL, '1', '2023-07-20 12:23:26', '1', '2023-07-20 12:23:26', '0');
INSERT INTO system_dict_data  VALUES (1348, 20, '模拟支付', 'mock', 'pay_channel_code', 0, 'default', '', '模拟支付', '1', '2023-07-29 11:10:51', '1', '2023-07-29 03:14:10', '0');
INSERT INTO system_dict_data  VALUES (1349, 12, '订单积分抵扣（整单取消）', '12', 'member_point_biz_type', 0, '', '', '', '1', '2023-08-20 12:00:03', '1', '2023-10-11 07:42:01', '0');
INSERT INTO system_dict_data  VALUES (1350, 0, '管理员调整', '0', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', '0');
INSERT INTO system_dict_data  VALUES (1351, 1, '邀新奖励', '1', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', '0');
INSERT INTO system_dict_data  VALUES (1352, 11, '下单奖励', '11', 'member_experience_biz_type', 0, 'success', '', NULL, '', '2023-08-22 12:41:01', '1', '2023-10-11 07:45:09', '0');
INSERT INTO system_dict_data  VALUES (1353, 12, '下单奖励（整单取消）', '12', 'member_experience_biz_type', 0, 'warning', '', NULL, '', '2023-08-22 12:41:01', '1', '2023-10-11 07:45:01', '0');
INSERT INTO system_dict_data  VALUES (1354, 4, '签到奖励', '4', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', '0');
INSERT INTO system_dict_data  VALUES (1355, 5, '抽奖奖励', '5', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', '0');
INSERT INTO system_dict_data  VALUES (1356, 1, '快递发货', '1', 'trade_delivery_type', 0, '', '', '', '1', '2023-08-23 00:04:55', '1', '2023-08-23 00:04:55', '0');
INSERT INTO system_dict_data  VALUES (1357, 2, '用户自提', '2', 'trade_delivery_type', 0, '', '', '', '1', '2023-08-23 00:05:05', '1', '2023-08-23 00:05:05', '0');
INSERT INTO system_dict_data  VALUES (1358, 3, '品类劵', '3', 'promotion_product_scope', 0, 'default', '', '', '1', '2023-09-01 23:43:07', '1', '2023-09-28 00:27:47', '0');
INSERT INTO system_dict_data  VALUES (1359, 1, '人人分销', '1', 'brokerage_enabled_condition', 0, '', '', '所有用户都可以分销', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1360, 2, '指定分销', '2', 'brokerage_enabled_condition', 0, '', '', '仅可后台手动设置推广员', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1361, 1, '首次绑定', '1', 'brokerage_bind_mode', 0, '', '', '只要用户没有推广人，随时都可以绑定推广关系', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1362, 2, '注册绑定', '2', 'brokerage_bind_mode', 0, '', '', '仅新用户注册时才能绑定推广关系', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1363, 3, '覆盖绑定', '3', 'brokerage_bind_mode', 0, '', '', '如果用户已经有推广人，推广人会被变更', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1364, 1, '钱包', '1', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1365, 2, '银行卡', '2', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1366, 3, '微信', '3', 'brokerage_withdraw_type', 0, '', '', '手动打款', '', '2023-09-28 02:46:05', '1', '2024-10-13 11:06:54', '0');
INSERT INTO system_dict_data  VALUES (1367, 4, '支付宝', '4', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1368, 1, '订单返佣', '1', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1369, 2, '申请提现', '2', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1370, 3, '申请提现驳回', '3', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1371, 0, '待结算', '0', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1372, 1, '已结算', '1', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1373, 2, '已取消', '2', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1374, 0, '审核中', '0', 'brokerage_withdraw_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1375, 10, '审核通过', '10', 'brokerage_withdraw_status', 0, 'success', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1376, 11, '提现成功', '11', 'brokerage_withdraw_status', 0, 'success', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1377, 20, '审核不通过', '20', 'brokerage_withdraw_status', 0, 'danger', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1378, 21, '提现失败', '21', 'brokerage_withdraw_status', 0, 'danger', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1379, 0, '工商银行', '0', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1380, 1, '建设银行', '1', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1381, 2, '农业银行', '2', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1382, 3, '中国银行', '3', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1383, 4, '交通银行', '4', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1384, 5, '招商银行', '5', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0');
INSERT INTO system_dict_data  VALUES (1385, 21, '钱包', 'wallet', 'pay_channel_code', 0, 'primary', '', '', '1', '2023-10-01 21:46:19', '1', '2023-10-01 21:48:01', '0');
INSERT INTO system_dict_data  VALUES (1386, 1, '砍价中', '1', 'promotion_bargain_record_status', 0, 'default', '', '', '1', '2023-10-05 10:41:26', '1', '2023-10-05 10:41:26', '0');
INSERT INTO system_dict_data  VALUES (1387, 2, '砍价成功', '2', 'promotion_bargain_record_status', 0, 'success', '', '', '1', '2023-10-05 10:41:39', '1', '2023-10-05 10:41:39', '0');
INSERT INTO system_dict_data  VALUES (1388, 3, '砍价失败', '3', 'promotion_bargain_record_status', 0, 'warning', '', '', '1', '2023-10-05 10:41:57', '1', '2023-10-05 10:41:57', '0');
INSERT INTO system_dict_data  VALUES (1389, 0, '拼团中', '0', 'promotion_combination_record_status', 0, '', '', '', '1', '2023-10-08 07:24:44', '1', '2024-10-13 10:08:17', '0');
INSERT INTO system_dict_data  VALUES (1390, 1, '拼团成功', '1', 'promotion_combination_record_status', 0, 'success', '', '', '1', '2023-10-08 07:24:56', '1', '2024-10-13 10:08:20', '0');
INSERT INTO system_dict_data  VALUES (1391, 2, '拼团失败', '2', 'promotion_combination_record_status', 0, 'warning', '', '', '1', '2023-10-08 07:25:11', '1', '2024-10-13 10:08:24', '0');
INSERT INTO system_dict_data  VALUES (1392, 2, '管理员修改', '2', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:41:34', '1', '2023-10-11 07:41:34', '0');
INSERT INTO system_dict_data  VALUES (1393, 13, '订单积分抵扣（单个退款）', '13', 'member_point_biz_type', 0, '', '', '', '1', '2023-10-11 07:42:29', '1', '2023-10-11 07:42:29', '0');
INSERT INTO system_dict_data  VALUES (1394, 21, '订单积分奖励', '21', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:42:44', '1', '2023-10-11 07:42:44', '0');
INSERT INTO system_dict_data  VALUES (1395, 22, '订单积分奖励（整单取消）', '22', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:42:55', '1', '2023-10-11 07:43:01', '0');
INSERT INTO system_dict_data  VALUES (1396, 23, '订单积分奖励（单个退款）', '23', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:43:16', '1', '2023-10-11 07:43:16', '0');
INSERT INTO system_dict_data  VALUES (1397, 13, '下单奖励（单个退款）', '13', 'member_experience_biz_type', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', '0');
INSERT INTO system_dict_data  VALUES (1398, 5, '网上转账', '5', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:24', '1', '2023-10-18 21:55:24', '0');
INSERT INTO system_dict_data  VALUES (1399, 6, '支付宝', '6', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:38', '1', '2023-10-18 21:55:38', '0');
INSERT INTO system_dict_data  VALUES (1400, 7, '微信支付', '7', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:53', '1', '2023-10-18 21:55:53', '0');
INSERT INTO system_dict_data  VALUES (1401, 8, '其他', '8', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:56:06', '1', '2023-10-18 21:56:06', '0');
INSERT INTO system_dict_data  VALUES (1402, 1, 'IT', '1', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:15', '1', '2024-02-18 23:30:38', '0');
INSERT INTO system_dict_data  VALUES (1403, 2, '金融业', '2', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:29', '1', '2024-02-18 23:30:43', '0');
INSERT INTO system_dict_data  VALUES (1404, 3, '房地产', '3', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:41', '1', '2024-02-18 23:30:48', '0');
INSERT INTO system_dict_data  VALUES (1405, 4, '商业服务', '4', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:54', '1', '2024-02-18 23:30:54', '0');
INSERT INTO system_dict_data  VALUES (1406, 5, '运输/物流', '5', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:03', '1', '2024-02-18 23:31:00', '0');
INSERT INTO system_dict_data  VALUES (1407, 6, '生产', '6', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:13', '1', '2024-02-18 23:31:08', '0');
INSERT INTO system_dict_data  VALUES (1408, 7, '政府', '7', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:27', '1', '2024-02-18 23:31:13', '0');
INSERT INTO system_dict_data  VALUES (1409, 8, '文化传媒', '8', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:37', '1', '2024-02-18 23:31:20', '0');
INSERT INTO system_dict_data  VALUES (1422, 1, 'A （重点客户）', '1', 'crm_customer_level', 0, 'primary', '', '', '1', '2023-10-28 23:07:13', '1', '2023-10-28 23:07:13', '0');
INSERT INTO system_dict_data  VALUES (1423, 2, 'B （普通客户）', '2', 'crm_customer_level', 0, 'info', '', '', '1', '2023-10-28 23:07:35', '1', '2023-10-28 23:07:35', '0');
INSERT INTO system_dict_data  VALUES (1424, 3, 'C （非优先客户）', '3', 'crm_customer_level', 0, 'default', '', '', '1', '2023-10-28 23:07:53', '1', '2023-10-28 23:07:53', '0');
INSERT INTO system_dict_data  VALUES (1425, 1, '促销', '1', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:29', '1', '2023-10-28 23:08:29', '0');
INSERT INTO system_dict_data  VALUES (1426, 2, '搜索引擎', '2', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:39', '1', '2023-10-28 23:08:39', '0');
INSERT INTO system_dict_data  VALUES (1427, 3, '广告', '3', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:47', '1', '2023-10-28 23:08:47', '0');
INSERT INTO system_dict_data  VALUES (1428, 4, '转介绍', '4', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:58', '1', '2023-10-28 23:08:58', '0');
INSERT INTO system_dict_data  VALUES (1429, 5, '线上注册', '5', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:12', '1', '2023-10-28 23:09:12', '0');
INSERT INTO system_dict_data  VALUES (1430, 6, '线上咨询', '6', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:22', '1', '2023-10-28 23:09:22', '0');
INSERT INTO system_dict_data  VALUES (1431, 7, '预约上门', '7', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:39', '1', '2023-10-28 23:09:39', '0');
INSERT INTO system_dict_data  VALUES (1432, 8, '陌拜', '8', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:04', '1', '2023-10-28 23:10:04', '0');
INSERT INTO system_dict_data  VALUES (1433, 9, '电话咨询', '9', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:18', '1', '2023-10-28 23:10:18', '0');
INSERT INTO system_dict_data  VALUES (1434, 10, '邮件咨询', '10', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:33', '1', '2023-10-28 23:10:33', '0');
INSERT INTO system_dict_data  VALUES (1435, 10, 'Gitee', '10', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:04:42', '1', '2023-11-04 13:04:42', '0');
INSERT INTO system_dict_data  VALUES (1436, 20, '钉钉', '20', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:04:54', '1', '2023-11-04 13:04:54', '0');
INSERT INTO system_dict_data  VALUES (1437, 30, '企业微信', '30', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:09', '1', '2023-11-04 13:05:09', '0');
INSERT INTO system_dict_data  VALUES (1438, 31, '微信公众平台', '31', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:18', '1', '2023-11-04 13:05:18', '0');
INSERT INTO system_dict_data  VALUES (1439, 32, '微信开放平台', '32', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:30', '1', '2023-11-04 13:05:30', '0');
INSERT INTO system_dict_data  VALUES (1440, 34, '微信小程序', '34', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:38', '1', '2023-11-04 13:07:16', '0');
INSERT INTO system_dict_data  VALUES (1441, 1, '上架', '1', 'crm_product_status', 0, 'success', '', '', '1', '2023-10-30 21:49:34', '1', '2023-10-30 21:49:34', '0');
INSERT INTO system_dict_data  VALUES (1442, 0, '下架', '0', 'crm_product_status', 0, 'success', '', '', '1', '2023-10-30 21:49:13', '1', '2023-10-30 21:49:13', '0');
INSERT INTO system_dict_data  VALUES (1443, 15, '子表', '15', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-13 23:06:16', '1', '2023-11-13 23:06:16', '0');
INSERT INTO system_dict_data  VALUES (1444, 10, '主表（标准模式）', '10', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-14 12:32:49', '1', '2023-11-14 12:32:49', '0');
INSERT INTO system_dict_data  VALUES (1445, 11, '主表（ERP 模式）', '11', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-14 12:33:05', '1', '2023-11-14 12:33:05', '0');
INSERT INTO system_dict_data  VALUES (1446, 12, '主表（内嵌模式）', '12', 'infra_codegen_template_type', 0, '', '', '', '1', '2023-11-14 12:33:31', '1', '2023-11-14 12:33:31', '0');
INSERT INTO system_dict_data  VALUES (1447, 1, '负责人', '1', 'crm_permission_level', 0, 'default', '', '', '1', '2023-11-30 09:53:12', '1', '2023-11-30 09:53:12', '0');
INSERT INTO system_dict_data  VALUES (1448, 2, '只读', '2', 'crm_permission_level', 0, '', '', '', '1', '2023-11-30 09:53:29', '1', '2023-11-30 09:53:29', '0');
INSERT INTO system_dict_data  VALUES (1449, 3, '读写', '3', 'crm_permission_level', 0, '', '', '', '1', '2023-11-30 09:53:36', '1', '2023-11-30 09:53:36', '0');
INSERT INTO system_dict_data  VALUES (1450, 0, '未提交', '0', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:56:59', '1', '2023-11-30 18:56:59', '0');
INSERT INTO system_dict_data  VALUES (1451, 10, '审批中', '10', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:10', '1', '2023-11-30 18:57:10', '0');
INSERT INTO system_dict_data  VALUES (1452, 20, '审核通过', '20', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:24', '1', '2023-11-30 18:57:24', '0');
INSERT INTO system_dict_data  VALUES (1453, 30, '审核不通过', '30', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:32', '1', '2023-11-30 18:57:32', '0');
INSERT INTO system_dict_data  VALUES (1454, 40, '已取消', '40', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:42', '1', '2023-11-30 18:57:42', '0');
INSERT INTO system_dict_data  VALUES (1456, 1, '支票', '1', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:29', '1', '2023-10-18 21:54:29', '0');
INSERT INTO system_dict_data  VALUES (1457, 2, '现金', '2', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:41', '1', '2023-10-18 21:54:41', '0');
INSERT INTO system_dict_data  VALUES (1458, 3, '邮政汇款', '3', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:53', '1', '2023-10-18 21:54:53', '0');
INSERT INTO system_dict_data  VALUES (1459, 4, '电汇', '4', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:07', '1', '2023-10-18 21:55:07', '0');
INSERT INTO system_dict_data  VALUES (1461, 1, '个', '1', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:26', '1', '2023-12-05 23:02:26', '0');
INSERT INTO system_dict_data  VALUES (1462, 2, '块', '2', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:34', '1', '2023-12-05 23:02:34', '0');
INSERT INTO system_dict_data  VALUES (1463, 3, '只', '3', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:57', '1', '2023-12-05 23:02:57', '0');
INSERT INTO system_dict_data  VALUES (1464, 4, '把', '4', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:05', '1', '2023-12-05 23:03:05', '0');
INSERT INTO system_dict_data  VALUES (1465, 5, '枚', '5', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:14', '1', '2023-12-05 23:03:14', '0');
INSERT INTO system_dict_data  VALUES (1466, 6, '瓶', '6', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:20', '1', '2023-12-05 23:03:20', '0');
INSERT INTO system_dict_data  VALUES (1467, 7, '盒', '7', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:30', '1', '2023-12-05 23:03:30', '0');
INSERT INTO system_dict_data  VALUES (1468, 8, '台', '8', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:41', '1', '2023-12-05 23:03:41', '0');
INSERT INTO system_dict_data  VALUES (1469, 9, '吨', '9', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:48', '1', '2023-12-05 23:03:48', '0');
INSERT INTO system_dict_data  VALUES (1470, 10, '千克', '10', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:03', '1', '2023-12-05 23:04:03', '0');
INSERT INTO system_dict_data  VALUES (1471, 11, '米', '11', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:12', '1', '2023-12-05 23:04:12', '0');
INSERT INTO system_dict_data  VALUES (1472, 12, '箱', '12', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:25', '1', '2023-12-05 23:04:25', '0');
INSERT INTO system_dict_data  VALUES (1473, 13, '套', '13', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:34', '1', '2023-12-05 23:04:34', '0');
INSERT INTO system_dict_data  VALUES (1474, 1, '打电话', '1', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:48:20', '1', '2024-01-15 20:48:20', '0');
INSERT INTO system_dict_data  VALUES (1475, 2, '发短信', '2', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:48:31', '1', '2024-01-15 20:48:31', '0');
INSERT INTO system_dict_data  VALUES (1476, 3, '上门拜访', '3', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:49:07', '1', '2024-01-15 20:49:07', '0');
INSERT INTO system_dict_data  VALUES (1477, 4, '微信沟通', '4', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:49:15', '1', '2024-01-15 20:49:15', '0');
INSERT INTO system_dict_data  VALUES (1478, 4, '钱包余额', '4', 'pay_transfer_type', 0, 'info', '', '', '1', '2023-10-28 16:28:37', '1', '2023-10-28 16:28:37', '0');
INSERT INTO system_dict_data  VALUES (1479, 3, '银行卡', '3', 'pay_transfer_type', 0, 'default', '', '', '1', '2023-10-28 16:28:21', '1', '2023-10-28 16:28:21', '0');
INSERT INTO system_dict_data  VALUES (1480, 2, '微信余额', '2', 'pay_transfer_type', 0, 'info', '', '', '1', '2023-10-28 16:28:07', '1', '2023-10-28 16:28:07', '0');
INSERT INTO system_dict_data  VALUES (1481, 1, '支付宝余额', '1', 'pay_transfer_type', 0, 'default', '', '', '1', '2023-10-28 16:27:44', '1', '2023-10-28 16:27:44', '0');
INSERT INTO system_dict_data  VALUES (1482, 4, '转账失败', '30', 'pay_transfer_status', 0, 'warning', '', '', '1', '2023-10-28 16:24:16', '1', '2023-10-28 16:24:16', '0');
INSERT INTO system_dict_data  VALUES (1483, 3, '转账成功', '20', 'pay_transfer_status', 0, 'success', '', '', '1', '2023-10-28 16:23:50', '1', '2023-10-28 16:23:50', '0');
INSERT INTO system_dict_data  VALUES (1484, 2, '转账进行中', '10', 'pay_transfer_status', 0, 'info', '', '', '1', '2023-10-28 16:23:12', '1', '2023-10-28 16:23:12', '0');
INSERT INTO system_dict_data  VALUES (1485, 1, '等待转账', '0', 'pay_transfer_status', 0, 'default', '', '', '1', '2023-10-28 16:21:43', '1', '2023-10-28 16:23:22', '0');
INSERT INTO system_dict_data  VALUES (1486, 10, '其它入库', '10', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-05 18:07:25', '1', '2024-02-05 18:07:43', '0');
INSERT INTO system_dict_data  VALUES (1487, 11, '其它入库（作废）', '11', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-05 18:08:07', '1', '2024-02-05 19:20:16', '0');
INSERT INTO system_dict_data  VALUES (1488, 20, '其它出库', '20', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-05 18:08:51', '1', '2024-02-05 18:08:51', '0');
INSERT INTO system_dict_data  VALUES (1489, 21, '其它出库（作废）', '21', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-05 18:09:00', '1', '2024-02-05 19:20:10', '0');
INSERT INTO system_dict_data  VALUES (1490, 10, '未审核', '10', 'erp_audit_status', 0, 'default', '', '', '1', '2024-02-06 00:00:21', '1', '2024-02-06 00:00:21', '0');
INSERT INTO system_dict_data  VALUES (1491, 20, '已审核', '20', 'erp_audit_status', 0, 'success', '', '', '1', '2024-02-06 00:00:35', '1', '2024-02-06 00:00:35', '0');
INSERT INTO system_dict_data  VALUES (1492, 30, '调拨入库', '30', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-07 20:34:19', '1', '2024-02-07 12:36:31', '0');
INSERT INTO system_dict_data  VALUES (1493, 31, '调拨入库（作废）', '31', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-07 20:34:29', '1', '2024-02-07 20:37:11', '0');
INSERT INTO system_dict_data  VALUES (1494, 32, '调拨出库', '32', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-07 20:34:38', '1', '2024-02-07 12:36:33', '0');
INSERT INTO system_dict_data  VALUES (1495, 33, '调拨出库（作废）', '33', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-07 20:34:49', '1', '2024-02-07 20:37:06', '0');
INSERT INTO system_dict_data  VALUES (1496, 40, '盘盈入库', '40', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-08 08:53:00', '1', '2024-02-08 08:53:09', '0');
INSERT INTO system_dict_data  VALUES (1497, 41, '盘盈入库（作废）', '41', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-08 08:53:39', '1', '2024-02-16 19:40:54', '0');
INSERT INTO system_dict_data  VALUES (1498, 42, '盘亏出库', '42', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-08 08:54:16', '1', '2024-02-08 08:54:16', '0');
INSERT INTO system_dict_data  VALUES (1499, 43, '盘亏出库（作废）', '43', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-08 08:54:31', '1', '2024-02-16 19:40:46', '0');
INSERT INTO system_dict_data  VALUES (1500, 50, '销售出库', '50', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-11 21:47:25', '1', '2024-02-11 21:50:40', '0');
INSERT INTO system_dict_data  VALUES (1501, 51, '销售出库（作废）', '51', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-11 21:47:37', '1', '2024-02-11 21:51:12', '0');
INSERT INTO system_dict_data  VALUES (1502, 60, '销售退货入库', '60', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-12 06:51:05', '1', '2024-02-12 06:51:05', '0');
INSERT INTO system_dict_data  VALUES (1503, 61, '销售退货入库（作废）', '61', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-12 06:51:18', '1', '2024-02-12 06:51:18', '0');
INSERT INTO system_dict_data  VALUES (1504, 70, '采购入库', '70', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-16 13:10:02', '1', '2024-02-16 13:10:02', '0');
INSERT INTO system_dict_data  VALUES (1505, 71, '采购入库（作废）', '71', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-16 13:10:10', '1', '2024-02-16 19:40:40', '0');
INSERT INTO system_dict_data  VALUES (1506, 80, '采购退货出库', '80', 'erp_stock_record_biz_type', 0, '', '', '', '1', '2024-02-16 13:10:17', '1', '2024-02-16 13:10:17', '0');
INSERT INTO system_dict_data  VALUES (1507, 81, '采购退货出库（作废）', '81', 'erp_stock_record_biz_type', 0, 'danger', '', '', '1', '2024-02-16 13:10:26', '1', '2024-02-16 19:40:33', '0');
INSERT INTO system_dict_data  VALUES (1509, 3, '审批不通过', '3', 'bpm_process_instance_status', 0, 'danger', '', '', '1', '2024-03-16 16:12:06', '1', '2024-03-16 16:12:06', '0');
INSERT INTO system_dict_data  VALUES (1510, 4, '已取消', '4', 'bpm_process_instance_status', 0, 'warning', '', '', '1', '2024-03-16 16:12:22', '1', '2024-03-16 16:12:22', '0');
INSERT INTO system_dict_data  VALUES (1511, 5, '已退回', '5', 'bpm_task_status', 0, 'warning', '', '', '1', '2024-03-16 19:10:46', '1', '2024-03-08 22:41:40', '0');
INSERT INTO system_dict_data  VALUES (1512, 6, '委派中', '6', 'bpm_task_status', 0, 'primary', '', '', '1', '2024-03-17 10:06:22', '1', '2024-03-08 22:41:40', '0');
INSERT INTO system_dict_data  VALUES (1513, 7, '审批通过中', '7', 'bpm_task_status', 0, 'success', '', '', '1', '2024-03-17 10:06:47', '1', '2024-03-08 22:41:41', '0');
INSERT INTO system_dict_data  VALUES (1514, 0, '待审批', '0', 'bpm_task_status', 0, 'info', '', '', '1', '2024-03-17 10:07:11', '1', '2024-03-08 22:41:42', '0');
INSERT INTO system_dict_data  VALUES (1515, 35, '发起人自选', '35', 'bpm_task_candidate_strategy', 0, '', '', '', '1', '2024-03-22 19:45:16', '1', '2024-03-22 19:45:16', '0');
INSERT INTO system_dict_data  VALUES (1516, 1, '执行监听器', 'execution', 'bpm_process_listener_type', 0, 'primary', '', '', '1', '2024-03-23 12:54:03', '1', '2024-03-23 19:14:19', '0');
INSERT INTO system_dict_data  VALUES (1517, 1, '任务监听器', 'task', 'bpm_process_listener_type', 0, 'success', '', '', '1', '2024-03-23 12:54:13', '1', '2024-03-23 19:14:24', '0');
INSERT INTO system_dict_data  VALUES (1526, 1, 'Java 类', 'class', 'bpm_process_listener_value_type', 0, 'primary', '', '', '1', '2024-03-23 15:08:45', '1', '2024-03-23 19:14:32', '0');
INSERT INTO system_dict_data  VALUES (1527, 2, '表达式', 'expression', 'bpm_process_listener_value_type', 0, 'success', '', '', '1', '2024-03-23 15:09:06', '1', '2024-03-23 19:14:38', '0');
INSERT INTO system_dict_data  VALUES (1528, 3, '代理表达式', 'delegateExpression', 'bpm_process_listener_value_type', 0, 'info', '', '', '1', '2024-03-23 15:11:23', '1', '2024-03-23 19:14:41', '0');
INSERT INTO system_dict_data  VALUES (1529, 1, '天', '1', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:50:26', '1', '2024-03-29 22:50:26', '0');
INSERT INTO system_dict_data  VALUES (1530, 2, '周', '2', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:50:36', '1', '2024-03-29 22:50:36', '0');
INSERT INTO system_dict_data  VALUES (1531, 3, '月', '3', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:50:46', '1', '2024-03-29 22:50:54', '0');
INSERT INTO system_dict_data  VALUES (1532, 4, '季度', '4', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:51:01', '1', '2024-03-29 22:51:01', '0');
INSERT INTO system_dict_data  VALUES (1533, 5, '年', '5', 'date_interval', 0, '', '', '', '1', '2024-03-29 22:51:07', '1', '2024-03-29 22:51:07', '0');
INSERT INTO system_dict_data  VALUES (1534, 1, '赢单', '1', 'crm_business_end_status_type', 0, 'success', '', '', '1', '2024-04-13 23:26:57', '1', '2024-04-13 23:26:57', '0');
INSERT INTO system_dict_data  VALUES (1535, 2, '输单', '2', 'crm_business_end_status_type', 0, 'primary', '', '', '1', '2024-04-13 23:27:31', '1', '2024-04-13 23:27:31', '0');
INSERT INTO system_dict_data  VALUES (1536, 3, '无效', '3', 'crm_business_end_status_type', 0, 'info', '', '', '1', '2024-04-13 23:27:59', '1', '2024-04-13 23:27:59', '0');
INSERT INTO system_dict_data  VALUES (1537, 1, 'OpenAI', 'OpenAI', 'ai_platform', 0, '', '', '', '1', '2024-05-09 22:33:47', '1', '2024-05-09 22:58:46', '0');
INSERT INTO system_dict_data  VALUES (1538, 2, 'Ollama', 'Ollama', 'ai_platform', 0, '', '', '', '1', '2024-05-17 23:02:55', '1', '2024-05-17 23:02:55', '0');
INSERT INTO system_dict_data  VALUES (1539, 3, '文心一言', 'YiYan', 'ai_platform', 0, '', '', '', '1', '2024-05-18 09:24:20', '1', '2024-05-18 09:29:01', '0');
INSERT INTO system_dict_data  VALUES (1540, 4, '讯飞星火', 'XingHuo', 'ai_platform', 0, '', '', '', '1', '2024-05-18 10:08:56', '1', '2024-05-18 10:08:56', '0');
INSERT INTO system_dict_data  VALUES (1541, 5, '通义千问', 'TongYi', 'ai_platform', 0, '', '', '', '1', '2024-05-18 10:32:29', '1', '2024-07-06 15:42:29', '0');
INSERT INTO system_dict_data  VALUES (1542, 6, 'StableDiffusion', 'StableDiffusion', 'ai_platform', 0, '', '', '', '1', '2024-06-01 15:09:31', '1', '2024-06-01 15:10:25', '0');
INSERT INTO system_dict_data  VALUES (1543, 10, '进行中', '10', 'ai_image_status', 0, 'primary', '', '', '1', '2024-06-26 20:51:41', '1', '2024-06-26 20:52:48', '0');
INSERT INTO system_dict_data  VALUES (1544, 20, '已完成', '20', 'ai_image_status', 0, 'success', '', '', '1', '2024-06-26 20:52:07', '1', '2024-06-26 20:52:41', '0');
INSERT INTO system_dict_data  VALUES (1545, 30, '已失败', '30', 'ai_image_status', 0, 'warning', '', '', '1', '2024-06-26 20:52:25', '1', '2024-06-26 20:52:35', '0');
INSERT INTO system_dict_data  VALUES (1546, 7, 'Midjourney', 'Midjourney', 'ai_platform', 0, '', '', '', '1', '2024-06-26 22:14:46', '1', '2024-06-26 22:14:46', '0');
INSERT INTO system_dict_data  VALUES (1547, 10, '进行中', '10', 'ai_music_status', 0, 'primary', '', '', '1', '2024-06-27 22:45:22', '1', '2024-06-28 00:56:17', '0');
INSERT INTO system_dict_data  VALUES (1548, 20, '已完成', '20', 'ai_music_status', 0, 'success', '', '', '1', '2024-06-27 22:45:33', '1', '2024-06-28 00:56:18', '0');
INSERT INTO system_dict_data  VALUES (1549, 30, '已失败', '30', 'ai_music_status', 0, 'danger', '', '', '1', '2024-06-27 22:45:44', '1', '2024-06-28 00:56:19', '0');
INSERT INTO system_dict_data  VALUES (1550, 1, '歌词模式', '1', 'ai_generate_mode', 0, '', '', '', '1', '2024-06-27 22:46:31', '1', '2024-06-28 01:22:25', '0');
INSERT INTO system_dict_data  VALUES (1551, 2, '描述模式', '2', 'ai_generate_mode', 0, '', '', '', '1', '2024-06-27 22:46:37', '1', '2024-06-28 01:22:24', '0');
INSERT INTO system_dict_data  VALUES (1552, 8, 'Suno', 'Suno', 'ai_platform', 0, '', '', '', '1', '2024-06-29 09:13:36', '1', '2024-06-29 09:13:41', '0');
INSERT INTO system_dict_data  VALUES (1553, 9, 'DeepSeek', 'DeepSeek', 'ai_platform', 0, '', '', '', '1', '2024-07-06 12:04:30', '1', '2024-07-06 12:05:20', '0');
INSERT INTO system_dict_data  VALUES (1554, 10, '智谱', 'ZhiPu', 'ai_platform', 0, '', '', '', '1', '2024-07-06 18:00:35', '1', '2024-07-06 18:00:35', '0');
INSERT INTO system_dict_data  VALUES (1555, 4, '长', '4', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:49:03', '1', '2024-07-07 15:49:03', '0');
INSERT INTO system_dict_data  VALUES (1556, 5, '段落', '5', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:49:54', '1', '2024-07-07 15:49:54', '0');
INSERT INTO system_dict_data  VALUES (1557, 6, '文章', '6', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:50:05', '1', '2024-07-07 15:50:05', '0');
INSERT INTO system_dict_data  VALUES (1558, 7, '博客文章', '7', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:50:23', '1', '2024-07-07 15:50:23', '0');
INSERT INTO system_dict_data  VALUES (1559, 8, '想法', '8', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:50:31', '1', '2024-07-07 15:50:31', '0');
INSERT INTO system_dict_data  VALUES (1560, 9, '大纲', '9', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:50:37', '1', '2024-07-07 15:50:37', '0');
INSERT INTO system_dict_data  VALUES (1561, 1, '自动', '1', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:06', '1', '2024-07-07 15:51:06', '0');
INSERT INTO system_dict_data  VALUES (1562, 2, '友善', '2', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:19', '1', '2024-07-07 15:51:19', '0');
INSERT INTO system_dict_data  VALUES (1563, 3, '随意', '3', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:27', '1', '2024-07-07 15:51:27', '0');
INSERT INTO system_dict_data  VALUES (1564, 4, '友好', '4', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:37', '1', '2024-07-07 15:51:37', '0');
INSERT INTO system_dict_data  VALUES (1565, 5, '专业', '5', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:51:49', '1', '2024-07-07 15:52:02', '0');
INSERT INTO system_dict_data  VALUES (1566, 6, '诙谐', '6', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:52:15', '1', '2024-07-07 15:52:15', '0');
INSERT INTO system_dict_data  VALUES (1567, 7, '有趣', '7', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:52:24', '1', '2024-07-07 15:52:24', '0');
INSERT INTO system_dict_data  VALUES (1568, 8, '正式', '8', 'ai_write_tone', 0, '', '', '', '1', '2024-07-07 15:54:33', '1', '2024-07-07 15:54:33', '0');
INSERT INTO system_dict_data  VALUES (1569, 5, '段落', '5', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:49:54', '1', '2024-07-07 15:49:54', '0');
INSERT INTO system_dict_data  VALUES (1570, 1, '自动', '1', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:19:34', '1', '2024-07-07 15:19:34', '0');
INSERT INTO system_dict_data  VALUES (1571, 2, '电子邮件', '2', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:19:50', '1', '2024-07-07 15:49:30', '0');
INSERT INTO system_dict_data  VALUES (1572, 3, '消息', '3', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:20:01', '1', '2024-07-07 15:49:38', '0');
INSERT INTO system_dict_data  VALUES (1573, 4, '评论', '4', 'ai_write_format', 0, '', '', '', '1', '2024-07-07 15:20:13', '1', '2024-07-07 15:49:45', '0');
INSERT INTO system_dict_data  VALUES (1574, 1, '自动', '1', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:44:18', '1', '2024-07-07 15:44:18', '0');
INSERT INTO system_dict_data  VALUES (1575, 2, '中文', '2', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:44:28', '1', '2024-07-07 15:44:28', '0');
INSERT INTO system_dict_data  VALUES (1576, 3, '英文', '3', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:44:37', '1', '2024-07-07 15:44:37', '0');
INSERT INTO system_dict_data  VALUES (1577, 4, '韩语', '4', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:46:28', '1', '2024-07-07 15:46:28', '0');
INSERT INTO system_dict_data  VALUES (1578, 5, '日语', '5', 'ai_write_language', 0, '', '', '', '1', '2024-07-07 15:46:44', '1', '2024-07-07 15:46:44', '0');
INSERT INTO system_dict_data  VALUES (1579, 1, '自动', '1', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:48:34', '1', '2024-07-07 15:48:34', '0');
INSERT INTO system_dict_data  VALUES (1580, 2, '短', '2', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:48:44', '1', '2024-07-07 15:48:44', '0');
INSERT INTO system_dict_data  VALUES (1581, 3, '中等', '3', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:48:52', '1', '2024-07-07 15:48:52', '0');
INSERT INTO system_dict_data  VALUES (1582, 4, '长', '4', 'ai_write_length', 0, '', '', '', '1', '2024-07-07 15:49:03', '1', '2024-07-07 15:49:03', '0');
INSERT INTO system_dict_data  VALUES (1584, 1, '撰写', '1', 'ai_write_type', 0, '', '', '', '1', '2024-07-10 21:26:00', '1', '2024-07-10 21:26:00', '0');
INSERT INTO system_dict_data  VALUES (1585, 2, '回复', '2', 'ai_write_type', 0, '', '', '', '1', '2024-07-10 21:26:06', '1', '2024-07-10 21:26:06', '0');
INSERT INTO system_dict_data  VALUES (1586, 2, '腾讯云', 'TENCENT', 'system_sms_channel_code', 0, '', '', '', '1', '2024-07-22 22:23:16', '1', '2024-07-22 22:23:16', '0');
INSERT INTO system_dict_data  VALUES (1587, 3, '华为云', 'HUAWEI', 'system_sms_channel_code', 0, '', '', '', '1', '2024-07-22 22:23:46', '1', '2024-07-22 22:23:53', '0');
INSERT INTO system_dict_data  VALUES (1588, 1, 'OpenAI 微软', 'AzureOpenAI', 'ai_platform', 0, '', '', '', '1', '2024-08-10 14:07:41', '1', '2024-08-10 14:07:41', '0');
INSERT INTO system_dict_data  VALUES (1589, 10, 'BPMN 设计器', '10', 'bpm_model_type', 0, 'primary', '', '', '1', '2024-08-26 15:22:17', '1', '2024-08-26 16:46:02', '0');
INSERT INTO system_dict_data  VALUES (1590, 20, 'SIMPLE 设计器', '20', 'bpm_model_type', 0, 'success', '', '', '1', '2024-08-26 15:22:27', '1', '2024-08-26 16:45:58', '0');
INSERT INTO system_dict_data  VALUES (1591, 4, '七牛云', 'QINIU', 'system_sms_channel_code', 0, '', '', '', '1', '2024-08-31 08:45:03', '1', '2024-08-31 08:45:24', '0');
INSERT INTO system_dict_data  VALUES (1592, 3, '新人券', '3', 'promotion_coupon_take_type', 0, 'info', '', '新人注册后，自动发放', '1', '2024-09-03 11:57:16', '1', '2024-09-03 11:57:28', '0');
INSERT INTO system_dict_data  VALUES (1593, 5, '微信零钱', '5', 'brokerage_withdraw_type', 0, '', '', '自动打款', '1', '2024-10-13 11:06:48', '1', '2024-10-13 11:06:59', '0');
INSERT INTO system_dict_data  VALUES (1655, 0, '标准数据格式（JSON）', '0', 'iot_data_format', 0, 'default', '', '', '1', '2024-08-10 11:53:26', '1', '2024-09-06 14:31:02', '0');
INSERT INTO system_dict_data  VALUES (1656, 1, '透传/自定义', '1', 'iot_data_format', 0, 'default', '', '', '1', '2024-08-10 11:53:37', '1', '2024-09-06 14:30:54', '0');
INSERT INTO system_dict_data  VALUES (1657, 0, '直连设备', '0', 'iot_product_device_type', 0, 'default', '', '', '1', '2024-08-10 11:54:58', '1', '2024-09-06 21:57:01', '0');
INSERT INTO system_dict_data  VALUES (1658, 2, '网关设备', '2', 'iot_product_device_type', 0, 'default', '', '', '1', '2024-08-10 11:55:08', '1', '2024-09-06 21:56:46', '0');
INSERT INTO system_dict_data  VALUES (1659, 1, '网关子设备', '1', 'iot_product_device_type', 0, 'default', '', '', '1', '2024-08-10 11:55:20', '1', '2024-09-06 21:57:10', '0');
INSERT INTO system_dict_data  VALUES (1661, 1, '已发布', '1', 'iot_product_status', 0, 'success', '', '', '1', '2024-08-10 12:10:33', '1', '2024-09-06 22:06:22', '0');
INSERT INTO system_dict_data  VALUES (1663, 0, '开发中', '0', 'iot_product_status', 0, 'default', '', '', '1', '2024-08-10 14:19:18', '1', '2024-09-07 10:58:07', '0');
INSERT INTO system_dict_data  VALUES (1665, 0, '弱校验', '0', 'iot_validate_type', 0, '', '', '', '1', '2024-09-06 20:05:48', '1', '2024-09-06 22:02:44', '0');
INSERT INTO system_dict_data  VALUES (1666, 1, '免校验', '1', 'iot_validate_type', 0, '', '', '', '1', '2024-09-06 20:06:03', '1', '2024-09-06 22:02:51', '0');
INSERT INTO system_dict_data  VALUES (1667, 0, 'Wi-Fi', '0', 'iot_net_type', 0, '', '', '', '1', '2024-09-06 22:04:47', '1', '2024-09-06 22:04:47', '0');
INSERT INTO system_dict_data  VALUES (1668, 1, '蜂窝（2G / 3G / 4G / 5G）', '1', 'iot_net_type', 0, '', '', '', '1', '2024-09-06 22:05:14', '1', '2024-09-06 22:05:14', '0');
INSERT INTO system_dict_data  VALUES (1669, 2, '以太网', '2', 'iot_net_type', 0, '', '', '', '1', '2024-09-06 22:05:35', '1', '2024-09-06 22:05:35', '0');
INSERT INTO system_dict_data  VALUES (1670, 3, '其他', '3', 'iot_net_type', 0, '', '', '', '1', '2024-09-06 22:05:52', '1', '2024-09-06 22:05:52', '0');
INSERT INTO system_dict_data  VALUES (1671, 0, '自定义', '0', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:10', '1', '2024-09-06 22:26:10', '0');
INSERT INTO system_dict_data  VALUES (1672, 1, 'Modbus', '1', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:21', '1', '2024-09-06 22:26:21', '0');
INSERT INTO system_dict_data  VALUES (1673, 2, 'OPC UA', '2', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:31', '1', '2024-09-06 22:26:31', '0');
INSERT INTO system_dict_data  VALUES (1674, 3, 'ZigBee', '3', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:39', '1', '2024-09-06 22:26:39', '0');
INSERT INTO system_dict_data  VALUES (1675, 4, 'BLE', '4', 'iot_protocol_type', 0, '', '', '', '1', '2024-09-06 22:26:48', '1', '2024-09-06 22:26:48', '0');
INSERT INTO system_dict_data  VALUES (1676, 0, '未激活', '0', 'iot_device_status', 0, '', '', '', '1', '2024-09-21 08:13:34', '1', '2024-09-21 08:13:34', '0');
INSERT INTO system_dict_data  VALUES (1677, 1, '在线', '1', 'iot_device_status', 0, '', '', '', '1', '2024-09-21 08:13:48', '1', '2024-09-21 08:13:48', '0');
INSERT INTO system_dict_data  VALUES (1678, 2, '离线', '2', 'iot_device_status', 0, '', '', '', '1', '2024-09-21 08:13:59', '1', '2024-09-21 08:13:59', '0');
INSERT INTO system_dict_data  VALUES (1679, 3, '已禁用', '3', 'iot_device_status', 0, '', '', '', '1', '2024-09-21 08:14:13', '1', '2024-09-21 08:14:13', '0');
INSERT INTO system_dict_data  VALUES (1680, 1, '属性', '1', 'iot_product_function_type', 0, '', '', '', '1', '2024-09-29 20:03:01', '1', '2024-09-29 20:09:41', '0');
INSERT INTO system_dict_data  VALUES (1681, 2, '服务', '2', 'iot_product_function_type', 0, '', '', '', '1', '2024-09-29 20:03:11', '1', '2024-09-29 20:08:23', '0');
INSERT INTO system_dict_data  VALUES (1682, 3, '事件', '3', 'iot_product_function_type', 0, '', '', '', '1', '2024-09-29 20:03:20', '1', '2024-09-29 20:08:20', '0');
INSERT INTO system_dict_data  VALUES (1683, 2, '蓝牙mac', '2', 'eiot_dn_typ', 0, '', '', '', '1', '2025-01-07 11:57:17', '1', '2025-01-07 11:57:17', '0');
INSERT INTO system_dict_data  VALUES (1684, 0, '序列号', '1', 'eiot_dn_typ', 0, '', '', '', '1', '2025-01-07 11:53:10', '1', '2025-01-07 11:56:46', '0');
INSERT INTO system_dict_data  VALUES (1685, 2, 'GB28181', 'GB28181', 'eiot_protocol_code', 0, '', '', '', '1', '2025-01-07 11:10:14', '1', '2025-01-07 11:10:14', '0');
INSERT INTO system_dict_data  VALUES (1686, 0, '无', 'N', 'eiot_mcu_code', 0, '', '', '', '1', '2025-01-07 11:09:49', '1', '2025-01-07 11:09:49', '0');
INSERT INTO system_dict_data  VALUES (1687, 9, '非联网设备', '9', 'eiot_node_type', 0, '', '', '', '1', '2025-01-03 14:53:51', '1', '2025-01-03 14:53:51', '0');
INSERT INTO system_dict_data  VALUES (1688, 1, '报警控制器', '117', 'channel_type', 0, '', '', '', '1', '2025-01-03 11:46:27', '1', '2025-01-03 11:46:27', '0');
INSERT INTO system_dict_data  VALUES (1689, 0, 'IPC', '132', 'channel_type', 0, '', '', '', '1', '2025-01-03 11:46:12', '1', '2025-01-03 11:46:12', '0');
INSERT INTO system_dict_data  VALUES (1690, 5, 'IPC', '132', 'video_type', 0, '', '', '', '1', '2025-01-03 11:31:35', '1', '2025-01-03 11:31:35', '0');
INSERT INTO system_dict_data  VALUES (1691, 4, '摄像机', '131', 'video_type', 0, '', '', '', '1', '2025-01-03 11:31:20', '1', '2025-01-03 11:31:20', '0');
INSERT INTO system_dict_data  VALUES (1692, 4, '报警控制器', '117', 'video_type', 0, '', '', '', '1', '2025-01-03 11:30:26', '1', '2025-01-03 11:30:26', '0');
INSERT INTO system_dict_data  VALUES (1693, 1, 'NVR', '118', 'video_type', 0, '', '', '', '1', '2025-01-03 11:30:07', '1', '2025-01-03 11:30:42', '0');
INSERT INTO system_dict_data  VALUES (1694, 0, 'DVR', '111', 'video_type', 0, '', '', '', '1', '2025-01-03 11:29:57', '1', '2025-01-03 11:30:34', '0');
INSERT INTO system_dict_data  VALUES (1695, 2, '在线', '2', 'sip_gen_status', 0, '', '', '', '1', '2025-01-03 11:22:46', '1', '2025-01-03 11:29:12', '0');
INSERT INTO system_dict_data  VALUES (1696, 1, '未使用', '1', 'sip_gen_status', 0, '', '', '', '1', '2025-01-03 11:22:34', '1', '2025-01-03 11:28:41', '0');
INSERT INTO system_dict_data  VALUES (1697, 1, '是', '1', 'common_boolean_int', 0, '', '', '', '1', '2025-02-10 14:33:26', '1', '2025-02-10 14:33:32', '0');
INSERT INTO system_dict_data  VALUES (1698, 2, '否', '0', 'common_boolean_int', 0, '', '', '', '1', '2025-02-10 14:33:38', '1', '2025-02-10 14:33:38', '0');
INSERT INTO system_dict_data  VALUES (1699, 0, '无', 'N', 'eiot_protocol_code', 0, '', '', '', '1', '2025-02-16 18:14:37', '1', '2025-02-16 18:14:37', '0');
INSERT INTO system_dict_data  VALUES (1706, 3, '监控设备', '3', 'eiot_node_type', 0, '', '', '', '1', '2024-12-05 12:14:39', '1', '2025-01-03 14:53:31', '0');
INSERT INTO system_dict_data  VALUES (1707, 2, '直连设备', '2', 'eiot_node_type', 0, '', '', '', '1', '2024-12-05 12:13:08', '1', '2024-12-05 12:14:18', '0');
INSERT INTO system_dict_data  VALUES (1708, 1, '网关子设备', '1', 'eiot_node_type', 0, '', '', '', '1', '2024-12-05 12:12:52', '1', '2024-12-05 12:14:06', '0');
INSERT INTO system_dict_data  VALUES (1709, 0, '网关', '0', 'eiot_node_type', 0, '', '', '', '1', '2024-12-05 12:12:34', '1', '2024-12-05 12:13:56', '0');
INSERT INTO system_dict_data  VALUES (1710, 1, 'Modbus Tcp', 'modbus-tcp', 'eiot_protocol_code', 0, '', '', '', '1', '2025-03-21 00:23:29', '1', '2025-03-21 00:23:41', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_dict_data_seq;
CREATE SEQUENCE system_dict_data_seq
    START 1711;

-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS system_dict_type;
CREATE TABLE system_dict_type (
    id int8 NOT NULL,
  name varchar(100) NOT NULL DEFAULT '',
  type varchar(100) NOT NULL DEFAULT '',
  status int2 NOT NULL DEFAULT 0,
  remark varchar(500) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  deleted_time timestamp NULL DEFAULT NULL
);

ALTER TABLE system_dict_type ADD CONSTRAINT pk_system_dict_type PRIMARY KEY (id);

COMMENT ON COLUMN system_dict_type.id IS '字典主键';
COMMENT ON COLUMN system_dict_type.name IS '字典名称';
COMMENT ON COLUMN system_dict_type.type IS '字典类型';
COMMENT ON COLUMN system_dict_type.status IS '状态（0正常 1停用）';
COMMENT ON COLUMN system_dict_type.remark IS '备注';
COMMENT ON COLUMN system_dict_type.creator IS '创建者';
COMMENT ON COLUMN system_dict_type.create_time IS '创建时间';
COMMENT ON COLUMN system_dict_type.updater IS '更新者';
COMMENT ON COLUMN system_dict_type.update_time IS '更新时间';
COMMENT ON COLUMN system_dict_type.deleted IS '是否删除';
COMMENT ON COLUMN system_dict_type.deleted_time IS '删除时间';
COMMENT ON TABLE system_dict_type IS '字典类型表';

-- ----------------------------
-- Records of system_dict_type
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_dict_type  VALUES (1, '用户性别', 'system_user_sex', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2022-05-16 20:29:32', '0', NULL);
INSERT INTO system_dict_type  VALUES (6, '参数类型', 'infra_config_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:36:54', '0', NULL);
INSERT INTO system_dict_type  VALUES (7, '通知类型', 'system_notice_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:35:26', '0', NULL);
INSERT INTO system_dict_type  VALUES (9, '操作类型', 'infra_operate_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2024-03-14 12:44:01', '0', NULL);
INSERT INTO system_dict_type  VALUES (10, '系统状态', 'common_status', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:21:28', '0', NULL);
INSERT INTO system_dict_type  VALUES (11, 'Boolean 是否类型', 'infra_boolean_string', 0, 'boolean 转是否', '', '2021-01-19 03:20:08', '', '2022-02-01 16:37:10', '0', NULL);
INSERT INTO system_dict_type  VALUES (104, '登陆结果', 'system_login_result', 0, '登陆结果', '', '2021-01-18 06:17:11', '', '2022-02-01 16:36:00', '0', NULL);
INSERT INTO system_dict_type  VALUES (106, '代码生成模板类型', 'infra_codegen_template_type', 0, NULL, '', '2021-02-05 07:08:06', '1', '2022-05-16 20:26:50', '0', NULL);
INSERT INTO system_dict_type  VALUES (107, '定时任务状态', 'infra_job_status', 0, NULL, '', '2021-02-07 07:44:16', '', '2022-02-01 16:51:11', '0', NULL);
INSERT INTO system_dict_type  VALUES (108, '定时任务日志状态', 'infra_job_log_status', 0, NULL, '', '2021-02-08 10:03:51', '', '2022-02-01 16:50:43', '0', NULL);
INSERT INTO system_dict_type  VALUES (109, '用户类型', 'user_type', 0, NULL, '', '2021-02-26 00:15:51', '', '2021-02-26 00:15:51', '0', NULL);
INSERT INTO system_dict_type  VALUES (110, 'API 异常数据的处理状态', 'infra_api_error_log_process_status', 0, NULL, '', '2021-02-26 07:07:01', '', '2022-02-01 16:50:53', '0', NULL);
INSERT INTO system_dict_type  VALUES (111, '短信渠道编码', 'system_sms_channel_code', 0, NULL, '1', '2021-04-05 01:04:50', '1', '2022-02-16 02:09:08', '0', NULL);
INSERT INTO system_dict_type  VALUES (112, '短信模板的类型', 'system_sms_template_type', 0, NULL, '1', '2021-04-05 21:50:43', '1', '2022-02-01 16:35:06', '0', NULL);
INSERT INTO system_dict_type  VALUES (113, '短信发送状态', 'system_sms_send_status', 0, NULL, '1', '2021-04-11 20:18:03', '1', '2022-02-01 16:35:09', '0', NULL);
INSERT INTO system_dict_type  VALUES (114, '短信接收状态', 'system_sms_receive_status', 0, NULL, '1', '2021-04-11 20:27:14', '1', '2022-02-01 16:35:14', '0', NULL);
INSERT INTO system_dict_type  VALUES (116, '登陆日志的类型', 'system_login_type', 0, '登陆日志的类型', '1', '2021-10-06 00:50:46', '1', '2022-02-01 16:35:56', '0', NULL);
INSERT INTO system_dict_type  VALUES (117, 'OA 请假类型', 'bpm_oa_leave_type', 0, NULL, '1', '2021-09-21 22:34:33', '1', '2022-01-22 10:41:37', '0', NULL);
INSERT INTO system_dict_type  VALUES (130, '支付渠道编码类型', 'pay_channel_code', 0, '支付渠道的编码', '1', '2021-12-03 10:35:08', '1', '2023-07-10 10:11:39', '0', NULL);
INSERT INTO system_dict_type  VALUES (131, '支付回调状态', 'pay_notify_status', 0, '支付回调状态（包括退款回调）', '1', '2021-12-03 10:53:29', '1', '2023-07-19 18:09:43', '0', NULL);
INSERT INTO system_dict_type  VALUES (132, '支付订单状态', 'pay_order_status', 0, '支付订单状态', '1', '2021-12-03 11:17:50', '1', '2021-12-03 11:17:50', '0', NULL);
INSERT INTO system_dict_type  VALUES (134, '退款订单状态', 'pay_refund_status', 0, '退款订单状态', '1', '2021-12-10 16:42:50', '1', '2023-07-19 10:13:17', '0', NULL);
INSERT INTO system_dict_type  VALUES (139, '流程实例的状态', 'bpm_process_instance_status', 0, '流程实例的状态', '1', '2022-01-07 23:46:42', '1', '2022-01-07 23:46:42', '0', NULL);
INSERT INTO system_dict_type  VALUES (140, '流程实例的结果', 'bpm_task_status', 0, '流程实例的结果', '1', '2022-01-07 23:48:10', '1', '2024-03-08 22:42:03', '0', NULL);
INSERT INTO system_dict_type  VALUES (141, '流程的表单类型', 'bpm_model_form_type', 0, '流程的表单类型', '103', '2022-01-11 23:50:45', '103', '2022-01-11 23:50:45', '0', NULL);
INSERT INTO system_dict_type  VALUES (142, '任务分配规则的类型', 'bpm_task_candidate_strategy', 0, 'BPM 任务的候选人的策略', '103', '2022-01-12 23:21:04', '103', '2024-03-06 02:53:59', '0', NULL);
INSERT INTO system_dict_type  VALUES (144, '代码生成的场景枚举', 'infra_codegen_scene', 0, '代码生成的场景枚举', '1', '2022-02-02 13:14:45', '1', '2022-03-10 16:33:46', '0', NULL);
INSERT INTO system_dict_type  VALUES (145, '角色类型', 'system_role_type', 0, '角色类型', '1', '2022-02-16 13:01:46', '1', '2022-02-16 13:01:46', '0', NULL);
INSERT INTO system_dict_type  VALUES (146, '文件存储器', 'infra_file_storage', 0, '文件存储器', '1', '2022-03-15 00:24:38', '1', '2022-03-15 00:24:38', '0', NULL);
INSERT INTO system_dict_type  VALUES (147, 'OAuth 2.0 授权类型', 'system_oauth2_grant_type', 0, 'OAuth 2.0 授权类型（模式）', '1', '2022-05-12 00:20:52', '1', '2022-05-11 16:25:49', '0', NULL);
INSERT INTO system_dict_type  VALUES (149, '商品 SPU 状态', 'product_spu_status', 0, '商品 SPU 状态', '1', '2022-10-24 21:19:04', '1', '2022-10-24 21:19:08', '0', NULL);
INSERT INTO system_dict_type  VALUES (150, '优惠类型', 'promotion_discount_type', 0, '优惠类型', '1', '2022-11-01 12:46:06', '1', '2022-11-01 12:46:06', '0', NULL);
INSERT INTO system_dict_type  VALUES (151, '优惠劵模板的有限期类型', 'promotion_coupon_template_validity_type', 0, '优惠劵模板的有限期类型', '1', '2022-11-02 00:06:20', '1', '2022-11-04 00:08:26', '0', NULL);
INSERT INTO system_dict_type  VALUES (152, '营销的商品范围', 'promotion_product_scope', 0, '营销的商品范围', '1', '2022-11-02 00:28:01', '1', '2022-11-02 00:28:01', '0', NULL);
INSERT INTO system_dict_type  VALUES (153, '优惠劵的状态', 'promotion_coupon_status', 0, '优惠劵的状态', '1', '2022-11-04 00:14:49', '1', '2022-11-04 00:14:49', '0', NULL);
INSERT INTO system_dict_type  VALUES (154, '优惠劵的领取方式', 'promotion_coupon_take_type', 0, '优惠劵的领取方式', '1', '2022-11-04 19:12:27', '1', '2022-11-04 19:12:27', '0', NULL);
INSERT INTO system_dict_type  VALUES (155, '促销活动的状态', 'promotion_activity_status', 0, '促销活动的状态', '1', '2022-11-04 22:54:23', '1', '2022-11-04 22:54:23', '0', NULL);
INSERT INTO system_dict_type  VALUES (156, '营销的条件类型', 'promotion_condition_type', 0, '营销的条件类型', '1', '2022-11-04 22:59:23', '1', '2022-11-04 22:59:23', '0', NULL);
INSERT INTO system_dict_type  VALUES (157, '交易售后状态', 'trade_after_sale_status', 0, '交易售后状态', '1', '2022-11-19 20:52:56', '1', '2022-11-19 20:52:56', '0', NULL);
INSERT INTO system_dict_type  VALUES (158, '交易售后的类型', 'trade_after_sale_type', 0, '交易售后的类型', '1', '2022-11-19 21:04:09', '1', '2022-11-19 21:04:09', '0', NULL);
INSERT INTO system_dict_type  VALUES (159, '交易售后的方式', 'trade_after_sale_way', 0, '交易售后的方式', '1', '2022-11-19 21:39:04', '1', '2022-11-19 21:39:04', '0', NULL);
INSERT INTO system_dict_type  VALUES (160, '终端', 'terminal', 0, '终端', '1', '2022-12-10 10:50:50', '1', '2022-12-10 10:53:11', '0', NULL);
INSERT INTO system_dict_type  VALUES (161, '交易订单的类型', 'trade_order_type', 0, '交易订单的类型', '1', '2022-12-10 16:33:54', '1', '2022-12-10 16:33:54', '0', NULL);
INSERT INTO system_dict_type  VALUES (162, '交易订单的状态', 'trade_order_status', 0, '交易订单的状态', '1', '2022-12-10 16:48:44', '1', '2022-12-10 16:48:44', '0', NULL);
INSERT INTO system_dict_type  VALUES (163, '交易订单项的售后状态', 'trade_order_item_after_sale_status', 0, '交易订单项的售后状态', '1', '2022-12-10 20:58:08', '1', '2022-12-10 20:58:08', '0', NULL);
INSERT INTO system_dict_type  VALUES (164, '公众号自动回复的请求关键字匹配模式', 'mp_auto_reply_request_match', 0, '公众号自动回复的请求关键字匹配模式', '1', '2023-01-16 23:29:56', '1', '2023-01-16 23:29:56', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (165, '公众号的消息类型', 'mp_message_type', 0, '公众号的消息类型', '1', '2023-01-17 22:17:09', '1', '2023-01-17 22:17:09', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (166, '邮件发送状态', 'system_mail_send_status', 0, '邮件发送状态', '1', '2023-01-26 09:53:13', '1', '2023-01-26 09:53:13', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (167, '站内信模版的类型', 'system_notify_template_type', 0, '站内信模版的类型', '1', '2023-01-28 10:35:10', '1', '2023-01-28 10:35:10', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (168, '代码生成的前端类型', 'infra_codegen_front_type', 0, '', '1', '2023-04-12 23:57:52', '1', '2023-04-12 23:57:52', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (170, '快递计费方式', 'trade_delivery_express_charge_mode', 0, '用于商城交易模块配送管理', '1', '2023-05-21 22:45:03', '1', '2023-05-21 22:45:03', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (171, '积分业务类型', 'member_point_biz_type', 0, '', '1', '2023-06-10 12:15:00', '1', '2023-06-28 13:48:20', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (173, '支付通知类型', 'pay_notify_type', 0, NULL, '1', '2023-07-20 12:23:03', '1', '2023-07-20 12:23:03', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (174, '会员经验业务类型', 'member_experience_biz_type', 0, NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', '0', NULL);
INSERT INTO system_dict_type  VALUES (175, '交易配送类型', 'trade_delivery_type', 0, '', '1', '2023-08-23 00:03:14', '1', '2023-08-23 00:03:14', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (176, '分佣模式', 'brokerage_enabled_condition', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0', NULL);
INSERT INTO system_dict_type  VALUES (177, '分销关系绑定模式', 'brokerage_bind_mode', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0', NULL);
INSERT INTO system_dict_type  VALUES (178, '佣金提现类型', 'brokerage_withdraw_type', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0', NULL);
INSERT INTO system_dict_type  VALUES (179, '佣金记录业务类型', 'brokerage_record_biz_type', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0', NULL);
INSERT INTO system_dict_type  VALUES (180, '佣金记录状态', 'brokerage_record_status', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0', NULL);
INSERT INTO system_dict_type  VALUES (181, '佣金提现状态', 'brokerage_withdraw_status', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0', NULL);
INSERT INTO system_dict_type  VALUES (182, '佣金提现银行', 'brokerage_bank_name', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', '0', NULL);
INSERT INTO system_dict_type  VALUES (183, '砍价记录的状态', 'promotion_bargain_record_status', 0, '', '1', '2023-10-05 10:41:08', '1', '2023-10-05 10:41:08', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (184, '拼团记录的状态', 'promotion_combination_record_status', 0, '', '1', '2023-10-08 07:24:25', '1', '2023-10-08 07:24:25', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (185, '回款-回款方式', 'crm_receivable_return_type', 0, '回款-回款方式', '1', '2023-10-18 21:54:10', '1', '2023-10-18 21:54:10', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (186, 'CRM 客户行业', 'crm_customer_industry', 0, 'CRM 客户所属行业', '1', '2023-10-28 22:57:07', '1', '2024-02-18 23:30:22', '0', NULL);
INSERT INTO system_dict_type  VALUES (187, '客户等级', 'crm_customer_level', 0, 'CRM 客户等级', '1', '2023-10-28 22:59:12', '1', '2023-10-28 15:11:16', '0', NULL);
INSERT INTO system_dict_type  VALUES (188, '客户来源', 'crm_customer_source', 0, 'CRM 客户来源', '1', '2023-10-28 23:00:34', '1', '2023-10-28 15:11:16', '0', NULL);
INSERT INTO system_dict_type  VALUES (600, 'Banner 位置', 'promotion_banner_position', 0, '', '1', '2023-10-08 07:24:25', '1', '2023-11-04 13:04:02', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (601, '社交类型', 'system_social_type', 0, '', '1', '2023-11-04 13:03:54', '1', '2023-11-04 13:03:54', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (604, '产品状态', 'crm_product_status', 0, '', '1', '2023-10-30 21:47:59', '1', '2023-10-30 21:48:45', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (605, 'CRM 数据权限的级别', 'crm_permission_level', 0, '', '1', '2023-11-30 09:51:59', '1', '2023-11-30 09:51:59', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (606, 'CRM 审批状态', 'crm_audit_status', 0, '', '1', '2023-11-30 18:56:23', '1', '2023-11-30 18:56:23', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (607, 'CRM 产品单位', 'crm_product_unit', 0, '', '1', '2023-12-05 23:01:51', '1', '2023-12-05 23:01:51', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (608, 'CRM 跟进方式', 'crm_follow_up_type', 0, '', '1', '2024-01-15 20:48:05', '1', '2024-01-15 20:48:05', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (609, '支付转账类型', 'pay_transfer_type', 0, '', '1', '2023-10-28 16:27:18', '1', '2023-10-28 16:27:18', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (610, '转账订单状态', 'pay_transfer_status', 0, '', '1', '2023-10-28 16:18:32', '1', '2023-10-28 16:18:32', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (611, 'ERP 库存明细的业务类型', 'erp_stock_record_biz_type', 0, 'ERP 库存明细的业务类型', '1', '2024-02-05 18:07:02', '1', '2024-02-05 18:07:02', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (612, 'ERP 审批状态', 'erp_audit_status', 0, '', '1', '2024-02-06 00:00:07', '1', '2024-02-06 00:00:07', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (613, 'BPM 监听器类型', 'bpm_process_listener_type', 0, '', '1', '2024-03-23 12:52:24', '1', '2024-03-09 15:54:28', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (615, 'BPM 监听器值类型', 'bpm_process_listener_value_type', 0, '', '1', '2024-03-23 13:00:31', '1', '2024-03-23 13:00:31', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (616, '时间间隔', 'date_interval', 0, '', '1', '2024-03-29 22:50:09', '1', '2024-03-29 22:50:09', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (619, 'CRM 商机结束状态类型', 'crm_business_end_status_type', 0, '', '1', '2024-04-13 23:23:00', '1', '2024-04-13 23:23:00', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (620, 'AI 模型平台', 'ai_platform', 0, '', '1', '2024-05-09 22:27:38', '1', '2024-05-09 22:27:38', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (621, 'AI 绘画状态', 'ai_image_status', 0, '', '1', '2024-06-26 20:51:23', '1', '2024-06-26 20:51:23', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (622, 'AI 音乐状态', 'ai_music_status', 0, '', '1', '2024-06-27 22:45:07', '1', '2024-06-28 00:56:27', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (623, 'AI 音乐生成模式', 'ai_generate_mode', 0, '', '1', '2024-06-27 22:46:21', '1', '2024-06-28 01:22:29', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (624, '写作语气', 'ai_write_tone', 0, '', '1', '2024-07-07 15:19:02', '1', '2024-07-07 15:19:02', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (625, '写作语言', 'ai_write_language', 0, '', '1', '2024-07-07 15:18:52', '1', '2024-07-07 15:18:52', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (626, '写作长度', 'ai_write_length', 0, '', '1', '2024-07-07 15:18:41', '1', '2024-07-07 15:18:41', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (627, '写作格式', 'ai_write_format', 0, '', '1', '2024-07-07 15:14:34', '1', '2024-07-07 15:14:34', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (628, 'AI 写作类型', 'ai_write_type', 0, '', '1', '2024-07-10 21:25:29', '1', '2024-07-10 21:25:29', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (629, 'BPM 流程模型类型', 'bpm_model_type', 0, '', '1', '2024-08-26 15:21:43', '1', '2024-08-26 15:21:43', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (630, 'IOT 接入网关协议', 'iot_protocol_type', 0, '', '1', '2024-09-06 22:20:17', '1', '2024-09-06 22:20:17', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (631, 'IOT 设备状态', 'iot_device_status', 0, '', '1', '2024-09-21 08:12:55', '1', '2024-09-21 08:12:55', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (632, 'IOT 物模型功能类型', 'iot_product_function_type', 0, '', '1', '2024-09-29 20:02:36', '1', '2024-09-29 20:09:26', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (634, 'IOT 数据格式', 'iot_data_format', 0, '', '1', '2024-08-10 11:52:58', '1', '2024-09-06 14:30:14', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (635, 'IOT 产品设备类型', 'iot_product_device_type', 0, '', '1', '2024-08-10 11:54:30', '1', '2024-08-10 04:06:56', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (637, 'IOT 产品状态', 'iot_product_status', 0, '', '1', '2024-08-10 12:06:09', '1', '2024-08-10 12:06:09', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (638, 'IOT 数据校验级别', 'iot_validate_type', 0, '', '1', '2024-09-06 20:05:13', '1', '2024-09-06 20:05:13', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (639, 'IOT 联网方式', 'iot_net_type', 0, '', '1', '2024-09-06 22:04:13', '1', '2024-09-06 22:04:13', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (640, '是否int', 'common_boolen_int', 0, '', '1', '2025-02-10 11:08:01', '1', '2025-02-10 11:10:02', '1', '2025-02-10 11:10:03');
INSERT INTO system_dict_type  VALUES (641, '是否ini', 'common_boolen_int', 0, '', '1', '2025-02-10 11:10:19', '1', '2025-02-10 11:10:26', '1', '2025-02-10 11:10:26');
INSERT INTO system_dict_type  VALUES (642, '是否int', 'common_boolean_int', 0, '', '1', '2025-02-10 11:10:35', '1', '2025-02-10 14:33:12', '1', '2025-02-10 14:33:13');
INSERT INTO system_dict_type  VALUES (643, 'SipID状态', 'sip_gen_status', 0, '1=未使用，2=使用中', '1', '2025-01-03 11:22:07', '1', '2025-01-03 11:28:18', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (644, '芯片编码', 'eiot_mcu_code', 0, '', '1', '2024-12-25 11:00:30', '1', '2024-12-25 11:07:55', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (645, 'eiot显示模型类型', 'eiot_show_model_typ', 0, '', '1', '2024-12-11 12:04:02', '1', '2024-12-11 12:04:02', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (646, 'eiot设备状态', 'eiot_device_status', 0, '', '1', '2024-12-06 16:01:00', '1', '2024-12-06 16:01:00', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (647, '数字布尔', 'common_boolean_int', 0, '', '1', '2024-12-05 13:49:40', '1', '2024-12-05 13:49:40', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (648, 'eiot协议code', 'eiot_protocol_code', 0, '', '1', '2024-12-05 12:15:21', '1', '2024-12-05 12:15:21', '0', '1970-01-01 00:00:00');
INSERT INTO system_dict_type  VALUES (649, 'eiot设备节点类型', 'eiot_node_type', 0, '', '1', '2024-12-05 12:11:56', '1', '2025-02-16 18:39:51', '0', '1970-01-01 00:00:00');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_dict_type_seq;
CREATE SEQUENCE system_dict_type_seq
    START 650;

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS system_login_log;
CREATE TABLE system_login_log (
    id int8 NOT NULL,
  log_type int8 NOT NULL,
  trace_id varchar(64) NOT NULL DEFAULT '',
  user_id int8 NOT NULL DEFAULT 0,
  user_type int2 NOT NULL DEFAULT 0,
  username varchar(50) NOT NULL DEFAULT '',
  result int2 NOT NULL,
  user_ip varchar(50) NOT NULL,
  user_agent varchar(512) NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_login_log ADD CONSTRAINT pk_system_login_log PRIMARY KEY (id);

COMMENT ON COLUMN system_login_log.id IS '访问ID';
COMMENT ON COLUMN system_login_log.log_type IS '日志类型';
COMMENT ON COLUMN system_login_log.trace_id IS '链路追踪编号';
COMMENT ON COLUMN system_login_log.user_id IS '用户编号';
COMMENT ON COLUMN system_login_log.user_type IS '用户类型';
COMMENT ON COLUMN system_login_log.username IS '用户账号';
COMMENT ON COLUMN system_login_log.result IS '登陆结果';
COMMENT ON COLUMN system_login_log.user_ip IS '用户 IP';
COMMENT ON COLUMN system_login_log.user_agent IS '浏览器 UA';
COMMENT ON COLUMN system_login_log.creator IS '创建者';
COMMENT ON COLUMN system_login_log.create_time IS '创建时间';
COMMENT ON COLUMN system_login_log.updater IS '更新者';
COMMENT ON COLUMN system_login_log.update_time IS '更新时间';
COMMENT ON COLUMN system_login_log.deleted IS '是否删除';
COMMENT ON COLUMN system_login_log.tenant_id IS '租户编号';
COMMENT ON TABLE system_login_log IS '系统访问记录';

-- ----------------------------
-- Records of system_login_log
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_login_log  VALUES (3426, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 06:35:01', NULL, '2025-02-23 06:35:01', '0', 1);
INSERT INTO system_login_log  VALUES (3427, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 19:59:22', NULL, '2025-02-23 19:59:22', '0', 1);
INSERT INTO system_login_log  VALUES (3428, 100, '', 1, 2, 'admin', 0, '222.247.113.4', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:00:21', NULL, '2025-02-23 20:00:21', '0', 1);
INSERT INTO system_login_log  VALUES (3429, 200, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '1', '2025-02-23 20:11:40', '1', '2025-02-23 20:11:40', '0', 1);
INSERT INTO system_login_log  VALUES (3430, 100, '', 104, 2, 'test', 10, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:11:48', NULL, '2025-02-23 20:11:48', '0', 1);
INSERT INTO system_login_log  VALUES (3431, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:12:00', NULL, '2025-02-23 20:12:00', '0', 1);
INSERT INTO system_login_log  VALUES (3432, 200, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '1', '2025-02-23 20:12:43', '1', '2025-02-23 20:12:43', '0', 1);
INSERT INTO system_login_log  VALUES (3433, 100, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:12:50', NULL, '2025-02-23 20:12:50', '0', 1);
INSERT INTO system_login_log  VALUES (3434, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 NetType/WIFI MicroMessenger/7.0.20.1781(0x6700143B) WindowsWechat(0x63090c2d) XWEB/13307 Flue', NULL, '2025-02-23 20:17:49', NULL, '2025-02-23 20:17:49', '0', 1);
INSERT INTO system_login_log  VALUES (3435, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:25:07', NULL, '2025-02-23 20:25:07', '0', 1);
INSERT INTO system_login_log  VALUES (3436, 200, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '1', '2025-02-23 21:17:37', '1', '2025-02-23 21:17:37', '0', 1);
INSERT INTO system_login_log  VALUES (3437, 100, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 21:18:02', NULL, '2025-02-23 21:18:02', '0', 1);
INSERT INTO system_login_log  VALUES (3438, 200, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '104', '2025-02-23 21:21:04', '104', '2025-02-23 21:21:04', '0', 1);
INSERT INTO system_login_log  VALUES (3439, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 21:21:13', NULL, '2025-02-23 21:21:13', '0', 1);
INSERT INTO system_login_log  VALUES (3440, 200, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '1', '2025-02-23 21:25:26', '1', '2025-02-23 21:25:26', '0', 1);
INSERT INTO system_login_log  VALUES (3441, 100, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 21:25:34', NULL, '2025-02-23 21:25:34', '0', 1);
INSERT INTO system_login_log  VALUES (3442, 200, '', 104, 2, 'test', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', '104', '2025-02-23 21:26:40', '104', '2025-02-23 21:26:40', '0', 1);
INSERT INTO system_login_log  VALUES (3443, 100, '', 1, 2, 'admin', 0, '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 21:26:47', NULL, '2025-02-23 21:26:47', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_login_log_seq;
CREATE SEQUENCE system_login_log_seq
    START 3444;

-- ----------------------------
-- Table structure for system_mail_account
-- ----------------------------
DROP TABLE IF EXISTS system_mail_account;
CREATE TABLE system_mail_account (
    id int8 NOT NULL,
  mail varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  host varchar(255) NOT NULL,
  port int4 NOT NULL,
  ssl_enable bool NOT NULL DEFAULT '0',
  starttls_enable bool NOT NULL DEFAULT '0',
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_mail_account ADD CONSTRAINT pk_system_mail_account PRIMARY KEY (id);

COMMENT ON COLUMN system_mail_account.id IS '主键';
COMMENT ON COLUMN system_mail_account.mail IS '邮箱';
COMMENT ON COLUMN system_mail_account.username IS '用户名';
COMMENT ON COLUMN system_mail_account.password IS '密码';
COMMENT ON COLUMN system_mail_account.host IS 'SMTP 服务器域名';
COMMENT ON COLUMN system_mail_account.port IS 'SMTP 服务器端口';
COMMENT ON COLUMN system_mail_account.ssl_enable IS '是否开启 SSL';
COMMENT ON COLUMN system_mail_account.starttls_enable IS '是否开启 STARTTLS';
COMMENT ON COLUMN system_mail_account.creator IS '创建者';
COMMENT ON COLUMN system_mail_account.create_time IS '创建时间';
COMMENT ON COLUMN system_mail_account.updater IS '更新者';
COMMENT ON COLUMN system_mail_account.update_time IS '更新时间';
COMMENT ON COLUMN system_mail_account.deleted IS '是否删除';
COMMENT ON TABLE system_mail_account IS '邮箱账号表';

-- ----------------------------
-- Records of system_mail_account
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_mail_account  VALUES (1, '7684413@qq.com', '7684413@qq.com', '1234576', '127.0.0.1', 8080, '0', '0', '1', '2023-01-25 17:39:52', '1', '2024-07-27 22:39:12', '0');
INSERT INTO system_mail_account  VALUES (2, 'ydym_test@163.com', 'ydym_test@163.com', 'WBZTEINMIFVRYSOE', 'smtp.163.com', 465, '1', '0', '1', '2023-01-26 01:26:03', '1', '2023-04-12 22:39:38', '0');
INSERT INTO system_mail_account  VALUES (3, '76854114@qq.com', '3335', '11234', 'enjoy-iot.cn', 466, '0', '0', '1', '2023-01-27 15:06:38', '1', '2023-01-27 07:08:36', '1');
INSERT INTO system_mail_account  VALUES (4, '7685413x@qq.com', '2', '3', '4', 5, '1', '0', '1', '2023-04-12 23:05:06', '1', '2023-04-12 15:05:11', '1');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_mail_account_seq;
CREATE SEQUENCE system_mail_account_seq
    START 5;

-- ----------------------------
-- Table structure for system_mail_log
-- ----------------------------
DROP TABLE IF EXISTS system_mail_log;
CREATE TABLE system_mail_log (
    id int8 NOT NULL,
  user_id int8 NULL DEFAULT NULL,
  user_type int2 NULL DEFAULT NULL,
  to_mail varchar(255) NOT NULL,
  account_id int8 NOT NULL,
  from_mail varchar(255) NOT NULL,
  template_id int8 NOT NULL,
  template_code varchar(63) NOT NULL,
  template_nickname varchar(255) NULL DEFAULT NULL,
  template_title varchar(255) NOT NULL,
  template_content varchar(10240) NOT NULL,
  template_params varchar(255) NOT NULL,
  send_status int2 NOT NULL DEFAULT 0,
  send_time timestamp NULL DEFAULT NULL,
  send_message_id varchar(255) NULL DEFAULT NULL,
  send_exception varchar(4096) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_mail_log ADD CONSTRAINT pk_system_mail_log PRIMARY KEY (id);

COMMENT ON COLUMN system_mail_log.id IS '编号';
COMMENT ON COLUMN system_mail_log.user_id IS '用户编号';
COMMENT ON COLUMN system_mail_log.user_type IS '用户类型';
COMMENT ON COLUMN system_mail_log.to_mail IS '接收邮箱地址';
COMMENT ON COLUMN system_mail_log.account_id IS '邮箱账号编号';
COMMENT ON COLUMN system_mail_log.from_mail IS '发送邮箱地址';
COMMENT ON COLUMN system_mail_log.template_id IS '模板编号';
COMMENT ON COLUMN system_mail_log.template_code IS '模板编码';
COMMENT ON COLUMN system_mail_log.template_nickname IS '模版发送人名称';
COMMENT ON COLUMN system_mail_log.template_title IS '邮件标题';
COMMENT ON COLUMN system_mail_log.template_content IS '邮件内容';
COMMENT ON COLUMN system_mail_log.template_params IS '邮件参数';
COMMENT ON COLUMN system_mail_log.send_status IS '发送状态';
COMMENT ON COLUMN system_mail_log.send_time IS '发送时间';
COMMENT ON COLUMN system_mail_log.send_message_id IS '发送返回的消息 ID';
COMMENT ON COLUMN system_mail_log.send_exception IS '发送异常';
COMMENT ON COLUMN system_mail_log.creator IS '创建者';
COMMENT ON COLUMN system_mail_log.create_time IS '创建时间';
COMMENT ON COLUMN system_mail_log.updater IS '更新者';
COMMENT ON COLUMN system_mail_log.update_time IS '更新时间';
COMMENT ON COLUMN system_mail_log.deleted IS '是否删除';
COMMENT ON TABLE system_mail_log IS '邮件日志表';

DROP SEQUENCE IF EXISTS system_mail_log_seq;
CREATE SEQUENCE system_mail_log_seq
    START 1;

-- ----------------------------
-- Table structure for system_mail_template
-- ----------------------------
DROP TABLE IF EXISTS system_mail_template;
CREATE TABLE system_mail_template (
    id int8 NOT NULL,
  name varchar(63) NOT NULL,
  code varchar(63) NOT NULL,
  account_id int8 NOT NULL,
  nickname varchar(255) NULL DEFAULT NULL,
  title varchar(255) NOT NULL,
  content varchar(10240) NOT NULL,
  params varchar(255) NOT NULL,
  status int2 NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_mail_template ADD CONSTRAINT pk_system_mail_template PRIMARY KEY (id);

COMMENT ON COLUMN system_mail_template.id IS '编号';
COMMENT ON COLUMN system_mail_template.name IS '模板名称';
COMMENT ON COLUMN system_mail_template.code IS '模板编码';
COMMENT ON COLUMN system_mail_template.account_id IS '发送的邮箱账号编号';
COMMENT ON COLUMN system_mail_template.nickname IS '发送人名称';
COMMENT ON COLUMN system_mail_template.title IS '模板标题';
COMMENT ON COLUMN system_mail_template.content IS '模板内容';
COMMENT ON COLUMN system_mail_template.params IS '参数数组';
COMMENT ON COLUMN system_mail_template.status IS '开启状态';
COMMENT ON COLUMN system_mail_template.remark IS '备注';
COMMENT ON COLUMN system_mail_template.creator IS '创建者';
COMMENT ON COLUMN system_mail_template.create_time IS '创建时间';
COMMENT ON COLUMN system_mail_template.updater IS '更新者';
COMMENT ON COLUMN system_mail_template.update_time IS '更新时间';
COMMENT ON COLUMN system_mail_template.deleted IS '是否删除';
COMMENT ON TABLE system_mail_template IS '邮件模版表';

-- ----------------------------
-- Records of system_mail_template
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_mail_template  VALUES (13, '后台用户短信登录', 'admin-sms-login', 1, '奥特曼', '你猜我猜', '<p>您的验证码是{code}，名字是{name}</p>', '["code","name"]', 0, '3', '1', '2021-10-11 08:10:00', '1', '2023-12-02 19:51:14', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_mail_template_seq;
CREATE SEQUENCE system_mail_template_seq
    START 14;

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS system_menu;
CREATE TABLE system_menu (
    id int8 NOT NULL,
  name varchar(50) NOT NULL,
  permission varchar(100) NOT NULL DEFAULT '',
  type int2 NOT NULL,
  sort int4 NOT NULL DEFAULT 0,
  parent_id int8 NOT NULL DEFAULT 0,
  path varchar(200) NULL DEFAULT '',
  icon varchar(100) NULL DEFAULT '#',
  component varchar(255) NULL DEFAULT NULL,
  component_name varchar(255) NULL DEFAULT NULL,
  status int2 NOT NULL DEFAULT 0,
  visible bool NOT NULL DEFAULT '1',
  keep_alive bool NOT NULL DEFAULT '1',
  always_show bool NOT NULL DEFAULT '1',
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_menu ADD CONSTRAINT pk_system_menu PRIMARY KEY (id);

COMMENT ON COLUMN system_menu.id IS '菜单ID';
COMMENT ON COLUMN system_menu.name IS '菜单名称';
COMMENT ON COLUMN system_menu.permission IS '权限标识';
COMMENT ON COLUMN system_menu.type IS '菜单类型';
COMMENT ON COLUMN system_menu.sort IS '显示顺序';
COMMENT ON COLUMN system_menu.parent_id IS '父菜单ID';
COMMENT ON COLUMN system_menu.path IS '路由地址';
COMMENT ON COLUMN system_menu.icon IS '菜单图标';
COMMENT ON COLUMN system_menu.component IS '组件路径';
COMMENT ON COLUMN system_menu.component_name IS '组件名';
COMMENT ON COLUMN system_menu.status IS '菜单状态';
COMMENT ON COLUMN system_menu.visible IS '是否可见';
COMMENT ON COLUMN system_menu.keep_alive IS '是否缓存';
COMMENT ON COLUMN system_menu.always_show IS '是否总是显示';
COMMENT ON COLUMN system_menu.creator IS '创建者';
COMMENT ON COLUMN system_menu.create_time IS '创建时间';
COMMENT ON COLUMN system_menu.updater IS '更新者';
COMMENT ON COLUMN system_menu.update_time IS '更新时间';
COMMENT ON COLUMN system_menu.deleted IS '是否删除';
COMMENT ON TABLE system_menu IS '菜单权限表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_menu  VALUES (1, '系统管理', '', 1, 8, 0, '/system', 'ep:tools', NULL, NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2025-02-18 21:45:12', '0');
INSERT INTO system_menu  VALUES (2, '基础设施', '', 1, 9, 0, '/infra', 'ep:monitor', NULL, NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2025-02-18 21:45:19', '0');
INSERT INTO system_menu  VALUES (100, '用户管理', 'system:user:list', 2, 1, 1, 'user', 'ep:avatar', 'system/user/index', 'SystemUser', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:02:04', '0');
INSERT INTO system_menu  VALUES (101, '角色管理', '', 2, 2, 1, 'role', 'ep:user', 'system/role/index', 'SystemRole', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-05-01 18:35:29', '0');
INSERT INTO system_menu  VALUES (102, '菜单管理', '', 2, 3, 1, 'menu', 'ep:menu', 'system/menu/index', 'SystemMenu', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:03:50', '0');
INSERT INTO system_menu  VALUES (103, '部门管理', '', 2, 4, 1, 'dept', 'fa:address-card', 'system/dept/index', 'SystemDept', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:06:28', '0');
INSERT INTO system_menu  VALUES (104, '岗位管理', '', 2, 5, 1, 'post', 'fa:address-book-o', 'system/post/index', 'SystemPost', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:06:39', '0');
INSERT INTO system_menu  VALUES (105, '字典管理', '', 2, 6, 1, 'dict', 'ep:collection', 'system/dict/index', 'SystemDictType', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:07:12', '0');
INSERT INTO system_menu  VALUES (106, '配置管理', '', 2, 8, 2, 'config', 'fa:connectdevelop', 'infra/config/index', 'InfraConfig', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:02:45', '0');
INSERT INTO system_menu  VALUES (107, '通知公告', '', 2, 4, 2739, 'notice', 'ep:takeaway-box', 'system/notice/index', 'SystemNotice', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-22 23:56:17', '0');
INSERT INTO system_menu  VALUES (108, '审计日志', '', 1, 9, 1, 'log', 'ep:document-copy', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:08:30', '0');
INSERT INTO system_menu  VALUES (109, '令牌管理', '', 2, 2, 1261, 'token', 'fa:key', 'system/oauth2/token/index', 'SystemTokenClient', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:13:48', '0');
INSERT INTO system_menu  VALUES (110, '定时任务', '', 2, 7, 2, 'job', 'fa-solid:tasks', 'infra/job/index', 'InfraJob', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 08:57:36', '0');
INSERT INTO system_menu  VALUES (111, 'MySQL 监控', '', 2, 1, 2740, 'druid', 'fa-solid:box', 'infra/druid/index', 'InfraDruid', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:05:58', '0');
INSERT INTO system_menu  VALUES (112, 'Java 监控', '', 2, 3, 2740, 'admin-server', 'ep:coffee-cup', 'infra/server/index', 'InfraAdminServer', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:06:57', '0');
INSERT INTO system_menu  VALUES (113, 'Redis 监控', '', 2, 2, 2740, 'redis', 'fa:reddit-square', 'infra/redis/index', 'InfraRedis', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:06:09', '0');
INSERT INTO system_menu  VALUES (114, '表单构建', 'infra:build:list', 2, 2, 2, 'build', 'fa:wpforms', 'infra/build/index', 'InfraBuild', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 08:51:35', '0');
INSERT INTO system_menu  VALUES (115, '代码生成', 'infra:codegen:query', 2, 1, 2, 'codegen', 'ep:document-copy', 'infra/codegen/index', 'InfraCodegen', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 08:51:06', '0');
INSERT INTO system_menu  VALUES (116, 'API 接口', 'infra:swagger:list', 2, 3, 2, 'swagger', 'fa:fighter-jet', 'infra/swagger/index', 'InfraSwagger', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-04-23 00:01:24', '0');
INSERT INTO system_menu  VALUES (500, '操作日志', '', 2, 1, 108, 'operate-log', 'ep:position', 'system/operatelog/index', 'SystemOperateLog', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:09:59', '0');
INSERT INTO system_menu  VALUES (501, '登录日志', '', 2, 2, 108, 'login-log', 'ep:promotion', 'system/loginlog/index', 'SystemLoginLog', 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2024-02-29 01:10:29', '0');
INSERT INTO system_menu  VALUES (1001, '用户查询', 'system:user:query', 3, 1, 100, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1002, '用户新增', 'system:user:create', 3, 2, 100, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1003, '用户修改', 'system:user:update', 3, 3, 100, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1004, '用户删除', 'system:user:delete', 3, 4, 100, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1005, '用户导出', 'system:user:export', 3, 5, 100, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1006, '用户导入', 'system:user:import', 3, 6, 100, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1007, '重置密码', 'system:user:update-password', 3, 7, 100, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1008, '角色查询', 'system:role:query', 3, 1, 101, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1009, '角色新增', 'system:role:create', 3, 2, 101, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1010, '角色修改', 'system:role:update', 3, 3, 101, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1011, '角色删除', 'system:role:delete', 3, 4, 101, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1012, '角色导出', 'system:role:export', 3, 5, 101, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1013, '菜单查询', 'system:menu:query', 3, 1, 102, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1014, '菜单新增', 'system:menu:create', 3, 2, 102, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1015, '菜单修改', 'system:menu:update', 3, 3, 102, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1016, '菜单删除', 'system:menu:delete', 3, 4, 102, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1017, '部门查询', 'system:dept:query', 3, 1, 103, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1018, '部门新增', 'system:dept:create', 3, 2, 103, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1019, '部门修改', 'system:dept:update', 3, 3, 103, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1020, '部门删除', 'system:dept:delete', 3, 4, 103, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1021, '岗位查询', 'system:post:query', 3, 1, 104, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1022, '岗位新增', 'system:post:create', 3, 2, 104, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1023, '岗位修改', 'system:post:update', 3, 3, 104, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1024, '岗位删除', 'system:post:delete', 3, 4, 104, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1025, '岗位导出', 'system:post:export', 3, 5, 104, '', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1026, '字典查询', 'system:dict:query', 3, 1, 105, '#', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1027, '字典新增', 'system:dict:create', 3, 2, 105, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1028, '字典修改', 'system:dict:update', 3, 3, 105, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1029, '字典删除', 'system:dict:delete', 3, 4, 105, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1030, '字典导出', 'system:dict:export', 3, 5, 105, '#', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1031, '配置查询', 'infra:config:query', 3, 1, 106, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1032, '配置新增', 'infra:config:create', 3, 2, 106, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1033, '配置修改', 'infra:config:update', 3, 3, 106, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1034, '配置删除', 'infra:config:delete', 3, 4, 106, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1035, '配置导出', 'infra:config:export', 3, 5, 106, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1036, '公告查询', 'system:notice:query', 3, 1, 107, '#', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1037, '公告新增', 'system:notice:create', 3, 2, 107, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1038, '公告修改', 'system:notice:update', 3, 3, 107, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1039, '公告删除', 'system:notice:delete', 3, 4, 107, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1040, '操作查询', 'system:operate-log:query', 3, 1, 500, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1042, '日志导出', 'system:operate-log:export', 3, 2, 500, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1043, '登录查询', 'system:login-log:query', 3, 1, 501, '#', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1045, '日志导出', 'system:login-log:export', 3, 3, 501, '#', '#', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1046, '令牌列表', 'system:oauth2-token:page', 3, 1, 109, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-05-09 23:54:42', '0');
INSERT INTO system_menu  VALUES (1048, '令牌删除', 'system:oauth2-token:delete', 3, 2, 109, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-05-09 23:54:53', '0');
INSERT INTO system_menu  VALUES (1050, '任务新增', 'infra:job:create', 3, 2, 110, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1051, '任务修改', 'infra:job:update', 3, 3, 110, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1052, '任务删除', 'infra:job:delete', 3, 4, 110, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1053, '状态修改', 'infra:job:update', 3, 5, 110, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1054, '任务导出', 'infra:job:export', 3, 7, 110, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1056, '生成修改', 'infra:codegen:update', 3, 2, 115, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1057, '生成删除', 'infra:codegen:delete', 3, 3, 115, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1058, '导入代码', 'infra:codegen:create', 3, 2, 115, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1059, '预览代码', 'infra:codegen:preview', 3, 4, 115, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1060, '生成代码', 'infra:codegen:download', 3, 5, 115, '', '', '', NULL, 0, '1', '1', '1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1063, '设置角色菜单权限', 'system:permission:assign-role-menu', 3, 6, 101, '', '', '', NULL, 0, '1', '1', '1', '', '2021-01-06 17:53:44', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1064, '设置角色数据权限', 'system:permission:assign-role-data-scope', 3, 7, 101, '', '', '', NULL, 0, '1', '1', '1', '', '2021-01-06 17:56:31', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1065, '设置用户角色', 'system:permission:assign-user-role', 3, 8, 101, '', '', '', NULL, 0, '1', '1', '1', '', '2021-01-07 10:23:28', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1066, '获得 Redis 监控信息', 'infra:redis:get-monitor-info', 3, 1, 113, '', '', '', NULL, 0, '1', '1', '1', '', '2021-01-26 01:02:31', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1067, '获得 Redis Key 列表', 'infra:redis:get-key-list', 3, 2, 113, '', '', '', NULL, 0, '1', '1', '1', '', '2021-01-26 01:02:52', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1070, '代码生成案例', '', 1, 1, 2, 'demo', 'ep:aim', 'infra/testDemo/index', NULL, 0, '1', '1', '1', '', '2021-02-06 12:42:49', '1', '2023-11-15 23:45:53', '0');
INSERT INTO system_menu  VALUES (1075, '任务触发', 'infra:job:trigger', 3, 8, 110, '', '', '', NULL, 0, '1', '1', '1', '', '2021-02-07 13:03:10', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1077, '链路追踪', '', 2, 4, 2740, 'skywalking', 'fa:eye', 'infra/skywalking/index', 'InfraSkyWalking', 0, '1', '1', '1', '', '2021-02-08 20:41:31', '1', '2024-04-23 00:07:15', '0');
INSERT INTO system_menu  VALUES (1078, '访问日志', '', 2, 1, 1083, 'api-access-log', 'ep:place', 'infra/apiAccessLog/index', 'InfraApiAccessLog', 0, '1', '1', '1', '', '2021-02-26 01:32:59', '1', '2024-02-29 08:54:57', '0');
INSERT INTO system_menu  VALUES (1082, '日志导出', 'infra:api-access-log:export', 3, 2, 1078, '', '', '', NULL, 0, '1', '1', '1', '', '2021-02-26 01:32:59', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1083, 'API 日志', '', 2, 4, 2, 'log', 'fa:tasks', NULL, NULL, 0, '1', '1', '1', '', '2021-02-26 02:18:24', '1', '2024-04-22 23:58:36', '0');
INSERT INTO system_menu  VALUES (1084, '错误日志', 'infra:api-error-log:query', 2, 2, 1083, 'api-error-log', 'ep:warning-filled', 'infra/apiErrorLog/index', 'InfraApiErrorLog', 0, '1', '1', '1', '', '2021-02-26 07:53:20', '1', '2024-02-29 08:55:17', '0');
INSERT INTO system_menu  VALUES (1085, '日志处理', 'infra:api-error-log:update-status', 3, 2, 1084, '', '', '', NULL, 0, '1', '1', '1', '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1086, '日志导出', 'infra:api-error-log:export', 3, 3, 1084, '', '', '', NULL, 0, '1', '1', '1', '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1087, '任务查询', 'infra:job:query', 3, 1, 110, '', '', '', NULL, 0, '1', '1', '1', '1', '2021-03-10 01:26:19', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1088, '日志查询', 'infra:api-access-log:query', 3, 1, 1078, '', '', '', NULL, 0, '1', '1', '1', '1', '2021-03-10 01:28:04', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1089, '日志查询', 'infra:api-error-log:query', 3, 1, 1084, '', '', '', NULL, 0, '1', '1', '1', '1', '2021-03-10 01:29:09', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1090, '文件列表', '', 2, 5, 1243, 'file', 'ep:upload-filled', 'infra/file/index', 'InfraFile', 0, '1', '1', '1', '', '2021-03-12 20:16:20', '1', '2024-02-29 08:53:02', '0');
INSERT INTO system_menu  VALUES (1091, '文件查询', 'infra:file:query', 3, 1, 1090, '', '', '', NULL, 0, '1', '1', '1', '', '2021-03-12 20:16:20', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1092, '文件删除', 'infra:file:delete', 3, 4, 1090, '', '', '', NULL, 0, '1', '1', '1', '', '2021-03-12 20:16:20', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1093, '短信管理', '', 1, 1, 2739, 'sms', 'ep:message', NULL, NULL, 0, '1', '1', '1', '1', '2021-04-05 01:10:16', '1', '2024-04-22 23:56:03', '0');
INSERT INTO system_menu  VALUES (1094, '短信渠道', '', 2, 0, 1093, 'sms-channel', 'fa:stack-exchange', 'system/sms/channel/index', 'SystemSmsChannel', 0, '1', '1', '1', '', '2021-04-01 11:07:15', '1', '2024-02-29 01:15:54', '0');
INSERT INTO system_menu  VALUES (1095, '短信渠道查询', 'system:sms-channel:query', 3, 1, 1094, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1096, '短信渠道创建', 'system:sms-channel:create', 3, 2, 1094, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1097, '短信渠道更新', 'system:sms-channel:update', 3, 3, 1094, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1098, '短信渠道删除', 'system:sms-channel:delete', 3, 4, 1094, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1100, '短信模板', '', 2, 1, 1093, 'sms-template', 'ep:connection', 'system/sms/template/index', 'SystemSmsTemplate', 0, '1', '1', '1', '', '2021-04-01 17:35:17', '1', '2024-02-29 01:16:18', '0');
INSERT INTO system_menu  VALUES (1101, '短信模板查询', 'system:sms-template:query', 3, 1, 1100, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1102, '短信模板创建', 'system:sms-template:create', 3, 2, 1100, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1103, '短信模板更新', 'system:sms-template:update', 3, 3, 1100, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1104, '短信模板删除', 'system:sms-template:delete', 3, 4, 1100, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1105, '短信模板导出', 'system:sms-template:export', 3, 5, 1100, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1106, '发送测试短信', 'system:sms-template:send-sms', 3, 6, 1100, '', '', '', NULL, 0, '1', '1', '1', '1', '2021-04-11 00:26:40', '1', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1107, '短信日志', '', 2, 2, 1093, 'sms-log', 'fa:edit', 'system/sms/log/index', 'SystemSmsLog', 0, '1', '1', '1', '', '2021-04-11 08:37:05', '1', '2024-02-29 08:49:02', '0');
INSERT INTO system_menu  VALUES (1108, '短信日志查询', 'system:sms-log:query', 3, 1, 1107, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-11 08:37:05', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1109, '短信日志导出', 'system:sms-log:export', 3, 5, 1107, '', '', '', NULL, 0, '1', '1', '1', '', '2021-04-11 08:37:05', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1138, '租户列表', '', 2, 0, 1224, 'list', 'ep:house', 'system/tenant/index', 'SystemTenant', 0, '1', '1', '1', '', '2021-12-14 12:31:43', '1', '2024-02-29 01:01:10', '0');
INSERT INTO system_menu  VALUES (1139, '租户查询', 'system:tenant:query', 3, 1, 1138, '', '', '', NULL, 0, '1', '1', '1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1140, '租户创建', 'system:tenant:create', 3, 2, 1138, '', '', '', NULL, 0, '1', '1', '1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1141, '租户更新', 'system:tenant:update', 3, 3, 1138, '', '', '', NULL, 0, '1', '1', '1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1142, '租户删除', 'system:tenant:delete', 3, 4, 1138, '', '', '', NULL, 0, '1', '1', '1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1143, '租户导出', 'system:tenant:export', 3, 5, 1138, '', '', '', NULL, 0, '1', '1', '1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1224, '租户管理', '', 2, 0, 1, 'tenant', 'fa-solid:house-user', NULL, NULL, 0, '1', '1', '1', '1', '2022-02-20 01:41:13', '1', '2024-02-29 00:59:29', '0');
INSERT INTO system_menu  VALUES (1225, '租户套餐', '', 2, 0, 1224, 'package', 'fa:bars', 'system/tenantPackage/index', 'SystemTenantPackage', 0, '1', '1', '1', '', '2022-02-19 17:44:06', '1', '2024-02-29 01:01:43', '0');
INSERT INTO system_menu  VALUES (1226, '租户套餐查询', 'system:tenant-package:query', 3, 1, 1225, '', '', '', NULL, 0, '1', '1', '1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1227, '租户套餐创建', 'system:tenant-package:create', 3, 2, 1225, '', '', '', NULL, 0, '1', '1', '1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1228, '租户套餐更新', 'system:tenant-package:update', 3, 3, 1225, '', '', '', NULL, 0, '1', '1', '1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1229, '租户套餐删除', 'system:tenant-package:delete', 3, 4, 1225, '', '', '', NULL, 0, '1', '1', '1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1237, '文件配置', '', 2, 0, 1243, 'file-config', 'fa-solid:file-signature', 'infra/fileConfig/index', 'InfraFileConfig', 0, '1', '1', '1', '', '2022-03-15 14:35:28', '1', '2024-02-29 08:52:54', '0');
INSERT INTO system_menu  VALUES (1238, '文件配置查询', 'infra:file-config:query', 3, 1, 1237, '', '', '', NULL, 0, '1', '1', '1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1239, '文件配置创建', 'infra:file-config:create', 3, 2, 1237, '', '', '', NULL, 0, '1', '1', '1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1240, '文件配置更新', 'infra:file-config:update', 3, 3, 1237, '', '', '', NULL, 0, '1', '1', '1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1241, '文件配置删除', 'infra:file-config:delete', 3, 4, 1237, '', '', '', NULL, 0, '1', '1', '1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1242, '文件配置导出', 'infra:file-config:export', 3, 5, 1237, '', '', '', NULL, 0, '1', '1', '1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', '0');
INSERT INTO system_menu  VALUES (1243, '文件管理', '', 2, 6, 2, 'file', 'ep:files', NULL, '', 0, '1', '1', '1', '1', '2022-03-16 23:47:40', '1', '2024-04-23 00:02:11', '0');
INSERT INTO system_menu  VALUES (1254, '作者动态', '', 1, 0, 0, 'http://www.enjoy-iot.cn', 'ep:avatar', NULL, NULL, 0, '1', '1', '1', '1', '2022-04-23 01:03:15', '1', '2025-02-04 11:15:24', '1');
INSERT INTO system_menu  VALUES (1255, '数据源配置', '', 2, 1, 2, 'data-source-config', 'ep:data-analysis', 'infra/dataSourceConfig/index', 'InfraDataSourceConfig', 0, '1', '1', '1', '', '2022-04-27 14:37:32', '1', '2024-02-29 08:51:25', '0');
INSERT INTO system_menu  VALUES (1256, '数据源配置查询', 'infra:data-source-config:query', 3, 1, 1255, '', '', '', NULL, 0, '1', '1', '1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', '0');
INSERT INTO system_menu  VALUES (1257, '数据源配置创建', 'infra:data-source-config:create', 3, 2, 1255, '', '', '', NULL, 0, '1', '1', '1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', '0');
INSERT INTO system_menu  VALUES (1258, '数据源配置更新', 'infra:data-source-config:update', 3, 3, 1255, '', '', '', NULL, 0, '1', '1', '1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', '0');
INSERT INTO system_menu  VALUES (1259, '数据源配置删除', 'infra:data-source-config:delete', 3, 4, 1255, '', '', '', NULL, 0, '1', '1', '1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', '0');
INSERT INTO system_menu  VALUES (1260, '数据源配置导出', 'infra:data-source-config:export', 3, 5, 1255, '', '', '', NULL, 0, '1', '1', '1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', '0');
INSERT INTO system_menu  VALUES (1261, 'OAuth 2.0', '', 2, 10, 1, 'oauth2', 'fa:dashcube', NULL, NULL, 0, '1', '1', '1', '1', '2022-05-09 23:38:17', '1', '2024-02-29 01:12:08', '0');
INSERT INTO system_menu  VALUES (1263, '应用管理', '', 2, 0, 1261, 'oauth2/application', 'fa:hdd-o', 'system/oauth2/client/index', 'SystemOAuth2Client', 0, '1', '1', '1', '', '2022-05-10 16:26:33', '1', '2024-02-29 01:13:14', '0');
INSERT INTO system_menu  VALUES (1264, '客户端查询', 'system:oauth2-client:query', 3, 1, 1263, '', '', '', NULL, 0, '1', '1', '1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:06', '0');
INSERT INTO system_menu  VALUES (1265, '客户端创建', 'system:oauth2-client:create', 3, 2, 1263, '', '', '', NULL, 0, '1', '1', '1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:23', '0');
INSERT INTO system_menu  VALUES (1266, '客户端更新', 'system:oauth2-client:update', 3, 3, 1263, '', '', '', NULL, 0, '1', '1', '1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:28', '0');
INSERT INTO system_menu  VALUES (1267, '客户端删除', 'system:oauth2-client:delete', 3, 4, 1263, '', '', '', NULL, 0, '1', '1', '1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:33', '0');
INSERT INTO system_menu  VALUES (2083, '地区管理', '', 2, 14, 1, 'area', 'fa:map-marker', 'system/area/index', 'SystemArea', 0, '1', '1', '1', '1', '2022-12-23 17:35:05', '1', '2024-02-29 08:50:28', '0');
INSERT INTO system_menu  VALUES (2130, '邮箱管理', '', 2, 2, 2739, 'mail', 'fa-solid:mail-bulk', NULL, NULL, 0, '1', '1', '1', '1', '2023-01-25 17:27:44', '1', '2024-04-22 23:56:08', '0');
INSERT INTO system_menu  VALUES (2131, '邮箱账号', '', 2, 0, 2130, 'mail-account', 'fa:universal-access', 'system/mail/account/index', 'SystemMailAccount', 0, '1', '1', '1', '', '2023-01-25 09:33:48', '1', '2024-02-29 08:48:16', '0');
INSERT INTO system_menu  VALUES (2132, '账号查询', 'system:mail-account:query', 3, 1, 2131, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', '0');
INSERT INTO system_menu  VALUES (2133, '账号创建', 'system:mail-account:create', 3, 2, 2131, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', '0');
INSERT INTO system_menu  VALUES (2134, '账号更新', 'system:mail-account:update', 3, 3, 2131, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', '0');
INSERT INTO system_menu  VALUES (2135, '账号删除', 'system:mail-account:delete', 3, 4, 2131, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', '0');
INSERT INTO system_menu  VALUES (2136, '邮件模版', '', 2, 0, 2130, 'mail-template', 'fa:tag', 'system/mail/template/index', 'SystemMailTemplate', 0, '1', '1', '1', '', '2023-01-25 12:05:31', '1', '2024-02-29 08:48:41', '0');
INSERT INTO system_menu  VALUES (2137, '模版查询', 'system:mail-template:query', 3, 1, 2136, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', '0');
INSERT INTO system_menu  VALUES (2138, '模版创建', 'system:mail-template:create', 3, 2, 2136, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', '0');
INSERT INTO system_menu  VALUES (2139, '模版更新', 'system:mail-template:update', 3, 3, 2136, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', '0');
INSERT INTO system_menu  VALUES (2140, '模版删除', 'system:mail-template:delete', 3, 4, 2136, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', '0');
INSERT INTO system_menu  VALUES (2141, '邮件记录', '', 2, 0, 2130, 'mail-log', 'fa:edit', 'system/mail/log/index', 'SystemMailLog', 0, '1', '1', '1', '', '2023-01-26 02:16:50', '1', '2024-02-29 08:48:51', '0');
INSERT INTO system_menu  VALUES (2142, '日志查询', 'system:mail-log:query', 3, 1, 2141, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-26 02:16:50', '', '2023-01-26 02:16:50', '0');
INSERT INTO system_menu  VALUES (2143, '发送测试邮件', 'system:mail-template:send-mail', 3, 5, 2136, '', '', '', NULL, 0, '1', '1', '1', '1', '2023-01-26 23:29:15', '1', '2023-01-26 23:29:15', '0');
INSERT INTO system_menu  VALUES (2144, '站内信管理', '', 1, 3, 2739, 'notify', 'ep:message-box', NULL, NULL, 0, '1', '1', '1', '1', '2023-01-28 10:25:18', '1', '2024-04-22 23:56:12', '0');
INSERT INTO system_menu  VALUES (2145, '模板管理', '', 2, 0, 2144, 'notify-template', 'fa:archive', 'system/notify/template/index', 'SystemNotifyTemplate', 0, '1', '1', '1', '', '2023-01-28 02:26:42', '1', '2024-02-29 08:49:14', '0');
INSERT INTO system_menu  VALUES (2146, '站内信模板查询', 'system:notify-template:query', 3, 1, 2145, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', '0');
INSERT INTO system_menu  VALUES (2147, '站内信模板创建', 'system:notify-template:create', 3, 2, 2145, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', '0');
INSERT INTO system_menu  VALUES (2148, '站内信模板更新', 'system:notify-template:update', 3, 3, 2145, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', '0');
INSERT INTO system_menu  VALUES (2149, '站内信模板删除', 'system:notify-template:delete', 3, 4, 2145, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', '0');
INSERT INTO system_menu  VALUES (2150, '发送测试站内信', 'system:notify-template:send-notify', 3, 5, 2145, '', '', '', NULL, 0, '1', '1', '1', '1', '2023-01-28 10:54:43', '1', '2023-01-28 10:54:43', '0');
INSERT INTO system_menu  VALUES (2151, '消息记录', '', 2, 0, 2144, 'notify-message', 'fa:edit', 'system/notify/message/index', 'SystemNotifyMessage', 0, '1', '1', '1', '', '2023-01-28 04:28:22', '1', '2024-02-29 08:49:22', '0');
INSERT INTO system_menu  VALUES (2152, '站内信消息查询', 'system:notify-message:query', 3, 1, 2151, '', '', '', NULL, 0, '1', '1', '1', '', '2023-01-28 04:28:22', '', '2023-01-28 04:28:22', '0');
INSERT INTO system_menu  VALUES (2159, 'Boot 开发文档', '', 1, 1, 0, 'http://doc.enjoy-iot.cn/', 'ep:document', NULL, NULL, 0, '1', '1', '1', '1', '2023-02-10 22:46:28', '1', '2025-02-04 11:15:32', '1');
INSERT INTO system_menu  VALUES (2160, 'Cloud 开发文档', '', 1, 2, 0, 'https://cloud.enjoy-iot.cn', 'ep:document-copy', NULL, NULL, 0, '1', '1', '1', '1', '2023-02-10 22:47:07', '1', '2025-02-04 11:15:29', '1');
INSERT INTO system_menu  VALUES (2447, '三方登录', '', 1, 10, 1, 'social', 'fa:rocket', '', '', 0, '1', '1', '1', '1', '2023-11-04 12:12:01', '1', '2024-02-29 01:14:05', '0');
INSERT INTO system_menu  VALUES (2448, '三方应用', '', 2, 1, 2447, 'client', 'ep:set-up', 'system/social/client/index.vue', 'SocialClient', 0, '1', '1', '1', '1', '2023-11-04 12:17:19', '1', '2024-05-04 19:09:54', '0');
INSERT INTO system_menu  VALUES (2449, '三方应用查询', 'system:social-client:query', 3, 1, 2448, '', '', '', '', 0, '1', '1', '1', '1', '2023-11-04 12:43:12', '1', '2023-11-04 12:43:33', '0');
INSERT INTO system_menu  VALUES (2450, '三方应用创建', 'system:social-client:create', 3, 2, 2448, '', '', '', '', 0, '1', '1', '1', '1', '2023-11-04 12:43:58', '1', '2023-11-04 12:43:58', '0');
INSERT INTO system_menu  VALUES (2451, '三方应用更新', 'system:social-client:update', 3, 3, 2448, '', '', '', '', 0, '1', '1', '1', '1', '2023-11-04 12:44:27', '1', '2023-11-04 12:44:27', '0');
INSERT INTO system_menu  VALUES (2452, '三方应用删除', 'system:social-client:delete', 3, 4, 2448, '', '', '', '', 0, '1', '1', '1', '1', '2023-11-04 12:44:43', '1', '2023-11-04 12:44:43', '0');
INSERT INTO system_menu  VALUES (2453, '三方用户', 'system:social-user:query', 2, 2, 2447, 'user', 'ep:avatar', 'system/social/user/index.vue', 'SocialUser', 0, '1', '1', '1', '1', '2023-11-04 14:01:05', '1', '2023-11-04 14:01:05', '0');
INSERT INTO system_menu  VALUES (2472, '主子表（内嵌）', '', 2, 12, 1070, 'demo03-inner', 'fa:power-off', 'infra/demo/demo03/inner/index', 'Demo03StudentInner', 0, '1', '1', '1', '', '2023-11-13 04:39:51', '1', '2023-11-16 23:53:46', '0');
INSERT INTO system_menu  VALUES (2478, '单表（增删改查）', '', 2, 1, 1070, 'demo01-contact', 'ep:bicycle', 'infra/demo/demo01/index', 'Demo01Contact', 0, '1', '1', '1', '', '2023-11-15 14:42:30', '1', '2023-11-16 20:34:40', '0');
INSERT INTO system_menu  VALUES (2479, '示例联系人查询', 'infra:demo01-contact:query', 3, 1, 2478, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', '0');
INSERT INTO system_menu  VALUES (2480, '示例联系人创建', 'infra:demo01-contact:create', 3, 2, 2478, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', '0');
INSERT INTO system_menu  VALUES (2481, '示例联系人更新', 'infra:demo01-contact:update', 3, 3, 2478, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', '0');
INSERT INTO system_menu  VALUES (2482, '示例联系人删除', 'infra:demo01-contact:delete', 3, 4, 2478, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', '0');
INSERT INTO system_menu  VALUES (2483, '示例联系人导出', 'infra:demo01-contact:export', 3, 5, 2478, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', '0');
INSERT INTO system_menu  VALUES (2484, '树表（增删改查）', '', 2, 2, 1070, 'demo02-category', 'fa:tree', 'infra/demo/demo02/index', 'Demo02Category', 0, '1', '1', '1', '', '2023-11-16 12:18:27', '1', '2023-11-16 20:35:01', '0');
INSERT INTO system_menu  VALUES (2485, '示例分类查询', 'infra:demo02-category:query', 3, 1, 2484, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', '0');
INSERT INTO system_menu  VALUES (2486, '示例分类创建', 'infra:demo02-category:create', 3, 2, 2484, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', '0');
INSERT INTO system_menu  VALUES (2487, '示例分类更新', 'infra:demo02-category:update', 3, 3, 2484, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', '0');
INSERT INTO system_menu  VALUES (2488, '示例分类删除', 'infra:demo02-category:delete', 3, 4, 2484, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', '0');
INSERT INTO system_menu  VALUES (2489, '示例分类导出', 'infra:demo02-category:export', 3, 5, 2484, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', '0');
INSERT INTO system_menu  VALUES (2490, '主子表（标准）', '', 2, 10, 1070, 'demo03-normal', 'fa:battery-3', 'infra/demo/demo03/normal/index', 'Demo03StudentNormal', 0, '1', '1', '1', '', '2023-11-16 12:53:37', '1', '2023-11-16 23:10:03', '0');
INSERT INTO system_menu  VALUES (2491, '学生查询', 'infra:demo03-student:query', 3, 1, 2490, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', '0');
INSERT INTO system_menu  VALUES (2492, '学生创建', 'infra:demo03-student:create', 3, 2, 2490, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', '0');
INSERT INTO system_menu  VALUES (2493, '学生更新', 'infra:demo03-student:update', 3, 3, 2490, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', '0');
INSERT INTO system_menu  VALUES (2494, '学生删除', 'infra:demo03-student:delete', 3, 4, 2490, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', '0');
INSERT INTO system_menu  VALUES (2495, '学生导出', 'infra:demo03-student:export', 3, 5, 2490, '', '', '', NULL, 0, '1', '1', '1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', '0');
INSERT INTO system_menu  VALUES (2497, '主子表（ERP）', '', 2, 11, 1070, 'demo03-erp', 'ep:calendar', 'infra/demo/demo03/erp/index', 'Demo03StudentERP', 0, '1', '1', '1', '', '2023-11-16 15:50:59', '1', '2023-11-17 13:19:56', '0');
INSERT INTO system_menu  VALUES (2525, 'WebSocket', '', 2, 5, 2, 'websocket', 'ep:connection', 'infra/webSocket/index', 'InfraWebSocket', 0, '1', '1', '1', '1', '2023-11-23 19:41:55', '1', '2024-04-23 00:02:00', '0');
INSERT INTO system_menu  VALUES (2739, '消息中心', '', 1, 7, 1, 'messages', 'ep:chat-dot-round', '', '', 0, '1', '1', '1', '1', '2024-04-22 23:54:30', '1', '2024-04-23 09:36:35', '0');
INSERT INTO system_menu  VALUES (2740, '监控中心', '', 1, 10, 2, 'monitors', 'ep:monitor', '', '', 0, '1', '1', '1', '1', '2024-04-23 00:04:44', '1', '2024-04-23 00:04:44', '0');
INSERT INTO system_menu  VALUES (2814, '设备中心', '', 1, 1, 0, '/device', 'ep:grid', '', '', 0, '1', '1', '1', '1', '2024-12-04 13:59:59', '1', '2025-02-04 11:55:31', '0');
INSERT INTO system_menu  VALUES (2815, '品类管理', '', 2, 0, 2814, 'category', 'ep:cherry', 'eiot/category/index', 'Category', 0, '1', '1', '1', '', '2024-12-04 18:48:09', '1', '2025-02-04 11:57:31', '0');
INSERT INTO system_menu  VALUES (2816, 'IOT产品分类查询', 'iot:category:query', 3, 1, 2815, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:29', '0');
INSERT INTO system_menu  VALUES (2817, 'IOT产品分类创建', 'iot:category:create', 3, 2, 2815, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:35', '0');
INSERT INTO system_menu  VALUES (2818, 'IOT产品分类更新', 'iot:category:update', 3, 3, 2815, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:43', '0');
INSERT INTO system_menu  VALUES (2819, 'IOT产品分类删除', 'iot:category:delete', 3, 4, 2815, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:48', '0');
INSERT INTO system_menu  VALUES (2820, 'IOT产品分类导出', 'iot:category:export', 3, 5, 2815, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 18:48:09', '1', '2025-02-23 20:30:55', '0');
INSERT INTO system_menu  VALUES (2821, '产品管理', '', 2, 0, 2814, 'eiotproduct', 'ep:credit-card', 'eiot/product/index', '', 0, '1', '1', '1', '', '2024-12-04 19:31:42', '1', '2025-02-04 11:57:57', '0');
INSERT INTO system_menu  VALUES (2822, '物联网产品查询', 'iot:product:query', 3, 1, 2821, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:30:02', '0');
INSERT INTO system_menu  VALUES (2823, '物联网产品创建', 'iot:product:create', 3, 2, 2821, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:30:10', '0');
INSERT INTO system_menu  VALUES (2824, '物联网产品更新', 'iot:product:update', 3, 3, 2821, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:30:16', '0');
INSERT INTO system_menu  VALUES (2825, '物联网产品删除', 'iot:product:delete', 3, 4, 2821, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:31:21', '0');
INSERT INTO system_menu  VALUES (2826, '物联网产品导出', 'iot:product:export', 3, 5, 2821, '', '', '', '', 0, '1', '1', '1', '', '2024-12-04 19:31:42', '1', '2025-02-23 20:31:26', '0');
INSERT INTO system_menu  VALUES (2827, '设备管理', '', 2, 0, 2814, 'device-info', 'ep:monitor', 'eiot/deviceinfo/index', '', 0, '1', '1', '1', '', '2024-12-06 14:17:52', '1', '2025-02-04 11:58:59', '0');
INSERT INTO system_menu  VALUES (2828, '设备信息查询', 'iot:device-info:query', 3, 1, 2827, '', '', '', '', 0, '1', '1', '1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:36:53', '0');
INSERT INTO system_menu  VALUES (2829, '设备信息创建', 'iot:device-info:create', 3, 2, 2827, '', '', '', '', 0, '1', '1', '1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:36:58', '0');
INSERT INTO system_menu  VALUES (2830, '设备信息更新', 'iot:device-info:update', 3, 3, 2827, '', '', '', '', 0, '1', '1', '1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:37:07', '0');
INSERT INTO system_menu  VALUES (2831, '设备信息删除', 'iot:device-info:delete', 3, 4, 2827, '', '', '', '', 0, '1', '1', '1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:37:13', '0');
INSERT INTO system_menu  VALUES (2832, '设备信息导出', 'iot:device-info:export', 3, 5, 2827, '', '', '', '', 0, '1', '1', '1', '', '2024-12-06 14:17:52', '1', '2025-02-23 20:37:20', '0');
INSERT INTO system_menu  VALUES (2833, '产品显示模型管理', '', 2, 0, 2814, 'show-model', '', 'eiot/showmodel/index', 'ShowModel', 0, '1', '1', '1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:38:27', '1');
INSERT INTO system_menu  VALUES (2834, '产品显示模型查询', 'eiot:show-model:query', 3, 1, 2833, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:37:44', '1');
INSERT INTO system_menu  VALUES (2835, '产品显示模型创建', 'eiot:show-model:create', 3, 2, 2833, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:37:48', '1');
INSERT INTO system_menu  VALUES (2836, '产品显示模型更新', 'eiot:show-model:update', 3, 3, 2833, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:37:53', '1');
INSERT INTO system_menu  VALUES (2837, '产品显示模型删除', 'eiot:show-model:delete', 3, 4, 2833, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:37:57', '1');
INSERT INTO system_menu  VALUES (2838, '产品显示模型导出', 'eiot:show-model:export', 3, 5, 2833, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:48:45', '1', '2025-01-08 10:38:02', '1');
INSERT INTO system_menu  VALUES (2839, '产品物模型管理', '', 2, 0, 2814, 'thing-model', '', 'eiot/thingmodel/index', 'ThingModel', 0, '1', '1', '1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:43', '1');
INSERT INTO system_menu  VALUES (2840, '产品物模型查询', 'eiot:thing-model:query', 3, 1, 2839, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:13', '1');
INSERT INTO system_menu  VALUES (2841, '产品物模型创建', 'eiot:thing-model:create', 3, 2, 2839, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:18', '1');
INSERT INTO system_menu  VALUES (2842, '产品物模型更新', 'eiot:thing-model:update', 3, 3, 2839, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:21', '1');
INSERT INTO system_menu  VALUES (2843, '产品物模型删除', 'eiot:thing-model:delete', 3, 4, 2839, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:33', '1');
INSERT INTO system_menu  VALUES (2844, '产品物模型导出', 'eiot:thing-model:export', 3, 5, 2839, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-11 11:51:51', '1', '2025-01-08 10:38:38', '1');
INSERT INTO system_menu  VALUES (2845, '规则引擎', '', 1, 3, 0, '/rule', 'ep:operation', '', '', 0, '1', '1', '1', '1', '2024-12-25 14:25:34', '1', '2025-02-04 12:00:53', '0');
INSERT INTO system_menu  VALUES (2846, '规则引擎管理', '', 2, 0, 2845, 'rule-info', 'ep:credit-card', 'eiot/ruleinfo/index', 'EiotRuleInfo', 0, '1', '1', '1', '', '2024-12-25 15:04:19', '1', '2025-02-04 12:01:13', '0');
INSERT INTO system_menu  VALUES (2847, '规则引擎查询', 'iot:rule-info:query', 3, 1, 2846, '', '', '', '', 0, '1', '1', '1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:11', '0');
INSERT INTO system_menu  VALUES (2848, '规则引擎创建', 'iot:rule-info:create', 3, 2, 2846, '', '', '', '', 0, '1', '1', '1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:20', '0');
INSERT INTO system_menu  VALUES (2849, '规则引擎更新', 'iot:rule-info:update', 3, 3, 2846, '', '', '', '', 0, '1', '1', '1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:27', '0');
INSERT INTO system_menu  VALUES (2850, '规则引擎删除', 'iot:rule-info:remove', 3, 4, 2846, '', '', '', '', 0, '1', '1', '1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:33', '0');
INSERT INTO system_menu  VALUES (2851, '规则引擎导出', 'iot:rule-info:export', 3, 5, 2846, '', '', '', '', 0, '1', '1', '1', '', '2024-12-25 15:04:19', '1', '2025-02-23 21:22:38', '0');
INSERT INTO system_menu  VALUES (2852, '告警中心', '', 1, 4, 0, '/notify', 'ep:message', '', '', 0, '1', '1', '1', '1', '2024-12-25 19:21:08', '1', '2025-02-04 12:02:43', '0');
INSERT INTO system_menu  VALUES (2853, '通道模板管理', '', 2, 0, 2914, 'channel-template', '', 'eiot/channel/template', 'ChannelTemplate', 0, '1', '1', '1', '', '2024-12-26 14:25:40', '1', '2025-02-04 12:05:07', '0');
INSERT INTO system_menu  VALUES (2854, '通道模板查询', 'iot:channel:query', 3, 1, 2853, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:05:01', '0');
INSERT INTO system_menu  VALUES (2855, '通道模板创建', 'iot:channel:add', 3, 2, 2853, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:05:57', '0');
INSERT INTO system_menu  VALUES (2856, '通道模板更新', 'iot:channel:update', 3, 3, 2853, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:06:06', '0');
INSERT INTO system_menu  VALUES (2857, '通道模板删除', 'iot:channel:remove', 3, 4, 2853, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:06:31', '0');
INSERT INTO system_menu  VALUES (2858, '通道模板导出', 'iot:channel:query', 3, 5, 2853, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:25:40', '1', '2025-02-23 21:06:51', '0');
INSERT INTO system_menu  VALUES (2859, '通道配置管理', '', 2, 0, 2914, 'channel-config', '', 'eiot/channel/config', 'ChannelConfig', 0, '1', '1', '1', '', '2024-12-26 14:26:29', '1', '2025-02-04 12:05:14', '0');
INSERT INTO system_menu  VALUES (2860, '通道配置查询', 'iot:channel:query', 3, 1, 2859, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:26:30', '1', '2025-02-23 21:07:00', '0');
INSERT INTO system_menu  VALUES (2861, '通道配置创建', 'iot:channel:add', 3, 2, 2859, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:26:30', '1', '2025-02-23 21:07:09', '0');
INSERT INTO system_menu  VALUES (2862, '通道配置更新', 'iot:channel:update', 3, 3, 2859, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:26:30', '1', '2025-02-23 21:07:21', '0');
INSERT INTO system_menu  VALUES (2863, '通道配置删除', 'iot:channel:remove', 3, 4, 2859, '', '', '', '', 0, '1', '1', '1', '', '2024-12-26 14:26:30', '1', '2025-02-23 21:07:30', '0');
INSERT INTO system_menu  VALUES (2864, '通道配置导出', 'eiot:channel-config:export', 3, 5, 2859, '', '', '', NULL, 0, '1', '1', '1', '', '2024-12-26 14:26:30', '', '2024-12-26 14:26:30', '0');
INSERT INTO system_menu  VALUES (2865, '设备分组', 'iot:deviceGroup:list', 2, 3, 2814, 'deviceGroup', 'ep:connection', 'eiot/deviceinfo/deviceGroup', '', 0, '1', '1', '1', '1', '2025-01-14 13:35:37', '1', '2025-02-04 11:59:31', '0');
INSERT INTO system_menu  VALUES (2866, '设备分组详情', 'iot:device:query', 2, 5, 2814, 'deviceGroupDetail/:id', '', 'eiot/deviceinfo/deviceGroupDetail', '', 0, '0', '0', '0', '1', '2025-01-14 14:26:36', '1', '2025-01-14 14:27:17', '0');
INSERT INTO system_menu  VALUES (2867, '定时任务', 'iot:task:query', 2, 2, 2845, 'scheduledTask', 'ep:compass', 'eiot/scheduledTask/index', '', 0, '1', '1', '1', '1', '2025-01-21 15:18:09', '1', '2025-02-23 20:58:38', '0');
INSERT INTO system_menu  VALUES (2868, 'OTA', 'iot:ota:query', 2, 6, 2814, 'deviceOta', 'ep:mostly-cloudy', 'eiot/ota/upgradePack/index', '', 0, '1', '1', '1', '1', '2025-01-21 19:06:31', '1', '2025-01-22 11:26:37', '0');
INSERT INTO system_menu  VALUES (2869, 'OTA查询', 'iot:ota:query', 3, 1, 2868, '', '', '', '', 0, '1', '1', '1', '1', '2025-01-21 19:08:56', '1', '2025-01-21 19:08:56', '0');
INSERT INTO system_menu  VALUES (2913, 'EIOT物联网', '', 1, 4, 0, '/eiot', 'ep:apple', '', '', 0, '1', '1', '1', '1', '2025-02-02 21:20:13', '1', '2025-02-04 11:54:48', '1');
INSERT INTO system_menu  VALUES (2914, '告警推送', '', 1, 2, 2852, 'alarm-setting', 'ep:operation', '', '', 0, '1', '1', '1', '1', '2025-02-04 12:04:41', '1', '2025-02-04 12:06:21', '0');
INSERT INTO system_menu  VALUES (2915, '告警消息', 'iot:alert:query', 2, 1, 2852, 'alarm-messages', 'ep:bell', 'eiot/alarm/list', '', 0, '1', '1', '1', '1', '2025-02-04 12:06:13', '1', '2025-02-23 21:25:17', '0');
INSERT INTO system_menu  VALUES (2916, '告警工单', '', 2, 3, 2852, 'alarm-work-order', 'ep:checked', '', '', 0, '1', '1', '1', '1', '2025-02-04 12:07:17', '1', '2025-02-04 12:07:17', '0');
INSERT INTO system_menu  VALUES (2917, '算法管理', '', 2, 6, 0, '/algorithms', 'ep:cpu', 'ai/algorithm/index', '', 0, '1', '1', '1', '1', '2025-02-04 12:08:46', '1', '2025-02-18 21:44:58', '0');
INSERT INTO system_menu  VALUES (2918, '组件中心', 'eiot:component:query', 2, 5, 0, '/components', 'ep:paperclip', 'eiot/component/index', '', 0, '1', '1', '1', '1', '2025-02-04 12:13:42', '1', '2025-02-23 20:46:10', '0');
INSERT INTO system_menu  VALUES (2919, '数据中心', '', 1, 7, 0, '/data-center', 'ep:data-line', '', '', 0, '1', '1', '1', '1', '2025-02-04 12:16:26', '1', '2025-02-18 21:45:04', '0');
INSERT INTO system_menu  VALUES (2920, '算法模型添加', 'ai:algorithm:create', 3, 1, 2917, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-06 10:10:28', '1', '2025-02-06 10:13:35', '0');
INSERT INTO system_menu  VALUES (2921, '算法模型修改', ' ai:algorithm:update', 3, 2, 2917, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-06 10:10:50', '1', '2025-02-06 10:13:28', '0');
INSERT INTO system_menu  VALUES (2922, '算法模型删除', 'ai:algorithm:delete', 3, 3, 2917, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-06 10:13:01', '1', '2025-02-06 10:13:01', '0');
INSERT INTO system_menu  VALUES (2923, '告警配置', 'iot:alertConfig:query', 2, 2, 2852, 'alarmconfig', 'ep:basketball', 'eiot/alarm/config', '', 0, '1', '1', '1', '1', '2025-02-09 15:40:26', '1', '2025-02-23 21:24:43', '0');
INSERT INTO system_menu  VALUES (2924, '更新组件', 'eiot:component:update', 3, 1, 2918, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-19 01:19:59', '1', '2025-02-19 01:19:59', '0');
INSERT INTO system_menu  VALUES (2925, '虚拟设备', 'eiot:virtual-device:query', 2, 10, 2814, 'virtualDeviceList', 'ep:dish-dot', 'eiot/virtualDevice/index', '', 0, '1', '0', '1', '1', '2025-02-19 14:30:29', '1', '2025-02-19 16:44:58', '0');
INSERT INTO system_menu  VALUES (2926, '虚拟设备详情', 'eiot:virtual-device:query', 2, 10, 2814, 'virtualDeviceConfig/:id', '', 'eiot/virtualDevice/virtualDeviceConfig', '', 0, '0', '0', '0', '1', '2025-02-19 15:10:05', '1', '2025-02-19 16:43:22', '0');
INSERT INTO system_menu  VALUES (2927, '虚拟设备查询', 'eiot:virtual-device:query', 3, 1, 2925, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-19 16:45:28', '1', '2025-02-19 16:45:28', '0');
INSERT INTO system_menu  VALUES (2928, '虚拟设备创建', 'eiot:virtual-device:create', 3, 2, 2925, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-19 16:45:45', '1', '2025-02-19 16:45:45', '0');
INSERT INTO system_menu  VALUES (2929, '虚拟设备更新', 'eiot:virtual-device:update', 3, 3, 2925, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-19 16:45:59', '1', '2025-02-19 16:46:38', '0');
INSERT INTO system_menu  VALUES (2930, '虚拟设备删除', 'eiot:virtual-device:delete', 3, 4, 2925, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-19 16:46:15', '1', '2025-02-19 16:46:15', '0');
INSERT INTO system_menu  VALUES (2931, '设备详情', '', 2, 10, 2814, 'deviceDetail/:id', '', 'eiot/deviceinfo/detail', '', 0, '0', '0', '0', '1', '2025-02-21 01:02:15', '1', '2025-02-21 01:03:08', '0');
INSERT INTO system_menu  VALUES (2932, '物模型修改', 'iot:thing-model:update', 3, 6, 2821, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:34:19', '1', '2025-02-23 20:34:19', '0');
INSERT INTO system_menu  VALUES (2933, '物模型查询', 'iot:thing-model:query', 3, 7, 2821, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:34:58', '1', '2025-02-23 20:34:58', '0');
INSERT INTO system_menu  VALUES (2934, '设备控制', 'iot:device:ctrl', 3, 6, 2827, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:36:20', '1', '2025-02-23 20:36:20', '0');
INSERT INTO system_menu  VALUES (2935, '设备日志', 'iot:deviceLog:query', 3, 7, 2827, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:39:51', '1', '2025-02-23 20:39:51', '0');
INSERT INTO system_menu  VALUES (2936, '设备分组添加', 'iot:deviceGroup:add', 3, 8, 2827, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:41:08', '1', '2025-02-23 20:41:08', '0');
INSERT INTO system_menu  VALUES (2937, '设备组修改', 'iot:deviceGroup:edit', 3, 9, 2827, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:41:45', '1', '2025-02-23 20:41:45', '0');
INSERT INTO system_menu  VALUES (2938, '设备组查询', 'iot:deviceGroup:query', 3, 10, 2827, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:42:13', '1', '2025-02-23 20:42:13', '0');
INSERT INTO system_menu  VALUES (2939, '设备组删除', 'iot:deviceGroup:remove', 3, 11, 2827, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:42:45', '1', '2025-02-23 20:42:45', '0');
INSERT INTO system_menu  VALUES (2940, '新增组件', 'eiot:component:create', 3, 2, 2918, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:46:45', '1', '2025-02-23 20:46:45', '0');
INSERT INTO system_menu  VALUES (2941, '组件删除', 'eiot:component:delete', 3, 3, 2918, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:47:06', '1', '2025-02-23 20:47:06', '0');
INSERT INTO system_menu  VALUES (2942, '暂停规则', 'iot:rule-info:pause', 3, 6, 2846, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:55:16', '1', '2025-02-23 20:55:22', '0');
INSERT INTO system_menu  VALUES (2943, '恢复规则', 'iot:rule-info:resume', 3, 7, 2846, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:55:56', '1', '2025-02-23 20:55:56', '0');
INSERT INTO system_menu  VALUES (2944, '编辑定时任务', 'iot:task:edit', 3, 1, 2867, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:57:50', '1', '2025-02-23 20:57:50', '0');
INSERT INTO system_menu  VALUES (2945, '删除定时任务', 'iot:task:remove', 3, 2, 2867, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 20:58:10', '1', '2025-02-23 20:58:10', '0');
INSERT INTO system_menu  VALUES (2946, '新增告警配置', 'iot:alertConfig:add', 3, 1, 2923, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 21:08:32', '1', '2025-02-23 21:08:32', '0');
INSERT INTO system_menu  VALUES (2947, '查询告警配置', 'iot:alertConfig:query', 3, 2, 2923, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 21:08:56', '1', '2025-02-23 21:08:56', '0');
INSERT INTO system_menu  VALUES (2948, '更新告警配置', 'iot:alertConfig:edit', 3, 3, 2923, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 21:09:26', '1', '2025-02-23 21:10:22', '0');
INSERT INTO system_menu  VALUES (2949, '删除告警配置', 'iot:alertConfig:remove', 3, 4, 2923, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 21:09:42', '1', '2025-02-23 21:09:42', '0');
INSERT INTO system_menu  VALUES (2950, '新增OTA', 'iot:ota:add', 3, 2, 2868, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 21:14:28', '1', '2025-02-23 21:14:28', '0');
INSERT INTO system_menu  VALUES (2951, '移除OTA', 'iot:ota:remove', 3, 3, 2868, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 21:14:52', '1', '2025-02-23 21:14:52', '0');
INSERT INTO system_menu  VALUES (2952, '执行ota', 'iot:ota:upgrade', 3, 4, 2868, '', '', '', '', 0, '1', '1', '1', '1', '2025-02-23 21:15:31', '1', '2025-02-23 21:15:31', '0');
INSERT INTO system_menu  VALUES (2953, 'Modbus管理', '', 1, 2, 0, '/modbus', 'fa:empire', '', '', 0, '1', '1', '1', '1', '2025-03-28 00:26:55', '1', '2025-03-28 00:27:15', '0');
INSERT INTO system_menu  VALUES (2954, '产品点位', 'iot:modbus:list', 2, 1, 2953, 'modbusinfo', 'fa:bullseye', 'eiot/modbus/index', '', 0, '1', '1', '1', '1', '2025-03-28 00:32:21', '1', '2025-03-28 00:33:56', '0');
INSERT INTO system_menu  VALUES (2955, '新建ModbusInfo', 'iot:modbus:add', 3, 1, 2954, '', '', '', '', 0, '1', '1', '1', '1', '2025-03-28 00:35:47', '1', '2025-03-28 00:35:47', '0');
INSERT INTO system_menu  VALUES (2956, '编辑ModbusInfo', 'iot:modbus:edit', 3, 2, 2954, '', '', '', '', 0, '1', '1', '1', '1', '2025-03-28 00:36:23', '1', '2025-03-28 00:36:23', '0');
INSERT INTO system_menu  VALUES (2957, '查看ModbusInfo详情', 'iot:modbus:query', 3, 3, 2954, '', '', '', '', 0, '1', '1', '1', '1', '2025-03-28 00:36:48', '1', '2025-03-28 00:36:48', '0');
INSERT INTO system_menu  VALUES (2958, '删除ModbusInfo', 'iot:modbus:remove', 3, 4, 2954, '', '', '', '', 0, '1', '1', '1', '1', '2025-03-28 00:37:12', '1', '2025-03-28 00:37:12', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_menu_seq;
CREATE SEQUENCE system_menu_seq
    START 2959;

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS system_notice;
CREATE TABLE system_notice (
    id int8 NOT NULL,
  title varchar(50) NOT NULL,
  content text NOT NULL,
  type int2 NOT NULL,
  status int2 NOT NULL DEFAULT 0,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_notice ADD CONSTRAINT pk_system_notice PRIMARY KEY (id);

COMMENT ON COLUMN system_notice.id IS '公告ID';
COMMENT ON COLUMN system_notice.title IS '公告标题';
COMMENT ON COLUMN system_notice.content IS '公告内容';
COMMENT ON COLUMN system_notice.type IS '公告类型（1通知 2公告）';
COMMENT ON COLUMN system_notice.status IS '公告状态（0正常 1关闭）';
COMMENT ON COLUMN system_notice.creator IS '创建者';
COMMENT ON COLUMN system_notice.create_time IS '创建时间';
COMMENT ON COLUMN system_notice.updater IS '更新者';
COMMENT ON COLUMN system_notice.update_time IS '更新时间';
COMMENT ON COLUMN system_notice.deleted IS '是否删除';
COMMENT ON COLUMN system_notice.tenant_id IS '租户编号';
COMMENT ON TABLE system_notice IS '通知公告表';

-- ----------------------------
-- Records of system_notice
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_notice  VALUES (1, '新版本发布通知', '<p>新版本内容133</p>', 1, 0, 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 21:00:20', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_notice_seq;
CREATE SEQUENCE system_notice_seq
    START 2;

-- ----------------------------
-- Table structure for system_notify_message
-- ----------------------------
DROP TABLE IF EXISTS system_notify_message;
CREATE TABLE system_notify_message (
    id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_type int2 NOT NULL,
  template_id int8 NOT NULL,
  template_code varchar(64) NOT NULL,
  template_nickname varchar(63) NOT NULL,
  template_content varchar(1024) NOT NULL,
  template_type int4 NOT NULL,
  template_params varchar(255) NOT NULL,
  read_status bool NOT NULL,
  read_time timestamp NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_notify_message ADD CONSTRAINT pk_system_notify_message PRIMARY KEY (id);

COMMENT ON COLUMN system_notify_message.id IS '用户ID';
COMMENT ON COLUMN system_notify_message.user_id IS '用户id';
COMMENT ON COLUMN system_notify_message.user_type IS '用户类型';
COMMENT ON COLUMN system_notify_message.template_id IS '模版编号';
COMMENT ON COLUMN system_notify_message.template_code IS '模板编码';
COMMENT ON COLUMN system_notify_message.template_nickname IS '模版发送人名称';
COMMENT ON COLUMN system_notify_message.template_content IS '模版内容';
COMMENT ON COLUMN system_notify_message.template_type IS '模版类型';
COMMENT ON COLUMN system_notify_message.template_params IS '模版参数';
COMMENT ON COLUMN system_notify_message.read_status IS '是否已读';
COMMENT ON COLUMN system_notify_message.read_time IS '阅读时间';
COMMENT ON COLUMN system_notify_message.creator IS '创建者';
COMMENT ON COLUMN system_notify_message.create_time IS '创建时间';
COMMENT ON COLUMN system_notify_message.updater IS '更新者';
COMMENT ON COLUMN system_notify_message.update_time IS '更新时间';
COMMENT ON COLUMN system_notify_message.deleted IS '是否删除';
COMMENT ON COLUMN system_notify_message.tenant_id IS '租户编号';
COMMENT ON TABLE system_notify_message IS '站内信消息表';

DROP SEQUENCE IF EXISTS system_notify_message_seq;
CREATE SEQUENCE system_notify_message_seq
    START 1;

-- ----------------------------
-- Table structure for system_notify_template
-- ----------------------------
DROP TABLE IF EXISTS system_notify_template;
CREATE TABLE system_notify_template (
    id int8 NOT NULL,
  name varchar(63) NOT NULL,
  code varchar(64) NOT NULL,
  nickname varchar(255) NOT NULL,
  content varchar(1024) NOT NULL,
  type int2 NOT NULL,
  params varchar(255) NULL DEFAULT NULL,
  status int2 NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_notify_template ADD CONSTRAINT pk_system_notify_template PRIMARY KEY (id);

COMMENT ON COLUMN system_notify_template.id IS '主键';
COMMENT ON COLUMN system_notify_template.name IS '模板名称';
COMMENT ON COLUMN system_notify_template.code IS '模版编码';
COMMENT ON COLUMN system_notify_template.nickname IS '发送人名称';
COMMENT ON COLUMN system_notify_template.content IS '模版内容';
COMMENT ON COLUMN system_notify_template.type IS '类型';
COMMENT ON COLUMN system_notify_template.params IS '参数数组';
COMMENT ON COLUMN system_notify_template.status IS '状态';
COMMENT ON COLUMN system_notify_template.remark IS '备注';
COMMENT ON COLUMN system_notify_template.creator IS '创建者';
COMMENT ON COLUMN system_notify_template.create_time IS '创建时间';
COMMENT ON COLUMN system_notify_template.updater IS '更新者';
COMMENT ON COLUMN system_notify_template.update_time IS '更新时间';
COMMENT ON COLUMN system_notify_template.deleted IS '是否删除';
COMMENT ON TABLE system_notify_template IS '站内信模板表';

DROP SEQUENCE IF EXISTS system_notify_template_seq;
CREATE SEQUENCE system_notify_template_seq
    START 1;

-- ----------------------------
-- Table structure for system_oauth2_access_token
-- ----------------------------
DROP TABLE IF EXISTS system_oauth2_access_token;
CREATE TABLE system_oauth2_access_token (
    id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_type int2 NOT NULL,
  user_info varchar(512) NOT NULL,
  access_token varchar(255) NOT NULL,
  refresh_token varchar(32) NOT NULL,
  client_id varchar(255) NOT NULL,
  scopes varchar(255) NULL DEFAULT NULL,
  expires_time timestamp NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_oauth2_access_token ADD CONSTRAINT pk_system_oauth2_access_token PRIMARY KEY (id);

CREATE INDEX idx_system_oauth2_access_token_01 ON system_oauth2_access_token (access_token);
CREATE INDEX idx_system_oauth2_access_token_02 ON system_oauth2_access_token (refresh_token);

COMMENT ON COLUMN system_oauth2_access_token.id IS '编号';
COMMENT ON COLUMN system_oauth2_access_token.user_id IS '用户编号';
COMMENT ON COLUMN system_oauth2_access_token.user_type IS '用户类型';
COMMENT ON COLUMN system_oauth2_access_token.user_info IS '用户信息';
COMMENT ON COLUMN system_oauth2_access_token.access_token IS '访问令牌';
COMMENT ON COLUMN system_oauth2_access_token.refresh_token IS '刷新令牌';
COMMENT ON COLUMN system_oauth2_access_token.client_id IS '客户端编号';
COMMENT ON COLUMN system_oauth2_access_token.scopes IS '授权范围';
COMMENT ON COLUMN system_oauth2_access_token.expires_time IS '过期时间';
COMMENT ON COLUMN system_oauth2_access_token.creator IS '创建者';
COMMENT ON COLUMN system_oauth2_access_token.create_time IS '创建时间';
COMMENT ON COLUMN system_oauth2_access_token.updater IS '更新者';
COMMENT ON COLUMN system_oauth2_access_token.update_time IS '更新时间';
COMMENT ON COLUMN system_oauth2_access_token.deleted IS '是否删除';
COMMENT ON COLUMN system_oauth2_access_token.tenant_id IS '租户编号';
COMMENT ON TABLE system_oauth2_access_token IS 'OAuth2 访问令牌';

-- ----------------------------
-- Records of system_oauth2_access_token
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539075, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '671c2e52d6a848098aa1bde826378977', '3697fe2caa3c42fbbf14b25167d27e5d', 'default', NULL, '2025-02-23 07:05:01', NULL, '2025-02-23 06:35:01', NULL, '2025-02-23 07:05:52', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539076, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', 'a6b320293435426bb9c4fd1b4c474598', '3697fe2caa3c42fbbf14b25167d27e5d', 'default', NULL, '2025-02-23 07:35:52', NULL, '2025-02-23 07:05:52', NULL, '2025-02-23 07:37:51', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539077, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '0fb206b6e0b148b5822c7f9e0d1a06e3', '3697fe2caa3c42fbbf14b25167d27e5d', 'default', NULL, '2025-02-23 08:07:52', NULL, '2025-02-23 07:37:52', NULL, '2025-02-23 08:08:28', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539078, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', 'd18dc53722ce4810a196c16fa288bfee', '3697fe2caa3c42fbbf14b25167d27e5d', 'default', NULL, '2025-02-23 08:38:28', NULL, '2025-02-23 08:08:28', NULL, '2025-02-23 08:08:28', '0', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539079, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '2151ff0f3b8146fb9d9f4d1a126864f5', '27522033a27342b2b04752e3a2647392', 'default', NULL, '2025-02-23 20:29:22', NULL, '2025-02-23 19:59:22', '1', '2025-02-23 20:11:40', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539080, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '960fb22b30cc4141822e4467db4079aa', '1dc5295cbbbe4d5ea4665b751cd34208', 'default', NULL, '2025-02-23 20:30:21', NULL, '2025-02-23 20:00:21', NULL, '2025-02-23 20:30:22', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539081, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '78301042b7a14645b4eed54850aac14b', 'c05966dd8a6c461f9375979a786e502f', 'default', NULL, '2025-02-23 20:42:00', NULL, '2025-02-23 20:12:00', '1', '2025-02-23 20:12:43', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539082, 104, 2, '{"nickname":"测试号","deptId":"107"}', '85c60a6c823b4d769f7e4520ad1c346e', '5e8bf32b6bc147128e24781562e7ee2f', 'default', NULL, '2025-02-23 20:42:50', NULL, '2025-02-23 20:12:50', NULL, '2025-02-23 20:12:50', '0', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539083, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '548881f0c4cf4050ab6ede06aea141b4', '6c630caa533246d8a48c50e36ba6af11', 'default', NULL, '2025-02-23 20:47:49', NULL, '2025-02-23 20:17:49', NULL, '2025-02-23 20:17:49', '0', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539084, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '252840e2e4ad46bd8881ea36ab1d56ec', '15871af084ad4726a25386b6fafd90e1', 'default', NULL, '2025-02-23 20:55:08', NULL, '2025-02-23 20:25:08', NULL, '2025-02-23 20:55:16', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539085, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '0eeabc414ee84d12af6ec27267ca034d', '1dc5295cbbbe4d5ea4665b751cd34208', 'default', NULL, '2025-02-23 21:00:22', NULL, '2025-02-23 20:30:22', NULL, '2025-02-23 21:00:32', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539086, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '1828a8d71a794745912592980a4b658a', '15871af084ad4726a25386b6fafd90e1', 'default', NULL, '2025-02-23 21:25:16', NULL, '2025-02-23 20:55:16', '1', '2025-02-23 21:17:37', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539087, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '89fa5b026d7f4bbbab9baf5927cb1b42', '1dc5295cbbbe4d5ea4665b751cd34208', 'default', NULL, '2025-02-23 21:30:32', NULL, '2025-02-23 21:00:32', NULL, '2025-02-23 21:00:32', '0', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539088, 104, 2, '{"nickname":"测试号","deptId":"107"}', '7dc77d72b7f7449186f24a41a8426eed', '4df0a1cd23674557aab2cd2ad45ca5c1', 'default', NULL, '2025-02-23 21:48:02', NULL, '2025-02-23 21:18:02', '104', '2025-02-23 21:21:04', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539089, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', 'f02057564b6f477ba4dcc3e9d5eaceb2', 'cc72a55ee66d405d882a228640f4dd53', 'default', NULL, '2025-02-23 21:51:13', NULL, '2025-02-23 21:21:13', '1', '2025-02-23 21:25:26', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539090, 104, 2, '{"nickname":"测试号","deptId":"107"}', '294e5776aa454e83af6774d205aa5f5f', 'ce7de432a49643718a9bfb4f12561f8b', 'default', NULL, '2025-02-23 21:55:34', NULL, '2025-02-23 21:25:34', '104', '2025-02-23 21:26:40', '1', 1);
INSERT INTO system_oauth2_access_token  VALUES (1891133221308539091, 1, 2, '{"nickname":"EnjoyIot","deptId":"103"}', '1093dea5ac3e42d080ed43623400f1b8', '5523e085b00a4bd982e6ecf4a89424a7', 'default', NULL, '2025-02-23 21:56:47', NULL, '2025-02-23 21:26:47', NULL, '2025-02-23 21:26:47', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_oauth2_access_token_seq;
CREATE SEQUENCE system_oauth2_access_token_seq
    START 1891133221308539092;

-- ----------------------------
-- Table structure for system_oauth2_approve
-- ----------------------------
DROP TABLE IF EXISTS system_oauth2_approve;
CREATE TABLE system_oauth2_approve (
    id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_type int2 NOT NULL,
  client_id varchar(255) NOT NULL,
  scope varchar(255) NOT NULL DEFAULT '',
  approved bool NOT NULL DEFAULT '0',
  expires_time timestamp NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_oauth2_approve ADD CONSTRAINT pk_system_oauth2_approve PRIMARY KEY (id);

COMMENT ON COLUMN system_oauth2_approve.id IS '编号';
COMMENT ON COLUMN system_oauth2_approve.user_id IS '用户编号';
COMMENT ON COLUMN system_oauth2_approve.user_type IS '用户类型';
COMMENT ON COLUMN system_oauth2_approve.client_id IS '客户端编号';
COMMENT ON COLUMN system_oauth2_approve.scope IS '授权范围';
COMMENT ON COLUMN system_oauth2_approve.approved IS '是否接受';
COMMENT ON COLUMN system_oauth2_approve.expires_time IS '过期时间';
COMMENT ON COLUMN system_oauth2_approve.creator IS '创建者';
COMMENT ON COLUMN system_oauth2_approve.create_time IS '创建时间';
COMMENT ON COLUMN system_oauth2_approve.updater IS '更新者';
COMMENT ON COLUMN system_oauth2_approve.update_time IS '更新时间';
COMMENT ON COLUMN system_oauth2_approve.deleted IS '是否删除';
COMMENT ON COLUMN system_oauth2_approve.tenant_id IS '租户编号';
COMMENT ON TABLE system_oauth2_approve IS 'OAuth2 批准表';

DROP SEQUENCE IF EXISTS system_oauth2_approve_seq;
CREATE SEQUENCE system_oauth2_approve_seq
    START 1;

-- ----------------------------
-- Table structure for system_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS system_oauth2_client;
CREATE TABLE system_oauth2_client (
    id int8 NOT NULL,
  client_id varchar(255) NOT NULL,
  secret varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  logo varchar(255) NOT NULL,
  description varchar(255) NULL DEFAULT NULL,
  status int2 NOT NULL,
  access_token_validity_seconds int4 NOT NULL,
  refresh_token_validity_seconds int4 NOT NULL,
  redirect_uris varchar(255) NOT NULL,
  authorized_grant_types varchar(255) NOT NULL,
  scopes varchar(255) NULL DEFAULT NULL,
  auto_approve_scopes varchar(255) NULL DEFAULT NULL,
  authorities varchar(255) NULL DEFAULT NULL,
  resource_ids varchar(255) NULL DEFAULT NULL,
  additional_information varchar(4096) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_oauth2_client ADD CONSTRAINT pk_system_oauth2_client PRIMARY KEY (id);

COMMENT ON COLUMN system_oauth2_client.id IS '编号';
COMMENT ON COLUMN system_oauth2_client.client_id IS '客户端编号';
COMMENT ON COLUMN system_oauth2_client.secret IS '客户端密钥';
COMMENT ON COLUMN system_oauth2_client.name IS '应用名';
COMMENT ON COLUMN system_oauth2_client.logo IS '应用图标';
COMMENT ON COLUMN system_oauth2_client.description IS '应用描述';
COMMENT ON COLUMN system_oauth2_client.status IS '状态';
COMMENT ON COLUMN system_oauth2_client.access_token_validity_seconds IS '访问令牌的有效期';
COMMENT ON COLUMN system_oauth2_client.refresh_token_validity_seconds IS '刷新令牌的有效期';
COMMENT ON COLUMN system_oauth2_client.redirect_uris IS '可重定向的 URI 地址';
COMMENT ON COLUMN system_oauth2_client.authorized_grant_types IS '授权类型';
COMMENT ON COLUMN system_oauth2_client.scopes IS '授权范围';
COMMENT ON COLUMN system_oauth2_client.auto_approve_scopes IS '自动通过的授权范围';
COMMENT ON COLUMN system_oauth2_client.authorities IS '权限';
COMMENT ON COLUMN system_oauth2_client.resource_ids IS '资源';
COMMENT ON COLUMN system_oauth2_client.additional_information IS '附加信息';
COMMENT ON COLUMN system_oauth2_client.creator IS '创建者';
COMMENT ON COLUMN system_oauth2_client.create_time IS '创建时间';
COMMENT ON COLUMN system_oauth2_client.updater IS '更新者';
COMMENT ON COLUMN system_oauth2_client.update_time IS '更新时间';
COMMENT ON COLUMN system_oauth2_client.deleted IS '是否删除';
COMMENT ON TABLE system_oauth2_client IS 'OAuth2 客户端表';

-- ----------------------------
-- Records of system_oauth2_client
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_oauth2_client  VALUES (1, 'default', 'admin123', 'EnjoyIot开源物联网平台', '', '我是描述', 0, 1800, 2592000, '["http://www.enjoy-iot.cn"]', '["password","authorization_code","implicit","refresh_token"]', '["user.read","user.write"]', '[]', '["user.read","user.write"]', '[]', '{}', '1', '2022-05-11 21:47:12', '1', '2024-02-22 16:31:52', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_oauth2_client_seq;
CREATE SEQUENCE system_oauth2_client_seq
    START 2;

-- ----------------------------
-- Table structure for system_oauth2_code
-- ----------------------------
DROP TABLE IF EXISTS system_oauth2_code;
CREATE TABLE system_oauth2_code (
    id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_type int2 NOT NULL,
  code varchar(32) NOT NULL,
  client_id varchar(255) NOT NULL,
  scopes varchar(255) NULL DEFAULT '',
  expires_time timestamp NOT NULL,
  redirect_uri varchar(255) NULL DEFAULT NULL,
  state varchar(255) NOT NULL DEFAULT '',
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_oauth2_code ADD CONSTRAINT pk_system_oauth2_code PRIMARY KEY (id);

COMMENT ON COLUMN system_oauth2_code.id IS '编号';
COMMENT ON COLUMN system_oauth2_code.user_id IS '用户编号';
COMMENT ON COLUMN system_oauth2_code.user_type IS '用户类型';
COMMENT ON COLUMN system_oauth2_code.code IS '授权码';
COMMENT ON COLUMN system_oauth2_code.client_id IS '客户端编号';
COMMENT ON COLUMN system_oauth2_code.scopes IS '授权范围';
COMMENT ON COLUMN system_oauth2_code.expires_time IS '过期时间';
COMMENT ON COLUMN system_oauth2_code.redirect_uri IS '可重定向的 URI 地址';
COMMENT ON COLUMN system_oauth2_code.state IS '状态';
COMMENT ON COLUMN system_oauth2_code.creator IS '创建者';
COMMENT ON COLUMN system_oauth2_code.create_time IS '创建时间';
COMMENT ON COLUMN system_oauth2_code.updater IS '更新者';
COMMENT ON COLUMN system_oauth2_code.update_time IS '更新时间';
COMMENT ON COLUMN system_oauth2_code.deleted IS '是否删除';
COMMENT ON COLUMN system_oauth2_code.tenant_id IS '租户编号';
COMMENT ON TABLE system_oauth2_code IS 'OAuth2 授权码表';

DROP SEQUENCE IF EXISTS system_oauth2_code_seq;
CREATE SEQUENCE system_oauth2_code_seq
    START 1;

-- ----------------------------
-- Table structure for system_oauth2_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS system_oauth2_refresh_token;
CREATE TABLE system_oauth2_refresh_token (
    id int8 NOT NULL,
  user_id int8 NOT NULL,
  refresh_token varchar(32) NOT NULL,
  user_type int2 NOT NULL,
  client_id varchar(255) NOT NULL,
  scopes varchar(255) NULL DEFAULT NULL,
  expires_time timestamp NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_oauth2_refresh_token ADD CONSTRAINT pk_system_oauth2_refresh_token PRIMARY KEY (id);

COMMENT ON COLUMN system_oauth2_refresh_token.id IS '编号';
COMMENT ON COLUMN system_oauth2_refresh_token.user_id IS '用户编号';
COMMENT ON COLUMN system_oauth2_refresh_token.refresh_token IS '刷新令牌';
COMMENT ON COLUMN system_oauth2_refresh_token.user_type IS '用户类型';
COMMENT ON COLUMN system_oauth2_refresh_token.client_id IS '客户端编号';
COMMENT ON COLUMN system_oauth2_refresh_token.scopes IS '授权范围';
COMMENT ON COLUMN system_oauth2_refresh_token.expires_time IS '过期时间';
COMMENT ON COLUMN system_oauth2_refresh_token.creator IS '创建者';
COMMENT ON COLUMN system_oauth2_refresh_token.create_time IS '创建时间';
COMMENT ON COLUMN system_oauth2_refresh_token.updater IS '更新者';
COMMENT ON COLUMN system_oauth2_refresh_token.update_time IS '更新时间';
COMMENT ON COLUMN system_oauth2_refresh_token.deleted IS '是否删除';
COMMENT ON COLUMN system_oauth2_refresh_token.tenant_id IS '租户编号';
COMMENT ON TABLE system_oauth2_refresh_token IS 'OAuth2 刷新令牌';

-- ----------------------------
-- Records of system_oauth2_refresh_token
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_oauth2_refresh_token  VALUES (1720, 1, '3697fe2caa3c42fbbf14b25167d27e5d', 2, 'default', NULL, '2025-03-25 06:35:01', NULL, '2025-02-23 06:35:01', NULL, '2025-02-23 06:35:01', '0', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1721, 1, '27522033a27342b2b04752e3a2647392', 2, 'default', NULL, '2025-03-25 19:59:22', NULL, '2025-02-23 19:59:22', NULL, '2025-02-23 20:11:39', '1', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1722, 1, '1dc5295cbbbe4d5ea4665b751cd34208', 2, 'default', NULL, '2025-03-25 20:00:21', NULL, '2025-02-23 20:00:21', NULL, '2025-02-23 20:00:21', '0', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1723, 1, 'c05966dd8a6c461f9375979a786e502f', 2, 'default', NULL, '2025-03-25 20:12:00', NULL, '2025-02-23 20:12:00', NULL, '2025-02-23 20:12:43', '1', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1724, 104, '5e8bf32b6bc147128e24781562e7ee2f', 2, 'default', NULL, '2025-03-25 20:12:50', NULL, '2025-02-23 20:12:50', NULL, '2025-02-23 20:12:50', '0', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1725, 1, '6c630caa533246d8a48c50e36ba6af11', 2, 'default', NULL, '2025-03-25 20:17:49', NULL, '2025-02-23 20:17:49', NULL, '2025-02-23 20:17:49', '0', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1726, 1, '15871af084ad4726a25386b6fafd90e1', 2, 'default', NULL, '2025-03-25 20:25:08', NULL, '2025-02-23 20:25:08', NULL, '2025-02-23 21:17:36', '1', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1727, 104, '4df0a1cd23674557aab2cd2ad45ca5c1', 2, 'default', NULL, '2025-03-25 21:18:02', NULL, '2025-02-23 21:18:02', NULL, '2025-02-23 21:21:03', '1', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1728, 1, 'cc72a55ee66d405d882a228640f4dd53', 2, 'default', NULL, '2025-03-25 21:21:13', NULL, '2025-02-23 21:21:13', NULL, '2025-02-23 21:25:26', '1', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1729, 104, 'ce7de432a49643718a9bfb4f12561f8b', 2, 'default', NULL, '2025-03-25 21:25:34', NULL, '2025-02-23 21:25:34', NULL, '2025-02-23 21:26:39', '1', 1);
INSERT INTO system_oauth2_refresh_token  VALUES (1730, 1, '5523e085b00a4bd982e6ecf4a89424a7', 2, 'default', NULL, '2025-03-25 21:26:47', NULL, '2025-02-23 21:26:47', NULL, '2025-02-23 21:26:47', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_oauth2_refresh_token_seq;
CREATE SEQUENCE system_oauth2_refresh_token_seq
    START 1731;

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS system_operate_log;
CREATE TABLE system_operate_log (
    id int8 NOT NULL,
  trace_id varchar(64) NOT NULL DEFAULT '',
  user_id int8 NOT NULL,
  user_type int2 NOT NULL DEFAULT 0,
  type varchar(50) NOT NULL,
  sub_type varchar(50) NOT NULL,
  biz_id int8 NOT NULL,
  action varchar(2000) NOT NULL DEFAULT '',
  success bool NOT NULL DEFAULT '1',
  extra varchar(2000) NOT NULL DEFAULT '',
  request_method varchar(16) NULL DEFAULT '',
  request_url varchar(255) NULL DEFAULT '',
  user_ip varchar(50) NULL DEFAULT NULL,
  user_agent varchar(512) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_operate_log ADD CONSTRAINT pk_system_operate_log PRIMARY KEY (id);

COMMENT ON COLUMN system_operate_log.id IS '日志主键';
COMMENT ON COLUMN system_operate_log.trace_id IS '链路追踪编号';
COMMENT ON COLUMN system_operate_log.user_id IS '用户编号';
COMMENT ON COLUMN system_operate_log.user_type IS '用户类型';
COMMENT ON COLUMN system_operate_log.type IS '操作模块类型';
COMMENT ON COLUMN system_operate_log.sub_type IS '操作名';
COMMENT ON COLUMN system_operate_log.biz_id IS '操作数据模块编号';
COMMENT ON COLUMN system_operate_log.action IS '操作内容';
COMMENT ON COLUMN system_operate_log.success IS '操作结果';
COMMENT ON COLUMN system_operate_log.extra IS '拓展字段';
COMMENT ON COLUMN system_operate_log.request_method IS '请求方法名';
COMMENT ON COLUMN system_operate_log.request_url IS '请求地址';
COMMENT ON COLUMN system_operate_log.user_ip IS '用户 IP';
COMMENT ON COLUMN system_operate_log.user_agent IS '浏览器 UA';
COMMENT ON COLUMN system_operate_log.creator IS '创建者';
COMMENT ON COLUMN system_operate_log.create_time IS '创建时间';
COMMENT ON COLUMN system_operate_log.updater IS '更新者';
COMMENT ON COLUMN system_operate_log.update_time IS '更新时间';
COMMENT ON COLUMN system_operate_log.deleted IS '是否删除';
COMMENT ON COLUMN system_operate_log.tenant_id IS '租户编号';
COMMENT ON TABLE system_operate_log IS '操作日志记录 V2 版本';

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_operate_log  VALUES (9064, '', 1, 2, 'SYSTEM 角色', '创建角色', 154, '创建了角色【只读用户】', '1', '', 'POST', '/admin-api/system/role/create', '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:05:09', NULL, '2025-02-23 20:05:09', '0', 1);
INSERT INTO system_operate_log  VALUES (9065, '', 1, 2, 'SYSTEM 用户', '重置用户密码', 104, '将用户【测试号】的密码从【$2a$04$jDFLttgfik0QqJKAbfhMa.2A9xXoZmAIxakdFJUzkX.MgBKT6ddo6】重置为【$2a$04$KG9vBvKLO5PP9Tfd8ttF0.CDObtXPhMQb8I7vPEdVBSuI7uOx51Ey】', '1', '', 'PUT', '/admin-api/system/user/update-password', '27.38.247.37', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36', NULL, '2025-02-23 20:12:38', NULL, '2025-02-23 20:12:38', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_operate_log_seq;
CREATE SEQUENCE system_operate_log_seq
    START 9066;

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS system_post;
CREATE TABLE system_post (
    id int8 NOT NULL,
  code varchar(64) NOT NULL,
  name varchar(50) NOT NULL,
  sort int4 NOT NULL,
  status int2 NOT NULL,
  remark varchar(500) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_post ADD CONSTRAINT pk_system_post PRIMARY KEY (id);

COMMENT ON COLUMN system_post.id IS '岗位ID';
COMMENT ON COLUMN system_post.code IS '岗位编码';
COMMENT ON COLUMN system_post.name IS '岗位名称';
COMMENT ON COLUMN system_post.sort IS '显示顺序';
COMMENT ON COLUMN system_post.status IS '状态（0正常 1停用）';
COMMENT ON COLUMN system_post.remark IS '备注';
COMMENT ON COLUMN system_post.creator IS '创建者';
COMMENT ON COLUMN system_post.create_time IS '创建时间';
COMMENT ON COLUMN system_post.updater IS '更新者';
COMMENT ON COLUMN system_post.update_time IS '更新时间';
COMMENT ON COLUMN system_post.deleted IS '是否删除';
COMMENT ON COLUMN system_post.tenant_id IS '租户编号';
COMMENT ON TABLE system_post IS '岗位信息表';

-- ----------------------------
-- Records of system_post
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_post  VALUES (1, 'ceo', '董事长', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2023-02-11 15:19:04', '0', 1);
INSERT INTO system_post  VALUES (2, 'se', '项目经理', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2023-11-15 09:18:20', '0', 1);
INSERT INTO system_post  VALUES (4, 'user', '普通员工', 4, 0, '111', 'admin', '2021-01-05 17:03:48', '1', '2023-12-02 10:04:37', '0', 1);
INSERT INTO system_post  VALUES (5, 'HR', '人力资源', 5, 0, '', '1', '2024-03-24 20:45:40', '1', '2024-03-24 20:45:40', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_post_seq;
CREATE SEQUENCE system_post_seq
    START 6;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS system_role;
CREATE TABLE system_role (
    id int8 NOT NULL,
  name varchar(30) NOT NULL,
  code varchar(100) NOT NULL,
  sort int4 NOT NULL,
  data_scope int2 NOT NULL DEFAULT 1,
  data_scope_dept_ids varchar(500) NOT NULL DEFAULT '',
  status int2 NOT NULL,
  type int2 NOT NULL,
  remark varchar(500) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_role ADD CONSTRAINT pk_system_role PRIMARY KEY (id);

COMMENT ON COLUMN system_role.id IS '角色ID';
COMMENT ON COLUMN system_role.name IS '角色名称';
COMMENT ON COLUMN system_role.code IS '角色权限字符串';
COMMENT ON COLUMN system_role.sort IS '显示顺序';
COMMENT ON COLUMN system_role.data_scope IS '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）';
COMMENT ON COLUMN system_role.data_scope_dept_ids IS '数据范围 ( 指定部门数组)';
COMMENT ON COLUMN system_role.status IS '角色状态（0正常 1停用）';
COMMENT ON COLUMN system_role.type IS '角色类型';
COMMENT ON COLUMN system_role.remark IS '备注';
COMMENT ON COLUMN system_role.creator IS '创建者';
COMMENT ON COLUMN system_role.create_time IS '创建时间';
COMMENT ON COLUMN system_role.updater IS '更新者';
COMMENT ON COLUMN system_role.update_time IS '更新时间';
COMMENT ON COLUMN system_role.deleted IS '是否删除';
COMMENT ON COLUMN system_role.tenant_id IS '租户编号';
COMMENT ON TABLE system_role IS '角色信息表';

-- ----------------------------
-- Records of system_role
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_role  VALUES (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21', '0', 1);
INSERT INTO system_role  VALUES (154, '只读用户', 'reader', 2, 1, '', 0, 2, '', '1', '2025-02-23 20:05:08', '1', '2025-02-23 20:05:08', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_role_seq;
CREATE SEQUENCE system_role_seq
    START 155;

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS system_role_menu;
CREATE TABLE system_role_menu (
    id int8 NOT NULL,
  role_id int8 NOT NULL,
  menu_id int8 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_role_menu ADD CONSTRAINT pk_system_role_menu PRIMARY KEY (id);

COMMENT ON COLUMN system_role_menu.id IS '自增编号';
COMMENT ON COLUMN system_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN system_role_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN system_role_menu.creator IS '创建者';
COMMENT ON COLUMN system_role_menu.create_time IS '创建时间';
COMMENT ON COLUMN system_role_menu.updater IS '更新者';
COMMENT ON COLUMN system_role_menu.update_time IS '更新时间';
COMMENT ON COLUMN system_role_menu.deleted IS '是否删除';
COMMENT ON COLUMN system_role_menu.tenant_id IS '租户编号';
COMMENT ON TABLE system_role_menu IS '角色和菜单关联表';

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_role_menu  VALUES (5793, 154, 2816, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5794, 154, 2817, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5795, 154, 1025, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5796, 154, 1, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5797, 154, 1026, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5798, 154, 2, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5799, 154, 2820, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5800, 154, 2821, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5801, 154, 2822, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5802, 154, 1030, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5803, 154, 2823, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5804, 154, 1031, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5805, 154, 2826, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5806, 154, 1035, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5807, 154, 2827, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5808, 154, 2828, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5809, 154, 1036, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5810, 154, 2829, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5811, 154, 2832, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5812, 154, 1040, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5813, 154, 1042, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5814, 154, 1043, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5815, 154, 1045, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5816, 154, 1046, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5817, 154, 2845, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5818, 154, 1054, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5819, 154, 2846, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5820, 154, 2847, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5821, 154, 2848, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5822, 154, 1056, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5823, 154, 1057, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5824, 154, 1058, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5825, 154, 2851, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5826, 154, 2083, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5827, 154, 1059, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5828, 154, 1060, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5829, 154, 2852, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5830, 154, 2853, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5831, 154, 2854, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5832, 154, 2855, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5833, 154, 2858, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5834, 154, 1066, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5835, 154, 1067, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5836, 154, 2859, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5837, 154, 2860, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5838, 154, 2861, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5839, 154, 1070, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5840, 154, 2864, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5841, 154, 2865, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5842, 154, 2866, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5843, 154, 2867, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5844, 154, 2868, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5845, 154, 2869, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5846, 154, 1077, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5847, 154, 1078, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5848, 154, 1082, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5849, 154, 1083, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5850, 154, 1084, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5851, 154, 1085, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5852, 154, 1086, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5853, 154, 1087, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5854, 154, 1088, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5855, 154, 1089, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5856, 154, 1090, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5857, 154, 1091, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5858, 154, 1093, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5859, 154, 1094, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5860, 154, 1095, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5861, 154, 1100, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5862, 154, 1101, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5863, 154, 1105, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5864, 154, 1106, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5865, 154, 2130, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5866, 154, 1107, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5867, 154, 2131, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5868, 154, 1108, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5869, 154, 2132, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5870, 154, 1109, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5871, 154, 2136, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5872, 154, 2137, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5873, 154, 2141, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5874, 154, 2142, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5875, 154, 2143, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5876, 154, 2144, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5877, 154, 2145, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5878, 154, 2146, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5879, 154, 2914, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5880, 154, 2915, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5881, 154, 2916, '1', '2025-02-23 20:11:14', '1', '2025-02-23 21:27:29', '1', 1);
INSERT INTO system_role_menu  VALUES (5882, 154, 100, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5883, 154, 2917, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5884, 154, 101, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5885, 154, 2918, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5886, 154, 2150, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5887, 154, 102, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5888, 154, 2151, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5889, 154, 103, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5890, 154, 2920, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5891, 154, 2152, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5892, 154, 104, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5893, 154, 105, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5894, 154, 106, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5895, 154, 2923, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5896, 154, 107, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5897, 154, 2924, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5898, 154, 108, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5899, 154, 2925, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5900, 154, 109, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5901, 154, 2926, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5902, 154, 110, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5903, 154, 2927, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5904, 154, 111, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5905, 154, 2928, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5906, 154, 112, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5907, 154, 2929, '1', '2025-02-23 20:11:14', '1', '2025-02-23 21:17:29', '1', 1);
INSERT INTO system_role_menu  VALUES (5908, 154, 113, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5909, 154, 2930, '1', '2025-02-23 20:11:14', '1', '2025-02-23 21:17:29', '1', 1);
INSERT INTO system_role_menu  VALUES (5910, 154, 114, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5911, 154, 1138, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5912, 154, 2931, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5913, 154, 1139, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5914, 154, 115, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5915, 154, 116, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5916, 154, 1143, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5917, 154, 2447, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5918, 154, 2448, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5919, 154, 2449, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5920, 154, 2453, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5921, 154, 2472, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5922, 154, 2478, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5923, 154, 2479, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5924, 154, 2480, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5925, 154, 2481, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5926, 154, 2482, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5927, 154, 2483, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5928, 154, 2739, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5929, 154, 2484, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5930, 154, 2740, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5931, 154, 2485, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5932, 154, 2486, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5933, 154, 2487, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5934, 154, 2488, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5935, 154, 2489, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5936, 154, 2490, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5937, 154, 2491, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5938, 154, 2492, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5939, 154, 2493, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5940, 154, 2494, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5941, 154, 2495, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5942, 154, 2497, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5943, 154, 1224, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5944, 154, 1225, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5945, 154, 1226, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5946, 154, 1237, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5947, 154, 1238, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5948, 154, 1242, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5949, 154, 1243, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5950, 154, 2525, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5951, 154, 1255, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5952, 154, 1256, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5953, 154, 1001, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5954, 154, 1260, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5955, 154, 1005, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5956, 154, 1261, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5957, 154, 1006, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5958, 154, 1263, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5959, 154, 1008, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5960, 154, 1264, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5961, 154, 1012, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5962, 154, 500, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5963, 154, 1013, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5964, 154, 501, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5965, 154, 1017, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5966, 154, 1021, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5967, 154, 2814, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5968, 154, 2815, '1', '2025-02-23 20:11:14', '1', '2025-02-23 20:11:14', '0', 1);
INSERT INTO system_role_menu  VALUES (5969, 154, 2933, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', '0', 1);
INSERT INTO system_role_menu  VALUES (5970, 154, 2934, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', '0', 1);
INSERT INTO system_role_menu  VALUES (5971, 154, 2935, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', '0', 1);
INSERT INTO system_role_menu  VALUES (5972, 154, 2936, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', '0', 1);
INSERT INTO system_role_menu  VALUES (5973, 154, 2938, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', '0', 1);
INSERT INTO system_role_menu  VALUES (5974, 154, 2944, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', '0', 1);
INSERT INTO system_role_menu  VALUES (5975, 154, 2947, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', '0', 1);
INSERT INTO system_role_menu  VALUES (5976, 154, 2948, '1', '2025-02-23 21:16:06', '1', '2025-02-23 21:16:06', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_role_menu_seq;
CREATE SEQUENCE system_role_menu_seq
    START 5977;

-- ----------------------------
-- Table structure for system_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS system_sms_channel;
CREATE TABLE system_sms_channel (
    id int8 NOT NULL,
  signature varchar(12) NOT NULL,
  code varchar(63) NOT NULL,
  status int2 NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  api_key varchar(128) NOT NULL,
  api_secret varchar(128) NULL DEFAULT NULL,
  callback_url varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_sms_channel ADD CONSTRAINT pk_system_sms_channel PRIMARY KEY (id);

COMMENT ON COLUMN system_sms_channel.id IS '编号';
COMMENT ON COLUMN system_sms_channel.signature IS '短信签名';
COMMENT ON COLUMN system_sms_channel.code IS '渠道编码';
COMMENT ON COLUMN system_sms_channel.status IS '开启状态';
COMMENT ON COLUMN system_sms_channel.remark IS '备注';
COMMENT ON COLUMN system_sms_channel.api_key IS '短信 API 的账号';
COMMENT ON COLUMN system_sms_channel.api_secret IS '短信 API 的秘钥';
COMMENT ON COLUMN system_sms_channel.callback_url IS '短信发送回调 URL';
COMMENT ON COLUMN system_sms_channel.creator IS '创建者';
COMMENT ON COLUMN system_sms_channel.create_time IS '创建时间';
COMMENT ON COLUMN system_sms_channel.updater IS '更新者';
COMMENT ON COLUMN system_sms_channel.update_time IS '更新时间';
COMMENT ON COLUMN system_sms_channel.deleted IS '是否删除';
COMMENT ON TABLE system_sms_channel IS '短信渠道';

-- ----------------------------
-- Records of system_sms_channel
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_sms_channel  VALUES (2, 'Ballcat', 'ALIYUN', 0, '你要改哦，只有我可以用！！！！', 'LTAI5tCnKso2uG3kJ5gRav88', 'fGJ5SNXL7P1NHNRmJ7DJaMJGPyE55C', NULL, '', '2021-03-31 11:53:10', '1', '2024-08-04 08:53:26', '0');
INSERT INTO system_sms_channel  VALUES (4, '测试渠道', 'DEBUG_DING_TALK', 0, '123', '696b5d8ead48071237e4aa5861ff08dbadb2b4ded1c688a7b7c9afc615579859', 'SEC5c4e5ff888bc8a9923ae47f59e7ccd30af1f14d93c55b4e2c9cb094e35aeed67', NULL, '1', '2021-04-13 00:23:14', '1', '2022-03-27 20:29:49', '0');
INSERT INTO system_sms_channel  VALUES (7, 'mock腾讯云', 'TENCENT', 0, '', '1 2', '2 3', '', '1', '2024-09-30 08:53:45', '1', '2024-09-30 08:55:01', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_sms_channel_seq;
CREATE SEQUENCE system_sms_channel_seq
    START 8;

-- ----------------------------
-- Table structure for system_sms_code
-- ----------------------------
DROP TABLE IF EXISTS system_sms_code;
CREATE TABLE system_sms_code (
    id int8 NOT NULL,
  mobile varchar(11) NOT NULL,
  code varchar(6) NOT NULL,
  create_ip varchar(15) NOT NULL,
  scene int2 NOT NULL,
  today_index int2 NOT NULL,
  used int2 NOT NULL,
  used_time timestamp NULL DEFAULT NULL,
  used_ip varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_sms_code ADD CONSTRAINT pk_system_sms_code PRIMARY KEY (id);

CREATE INDEX idx_system_sms_code_01 ON system_sms_code (mobile);

COMMENT ON COLUMN system_sms_code.id IS '编号';
COMMENT ON COLUMN system_sms_code.mobile IS '手机号';
COMMENT ON COLUMN system_sms_code.code IS '验证码';
COMMENT ON COLUMN system_sms_code.create_ip IS '创建 IP';
COMMENT ON COLUMN system_sms_code.scene IS '发送场景';
COMMENT ON COLUMN system_sms_code.today_index IS '今日发送的第几条';
COMMENT ON COLUMN system_sms_code.used IS '是否使用';
COMMENT ON COLUMN system_sms_code.used_time IS '使用时间';
COMMENT ON COLUMN system_sms_code.used_ip IS '使用 IP';
COMMENT ON COLUMN system_sms_code.creator IS '创建者';
COMMENT ON COLUMN system_sms_code.create_time IS '创建时间';
COMMENT ON COLUMN system_sms_code.updater IS '更新者';
COMMENT ON COLUMN system_sms_code.update_time IS '更新时间';
COMMENT ON COLUMN system_sms_code.deleted IS '是否删除';
COMMENT ON COLUMN system_sms_code.tenant_id IS '租户编号';
COMMENT ON TABLE system_sms_code IS '手机验证码';

DROP SEQUENCE IF EXISTS system_sms_code_seq;
CREATE SEQUENCE system_sms_code_seq
    START 1;

-- ----------------------------
-- Table structure for system_sms_log
-- ----------------------------
DROP TABLE IF EXISTS system_sms_log;
CREATE TABLE system_sms_log (
    id int8 NOT NULL,
  channel_id int8 NOT NULL,
  channel_code varchar(63) NOT NULL,
  template_id int8 NOT NULL,
  template_code varchar(63) NOT NULL,
  template_type int2 NOT NULL,
  template_content varchar(255) NOT NULL,
  template_params varchar(255) NOT NULL,
  api_template_id varchar(63) NOT NULL,
  mobile varchar(11) NOT NULL,
  user_id int8 NULL DEFAULT NULL,
  user_type int2 NULL DEFAULT NULL,
  send_status int2 NOT NULL DEFAULT 0,
  send_time timestamp NULL DEFAULT NULL,
  api_send_code varchar(63) NULL DEFAULT NULL,
  api_send_msg varchar(255) NULL DEFAULT NULL,
  api_request_id varchar(255) NULL DEFAULT NULL,
  api_serial_no varchar(255) NULL DEFAULT NULL,
  receive_status int2 NOT NULL DEFAULT 0,
  receive_time timestamp NULL DEFAULT NULL,
  api_receive_code varchar(63) NULL DEFAULT NULL,
  api_receive_msg varchar(255) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_sms_log ADD CONSTRAINT pk_system_sms_log PRIMARY KEY (id);

COMMENT ON COLUMN system_sms_log.id IS '编号';
COMMENT ON COLUMN system_sms_log.channel_id IS '短信渠道编号';
COMMENT ON COLUMN system_sms_log.channel_code IS '短信渠道编码';
COMMENT ON COLUMN system_sms_log.template_id IS '模板编号';
COMMENT ON COLUMN system_sms_log.template_code IS '模板编码';
COMMENT ON COLUMN system_sms_log.template_type IS '短信类型';
COMMENT ON COLUMN system_sms_log.template_content IS '短信内容';
COMMENT ON COLUMN system_sms_log.template_params IS '短信参数';
COMMENT ON COLUMN system_sms_log.api_template_id IS '短信 API 的模板编号';
COMMENT ON COLUMN system_sms_log.mobile IS '手机号';
COMMENT ON COLUMN system_sms_log.user_id IS '用户编号';
COMMENT ON COLUMN system_sms_log.user_type IS '用户类型';
COMMENT ON COLUMN system_sms_log.send_status IS '发送状态';
COMMENT ON COLUMN system_sms_log.send_time IS '发送时间';
COMMENT ON COLUMN system_sms_log.api_send_code IS '短信 API 发送结果的编码';
COMMENT ON COLUMN system_sms_log.api_send_msg IS '短信 API 发送失败的提示';
COMMENT ON COLUMN system_sms_log.api_request_id IS '短信 API 发送返回的唯一请求 ID';
COMMENT ON COLUMN system_sms_log.api_serial_no IS '短信 API 发送返回的序号';
COMMENT ON COLUMN system_sms_log.receive_status IS '接收状态';
COMMENT ON COLUMN system_sms_log.receive_time IS '接收时间';
COMMENT ON COLUMN system_sms_log.api_receive_code IS 'API 接收结果的编码';
COMMENT ON COLUMN system_sms_log.api_receive_msg IS 'API 接收结果的说明';
COMMENT ON COLUMN system_sms_log.creator IS '创建者';
COMMENT ON COLUMN system_sms_log.create_time IS '创建时间';
COMMENT ON COLUMN system_sms_log.updater IS '更新者';
COMMENT ON COLUMN system_sms_log.update_time IS '更新时间';
COMMENT ON COLUMN system_sms_log.deleted IS '是否删除';
COMMENT ON TABLE system_sms_log IS '短信日志';

DROP SEQUENCE IF EXISTS system_sms_log_seq;
CREATE SEQUENCE system_sms_log_seq
    START 1;

-- ----------------------------
-- Table structure for system_sms_template
-- ----------------------------
DROP TABLE IF EXISTS system_sms_template;
CREATE TABLE system_sms_template (
    id int8 NOT NULL,
  type int2 NOT NULL,
  status int2 NOT NULL,
  code varchar(63) NOT NULL,
  name varchar(63) NOT NULL,
  content varchar(255) NOT NULL,
  params varchar(255) NOT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  api_template_id varchar(63) NOT NULL,
  channel_id int8 NOT NULL,
  channel_code varchar(63) NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_sms_template ADD CONSTRAINT pk_system_sms_template PRIMARY KEY (id);

COMMENT ON COLUMN system_sms_template.id IS '编号';
COMMENT ON COLUMN system_sms_template.type IS '模板类型';
COMMENT ON COLUMN system_sms_template.status IS '开启状态';
COMMENT ON COLUMN system_sms_template.code IS '模板编码';
COMMENT ON COLUMN system_sms_template.name IS '模板名称';
COMMENT ON COLUMN system_sms_template.content IS '模板内容';
COMMENT ON COLUMN system_sms_template.params IS '参数数组';
COMMENT ON COLUMN system_sms_template.remark IS '备注';
COMMENT ON COLUMN system_sms_template.api_template_id IS '短信 API 的模板编号';
COMMENT ON COLUMN system_sms_template.channel_id IS '短信渠道编号';
COMMENT ON COLUMN system_sms_template.channel_code IS '短信渠道编码';
COMMENT ON COLUMN system_sms_template.creator IS '创建者';
COMMENT ON COLUMN system_sms_template.create_time IS '创建时间';
COMMENT ON COLUMN system_sms_template.updater IS '更新者';
COMMENT ON COLUMN system_sms_template.update_time IS '更新时间';
COMMENT ON COLUMN system_sms_template.deleted IS '是否删除';
COMMENT ON TABLE system_sms_template IS '短信模板';

-- ----------------------------
-- Records of system_sms_template
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_sms_template  VALUES (2, 1, 0, 'test_01', '测试验证码短信', '正在进行登录操作{operation}，您的验证码是{code}', '["operation","code"]', '测试备注', '4383920', 4, 'DEBUG_DING_TALK', '', '2021-03-31 10:49:38', '1', '2024-08-18 11:57:18', '0');
INSERT INTO system_sms_template  VALUES (3, 1, 0, 'test_02', '公告通知', '您的验证码{code}，该验证码5分钟内有效，请勿泄漏于他人！', '["code"]', NULL, 'SMS_207945135', 2, 'ALIYUN', '', '2021-03-31 11:56:30', '1', '2021-04-10 01:22:02', '0');
INSERT INTO system_sms_template  VALUES (6, 3, 0, 'test-01', '测试模板', '哈哈哈 {name}', '["name"]', 'f哈哈哈', '4383920', 4, 'DEBUG_DING_TALK', '1', '2021-04-10 01:07:21', '1', '2024-08-18 11:57:07', '0');
INSERT INTO system_sms_template  VALUES (7, 3, 0, 'test-04', '测试下', '老鸡{name}，牛逼{code}', '["name","code"]', '哈哈哈哈', 'suibian', 7, 'DEBUG_DING_TALK', '1', '2021-04-13 00:29:53', '1', '2024-09-30 00:56:24', '0');
INSERT INTO system_sms_template  VALUES (8, 1, 0, 'user-sms-login', '前台用户短信登录', '您的验证码是{code}', '["code"]', NULL, '4372216', 4, 'DEBUG_DING_TALK', '1', '2021-10-11 08:10:00', '1', '2024-08-18 11:57:06', '0');
INSERT INTO system_sms_template  VALUES (9, 2, 0, 'bpm_task_assigned', '【工作流】任务被分配', '您收到了一条新的待办任务：{processInstanceName}-{taskName}，申请人：{startUserNickname}，处理链接：{detailUrl}', '["processInstanceName","taskName","startUserNickname","detailUrl"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-21 22:31:19', '1', '2022-01-22 00:03:36', '0');
INSERT INTO system_sms_template  VALUES (10, 2, 0, 'bpm_process_instance_reject', '【工作流】流程被不通过', '您的流程被审批不通过：{processInstanceName}，原因：{reason}，查看链接：{detailUrl}', '["processInstanceName","reason","detailUrl"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-22 00:03:31', '1', '2022-05-01 12:33:14', '0');
INSERT INTO system_sms_template  VALUES (11, 2, 0, 'bpm_process_instance_approve', '【工作流】流程被通过', '您的流程被审批通过：{processInstanceName}，查看链接：{detailUrl}', '["processInstanceName","detailUrl"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-22 00:04:31', '1', '2022-03-27 20:32:21', '0');
INSERT INTO system_sms_template  VALUES (12, 2, 0, 'demo', '演示模板', '我就是测试一下下', '[]', NULL, 'biubiubiu', 4, 'DEBUG_DING_TALK', '1', '2022-04-10 23:22:49', '1', '2024-08-18 11:57:04', '0');
INSERT INTO system_sms_template  VALUES (14, 1, 0, 'user-update-mobile', '会员用户 - 修改手机', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '["code"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-08-19 11:34:04', '0');
INSERT INTO system_sms_template  VALUES (15, 1, 0, 'user-update-password', '会员用户 - 修改密码', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '["code"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-08-19 11:34:18', '0');
INSERT INTO system_sms_template  VALUES (16, 1, 0, 'user-reset-password', '会员用户 - 重置密码', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '["code"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-12-02 22:35:27', '0');
INSERT INTO system_sms_template  VALUES (17, 2, 0, 'bpm_task_timeout', '【工作流】任务审批超时', '您收到了一条超时的待办任务：{processInstanceName}-{taskName}，处理链接：{detailUrl}', '["processInstanceName","taskName","detailUrl"]', '', 'X', 4, 'DEBUG_DING_TALK', '1', '2024-08-16 21:59:15', '1', '2024-08-16 21:59:34', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_sms_template_seq;
CREATE SEQUENCE system_sms_template_seq
    START 18;

-- ----------------------------
-- Table structure for system_social_client
-- ----------------------------
DROP TABLE IF EXISTS system_social_client;
CREATE TABLE system_social_client (
    id int8 NOT NULL,
  name varchar(255) NOT NULL,
  social_type int2 NOT NULL,
  user_type int2 NOT NULL,
  client_id varchar(255) NOT NULL,
  client_secret varchar(255) NOT NULL,
  agent_id varchar(255) NULL DEFAULT NULL,
  status int2 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_social_client ADD CONSTRAINT pk_system_social_client PRIMARY KEY (id);

COMMENT ON COLUMN system_social_client.id IS '编号';
COMMENT ON COLUMN system_social_client.name IS '应用名';
COMMENT ON COLUMN system_social_client.social_type IS '社交平台的类型';
COMMENT ON COLUMN system_social_client.user_type IS '用户类型';
COMMENT ON COLUMN system_social_client.client_id IS '客户端编号';
COMMENT ON COLUMN system_social_client.client_secret IS '客户端密钥';
COMMENT ON COLUMN system_social_client.agent_id IS '代理编号';
COMMENT ON COLUMN system_social_client.status IS '状态';
COMMENT ON COLUMN system_social_client.creator IS '创建者';
COMMENT ON COLUMN system_social_client.create_time IS '创建时间';
COMMENT ON COLUMN system_social_client.updater IS '更新者';
COMMENT ON COLUMN system_social_client.update_time IS '更新时间';
COMMENT ON COLUMN system_social_client.deleted IS '是否删除';
COMMENT ON COLUMN system_social_client.tenant_id IS '租户编号';
COMMENT ON TABLE system_social_client IS '社交客户端表';

-- ----------------------------
-- Records of system_social_client
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_social_client  VALUES (1, '钉钉', 20, 2, 'dingvrnreaje3yqvzhxg', 'i8E6iZyDvZj51JIb0tYsYfVQYOks9Cq1lgryEjFRqC79P3iJcrxEwT6Qk2QvLrLI', NULL, 0, '', '2023-10-18 11:21:18', '1', '2023-12-20 21:28:26', '1', 1);
INSERT INTO system_social_client  VALUES (2, '钉钉（王土豆）', 20, 2, 'dingtsu9hpepjkbmthhw', 'FP_bnSq_HAHKCSncmJjw5hxhnzs6vaVDSZZn3egj6rdqTQ_hu5tQVJyLMpgCakdP', NULL, 0, '', '2023-10-18 11:21:18', '', '2023-12-20 21:28:26', '1', 121);
INSERT INTO system_social_client  VALUES (3, '微信公众号', 31, 1, 'wx5b23ba7a5589ecbb', '2a7b3b20c537e52e74afd395eb85f61f', NULL, 0, '', '2023-10-18 16:07:46', '1', '2023-12-20 21:28:23', '1', 1);
INSERT INTO system_social_client  VALUES (43, '微信小程序', 34, 1, 'wx63c280fe3248a3e7', '6f270509224a7ae1296bbf1c8cb97aed', NULL, 0, '', '2023-10-19 13:37:41', '1', '2023-12-20 21:28:25', '1', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_social_client_seq;
CREATE SEQUENCE system_social_client_seq
    START 44;

-- ----------------------------
-- Table structure for system_social_user
-- ----------------------------
DROP TABLE IF EXISTS system_social_user;
CREATE TABLE system_social_user (
    id int8 NOT NULL,
  type int2 NOT NULL,
  openid varchar(32) NOT NULL,
  token varchar(256) NULL DEFAULT NULL,
  raw_token_info varchar(1024) NOT NULL,
  nickname varchar(32) NOT NULL,
  avatar varchar(255) NULL DEFAULT NULL,
  raw_user_info varchar(1024) NOT NULL,
  code varchar(256) NOT NULL,
  state varchar(256) NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_social_user ADD CONSTRAINT pk_system_social_user PRIMARY KEY (id);

COMMENT ON COLUMN system_social_user.id IS '主键 ( 自增策略)';
COMMENT ON COLUMN system_social_user.type IS '社交平台的类型';
COMMENT ON COLUMN system_social_user.openid IS '社交 openid';
COMMENT ON COLUMN system_social_user.token IS '社交 token';
COMMENT ON COLUMN system_social_user.raw_token_info IS '原始 Token 数据，一般是 JSON 格式';
COMMENT ON COLUMN system_social_user.nickname IS '用户昵称';
COMMENT ON COLUMN system_social_user.avatar IS '用户头像';
COMMENT ON COLUMN system_social_user.raw_user_info IS '原始用户数据，一般是 JSON 格式';
COMMENT ON COLUMN system_social_user.code IS '最后一次的认证 code';
COMMENT ON COLUMN system_social_user.state IS '最后一次的认证 state';
COMMENT ON COLUMN system_social_user.creator IS '创建者';
COMMENT ON COLUMN system_social_user.create_time IS '创建时间';
COMMENT ON COLUMN system_social_user.updater IS '更新者';
COMMENT ON COLUMN system_social_user.update_time IS '更新时间';
COMMENT ON COLUMN system_social_user.deleted IS '是否删除';
COMMENT ON COLUMN system_social_user.tenant_id IS '租户编号';
COMMENT ON TABLE system_social_user IS '社交用户表';

DROP SEQUENCE IF EXISTS system_social_user_seq;
CREATE SEQUENCE system_social_user_seq
    START 1;

-- ----------------------------
-- Table structure for system_social_user_bind
-- ----------------------------
DROP TABLE IF EXISTS system_social_user_bind;
CREATE TABLE system_social_user_bind (
    id int8 NOT NULL,
  user_id int8 NOT NULL,
  user_type int2 NOT NULL,
  social_type int2 NOT NULL,
  social_user_id int8 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_social_user_bind ADD CONSTRAINT pk_system_social_user_bind PRIMARY KEY (id);

COMMENT ON COLUMN system_social_user_bind.id IS '主键 ( 自增策略)';
COMMENT ON COLUMN system_social_user_bind.user_id IS '用户编号';
COMMENT ON COLUMN system_social_user_bind.user_type IS '用户类型';
COMMENT ON COLUMN system_social_user_bind.social_type IS '社交平台的类型';
COMMENT ON COLUMN system_social_user_bind.social_user_id IS '社交用户的编号';
COMMENT ON COLUMN system_social_user_bind.creator IS '创建者';
COMMENT ON COLUMN system_social_user_bind.create_time IS '创建时间';
COMMENT ON COLUMN system_social_user_bind.updater IS '更新者';
COMMENT ON COLUMN system_social_user_bind.update_time IS '更新时间';
COMMENT ON COLUMN system_social_user_bind.deleted IS '是否删除';
COMMENT ON COLUMN system_social_user_bind.tenant_id IS '租户编号';
COMMENT ON TABLE system_social_user_bind IS '社交绑定表';

DROP SEQUENCE IF EXISTS system_social_user_bind_seq;
CREATE SEQUENCE system_social_user_bind_seq
    START 1;

-- ----------------------------
-- Table structure for system_tenant
-- ----------------------------
DROP TABLE IF EXISTS system_tenant;
CREATE TABLE system_tenant (
    id int8 NOT NULL,
  name varchar(30) NOT NULL,
  contact_user_id int8 NULL DEFAULT NULL,
  contact_name varchar(30) NOT NULL,
  contact_mobile varchar(500) NULL DEFAULT NULL,
  status int2 NOT NULL DEFAULT 0,
  website varchar(256) NULL DEFAULT '',
  package_id int8 NOT NULL,
  expire_time timestamp NOT NULL,
  account_count int4 NOT NULL,
  creator varchar(64) NOT NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_tenant ADD CONSTRAINT pk_system_tenant PRIMARY KEY (id);

COMMENT ON COLUMN system_tenant.id IS '租户编号';
COMMENT ON COLUMN system_tenant.name IS '租户名';
COMMENT ON COLUMN system_tenant.contact_user_id IS '联系人的用户编号';
COMMENT ON COLUMN system_tenant.contact_name IS '联系人';
COMMENT ON COLUMN system_tenant.contact_mobile IS '联系手机';
COMMENT ON COLUMN system_tenant.status IS '租户状态（0正常 1停用）';
COMMENT ON COLUMN system_tenant.website IS '绑定域名';
COMMENT ON COLUMN system_tenant.package_id IS '租户套餐编号';
COMMENT ON COLUMN system_tenant.expire_time IS '过期时间';
COMMENT ON COLUMN system_tenant.account_count IS '账号数量';
COMMENT ON COLUMN system_tenant.creator IS '创建者';
COMMENT ON COLUMN system_tenant.create_time IS '创建时间';
COMMENT ON COLUMN system_tenant.updater IS '更新者';
COMMENT ON COLUMN system_tenant.update_time IS '更新时间';
COMMENT ON COLUMN system_tenant.deleted IS '是否删除';
COMMENT ON TABLE system_tenant IS '租户表';

-- ----------------------------
-- Records of system_tenant
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_tenant  VALUES (1, 'EnjoyIot', NULL, '小E', '17321315478', 0, 'www.enjoy-iot.cn', 0, '2099-02-19 17:14:16', 9999, '1', '2021-01-05 17:03:47', '1', '2025-02-19 14:23:54', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_tenant_seq;
CREATE SEQUENCE system_tenant_seq
    START 2;

-- ----------------------------
-- Table structure for system_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS system_tenant_package;
CREATE TABLE system_tenant_package (
    id int8 NOT NULL,
  name varchar(30) NOT NULL,
  status int2 NOT NULL DEFAULT 0,
  remark varchar(256) NULL DEFAULT '',
  menu_ids varchar(4096) NOT NULL,
  creator varchar(64) NOT NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0
);

ALTER TABLE system_tenant_package ADD CONSTRAINT pk_system_tenant_package PRIMARY KEY (id);

COMMENT ON COLUMN system_tenant_package.id IS '套餐编号';
COMMENT ON COLUMN system_tenant_package.name IS '套餐名';
COMMENT ON COLUMN system_tenant_package.status IS '租户状态（0正常 1停用）';
COMMENT ON COLUMN system_tenant_package.remark IS '备注';
COMMENT ON COLUMN system_tenant_package.menu_ids IS '关联的菜单编号';
COMMENT ON COLUMN system_tenant_package.creator IS '创建者';
COMMENT ON COLUMN system_tenant_package.create_time IS '创建时间';
COMMENT ON COLUMN system_tenant_package.updater IS '更新者';
COMMENT ON COLUMN system_tenant_package.update_time IS '更新时间';
COMMENT ON COLUMN system_tenant_package.deleted IS '是否删除';
COMMENT ON TABLE system_tenant_package IS '租户套餐表';

-- ----------------------------
-- Records of system_tenant_package
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_tenant_package  VALUES (111, '普通套餐', 0, '小功能', '[1,2,5,1031,1032,1033,1034,1035,1036,1037,1038,1039,1050,1051,1052,1053,1054,1056,1057,1058,1059,1060,1063,1064,1065,1066,1067,1070,1075,1077,1078,1082,1083,1084,1085,1086,1087,1088,1089,1090,1091,1092,1118,1119,1120,100,101,102,103,106,107,110,111,112,113,1138,114,1139,115,1140,116,1141,1142,1143,2713,2714,2715,2716,2717,2718,2720,1185,2721,1186,2722,1187,2723,1188,2724,1189,2725,1190,2726,1191,2727,2472,1192,2728,1193,2729,1194,2730,1195,2731,1196,2732,1197,2733,2478,1198,2734,2479,1199,2735,2480,1200,2481,1201,2482,1202,2483,2739,2484,2740,2485,2486,2487,1207,2488,1208,2489,1209,2490,1210,2491,1211,2492,1212,2493,1213,2494,2495,1215,1216,2497,1217,1218,1219,1220,1221,1222,1224,1225,1226,1227,1228,1229,1237,1238,1239,1240,1241,1242,1243,2525,1255,1256,1001,1257,1002,1258,1003,1259,1004,1260,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020]', '1', '2022-02-22 00:54:00', '1', '2024-07-13 22:37:24', '0');
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_tenant_package_seq;
CREATE SEQUENCE system_tenant_package_seq
    START 112;

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS system_user_post;
CREATE TABLE system_user_post (
    id int8 NOT NULL,
  user_id int8 NOT NULL DEFAULT 0,
  post_id int8 NOT NULL DEFAULT 0,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_user_post ADD CONSTRAINT pk_system_user_post PRIMARY KEY (id);

COMMENT ON COLUMN system_user_post.id IS 'id';
COMMENT ON COLUMN system_user_post.user_id IS '用户ID';
COMMENT ON COLUMN system_user_post.post_id IS '岗位ID';
COMMENT ON COLUMN system_user_post.creator IS '创建者';
COMMENT ON COLUMN system_user_post.create_time IS '创建时间';
COMMENT ON COLUMN system_user_post.updater IS '更新者';
COMMENT ON COLUMN system_user_post.update_time IS '更新时间';
COMMENT ON COLUMN system_user_post.deleted IS '是否删除';
COMMENT ON COLUMN system_user_post.tenant_id IS '租户编号';
COMMENT ON TABLE system_user_post IS '用户岗位表';

-- ----------------------------
-- Records of system_user_post
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_user_post  VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', '0', 1);
INSERT INTO system_user_post  VALUES (113, 100, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', '0', 1);
INSERT INTO system_user_post  VALUES (115, 104, 1, '1', '2022-05-16 19:36:28', '1', '2022-05-16 19:36:28', '0', 1);
INSERT INTO system_user_post  VALUES (116, 117, 2, '1', '2022-07-09 17:40:26', '1', '2022-07-09 17:40:26', '0', 1);
INSERT INTO system_user_post  VALUES (117, 118, 1, '1', '2022-07-09 17:44:44', '1', '2022-07-09 17:44:44', '0', 1);
INSERT INTO system_user_post  VALUES (119, 114, 5, '1', '2024-03-24 20:45:51', '1', '2024-03-24 20:45:51', '0', 1);
INSERT INTO system_user_post  VALUES (123, 115, 1, '1', '2024-04-04 09:37:14', '1', '2024-04-04 09:37:14', '0', 1);
INSERT INTO system_user_post  VALUES (124, 115, 2, '1', '2024-04-04 09:37:14', '1', '2024-04-04 09:37:14', '0', 1);
INSERT INTO system_user_post  VALUES (125, 1, 2, '1', '2024-07-13 22:31:39', '1', '2024-07-13 22:31:39', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_user_post_seq;
CREATE SEQUENCE system_user_post_seq
    START 126;

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS system_user_role;
CREATE TABLE system_user_role (
    id int8 NOT NULL,
  user_id int8 NOT NULL,
  role_id int8 NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_user_role ADD CONSTRAINT pk_system_user_role PRIMARY KEY (id);

COMMENT ON COLUMN system_user_role.id IS '自增编号';
COMMENT ON COLUMN system_user_role.user_id IS '用户ID';
COMMENT ON COLUMN system_user_role.role_id IS '角色ID';
COMMENT ON COLUMN system_user_role.creator IS '创建者';
COMMENT ON COLUMN system_user_role.create_time IS '创建时间';
COMMENT ON COLUMN system_user_role.updater IS '更新者';
COMMENT ON COLUMN system_user_role.update_time IS '更新时间';
COMMENT ON COLUMN system_user_role.deleted IS '是否删除';
COMMENT ON COLUMN system_user_role.tenant_id IS '租户编号';
COMMENT ON TABLE system_user_role IS '用户和角色关联表';

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_user_role  VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:17', '0', 1);
INSERT INTO system_user_role  VALUES (2, 2, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', '0', 1);
INSERT INTO system_user_role  VALUES (4, 100, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', '0', 1);
INSERT INTO system_user_role  VALUES (5, 100, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:12', '0', 1);
INSERT INTO system_user_role  VALUES (6, 100, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', '0', 1);
INSERT INTO system_user_role  VALUES (10, 103, 1, '1', '2022-01-11 13:19:45', '1', '2022-01-11 13:19:45', '0', 1);
INSERT INTO system_user_role  VALUES (14, 110, 109, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', '0', 121);
INSERT INTO system_user_role  VALUES (15, 111, 110, '110', '2022-02-23 13:14:38', '110', '2022-02-23 13:14:38', '0', 121);
INSERT INTO system_user_role  VALUES (16, 113, 111, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', '0', 122);
INSERT INTO system_user_role  VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2022-05-12 20:39:29', '0', 1);
INSERT INTO system_user_role  VALUES (20, 104, 101, '1', '2022-05-28 15:43:57', '1', '2025-02-23 20:11:30', '1', 1);
INSERT INTO system_user_role  VALUES (22, 115, 2, '1', '2022-07-21 22:08:30', '1', '2022-07-21 22:08:30', '0', 1);
INSERT INTO system_user_role  VALUES (35, 112, 1, '1', '2024-03-15 20:00:24', '1', '2024-03-15 20:00:24', '0', 1);
INSERT INTO system_user_role  VALUES (36, 118, 1, '1', '2024-03-17 09:12:08', '1', '2024-03-17 09:12:08', '0', 1);
INSERT INTO system_user_role  VALUES (38, 114, 101, '1', '2024-03-24 22:23:03', '1', '2024-03-24 22:23:03', '0', 1);
INSERT INTO system_user_role  VALUES (46, 117, 1, '1', '2024-10-02 10:16:11', '1', '2024-10-02 10:16:11', '0', 1);
INSERT INTO system_user_role  VALUES (47, 104, 154, '1', '2025-02-23 20:11:31', '1', '2025-02-23 20:11:31', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_user_role_seq;
CREATE SEQUENCE system_user_role_seq
    START 48;

-- ----------------------------
-- Table structure for system_users
-- ----------------------------
DROP TABLE IF EXISTS system_users;
CREATE TABLE system_users (
    id int8 NOT NULL,
  username varchar(30) NOT NULL,
  password varchar(100) NOT NULL DEFAULT '',
  nickname varchar(30) NOT NULL,
  remark varchar(500) NULL DEFAULT NULL,
  dept_id int8 NULL DEFAULT NULL,
  post_ids varchar(255) NULL DEFAULT NULL,
  email varchar(50) NULL DEFAULT '',
  mobile varchar(11) NULL DEFAULT '',
  sex int2 NULL DEFAULT 0,
  avatar varchar(512) NULL DEFAULT '',
  status int2 NOT NULL DEFAULT 0,
  login_ip varchar(50) NULL DEFAULT '',
  login_date timestamp NULL DEFAULT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0
);

ALTER TABLE system_users ADD CONSTRAINT pk_system_users PRIMARY KEY (id);

COMMENT ON COLUMN system_users.id IS '用户ID';
COMMENT ON COLUMN system_users.username IS '用户账号';
COMMENT ON COLUMN system_users.password IS '密码';
COMMENT ON COLUMN system_users.nickname IS '用户昵称';
COMMENT ON COLUMN system_users.remark IS '备注';
COMMENT ON COLUMN system_users.dept_id IS '部门ID';
COMMENT ON COLUMN system_users.post_ids IS '岗位编号数组';
COMMENT ON COLUMN system_users.email IS '用户邮箱';
COMMENT ON COLUMN system_users.mobile IS '手机号码';
COMMENT ON COLUMN system_users.sex IS '用户性别';
COMMENT ON COLUMN system_users.avatar IS '头像地址';
COMMENT ON COLUMN system_users.status IS '帐号状态（0正常 1停用）';
COMMENT ON COLUMN system_users.login_ip IS '最后登录IP';
COMMENT ON COLUMN system_users.login_date IS '最后登录时间';
COMMENT ON COLUMN system_users.creator IS '创建者';
COMMENT ON COLUMN system_users.create_time IS '创建时间';
COMMENT ON COLUMN system_users.updater IS '更新者';
COMMENT ON COLUMN system_users.update_time IS '更新时间';
COMMENT ON COLUMN system_users.deleted IS '是否删除';
COMMENT ON COLUMN system_users.tenant_id IS '租户编号';
COMMENT ON TABLE system_users IS '用户信息表';

-- ----------------------------
-- Records of system_users
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO system_users  VALUES (1, 'admin', '$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm', 'EnjoyIot', '管理员', 103, '[1,2]', 'xw2sy@163.com', '19918996474', 1, 'http://127.0.0.1:48080/admin-api/infra/file/29/get/ed16f2223a3b2999b491dcf8fcf989e7342ae9d73e6bb419c0e4084f1c20c6d5.png', 0, '27.38.247.37', '2025-02-23 21:26:47', 'admin', '2021-01-05 17:03:47', NULL, '2025-02-23 21:26:47', '0', 1);
INSERT INTO system_users  VALUES (104, 'test', '$2a$04$KG9vBvKLO5PP9Tfd8ttF0.CDObtXPhMQb8I7vPEdVBSuI7uOx51Ey', '测试号', NULL, 107, '[1,2]', '111@qq.com', '15601691200', 1, '', 0, '27.38.247.37', '2025-02-23 21:25:34', '', '2021-01-21 02:13:53', NULL, '2025-02-23 21:25:34', '0', 1);
COMMIT;
-- @formatter:on

DROP SEQUENCE IF EXISTS system_users_seq;
CREATE SEQUENCE system_users_seq
    START 105;

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS task_info;
CREATE TABLE task_info (
    id int8 NOT NULL,
  name varchar(255) NOT NULL,
  actions text NOT NULL,
  state varchar(32) NOT NULL,
  type varchar(255) NOT NULL,
  creator varchar(64) NULL DEFAULT '',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updater varchar(64) NULL DEFAULT '',
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted int2 NOT NULL DEFAULT 0,
  tenant_id int8 NOT NULL DEFAULT 0,
  dept_id int8 NULL DEFAULT 0,
  expression varchar(255) NOT NULL,
  remark varchar(255) NULL DEFAULT NULL
);

ALTER TABLE task_info ADD CONSTRAINT pk_task_info PRIMARY KEY (id);

COMMENT ON COLUMN task_info.id IS '任务id';
COMMENT ON COLUMN task_info.name IS '任务名称';
COMMENT ON COLUMN task_info.actions IS '任务输出';
COMMENT ON COLUMN task_info.state IS '状态';
COMMENT ON COLUMN task_info.type IS '任务类型';
COMMENT ON COLUMN task_info.creator IS '创建者';
COMMENT ON COLUMN task_info.create_time IS '创建时间';
COMMENT ON COLUMN task_info.updater IS '更新者';
COMMENT ON COLUMN task_info.update_time IS '更新时间';
COMMENT ON COLUMN task_info.deleted IS '是否删除';
COMMENT ON COLUMN task_info.tenant_id IS '租户编号';
COMMENT ON COLUMN task_info.dept_id IS '机构id';
COMMENT ON COLUMN task_info.expression IS '表达式';
COMMENT ON COLUMN task_info.remark IS '备注';
COMMENT ON TABLE task_info IS 'iot任务';

DROP SEQUENCE IF EXISTS task_info_seq;
CREATE SEQUENCE task_info_seq
    START 1;

