package com.jwa.pushlistener.ports.communication.port;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.communication.CommunicationException;

public interface Sender extends Port {
    void connect() throws CommunicationException;

    /**
     *
     * @param msg must not be null
     * @return Optional<MessageModel> if communication is synchronous, Optional.empty() if communication is asynchronous
     * @throws IllegalArgumentException if msg is null
     * @throws CommunicationException
     */
    Optional<MessageModel> execute(MessageModel msg) throws IllegalArgumentException, CommunicationException;

    void disconnect() throws CommunicationException;
}
