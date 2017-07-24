package com.jwa.pushlistener.ports.port;

public interface AsynchronousSender extends Sender {
    void register(final AsynchronousSenderCallback callback);
}
