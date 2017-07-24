package com.jwa.pushlistener.ports.port;

import com.jwa.pushlistener.ports.PortsException;

public interface Receiver extends Port {
    void register(final ReceiverHandler handler);
    void start() throws PortsException;
    void shutdown();
}
