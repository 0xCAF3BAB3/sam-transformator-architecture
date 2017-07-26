package com.jwa.pushlistener.code.architecture.ports.port;

public interface Receiver extends Port {
    /**
     *
     * @param handler must not be null
     */
    void register(final ReceiverHandler handler);

    void start() throws PortException;

    void shutdown();
}
