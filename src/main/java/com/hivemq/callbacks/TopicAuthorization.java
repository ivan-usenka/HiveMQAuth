package com.hivemq.callbacks;

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
    @Cached(timeToLive = 10, timeUnit = TimeUnit.MINUTES)
    public List<MqttTopicPermission> getPermissionsForClient(ClientData clientData) {
        List<MqttTopicPermission> mqttTopicPermissions = new ArrayList<>();

        //TODO Just for example. This data can be gathered from database or calculated manually
        mqttTopicPermissions.add(new MqttTopicPermission(clientData.getClientId() + "/#", MqttTopicPermission.TYPE.ALLOW));

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
