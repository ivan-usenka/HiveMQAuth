package com.hivemq.callbacks;

import com.google.inject.Inject;
import com.hivemq.configuration.Configuration;
import com.hivemq.services.validators.ClientValidator;
import com.hivemq.spi.aop.cache.Cached;
import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.security.OnAuthorizationCallback;
import com.hivemq.spi.callback.security.authorization.AuthorizationBehaviour;
import com.hivemq.spi.security.ClientCredentialsData;
import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.topic.MqttTopicPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan Usenka on 24-Jul-17.
 */
public class AuthorizationCallback implements OnAuthorizationCallback {

    private final ClientValidator clientValidator;
    private final Configuration configuration;

    @Inject
    public AuthorizationCallback(ClientValidator clientValidator, Configuration configuration) {
        this.clientValidator = clientValidator;
        this.configuration = configuration;
    }

    @Override
    //TODO Approve caching time
    @Cached(timeToLive = 24, timeUnit = TimeUnit.HOURS)
    public List<MqttTopicPermission> getPermissionsForClient(ClientData clientData) {
        List<MqttTopicPermission> mqttTopicPermissions = new ArrayList<>();
        ClientCredentialsData clientCredsData = (ClientCredentialsData) clientData;

        //TODO Clarify info about actual suffix
        String topicName = clientData.getClientId().substring(1);

        //TODO Clarify actual topic names
        if (configuration.isExternalInstance()) {
            if (clientCredsData.getUsername().isPresent() && clientCredsData.getPassword().isPresent()
                    && clientValidator.isUsernameAndPasswordValid(clientCredsData.getUsername().get(), clientCredsData.getPassword().get())) {
                mqttTopicPermissions.add(new MqttTopicPermission(topicName + "/#", MqttTopicPermission.TYPE.ALLOW));


            } else {
                mqttTopicPermissions.add(new MqttTopicPermission(topicName + "/lost/#", MqttTopicPermission.TYPE.ALLOW, MqttTopicPermission.ACTIVITY.PUBLISH));
                mqttTopicPermissions.add(new MqttTopicPermission(topicName + "/lost/res/#", MqttTopicPermission.TYPE.ALLOW, MqttTopicPermission.ACTIVITY.SUBSCRIBE));
            }

        } else {
            //TODO Clarify case about internal validation
        }
        return mqttTopicPermissions;
    }

    @Override
    public AuthorizationBehaviour getDefaultBehaviour() {
        return AuthorizationBehaviour.DENY;
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
