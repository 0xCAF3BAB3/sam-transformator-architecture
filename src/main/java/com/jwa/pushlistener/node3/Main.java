package com.jwa.pushlistener.node3;

import com.jwa.pushlistener.communication.messagemodel.FMessage;
import com.jwa.pushlistener.node3.communication.rmi.impl.RMIClientImpl;
import com.jwa.pushlistener.node3.communication.rmi.impl.RMIRemoteInterfaceImpl;
import com.jwa.pushlistener.communication.rmi.components.RMIServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {
    public static void main( String[] args ) {
        RMIRemoteInterfaceImpl impl = new RMIRemoteInterfaceImpl();
        RMIServer rmiServer = new RMIServer(1103, impl);
        rmiServer.start();
        try (RMIClientImpl rmiClient = new RMIClientImpl()) {
            // TODO: implement me
            // wait a bit until we manually start the other nodes (= their Main.java or Maven profile)
            try {
                Thread.sleep(20 * 1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            try {
                rmiClient.execute(new FMessage());
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(20 * 1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } finally {
            rmiServer.shutdown();
        }
    }
}
