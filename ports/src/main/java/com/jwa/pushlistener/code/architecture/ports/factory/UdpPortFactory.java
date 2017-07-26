package com.jwa.pushlistener.code.architecture.ports.factory;

import com.jwa.pushlistener.code.architecture.ports.port.AsynchronousSenderCallback;
import com.jwa.pushlistener.code.architecture.ports.port.Port;
import com.jwa.pushlistener.code.architecture.ports.port.ReceiverHandler;
import com.jwa.pushlistener.code.architecture.ports.port.impl.udp.UdpAsynchronousSender;
import com.jwa.pushlistener.code.architecture.ports.port.impl.udp.UdpReceiver;
import com.jwa.pushlistener.code.architecture.ports.port.impl.udp.UdpSynchronousSender;
import com.jwa.pushlistener.code.architecture.ports.port.impl.udp.config.UdpReceiverConfig;
import com.jwa.pushlistener.code.architecture.ports.port.impl.udp.config.UdpSenderConfig;

public final class UdpPortFactory extends PortAbstractFactory {
    @Override
    public final Port getReceiverPort(final int port, final ReceiverHandler handler) {
        final UdpReceiverConfig config = new UdpReceiverConfig(port);
        final UdpReceiver receiver = new UdpReceiver(config);
        receiver.setHandler(handler);
        return receiver;
    }

    @Override
    public final Port getSynchronousSenderPort(final String hostname, final int port) {
        final UdpSenderConfig config = new UdpSenderConfig(hostname, port);
        return new UdpSynchronousSender(config);
    }

    @Override
    public final Port getAsynchronousSenderPort(final String hostname, final int port, final AsynchronousSenderCallback callback) {
        final UdpSenderConfig config = new UdpSenderConfig(hostname, port);
        final UdpAsynchronousSender sender = new UdpAsynchronousSender(config);
        sender.setCallback(callback);
        return sender;
    }
}
