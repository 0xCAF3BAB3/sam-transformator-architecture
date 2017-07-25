package com.jwa.pushlistener.architecture.ports.port.impl.udp.config;

public final class UdpSenderConfig extends AbstractUdpConfig {
    private final String hostname;

    public UdpSenderConfig(final String hostname, final int port) {
        super(port);
        this.hostname = hostname;
    }

    public final String getHostname() {
        return hostname;
    }
}
