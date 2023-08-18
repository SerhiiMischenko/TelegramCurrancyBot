package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

public class CurrencyService {
    public static String getCurrencyRate(String message, CurrencyModel model) throws IOException, ParseException {
        URL url = new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }

        JSONArray jsonArray = new JSONArray(result.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if(object.getString("cc").equalsIgnoreCase(message)){
                model.setCc(object.getString("cc"));
                model.setRateBuy(object.getDouble("rate"));
                if(model.getCc().equals("USD")) {
                    model.setRateSell(model.getRateBuy() + (model.getRateBuy() * 0.5f / 10));
                    BigDecimal roundedNumberSell = BigDecimal.valueOf(model.getRateSell()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateSell(roundedNumberSell.doubleValue());
                    BigDecimal roundedNumberBuy = BigDecimal.valueOf(model.getRateBuy()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateBuy(roundedNumberBuy.doubleValue());
                }if(model.getCc().equals("EUR")) {
                    model.setRateSell(model.getRateBuy() + (model.getRateBuy() * 0.6f / 10));
                    BigDecimal roundedNumberSell = BigDecimal.valueOf(model.getRateSell()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateSell(roundedNumberSell.doubleValue());
                    BigDecimal roundedNumberBuy = BigDecimal.valueOf(model.getRateBuy()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateBuy(roundedNumberBuy.doubleValue());
                }if(model.getCc().equals("RUB")) {
                    model.setRateSell(model.getRateBuy() + (model.getRateBuy() * 0.2f / 10));
                    BigDecimal roundedNumberSell = BigDecimal.valueOf(model.getRateSell()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateSell(roundedNumberSell.doubleValue());
                    BigDecimal roundedNumberBuy = BigDecimal.valueOf(model.getRateBuy()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateBuy(roundedNumberBuy.doubleValue());
                }if(model.getCc().equals("PLN")) {
                    model.setRateSell(model.getRateBuy() + (model.getRateBuy() * 0.2f / 10));
                    BigDecimal roundedNumberSell = BigDecimal.valueOf(model.getRateSell()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateSell(roundedNumberSell.doubleValue());
                    BigDecimal roundedNumberBuy = BigDecimal.valueOf(model.getRateBuy()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateBuy(roundedNumberBuy.doubleValue());
                }if(model.getCc().equals("GBP")) {
                    model.setRateSell(model.getRateBuy() + (model.getRateBuy() * 0.365f / 10));
                    BigDecimal roundedNumberSell = BigDecimal.valueOf(model.getRateSell()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateSell(roundedNumberSell.doubleValue());
                    BigDecimal roundedNumberBuy = BigDecimal.valueOf(model.getRateBuy()).setScale(2, RoundingMode.HALF_UP);
                    model.setRateBuy(roundedNumberBuy.doubleValue());
                }
                model.setRateSell(model.getRateSell() );

                return "Официальный курс " + model.getCc() + " к украинской гривне:" + "\n" +
                        "Покупка " + model.getRateBuy() + "." + "\n" +
                        "Продажа " + model.getRateSell() + "." + "\n";
            }
        }
        return "Курс валюты " + message + " Privat Bank не предоставляет";
    }
}
