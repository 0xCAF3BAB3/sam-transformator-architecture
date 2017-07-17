package com.jwa.pushlistener.ports.communication;

public interface Receiver<T extends PortInterface> extends Port {
    void start() throws CommunicationException;
    void shutdown() throws CommunicationException;
}
