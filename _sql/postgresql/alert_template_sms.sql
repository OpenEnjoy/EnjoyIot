
-- 增加短信告警模板
alter table eiot_channel_template add column status int4 default 1;
alter table eiot_channel_template add column template_code varchar(128);
comment on column eiot_channel_template.status is '状态 0-待审核 1-审核成功 2-审核失败';
comment on column eiot_channel_template.template_code is '模板编号';

INSERT INTO channel VALUES (4, 'SMS', NULL, '短信', '', '2025-02-06 21:43:55', '', '2025-02-07 20:53:03', '0', 1, 0);
INSERT INTO channel VALUES (5, 'VMS', NULL, '语音', '', '2025-02-06 21:43:55', '', '2025-02-07 20:53:03', '0', 1, 0);