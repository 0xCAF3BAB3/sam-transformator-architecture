package com.jwa.pushlistener.code.architecture.component1;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsService;

public final class CommunicationService {
    public enum Receivers {
        PORT2
    }

    public enum Senders {
        PORT1,
        PORT3,
        PORT4
    }

    public enum SynchronousSenders {
        PORT1,
        PORT3,
        PORT4
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
        portsService.setPort(Senders.PORT1.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("Rmi.hostname", "127.0.0.1")
                        .setParameter("Rmi.portRegistry", "11021")
                        .build()
        );
        portsService.setPort(Receivers.PORT2.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Receiver")
                        .setParameter("Rmi.portRegistry", "11012")
                        .build()
        );
        portsService.setPort(Senders.PORT3.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("Rmi.hostname", "127.0.0.1")
                        .setParameter("Rmi.portRegistry", "11023")
                        .build()
        );
        portsService.setPort(Senders.PORT4.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("Rmi.hostname", "127.0.0.1")
                        .setParameter("Rmi.portRegistry", "11033")
                        .build()
        );
    }
}
