package com.hivemq.callbacks;

import com.google.inject.Inject;
import com.hivemq.services.ClientValidationService;
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
public class TopicAuthorization implements OnAuthorizationCallback {

    private final ClientValidationService clientValidationService;

    @Inject
    public TopicAuthorization(ClientValidationService clientValidationService) {
        this.clientValidationService = clientValidationService;
    }

    @Override
    @Cached(timeToLive = 24, timeUnit = TimeUnit.HOURS)
    public List<MqttTopicPermission> getPermissionsForClient(ClientData clientData) {
        List<MqttTopicPermission> mqttTopicPermissions = new ArrayList<>();

        //TODO Clarify info about actual suffix
        String topicName = clientData.getClientId().substring(1);


        //Jason's quote: "This will be two fold.
        //1.  We will use IP based authentication.
        //We desire to support the entry of specific IPs as well as IP ranges.
        //2.  HiveMQ Client Authentication Plugin
        //We desire to support username/password authentication.
        //The user name will be the clientID however the password will be an alphanumeric string.  Similar to a API key."
        if (clientValidationService.isIpInRange(clientData) &&
                clientValidationService.isUserNamePresented(clientData) &&
                //According to HiveMQ support response we can just cast to ClientCredentialsData to be able to receive password
                clientValidationService.isPasswordValid((ClientCredentialsData) clientData))

            //Jason's quote: " Devices that successfully authenticate
            //will be allowed to publish and subscribe to topics that begins with their RFID.
            //(This is the suffix of their clientID)"
            mqttTopicPermissions.add(new MqttTopicPermission(topicName + "/#", MqttTopicPermission.TYPE.ALLOW));

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
