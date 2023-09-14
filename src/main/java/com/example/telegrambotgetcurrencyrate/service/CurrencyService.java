package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Service
public class CurrencyService {
    public static String getCurrencyRate(String message, CurrencyModel model) throws IOException {
        String url = "https://obmenka.kharkov.ua";
        Document doc = Jsoup.connect(url).get();

        Elements scriptElements = doc.select("script");

        for (Element scriptElement : scriptElements) {
            String scriptContent = scriptElement.html();
            if (scriptContent.contains("\"rates\":")) {
                String startMarker = "\"rates\":[{";
                String endMarker = "}],";

                int startIndex = scriptContent.indexOf(startMarker);
                int endIndex = scriptContent.lastIndexOf(endMarker);

                if (startIndex != -1 && endIndex != -1) {
                    String extractedText = "{" + scriptContent.substring(startIndex, endIndex + endMarker.length() - 1) + "}";
                    JSONObject jsonArray = new JSONObject(extractedText);
                    JSONArray rateArray = jsonArray.getJSONArray("rates");
                    for (int i = 0; i < rateArray.length(); i++) {
                        JSONObject rateJson = rateArray.getJSONObject(i);
                        if(rateJson.getString("currencyBase").equalsIgnoreCase(message)) {
                            model.setCurrencyBase(rateJson.getString("currencyBase"));
                            model.setRateBid(rateJson.getDouble("rateBid"));
                            model.setRateAsk(rateJson.getDouble("rateAsk"));
                        }
                    }
                } else {
                    System.out.println("Start and end markers not found.");
                }
            }
        }
        return "Официальный курс украинской гривны к " + model.getCurrencyBase() + ":" + "\n" +
                "Покупка " + model.getRateBid() + " грн." + "\n" +
                "Продажа " + model.getRateAsk() + " грн." + "\n";

    }
}