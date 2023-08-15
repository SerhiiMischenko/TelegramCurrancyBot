package com.example.telegrambotgetcurrencyrate.bot;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.service.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        CurrencyModel currencyModel = new CurrencyModel();
        Massage massage = new Massage(this);
        String currency;
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                massage.startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            }
        }
        if (update.hasCallbackQuery()) {
                String callbackData = update.getCallbackQuery().getData();
                if (callbackData.equalsIgnoreCase("/currency")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendCurrencyInlineMessage(chatId);
                }
                else if (callbackData.equals("/usd")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    currency = CurrencyService.getCurrencyRate("840", currencyModel, "USD");
                    massage.sendMessage(chatId, currency);
                }
                else if (callbackData.equals("/eur")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    currency = CurrencyService.getCurrencyRate("978", currencyModel, "EUR");
                    massage.sendMessage(chatId, currency);
                }
                else if (callbackData.equals("/pln")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    currency = CurrencyService.getCurrencyRate("985", currencyModel, "PLN");
                    massage.sendMessage(chatId, currency);
                }
                else if (callbackData.equals("/rub")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    currency = CurrencyService.getCurrencyRate("643", currencyModel, "RUB");
                    massage.sendMessage(chatId, currency);
                }
                else if (callbackData.equals("/gbp")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    currency = CurrencyService.getCurrencyRate("826", currencyModel, "GBP");
                    massage.sendMessage(chatId, currency);
                }
                else if (callbackData.equals("/news")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "In /news block");
                }
                else if (callbackData.equals("/weather")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "In /weather block");
                }
            }
        }
}
