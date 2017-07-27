package com.jwa.pushlistener.code.architecture.communication.port.config;

import java.util.Map;

public final class PortConfig {
    private final Map<String, String> config;

    PortConfig(final Map<String, String> config) {
        this.config = config;
    }

    public final String getValue(final String key) throws IllegalArgumentException {
        if (!config.containsKey(key)) {
            throw new IllegalArgumentException("Attribute '" + key + "' is missing");
        }
        final String value = config.get(key);
        if (value == null || value.isEmpty()) {
            throw generateIllegalValueException(key);
        }
        return value;
    }

    public final String getParameterValue(final String parameterKey) throws IllegalArgumentException {
        return getValue("PortParameters." + parameterKey);
    }

    public final int getParameterValueInt(final String parameterKey) throws IllegalArgumentException {
        final int value;
        try {
            value = Integer.parseInt(getParameterValue(parameterKey));
        } catch (NumberFormatException e) {
            throw PortConfig.generateIllegalValueException(parameterKey);
        }
        return value;
    }

    public static IllegalArgumentException generateNotImplementedException(final String key) {
        return new IllegalArgumentException("No implementation found for attribute '" + key + "'");
    }

    private static IllegalArgumentException generateIllegalValueException(final String key) {
        return new IllegalArgumentException("Attribute '" + key + "' has invalid value");
    }
}
