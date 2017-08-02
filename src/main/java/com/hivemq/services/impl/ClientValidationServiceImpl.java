package com.hivemq.services.impl;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.hivemq.services.ClientValidationService;
import com.hivemq.services.dao.HiveMQConnectorsDBRESTClient;
import com.hivemq.spi.aop.cache.Cached;
import com.hivemq.spi.security.ClientCredentialsData;
import com.hivemq.spi.security.ClientData;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan Usenka on 01-Aug-17.
 */
class ClientValidationServiceImpl implements ClientValidationService {

    HiveMQConnectorsDBRESTClient dbrestClient;

    @Inject
    public ClientValidationServiceImpl(HiveMQConnectorsDBRESTClient dbrestClient) {
        this.dbrestClient = dbrestClient;
    }

    @Override
    public boolean isIpInRange(ClientData clientData) {
        return false;
    }

    @Override
    public boolean isUserNamePresented(ClientData clientData) {
        //Jason's quote: "The user name will be the clientID"
        return !Strings.isNullOrEmpty(clientData.getClientId());
    }

    @Override
    public boolean isPasswordValid(ClientCredentialsData clientCredentialsData) {
        Optional<String> clientPassword = clientCredentialsData.getPassword();

        if (clientPassword.isPresent()) {
            Optional<String> storedPassword = Optional.fromNullable(retrievePasswordFromDatabase(clientCredentialsData.getUsername().get()));
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
