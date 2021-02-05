package com.sire.crypt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.*;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String args[]) throws GeneralSecurityException, IOException {
        Map map = send();
        recieve(map);
    }

    private static Map send() throws IOException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException {

        Map mapData = new HashMap<>();

        mapData.put("identificacion", "0801255621");
        mapData.put("tipoIdentificacion", "C");

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(mapData);
            System.out.println("DataJSONstring = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String ivString = "0000000000000000";
        String secretKey = "boooooooooooooooooooooooooom!!!!";

        System.out.println(ivString.getBytes().length);

        String encryptedData = CryptUtil.encryptWithAesCBC(json, ivString, secretKey);

        String publicKey = "/home/pestupinan/IdeaProjects/SIRE/SIRE-EE/SIRE/SIRE-Crypt/publicKey.pem";
        PublicKey publicRSA = PemUtils.readPublicKeyFromFile(publicKey, "RSA");

        String encryptedIV = CryptUtil.encrypt(publicRSA, ivString) ;
        String encryptedSecretKey = CryptUtil.encrypt(publicRSA, secretKey);

        Map map = new HashMap<>();

        map.put("SessionKey", encryptedSecretKey);
        map.put("IV", encryptedIV);
        map.put("Data", encryptedData);

        try {
            json = mapper.writeValueAsString(map);
            System.out.println("ResultingJSONstring Sent = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //
        System.out.println();
        String privateKey = "/home/pestupinan/IdeaProjects/SIRE/SIRE-EE/SIRE/SIRE-Crypt/privateKey.pem";
        PrivateKey privateRSA = PemUtils.readPrivateKeyFromFile(privateKey, "RSA");

        String iv = CryptUtil.decrypt(privateRSA, encryptedIV);
        String sessionKey = CryptUtil.decrypt(privateRSA, encryptedSecretKey);

        System.out.println("IV: " + iv);
        System.out.println("SessionKey: " + sessionKey);

        String decryptedData = CryptUtil.decryptWithAesCBC(encryptedData, iv, sessionKey);

        System.out.println("Data: " + decryptedData);
        System.out.println();
        //

        String REST_URI = "http://10.2.2.69:8095/api/ServiciosExternosCredito/ConsultaScoreCliente";

        Client client = ClientBuilder.newClient();
        Response response = client.target(REST_URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(json, MediaType.APPLICATION_JSON));

        TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {};

        String jsonResponse = response.readEntity(String.class);

        System.out.println();
        System.out.println("ResultingJSONstring Recived " + jsonResponse);
        System.out.println();

        Map<String, String> mapResponse = mapper.readValue(jsonResponse, typeRef);

        System.out.println("--> 2: " + mapResponse);
        System.out.println();

        return mapResponse;
    }

    private static void recieve(Map map) throws IOException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String privateKey = "/home/pestupinan/IdeaProjects/SIRE/SIRE-EE/SIRE/SIRE-Crypt/privateKey.pem";

        PrivateKey privateRSA = PemUtils.readPrivateKeyFromFile(privateKey, "RSA");

        String encryptedIV = (String) map.get("IV");
        String encryptedSecretKey = (String) map.get("SessionKey");
        String encryptedData = (String) map.get("Data");

        System.out.println("encryptedIV: " + encryptedIV);
        System.out.println();
        System.out.println("encryptedSecretKey:" + encryptedSecretKey);
        System.out.println();
        System.out.println("encryptedData: " + encryptedData);
        System.out.println();

        String iv = CryptUtil.decrypt(privateRSA, encryptedIV).substring(0,16);
        String secretKey = CryptUtil.decrypt(privateRSA, encryptedSecretKey).substring(0,32);
        String decryptedData = CryptUtil.decryptWithAesCBC(encryptedData, iv, secretKey);

        System.out.println("IV: " + iv);
        System.out.println("SessionKey:" + secretKey);
        System.out.println("Data: " + decryptedData);
    }
}