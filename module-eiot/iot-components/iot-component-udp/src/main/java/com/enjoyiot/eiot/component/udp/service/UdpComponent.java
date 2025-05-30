package com.enjoyiot.eiot.component.udp.service;

import com.alibaba.fastjson.JSON;
import com.enjoyiot.eiot.common.enums.DeviceState;
import com.enjoyiot.eiot.component.core.ComponentServices;
import com.enjoyiot.eiot.component.core.ThingComponent;
import com.enjoyiot.eiot.component.core.model.down.DeviceConfig;
import com.enjoyiot.eiot.component.core.model.down.PropertyGet;
import com.enjoyiot.eiot.component.core.model.down.PropertySet;
import com.enjoyiot.eiot.component.core.model.down.ServiceInvoke;
import com.enjoyiot.eiot.component.core.model.up.DeviceStateChange;
import com.enjoyiot.eiot.component.core.model.up.EventReport;
import com.enjoyiot.eiot.component.core.model.up.PropertyReport;
import com.enjoyiot.eiot.component.udp.DTO.UWBDataBaseDTO;
import com.enjoyiot.eiot.component.udp.DTO.UWBForwardedDataDisplayDTO;
import com.enjoyiot.eiot.component.udp.DTO.UWBForwardedDataStatusOneDTO;
import com.enjoyiot.eiot.component.udp.enums.UDPReportTypeEnum;
import com.enjoyiot.eiot.component.udp.model.DeviceData;
import com.enjoyiot.eiot.component.udp.model.UdpConfig;
import com.enjoyiot.eiot.component.udp.parser.uwb.UWBMessageParserFactory;
import com.enjoyiot.framework.common.util.json.JsonUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.device.dto.RegisterDevice;
import com.enjoyiot.module.eiot.api.product.ProductApi;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UdpComponent
 */
@Component
public class UdpComponent extends ThingComponent {
    private static final Logger log = LoggerFactory.getLogger(UdpComponent.class);

    private final UdpVerticle udpVerticle;
    private final ProductApi productApi;
    private final DeviceApi deviceApi;
    private UdpConfig udpConfigKey;

    private final Map<String, String> lastDataHash = new ConcurrentHashMap<>();
    private final Map<String, Long> lastSeen    = new ConcurrentHashMap<>();

    private final MessageDigest md5;

