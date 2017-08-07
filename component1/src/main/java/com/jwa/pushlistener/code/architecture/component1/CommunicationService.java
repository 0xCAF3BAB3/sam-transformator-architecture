package com.jwa.pushlistener.code.architecture.component1;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsService;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsServiceException;

public final class CommunicationService {
    public enum Receivers {
        PORT2("Port2")
        ;

        private final String name;
        Receivers(final String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    public enum Senders {
        PORT1("Port1"),
        PORT3("Port3"),
        PORT4("Port4")
        ;

        private final String name;
        Senders(final String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    public enum SynchronousSenders {
        PORT1("Port1"),
        PORT3("Port3"),
        PORT4("Port4")
        ;

        private final String name;
        SynchronousSenders(final String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    public enum AsynchronousSenders {
        ;

        private final String name;
        AsynchronousSenders(final String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    private final PortsService portsService;

    public CommunicationService() {
        this.portsService = new PortsService();
    }

    public final void init() throws PortsServiceException {
        portsService.setPort(
                "Port1",
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("rmi.hostname", "127.0.0.1")
                        .setParameter("rmi.portRegistry", "11021")
                        .build()
        );
        portsService.setPort(
                "Port2",
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Receiver")
                        .setParameter("rmi.portRegistry", "11012")
                        .build()
        );
        portsService.setPort(
                "Port3",
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("rmi.hostname", "127.0.0.1")
                        .setParameter("rmi.portRegistry", "11023")
                        .build()
        );
        portsService.setPort(
                "Port4",
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("rmi.hostname", "127.0.0.1")
                        .setParameter("rmi.portRegistry", "11033")
                        .build()
        );
    }

    public final PortsService getPortsService() {
        return portsService;
    }
}
