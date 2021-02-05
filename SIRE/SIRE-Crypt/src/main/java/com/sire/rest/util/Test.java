package com.sire.rest.util;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.*;

public class Test {
    public static void main(String args[]) throws MalformedURLException {

        ResponseErrorHandler responseHandler = new ResponseErrorHandler() {

            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {

                if (response.getStatusCode() != HttpStatus.OK) {
                    System.out.println(response.getStatusText());
                }
                return response.getStatusCode() == HttpStatus.OK ? false : true;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // TODO Auto-generated method stub

            }
        };

        try {

            String responseBody = new RestUtil<String, String>().execute(
                    new RequestDetails("https://jsonplaceholder.typicode.com/todos/1", HttpMethod.GET), "", null,
                    responseHandler, String.class);

            System.out.println(responseBody);

            //responseBody = new RestUtil<String, String>().execute(
            //        new RequestDetails("http://localhost:8082/create", HttpMethod.POST), "Data", null, responseHandler,
            //        String.class);

            //System.out.println(responseBody);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
