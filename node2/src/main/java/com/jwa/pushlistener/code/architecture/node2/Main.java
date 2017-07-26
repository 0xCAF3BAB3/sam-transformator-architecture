package com.jwa.pushlistener.code.architecture.node2;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.BMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.EMessage;
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
        ports.registerPort("Port1", factory.getReceiverPort(11021, msg -> {
            LOGGER.info("Port1 got called by other component");
            return Optional.absent();
        }));
        ports.registerPort("Port2", factory.getSynchronousSenderPort("127.0.0.1", 11012));
        ports.registerPort("Port3", factory.getReceiverPort(11023, msg -> {
            LOGGER.info("Port3 got called by other component");
            return Optional.absent();
        }));
        ports.registerPort("Port4", factory.getSynchronousSenderPort("127.0.0.1", 11031));
        ports.registerPort("Port5", factory.getReceiverPort(11025, msg -> {
            LOGGER.info("Port5 got called by other component");
            return Optional.absent();
        }));
        ports.start();

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.connectSender("Port2");
        ports.executeSender("Port2", new BMessage());

        ports.connectSender("Port4");
        ports.executeSender("Port4", new EMessage());

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.shutdown();
    }
}
