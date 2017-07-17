package com.jwa.pushlistener.ports.communication.todo_move;

import com.jwa.pushlistener.messagemodel.AAMessage;
import com.jwa.pushlistener.messagemodel.AMessage;
import com.jwa.pushlistener.messagemodel.CCMessage;
import com.jwa.pushlistener.messagemodel.CMessage;
import com.jwa.pushlistener.messagemodel.FFMessage;
import com.jwa.pushlistener.messagemodel.FMessage;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMIPortInterface;

import java.rmi.RemoteException;

public interface Node2RMIPortInterface extends RMIPortInterface {
    AAMessage execute(AMessage message) throws RemoteException;
    CCMessage execute(CMessage message) throws RemoteException;
    FFMessage execute(FMessage message) throws RemoteException;
}
