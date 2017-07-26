package com.siemens.railfusion.hivemq.configuration;

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

    public String getMyProperty() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("myProperty");
    }
}
