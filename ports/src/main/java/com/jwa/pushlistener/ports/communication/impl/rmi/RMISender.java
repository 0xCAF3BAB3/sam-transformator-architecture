package com.jwa.pushlistener.ports.communication.impl.rmi;

import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.Sender;
import com.jwa.pushlistener.ports.communication.impl.rmi.config.RMISenderConfig;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMISender<T extends RMIPortInterface> implements Sender<T> {
    private RMISenderConfig config;
    private T remoteInterface;
    // TODO: add connected var

    public RMISender(RMISenderConfig config) {
        this.config = config;
    }

    @Override
    public void connect() throws CommunicationException {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(config.getHostname(), config.getPortRegistry());
        } catch (RemoteException e) {
            throw new CommunicationException("...", e); // TODO:
        }

        try {
            remoteInterface = (T) registry.lookup(config.getNameRemoteobjectRegistryLookup());
        } catch (RemoteException | NotBoundException e) {
            throw new CommunicationException("...", e); // TODO:
        }

        this.config = config;

        System.out.println("Connection to RMI server '" + config.getHostname() + "' on registry-port " + config.getPortRegistry() +
                " and registry-remoteobject-name '" + config.getNameRemoteobjectRegistryLookup() +
                "' is now established"
        );
    }

    @Override
    public void disconnect() throws CommunicationException {
        // TODO: implement me
        System.out.println("Connection to RMI server '" + config.getHostname() + "' on registry-port " + config.getPortRegistry() +
                " and registry-remoteobject-name '" + config.getNameRemoteobjectRegistryLookup() +
                "' was closed"
        );
    }

    @Override
    public T getInterface() throws CommunicationException {
        return remoteInterface;
    }
}
