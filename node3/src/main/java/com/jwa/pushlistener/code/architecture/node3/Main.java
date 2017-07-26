package com.jwa.pushlistener.code.architecture.node3;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.FMessage;
import com.jwa.pushlistener.code.architecture.ports.Ports;
import com.jwa.pushlistener.code.architecture.ports.PortsException;
import com.jwa.pushlistener.code.architecture.ports.portfactory.factory.AbstractPortFactory;
import com.jwa.pushlistener.code.architecture.ports.portfactory.factory.AbstractPortFactoryProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws PortsException {
        // setup ports
        final Ports ports = new Ports();
        final AbstractPortFactory factory = AbstractPortFactoryProducer.getFactory();
        ports.setPort("Port1", factory.getReceiverPort(11031, msg -> {
            LOGGER.info("Port1 got called by other component");
            return Optional.absent();
        }));
        ports.setPort("Port2", factory.getSynchronousSenderPort("127.0.0.1", 11025));
        ports.setPort("Port3", factory.getReceiverPort(11033, msg -> {
            LOGGER.info("Port3 got called by other component");
            return Optional.absent();
        }));
        ports.startReceiverPorts();

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.connectSender("Port2");
        ports.executeSender("Port2", new FMessage());

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.stopPorts();
    }
}
