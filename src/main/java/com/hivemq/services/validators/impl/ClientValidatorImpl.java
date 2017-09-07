package com.hivemq.services.validators.impl;

import com.google.inject.Inject;
import com.hivemq.services.dao.HiveMQConnectorsDBRESTClient;
import com.hivemq.services.validators.ClientValidator;

/**
 * Created by Ivan Usenka on 01-Aug-17.
 */
class ClientValidatorImpl implements ClientValidator {

    private final HiveMQConnectorsDBRESTClient dbrestClient;

    @Inject
    public ClientValidatorImpl(HiveMQConnectorsDBRESTClient dbrestClient) {
        this.dbrestClient = dbrestClient;
    }

    @Override
    public boolean isIpInRange(String ipAddress) {
        return false;
    }

    @Override
    public boolean isIpAndUsernameAndPasswordValid(String ipAddress, String username, String password) {
        return false;
    }

    @Override
    public boolean isUsernameAndPasswordValid(String username, String password) {
        return false;
    }
}
