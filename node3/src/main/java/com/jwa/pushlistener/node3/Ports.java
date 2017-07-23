package com.jwa.pushlistener.node3;

import com.google.common.base.Optional;

import com.jwa.pushlistener.ports.communication.AbstractPorts;
import com.jwa.pushlistener.ports.communication.port.Receiver;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.RmiReceiver;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.RmiSynchronousSender;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.config.RmiReceiverConfig;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.config.RmiSenderConfig;

public class Ports extends AbstractPorts {
    public Ports() {
        Receiver receiverOnPort1 = new RmiReceiver(new RmiReceiverConfig(11031));
        receiverOnPort1.register(msg -> {
            System.out.println("port1 got called by other component");
            return Optional.absent();
        });
        super.addPort("port1", receiverOnPort1);

        super.addPort("port2", new RmiSynchronousSender(new RmiSenderConfig("127.0.0.1", 11025)));

        Receiver receiverOnPort3 = new RmiReceiver(new RmiReceiverConfig(11033));
        receiverOnPort3.register(msg -> {
            System.out.println("port3 got called by other component");
            return Optional.absent();
        });
        super.addPort("port3", receiverOnPort3);
    }
}
