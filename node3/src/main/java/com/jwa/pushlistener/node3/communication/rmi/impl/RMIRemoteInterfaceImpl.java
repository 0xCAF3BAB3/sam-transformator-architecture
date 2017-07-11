package com.jwa.pushlistener.node3.communication.rmi.impl;

import com.jwa.pushlistener.messagemodel.DMessage;
import com.jwa.pushlistener.messagemodel.EMessage;
import com.jwa.pushlistener.communication.rmi.interfaces.RMIRemoteInterfaceNode3;

import java.rmi.RemoteException;

public class RMIRemoteInterfaceImpl implements RMIRemoteInterfaceNode3 {
    @Override
    public void execute(EMessage message) throws RemoteException {
        // TODO: implement me
        System.out.println("execute(EMessage message) got called");
    }

    @Override
    public void execute(DMessage message) throws RemoteException {
        // TODO: implement me
        System.out.println("execute(DMessage message) got called");
    }
}
