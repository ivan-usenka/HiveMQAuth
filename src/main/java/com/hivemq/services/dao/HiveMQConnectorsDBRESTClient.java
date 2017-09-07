package com.hivemq.services.dao;

/**
 * Created by Ivan Usenka on 02-Aug-17.
 */
public interface HiveMQConnectorsDBRESTClient {

    //TODO Stub method until DB microservice is ready
    String executePost(String url, Object entity);

    String executeGet(String url);
}
