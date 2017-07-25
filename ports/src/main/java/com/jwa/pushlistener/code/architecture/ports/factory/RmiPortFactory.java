package com.jwa.pushlistener.code.architecture.ports.factory;

import com.jwa.pushlistener.code.architecture.ports.port.AsynchronousSenderCallback;
import com.jwa.pushlistener.code.architecture.ports.port.Port;
import com.jwa.pushlistener.code.architecture.ports.port.ReceiverHandler;
import com.jwa.pushlistener.code.architecture.ports.port.impl.rmi.RmiReceiver;
import com.jwa.pushlistener.code.architecture.ports.port.impl.rmi.RmiSynchronousSender;
import com.jwa.pushlistener.code.architecture.ports.port.impl.rmi.config.RmiReceiverConfig;
import com.jwa.pushlistener.code.architecture.ports.port.impl.rmi.config.RmiSenderConfig;

public final class RmiPortFactory extends PortFactory {
    @Override
    public final Port getReceiverPort(final int port, final ReceiverHandler handler) {
        final RmiReceiverConfig config = new RmiReceiverConfig(port);
        final RmiReceiver receiver = new RmiReceiver(config);
        receiver.register(handler);
        return receiver;
    }

    @Override
    public final Port getSynchronousSenderPort(final String hostname, final int port) {
        final RmiSenderConfig config = new RmiSenderConfig(hostname, port);
        return new RmiSynchronousSender(config);
    }

    @Override
    public final Port getAsynchronousSenderPort(final String hostname, final int port, final AsynchronousSenderCallback callback) {
        throw new UnsupportedOperationException();
    }
}
