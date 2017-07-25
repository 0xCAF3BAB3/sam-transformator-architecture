package com.jwa.pushlistener.architecture.ports.port.impl.rmi.config;

public final class RmiSenderConfig extends AbstractRmiConfig {
    private final String hostname;

    public RmiSenderConfig(final String hostname, final int portRegistry) {
        super(portRegistry);
        this.hostname = hostname;
    }

    public final String getHostname() {
        return hostname;
    }
}
