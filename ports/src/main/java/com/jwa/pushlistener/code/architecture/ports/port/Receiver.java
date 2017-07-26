package com.jwa.pushlistener.code.architecture.ports.port;

public interface Receiver extends Port {
    /**
     * If no handler is registered, a default-handler gets registered (which only logs the call).
     * @param handler must not be null
     */
    void register(final ReceiverHandler handler);

    /**
     *
     * @throws PortException if starting is not possible or already started
     */
    void start() throws PortException;

    boolean isStarted();

    /**
     * Shutdown.
     * If already shutdown: no operation.
     */
    void shutdown();
}
