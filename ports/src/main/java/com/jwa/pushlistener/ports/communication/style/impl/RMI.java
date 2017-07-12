package com.jwa.pushlistener.ports.communication.style.impl;

import com.jwa.pushlistener.messagemodel.MessageModel;
import com.jwa.pushlistener.ports.communication.style.Synchronous;

import java.util.Optional;

public class RMI implements Synchronous {
    @Override
    public void register(Class type, int id) {

    }

    @Override
    public Optional<MessageModel> execute(MessageModel msg) {
        return null;
    }
}
