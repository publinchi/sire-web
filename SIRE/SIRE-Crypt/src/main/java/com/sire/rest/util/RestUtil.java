/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.rest.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pestupinan
 */
public class RestUtil<T, V>  {

    private RestTemplate restTemplate = new RestTemplate();

    public V execute(RequestDetails requestDetails, T data, HttpHeaders httpHeaders, ResponseErrorHandler errorHandler,
                     Class<V> genericClass) throws ResourceAccessException, Exception {

        restTemplate.setErrorHandler(errorHandler);
        if(Objects.isNull(httpHeaders))
            httpHeaders = new HttpHeaders();

        HttpEntity<T> entity = new HttpEntity<T>(data, httpHeaders);
        ResponseEntity<V> response = restTemplate.exchange(requestDetails.getUrl(), requestDetails.getRequestType(),
                entity, genericClass);
        return response.getBody();
    }
}