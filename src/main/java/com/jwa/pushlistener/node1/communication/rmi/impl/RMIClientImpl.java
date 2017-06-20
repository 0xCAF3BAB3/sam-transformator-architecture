package com.jwa.pushlistener.node1.communication.rmi.impl;

import com.jwa.pushlistener.communication.messagemodel.AAMessage;
import com.jwa.pushlistener.communication.messagemodel.AMessage;
import com.jwa.pushlistener.communication.messagemodel.CCMessage;
import com.jwa.pushlistener.communication.messagemodel.CMessage;
import com.jwa.pushlistener.communication.messagemodel.DMessage;
import com.jwa.pushlistener.communication.rmi.components.RMIClient;
import com.jwa.pushlistener.communication.rmi.interfaces.RMIRemoteInterfaceNode2;
import com.jwa.pushlistener.communication.rmi.interfaces.RMIRemoteInterfaceNode3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClientImpl implements AutoCloseable {
    private RMIClient clientNode2;
    private RMIRemoteInterfaceNode2 remoteInterfaceNode2;
    private RMIClient clientNode3;
    private RMIRemoteInterfaceNode3 remoteInterfaceNode3;

    public AAMessage execute(AMessage message) throws RemoteException, NotBoundException {
        return getClientNode2().execute(message);
    }

    public CCMessage execute(CMessage message) throws RemoteException, NotBoundException {
        return getClientNode2().execute(message);
    }

    public void execute(DMessage message) throws RemoteException, NotBoundException {
        getClientNode3().execute(message);
    }

    @Override
    public void close() {
        RMIClient.close(clientNode2);
        RMIClient.close(clientNode3);
    }

    private RMIRemoteInterfaceNode2 getClientNode2() throws RemoteException, NotBoundException {
        if (clientNode2 == null) {
            clientNode2 = new RMIClient("127.0.0.1", 1102);
            remoteInterfaceNode2 = (RMIRemoteInterfaceNode2) clientNode2.getRemoteInterface();
        }
        return remoteInterfaceNode2;
    }

    private RMIRemoteInterfaceNode3 getClientNode3() throws RemoteException, NotBoundException {
        if (clientNode3 == null) {
            clientNode3 = new RMIClient("127.0.0.1", 1103);
            remoteInterfaceNode3 = (RMIRemoteInterfaceNode3) clientNode3.getRemoteInterface();
        }
        return remoteInterfaceNode3;
    }
}
