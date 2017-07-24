package com.jwa.pushlistener.node3;

import com.google.common.base.Optional;

import com.jwa.pushlistener.ports.AbstractPorts;
import com.jwa.pushlistener.ports.factory.PortFactory;
import com.jwa.pushlistener.ports.factory.PortFactoryProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Ports extends AbstractPorts {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ports.class);

    public Ports() {
        final PortFactory factory = PortFactoryProducer.getRmiFactory();

        super.addPort("Port1", factory.getReceiverPort(11031, msg -> {
            LOGGER.info("Port1 got called by other component");
            return Optional.absent();
        }));

        super.addPort("Port2", factory.getSynchronousSenderPort("127.0.0.1", 11025));

        super.addPort("Port3", factory.getReceiverPort(11033, msg -> {
            LOGGER.info("Port3 got called by other component");
            return Optional.absent();
        }));
    }
}
