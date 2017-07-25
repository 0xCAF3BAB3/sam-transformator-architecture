package com.jwa.pushlistener.architecture.ports.port;

public interface AsynchronousSender extends Sender {
    void register(final AsynchronousSenderCallback callback);
}