    @Autowired
    public UdpComponent(ComponentServices services,
                        UdpVerticle verticle,
                        ProductApi productApi,
                        DeviceApi deviceApi) {
        super(services);
        this.udpVerticle = verticle;
        this.productApi  = productApi;
        this.deviceApi   = deviceApi;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 init failed", e);
        }
    }

    @Override
    public String getType() {
        return "udp";
    }

    @Override
    public String getName() {
        return "官方 UDP 组件 (Java8)";
    }

    @Override
    public boolean stateChange(boolean enable, String cfg) {
        udpVerticle.setUdpComponent(this);
        if (!enable) {
            udpVerticle.stopServer();
            return true;
        }
        if (cfg == null) {
            return false;
        }

        udpConfigKey = JsonUtils.parseObject(cfg, UdpConfig.class);
        if (udpConfigKey == null) {
            log.error("解析 UDP 配置失败: {}", cfg);
            return false;
        }

        udpVerticle.stopServer();
        udpVerticle.startServer(udpConfigKey);
        return true;
    }

    /**
     * 提供外部访问
     */
    public UdpConfig getConfig() {
        return udpConfigKey;
    }

    /**
     * 接收并处理 UDP 字符串消息
     */
    public void handleUwb(String raw) {
        DeviceData entity = UWBMessageParserFactory.parse(raw);
        processData(entity,createSignatureStr((UWBDataBaseDTO) entity), udpConfigKey.getDefaultProductKey(), "udp_uwb");
    }

    public void handleJson(String jsonStr) {
        // todo 自己写json格式的解析 调用设备上报信息处理方法
        DeviceData entity = JSON.parseObject(jsonStr, DeviceData.class);
        // 加密明文 自己组装
        String signatureStr = entity.getDeviceId();
        processData(entity,signatureStr, udpConfigKey.getDefaultProductKey(), "udp_json");
    }
    public void handleHex(String hexStr) {
        // todo 自己写json格式的解析 调用设备上报信息处理方法
        try {
            //  解码
            byte[] raw = Hex.decodeHex(hexStr.trim().toCharArray());

            // 封装json
            Map<String,Object> m = new HashMap<>();
            m.put("deviceId", extractDeviceIdFromHex(raw));
            m.put("dataBin", Base64.getEncoder().encodeToString(raw));
            String json = JsonUtils.toJsonString(m);

            handleJson(json);

        } catch (DecoderException e) {
            log.error("HEX 解码失败: {}", hexStr, e);
        }
    }

    public void handleAscii(String txt) {
        // todo 自己写json格式的解析 调用设备上报信息处理方法
        Map<String,Object> m = new HashMap<>();
        m.put("deviceId", extractDeviceIdFromAscii(txt));
        m.put("dataTxt", txt);
        String json = JsonUtils.toJsonString(m);
        handleJson(json);
    }

    public void handleBinary(byte[] raw) {
        // todo 自己写json格式的解析 调用设备上报信息处理方法
        Map<String,Object> m = new HashMap<>();
        m.put("deviceId", extractDeviceIdFromBinary(raw));
        m.put("dataBin", Base64.getEncoder().encodeToString(raw));
        String json = JsonUtils.toJsonString(m);
        handleJson(json);
    }
    /**
     * 解析出对应的参数 封装json字符串
     */
    private String extractDeviceIdFromHex(byte[] raw) {
        // TODO: 根据你的协议，解析出设备 ID，比如前 4 字节、或者某个字段
        // 这里只是个示例
        return String.format("%02X%02X%02X%02X", raw[0], raw[1], raw[2], raw[3]);
    }

    /**
     * 解析出对应的参数 封装json字符串
     */
    private String extractDeviceIdFromAscii(String txt) {
        // TODO: 根据你的 ASCII 协议解析 deviceId
        // 例如：txt 里有 “id=DEV123;...” 这样的字段
        int idx = txt.indexOf("id=");
        if (idx >= 0) {
            int end = txt.indexOf(';', idx);
            return txt.substring(idx + 3, end > 0 ? end : txt.length());
        }
        return "unknown";
    }

    /**
     * 解析出对应的参数 封装json字符串
     */
    private String extractDeviceIdFromBinary(byte[] raw) {
        // TODO: 二进制协议里，哪几个字节是 ID，就解析那几位
        return Base64.getEncoder().encodeToString(Arrays.copyOf(raw, Math.min(4, raw.length)));
    }

    /**
        * 设备上报信息处理
        * @param deviceData 设备数据
    	* @param signatureStr 签名明文，防止重复上报
    	* @param productKey 产品密钥
    	* @param eventName 事件名称
        * @author stuil
    */
    public void processData(DeviceData deviceData,String signatureStr,String productKey,String eventName) {
        if (deviceData == null || deviceData.getDeviceId() == null) {
            log.warn("无效或未识别的UDP消息: {}", deviceData);
            return;
        }
        String devDevId = deviceData.getDeviceId();
        // 签名  防止重复上报
        String sig = computeSignatureStr(signatureStr);
        String prev = lastDataHash.put(devDevId, sig);
        if (sig.equals(prev)) {
            log.debug("数据未变化，跳过上报: {}", devDevId);
            return;
        }
        lastSeen.put(devDevId, System.currentTimeMillis());

        DeviceInfo deviceInfo = getDeviceInfoByDnAndPk(devDevId,productKey);
        if (deviceInfo == null) {
            log.warn("设备未注册，跳过: {}", devDevId);
            return;
        }
        // 上线上报
        handleOnline(deviceInfo);
        // 属性或事件上报
        UDPReportTypeEnum udpReportTypeEnum = deviceData.reportType();
        switch (udpReportTypeEnum){
            case PROPERTY_REPORT:
                propertyReport(deviceInfo, deviceData.toMap());
                break;
            case EVENT_REPORT:
                if (!StringUtils.hasText(eventName)){
                    eventName = "udp_other";
                }
                eventReport(deviceInfo, deviceData.toMap(),eventName);
                break;
            default:
                break;
        }
    }
    /**
     * 基于 DTO 生成签名，不包含时间戳
     */
    private String createSignatureStr(UWBDataBaseDTO e) {
        if (e == null) {
            return "";
        }
        // 拼接业务字段
        StringBuilder sb = new StringBuilder(e.getDevId());
        if (e instanceof UWBForwardedDataDisplayDTO) {
            UWBForwardedDataDisplayDTO d = (UWBForwardedDataDisplayDTO) e;
            sb.append('|').append(d.getPos());
        } else if (e instanceof UWBForwardedDataStatusOneDTO) {
            UWBForwardedDataStatusOneDTO s = (UWBForwardedDataStatusOneDTO) e;
            sb.append('|').append(s.getSubType())
                    .append('|').append(s.getVbat())
                    .append('|').append(s.getRgnid())
                    .append('|').append(s.getVelo());
        }
        return sb.toString();
    }
    private String computeSignature(UWBDataBaseDTO e) {
        // 拼接业务字段
        String signatureStr = createSignatureStr(e);
        return computeSignatureStr(signatureStr);
    }
    private String computeSignatureStr(String signatureStr) {
        byte[] data = signatureStr.getBytes(StandardCharsets.UTF_8);
        md5.reset();
        byte[] digest = md5.digest(data);
        return Hex.encodeHexString(digest);
    }

    /**
     * 获取设备信息
     */
    public DeviceInfo getDeviceInfoByDnAndPk(String devDn, String productKey) {
        // 注册或获取设备
        DeviceInfo deviceInfo;
        if (udpConfigKey.isEnableAutoRegister()) {
            deviceInfo = deviceApi.registerDevice(
                    RegisterDevice.builder()
                            .productKey(productKey)
                            .deviceName(devDn)
                            .build()
            );
        } else {
            deviceInfo = deviceApi.getDeviceByPkDnByCache(udpConfigKey.getDefaultProductKey(), devDn);
        }
        return deviceInfo;
    }
    public void propertyReport(DeviceInfo deviceInfo,  Map<String, Object> params) {
        // 属性上报
        report(PropertyReport.builder()
                .productKey(deviceInfo.getProductKey())
                .deviceName(deviceInfo.getDn())
                .params(params)
                .time(System.currentTimeMillis())
                .build()
        );
    }
    public void eventReport(DeviceInfo deviceInfo,  Map<String, Object> params,String eventName) {
        // 事件上报
        report(EventReport.builder()
                .productKey(deviceInfo.getProductKey())
                .deviceName(deviceInfo.getDn())
                .name(eventName)
                .params(params)
                .time(System.currentTimeMillis())
                .build()
        );
    }
    /**
     * 定时清理超时并上报下线
     */
    public void handleOffline(String dev) {
        report(DeviceStateChange.builder()
                .productKey(udpConfigKey.getDefaultProductKey())
                .deviceName(dev)
                .state(DeviceState.OFFLINE)
                .time(System.currentTimeMillis())
                .build()
        );
    }
    public void handleOnline(DeviceInfo deviceInfo) {
        // 上线上报
        report(DeviceStateChange.builder()
                .productKey(deviceInfo.getProductKey())
                .deviceName(deviceInfo.getDn())
                .state(DeviceState.ONLINE)
                .time(System.currentTimeMillis())
                .build()
        );
    }
    // 以下不支持的操作保持不变
    @Override protected void serviceInvoke(ServiceInvoke action)   { throw new UnsupportedOperationException("UDP 不支持服务下发"); }
    @Override protected void propertyGet(PropertyGet action)       { throw new UnsupportedOperationException("UDP 不支持属性获取"); }
    @Override protected void propertySet(PropertySet action)       { throw new UnsupportedOperationException("UDP 不支持属性设置"); }
    @Override protected void deviceConfig(DeviceConfig action)     { throw new UnsupportedOperationException("UDP 不支持配置下发"); }
}
