package com.enjoyiot.eiot.component.modbusCustom.cilent;

import com.enjoyiot.eiot.common.utils.HexUtil;
import com.enjoyiot.module.eiot.api.modbus.dto.ModbusThingModel;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public class VertxModbusClient {
    @Getter
    private String id;
    /**
     * 设备序列号
     */
    @Getter
    @Setter
    private String dn;
    /**
     * 产品key
     */
    @Getter
    @Setter
    private String productKey;

    /**
     * modbus 从机id
     */
    @Getter
    @Setter
    private Byte slaveId;

    public NetSocket socket;
    @Setter
    private long keepAliveTimeoutMs = Duration.ofSeconds(60).toMillis();
    private volatile long lastKeepAliveTime = System.currentTimeMillis();

    private final AtomicInteger transactionId = new AtomicInteger(0);
    @Getter
    private final Map<Short, ModbusThingModel.Property> transactionMap = new ConcurrentHashMap<>();

    @Setter
    private Consumer<Buffer> consumer;

    public VertxModbusClient(String id) {
        this.id = id;
    }

    public void keepAlive() {
        lastKeepAliveTime = System.currentTimeMillis();
    }

    public boolean isOnline() {
        return System.currentTimeMillis() - lastKeepAliveTime < keepAliveTimeoutMs;
    }

    public void setSocket(NetSocket socket) {
        synchronized (this) {
            if (this.socket != null && this.socket != socket) {
                this.socket.close();
            }

            this.socket = socket
                    .exceptionHandler(Throwable::printStackTrace)
                    .closeHandler(v -> {
                        log.debug("modbus client[{}] close", dn);
                        shutdown();
                    })
                    .handler(buffer -> {
                        if (log.isDebugEnabled()) {
                            log.debug("handle modbus client[{}] payload:[{}]",
                                    dn,
                                    HexUtil.toFormattedHexString(buffer.getBytes()));
                        }
                        keepAlive();
                        consumer.accept(buffer);
                        if (this.socket != socket) {
                            log.warn("modbus client[{}] memory leak ", dn);
                            socket.close();
                        }
                    });
        }
    }

    public void shutdown() {
        log.debug("modbus client[{}] disconnect", dn);
        synchronized (this) {
            if (null != socket) {
                execute(socket::close);
                this.socket = null;
            }
        }
    }

    public void sendMessage(Buffer buffer) {
        NetSocket socket;
        synchronized (this) {
            socket = this.socket;
            if (socket == null) {
                return;
            }
        }
        log.debug("write data:{}", HexUtil.toFormattedHexString(buffer.getBytes()));
        socket.write(buffer, r -> {
            keepAlive();
            if (r.succeeded()) {
                log.info("client[{}] msg send success:{}", dn, HexUtil.toFormattedHexString(buffer.getBytes()));
            } else {
                log.error("client[{}] msg send failed", dn, r.cause());
            }
        });
    }

    private void execute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.warn("close modbus client error", e);
        }
    }

    public short getTransactionId() {
        // 1-999
        return (short) transactionId.updateAndGet(i -> (i % 999) + 1);
    }
}
