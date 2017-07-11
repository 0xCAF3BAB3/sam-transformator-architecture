package com.jwa.pushlistener.node3.communication.rmi.impl;

import com.jwa.pushlistener.messagemodel.FFMessage;
import com.jwa.pushlistener.messagemodel.FMessage;
import com.jwa.pushlistener.communication.rmi.components.RMIClient;
import com.jwa.pushlistener.communication.rmi.interfaces.RMIRemoteInterfaceNode2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClientImpl implements AutoCloseable {
    private RMIClient clientNode2;
    private RMIRemoteInterfaceNode2 remoteInterfaceNode2;

    public FFMessage execute(FMessage message) throws RemoteException, NotBoundException {
        return getClientNode2().execute(message);
    }

    @Override
    public void close() {
        if (clientNode2 != null) {
            clientNode2.close();
        }
    }

    private RMIRemoteInterfaceNode2 getClientNode2() throws RemoteException, NotBoundException {
        if (clientNode2 == null) {
            clientNode2 = new RMIClient("127.0.0.1", 1102);
            remoteInterfaceNode2 = (RMIRemoteInterfaceNode2) clientNode2.getRemoteInterface();
        }
        return remoteInterfaceNode2;
    }
}
