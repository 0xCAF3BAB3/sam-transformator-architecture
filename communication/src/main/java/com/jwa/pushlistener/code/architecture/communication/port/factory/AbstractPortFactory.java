package com.jwa.pushlistener.code.architecture.communication.port.factory;

import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfig;

public abstract class AbstractPortFactory {
    public abstract Port createPort(final PortConfig config) throws IllegalArgumentException;
}
