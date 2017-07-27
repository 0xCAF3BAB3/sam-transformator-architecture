package com.jwa.pushlistener.code.architecture.communication.port.impl.udp;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;
import com.jwa.pushlistener.code.architecture.communication.port.PortException;
import com.jwa.pushlistener.code.architecture.communication.port.SynchronousSender;
import com.jwa.pushlistener.code.architecture.communication.port.impl.udp.config.UdpSenderConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public final class UdpSynchronousSender implements SynchronousSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(UdpSynchronousSender.class);
    private final UdpSenderConfig config;
    private DatagramSocket datagramSocket;
    private boolean connected;

    public UdpSynchronousSender(final UdpSenderConfig config) {
        this.config = config;
    }

    @Override
    public final void connect() throws PortException {
        if (isConnected()) {
            throw new PortException("Already connected");
        }
        try {
            datagramSocket = new DatagramSocket(0); // 0 = use any free port
        } catch (SocketException e) {
            throw new PortException("Datagram-socket creation failed: " + e.getMessage(), e);
        }
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
            UdpUtils.send(msg, recipient, datagramSocket);
        } catch (IOException e) {
            throw new PortException("Message sending failed: " + e.getMessage(), e);
        }
        LOGGER.info("Message was executed");
        final byte[] buffer = new byte[UdpUtils.DATAGRAM_BUFFER_SIZE];
        final DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        // listen for reply and block until then (or until timeout)
        final MessageModel response;
        try {
            datagramSocket.receive(datagramPacket);
            response = UdpUtils.deserialize(datagramPacket.getData());
        } catch (IOException e) {
            throw new PortException("Message receiving failed: " + e.getMessage(), e);
        }
        LOGGER.info("Message response received");
        return response instanceof UdpAckMessage ? Optional.absent() : Optional.of(response);
    }

    @Override
    public final void disconnect() {
        if (isConnected()) {
            UdpUtils.close(datagramSocket);
            connected = false;
        }
    }
}
