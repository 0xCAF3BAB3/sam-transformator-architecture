package com.jwa.pushlistener.code.architecture.communication.portfactory.factory.impl;

import com.jwa.pushlistener.code.architecture.communication.port.AsynchronousSender;
import com.jwa.pushlistener.code.architecture.communication.port.Receiver;
import com.jwa.pushlistener.code.architecture.communication.port.Sender;
import com.jwa.pushlistener.code.architecture.communication.port.SynchronousSender;
import com.jwa.pushlistener.code.architecture.communication.portfactory.factory.AbstractPortFactory;
import com.jwa.pushlistener.code.architecture.communication.portfactory.context.PortContext;
import com.jwa.pushlistener.code.architecture.communication.portfactory.context.PortContextKey;
import com.jwa.pushlistener.code.architecture.communication.port.Port;

public final class RmiPortFactory extends AbstractPortFactory {
    @Override
    public final Port createPort(final PortContext context) throws IllegalArgumentException {
        if (context.getContext().containsKey(PortContextKey.TYPE_RECEIVER)) {
            return createReceiver(context);
        } else if (context.getContext().containsKey(PortContextKey.TYPE_SENDER)) {
            return createSender(context);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Receiver createReceiver(PortContext context) {
        // TODO: implement me
        throw new IllegalArgumentException();
    }

    private Sender createSender(PortContext context) {
        // TODO: implement me
        throw new IllegalArgumentException();
    }

    private SynchronousSender createSynchronousSender(PortContext context) {
        // TODO: implement me
        throw new IllegalArgumentException();
    }

    private AsynchronousSender createAsynchronousSender(PortContext context) {
        throw new UnsupportedOperationException();
    }
}
