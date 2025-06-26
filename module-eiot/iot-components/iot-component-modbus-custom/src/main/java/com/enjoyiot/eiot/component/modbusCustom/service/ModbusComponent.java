
package com.enjoyiot.eiot.component.modbusCustom.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import com.digitalpetri.modbus.ModbusPdu;
import com.digitalpetri.modbus.codec.MbapHeader;
import com.digitalpetri.modbus.responses.ReadCoilsResponse;
import com.digitalpetri.modbus.responses.ReadDiscreteInputsResponse;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.digitalpetri.modbus.responses.ReadInputRegistersResponse;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.common.utils.HexUtil;
import com.enjoyiot.eiot.component.core.ComponentServices;
import com.enjoyiot.eiot.component.core.ThingComponent;
import com.enjoyiot.eiot.component.core.model.down.DeviceConfig;
import com.enjoyiot.eiot.component.core.model.down.PropertyGet;
import com.enjoyiot.eiot.component.core.model.down.PropertySet;
import com.enjoyiot.eiot.component.core.model.down.ServiceInvoke;
import com.enjoyiot.eiot.component.core.model.up.DeviceStateChange;
import com.enjoyiot.eiot.component.core.model.up.PropertyReport;
import com.enjoyiot.eiot.component.modbusCustom.cilent.VertxModbusClient;
import com.enjoyiot.eiot.component.modbusCustom.model.ModbusConfig;
import com.enjoyiot.eiot.component.modbusCustom.parser.*;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.RegisterDevice;
import com.enjoyiot.module.eiot.api.modbus.ModbusInfoApi;
import com.enjoyiot.module.eiot.api.modbus.ModbusThingModelApi;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusThingModel;
import com.enjoyiot.module.eiot.api.product.ProductApi;
import io.netty.buffer.ByteBuf;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

@Slf4j
@Component
public class ModbusComponent extends ThingComponent implements Handler<NetSocket> {

    private final Map<String, VertxModbusClient> clientMap = new ConcurrentHashMap<>();

    private final Map<String, DeviceInfo> dnToDevice = new ConcurrentHashMap<>();

    private final Map<String, Long> heartbeatDevice = new ConcurrentHashMap<>();

    private final Map<String, ModbusThingModel> pkToThingModel = new ConcurrentHashMap<>();

    private final ModbusVerticle modbusVerticle;

    private final ProductApi productApi;

    private final DeviceApi deviceApi;

    private final ModbusInfoApi modbusInfoApi;

    private final ModbusThingModelApi modbusThingModelApi;

    private final ThreadPoolTaskScheduler taskScheduler;
    private ScheduledFuture<?> readTaskFuture;
    private ScheduledFuture<?> offlineCheckTaskFuture;


    /**
     * 服务
     */
    private Vertx vertx;

    /**
     * tcp服务
     */
    private NetServer server;

    private ModbusConfig modbusConfig;

    @Setter
    private long keepAliveTimeout = Duration.ofSeconds(60).toMillis();



    protected ModbusComponent(
            ComponentServices componentServices,
            ModbusVerticle modbusVerticle,
            ProductApi productApi,
            DeviceApi deviceApi,
            ModbusInfoApi modbusInfoApi,
            ModbusThingModelApi modbusThingModelApi,
            @Qualifier("ModbusTaskScheduler") ThreadPoolTaskScheduler taskScheduler
    ) {
        super(componentServices);
        this.modbusVerticle = modbusVerticle;
        this.productApi = productApi;
        this.deviceApi = deviceApi;
        this.modbusInfoApi = modbusInfoApi;
        this.modbusThingModelApi = modbusThingModelApi;
        this.taskScheduler = taskScheduler;
    }

    public void setModbusServer(NetServer server, Vertx vertx){
        this.vertx = vertx;
        this.server = server;
        // 处理新的连接
        server.connectHandler(this);
    }



