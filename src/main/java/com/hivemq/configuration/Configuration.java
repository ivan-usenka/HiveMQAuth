package com.hivemq.configuration;

import com.google.inject.Inject;

import java.util.Properties;

/**
 * Created by Ivan Usenka on 24-Jul-17.
 */
public class Configuration {

    private final Properties properties;

    @Inject
    public Configuration(PluginReader pluginReader) {
        properties = pluginReader.getProperties();
    }

    public Boolean isExternalInstance() {
        if (properties == null) {
            return null;
        }

        return Boolean.valueOf(properties.getProperty("isExternal"));
    }
}
