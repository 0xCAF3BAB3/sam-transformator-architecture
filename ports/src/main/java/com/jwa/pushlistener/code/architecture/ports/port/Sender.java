package com.jwa.pushlistener.code.architecture.ports.port;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;

public interface Sender extends Port {
    /**
     *
     * @throws PortException if connection can't be established or already connected
     */
    void connect() throws PortException;

    boolean isConnected();

    /**
     *
     * @param msg must not be null
     * @return Optional<MessageModel> if communication is synchronous, Optional.empty() if communication is asynchronous
     * @throws PortException if a connection problem occured or not connected
     */
    Optional<MessageModel> execute(final MessageModel msg) throws PortException;

    void disconnect();
}
