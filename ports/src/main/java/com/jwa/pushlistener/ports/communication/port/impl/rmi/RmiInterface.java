package com.jwa.pushlistener.ports.communication.port.impl.rmi;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInterface extends Remote {
    Optional<MessageModel> execute(final MessageModel msg) throws RemoteException;
}
