package com.jwa.pushlistener.communication.rmi.interfaces;

import com.jwa.pushlistener.communication.messagemodel.AAMessage;
import com.jwa.pushlistener.communication.messagemodel.AMessage;
import com.jwa.pushlistener.communication.messagemodel.CCMessage;
import com.jwa.pushlistener.communication.messagemodel.CMessage;
import com.jwa.pushlistener.communication.messagemodel.FFMessage;
import com.jwa.pushlistener.communication.messagemodel.FMessage;
import com.jwa.pushlistener.communication.rmi.components.RMIRemoteInterface;

import java.rmi.RemoteException;

public interface RMIRemoteInterfaceNode2 extends RMIRemoteInterface {
    AAMessage execute(AMessage message) throws RemoteException;
    CCMessage execute(CMessage message) throws RemoteException;
    FFMessage execute(FMessage message) throws RemoteException;
}
