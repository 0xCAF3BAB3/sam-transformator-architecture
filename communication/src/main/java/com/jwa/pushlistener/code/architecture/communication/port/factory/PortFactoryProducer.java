package com.jwa.pushlistener.code.architecture.communication.port.factory;

import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfig;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.RmiPortFactory;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.UdpPortFactory;

public final class PortFactoryProducer implements AbstractPortFactory {
    private static PortFactoryProducer instance = null;
    private AbstractPortFactory rmiFactory;
    private AbstractPortFactory udpFactory;

    private PortFactoryProducer() {}

    public static PortFactoryProducer getInstance() {
        if(instance == null) {
            instance = new PortFactoryProducer();
        }
        return instance;
    }

    public final Port createPort(final PortConfig config) throws IllegalArgumentException {
        final String portStyleParameterKey = "PortStyle";
        switch (config.getParameter(portStyleParameterKey)) {
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
                throw PortConfig.generateNotImplementedException(portStyleParameterKey);
        }
    }
}
