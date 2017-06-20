package com.jwa.pushlistener.communication.rmi.components;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {
    static final String NAME_REMOTEOBJECT_REGISTRY_LOOKUP = "RemoteInterfaceObject";

    private final int portRegistry;
    private final Remote remoteInterfaceImpl;

    public RMIServer(int portRegistry, Remote remoteInterfaceImpl) {
        this.portRegistry = portRegistry;
        this.remoteInterfaceImpl = remoteInterfaceImpl;
    }

    public void start() {
        RMIServerRunnable runnable = new RMIServerRunnable();
        Thread t = new Thread(runnable, "RMIServer");
        t.start();
    }

    public void shutdown() {
        // TODO: implement me
        System.out.println("RMI server was shutdown");
    }

    private class RMIServerRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Remote stub = UnicastRemoteObject.exportObject(remoteInterfaceImpl, 0);
                Registry registry = LocateRegistry.createRegistry(portRegistry);
                registry.bind(NAME_REMOTEOBJECT_REGISTRY_LOOKUP, stub);
                System.out.println("RMI server is now running");
            } catch (RemoteException | AlreadyBoundException e) {
                e.printStackTrace();
            }
        }
    }
}
