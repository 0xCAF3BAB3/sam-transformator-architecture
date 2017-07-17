package com.jwa.pushlistener.ports.communication;

public interface Sender<T extends PortInterface> extends Port {
    void connect() throws CommunicationException;
    void disconnect() throws CommunicationException;
    T getInterface() throws CommunicationException;
}
