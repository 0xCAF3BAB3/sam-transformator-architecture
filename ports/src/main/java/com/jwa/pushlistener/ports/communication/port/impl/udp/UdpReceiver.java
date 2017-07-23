package com.jwa.pushlistener.ports.communication.port.impl.udp;

import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.port.ReceiverHandler;
import com.jwa.pushlistener.ports.communication.port.Receiver;
import com.jwa.pushlistener.ports.communication.port.impl.udp.config.UdpReceiverConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UdpReceiver implements Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UdpReceiver.class);
    private final UdpReceiverConfig config;
    private ReceiverHandler handler;
    private UdpServer udpServer;

    public UdpReceiver(final UdpReceiverConfig config) {
        this.config = config;
    }

    @Override
    public final void register(final ReceiverHandler handler) {
        this.handler = handler;
    }

    @Override
    public final void start() throws CommunicationException {
        udpServer = new UdpServer(config.getPort(), handler);
        udpServer.start();
        LOGGER.info("UdpReceiver started");
    }

    @Override
    public final void shutdown() {
        udpServer.shutdown();
        LOGGER.info("UdpReceiver was shutdown");
    }
}
