package com.siemens.railfusion.hivemq.plugin;

import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.callback.registry.CallbackRegistry;
import com.siemens.railfusion.hivemq.callbacks.TopicAuthorization;
import com.siemens.railfusion.hivemq.callbacks.UserAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by Ivan Usenka on 24-Jul-17.
 */
public class AuthPlugin extends PluginEntryPoint {

    private final TopicAuthorization authorizationCallback;
    private final UserAuthentication authenticationCallback;

    Logger log = LoggerFactory.getLogger(AuthPlugin.class);

    @Inject
    public AuthPlugin(TopicAuthorization authorizationCallback, UserAuthentication authenticationCallback) {
        this.authorizationCallback = authorizationCallback;
        this.authenticationCallback = authenticationCallback;
    }

    /**
     * This method is executed after the instanciation of the whole class. It is used to initialize
     * the implemented callbacks and make them known to the HiveMQ core.
     */
    @PostConstruct
    public void postConstruct() {
        CallbackRegistry callbackRegistry = getCallbackRegistry();

        callbackRegistry.addCallback(authorizationCallback);
        callbackRegistry.addCallback(authenticationCallback);
    }
}
