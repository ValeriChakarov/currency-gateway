package com.example.currency.gateway.clients;

import org.json.*;

public class Tester {

    //    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        FixerClient client = new FixerClient(restTemplate);
//        client.getExchangeRates();
//
//    }
    public static String xmlString = "<command id=\"1234\" >\n" +
            "<get consumer=\"13617162\" >\n" +
            "<currency>EUR</currency>\n" +
            "</get>\n" +
            "</command>";

    public static void main(String[] args) {
        try {
            JSONObject json = XML.toJSONObject(xmlString); // converts xml to json
            String jsonPrettyPrintString = json.toString(4); // json pretty print
            System.out.println(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    }
}
