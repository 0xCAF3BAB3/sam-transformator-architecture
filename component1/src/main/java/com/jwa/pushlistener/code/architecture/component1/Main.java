package com.jwa.pushlistener.code.architecture.component1;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.communication.ports.Ports;
import com.jwa.pushlistener.code.architecture.messagemodel.AMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.CMessage;
import com.jwa.pushlistener.code.architecture.messagemodel.DMessage;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsException;

public final class Main {
    public static void main(final String[] args) {
        // TODO: implement me

        // example usage of CommunicationService
        try {
            final CommunicationService communicationService = CommunicationService.getInstance();
            final Ports ports = communicationService.getPorts();

            ports.setReceiverHandler(CommunicationService.Receivers.PORT2.name(),
                msg -> {
                    System.out.println("ReceiverHandler on port '" + CommunicationService.Receivers.PORT2.name() + "' got called");
                    return Optional.absent();
                }
            );

            ports.startReceiverPorts();

            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            ports.connectSender(CommunicationService.Senders.PORT1.name());
            ports.executeSender(CommunicationService.Senders.PORT1.name(), new AMessage());

            ports.connectSender(CommunicationService.Senders.PORT3.name());
            ports.executeSender(CommunicationService.Senders.PORT3.name(), new CMessage());

            ports.connectSender(CommunicationService.Senders.PORT4.name());
            ports.executeSender(CommunicationService.Senders.PORT4.name(), new DMessage());

            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            ports.stopPorts();
        } catch (PortsException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
