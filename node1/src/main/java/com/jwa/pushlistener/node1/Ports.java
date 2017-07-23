package com.jwa.pushlistener.node1;

import com.google.common.base.Optional;

import com.jwa.pushlistener.ports.communication.AbstractPorts;
import com.jwa.pushlistener.ports.communication.port.Receiver;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.RmiReceiver;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.RmiSynchronousSender;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.config.RmiReceiverConfig;
import com.jwa.pushlistener.ports.communication.port.impl.rmi.config.RmiSenderConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Ports extends AbstractPorts {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ports.class);

    public Ports() {
        super.addPort("port1", new RmiSynchronousSender(new RmiSenderConfig("127.0.0.1", 11021)));

        final Receiver receiverOnPort2 = new RmiReceiver(new RmiReceiverConfig(11012));
        receiverOnPort2.register(msg -> {
            LOGGER.info("Port2 got called by other component");
            return Optional.absent();
        });
        super.addPort("port2", receiverOnPort2);

        super.addPort("port3", new RmiSynchronousSender(new RmiSenderConfig("127.0.0.1", 11023)));

        super.addPort("port4", new RmiSynchronousSender(new RmiSenderConfig("127.0.0.1", 11033)));
    }
}
