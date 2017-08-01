package com.hivemq.configuration;

import com.google.inject.Inject;
import com.hivemq.spi.config.SystemInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Ivan Usenka on 24-Jul-17.
 */
public class PluginReader {

    private static final Logger log = LoggerFactory.getLogger(PluginReader.class);

    private final Properties properties = new Properties();
    private final SystemInformation systemInformation;

    @Inject
    PluginReader(SystemInformation systemInformation) {
        this.systemInformation = systemInformation;
    }

    @PostConstruct
    public void postConstruct() {
        final File pluginFolder = systemInformation.getPluginFolder();

        final File pluginFile = new File(pluginFolder, "authPlugin.properties");

        if (!pluginFile.canRead()) {
            log.error("Could not read the properties file {}", pluginFile.getAbsolutePath());
            return;
        }

        try (InputStream is = new FileInputStream(pluginFile)) {

            log.debug("Reading property file {}", pluginFile.getAbsolutePath());

            properties.load(is);


        } catch (IOException e) {
            log.error("An error occurred while reading the properties file {}", pluginFile.getAbsolutePath(), e);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
