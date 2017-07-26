package com.jwa.pushlistener.code.architecture.ports.port.impl.udp;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;
import com.jwa.pushlistener.code.architecture.ports.port.PortException;
import com.jwa.pushlistener.code.architecture.ports.port.AsynchronousSender;
import com.jwa.pushlistener.code.architecture.ports.port.AsynchronousSenderCallback;
import com.jwa.pushlistener.code.architecture.ports.port.impl.udp.config.UdpSenderConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class UdpAsynchronousSender implements AsynchronousSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(UdpAsynchronousSender.class);
    private final UdpSenderConfig config;
    private AsynchronousSenderCallback callback;
    private UdpServer udpServer;
    private boolean connected;

    public UdpAsynchronousSender(final UdpSenderConfig config) {
        this.config = config;
    }

    @Override
    public final void register(final AsynchronousSenderCallback callback) {
        this.callback = callback;
    }

    @Override
    public final void connect() throws PortException {
        if (isConnected()) {
            throw new PortException("Already connected");
        }
        udpServer = new UdpServer(config.getPort(), callback);
        udpServer.start();
        connected = true;
    }

    @Override
    public final boolean isConnected() {
        return connected;
    }

    @Override
    public final Optional<MessageModel> execute(final MessageModel msg) throws PortException {
        if (!isConnected()) {
            throw new PortException("Not connected");
        }
        try {
            final InetSocketAddress recipient = new InetSocketAddress(config.getHostname(), config.getPort());
            UdpUtils.send(msg, recipient, udpServer.getDatagramSocket());
        } catch (IOException e) {
            throw new PortException("Message sending failed: " + e.getMessage(), e);
        }
        LOGGER.info("Message was executed");
        return Optional.absent();
    }

    @Override
    public final void disconnect() {
        if (isConnected()) {
            udpServer.shutdown();
            connected = false;
        }
    }
}