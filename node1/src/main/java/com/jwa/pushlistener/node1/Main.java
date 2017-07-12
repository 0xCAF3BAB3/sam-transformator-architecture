package com.jwa.pushlistener.node1;

import com.jwa.pushlistener.messagemodel.AMessage;
import com.jwa.pushlistener.messagemodel.CMessage;
import com.jwa.pushlistener.messagemodel.DMessage;
import com.jwa.pushlistener.ports.rmi.components.RMIServer;
import com.jwa.pushlistener.node1.communication.rmi.impl.RMIClientImpl;
import com.jwa.pushlistener.node1.communication.rmi.impl.RMIRemoteInterfaceImpl;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {
    public static void main( String[] args ) {
        RMIRemoteInterfaceImpl impl = new RMIRemoteInterfaceImpl();
        RMIServer rmiServer = new RMIServer(1101, impl);
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
                rmiClient.execute(new AMessage());
                rmiClient.execute(new CMessage());
                rmiClient.execute(new DMessage());
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
