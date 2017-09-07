package com.hivemq.callbacks;

import com.google.inject.Inject;
import com.hivemq.configuration.Configuration;
import com.hivemq.services.validators.ClientValidator;
import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.exception.AuthenticationException;
import com.hivemq.spi.callback.security.OnAuthenticationCallback;
import com.hivemq.spi.security.ClientCredentialsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ivan.usenka on 30-Aug-17.
 */
public class AuthenticationCallback implements OnAuthenticationCallback {

    private final ClientValidator clientValidator;
    private final Configuration configuration;

    Logger log = LoggerFactory.getLogger(AuthenticationCallback.class);

    @Inject
    public AuthenticationCallback(ClientValidator clientValidator, Configuration configuration) {
        this.clientValidator = clientValidator;
        this.configuration = configuration;
    }

    @Override
    public Boolean checkCredentials(ClientCredentialsData clientData) throws AuthenticationException {
        if (configuration.isExternalInstance()) {
            if (clientData.getInetAddress().isPresent()) {
                return clientValidator.isIpInRange(clientData.getInetAddress().get().getHostAddress());
            }
        } else {
            if (clientData.getInetAddress().isPresent()
                    && clientData.getPassword().isPresent()
                    && clientData.getUsername().isPresent()) {
                return clientValidator.isIpAndUsernameAndPasswordValid(clientData.getInetAddress().get().getHostAddress(),
                        clientData.getUsername().get(),
                        clientData.getPassword().get());

            }
        }
        return false;
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
