package com.example.telegrambotgetcurrencyrate.bot;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.service.CurrencyService;
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
}
