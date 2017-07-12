package com.jwa.pushlistener.ports.communication.style;

import com.jwa.pushlistener.messagemodel.MessageModel;

import java.util.Optional;

public interface CommStyleDeligate {
    Optional<MessageModel> execute(MessageModel msg);
}
