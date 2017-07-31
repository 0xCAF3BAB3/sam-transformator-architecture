package com.jwa.pushlistener.code.architecture.communication.port.factory;

import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfig;
import com.jwa.pushlistener.code.architecture.communication.port.factory.config.PortFactoryConfig;

public final class PortFactory {
    private final PortFactoryConfig factoryConfig;

    public PortFactory(final PortFactoryConfig factoryConfig) throws IllegalArgumentException {
        if (factoryConfig == null) {
            throw new IllegalArgumentException("Passed factory-config is null");
        }
        this.factoryConfig = factoryConfig;
    }

    public final Port createPort(final PortConfig portConfig) throws IllegalArgumentException {
        if (portConfig == null) {
            throw new IllegalArgumentException("Passed port-config is null");
        }
        final String portStyle = portConfig.getStyle();
        final AbstractPortFactory factory = factoryConfig.getFactory(portStyle);
        if (factory == null) {
            throw portConfig.generateNoImplementationFoundForStyleException();
        }
        return factory.createPort(portConfig);
    }
}
