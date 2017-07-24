package com.jwa.pushlistener.ports.port.impl.rmi;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.PortsException;
import com.jwa.pushlistener.ports.port.SynchronousSender;
import com.jwa.pushlistener.ports.port.impl.rmi.config.RmiSenderConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public final class RmiSynchronousSender implements SynchronousSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(RmiSynchronousSender.class);
    private final RmiSenderConfig config;
    private RmiInterface remoteInterface;

    public RmiSynchronousSender(final RmiSenderConfig config) {
        this.config = config;
    }

    @Override
    public final void connect() throws PortsException {
        try {
            final Registry registry = LocateRegistry.getRegistry(config.getHostname(), config.getPortRegistry());
            remoteInterface = (RmiInterface) registry.lookup(config.getNameRemoteobjectRegistryLookup());
        } catch (RemoteException | NotBoundException e) {
            throw new PortsException("Registry lookup failed: " + e.getMessage(), e);
        }
        LOGGER.info("Connection to RMI server '" + config.getHostname() + "' on registry-port " + config.getPortRegistry() +
                " and registry-remoteobject-name '" + config.getNameRemoteobjectRegistryLookup() +
                "' is now established"
        );
    }

    @Override
    public final Optional<MessageModel> execute(final MessageModel msg) throws IllegalArgumentException, PortsException {
        if (msg == null) {
            throw new IllegalArgumentException();
        }
        final Optional<MessageModel> response;
        try {
            response = remoteInterface.execute(msg);
        } catch (RemoteException e) {
            throw new PortsException("Message executing failed: " + e.getMessage(), e);
        }
        LOGGER.info("Message was executed");
        LOGGER.info("Message response received");
        return response;
    }

    @Override
    public final void disconnect() {
        // Connection between RMI client and server is implicit and closes automatically after a short idle period of time.
        LOGGER.info("Connection to RMI server '" + config.getHostname() + "' on registry-port " + config.getPortRegistry() +
                " and registry-remoteobject-name '" + config.getNameRemoteobjectRegistryLookup() +
                "' was closed"
        );
    }
}
