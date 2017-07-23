package com.jwa.pushlistener.ports.communication.port;

import com.jwa.pushlistener.messagemodel.MessageModel;

public interface AsynchronousSenderCallback {
    /**
     *
     * @param msg must not be null
     * @throws IllegalArgumentException if msg is null
     */
    void process(final MessageModel msg) throws IllegalArgumentException;
}
