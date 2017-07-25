package com.jwa.pushlistener.architecture.node1;

import com.google.common.base.Optional;

import com.jwa.pushlistener.architecture.ports.AbstractPorts;
import com.jwa.pushlistener.architecture.ports.factory.PortFactory;
import com.jwa.pushlistener.architecture.ports.factory.PortFactoryProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Ports extends AbstractPorts {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ports.class);

    public Ports() {
        final PortFactory factory = PortFactoryProducer.getRmiFactory();

        super.addPort("Port1", factory.getSynchronousSenderPort("127.0.0.1", 11021));

        super.addPort("Port2", factory.getReceiverPort(11012, msg -> {
            LOGGER.info("Port2 got called by other component");
            return Optional.absent();
        }));

        super.addPort("Port3", factory.getSynchronousSenderPort("127.0.0.1", 11023));

        super.addPort("Port4", factory.getSynchronousSenderPort("127.0.0.1", 11033));
    }
}
