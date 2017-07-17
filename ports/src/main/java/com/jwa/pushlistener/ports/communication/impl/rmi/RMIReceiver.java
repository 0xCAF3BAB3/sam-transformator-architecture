package com.jwa.pushlistener.ports.communication.impl.rmi;

import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.Receiver;
import com.jwa.pushlistener.ports.communication.impl.rmi.config.RMIReceiverConfig;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIReceiver<T extends RMIPortInterface> implements Receiver<T> {
    private final RMIReceiverConfig config;

    public RMIReceiver(RMIReceiverConfig config) {
        this.config = config;
    }

    @Override
    public void start() throws CommunicationException {
        RMIServerRunnable runnable = new RMIServerRunnable();
        Thread t = new Thread(runnable, "RMIServer");
        t.start();
    }

    @Override
    public void shutdown() throws CommunicationException {
        // TODO: implement me
        System.out.println("RMI server (registry-port " + config.getPortRegistry() + ", registry-remoteobject-name '" +
                config.getNameRemoteobjectRegistryLookup() + "') was shutdown"
        );
    }

    private class RMIServerRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Remote stub = UnicastRemoteObject.exportObject(config.getInterfaceImpl(), 0);
                Registry registry = LocateRegistry.createRegistry(config.getPortRegistry());
                registry.bind(config.getNameRemoteobjectRegistryLookup(), stub);
                System.out.println("RMI server (registry-port " + config.getPortRegistry() + ", registry-remoteobject-name '" +
                        config.getNameRemoteobjectRegistryLookup() + "') is now running"
                );
            } catch (RemoteException | AlreadyBoundException e) {
                // TODO:
                e.printStackTrace();
            }
        }
    }
}
