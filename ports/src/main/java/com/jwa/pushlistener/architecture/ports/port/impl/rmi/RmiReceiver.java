package com.jwa.pushlistener.architecture.ports.port.impl.rmi;

import com.google.common.base.Optional;

import com.jwa.pushlistener.architecture.messagemodel.MessageModel;
import com.jwa.pushlistener.architecture.ports.PortsException;
import com.jwa.pushlistener.architecture.ports.port.ReceiverHandler;
import com.jwa.pushlistener.architecture.ports.port.Receiver;
import com.jwa.pushlistener.architecture.ports.port.impl.rmi.config.RmiReceiverConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public final class RmiReceiver implements Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RmiReceiver.class);
    private final RmiReceiverConfig config;
    private ReceiverHandler handler;
    private RMIServerRunnable rmiServerRunnable;

    public RmiReceiver(final RmiReceiverConfig config) {
        this.config = config;
    }

    @Override
    public final void register(final ReceiverHandler handler) {
        this.handler = handler;
    }

    @Override
    public final void start() throws PortsException {
        rmiServerRunnable = new RMIServerRunnable();
        final Thread t = new Thread(rmiServerRunnable, "RmiReceiver{portRegistry=" + config.getPortRegistry() + "}");
        t.start();
        LOGGER.info("RmiReceiver started");
    }

    @Override
    public final void shutdown() {
        rmiServerRunnable.shutdown();
        LOGGER.info("RmiReceiver{portRegistry=" + config.getPortRegistry() + ", nameRemoteobjectRegistryLookup='" +
                config.getNameRemoteobjectRegistryLookup() + "'} was shutdown"
        );
    }

    private final class RMIServerRunnable implements Runnable {
        private final class DelegateRmiInterface extends UnicastRemoteObject implements RmiInterface {
            private DelegateRmiInterface() throws RemoteException {}

            @Override
            public final Optional<MessageModel> execute(final MessageModel msg) throws RemoteException {
                return handler.handle(msg);
            }
        }

        private Registry registry;
        private Remote delegateInterfaceImpl;

        @Override
        public final void run() {
            try {
                registry = LocateRegistry.createRegistry(config.getPortRegistry());
                delegateInterfaceImpl = new DelegateRmiInterface();
                registry.bind(config.getNameRemoteobjectRegistryLookup(), delegateInterfaceImpl);
            } catch (RemoteException | AlreadyBoundException e) {
                LOGGER.error("Registry setup failed: " + e.getMessage(), e);
                return;
            }
            LOGGER.info("RmiReceiver{portRegistry=" + config.getPortRegistry() + ", nameRemoteobjectRegistryLookup='" +
                    config.getNameRemoteobjectRegistryLookup() + "'} is now running"
            );
        }

        final void shutdown() {
            if (registry != null) {
                try {
                    registry.unbind(config.getNameRemoteobjectRegistryLookup());
                } catch (RemoteException | NotBoundException e) {
                    LOGGER.warn("Removing registry binding for interface failed: " + e.getMessage(), e);
                }
                try {
                    UnicastRemoteObject.unexportObject(registry, true);
                } catch (NoSuchObjectException e) {
                    LOGGER.warn("Removing registry from RMI runtime failed: " + e.getMessage(), e);
                }
            }
            if (delegateInterfaceImpl != null) {
                try {
                    UnicastRemoteObject.unexportObject(delegateInterfaceImpl, true);
                } catch (NoSuchObjectException e) {
                    LOGGER.warn("Removing interface from RMI runtime failed: " + e.getMessage(), e);
                }
            }
        }
    }
}
