package com.jwa.pushlistener.ports.port;

import com.google.common.base.Optional;

import com.jwa.pushlistener.messagemodel.MessageModel;

public interface ReceiverHandler {
    /**
     *
     * @param msg must not be null
     * @return
     * @throws IllegalArgumentException if msg is null
     */
    Optional<MessageModel> handle(final MessageModel msg) throws IllegalArgumentException;
}
