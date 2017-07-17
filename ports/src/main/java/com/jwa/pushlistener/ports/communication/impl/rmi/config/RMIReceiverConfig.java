package com.jwa.pushlistener.ports.communication.impl.rmi.config;

import com.jwa.pushlistener.ports.communication.impl.rmi.RMIPortInterface;

public class RMIReceiverConfig<T extends RMIPortInterface> extends RMIGeneralConfig {
    private final T interfaceImpl;

    public RMIReceiverConfig(int portRegistry, T interfaceImpl) {
        super(portRegistry);
        this.interfaceImpl = interfaceImpl;
    }

    public T getInterfaceImpl() {
        return interfaceImpl;
    }
}
