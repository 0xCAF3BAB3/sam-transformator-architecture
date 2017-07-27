package com.jwa.pushlistener.code.architecture.communication.port.config;

import java.util.HashMap;
import java.util.Map;

public final class PortConfigBuilder {
    private final Map<String, String> config;

    public PortConfigBuilder() {
        this.config = new HashMap<>();
    }

    public final PortConfigBuilder setProperty(final String key, final String value) {
        config.put(key, value);
        return this;
    }

    public final PortConfig build() {
        return new PortConfig(config);
    }
}
