package com.hivemq.services.dao.impl;

import com.hivemq.services.dao.HiveMQConnectorsDBRESTClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by Ivan Usenka on 02-Aug-17.
 */
class HiveMQConnectorsDBRESTClientImpl implements HiveMQConnectorsDBRESTClient {

    private HttpClient httpClient;

    public HiveMQConnectorsDBRESTClientImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public String executePost(String url, Object entity) {
        String response = null;
        try {
            response = Request
                    .Post(url)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .bodyForm()
                    .connectTimeout(1000)
                    .socketTimeout(1000).execute().handleResponse(new BasicResponseHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String executeGet(String url) {
        return null;
    }
}
