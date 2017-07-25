package com.jwa.pushlistener.code.architecture.ports.port;

public interface AsynchronousSender extends Sender {
    void register(final AsynchronousSenderCallback callback);
}
