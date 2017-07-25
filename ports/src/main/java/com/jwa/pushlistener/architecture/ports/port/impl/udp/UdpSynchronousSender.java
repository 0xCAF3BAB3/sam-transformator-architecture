package com.jwa.pushlistener.architecture.ports.port.impl.udp;

import com.google.common.base.Optional;

import com.jwa.pushlistener.architecture.messagemodel.MessageModel;
import com.jwa.pushlistener.architecture.ports.PortsException;
import com.jwa.pushlistener.architecture.ports.port.SynchronousSender;
import com.jwa.pushlistener.architecture.ports.port.impl.udp.config.UdpSenderConfig;

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

    public UdpSynchronousSender(final UdpSenderConfig config) {
        this.config = config;
    }

    @Override
    public final void connect() throws PortsException {
        try {
            datagramSocket = new DatagramSocket(0); // 0 = use any free port
        } catch (SocketException e) {
            throw new PortsException("Datagram-socket creation failed: " + e.getMessage(), e);
        }
    }

    @Override
    public final Optional<MessageModel> execute(final MessageModel msg) throws IllegalArgumentException, PortsException {
        if (msg == null) {
            throw new IllegalArgumentException();
        }
        try {
            final InetSocketAddress recipient = new InetSocketAddress(config.getHostname(), config.getPort());
            UdpUtils.send(msg, recipient, datagramSocket);
        } catch (IOException e) {
            throw new PortsException("Message sending failed: " + e.getMessage(), e);
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
            throw new PortsException("Message receiving failed: " + e.getMessage(), e);
        }
        LOGGER.info("Message response received");
        return response instanceof UdpAckMessage ? Optional.absent() : Optional.of(response);
    }

    @Override
    public final void disconnect() {
        UdpUtils.close(datagramSocket);
    }
}
