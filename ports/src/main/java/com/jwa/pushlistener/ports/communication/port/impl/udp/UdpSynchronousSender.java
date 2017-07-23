package com.jwa.pushlistener.ports.communication.port.impl.udp;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.port.SynchronousSender;
import com.jwa.pushlistener.ports.communication.port.impl.udp.config.UdpSenderConfig;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UdpSynchronousSender implements SynchronousSender {
    private final UdpSenderConfig config;

    private DatagramSocket datagramSocket;

    public UdpSynchronousSender(UdpSenderConfig config) {
        this.config = config;
    }

    @Override
    public void connect() throws CommunicationException {
        try {
            datagramSocket = new DatagramSocket(0); // use any free port
        } catch (SocketException e) {
            throw new CommunicationException("...", e);
        }
    }

    @Override
    public Optional<MessageModel> execute(MessageModel msg) throws IllegalArgumentException, CommunicationException {
        if (msg == null) {
            throw new IllegalArgumentException();
        }

        try {
            InetSocketAddress recipient = new InetSocketAddress(config.getHostname(), config.getPort());
            UdpUtils.send(msg, recipient, datagramSocket);
        } catch (IOException e) {
            throw new CommunicationException("...", e);
        }

        byte[] buffer = new byte[UdpUtils.DATAGRAM_BUFFER_SIZE];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        // listen for reply and block until then
        // TODO: add timeout (currently infinite)
        try {
            datagramSocket.receive(datagramPacket);
            MessageModel response = UdpUtils.deserialize(datagramPacket.getData());
            return response instanceof UdpAckMessage ? Optional.absent() : Optional.of(response);
        } catch (IOException e) {
            throw new CommunicationException("...", e);
        }
    }

    @Override
    public void disconnect() throws CommunicationException {
        UdpUtils.close(datagramSocket);
    }
}
