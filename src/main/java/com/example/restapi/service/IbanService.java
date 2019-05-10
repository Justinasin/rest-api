package com.example.restapi.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class IbanService {

    public static Map<String, Integer> parseJson() {

        Map<String, Integer> ibanList = new HashMap<>();
        jsonToMap(ibanList);

        return ibanList;

    }

    public static void jsonToMap(Map<String, Integer> map) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("iban.json"));
            JSONObject element = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) element.get("values");
            for (Object ms : jsonArray) {
                element = (JSONObject) ms;
                String country = (String) element.get("code");
                int length = Integer.parseInt((String) element.get("length"));
                map.put(country, length);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIban(String iban) {

        Map<String, Integer> ibanCountryList = parseJson();

        iban = iban.trim().replaceAll("\\s+", ""); // trims the line and replaces spaces with empty char

        if (!ibanCountryList.containsKey(iban.substring(0, 2)) || iban.length() != ibanCountryList.get(iban.substring(0, 2))) {

            return false;

        }

        String reformatedIban = iban.substring(4) + iban.substring(0, 4); // takes string from 4th char and first four chars
        char[] charIban = reformatedIban.toCharArray(); // makes string char array
        StringBuilder stringBuilder = new StringBuilder();

        for (char i : charIban) {
            if (i >= 65 && i <= 91) {
                stringBuilder.append(i - 55);
            } else if (i >= 48 && i <= 57) {
                stringBuilder.append(i);
            }
        }

        BigInteger bigInteger = new BigInteger(stringBuilder.toString());
        BigInteger mod = new BigInteger("97");
        if (bigInteger.mod(mod).toString().equals("1")) {
            return true;
        } else {
            return false;
        }

    }



}
