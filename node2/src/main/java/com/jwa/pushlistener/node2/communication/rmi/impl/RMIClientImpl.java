package com.jwa.pushlistener.node2.communication.rmi.impl;

import com.jwa.pushlistener.messagemodel.BMessage;
import com.jwa.pushlistener.messagemodel.EMessage;
import com.jwa.pushlistener.ports.rmi.components.RMIClient;
import com.jwa.pushlistener.ports.rmi.interfaces.RMIRemoteInterfaceNode1;
import com.jwa.pushlistener.ports.rmi.interfaces.RMIRemoteInterfaceNode3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClientImpl implements AutoCloseable {
    private RMIClient clientNode1;
    private RMIRemoteInterfaceNode1 remoteInterfaceNode1;
    private RMIClient clientNode3;
    private RMIRemoteInterfaceNode3 remoteInterfaceNode3;

    public void execute(BMessage message) throws RemoteException, NotBoundException {
        getClientNode1().execute(message);
    }

    public void execute(EMessage message) throws RemoteException, NotBoundException {
        getClientNode3().execute(message);
    }

    @Override
    public void close() {
        if (clientNode1 != null) {
            clientNode1.close();
        }
        if (clientNode3 != null) {
            clientNode3.close();
        }
    }

    private RMIRemoteInterfaceNode1 getClientNode1() throws RemoteException, NotBoundException {
        if (clientNode1 == null) {
            clientNode1 = new RMIClient("127.0.0.1", 1101);
            remoteInterfaceNode1 = (RMIRemoteInterfaceNode1) clientNode1.getRemoteInterface();
        }
        return remoteInterfaceNode1;
    }

    private RMIRemoteInterfaceNode3 getClientNode3() throws RemoteException, NotBoundException {
        if (clientNode3 == null) {
            clientNode3 = new RMIClient("127.0.0.1", 1103);
            remoteInterfaceNode3 = (RMIRemoteInterfaceNode3) clientNode3.getRemoteInterface();
        }
        return remoteInterfaceNode3;
    }
}
