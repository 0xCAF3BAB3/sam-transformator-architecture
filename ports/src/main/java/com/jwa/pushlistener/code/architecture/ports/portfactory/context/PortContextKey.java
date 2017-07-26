package com.jwa.pushlistener.code.architecture.ports.portfactory.context;

public enum PortContextKey {
    STYLE_RMI("..."),
    STYLE_UDP("..."),
    TYPE_SENDER("..."),
    TYPE_RECEIVER("..."),



    RMI_PORT("...."),
    RMI_HOSTNAME("...");

    private final String amlName;

    PortContextKey(final String amlName) {
        this.amlName = amlName;
    }

    final String getAmlName() {
        return amlName;
    }

    // TODO: add a by aml name method
}
