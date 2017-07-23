package com.jwa.pushlistener.ports.communication;

public final class CommunicationException extends Exception {
    public CommunicationException(final String message, final Throwable cause) {
        super (message, cause);
    }
}
