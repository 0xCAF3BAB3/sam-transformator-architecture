package com.jwa.pushlistener.code.architecture.ports.port;

public interface AsynchronousSender extends Sender {
    /**
     * If no callback is registered, a default-callback gets registered (which only logs the call).
     * @param callback must not be null
     */
    void register(final AsynchronousSenderCallback callback);
}
