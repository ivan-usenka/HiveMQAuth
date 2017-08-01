package com.hivemq.services;

import com.hivemq.spi.security.ClientCredentialsData;
import com.hivemq.spi.security.ClientData;

/**
 * Created by Ivan Usenka on 01-Aug-17.
 */
public interface ClientValidationService {

    /**
     * Check that client's IP address is corresponds to IP range stored in DB
     */
    boolean isIpInRange(ClientData clientData);

    /**
     * Check if username is presented
     */
    boolean isUserNamePresented(ClientData clientData);

    /**
     * Simple password validation. Check if presented password in {@link ClientCredentialsData} match the password stored in DB
     */
    boolean isPasswordValid(ClientCredentialsData clientCredentialsData);
}
