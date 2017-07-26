package com.jwa.pushlistener.code.architecture.node1;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.AMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.CMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.DMessage;
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
        ports.setPort("Port1", factory.getSynchronousSenderPort("127.0.0.1", 11021));
        ports.setPort("Port2", factory.getReceiverPort(11012, msg -> {
            LOGGER.info("Port2 got called by other component");
            return Optional.absent();
        }));
        ports.setPort("Port3", factory.getSynchronousSenderPort("127.0.0.1", 11023));
        ports.setPort("Port4", factory.getSynchronousSenderPort("127.0.0.1", 11033));
        ports.startReceiverPorts();

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.connectSender("Port1");
        ports.executeSender("Port1", new AMessage());

        ports.connectSender("Port3");
        ports.executeSender("Port3", new CMessage());

        ports.connectSender("Port4");
        ports.executeSender("Port4", new DMessage());

        try {
            Thread.sleep(20 * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        ports.stopPorts();
    }
}
