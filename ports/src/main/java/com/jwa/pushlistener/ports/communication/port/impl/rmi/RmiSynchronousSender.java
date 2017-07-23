package com.jwa.pushlistener.ports.communication.port.impl.rmi;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.port.SynchronousSender;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.config.RmiSenderConfig;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiSynchronousSender implements SynchronousSender {
    private final RmiSenderConfig config;

    private RmiInterface remoteInterface;

    public RmiSynchronousSender(RmiSenderConfig config) {
        this.config = config;
    }

    @Override
    public Optional<MessageModel> execute(MessageModel msg) throws IllegalArgumentException, CommunicationException {
        if (msg == null) {
            throw new IllegalArgumentException();
        }
        try {
            return remoteInterface.process(msg);
        } catch (RemoteException e) {
            throw new CommunicationException("...", e);
        }
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
            remoteInterface = (RmiInterface) registry.lookup(config.getNameRemoteobjectRegistryLookup());
        } catch (RemoteException | NotBoundException e) {
            throw new CommunicationException("...", e); // TODO:
        }

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
}
