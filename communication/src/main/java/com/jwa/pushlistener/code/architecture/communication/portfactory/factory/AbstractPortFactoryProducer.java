package com.jwa.pushlistener.code.architecture.communication.portfactory.factory;

import com.jwa.pushlistener.code.architecture.communication.portfactory.context.PortContext;
import com.jwa.pushlistener.code.architecture.communication.portfactory.context.PortContextKey;
import com.jwa.pushlistener.code.architecture.communication.portfactory.factory.impl.RmiPortFactory;
import com.jwa.pushlistener.code.architecture.communication.portfactory.factory.impl.UdpPortFactory;

public final class AbstractPortFactoryProducer {
    private static RmiPortFactory rmiFactoryDelegator;
    private static UdpPortFactory udpFactoryDelegator;

    private AbstractPortFactoryProducer() {}

    public static AbstractPortFactory getFactory(final PortContext context) throws IllegalArgumentException {
        if (context.getContext().containsKey(PortContextKey.STYLE_RMI)) {
            if (rmiFactoryDelegator == null) {
                rmiFactoryDelegator = new RmiPortFactory();
            }
            return rmiFactoryDelegator;
        } else if (context.getContext().containsKey(PortContextKey.STYLE_UDP)) {
            if (udpFactoryDelegator == null) {
                udpFactoryDelegator = new UdpPortFactory();
            }
            return udpFactoryDelegator;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
