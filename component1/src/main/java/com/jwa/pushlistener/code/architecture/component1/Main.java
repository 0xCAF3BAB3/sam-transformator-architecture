package com.jwa.pushlistener.code.architecture.component1;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfigBuilder;
import com.jwa.pushlistener.code.architecture.messagemodel.AMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.CMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.DMessage;
import com.jwa.pushlistener.code.architecture.communication.ports.Ports;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws PortsException {
        final Ports ports = new Ports();

        // init ports
        ports.setPort("Port1",
                new PortConfigBuilder()
                        .setParameter("PortStyle", "Rmi")
                        .setParameter("PortType", "Sender/SynchronousSender")
                        .setParameter("PortParameters.Rmi.hostname", "127.0.0.1")
                        .setParameter("PortParameters.Rmi.portRegistry", "11021")
                        .build()
        );
        ports.setPort("Port2",
                new PortConfigBuilder()
                        .setParameter("PortStyle", "Rmi")
                        .setParameter("PortType", "Receiver")
                        .setParameter("PortParameters.Rmi.portRegistry", "11012")
                        .build()
        );
        ports.setReceiverHandler("Port2", msg -> {
            LOGGER.info("Port2 got called by other component");
            return Optional.absent();
        });
        ports.setPort("Port3",
                new PortConfigBuilder()
                        .setParameter("PortStyle", "Rmi")
                        .setParameter("PortType", "Sender/SynchronousSender")
                        .setParameter("PortParameters.Rmi.hostname", "127.0.0.1")
                        .setParameter("PortParameters.Rmi.portRegistry", "11023")
                        .build()
        );
        ports.setPort("Port4",
                new PortConfigBuilder()
                        .setParameter("PortStyle", "Rmi")
                        .setParameter("PortType", "Sender/SynchronousSender")
                        .setParameter("PortParameters.Rmi.hostname", "127.0.0.1")
                        .setParameter("PortParameters.Rmi.portRegistry", "11033")
                        .build()
        );
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
