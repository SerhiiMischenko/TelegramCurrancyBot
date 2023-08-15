package com.example.telegrambotgetcurrencyrate.bot;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
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
                } else if (callbackData.equals("/news")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "In /news block");
                } else if (callbackData.equals("/weather")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "In /weather block");
                }
            }
        }
}
