package com.jwa.pushlistener.code.architecture.ports;

import com.google.common.base.Optional;

import com.jwa.pushlistener.code.architecture.messagemodel.MessageModel;
import com.jwa.pushlistener.code.architecture.ports.port.AsynchronousSender;
import com.jwa.pushlistener.code.architecture.ports.port.AsynchronousSenderCallback;
import com.jwa.pushlistener.code.architecture.ports.port.Port;
import com.jwa.pushlistener.code.architecture.ports.port.PortException;
import com.jwa.pushlistener.code.architecture.ports.port.Receiver;
import com.jwa.pushlistener.code.architecture.ports.port.ReceiverHandler;
import com.jwa.pushlistener.code.architecture.ports.port.Sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Ports {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ports.class);
    private final Map<String, Port> ports;

    public Ports() {
        this.ports = new LinkedHashMap<>();
    }

    public final void addPort(final String portName, final Port port) throws IllegalArgumentException {
        if (portName == null || portName.isEmpty()) {
            throw new IllegalArgumentException("Passed port-name is invalid");
        }
        if (port == null) {
            throw new IllegalArgumentException("Passed port is null");
        }
        ports.put(portName, port);
    }

    public final void registerReceiverHandler(final String portName, final ReceiverHandler handler) throws IllegalArgumentException {
        if (handler == null) {
            throw new IllegalArgumentException("Passed handler is null");
        }
        final Receiver receiver = getReceiverByName(portName);
        receiver.register(handler);
    }

    public final void registerAsynchronousSenderCallback(final String portName, final AsynchronousSenderCallback callback) throws IllegalArgumentException {
        if (callback == null) {
            throw new IllegalArgumentException("Passed callback is null");
        }
        final AsynchronousSender sender = getAsynchronousSenderByName(portName);
        sender.register(callback);
    }

    public final void connectSender(final String portName) throws IllegalArgumentException, PortException {
        final Sender sender = getSenderByName(portName);
        sender.connect();
    }

    public final Optional<MessageModel> executeSender(final String portName, final MessageModel msg) throws IllegalArgumentException, PortException {
        if (msg == null) {
            throw new IllegalArgumentException("Passed message is null");
        }
        final Sender sender = getSenderByName(portName);
        return sender.execute(msg);
    }

    public final void start() throws PortException {
        LOGGER.info("Starting ...");
        for(Port port : ports.values()) {
            if (port instanceof Receiver) {
                ((Receiver) port).start();
            }
        }
        LOGGER.info("Starting completed");
    }

    public final void shutdown() {
        LOGGER.info("Shutdown ...");
        for(final Port port : ports.values()) {
            if (port instanceof Receiver) {
                ((Receiver) port).shutdown();
            } else if (port instanceof Sender) {
                ((Sender) port).disconnect();
            }
        }
        LOGGER.info("Shutdown completed");
    }

    private Port getPortByName(String portName) throws IllegalArgumentException {
        final Port port = ports.get(portName);
        if (port == null) {
            throw new IllegalArgumentException("Passed port not found");
        }
        return port;
    }

    private Sender getSenderByName(String portName) throws IllegalArgumentException {
        final Port port = getPortByName(portName);
        if (port instanceof Sender) {
             return (Sender) port;
        } else {
            throw new IllegalArgumentException("Passed port is not from type Sender");
        }
    }

    private AsynchronousSender getAsynchronousSenderByName(String portName) throws IllegalArgumentException {
        final Port port = getPortByName(portName);
        if (port instanceof AsynchronousSender) {
            return (AsynchronousSender) port;
        } else {
            throw new IllegalArgumentException("Passed port is not from type AsynchronousSender");
        }
    }

    private Receiver getReceiverByName(String portName) throws IllegalArgumentException {
        final Port port = getPortByName(portName);
        if (port instanceof Receiver) {
            return (Receiver) port;
        } else {
            throw new IllegalArgumentException("Passed port is not from type Receiver");
        }
    }
}
