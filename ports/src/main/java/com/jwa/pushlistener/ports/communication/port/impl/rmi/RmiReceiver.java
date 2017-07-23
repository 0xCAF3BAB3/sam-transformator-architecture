package com.jwa.pushlistener.ports.communication.port.impl.rmi;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.port.ReceiverHandler;
import com.jwa.pushlistener.ports.communication.port.Receiver;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.config.RmiReceiverConfig;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiReceiver implements Receiver {
    private final RmiReceiverConfig config;

    private ReceiverHandler handler;

    public RmiReceiver(RmiReceiverConfig config) {
        this.config = config;
    }

    @Override
    public void register(ReceiverHandler handler) {
        this.handler = handler;
    }

    @Override
    public void start() throws CommunicationException {
        Runnable runnable = new RMIServerRunnable();
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
        private class DelegateRmiInterface extends UnicastRemoteObject implements RmiInterface {
            private DelegateRmiInterface() throws RemoteException {}

            @Override
            public Optional<MessageModel> process(MessageModel msg) throws RemoteException {
                return handler.handle(msg);
            }
        }

        @Override
        public void run() {
            try {
                Registry registry = LocateRegistry.createRegistry(config.getPortRegistry());
                Remote delegateInterfaceImpl = new DelegateRmiInterface();
                registry.bind(config.getNameRemoteobjectRegistryLookup(), delegateInterfaceImpl);
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
