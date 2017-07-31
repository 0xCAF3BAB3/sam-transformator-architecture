package com.jwa.pushlistener.code.architecture.component3;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.communication.ports.Ports;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsException;
import com.jwa.pushlistener.code.architecture.messagemodel.FMessage;

public final class Main {
    public static void main(final String[] args) {
        // TODO: implement me

        // example usage of CommunicationService
        try {
            final CommunicationService communicationService = CommunicationService.getInstance();
            final Ports ports = communicationService.getPorts();

            ports.setReceiverHandler(CommunicationService.Receivers.PORT1.name(),
                msg -> {
                    System.out.println("ReceiverHandler on port '" + CommunicationService.Receivers.PORT1.name() + "' got called");
                    return Optional.absent();
                }
            );
            ports.setReceiverHandler(CommunicationService.Receivers.PORT3.name(),
                    msg -> {
                        System.out.println("ReceiverHandler on port '" + CommunicationService.Receivers.PORT3.name() + "' got called");
                        return Optional.absent();
                    }
            );

            ports.startReceiverPorts();

            try {
                Thread.sleep(20 * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            ports.connectSender(CommunicationService.Senders.PORT2.name());
            ports.executeSender(CommunicationService.Senders.PORT2.name(), new FMessage());

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
