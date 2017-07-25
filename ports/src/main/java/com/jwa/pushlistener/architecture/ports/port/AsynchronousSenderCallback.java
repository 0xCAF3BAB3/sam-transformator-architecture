package com.jwa.pushlistener.architecture.ports.port;

import com.jwa.pushlistener.architecture.messagemodel.MessageModel;

public interface AsynchronousSenderCallback {
    /**
     *
     * @param msg must not be null
     * @throws IllegalArgumentException if msg is null
     */
    void process(final MessageModel msg) throws IllegalArgumentException;
}
