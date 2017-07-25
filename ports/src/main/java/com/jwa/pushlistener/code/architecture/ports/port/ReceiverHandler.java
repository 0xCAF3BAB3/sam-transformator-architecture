package com.jwa.pushlistener.code.architecture.ports.port;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;

public interface ReceiverHandler {
    /**
     *
     * @param msg must not be null
     * @return
     * @throws IllegalArgumentException if msg is null
     */
    Optional<MessageModel> handle(final MessageModel msg) throws IllegalArgumentException;
}
