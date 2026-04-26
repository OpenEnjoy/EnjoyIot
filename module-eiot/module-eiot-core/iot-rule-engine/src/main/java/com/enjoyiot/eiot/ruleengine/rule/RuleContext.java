package com.enjoyiot.eiot.ruleengine.rule;

import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.framework.common.util.spring.SpringUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;


import java.util.ArrayList;
import java.util.List;

public class RuleContext {

    /**
     * 原始消息
     */
    private ThingModelMessage message;

    private DeviceInfo deviceInfo;

    private boolean getDevice;

    private Long ruleId;

    /**
     * 触发成功的物模型信息集合
     */
    private List<RuleContextItem> triggeredThings = new ArrayList<>();

    private static DeviceApi deviceApi = SpringUtils.getBean(DeviceApi.class);

    public RuleContext(Rule rule,ThingModelMessage message) {
        this.message = message;
        this.ruleId = rule.getId();
        this.getDevice = false;
    }

    public DeviceInfo getDeviceInfo() {
        if (deviceInfo == null && !getDevice) {
            getDevice = true;
            if (message.getDeviceId() != null) {
                deviceInfo = deviceApi.getDeviceInfoFromCache(message.getDeviceId());
            } else if (message.getProductKey() != null && message.getDn() != null) {
                deviceInfo = deviceApi.getDeviceByPkDnByCache(message.getProductKey(), message.getDn());
            }
        }
        return deviceInfo;
    }

    public void addTriggeredThing(RuleContextItem thing) {
        this.triggeredThings.add(thing);
    }

    public ThingModelMessage getMessage() {
        return message;
    }

    public List<RuleContextItem> getTriggeredThings() {
        return triggeredThings;
    }
    public Long getRuleId() {
        return ruleId;
    }
}
