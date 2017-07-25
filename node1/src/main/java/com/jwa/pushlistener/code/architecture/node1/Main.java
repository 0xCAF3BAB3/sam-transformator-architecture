package com.jwa.pushlistener.code.architecture.node1;

import com.jwa.pushlistener.code.architecture.messagemodel.AMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.CMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.DMessage;
import com.jwa.pushlistener.code.architecture.ports.PortsException;
import com.jwa.pushlistener.code.architecture.ports.port.Sender;

public final class Main {
    public static void main(final String[] args) throws PortsException {
        final Ports ports = new Ports();
        ports.start();

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        final Sender senderOnPort1 = ports.getSender("Port1");
        senderOnPort1.connect();
        senderOnPort1.execute(new AMessage());

        final Sender senderOnPort3 = ports.getSender("Port3");
        senderOnPort3.connect();
        senderOnPort3.execute(new CMessage());

        final Sender senderOnPort4 = ports.getSender("Port4");
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
