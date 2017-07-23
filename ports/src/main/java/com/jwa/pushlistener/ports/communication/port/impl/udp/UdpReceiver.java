package com.jwa.pushlistener.ports.communication.port.impl.udp;

import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.port.ReceiverHandler;
import com.jwa.pushlistener.ports.communication.port.Receiver;
import com.jwa.pushlistener.ports.communication.port.impl.udp.config.UdpReceiverConfig;

public class UdpReceiver implements Receiver {
    private final UdpReceiverConfig config;

    private ReceiverHandler handler;
    private UdpServer udpServer;

    public UdpReceiver(UdpReceiverConfig config) {
        this.config = config;
    }

    @Override
    public void register(ReceiverHandler handler) {
        this.handler = handler;
    }

    @Override
    public void start() throws CommunicationException {
        udpServer = new UdpServer(config.getPort(), handler);
        udpServer.start();
    }

    @Override
    public void shutdown() throws CommunicationException {
        udpServer.shutdown();
    }
}
