package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.model.NewsModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class NewsService {
    public static String getNews(String message, NewsModel model) throws IOException {
//        URL url = new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");
//        Scanner scanner = new Scanner((InputStream) url.getContent());
//        StringBuilder result = new StringBuilder();
//        while (scanner.hasNext()){
//            result.append(scanner.nextLine());
//        }
//
//        JSONArray jsonArray = new JSONArray(result.toString());
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject object = jsonArray.getJSONObject(i);
//            if(object.getString("cc").equalsIgnoreCase(message)){
//                model.setCc(object.getString("cc"));
//                model.setRateBuy(object.getDouble("rate"));
//                if(model.getCc().equals("USD")) {
//                    currencyModelInit(model, 0.5);
//                }if(model.getCc().equals("EUR")) {
//                    currencyModelInit(model, 0.6);
//                }if(model.getCc().equals("RUB")) {
//                    currencyModelInit(model, 0.2);
//                }if(model.getCc().equals("PLN")) {
//                    currencyModelInit(model, 0.2);
//                }if(model.getCc().equals("GBP")) {
//                    currencyModelInit(model, 0.365);
//                }
//                model.setRateSell(model.getRateSell() );
//
//                return "Официальный курс " + model.getCc() + " к украинской гривне:" + "\n" +
//                        "Покупка " + model.getRateBuy() + "." + "\n" +
//                        "Продажа " + model.getRateSell() + "." + "\n";
//            }
//        }
        return "Курс валюты " + message + " Privat Bank не предоставляет";
    }
}
