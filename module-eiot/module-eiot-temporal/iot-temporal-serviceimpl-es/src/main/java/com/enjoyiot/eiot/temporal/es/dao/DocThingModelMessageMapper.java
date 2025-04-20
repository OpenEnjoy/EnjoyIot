package com.enjoyiot.eiot.temporal.es.dao;

import com.enjoyiot.eiot.temporal.es.document.DocThingModelMessage;
import org.dromara.easyes.core.kernel.BaseEsMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface DocThingModelMessageMapper extends BaseEsMapper<DocThingModelMessage> {
}
