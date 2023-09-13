package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.model.WeatherModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class WeatherService {
    static String unixTimeEncoder(long unixTimeStamp) {
        Instant instant = Instant.ofEpochSecond(unixTimeStamp);
        LocalDate date = instant.atZone(ZoneId.of("UTC")).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    public static List<WeatherModel> getWeather(String latitude, String longitude, String status) throws IOException {
        List<WeatherModel> weatherModels = new ArrayList<>();
        URL url;
            url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longitude +
                    "&exclude=hourly&appid=5373f6c55cd9dbc4b37f3452e86182e8&lang=ru&units=metric");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }

        JSONObject jsonObject = new JSONObject(result.toString());
        JSONArray dailyArray = jsonObject.getJSONArray("daily");
        WeatherModel weatherModel = new WeatherModel();
        if(status.equals("today")) {
            URL urlCity;
            urlCity = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                    "&lon=" + longitude + "&appid=5373f6c55cd9dbc4b37f3452e86182e8&lang=ru&units=metric");
            Scanner scannerCity = new Scanner((InputStream) urlCity.getContent());
            StringBuilder resultCity = new StringBuilder();
            while (scannerCity.hasNext()){
                resultCity.append(scannerCity.nextLine());
            }


            JSONObject dailyJson = dailyArray.getJSONObject(0);
            weatherModel.setDate(unixTimeEncoder(dailyJson.getLong("dt")));
            JSONObject nameJson = new JSONObject(resultCity.toString());
            weatherModel.setName(nameJson.getString("name"));
            JSONArray descriptionArray = dailyJson.getJSONArray("weather");
            JSONObject descriptionJson = descriptionArray.getJSONObject(0);
            weatherModel.setDescription(descriptionJson.getString("description"));
            JSONObject tempJson = dailyJson.getJSONObject("temp");
            weatherModel.setTemp((int) tempJson.getDouble("day"));
            JSONObject feelsLikeJson = dailyJson.getJSONObject("feels_like");
            weatherModel.setFeels_like((int)feelsLikeJson.getDouble("day"));
            weatherModel.setTemp_min((int) tempJson.getDouble("min"));
            weatherModel.setTemp_max((int) tempJson.getDouble("max"));
        }


        weatherModels.add(weatherModel);

        return weatherModels;
    }

    public static String formatWeather(WeatherModel weather) {
        return  "Прогноз на : " + weather.getDate() + "\n" +
                "Ваше местонахождение: " + weather.getName() + "\n" +
                "В этот день " + weather.getDescription() + "\n" +
                "Температура воздуха: " + weather.getTemp() + " ℃ \n" +
                "Ощушается как: " + weather.getFeels_like() + "  ℃ \n" +
                "Минимальная температура: " + weather.getTemp_min() + "  ℃ \n" +
                "Максимальная температура: " + weather.getTemp_max() + "  ℃ \n\n";
    }
}
