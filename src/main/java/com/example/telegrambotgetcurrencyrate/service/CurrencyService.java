package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

public class CurrencyService {
    public static String getCurrencyRate(String message, CurrencyModel model) throws IOException, ParseException {
        URL url = new URL("https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }

        JSONArray jsonArray = new JSONArray(result.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if(object.getString("ccy").equalsIgnoreCase(message)){
                model.setCcy(object.getString("ccy"));
                model.setBase_ccy(object.getString("base_ccy"));
                model.setBuy(object.getDouble("buy"));
                model.setSale(object.getDouble("sale"));

                return "Официальный курс " + model.getCcy() + " к украинской гривне:" + "\n" +
                        "Покупка " + model.getBuy() + "\n" +
                        "Продажа " + model.getSale();
            }
        }
        return "Курс валюты " + message + " Privat Bank не предоставляет";
    }
}
