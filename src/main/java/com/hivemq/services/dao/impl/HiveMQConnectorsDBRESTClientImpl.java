package com.hivemq.services.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.domain.HiveMQClientsData;
import com.hivemq.services.dao.HiveMQConnectorsDBRESTClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ivan Usenka on 02-Aug-17.
 */
class HiveMQConnectorsDBRESTClientImpl implements HiveMQConnectorsDBRESTClient {

    @Override
    public HiveMQClientsData getClientsData() {
        try {

            //TODO Enter valid url after DB microservice implemented
            URL url = new URL("");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder builder = new StringBuilder();
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                builder.append(output);
            }

            conn.disconnect();

            return new ObjectMapper().readValue(builder.toString(), HiveMQClientsData.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
