package com.jwa.pushlistener.node2;

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
        final Receiver receiverOnPort1 = new RmiReceiver(new RmiReceiverConfig(11021));
        receiverOnPort1.register(msg -> {
            LOGGER.info("Port1 got called by other component");
            return Optional.absent();
        });
        super.addPort("port1", receiverOnPort1);

        super.addPort("port2", new RmiSynchronousSender(new RmiSenderConfig("127.0.0.1", 11012)));

        final Receiver receiverOnPort3 = new RmiReceiver(new RmiReceiverConfig(11023));
        receiverOnPort3.register(msg -> {
            LOGGER.info("Port3 got called by other component");
            return Optional.absent();
        });
        super.addPort("port3", receiverOnPort3);

        super.addPort("port4", new RmiSynchronousSender(new RmiSenderConfig("127.0.0.1", 11031)));

        final Receiver receiverOnPort5 = new RmiReceiver(new RmiReceiverConfig(11025));
        receiverOnPort5.register(msg -> {
            LOGGER.info("Port5 got called by other component");
            return Optional.absent();
        });
        super.addPort("port5", receiverOnPort5);
    }
}
