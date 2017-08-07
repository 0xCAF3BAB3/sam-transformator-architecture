package com.jwa.pushlistener.code.architecture.component3;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.communication.ports.PortsService;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsServiceException;
import com.jwa.pushlistener.code.architecture.messagemodel.FMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        // example usage of CommunicationService
        final CommunicationService communicationService = new CommunicationService();
        final PortsService portsService = communicationService.getPortsService();
        try {
            communicationService.init();
            /*
            optional: set receiver-handlers, e.g.:
            portsService.setReceiverHandler(
                    CommunicationService.Receivers.PORTX.getName(),
                    msg -> {
                        ...
                        return ...;
                    }
            );
            */
            portsService.setReceiverHandler(
                    CommunicationService.Receivers.PORT1.getName(),
                    msg -> {
                        System.out.println("ReceiverHandler on port '" + CommunicationService.Receivers.PORT1.getName() + "' got called");
                        return Optional.absent();
                    }
            );
            portsService.setReceiverHandler(
                    CommunicationService.Receivers.PORT3.getName(),
                    msg -> {
                        System.out.println("ReceiverHandler on port '" + CommunicationService.Receivers.PORT3.getName() + "' got called");
                        return Optional.absent();
                    }
            );
            /*
            optional: set asynchronous-sender-callbacks, e.g.:
            portsService.setAsynchronousSenderCallback(
                    CommunicationService.AsynchronousSenders.PORTX.getName(),
                    msg -> {
                        ...
                    });
            */
            portsService.startReceiverPorts();
            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            /*
            optional: connect and execute senders, e.g.:
            portsService.connectSender(
                    CommunicationService.Senders.PORTX.getName()
            );
            portsService.executeSender(
                    CommunicationService.Senders.PORTX.getName(),
                    ...
            );
            */
            portsService.connectSender(CommunicationService.Senders.PORT2.getName());
            portsService.executeSender(CommunicationService.Senders.PORT2.getName(), new FMessage());
        } catch (PortsServiceException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            portsService.stopPorts();
        }
    }
}
