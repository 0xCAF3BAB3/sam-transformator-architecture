package com.jwa.pushlistener.ports.factory;

import com.jwa.pushlistener.ports.port.AsynchronousSenderCallback;
import com.jwa.pushlistener.ports.port.Port;
import com.jwa.pushlistener.ports.port.ReceiverHandler;

public abstract class PortFactory {
    public abstract Port getReceiverPort(final int port, final ReceiverHandler handler);
    public abstract Port getSynchronousSenderPort(final String hostname, final int port);
    public abstract Port getAsynchronousSenderPort(final String hostname, final int port, final AsynchronousSenderCallback callback);
}
