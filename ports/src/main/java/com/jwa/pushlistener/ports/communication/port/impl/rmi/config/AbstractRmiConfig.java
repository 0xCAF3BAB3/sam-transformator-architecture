package com.jwa.pushlistener.ports.communication.port.impl.rmi.config;

abstract class AbstractRmiConfig {
    private final int portRegistry;

    AbstractRmiConfig(int portRegistry) {
        this.portRegistry = portRegistry;
    }

    public int getPortRegistry() {
        return portRegistry;
    }

    public String getNameRemoteobjectRegistryLookup() {
        return "RemoteInterfaceObject";
    }
}
