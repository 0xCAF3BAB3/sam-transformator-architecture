package com.jwa.pushlistener.code.architecture.communication.port.config;

import java.util.Map;

public final class PortConfig {
    private final Map<String, String> parameters;

    PortConfig(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public final String getParameter(final String parameterKey) throws IllegalArgumentException {
        if (!parameters.containsKey(parameterKey)) {
            throw new IllegalArgumentException("Parameter '" + parameterKey + "' is missing");
        }
        final String value = parameters.get(parameterKey);
        if (value == null || value.isEmpty()) {
            throw generateIllegalValueException(parameterKey);
        }
        return value;
    }

    public final String getPortParameter(final String portParameterKey) throws IllegalArgumentException {
        return getParameter("PortParameters." + portParameterKey);
    }

    public final int getPortParameterInt(final String portParameterKey) throws IllegalArgumentException {
        final int value;
        try {
            value = Integer.parseInt(getPortParameter(portParameterKey));
        } catch (NumberFormatException e) {
            throw PortConfig.generateIllegalValueException(portParameterKey);
        }
        return value;
    }

    public static IllegalArgumentException generateNotImplementedException(final String parameterKey) {
        return new IllegalArgumentException("No implementation found for parameter '" + parameterKey + "'");
    }

    private static IllegalArgumentException generateIllegalValueException(final String parameterKey) {
        return new IllegalArgumentException("Parameter '" + parameterKey + "' has invalid value");
    }
}
