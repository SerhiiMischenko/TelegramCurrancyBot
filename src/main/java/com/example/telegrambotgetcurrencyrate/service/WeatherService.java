package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.WeatherModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class WeatherService {
    private final BotConfig botConfig;
    static String unixTimeEncoder(long unixTimeStamp) {
        Instant instant = Instant.ofEpochSecond(unixTimeStamp);
        LocalDate date = instant.atZone(ZoneId.of("UTC")).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
    static String urlParser(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Scanner scanner;
        try {
            scanner = new Scanner((InputStream) url.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }
        return result.toString();
    }

    public static List<WeatherModel> getTodayWeather(String latitude, String longitude) {
        return getWeatherModels(latitude, longitude, 0);
    }

    public static List<WeatherModel> getTomorrowWeather(String latitude, String longitude) {
        return getWeatherModels(latitude, longitude, 1);
    }

    @NotNull
    private static List<WeatherModel> getWeatherModels(String latitude, String longitude, int dayCount) {
        ArrayList<WeatherModel> weatherModels = new ArrayList<>();
        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude + "&lon=" + longitude +
                "&exclude=hourly&appid=5373f6c55cd9dbc4b37f3452e86182e8&lang=ru&units=metric";
        JSONObject jsonObject = new JSONObject(urlParser(url));
        JSONArray dailyArray = jsonObject.getJSONArray("daily");
        WeatherModel weatherModel = new WeatherModel();
        String urlCity = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                "&lon=" + longitude + "&appid=5373f6c55cd9dbc4b37f3452e86182e8&lang=ru&units=metric";
        JSONObject dailyJson = dailyArray.getJSONObject(dayCount);
        weatherModel.setDate(unixTimeEncoder(dailyJson.getLong("dt")));
        JSONObject nameJson = new JSONObject(urlParser(urlCity));
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
