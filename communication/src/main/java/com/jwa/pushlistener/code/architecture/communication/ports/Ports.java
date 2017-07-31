package com.jwa.pushlistener.code.architecture.communication.ports;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfig;
import com.jwa.pushlistener.code.architecture.communication.port.factory.PortFactory;
import com.jwa.pushlistener.code.architecture.communication.port.factory.config.PortFactoryConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.RmiPortFactory;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.UdpPortFactory;
import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;
import com.jwa.pushlistener.code.architecture.communication.port.AsynchronousSender;
import com.jwa.pushlistener.code.architecture.communication.port.AsynchronousSenderCallback;
import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.port.PortException;
import com.jwa.pushlistener.code.architecture.communication.port.Receiver;
import com.jwa.pushlistener.code.architecture.communication.port.ReceiverHandler;
import com.jwa.pushlistener.code.architecture.communication.port.Sender;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Ports {
    private final Map<String, Port> ports;
    private final PortFactory portFactory;

    public Ports() {
        this.ports = new LinkedHashMap<>();
        portFactory = new PortFactory(
                new PortFactoryConfigBuilder()
                        .setFactory("Rmi", new RmiPortFactory())
                        .setFactory("Udp", new UdpPortFactory())
                        .build()
        );
    }

    // for testing
    Ports(final PortFactory portFactory) throws IllegalArgumentException {
        this.ports = new LinkedHashMap<>();
        if (portFactory == null) {
            throw new IllegalArgumentException("Passed port-factory is null");
        }
        this.portFactory = portFactory;
    }

    public final void setPort(final String portName, final PortConfig portConfig) throws IllegalArgumentException {
        if (portName == null || portName.isEmpty()) {
            throw new IllegalArgumentException("Passed port-name is invalid");
        }
        if (portConfig == null) {
            throw new IllegalArgumentException("Passed port-configuration is null");
        }
        final Port port;
        try {
            port = portFactory.createPort(portConfig);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Passed port-configuration is invalid: " + e.getMessage(), e);
        }
        ports.put(portName, port);
    }

    public final void setReceiverHandler(final String portName, final ReceiverHandler handler) throws IllegalArgumentException {
        if (handler == null) {
            throw new IllegalArgumentException("Passed handler is null");
        }
        final Receiver receiver = getReceiverByName(portName);
        receiver.setHandler(handler);
    }

    public final void setAsynchronousSenderCallback(final String portName, final AsynchronousSenderCallback callback) throws IllegalArgumentException {
        if (callback == null) {
            throw new IllegalArgumentException("Passed callback is null");
        }
        final AsynchronousSender sender = getAsynchronousSenderByName(portName);
        sender.setCallback(callback);
    }

    public final void startReceiverPort(final String portName) throws PortsException {
        final Receiver receiver = getReceiverByName(portName);
        if (!receiver.isStarted()) {
            try {
                receiver.start();
            } catch (PortException e) {
                throw new PortsException("Starting receiver-port '" + portName + "' failed: " + e.getMessage(), e);
            }
        }
    }

    public final void startReceiverPorts() throws PortsException {
        for(String receiverPortName : getReceivers().keySet()) {
            startReceiverPort(receiverPortName);
        }
    }

    public final void stopReceiverPort(final String portName) {
        final Receiver receiver = getReceiverByName(portName);
        if (receiver.isStarted()) {
            receiver.shutdown();
        }
    }

    public final void stopReceiverPorts() {
        for(String receiverPortName : getReceivers().keySet()) {
            stopReceiverPort(receiverPortName);
        }
    }

    public final void stopPorts() {
        stopReceiverPorts();
        for(Sender sender : getSenders().values()) {
            if (sender.isConnected()) {
                sender.disconnect();
            }
        }
    }

    public final void connectSender(final String portName) throws IllegalArgumentException, PortsException {
        final Sender sender = getSenderByName(portName);
        try {
            sender.connect();
        } catch (PortException e) {
            throw new PortsException("Connecting sender-port '" + portName + "' failed: " + e.getMessage(), e);
        }
    }

    public final Optional<MessageModel> executeSender(final String portName, final MessageModel msg) throws IllegalArgumentException, PortsException {
        if (msg == null) {
            throw new IllegalArgumentException("Passed message is null");
        }
        final Sender sender = getSenderByName(portName);
        try {
            return sender.execute(msg);
        } catch (PortException e) {
            throw new PortsException("Executing sender-port '" + portName + "' failed: " + e.getMessage(), e);
        }
    }

    private Port getPortByName(final String portName) throws IllegalArgumentException {
        final Port port = ports.get(portName);
        if (port == null) {
            throw new IllegalArgumentException("Passed port not found");
        }
        return port;
    }

    private Sender getSenderByName(final String portName) throws IllegalArgumentException {
        final Port port = getPortByName(portName);
        if (port instanceof Sender) {
             return (Sender) port;
        } else {
            throw new IllegalArgumentException("Passed port is not from type Sender");
        }
    }

    private AsynchronousSender getAsynchronousSenderByName(final String portName) throws IllegalArgumentException {
        final Port port = getPortByName(portName);
        if (port instanceof AsynchronousSender) {
            return (AsynchronousSender) port;
        } else {
            throw new IllegalArgumentException("Passed port is not from type AsynchronousSender");
        }
    }

    private Receiver getReceiverByName(final String portName) throws IllegalArgumentException {
        final Port port = getPortByName(portName);
        if (port instanceof Receiver) {
            return (Receiver) port;
        } else {
            throw new IllegalArgumentException("Passed port is not from type Receiver");
        }
    }

    private Map<String, Receiver> getReceivers() {
        Map<String, Receiver> receivers = new LinkedHashMap<>();
        for(Map.Entry<String, Port> port : ports.entrySet()) {
            if (port.getValue() instanceof Receiver) {
                receivers.put(port.getKey(), (Receiver) port.getValue());
            }
        }
        return Collections.unmodifiableMap(receivers);
    }

    private Map<String, Sender> getSenders() {
        Map<String, Sender> senders = new LinkedHashMap<>();
        for(Map.Entry<String, Port> port : ports.entrySet()) {
            if (port.getValue() instanceof Sender) {
                senders.put(port.getKey(), (Sender) port.getValue());
            }
        }
        return Collections.unmodifiableMap(senders);
    }
}
