
package com.enjoyiot.eiot.component.tcp.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.enjoyiot.eiot.component.core.ComponentServices;
import com.enjoyiot.eiot.component.core.ThingComponent;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.component.core.model.ActionResult;
import com.enjoyiot.eiot.component.core.model.down.DeviceConfig;
import com.enjoyiot.eiot.component.core.model.down.PropertyGet;
import com.enjoyiot.eiot.component.core.model.down.PropertySet;
import com.enjoyiot.eiot.component.core.model.down.ServiceInvoke;
import com.enjoyiot.eiot.component.core.model.up.*;
import com.enjoyiot.eiot.component.tcp.cilent.VertxTcpClient;
import com.enjoyiot.eiot.component.tcp.model.TcpConfig;
import com.enjoyiot.eiot.component.tcp.parser.DataDecoder;
import com.enjoyiot.eiot.component.tcp.parser.DataEncoder;
import com.enjoyiot.eiot.component.tcp.parser.DataPackage;
import com.enjoyiot.eiot.component.tcp.parser.DataReader;
import com.enjoyiot.framework.common.exception.ServiceException;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.RegisterDevice;
import com.enjoyiot.module.eiot.api.product.ProductApi;
import com.enjoyiot.module.eiot.api.product.dto.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.mqtt.MqttAuth;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.codes.MqttSubAckReasonCode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TcpComponent extends ThingComponent implements Handler<NetSocket> {

    private final Map<String, VertxTcpClient> clientMap = new ConcurrentHashMap<>();

    private final Map<String, String> dnToPk = new HashMap<>();

    private final Map<String, Long> heartbeatDevice = new HashMap<>();

    private final TcpVerticle tcpVerticle;

    private final ProductApi productApi;

    private final DeviceApi deviceApi;

    /**
     * 服务
     */
    private Vertx vertx;

    /**
     * tcp服务
     */
    private NetServer server;

    @Setter
    private long keepAliveTimeout = Duration.ofSeconds(30).toMillis();



    protected TcpComponent(
            TcpVerticle tcpVerticle,
            ProductApi productApi,
            DeviceApi deviceApi,
            ComponentServices componentServices
    ) {
        super(componentServices);
        this.tcpVerticle = tcpVerticle;
        this.productApi = productApi;
        this.deviceApi = deviceApi;
    }

    public void setTcpServer(NetServer server, Vertx vertx){
        this.vertx = vertx;
        this.server = server;
        // 处理新的连接
        server.connectHandler(this::handle);
    }



    @Override
    public void handle(NetSocket socket) {
        log.info("收到客户端连接：{}", socket.remoteAddress());
        // 客户端连接处理
        String clientId = IdUtil.simpleUUID() + "_" + socket.remoteAddress();
        VertxTcpClient client = new VertxTcpClient(clientId);
        client.setKeepAliveTimeoutMs(keepAliveTimeout);
        try {
            // TCP异常和关闭处理
            socket.exceptionHandler(Throwable::printStackTrace).closeHandler(nil -> {
                log.debug("tcp server client [{}] closed", socket.remoteAddress());
                client.shutdown();
            });
            // 这个地方是在TCP服务初始化的时候设置的 parserSupplier
            client.setKeepAliveTimeoutMs(keepAliveTimeout);
            client.setSocket(socket);
            RecordParser parser = DataReader.getParser(buffer -> {
                try {
                    log.info("数据：{}",buffer.toString());
                    DataPackage data = DataDecoder.decode(buffer);
                    String addr = data.getAddr();
                    short code = data.getCode();
                    clientMap.put(addr, client);
                    if (code == DataPackage.CODE_REGISTER) {
                        heartbeatDevice.remove(addr);
                        //设备注册
                        String pk = new String(data.getPayload());
                        dnToPk.put(addr, pk);
                        RegisterDevice build = RegisterDevice.builder()
                                .productKey(pk)
                                .deviceName(addr)
                                .build();

                        DeviceInfo parentDevice = deviceApi.registerDevice(build);

                        if(parentDevice != null){
                            //回复注册成功给客户端
                            sendMsg(addr, DataEncoder.encode(
                                    DataPackage.builder()
                                            .addr(addr)
                                            .code(DataPackage.CODE_REGISTER_REPLY)
                                            .mid(data.getMid())
                                            .payload(Buffer.buffer().appendInt(0).toString())
                                            .build()
                            ));
                        }

                        // 缓存设备对应的组件信息(用于下发控制指令时查询设备对应的组件信息从而拼接topic)
                        cacheDeviceComponentInfo(pk, addr);

                        return;
                    }

                    if (code == DataPackage.CODE_HEARTBEAT) {
                        //心跳
                        online(addr);
                        return;
                    }

                    if (code == DataPackage.CODE_DATA_UP) {
                        //设备数据上报
//                        online(addr);

                        JSONObject object = JSONUtil.parseObj(data.getPayload());
                        report(PropertyReport.builder()
                                .productKey(dnToPk.get(addr))
                                .deviceName(addr)
                                .params(object.getRaw())
                                .build());

                        sendMsg(addr, DataEncoder.encode(
                                DataPackage.builder()
                                        .addr(addr)
                                        .code(DataPackage.CODE_DATA_UP)
                                        .mid(data.getMid())
                                        .payload(Buffer.buffer().appendInt(0).toString())
                                        .build()
                        ));
                    }
                    if (code == DataPackage.CODE_EVENT_UP) {
                        //设备事件上报
//                        online(addr);

                        JSONObject object = JSONUtil.parseObj(data.getPayload());
                        report(EventReport.builder()
                                .productKey(dnToPk.get(addr))
                                .deviceName(addr)
                                .name("up_param")
                                .params(object.getRaw())
                                .build());

                        sendMsg(addr, DataEncoder.encode(
                                DataPackage.builder()
                                        .addr(addr)
                                        .code(DataPackage.CODE_DATA_UP)
                                        .mid(data.getMid())
                                        .payload(Buffer.buffer().appendInt(0).toString())
                                        .build()
                        ));
                    }

                    //未注册断开连接
                    if (!clientMap.containsKey(data.getAddr())) {
                        socket.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            client.setParser(parser);
            log.debug("accept tcp client [{}] connection", socket.remoteAddress());
        } catch (Exception e) {
            e.printStackTrace();
            client.shutdown();
        }
    }

    @Override
    public boolean stateChange(boolean enable, String config) {
        tcpVerticle.setTcpComponent(this);

        //停止组件
        if (!enable) {
            tcpVerticle.stopServer();
            return true;
        }

        if (config == null) {
            return false;
        }

        TcpConfig tcpConfig = JsonUtils.parseObject(config, TcpConfig.class);
        if (tcpConfig == null) {
            log.error("parse json tcp config failed.");
            return false;
        }

        tcpVerticle.stopServer();
        tcpVerticle.startServer(tcpConfig);
        return true;
    }

    @Override
    protected void serviceInvoke(ServiceInvoke action) {

    }

    @Override
    protected void propertyGet(PropertyGet action) {

    }

    /**
     * 属性设置指令发送给设备
     * @param action
     */
    @Override
    protected void propertySet(PropertySet action) {
        log.info("属性设置action:{}", action);

        // 消息体
        String payload = new JsonObject()
                .put("id", action.getId())
                .put("method", "thing.service.property.set")
                .put("params", action.getParams())
                .toString();

        DataPackage dataPackage =  DataPackage.builder()
                // 设备标识
                .addr(action.getDeviceName())
                .code(DataPackage.CODE_DATA_DOWN)
                // 消息序号
                .mid((short)IdUtil.getSnowflakeNextId())
                // 消息体
                .payload(payload)
                .build();
        log.info("下发属性设置数据包dataPackage:{}", JSON.toJSONString(dataPackage));
        sendMsg(action.getDeviceName(), DataEncoder.encode(dataPackage));
    }

    @Override
    protected void deviceConfig(DeviceConfig action) {

    }

    @Scheduled(fixedRate = 40, timeUnit = TimeUnit.SECONDS)
    private void offlineCheckTask() {
        log.info("keepClientTask");
        Set<String> clients = new HashSet<>(clientMap.keySet());
        for (String key : clients) {
            VertxTcpClient client = clientMap.get(key);
            if (!client.isOnline()) {
                client.shutdown();
            }
        }

        heartbeatDevice.keySet().iterator().forEachRemaining(addr -> {
            Long time = heartbeatDevice.get(addr);
            //心跳超时，判定为离线
            if (System.currentTimeMillis() - time > keepAliveTimeout * 2) {
                heartbeatDevice.remove(addr);
                //离线上报
                report(DeviceStateChange.builder()
                        .productKey(dnToPk.get(addr))
                        .deviceName(addr)
                        .state(DeviceState.OFFLINE)
                        .time(System.currentTimeMillis())
                        .build());
            }
        });
    }

    public void sendMsg(String addr, Buffer msg) {
        VertxTcpClient tcpClient = clientMap.get(addr);
        if (tcpClient != null) {
            tcpClient.sendMessage(msg);
        }
    }

    public void online(String addr) {
        heartbeatDevice.put(addr, System.currentTimeMillis());
        String pk = dnToPk.get(addr);

        //上线
        report(DeviceStateChange.builder()
                .deviceName(addr)
                .productKey(pk)
                .state(DeviceState.ONLINE)
                .build());
    }


    @Override
    public String getType() {
        return "tcp";
    }

    @Override
    public String getName() {
        return "tcp协议组件";
    }

}
