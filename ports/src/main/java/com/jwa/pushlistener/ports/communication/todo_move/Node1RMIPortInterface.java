package com.jwa.pushlistener.ports.communication.todo_move;

import com.jwa.pushlistener.messagemodel.BMessage;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMIPortInterface;

import java.rmi.RemoteException;

public interface Node1RMIPortInterface extends RMIPortInterface {
    void execute(BMessage message) throws RemoteException;
}
