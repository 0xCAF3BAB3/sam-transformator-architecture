package com.jwa.pushlistener.code.architecture.ports.port.impl.udp.config;

abstract class AbstractUdpConfig {
    private final int port;

    AbstractUdpConfig(final int port) {
        this.port = port;
    }

    public final int getPort() {
        return port;
    }
}
