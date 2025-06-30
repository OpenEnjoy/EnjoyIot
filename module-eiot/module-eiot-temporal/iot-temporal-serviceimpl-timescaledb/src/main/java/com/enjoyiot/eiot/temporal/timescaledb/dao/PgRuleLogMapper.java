package com.enjoyiot.eiot.temporal.timescaledb.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.enjoyiot.eiot.temporal.timescaledb.model.PgRuleLog;
import com.enjoyiot.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

@DS("timescaledb")
@Mapper
public interface PgRuleLogMapper extends BaseMapperX<PgRuleLog> {
}
