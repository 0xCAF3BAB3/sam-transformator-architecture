package com.jwa.pushlistener.code.architecture.communication.port.factory.config;

import com.jwa.pushlistener.code.architecture.communication.port.factory.AbstractPortFactory;

import java.util.HashMap;
import java.util.Map;

public final class PortFactoryConfigBuilder {
    private final Map<String, AbstractPortFactory> factories;

    public PortFactoryConfigBuilder() {
        this.factories = new HashMap<>();
    }

    public final PortFactoryConfigBuilder setFactory(final String portStyleName, final AbstractPortFactory instance) {
        factories.put(portStyleName, instance);
        return this;
    }

    public final PortFactoryConfig build() {
        return new PortFactoryConfig(factories);
    }
}
