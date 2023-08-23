package com.example.telegrambotgetcurrencyrate.bot;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.model.NewsModel;
import com.example.telegrambotgetcurrencyrate.service.CurrencyService;
import com.example.telegrambotgetcurrencyrate.service.NewsService;

import java.io.IOException;
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
        String news;
        switch (handler) {
            case "/news" -> massage.sendNewsInlineMessage(chatId);
            case "/worldNews" -> {
                news = NewsService.getNews("World", newsModel);
                massage.sendMessage(chatId, news);
            }
            case "/ukraineNews" -> {
                news = NewsService.getNews("Ukraine", newsModel);
                massage.sendMessage(chatId, news);
            }
            case "/sport" -> {
                news = NewsService.getNews("Sport", newsModel);
                massage.sendMessage(chatId, news);
            }
            case "/economy" -> {
                news = NewsService.getNews("Economy", newsModel);
                massage.sendMessage(chatId, news);
            }
        }
    }
}
