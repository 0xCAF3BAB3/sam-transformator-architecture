package com.jwa.pushlistener.code.architecture.communication.port.factory;

import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfig;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.RmiPortFactory;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.UdpPortFactory;

public final class PortFactoryProducer {
    private static AbstractPortFactory rmiFactory;
    private static AbstractPortFactory udpFactory;

    private PortFactoryProducer() {}

    public static Port createPort(final PortConfig config) throws IllegalArgumentException {
        final String portStyleKey = "PortStyle";
        switch (config.getValue(portStyleKey)) {
            case "Rmi":
                if (rmiFactory == null) {
                    rmiFactory = new RmiPortFactory();
                }
                return rmiFactory.createPort(config);
            case "Udp":
                if (udpFactory == null) {
                    udpFactory = new UdpPortFactory();
                }
                return udpFactory.createPort(config);
            default:
                throw PortConfig.generateNotImplementedException(portStyleKey);
        }
    }
}
