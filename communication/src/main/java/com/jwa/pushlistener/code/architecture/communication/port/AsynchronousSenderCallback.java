package com.jwa.pushlistener.code.architecture.communication.port;

import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;

public interface AsynchronousSenderCallback {
    /**
     *
     * @param msg must not be null
     */
    void process(final MessageModel msg);
}
