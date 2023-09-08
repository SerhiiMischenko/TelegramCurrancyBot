package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.model.NewsModel;
import com.example.telegrambotgetcurrencyrate.model.WeatherModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeatherService {
    public static List<WeatherModel> getWeather(String latitude, String longitude, WeatherModel model) throws IOException {
        List<WeatherModel> weatherModels = new ArrayList<>();
        URL url;
            url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=50.640205&lon=26.20678&appid=4b623f564d2180dec0cbe581211baf0e&lang=ru&units=metric");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }

        JSONObject jsonObject = new JSONObject(result.toString());

        if (jsonObject.getString("status").equals("ok")) {
            JSONArray jsonArray = jsonObject.getJSONArray("articles");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                NewsModel newsModel = new NewsModel();
                newsModel.setAuthor(object.getJSONObject("source").getString("name"));
                newsModel.setTitle(object.getString("title"));
                newsModel.setUrl(object.getString("url"));
                newsList.add(newsModel);
                if(i >= 5){
                    break;
                }
            }
        }

        return newsList;
    }

    public static String formatWeather(WeatherModel weather) {
        return  "Ваш город: " + weather.getName() + "\n" +
                "Сейчас на улице: " + weather.getDescription() + "\n" +
                "Сейчас на улице: " + weather.getDescription() + "\n" +
                "Температура воздуха: " + weather.getTemp() + "\n\n";
    }
}
