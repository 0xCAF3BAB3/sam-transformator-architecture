package com.jwa.pushlistener.code.architecture.component3;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsService;

public final class CommunicationService {
    public enum Receivers {
        PORT1,
        PORT3
    }

    public enum Senders {
        PORT2
    }

    public enum SynchronousSenders {
        PORT2
    }

    public enum AsynchronousSenders {
    }

    private final PortsService portsService;

    public CommunicationService() throws IllegalArgumentException {
        this.portsService = new PortsService();
        init();
    }

    public final PortsService getPortsService() {
        return portsService;
    }

    private void init() throws IllegalArgumentException {
        portsService.setPort(Receivers.PORT1.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Receiver")
                        .setParameter("rmi.portRegistry", "11031")
                        .build()
        );
        portsService.setPort(Senders.PORT2.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("rmi.hostname", "127.0.0.1")
                        .setParameter("rmi.portRegistry", "11025")
                        .build()
        );
        portsService.setPort(Receivers.PORT3.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Receiver")
                        .setParameter("rmi.portRegistry", "11033")
                        .build()
        );
    }
}
