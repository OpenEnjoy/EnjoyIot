/*
 *
 *  * | Licensed 未经许可不能去掉「Enjoy-iot」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2025] [Enjoy-iot]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */
package com.enjoyiot.eiot.virtualdevice;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.enjoyiot.eiot.IVirtualDeviceLogData;
import com.enjoyiot.eiot.common.constant.Constants;
import com.enjoyiot.eiot.common.thing.ThingModelMessage;
import com.enjoyiot.eiot.engine.IScriptEngine;
import com.enjoyiot.eiot.engine.ScriptEngineFactory;
import com.enjoyiot.eiot.message.core.MqProducer;
import com.enjoyiot.eiot.virtualdevice.trigger.RandomScheduleBuilder;
import com.enjoyiot.framework.tenant.core.util.TenantUtils;
import com.enjoyiot.module.eiot.api.device.DeviceApi;
import com.enjoyiot.module.eiot.api.device.dto.DeviceInfo;
import com.enjoyiot.module.eiot.api.virtualdevice.VirtualDeviceApi;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDevice;
import com.enjoyiot.module.eiot.api.virtualdevice.dto.VirtualDeviceLog;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
public class VirtualManager {
    private final Map<Long, IScriptEngine> virtualScripts = new HashMap<>();
    private final Map<Long, Set<Long>> deviceIdToVirtualId = new HashMap<>();

    @Autowired
    private VirtualDeviceApi virtualDeviceApi;

    @Autowired
    private DeviceApi deviceApi;
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private MqProducer<ThingModelMessage> producer;

    @Autowired
    private IVirtualDeviceLogData virtualDeviceLogData;


