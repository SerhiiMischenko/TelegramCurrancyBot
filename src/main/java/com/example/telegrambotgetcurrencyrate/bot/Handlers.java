package com.example.telegrambotgetcurrencyrate.bot;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.model.NewsModel;
import com.example.telegrambotgetcurrencyrate.model.WeatherModel;
import com.example.telegrambotgetcurrencyrate.service.CurrencyService;
import com.example.telegrambotgetcurrencyrate.service.NewsService;
import com.example.telegrambotgetcurrencyrate.service.WeatherService;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.io.IOException;
import java.util.List;

public class Handlers {
     void currencyHandler(String handler, long chatId, CurrencyModel currencyModel, Massage massage) throws IOException {
         String currency;
         switch (handler) {
             case "/currency" -> massage.sendCurrencyInlineMessage(chatId);
             case "/usd" -> {
                 currency = CurrencyService.getCurrencyRate("USD", currencyModel);
                 massage.sendMessage(chatId, currency);
             }
             case "/eur" -> {
                 currency = CurrencyService.getCurrencyRate("EUR", currencyModel);
                 massage.sendMessage(chatId, currency);
             }
             case "/rub" -> {
                 currency = CurrencyService.getCurrencyRate("RUB", currencyModel);
                 massage.sendMessage(chatId, currency);
             }
             case "/pln" -> {
                 currency = CurrencyService.getCurrencyRate("PLN", currencyModel);
                 massage.sendMessage(chatId, currency);
             }
             case "/gbp" -> {
                 currency = CurrencyService.getCurrencyRate("GBP", currencyModel);
                 massage.sendMessage(chatId, currency);
             }
         }
    }

    void newsHandler(String handler, long chatId, NewsModel newsModel, Massage massage) throws IOException {
        switch (handler) {
            case "/news" -> massage.sendNewsInlineMessage(chatId);
            case "/top" -> {
                List<NewsModel> newsList  = NewsService.getNews("top", newsModel);
                for (NewsModel news : newsList) {
                    massage.sendMessage(chatId, NewsService.formatNews(news));
                }
                massage.sendBackMainMenuMessage(chatId);
            }
            case "/technology" -> {
                List<NewsModel> newsList  = NewsService.getNews("technology", newsModel);
                for (NewsModel news : newsList) {
                    massage.sendMessage(chatId, NewsService.formatNews(news));
                }
                massage.sendBackMainMenuMessage(chatId);
            }
            case "/sport" -> {
                List<NewsModel> newsList  = NewsService.getNews("sport", newsModel);
                for (NewsModel news : newsList) {
                    massage.sendMessage(chatId, NewsService.formatNews(news));
                }
                massage.sendBackMainMenuMessage(chatId);
            }
            case "/business" -> {
                List<NewsModel> newsList  = NewsService.getNews("business", newsModel);
                for (NewsModel news : newsList) {
                    massage.sendMessage(chatId, NewsService.formatNews(news));
                }
                massage.sendBackMainMenuMessage(chatId);
            }
        }
    }

    void weatherHandler(String handler, long chatId, Massage massage, Location userLocation)
            throws IOException {
        String latitude = String.valueOf(userLocation.getLatitude());
        String longitude = String.valueOf(userLocation.getLongitude());
        switch (handler) {
            case "/today" -> {
                List<WeatherModel> weatherList  = WeatherService.getTodayWeather(latitude, longitude);
                for (WeatherModel weather : weatherList) {
                    massage.sendMessage(chatId, WeatherService.formatWeather(weather));
                }
                massage.sendBackMainMenuMessage(chatId);
            }
            case "/tomorrow" -> {
                List<WeatherModel> weatherModels  = WeatherService.getTomorrowWeather(latitude, longitude);
                for (WeatherModel weather : weatherModels) {
                    massage.sendMessage(chatId, WeatherService.formatWeather(weather));
                }
                massage.sendBackMainMenuMessage(chatId);
            }
        }
    }
}
