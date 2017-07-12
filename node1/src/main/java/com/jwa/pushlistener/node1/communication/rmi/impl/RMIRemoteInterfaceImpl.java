package com.jwa.pushlistener.node1.communication.rmi.impl;

import com.jwa.pushlistener.messagemodel.BMessage;
import com.jwa.pushlistener.ports.rmi.interfaces.RMIRemoteInterfaceNode1;

import java.rmi.RemoteException;

public class RMIRemoteInterfaceImpl implements RMIRemoteInterfaceNode1 {
    @Override
    public void execute(BMessage message) throws RemoteException {
        // TODO: implement me
        System.out.println("execute(BMessage message) got called");
    }
}
