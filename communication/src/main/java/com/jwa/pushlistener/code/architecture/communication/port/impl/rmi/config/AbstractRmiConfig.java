package com.jwa.pushlistener.code.architecture.communication.port.impl.rmi.config;

abstract class AbstractRmiConfig {
    private final int portRegistry;

    AbstractRmiConfig(final int portRegistry) {
        this.portRegistry = portRegistry;
    }

    public final int getPortRegistry() {
        return portRegistry;
    }

    public final String getNameRemoteobjectRegistryLookup() {
        return "RemoteInterfaceObject";
    }
}