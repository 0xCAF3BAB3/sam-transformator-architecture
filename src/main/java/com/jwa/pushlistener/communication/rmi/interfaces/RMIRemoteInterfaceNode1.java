package com.jwa.pushlistener.communication.rmi.interfaces;

import com.jwa.pushlistener.communication.messagemodel.BMessage;
import com.jwa.pushlistener.communication.rmi.components.RMIRemoteInterface;

import java.rmi.RemoteException;

public interface RMIRemoteInterfaceNode1 extends RMIRemoteInterface {
    void execute(BMessage message) throws RemoteException;
}
