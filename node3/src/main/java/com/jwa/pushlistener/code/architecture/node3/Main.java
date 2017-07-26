package com.jwa.pushlistener.code.architecture.node3;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.FMessage;
import com.jwa.pushlistener.code.architecture.ports.Ports;
import com.jwa.pushlistener.code.architecture.ports.factory.PortAbstractFactory;
import com.jwa.pushlistener.code.architecture.ports.factory.PortAbstractFactoryProducer;
import com.jwa.pushlistener.code.architecture.ports.port.PortException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws PortException {
        // setup ports
        final Ports ports = new Ports();
        final PortAbstractFactory factory = PortAbstractFactoryProducer.getFactory();
        ports.addPort("Port1", factory.getReceiverPort(11031, msg -> {
            LOGGER.info("Port1 got called by other component");
            return Optional.absent();
        }));
        ports.addPort("Port2", factory.getSynchronousSenderPort("127.0.0.1", 11025));
        ports.addPort("Port3", factory.getReceiverPort(11033, msg -> {
            LOGGER.info("Port3 got called by other component");
            return Optional.absent();
        }));
        ports.start();

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

        ports.shutdown();
    }
}
