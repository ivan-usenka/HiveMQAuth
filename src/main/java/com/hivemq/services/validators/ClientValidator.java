package com.hivemq.services.validators;

/**
 * Created by ivan.usenka on 07-Sep-17.
 */
public interface ClientValidator {

    boolean isIpInRange(String ipAddress);

    boolean isUsernameAndPasswordValid(String username, String password);

    boolean isIpAndUsernameAndPasswordValid(String ipAddress, String username, String password);
}
