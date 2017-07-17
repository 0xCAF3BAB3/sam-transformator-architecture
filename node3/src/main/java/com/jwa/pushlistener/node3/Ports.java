package com.jwa.pushlistener.node3;

import com.jwa.pushlistener.messagemodel.DMessage;
import com.jwa.pushlistener.messagemodel.EMessage;
import com.jwa.pushlistener.messagemodel.FFMessage;
import com.jwa.pushlistener.messagemodel.FMessage;
import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.Receiver;
import com.jwa.pushlistener.ports.communication.Sender;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMIPortInterface;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMIReceiver;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMISender;
import com.jwa.pushlistener.ports.communication.impl.rmi.config.RMIReceiverConfig;
import com.jwa.pushlistener.ports.communication.impl.rmi.config.RMISenderConfig;
import com.jwa.pushlistener.ports.communication.todo_move.Node2RMIPortInterface;
import com.jwa.pushlistener.ports.communication.todo_move.Node3RMIPortInterface;

import java.rmi.RemoteException;

public class Ports {
    private class Node3RMIPortInterfaceImpl implements Node3RMIPortInterface {
        @Override
        public void execute(DMessage message) throws RemoteException {
            // TODO: implement me
            System.out.println("execute(DMessage message) got called");
        }

        @Override
        public void execute(EMessage message) throws RemoteException {
            // TODO: implement me
            System.out.println("execute(EMessage message) got called");
        }
    }

    private class SenderImpl {
        private Sender<Node2RMIPortInterface> senderToNode2;

        public SenderImpl() {}

        private Node2RMIPortInterface getSenderToNode2() throws CommunicationException {
            if (senderToNode2 == null) {
                RMISenderConfig config = new RMISenderConfig("127.0.0.1", 1102);
                senderToNode2 = new RMISender<>(config);
                senderToNode2.connect();
            }
            return senderToNode2.getInterface();
        }

        public void shutdown() {
            if (senderToNode2 != null) {
                try {
                    senderToNode2.disconnect();
                } catch (CommunicationException ignored) {};
            }
        }
    }

    private final Receiver receiver;
    private final SenderImpl sender;

    public Ports() {
        RMIPortInterface interfaceImpl = new Node3RMIPortInterfaceImpl();
        RMIReceiverConfig config = new RMIReceiverConfig<>(1103, interfaceImpl);
        receiver = new RMIReceiver<>(config);
        sender = new SenderImpl();
    }

    public void startReceiver() throws CommunicationException {
        receiver.start();
    }

    public void shutdownReceiver() {
        try {
            receiver.shutdown();
        } catch (CommunicationException e) {
            e.printStackTrace();
        }
    }

    public void shutdownSender() {
        sender.shutdown();
    }

    public FFMessage send(FMessage message) throws CommunicationException {
        try {
            return sender.getSenderToNode2().execute(message);
        } catch (RemoteException e) {
            throw new CommunicationException("...", e); // TODO:
        }
    }
}
