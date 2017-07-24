package com.jwa.pushlistener.node2;

import com.jwa.pushlistener.messagemodel.BMessage;
import com.jwa.pushlistener.messagemodel.EMessage;
import com.jwa.pushlistener.ports.PortsException;
import com.jwa.pushlistener.ports.port.Sender;

public final class Main {
    public static void main(final String[] args) throws PortsException {
        final Ports ports = new Ports();
        ports.start();

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        final Sender senderOnPort2 = ports.getSender("Port2");
        senderOnPort2.connect();
        senderOnPort2.execute(new BMessage());

        final Sender senderOnPort4 = ports.getSender("Port4");
        senderOnPort4.connect();
        senderOnPort4.execute(new EMessage());

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.shutdown();
    }
}
