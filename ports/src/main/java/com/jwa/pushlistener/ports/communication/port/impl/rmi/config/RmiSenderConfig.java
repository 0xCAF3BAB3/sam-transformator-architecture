package com.jwa.pushlistener.ports.communication.port.impl.rmi.config;

public class RmiSenderConfig extends AbstractRmiConfig {
    private final String hostname;

    public RmiSenderConfig(String hostname, int portRegistry) {
        super(portRegistry);
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }
}
