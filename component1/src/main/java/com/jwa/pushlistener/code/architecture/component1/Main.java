package com.jwa.pushlistener.code.architecture.component1;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.communication.ports.PortsService;
import com.jwa.pushlistener.code.architecture.messagemodel.AMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.CMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.DMessage;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsServiceException;

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
                    CommunicationService.Receivers.PORT2.getName(),
                    msg -> {
                        System.out.println("ReceiverHandler on port '" + CommunicationService.Receivers.PORT2.getName() + "' got called");
                        return Optional.absent();
                    }
            );
            /*
            optional: set asynchronous-sender-callbacks, e.g.:
            portsService.setAsynchronousSenderCallback(
                    CommunicationService.AsynchronousSenders.PORTX.getName(),
                    msg -> {
                        ...
                    }
            );
            */
            portsService.startReceiverPorts();
            // for testing: wait 20sec before continuing
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
            portsService.connectSender(CommunicationService.Senders.PORT1.getName());
            portsService.executeSender(CommunicationService.Senders.PORT1.getName(), new AMessage());
            portsService.connectSender(CommunicationService.Senders.PORT3.getName());
            portsService.executeSender(CommunicationService.Senders.PORT3.getName(), new CMessage());
            portsService.connectSender(CommunicationService.Senders.PORT4.getName());
            portsService.executeSender(CommunicationService.Senders.PORT4.getName(), new DMessage());
        } catch (PortsServiceException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            // for testing: wait 20sec before continuing
            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            portsService.stopPorts();
        }
    }
}
