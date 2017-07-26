package com.jwa.pushlistener.code.architecture.ports.port;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;

public interface ReceiverHandler {
    /**
     *
     * @param msg must not be null
     * @return
     */
    Optional<MessageModel> handle(final MessageModel msg);
}
