package com.jwa.pushlistener.code.architecture.ports.port;

import com.jwa.pushlistener.code.architecture.ports.PortsException;

public interface Receiver extends Port {
    void register(final ReceiverHandler handler);
    void start() throws PortsException;
    void shutdown();
}
