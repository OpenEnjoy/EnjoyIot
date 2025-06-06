package com.enjoyiot.eiot.temporal.iotdb.service;


import com.enjoyiot.eiot.IDbStructureData;
import com.enjoyiot.eiot.temporal.iotdb.config.Constants;
import com.enjoyiot.eiot.temporal.iotdb.config.IotdbDatasourceConfig;
import com.enjoyiot.module.eiot.api.thingmodel.dto.ThingModel;
import org.apache.iotdb.isession.SessionDataSet;
import org.apache.iotdb.isession.pool.SessionDataSetWrapper;
import org.apache.iotdb.isession.template.Template;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.session.template.MeasurementNode;
import org.apache.iotdb.tsfile.file.metadata.enums.CompressionType;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.file.metadata.enums.TSEncoding;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class DbStructureDataImpl implements IDbStructureData {

    @Resource
    private IotdbDatasourceConfig config;
    @Resource
    protected SessionPool sessionPool;

    /**
     * 定义物模型，根据物模型定义表
     */
    @Override
    public void defineThingModel(ThingModel thingModel) {
        //todo iotdb不支持模版更新 不设置模板也可使用 暂不实现
    }

    /**
     * 更新物模型定义
     */
    @Override
    public void updateThingModel(ThingModel thingModel) {
        //todo iotdb不支持模版更新 不设置模板也可使用 暂不实现
    }

    /**
     * 初始化数据库结构
     */
    @Override
    @PostConstruct
    public void initDbStructure() {
        try {
            String databaseName = config.getBaseDb();
            try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement("show databases " + databaseName);
                 SessionDataSet dataSet = wrapper.getSessionDataSet()) {
                if (!dataSet.hasNext()) {
                    sessionPool.createDatabase(databaseName);
                }
            }
            try (SessionDataSetWrapper wrapper = sessionPool.executeQueryStatement("show device templates");
                 SessionDataSet dataSet = wrapper.getSessionDataSet()) {
                Set<String> templateNameSet = new HashSet<>();
                while (dataSet.hasNext()) {
                    RowRecord rowRecord = dataSet.next();
                    String templateName = rowRecord.getFields().get(0).getStringValue();
                    templateNameSet.add(templateName);
                }
                String thingModelMessageTemplateName = Constants.THING_MODEL_MESSAGE_PREFIX;
                if (!templateNameSet.contains(thingModelMessageTemplateName)) {
                    Template thingModelMessageTemplate = new Template(thingModelMessageTemplateName);
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("mid", TSDataType.TEXT, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("product_key", TSDataType.TEXT, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("device_name", TSDataType.TEXT, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("uid", TSDataType.TEXT, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("type", TSDataType.TEXT, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("identifier", TSDataType.TEXT, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("code", TSDataType.INT32, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("data", TSDataType.TEXT, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    thingModelMessageTemplate.addToTemplate(new MeasurementNode("report_time", TSDataType.INT64, TSEncoding.PLAIN, CompressionType.SNAPPY));
                    sessionPool.createSchemaTemplate(thingModelMessageTemplate);
                }
                sessionPool.setSchemaTemplate(thingModelMessageTemplateName, databaseName + "." + thingModelMessageTemplateName);
            }


        } catch (StatementExecutionException e) {
            e.printStackTrace();
        } catch (IoTDBConnectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
