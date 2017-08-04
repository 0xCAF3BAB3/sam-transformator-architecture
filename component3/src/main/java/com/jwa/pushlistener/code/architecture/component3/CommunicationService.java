package com.jwa.pushlistener.code.architecture.component3;

import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.port.factory.PortFactory;
import com.jwa.pushlistener.code.architecture.communication.port.factory.config.PortFactoryConfig;
import com.jwa.pushlistener.code.architecture.communication.port.factory.config.PortFactoryConfigBuilder;
import com.jwa.pushlistener.code.architecture.communication.port.factory.impl.rmi.RmiPortFactory;
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
        final PortFactoryConfig portFactoryConfig = new PortFactoryConfigBuilder()
                .setFactory("Rmi", new RmiPortFactory())
                .build();
        final PortFactory portFactory = new PortFactory(portFactoryConfig);
        this.portsService = new PortsService(portFactory);
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
                        .setParameter("Rmi.portRegistry", "11031")
                        .build()
        );
        portsService.setPort(SynchronousSenders.PORT2.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Sender/SynchronousSender")
                        .setParameter("Rmi.hostname", "127.0.0.1")
                        .setParameter("Rmi.portRegistry", "11025")
                        .build()
        );
        portsService.setPort(Receivers.PORT3.name(),
                new PortConfigBuilder()
                        .setStyle("Rmi")
                        .setType("Receiver")
                        .setParameter("Rmi.portRegistry", "11033")
                        .build()
        );
    }
}
