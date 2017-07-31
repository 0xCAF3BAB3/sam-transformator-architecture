package com.jwa.pushlistener.code.architecture.communication.port.factory.config;

import com.jwa.pushlistener.code.architecture.communication.port.factory.AbstractPortFactory;

import java.util.Map;

public final class PortFactoryConfig {
    private final Map<String, AbstractPortFactory> factories;

    PortFactoryConfig(final Map<String, AbstractPortFactory> factories) {
        this.factories = factories;
    }

    public final AbstractPortFactory getFactory(final String name) {
        return factories.get(name);
    }
}
