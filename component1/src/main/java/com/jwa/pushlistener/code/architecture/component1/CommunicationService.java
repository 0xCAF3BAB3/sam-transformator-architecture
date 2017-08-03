package com.jwa.pushlistener.code.architecture.component1;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.port.factory.PortFactory;
import com.jwa.pushlistener.code.architecture.communication.port.factory.config.PortFactoryConfig;
import com.jwa.pushlistener.code.architecture.communication.port.factory.config.PortFactoryConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.RmiPortFactory;
import com.jwa.pushlistener.code.architecture.communication.ports.PortsService;

public final class CommunicationService {
    private static CommunicationService instance;
    private final PortsService portsService;

    public static CommunicationService getInstance() throws IllegalArgumentException {
        if (instance == null) {
            instance = new CommunicationService();
        }
        return instance;
    }

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

    public enum AsynchronousSenders {}

    private CommunicationService() throws IllegalArgumentException {
        final PortFactoryConfig portFactoryConfig = new PortFactoryConfigBuilder()
                .setFactory("Rmi", new RmiPortFactory())
                .build();
        final PortFactory portFactory = new PortFactory(portFactoryConfig);
        this.portsService = new PortsService(portFactory);
        init();
    }

    private void init() throws IllegalArgumentException {
        portsService.setPort(SynchronousSenders.PORT1.name(),
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
        portsService.setPort(SynchronousSenders.PORT3.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("Rmi.hostname", "127.0.0.1")
                        .setParameter("Rmi.portRegistry", "11023")
                        .build()
        );
        portsService.setPort(SynchronousSenders.PORT4.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("Rmi.hostname", "127.0.0.1")
                        .setParameter("Rmi.portRegistry", "11033")
                        .build()
        );
    }

    public final PortsService getPortsService() {
        return portsService;
    }
}
