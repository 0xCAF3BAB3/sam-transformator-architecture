package com.jwa.pushlistener.ports.rmi.interfaces;

import com.jwa.pushlistener.messagemodel.BMessage;
import com.jwa.pushlistener.ports.rmi.components.RMIRemoteInterface;

import java.rmi.RemoteException;

public interface RMIRemoteInterfaceNode1 extends RMIRemoteInterface {
    void execute(BMessage message) throws RemoteException;
}
