package com.hivemq.services.validators;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.hivemq.domain.HiveMQClientsData;
import com.hivemq.spi.security.ClientCredentialsData;
import com.hivemq.spi.security.ClientData;

/**
 * Created by Ivan Usenka on 01-Aug-17.
 */
public class ClientDataValidator {

    private final ClientData clientData;
    private final HiveMQClientsData mqClientsData;

    public ClientDataValidator(ClientData clientData, HiveMQClientsData mqClientsData) {
        this.clientData = clientData;
        this.mqClientsData = mqClientsData;
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
            Optional<String> storedPassword = Optional.fromNullable(""/*mqClientsData.getPassword()*/);
            return storedPassword.isPresent() && storedPassword.get().equals(clientPassword.get());
        } else {
            return false;
        }
    }
}
