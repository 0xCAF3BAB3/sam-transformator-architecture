package com.jwa.pushlistener.node1;

import com.jwa.pushlistener.messagemodel.AAMessage;
import com.jwa.pushlistener.messagemodel.AMessage;
import com.jwa.pushlistener.messagemodel.CCMessage;
import com.jwa.pushlistener.messagemodel.CMessage;
import com.jwa.pushlistener.messagemodel.DMessage;
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

        AAMessage aaMessage = ports.send(new AMessage());
        CCMessage ccMessage = ports.send(new CMessage());
        ports.send(new DMessage());

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.shutdownSender();
        ports.shutdownReceiver();
    }
}
