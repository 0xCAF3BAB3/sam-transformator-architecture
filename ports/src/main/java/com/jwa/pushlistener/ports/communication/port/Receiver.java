package com.jwa.pushlistener.ports.communication.port;

import com.jwa.pushlistener.ports.communication.CommunicationException;

public interface Receiver extends Port {
    void register(final ReceiverHandler handler);
    void start() throws CommunicationException;
    void shutdown();
}
