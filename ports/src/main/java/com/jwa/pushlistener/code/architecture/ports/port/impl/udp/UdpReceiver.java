package com.jwa.pushlistener.code.architecture.ports.port.impl.udp;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.ports.port.PortException;
import com.jwa.pushlistener.code.architecture.ports.port.ReceiverHandler;
import com.jwa.pushlistener.code.architecture.ports.port.Receiver;
import com.jwa.pushlistener.code.architecture.ports.port.impl.udp.config.UdpReceiverConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UdpReceiver implements Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UdpReceiver.class);
    private final UdpReceiverConfig config;
    private ReceiverHandler handler;
    private UdpServer udpServer;
    private boolean started;

    public UdpReceiver(final UdpReceiverConfig config) {
        this.config = config;
        this.handler = msg -> {
            LOGGER.debug("Default receiver-handler got called");
            return Optional.absent();
        };
    }

    @Override
    public final void register(final ReceiverHandler handler) {
        this.handler = handler;
        if (isStarted()) {
            udpServer.setHandler(handler);
        }
    }

    @Override
    public final void start() throws PortException {
        if (isStarted()) {
            throw new PortException("Already started");
        }
        udpServer = new UdpServer(config.getPort(), handler);
        udpServer.start();
        started = true;
        LOGGER.info("UdpReceiver started");
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public final void shutdown() {
        if (isStarted()) {
            udpServer.shutdown();
            started = false;
            LOGGER.info("UdpReceiver was shutdown");
        }
    }
}
