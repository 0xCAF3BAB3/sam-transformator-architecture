package com.jwa.pushlistener.node2.communication.rmi.impl;

import com.jwa.pushlistener.messagemodel.AAMessage;
import com.jwa.pushlistener.messagemodel.AMessage;
import com.jwa.pushlistener.messagemodel.CCMessage;
import com.jwa.pushlistener.messagemodel.CMessage;
import com.jwa.pushlistener.messagemodel.FFMessage;
import com.jwa.pushlistener.messagemodel.FMessage;
import com.jwa.pushlistener.ports.rmi.interfaces.RMIRemoteInterfaceNode2;

import java.rmi.RemoteException;

public class RMIRemoteInterfaceImpl implements RMIRemoteInterfaceNode2 {
    @Override
    public AAMessage execute(AMessage message) throws RemoteException {
        // TODO: implement me
        System.out.println("AAMessage:execute(AMessage message) got called");
        return null;
    }

    @Override
    public CCMessage execute(CMessage message) throws RemoteException {
        // TODO: implement me
        System.out.println("CCMessage:execute(CMessage message) got called");
        return null;
    }

    @Override
    public FFMessage execute(FMessage message) throws RemoteException {
        // TODO: implement me
        System.out.println("FFMessage:execute(FMessage message) got called");
        return null;
    }
}
