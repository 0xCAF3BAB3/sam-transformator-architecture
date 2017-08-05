package com.jwa.pushlistener.code.architecture.communication.port.factory;

import com.jwa.pushlistener.code.architecture.communication.port.Port;
import com.jwa.pushlistener.code.architecture.communication.port.config.PortConfig;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

/**
 * Port-factory implementations get loaded via reflection and must:
 *  o implement interface 'AbstractPortFactory'
 *  o have a zero argument constructor
 *  o have annotation 'PortFactoryStyle' set
 *  o be located somewhere under the package <PACKAGE_PORTFACTORY_IMPLEMENTATIONS>
 */
public final class PortFactory {
    private static final String PACKAGE_PORTFACTORY_IMPLEMENTATIONS = AbstractPortFactory.class.getPackage().getName() + ".impl";
    private final Map<String, AbstractPortFactory> cachedFactories;

    public PortFactory() throws IllegalArgumentException {
        this.cachedFactories = new HashMap<>();
    }

    public final Port createPort(final PortConfig portConfig) throws IllegalArgumentException {
        if (portConfig == null) {
            throw new IllegalArgumentException("Passed port-config is null");
        }
        final String styleName = portConfig.getStyle();
        AbstractPortFactory cachedFactory = cachedFactories.get(styleName);
        if (cachedFactory == null) {
            boolean loaded = loadFactory(styleName);
            if (loaded) {
                cachedFactory = cachedFactories.get(styleName);
            } else {
                throw portConfig.generateNoImplementationFoundForStyleException();
            }
        }
        return cachedFactory.createPort(portConfig);
    }

    private boolean loadFactory(final String styleName) {
        final Reflections reflections = new Reflections(PACKAGE_PORTFACTORY_IMPLEMENTATIONS);
        for(Class<?> foundFactoryClass : reflections.getTypesAnnotatedWith(PortFactoryStyle.class)) {
            final String foundFactoryStyle = foundFactoryClass.getAnnotation(PortFactoryStyle.class).name();
            if (foundFactoryStyle.equals(styleName)) {
                if (!AbstractPortFactory.class.isAssignableFrom(foundFactoryClass)) {
                    throw new RuntimeException("Found class '" + foundFactoryClass.getName() + "' is not a valid port-factory implementation");
                }
                final AbstractPortFactory factory;
                try {
                    factory = (AbstractPortFactory) foundFactoryClass.newInstance();
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException("Instantiating found class '" + foundFactoryClass.getName() + "' failed", e);
                }
                cachedFactories.put(foundFactoryStyle, factory);
                return true;
            }
        }
        return false;
    }
}
