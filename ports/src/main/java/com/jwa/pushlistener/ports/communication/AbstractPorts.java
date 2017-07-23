package com.jwa.pushlistener.ports.communication;

import com.jwa.pushlistener.ports.communication.port.Port;
import com.jwa.pushlistener.ports.communication.port.Receiver;
import com.jwa.pushlistener.ports.communication.port.Sender;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractPorts {
    private final Map<String, Port> ports;

    protected AbstractPorts() {
        this.ports = new LinkedHashMap<>();
    }

    protected void addPort(String name, Port port) {
        ports.put(name, port);
    }

    public Sender getSender(String name) {
        Port match = ports.get(name);
        if (match instanceof Sender) {
            return (Sender) match;
        }
        return null;
    }

    public void start() throws CommunicationException {
        for(Port port : ports.values()) {
            if (port instanceof Receiver) {
                ((Receiver) port).start();
            }
        }
    }

    public void shutdown() {
        for(Port port : ports.values()) {
            try {
                if (port instanceof Receiver) {
                    ((Receiver) port).shutdown();
                } else if (port instanceof Sender) {
                    ((Sender) port).disconnect();
                }
            } catch (CommunicationException e) {
                e.printStackTrace();
            }
        }
    }
}
