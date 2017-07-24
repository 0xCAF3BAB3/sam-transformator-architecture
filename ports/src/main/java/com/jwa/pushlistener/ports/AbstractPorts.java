package com.jwa.pushlistener.ports;

import com.jwa.pushlistener.ports.port.Port;
import com.jwa.pushlistener.ports.port.Receiver;
import com.jwa.pushlistener.ports.port.Sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractPorts {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPorts.class);
    private final Map<String, Port> ports;

    protected AbstractPorts() {
        this.ports = new LinkedHashMap<>();
    }

    protected final void addPort(final String name, final Port port) {
        ports.put(name, port);
    }

    public final Sender getSender(final String name) {
        final Port port = ports.get(name);
        if (port instanceof Sender) {
            return (Sender) port;
        }
        return null;
    }

    public final void start() throws PortsException {
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
}
