package com.jwa.pushlistener.code.architecture.communication.portfactory.factory;

import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.portfactory.context.PortContext;

public abstract class AbstractPortFactory {
    public abstract Port createPort(final PortContext context) throws IllegalArgumentException;
}
