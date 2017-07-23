package com.jwa.pushlistener.ports.communication.port.impl.udp;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.communication.port.AsynchronousSender;
import com.jwa.pushlistener.ports.communication.port.AsynchronousSenderCallback;
import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.port.impl.udp.config.UdpSenderConfig;

import java.io.IOException;
import java.net.InetSocketAddress;

public class UdpAsynchronousSender implements AsynchronousSender {
    private final UdpSenderConfig config;

    private AsynchronousSenderCallback callback;
    private UdpServer udpServer;

    public UdpAsynchronousSender(UdpSenderConfig config) {
        this.config = config;
    }

    @Override
    public void register(AsynchronousSenderCallback callback) {
        this.callback = callback;
    }

    @Override
    public void connect() throws CommunicationException {
        udpServer = new UdpServer(config.getPort(), callback);
        udpServer.start();
    }

    @Override
    public Optional<MessageModel> execute(MessageModel msg) throws IllegalArgumentException, CommunicationException {
        if (msg == null) {
            throw new IllegalArgumentException();
        }

        try {
            InetSocketAddress recipient = new InetSocketAddress(config.getHostname(), config.getPort());
            UdpUtils.send(msg, recipient, udpServer.getDatagramSocket());
        } catch (IOException e) {
            throw new CommunicationException("...", e);
        }

        return Optional.absent();
    }

    @Override
    public void disconnect() throws CommunicationException {
        udpServer.shutdown();
    }
}
