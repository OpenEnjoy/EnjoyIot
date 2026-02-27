-- =====================================================
-- 设备影子表 - PostgreSQL 版本
-- =====================================================

-- 设备影子表
CREATE TABLE IF NOT EXISTS eiot_device_shadow (
    id BIGSERIAL PRIMARY KEY,
    device_id BIGINT NOT NULL,
    product_key VARCHAR(64) NOT NULL,
    dn VARCHAR(64) NOT NULL,
    
    -- 影子状态（JSON格式）
    desired TEXT,
    reported TEXT,
    metadata TEXT,
    
    -- 版本控制
    version BIGINT NOT NULL DEFAULT 0,
    
    -- 时间戳
    last_desired_time TIMESTAMP,
    last_reported_time TIMESTAMP,
    
    -- 租户和审计字段
    tenant_id BIGINT NOT NULL DEFAULT 0,
    creator VARCHAR(64) DEFAULT '',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updater VARCHAR(64) DEFAULT '',
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted SMALLINT NOT NULL DEFAULT 0,
    
    CONSTRAINT uk_device_id UNIQUE (device_id)
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_device_shadow_product_key ON eiot_device_shadow(product_key);
CREATE INDEX IF NOT EXISTS idx_device_shadow_tenant_id ON eiot_device_shadow(tenant_id);
CREATE INDEX IF NOT EXISTS idx_device_shadow_create_time ON eiot_device_shadow(create_time);

-- 添加注释
COMMENT ON TABLE eiot_device_shadow IS '设备影子表';
COMMENT ON COLUMN eiot_device_shadow.id IS '主键ID';
COMMENT ON COLUMN eiot_device_shadow.device_id IS '设备ID';
COMMENT ON COLUMN eiot_device_shadow.product_key IS '产品Key';
COMMENT ON COLUMN eiot_device_shadow.dn IS '设备唯一标识';
COMMENT ON COLUMN eiot_device_shadow.desired IS '期望状态JSON - 应用程序期望设备达到的状态';
COMMENT ON COLUMN eiot_device_shadow.reported IS '上报状态JSON - 设备实际上报的状态';
COMMENT ON COLUMN eiot_device_shadow.metadata IS '元数据JSON - 包含每个属性的时间戳';
COMMENT ON COLUMN eiot_device_shadow.version IS '影子版本号';
COMMENT ON COLUMN eiot_device_shadow.last_desired_time IS '最后期望状态更新时间';
COMMENT ON COLUMN eiot_device_shadow.last_reported_time IS '最后上报状态更新时间';
COMMENT ON COLUMN eiot_device_shadow.tenant_id IS '租户ID';
COMMENT ON COLUMN eiot_device_shadow.creator IS '创建者';
COMMENT ON COLUMN eiot_device_shadow.create_time IS '创建时间';
COMMENT ON COLUMN eiot_device_shadow.updater IS '更新者';
COMMENT ON COLUMN eiot_device_shadow.update_time IS '更新时间';
COMMENT ON COLUMN eiot_device_shadow.deleted IS '是否删除(0-未删除 1-已删除)';

