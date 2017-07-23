package com.jwa.pushlistener.ports.communication.port.impl.udp.config;

abstract class AbstractUdpConfig {
    private final int port;

    AbstractUdpConfig(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