    @Override
    public void handle(NetSocket socket) {
        log.info("收到客户端连接：{}", socket.remoteAddress());
        // 客户端连接处理
        String clientId = IdUtil.simpleUUID() + "_" + socket.remoteAddress();
        VertxModbusClient client = new VertxModbusClient(clientId);
        client.setDn(clientId); // 先初始化dn,避免报错
        try {
            // 这个地方是在TCP服务初始化的时候设置的 parserSupplier
            client.setKeepAliveTimeoutMs(keepAliveTimeout);
            client.setSocket(socket);

            Consumer<Buffer> bufferConsumer = buffer -> {
                try {
                    log.info("[{}]解析数据:{} ASCII:{}", client.getDn(), HexUtil.toHexString(buffer.getBytes()), buffer.toString("US-ASCII"));

                    DataPackage data = DataDecoder.decode(buffer);
                    if (data == null) return;

                    //设备注册
                    if (data instanceof RegisterDataPackage) {
                        String dn = ((RegisterDataPackage) data).getDn();
                        client.setDn(dn);

                        heartbeatDevice.remove(dn);
                        clientMap.put(dn, client);

                        String productKey = ((RegisterDataPackage) data).getProductKey();
                        Byte slaveId = ((RegisterDataPackage) data).getSlaveId();

                        client.setProductKey(productKey);
                        client.setSlaveId(slaveId);

                        RegisterDevice build = RegisterDevice.builder()
                                .productKey(productKey)
                                .deviceName(dn)
                                .build();

                        DeviceInfo device = deviceApi.registerDevice(build);
                        dnToDevice.put(dn, device);

                        ModbusThingModel thingModel = pkToThingModel.computeIfAbsent(device.getProductKey(), modbusThingModelApi::findByProductKey);
                        if (thingModel == null) {
                            log.warn("thingModel not found for productKey: {}", device.getProductKey());
                            return;
                        }
                        return;
                    }

                    if (data instanceof HeartbeatDataPackage) {
                        //心跳
                        String dn = ((HeartbeatDataPackage) data).getDn();
                        online(dn);
                        return;
                    }

                    if (data instanceof ResponseDataPackage) {
                        //设备数据上报
                        String dn = client.getDn();
                        online(dn);
                        MbapHeader header = ((ResponseDataPackage) data).getHeader();
                        ModbusPdu pdu = ((ResponseDataPackage) data).getPdu();

                        short transactionId = header.getTransactionId();

                        ModbusThingModel.Property property = client.getTransactionMap().remove(transactionId);
                        if (property == null) return;

                        switch (pdu.getFunctionCode()) {
                            case ReadCoils:
                                ByteBuf coilStatus = ((ReadCoilsResponse) pdu).getCoilStatus();
                                byte[] coilBytes = Arrays.copyOfRange(coilStatus.array(), 2, coilStatus.array().length);
                                Object coilValue = property.parse(coilBytes);
                                report(PropertyReport.builder()
                                    .productKey(client.getProductKey())
                                        .deviceName(dn)
                                        .params(Dict.create().set(property.getIdentifier(), coilValue))
                                        .build());
                                break;
                            case ReadDiscreteInputs:
                                ByteBuf inputStatus = ((ReadDiscreteInputsResponse) pdu).getInputStatus();
                                byte[] inputBytes = Arrays.copyOfRange(inputStatus.array(), 2, inputStatus.array().length);
                                Object inputValue = property.parse(inputBytes);
                                report(PropertyReport.builder()
                                        .productKey(client.getProductKey())
                                        .deviceName(dn)
                                        .params(Dict.create().set(property.getIdentifier(), inputValue))
                                        .build());
                                break;
                            case ReadHoldingRegisters:
                                ByteBuf holdingRegisters = ((ReadHoldingRegistersResponse) pdu).getRegisters();
                                byte[] holdingRegistersBytes = Arrays.copyOfRange(holdingRegisters.array(), 2, holdingRegisters.array().length);
                                Object holdingRegistersValue = property.parse(holdingRegistersBytes);
                                report(PropertyReport.builder()
                                        .productKey(client.getProductKey())
                                        .deviceName(dn)
                                        .params(Dict.create().set(property.getIdentifier(), holdingRegistersValue))
                                        .build());
                                break;
                            case ReadInputRegisters:
                                ByteBuf inputRegisters = ((ReadInputRegistersResponse) pdu).getRegisters();
                                byte[] inputRegistersBytes = Arrays.copyOfRange(inputRegisters.array(), 2, inputRegisters.array().length);
                                Object inputRegistersValue = property.parse(inputRegistersBytes);
                                report(PropertyReport.builder()
                                        .productKey(client.getProductKey())
                                        .deviceName(dn)
                                        .params(Dict.create().set(property.getIdentifier(), inputRegistersValue))
                                        .build());
                                break;
                            default:
                                return;
                        }

                    }

                    //未注册断开连接
                    if (!clientMap.containsKey(client.getDn())) {
                        socket.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            client.setConsumer(bufferConsumer);
            log.debug("accept modbus client [{}] connection", socket.remoteAddress());
        } catch (Exception e) {
            e.printStackTrace();
            client.shutdown();
        }
    }

    @Override
    public boolean stateChange(boolean enable, String config) {
        modbusVerticle.setModbusComponent(this);

        //停止组件
        if (!enable) {
            // 停止定时任务
            cancelTasks();
            // 关闭所有客户端连接
            closeAllClients();
            // 停止服务
            modbusVerticle.stopServer();
            return true;
        }

        if (config == null) {
            return false;
        }

        ModbusConfig modbusConfig = JsonUtils.parseObject(config, ModbusConfig.class);
        if (modbusConfig == null) {
            log.error("parse json modbus config failed.");
            return false;
        }
        this.modbusConfig = modbusConfig;
        // 先停止旧服务
        cancelTasks();
        closeAllClients();
        modbusVerticle.stopServer();
        // 再启动新配置
        modbusVerticle.startServer(modbusConfig);
        // 根据时间间隔执行定时任务（上次开始时计算）
        // 属性读取定时任务
        readTaskFuture = taskScheduler.scheduleAtFixedRate(this::readTask, Duration.ofSeconds(modbusConfig.getTimer()));
        // 离线设备检测定时任务
        offlineCheckTaskFuture = taskScheduler.scheduleAtFixedRate(this::offlineCheckTask, Duration.ofSeconds(40));
        return true;
    }

    private void cancelTasks() {
        if (readTaskFuture != null) {
            readTaskFuture.cancel(true);
            readTaskFuture = null;
        }
        if (offlineCheckTaskFuture != null) {
            offlineCheckTaskFuture.cancel(true);
            offlineCheckTaskFuture = null;
        }
    }


    private void closeAllClients() {
        clientMap.values().forEach(VertxModbusClient::shutdown);
        clientMap.clear();
        heartbeatDevice.clear();

        dnToDevice.clear();
        pkToThingModel.clear();
    }

    /**
     * 定时任务
     * 读取所有已连接设备的属性值
     */
    private void readTask() {
        ArrayList<VertxModbusClient> clients = new ArrayList<>(clientMap.values());
        for (VertxModbusClient client : clients) {
            DeviceInfo device = dnToDevice.get(client.getDn());
            if (device == null) continue;
            // 采用线程池异步执行
            ThreadUtil.execAsync(() -> {
                if (!clientMap.containsKey(client.getDn())) return;
                try {
                    ModbusThingModel thingModel = pkToThingModel.computeIfAbsent(device.getProductKey(), modbusThingModelApi::findByProductKey);
                    if (thingModel == null) {
                        log.warn("thingModel not found for productKey: {}", device.getProductKey());
                        return;
                    }

                    //遍历物模型属性，读取属性值
                    for (ModbusThingModel.Property property : thingModel.getModel().getProperties()) {
                        RequestDataPackage requestData = RequestDataPackage.builder()
                                .transactionId(client.getTransactionId())
                                .slaveId(client.getSlaveId())
                                .functionCode(Byte.parseByte(property.getRegType()))
                                .address(property.getRegAddr().shortValue())
                                .quantity(property.getRegNum().shortValue())
                                .build();
                        // 将属性与请求数据关联起来，以便后续读取响应值的时候能知道读的是哪个属性
                        client.getTransactionMap().put(requestData.getTransactionId(), property);

                        Buffer buffer = DataEncoder.encode(requestData);
                        //log.info("发送MODBUS数据: {}", HexUtil.toHexString(buffer.getBytes()));
                        client.sendMessage(buffer);
                        // modbus设备不支持并发，所以极短时间内发送多个读取命令，只会响应第一个命令。
                        // 所以这里需要休眠，避免设备处理不过来
                        ThreadUtil.safeSleep(2000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void serviceInvoke(ServiceInvoke action) {

    }

    @Override
    protected void propertyGet(PropertyGet action) {

    }

    @Override
    protected void propertySet(PropertySet action) {

    }

    @Override
    protected void deviceConfig(DeviceConfig action) {

    }

    private void offlineCheckTask() {
        log.info("keepClientTask");
        //新建列表，防止并发时被修改
        ArrayList<VertxModbusClient> clients = new ArrayList<>(clientMap.values());
        for (VertxModbusClient client : clients) {
            if (!client.isOnline()) {
                client.shutdown();
            }
        }

        Iterator<Map.Entry<String, Long>> iterator = heartbeatDevice.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            String dn = entry.getKey();
            Long time = entry.getValue();
            //心跳超时，判定为离线
            if (System.currentTimeMillis() - time > keepAliveTimeout * 2) {
                iterator.remove();
                VertxModbusClient client = clientMap.get(dn);
                if (client != null) {
                    //离线上报
                    report(DeviceStateChange.builder()
                            .productKey(client.getProductKey())
                            .deviceName(dn)
                            .state(DeviceState.OFFLINE)
                            .time(System.currentTimeMillis())
                            .build());
                }
            }
        }
    }

    public void sendMsg(String dn, Buffer msg) {
        VertxModbusClient modbusClient = clientMap.get(dn);
        if (modbusClient != null) {
            modbusClient.sendMessage(msg);
        }
    }

    public void online(String dn) {
        heartbeatDevice.put(dn, System.currentTimeMillis());
        VertxModbusClient client = clientMap.get(dn);
        if (client != null) {
            //上线
            report(DeviceStateChange.builder()
                    .deviceName(dn)
                    .productKey(client.getProductKey())
                    .state(DeviceState.ONLINE)
                    .build());
        }
    }


    @Override
    public String getType() {
        return "modbus-custom";
    }

    @Override
    public String getName() {
        return "有人云的USR-G770数传终端的modbus-tcp协议组件";
    }

}
