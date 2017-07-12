package com.jwa.pushlistener.ports.rmi.interfaces;

import com.jwa.pushlistener.messagemodel.DMessage;
import com.jwa.pushlistener.messagemodel.EMessage;
import com.jwa.pushlistener.ports.rmi.components.RMIRemoteInterface;

import java.rmi.RemoteException;

public interface RMIRemoteInterfaceNode3 extends RMIRemoteInterface {
    void execute(EMessage message) throws RemoteException;
    void execute(DMessage message) throws RemoteException;
}
