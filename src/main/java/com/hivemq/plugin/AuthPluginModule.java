package com.hivemq.plugin;

import com.hivemq.spi.HiveMQPluginModule;
import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.plugin.meta.Information;

/**
 * Created by Ivan Usenka on 24-Jul-17.
 */
@Information(
        name = "Authorization and authentication plugin",
        author = "Ivan Usenka",
        version = "1.0",
        description = "A plugin created to support authorization and authentication checks on each message received"
)
public class AuthPluginModule extends HiveMQPluginModule {

    /**
     * This method is provided to execute some custom plugin configuration stuff. Is is the place
     * to execute Google Guice bindings,etc if needed.
     */
    @Override
    protected void configurePlugin() {

    }

    /**
     * This method needs to return the main class of the plugin.
     *
     * @return callback priority
     */
    @Override
    protected Class<? extends PluginEntryPoint> entryPointClass() {
        return AuthPlugin.class;
    }
}
