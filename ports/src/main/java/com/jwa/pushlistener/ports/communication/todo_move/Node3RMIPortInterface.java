package com.jwa.pushlistener.ports.communication.todo_move;

import com.jwa.pushlistener.messagemodel.DMessage;
import com.jwa.pushlistener.messagemodel.EMessage;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMIPortInterface;

import java.rmi.RemoteException;

public interface Node3RMIPortInterface extends RMIPortInterface {
    void execute(DMessage message) throws RemoteException;
    void execute(EMessage message) throws RemoteException;
}
