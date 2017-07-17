package com.jwa.pushlistener.ports.communication.impl.rmi.config;

public class RMIGeneralConfig {
    private final int portRegistry;

    public RMIGeneralConfig(int portRegistry) {
        this.portRegistry = portRegistry;
    }

    public int getPortRegistry() {
        return portRegistry;
    }

    public String getNameRemoteobjectRegistryLookup() {
        return "RemoteInterfaceObject";
    }
}
