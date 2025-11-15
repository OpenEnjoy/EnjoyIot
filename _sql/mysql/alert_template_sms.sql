
-- 增加短信告警模板
ALTER TABLE eiot_channel_template ADD COLUMN status INT DEFAULT 1 COMMENT '状态 0-待审核 1-审核成功 2-审核失败';
ALTER TABLE eiot_channel_template ADD COLUMN template_code VARCHAR(128) COMMENT '模板编号';

INSERT INTO channel VALUES (4, 'SMS', NULL, '短信', '', '2025-02-06 21:43:55', '', '2025-02-07 20:53:03', '0', 1, 0);
INSERT INTO channel VALUES (5, 'VMS', NULL, '语音', '', '2025-02-06 21:43:55', '', '2025-02-07 20:53:03', '0', 1, 0);