    @PostConstruct
    public void init() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                TenantUtils.executeIgnore(() -> {
                    List<VirtualDevice> virtualDevices = getAllVirtualDevices();
                    for (VirtualDevice virtualDevice : virtualDevices) {
                        addTask(virtualDevice);
                    }
                });

            }
        }, 8000);
    }

    /**
     * 判断设备是否应用了虚拟设备
     */
    public boolean isVirtual(Long deviceId) {
        return deviceIdToVirtualId.containsKey(deviceId);
    }

    /**
     * 调用虚拟设备下发
     */
    @SneakyThrows
    public void send(ThingModelMessage message) {
        DeviceInfo deviceInfo = deviceApi.getDeviceByPkDnByCache(message.getProductKey(), message.getDn());
        Long deviceId = deviceInfo.getId();

        //根据设备Id取虚拟设备列表
        Set<Long> virtualIds = deviceIdToVirtualId.get(deviceId);
        for (Long virtualId : virtualIds) {
            IScriptEngine scriptEngine = virtualScripts.get(virtualId);
            //多条虚拟设备消息
            List<ThingModelMessage> result = scriptEngine.invokeMethod(
                    new TypeReference<List<ThingModelMessage>>() {
                    },
                    "receive", message);
            for (ThingModelMessage msg : result) {
                processReport(msg);
            }
            log.info("virtual device send result:{}", JSON.toJSONString(result));
        }
    }


    /**
     * 添加虚拟设备
     */
    public void add(VirtualDevice virtualDevice) {
        addTask(virtualDevice);
    }

    /**
     * 删除虚拟设备
     */
    public void remove(VirtualDevice virtualDevice) {
        deleteTask(virtualDevice);
    }

    /**
     * 立即执行一次虚拟设备上报
     */
    public void run(VirtualDevice virtualDevice) {
        List<Long> devices = virtualDevice.getDevices();
        VirtualDeviceLog virtualDeviceLog = VirtualDeviceLog.builder()
                .virtualDeviceId(virtualDevice.getId())
                .virtualDeviceName(virtualDevice.getName())
                .deviceTotal(devices.size())
                .result("success")
                .build();
        try {
            IScriptEngine scriptEngine = virtualScripts.get(virtualDevice.getId());
            for (Long deviceId : devices) {
                DeviceInfo device = deviceApi.getDeviceInfoFromCache(deviceId);
                processReport(scriptEngine.invokeMethod(new TypeReference<ThingModelMessage>() {
                }, "report", device));
            }
        } catch (Throwable e) {
            virtualDeviceLog.setResult(e.getMessage());
            log.error("run VirtualDevice error", e);
        }
        virtualDeviceLogData.add(virtualDeviceLog);
    }

    /**
     * 更新虚拟设备
     */
    public void update(VirtualDevice virtualDevice) {
        remove(virtualDevice);
        add(virtualDevice);
    }

    /**
     * 获取所有虚拟设备
     */
    private List<VirtualDevice> getAllVirtualDevices() {
        List<VirtualDevice> randomVirtualDevices = virtualDeviceApi
                .findByTriggerAndState(VirtualDevice.TRIGGER_RANDOM, VirtualDevice.STATE_RUNNING);
        List<VirtualDevice> cronVirtualDevices = virtualDeviceApi
                .findByTriggerAndState(VirtualDevice.TRIGGER_CRON, VirtualDevice.STATE_RUNNING);
        cronVirtualDevices.addAll(randomVirtualDevices);
        return cronVirtualDevices;
    }

    private void addTask(VirtualDevice virtualDevice) {
        try {
            Long id = virtualDevice.getId();
            String name = virtualDevice.getName();
            String script = virtualDevice.getScript();
            log.info("adding virtual device job,id:{},name:{}", id, name);

            //添加新的脚本对象
            IScriptEngine scriptEngine = ScriptEngineFactory.getScriptEngine("js");
            scriptEngine.setScript(script);
            virtualScripts.put(id, scriptEngine);
            List<DeviceInfo> devices = new ArrayList<>();
            for (Long deviceId : virtualDevice.getDevices()) {
                devices.add(deviceApi.getDeviceInfoFromCache(deviceId));
                //更新deviceId的虚拟设备Id对应关系
                Set<Long> virtualIds = deviceIdToVirtualId.getOrDefault(deviceId, new HashSet<>());
                virtualIds.add(id);
                //一个真实设备可能会被多个虚拟设备使用
                deviceIdToVirtualId.put(deviceId, virtualIds);
            }

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("virtualManager", this);
            jobDataMap.put("virtualDevice", virtualDevice);
            jobDataMap.put("devices", devices);

            JobDetail jobDetail = JobBuilder.newJob(VirtualExecutor.class)
                    .withIdentity(String.valueOf(id), name)
                    .usingJobData(jobDataMap)
                    .build();

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger_" + id, "triggerGroup_" + name)
                    .startNow()
                    .withSchedule(
                            getTriggerBuilder(virtualDevice)
                    ).build();

            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Throwable e) {
            log.error("create job failed", e);
        }
    }

    private ScheduleBuilder<?> getTriggerBuilder(VirtualDevice virtualDevice) {
        String type = virtualDevice.getTrigger();
        if ("random".equals(type)) {
            return new RandomScheduleBuilder(virtualDevice.getTriggerExpression());
        }
        if ("cron".equals(type)) {
            return CronScheduleBuilder.cronSchedule(virtualDevice.getTriggerExpression());
        }
        return null;
    }

    @SneakyThrows
    public void deleteTask(VirtualDevice virtualDevice) {
        Long id = virtualDevice.getId();
        String name = virtualDevice.getName();

        //删除脚本对象
        virtualScripts.remove(id);

        //更新deviceId的虚拟设备Id对应关系
        Iterator<Long> keyIterator = deviceIdToVirtualId.keySet().iterator();
        while (keyIterator.hasNext()) {
            Long deviceId = keyIterator.next();
            Set<Long> virtualIds = deviceIdToVirtualId.get(deviceId);
            virtualIds.remove(id);
            if (virtualIds.isEmpty()) {
                keyIterator.remove();
            }
        }

        //删除job
        TriggerKey triggerKey = new TriggerKey("trigger_" + id, "triggerGroup_" + name);
        if (!scheduler.checkExists(triggerKey)) {
            return;
        }
        scheduler.deleteJob(JobKey.jobKey(String.valueOf(id), name));
    }

    /**
     * 处理js上报方法返回结果
     */
    private void processReport(ThingModelMessage message) {
        try {
            producer.publish(Constants.THING_MODEL_MESSAGE_TOPIC, message);
        } catch (Throwable e) {
            log.error("process js data error", e);
        }
    }

    /**
     * 调用脚本中上报方法
     */
    @SneakyThrows
    public void invokeReport(DeviceInfo device, Long virtualId) {
        //设备上线
        deviceOnline(device);

        IScriptEngine scriptEngine = virtualScripts.get(virtualId);
        if (scriptEngine == null) {
            return;
        }
        processReport(scriptEngine.invokeMethod(new TypeReference<ThingModelMessage>() {
        }, "report", device));
    }

    public void refreshScript(Long virtualDeviceId, String script) {
        if (virtualScripts.containsKey(virtualDeviceId)) {
            IScriptEngine scriptEngine = virtualScripts.get(virtualDeviceId);
            scriptEngine.setScript(script);
        }
    }

    /**
     * 设备上线
     */
    private void deviceOnline(DeviceInfo device) {
        Integer state = device.getState();
        if (state == null || !device.isOnline()) {
            //设备离线，产生上线消息
            processReport(ThingModelMessage.builder()
                    .id(IdUtil.fastSimpleUUID())
                    .time(System.currentTimeMillis())
                    .occurred(System.currentTimeMillis())
                    .identifier(ThingModelMessage.ID_ONLINE)
                    .productKey(device.getProductKey())
                    .deviceId(device.getId())
                    .dn(device.getDn())
                    .type(ThingModelMessage.TYPE_STATE)
                    .build());
        }
    }

    /**
     * 保存虚拟设备日志
     */
    public void saveLog(VirtualDeviceLog log) {
        virtualDeviceLogData.add(log);
    }

}
