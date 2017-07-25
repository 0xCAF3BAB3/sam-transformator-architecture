package com.jwa.pushlistener.code.architecture.ports.factory;

public final class PortFactoryProducer {
    public static PortFactory getRmiFactory() {
        return new RmiPortFactory();
    }

    public static PortFactory getUdpFactory() {
        return new UdpPortFactory();
    }
}
