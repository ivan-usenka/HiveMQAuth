package com.hivemq.callbacks;

import com.hivemq.services.validators.ClientDataValidator;
import com.hivemq.spi.aop.cache.Cached;
import com.hivemq.spi.callback.CallbackPriority;
import com.hivemq.spi.callback.security.OnAuthorizationCallback;
import com.hivemq.spi.callback.security.authorization.AuthorizationBehaviour;
import com.hivemq.spi.security.ClientData;
import com.hivemq.spi.topic.MqttTopicPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ivan Usenka on 24-Jul-17.
 */
public class TopicAuthorization implements OnAuthorizationCallback {

    @Override
    @Cached(timeToLive = 24, timeUnit = TimeUnit.HOURS)
    public List<MqttTopicPermission> getPermissionsForClient(ClientData clientData) {
        ClientDataValidator clientDataValidator = new ClientDataValidator(clientData);
        List<MqttTopicPermission> mqttTopicPermissions = new ArrayList<>();

        //TODO Clarify info about actual suffix
        String topicName = clientData.getClientId().substring(1);


        //Jason's quote: "This will be two fold.
        //1.  We will use IP based authentication (authorization).
        //We desire to support the entry of specific IPs as well as IP ranges.
        //2.  HiveMQ Client Authentication (authorization) Plugin
        //We desire to support username/password authentication (authorization).
        //The user name will be the clientID however the password will be an alphanumeric string.  Similar to a API key."
        if (clientDataValidator.isIpInRange() &&
                clientDataValidator.isUserNamePresented() &&
                //According to HiveMQ support response we can just cast to ClientCredentialsData to be able to receive password
                clientDataValidator.isPasswordValid()) {

            //Jason's quote: " Devices that successfully authenticate (authorize)
            //will be allowed to publish and subscribe to topics that begins with their RFID.
            //(This is the suffix of their clientID)"
            mqttTopicPermissions.add(new MqttTopicPermission(topicName + "/#", MqttTopicPermission.TYPE.ALLOW));
        } else {

            //Jason's quote: "Devices that fail authentication (authorization) will only be allowed to
            //1. Publish to {rfid}/lost/
            //2. Subscribe to {rfid}/lost/res/"
            //
            //This point is the reason why all logic moved to authorization only.
            //Devices that fail authentication immediately disconnected and not allowed to publish/subscribe.
            //See http://www.hivemq.com/docs/plugins/latest/#hivemqdocs_client_authentication
            mqttTopicPermissions.add(new MqttTopicPermission(topicName + "/lost/#", MqttTopicPermission.TYPE.ALLOW, MqttTopicPermission.ACTIVITY.PUBLISH));
            mqttTopicPermissions.add(new MqttTopicPermission(topicName + "/lost/res/#", MqttTopicPermission.TYPE.ALLOW, MqttTopicPermission.ACTIVITY.SUBSCRIBE));
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
