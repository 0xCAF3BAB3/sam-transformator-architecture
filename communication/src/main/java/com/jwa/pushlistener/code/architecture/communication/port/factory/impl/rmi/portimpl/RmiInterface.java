package com.jwa.pushlistener.code.architecture.communication.port.factory.impl.rmi.portimpl;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInterface extends Remote {
    Optional<MessageModel> execute(final MessageModel msg) throws RemoteException;
}
