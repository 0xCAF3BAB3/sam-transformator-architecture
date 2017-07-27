package com.jwa.pushlistener.code.architecture.communication.portfactory.context;

import java.util.Collections;
import java.util.Map;

public final class PortContext {
    private final Map<PortContextKey, String> context;

    PortContext(final Map<PortContextKey, String> context) {
        this.context = context;
    }

    public final Map<PortContextKey, String> getContext() {
        return Collections.unmodifiableMap(context);
    }
}
