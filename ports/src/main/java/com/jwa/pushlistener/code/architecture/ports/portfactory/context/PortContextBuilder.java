package com.jwa.pushlistener.code.architecture.ports.portfactory.context;

import java.util.HashMap;
import java.util.Map;

public final class PortContextBuilder {
    private final Map<PortContextKey, String> context;

    public PortContextBuilder() {
        this.context = new HashMap<>();
    }

    public final PortContextBuilder setRmiHostname(String hostname) {
        context.put(PortContextKey.RMI_HOSTNAME, hostname);
        return this;
    }

    // TODO: add all methods

    public final PortContext build() throws IllegalArgumentException {
        // TODO: check conditions
        return new PortContext(context);
    }
}
