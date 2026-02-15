-- =====================================================
-- 设备影子表 - MySQL 版本
-- =====================================================

-- 设备影子表
CREATE TABLE IF NOT EXISTS `eiot_device_shadow` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `device_id` BIGINT(20) NOT NULL COMMENT '设备ID',
    `product_key` VARCHAR(64) NOT NULL COMMENT '产品Key',
    `dn` VARCHAR(64) NOT NULL COMMENT '设备唯一标识',
    
    -- 影子状态（JSON格式）
    `desired` TEXT COMMENT '期望状态JSON - 应用程序期望设备达到的状态',
    `reported` TEXT COMMENT '上报状态JSON - 设备实际上报的状态',
    `metadata` TEXT COMMENT '元数据JSON - 包含每个属性的时间戳',
    
    -- 版本控制
    `version` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '影子版本号',
    
    -- 时间戳
    `last_desired_time` DATETIME COMMENT '最后期望状态更新时间',
    `last_reported_time` DATETIME COMMENT '最后上报状态更新时间',
    
    -- 租户和审计字段
    `tenant_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_device_id` (`device_id`),
    KEY `idx_product_key` (`product_key`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备影子表';
