package com.jwa.pushlistener.code.architecture.communication.port.config;

import java.util.HashMap;
import java.util.Map;

public final class PortConfigBuilder {
    private final Map<String, String> parameters;

    public PortConfigBuilder() {
        this.parameters = new HashMap<>();
    }

    public final PortConfigBuilder setParameter(final String key, final String value) {
        parameters.put(key, value);
        return this;
    }

    public final PortConfig build() {
        return new PortConfig(parameters);
    }
}
