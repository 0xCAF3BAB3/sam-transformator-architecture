package com.jwa.pushlistener.ports.communication.port;

public interface AsynchronousSender extends Sender {
    void register(final AsynchronousSenderCallback callback);
}
