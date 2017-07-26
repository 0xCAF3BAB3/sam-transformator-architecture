package com.jwa.pushlistener.code.architecture.ports.factory;

public final class PortAbstractFactoryProducer {
    public static PortAbstractFactory getFactory() {
        // TODO: replace through PortFactoryContext implementation
        return new RmiPortFactory();
    }
}
