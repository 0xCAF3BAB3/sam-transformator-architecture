package com.jwa.pushlistener.code.architecture.ports.port;

public interface AsynchronousSender extends Sender {
    /**
     *
     * @param callback must not be null
     */
    void register(final AsynchronousSenderCallback callback);
}
