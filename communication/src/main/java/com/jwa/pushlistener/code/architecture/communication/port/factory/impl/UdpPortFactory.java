package com.jwa.pushlistener.code.architecture.communication.port.factory.impl;

import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfig;
import com.jwa.pushlistener.code.architecture.communication.port.factory.AbstractPortFactory;
import com.jwa.pushlistener.code.architecture.communication.port.impl.udp.UdpAsynchronousSender;
import com.jwa.pushlistener.code.architecture.communication.port.impl.udp.UdpReceiver;
import com.jwa.pushlistener.code.architecture.communication.port.impl.udp.UdpSynchronousSender;
import com.jwa.pushlistener.code.architecture.communication.port.impl.udp.config.UdpReceiverConfig;
import com.jwa.pushlistener.code.architecture.communication.port.impl.udp.config.UdpSenderConfig;

public final class UdpPortFactory extends AbstractPortFactory {
    private static final String PROPERTY_PREFIX = "Udp.";

    @Override
    public final Port createPort(final PortConfig config) throws IllegalArgumentException {
        final String portTypeKey = "PortType";
        switch (config.getValue(portTypeKey)) {
            case "Receiver":
                return createReceiverPort(config);
            case "Sender/SynchronousSender":
                return createSynchronousSender(config);
            case "Sender/AsynchronousSender":
                return createAsynchronousSender(config);
            default:
                throw PortConfig.generateNotImplementedException(portTypeKey);
        }
    }

    private UdpReceiver createReceiverPort(final PortConfig config) throws IllegalArgumentException {
        final int port = config.getPropertyValueInt(PROPERTY_PREFIX + "port");
        final UdpReceiverConfig receiverConfig = new UdpReceiverConfig(port);
        return new UdpReceiver(receiverConfig);
    }

    private UdpSynchronousSender createSynchronousSender(final PortConfig config) throws IllegalArgumentException {
        final String hostname = config.getPropertyValue(PROPERTY_PREFIX + "hostname");
        final int port = config.getPropertyValueInt(PROPERTY_PREFIX + "port");
        final UdpSenderConfig senderConfig = new UdpSenderConfig(hostname, port);
        return new UdpSynchronousSender(senderConfig);
    }

    private UdpAsynchronousSender createAsynchronousSender(final PortConfig config) throws IllegalArgumentException {
        final String hostname = config.getPropertyValue(PROPERTY_PREFIX + "hostname");
        final int port = config.getPropertyValueInt(PROPERTY_PREFIX + "port");
        final UdpSenderConfig senderConfig = new UdpSenderConfig(hostname, port);
        return new UdpAsynchronousSender(senderConfig);
    }
}
