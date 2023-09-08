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
            url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                    "&lon=" + longitude + "&appid=4b623f564d2180dec0cbe581211baf0e&lang=ru&units=metric");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }

        JSONObject jsonObject = new JSONObject(result.toString());
        WeatherModel weatherModel = new WeatherModel();
        weatherModel.setName(jsonObject.getString("name"));

            JSONArray descriptionArray = jsonObject.getJSONArray("weather");
            for (int i = 0; i < descriptionArray.length(); i++) {
                JSONObject object = descriptionArray.getJSONObject(i);
                weatherModel.setDescription(object.getString("description"));
            }
        JSONObject mainObject = jsonObject.getJSONObject("main");
        weatherModel.setTemp((int) mainObject.getDouble("temp"));
        weatherModel.setFeels_like((int) mainObject.getDouble("feels_like"));
        weatherModel.setTemp_min((int) mainObject.getDouble("temp_min"));
        weatherModel.setTemp_max((int) mainObject.getDouble("temp_max"));

        weatherModels.add(weatherModel);

        return weatherModels;
    }

    public static String formatWeather(WeatherModel weather) {
        return  "Ваш город: " + weather.getName() + "\n" +
                "Сейчас на улице: " + weather.getDescription() + "\n" +
                "Температура воздуха: " + weather.getTemp() + "\n" +
                "Ощушается как: " + weather.getFeels_like() + "\n" +
                "Минимальная температура: " + weather.getTemp_min() + "\n" +
                "Максимальная температура: " + weather.getTemp_max() + "\n\n";
    }
}
