package com.jwa.pushlistener.node1;

import com.jwa.pushlistener.messagemodel.AAMessage;
import com.jwa.pushlistener.messagemodel.AMessage;
import com.jwa.pushlistener.messagemodel.BMessage;
import com.jwa.pushlistener.messagemodel.CCMessage;
import com.jwa.pushlistener.messagemodel.CMessage;
import com.jwa.pushlistener.messagemodel.DMessage;
import com.jwa.pushlistener.ports.communication.CommunicationException;
import com.jwa.pushlistener.ports.communication.Receiver;
import com.jwa.pushlistener.ports.communication.Sender;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMIPortInterface;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMIReceiver;
import com.jwa.pushlistener.ports.communication.impl.rmi.RMISender;
import com.jwa.pushlistener.ports.communication.impl.rmi.config.RMIReceiverConfig;
import com.jwa.pushlistener.ports.communication.impl.rmi.config.RMISenderConfig;
import com.jwa.pushlistener.ports.communication.todo_move.Node1RMIPortInterface;
import com.jwa.pushlistener.ports.communication.todo_move.Node2RMIPortInterface;
import com.jwa.pushlistener.ports.communication.todo_move.Node3RMIPortInterface;

import java.rmi.RemoteException;

public class Ports {
    private class Node1RMIPortInterfaceImpl implements Node1RMIPortInterface {
        @Override
        public void execute(BMessage message) throws RemoteException {
            // TODO: implement me
            System.out.println("execute(BMessage message) got called");
        }
    }

    private class SenderImpl {
        private Sender<Node2RMIPortInterface> senderToNode2;
        private Sender<Node3RMIPortInterface> senderToNode3;

        public SenderImpl() {}

        private Node2RMIPortInterface getSenderToNode2() throws CommunicationException {
            if (senderToNode2 == null) {
                RMISenderConfig config = new RMISenderConfig("127.0.0.1", 1102);
                senderToNode2 = new RMISender<>(config);
                senderToNode2.connect();
            }
            return senderToNode2.getInterface();
        }

        private Node3RMIPortInterface getSenderToNode3() throws CommunicationException {
            if (senderToNode3 == null) {
                RMISenderConfig config = new RMISenderConfig("127.0.0.1", 1103);
                senderToNode3 = new RMISender<>(config);
                senderToNode3.connect();
            }
            return senderToNode3.getInterface();
        }

        public void shutdown() {
            if (senderToNode2 != null) {
                try {
                    senderToNode2.disconnect();
                } catch (CommunicationException ignored) {};
            }
            if (senderToNode3 != null) {
                try {
                    senderToNode3.disconnect();
                } catch (CommunicationException ignored) {};
            }
        }
    }

    private final Receiver receiver;
    private final SenderImpl sender;

    public Ports() {
        RMIPortInterface interfaceImpl = new Node1RMIPortInterfaceImpl();
        RMIReceiverConfig config = new RMIReceiverConfig<>(1101, interfaceImpl);
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

    public AAMessage send(AMessage message) throws CommunicationException {
        try {
            return sender.getSenderToNode2().execute(message);
        } catch (RemoteException e) {
            throw new CommunicationException("...", e); // TODO:
        }
    }

    public CCMessage send(CMessage message) throws CommunicationException {
        try {
            return sender.getSenderToNode2().execute(message);
        } catch (RemoteException e) {
            throw new CommunicationException("...", e); // TODO:
        }
    }

    public void send(DMessage message) throws CommunicationException {
        try {
            sender.getSenderToNode3().execute(message);
        } catch (RemoteException e) {
            throw new CommunicationException("...", e); // TODO:
        }
    }
}
