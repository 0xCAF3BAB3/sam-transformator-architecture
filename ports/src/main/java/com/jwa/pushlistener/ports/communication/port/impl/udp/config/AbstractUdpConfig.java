package com.jwa.pushlistener.ports.communication.port.impl.udp.config;

abstract class AbstractUdpConfig {
    private final int port;

    AbstractUdpConfig(final int port) {
        this.port = port;
    }

    public final int getPort() {
        return port;
    }
}
