package com.jwa.pushlistener.code.architecture.ports.portfactory.factory;

import com.jwa.pushlistener.code.architecture.ports.port.Port;
import com.jwa.pushlistener.code.architecture.ports.portfactory.context.PortContext;

public abstract class AbstractPortFactory {
    public abstract Port createPort(final PortContext context) throws IllegalArgumentException;
}
