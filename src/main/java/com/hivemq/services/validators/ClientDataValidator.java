package com.hivemq.services.validators;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.hivemq.spi.aop.cache.Cached;
import com.hivemq.spi.security.ClientCredentialsData;
import com.hivemq.spi.security.ClientData;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan Usenka on 01-Aug-17.
 */
public class ClientDataValidator {

    private final ClientData clientData;

    public ClientDataValidator(ClientData clientData) {
        this.clientData = clientData;
    }

    public boolean isIpInRange() {
        return true;
    }

    public boolean isUserNamePresented() {
        //Jason's quote: "The user name will be the clientID"
        return !Strings.isNullOrEmpty(clientData.getClientId());
    }

    public boolean isPasswordValid() {
        Optional<String> clientPassword = ((ClientCredentialsData) clientData).getPassword();

        if (clientPassword.isPresent()) {
            Optional<String> storedPassword = Optional.fromNullable(retrievePasswordFromDatabase(clientData.getUsername().get()));
            return storedPassword.isPresent() && storedPassword.get().equals(clientPassword.get());
        } else {
            return false;
        }
    }

    @Cached(timeToLive = 10, timeUnit = TimeUnit.MINUTES)
    private String retrievePasswordFromDatabase(String username) {

        //TODO Call to any database to ask for the password of the user
        String password = "";

        return password;
    }
}
