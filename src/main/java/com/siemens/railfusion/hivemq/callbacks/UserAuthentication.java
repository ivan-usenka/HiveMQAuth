package com.siemens.railfusion.hivemq.callbacks;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.hivemq.spi.aop.cache.Cached;
import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.exception.AuthenticationException;
import com.hivemq.spi.callback.security.OnAuthenticationCallback;
import com.hivemq.spi.message.ReturnCode;
import com.hivemq.spi.security.ClientCredentialsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan Usenka on 24-Jul-17.
 */
public class UserAuthentication implements OnAuthenticationCallback {

    Logger log = LoggerFactory.getLogger(UserAuthentication.class);

    @Override
    public Boolean checkCredentials(ClientCredentialsData clientData) throws AuthenticationException {


        String username;
        if (!clientData.getUsername().isPresent()) {

            throw new AuthenticationException("No Username provided", ReturnCode.REFUSED_NOT_AUTHORIZED);

        }
        username = clientData.getUsername().get();


        if (Strings.isNullOrEmpty(username)) {
            throw new AuthenticationException("No Username provided", ReturnCode.REFUSED_NOT_AUTHORIZED);

        }

        Optional<String> password = Optional.fromNullable(retrievePasswordFromDatabase(username));

        if (!password.isPresent()) {
            throw new AuthenticationException("No Account with the credentials was found!", ReturnCode.REFUSED_NOT_AUTHORIZED);

        } else {
            if (clientData.getPassword().get().equals(password.get())) {
                return true;
            }
            return false;
        }

    }

    @Cached(timeToLive = 10, timeUnit = TimeUnit.MINUTES)
    private String retrievePasswordFromDatabase(String username) {

        String password = "";   //Call to any database to ask for the password of the user

        return password;
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
