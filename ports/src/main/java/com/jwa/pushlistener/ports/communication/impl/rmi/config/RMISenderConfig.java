package com.jwa.pushlistener.ports.communication.impl.rmi.config;

public class RMISenderConfig extends RMIGeneralConfig {
    private final String hostname;

    public RMISenderConfig(String hostname, int portRegistry) {
        super(portRegistry);
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }
}
