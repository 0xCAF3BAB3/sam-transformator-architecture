package com.jwa.pushlistener.node1;

import com.jwa.pushlistener.messagemodel.AMessage;
import com.jwa.pushlistener.messagemodel.CMessage;
import com.jwa.pushlistener.messagemodel.DMessage;
import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.port.Sender;

public class Main {
    public static void main( String[] args ) throws CommunicationException {
        Ports ports = new Ports();
        ports.start();

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        Sender senderOnPort1 = ports.getSender("port1");
        senderOnPort1.connect();
        senderOnPort1.execute(new AMessage());

        Sender senderOnPort3 = ports.getSender("port3");
        senderOnPort3.connect();
        senderOnPort3.execute(new CMessage());

        Sender senderOnPort4 = ports.getSender("port4");
        senderOnPort4.connect();
        senderOnPort4.execute(new DMessage());

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.shutdown();
    }
}
