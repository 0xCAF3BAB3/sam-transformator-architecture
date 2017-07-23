package com.jwa.pushlistener.ports.communication.port.impl.udp.config;

public class UdpSenderConfig extends AbstractUdpConfig {
    private final String hostname;

    public UdpSenderConfig(String hostname, int port) {
        super(port);
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }
}
