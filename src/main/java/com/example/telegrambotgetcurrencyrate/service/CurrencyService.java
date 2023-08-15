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
    public static String getCurrencyRate(String message, CurrencyModel model, String currencyName) throws IOException, ParseException {
        URL url = new URL("https://api.monobank.ua/bank/currency");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }

        JSONArray jsonArray = new JSONArray(result.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if(object.getInt("currencyCodeA") == Integer.parseInt(message)){
                model.setCurrencyCodeA(object.getInt("currencyCodeA"));
                model.setRateBuy(object.getDouble("rateBuy"));
                model.setRateSell(object.getDouble("rateSell"));

                return "Официальный курс " + currencyName + " к украинской гривне:" + "\n" +
                        "Покупка" + model.getRateBuy() + "\n" +
                        "Продажа" + model.getRateSell() + "\n";
            }
        }
        return "Курс валюты " + message + " Privat Bank не предоставляет";
    }
}
