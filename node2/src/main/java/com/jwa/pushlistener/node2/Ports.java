package com.jwa.pushlistener.node2;

import com.jwa.pushlistener.messagemodel.AAMessage;
import com.jwa.pushlistener.messagemodel.AMessage;
import com.jwa.pushlistener.messagemodel.BMessage;
import com.jwa.pushlistener.messagemodel.CCMessage;
import com.jwa.pushlistener.messagemodel.CMessage;
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
import com.jwa.pushlistener.ports.communication.todo_move.Node1RMIPortInterface;
import com.jwa.pushlistener.ports.communication.todo_move.Node2RMIPortInterface;
import com.jwa.pushlistener.ports.communication.todo_move.Node3RMIPortInterface;

import java.rmi.RemoteException;

public class Ports {
    private class Node2RMIPortInterfaceImpl implements Node2RMIPortInterface {
        @Override
        public AAMessage execute(AMessage message) throws RemoteException {
            // TODO: implement me
            System.out.println("AAMessage:execute(AMessage message) got called");
            return null;
        }

        @Override
        public CCMessage execute(CMessage message) throws RemoteException {
            // TODO: implement me
            System.out.println("CCMessage:execute(CMessage message) got called");
            return null;
        }

        @Override
        public FFMessage execute(FMessage message) throws RemoteException {
            // TODO: implement me
            System.out.println("FFMessage:execute(FMessage message) got called");
            return null;
        }
    }

    private class SenderImpl {
        private Sender<Node1RMIPortInterface> senderToNode1;
        private Sender<Node3RMIPortInterface> senderToNode3;

        public SenderImpl() {}

        private Node1RMIPortInterface getSenderToNode1() throws CommunicationException {
            if (senderToNode1 == null) {
                RMISenderConfig config = new RMISenderConfig("127.0.0.1", 1101);
                senderToNode1 = new RMISender<>(config);
                senderToNode1.connect();
            }
            return senderToNode1.getInterface();
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
            if (senderToNode1 != null) {
                try {
                    senderToNode1.disconnect();
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
        RMIPortInterface interfaceImpl = new Node2RMIPortInterfaceImpl();
        RMIReceiverConfig config = new RMIReceiverConfig<>(1102, interfaceImpl);
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

    public void send(BMessage message) throws CommunicationException {
        try {
            sender.getSenderToNode1().execute(message);
        } catch (RemoteException e) {
            throw new CommunicationException("...", e); // TODO:
        }
    }

    public void send(EMessage message) throws CommunicationException {
        try {
            sender.getSenderToNode3().execute(message);
        } catch (RemoteException e) {
            throw new CommunicationException("...", e); // TODO:
        }
    }
}
