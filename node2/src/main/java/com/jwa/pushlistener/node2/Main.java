package com.jwa.pushlistener.node2;

import com.jwa.pushlistener.messagemodel.BMessage;
import com.jwa.pushlistener.messagemodel.EMessage;
import com.jwa.pushlistener.ports.communication.CommunicationException;

public class Main {
    public static void main( String[] args ) throws CommunicationException {
        Ports ports = new Ports();
        ports.startReceiver();

        // wait a bit until we manually start the other nodes (= their Main.java or Maven profile)
        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.send(new BMessage());
        ports.send(new EMessage());

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.shutdownSender();
        ports.shutdownReceiver();
    }
}
