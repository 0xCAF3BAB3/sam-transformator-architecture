package com.jwa.pushlistener.communication.rmi.components;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient implements AutoCloseable {
    private final String hostname;
    private final int portRegistry;
    private final RMIRemoteInterface remoteInterface;

    public RMIClient(String hostname, int portRegistry) throws RemoteException, NotBoundException {
        this.hostname = hostname;
        this.portRegistry = portRegistry;
        Registry registry = LocateRegistry.getRegistry(hostname, portRegistry);
        remoteInterface = (RMIRemoteInterface) registry.lookup(RMIServer.NAME_REMOTEOBJECT_REGISTRY_LOOKUP);
        System.out.println("Connection to RMI server '" + hostname + "' on registry-port " + portRegistry +
                " and registry-remoteobject-name '" + RMIServer.NAME_REMOTEOBJECT_REGISTRY_LOOKUP +
                "' is now established"
        );
    }

    public RMIRemoteInterface getRemoteInterface() {
        return remoteInterface;
    }

    @Override
    public void close() {
        // TODO: implement me
        System.out.println("Connection to RMI server '" + hostname + "' on registry-port " + portRegistry +
                " and registry-remoteobject-name '" + RMIServer.NAME_REMOTEOBJECT_REGISTRY_LOOKUP +
                "' was closed"
        );
    }
}
