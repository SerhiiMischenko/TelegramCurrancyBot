package com.example.telegrambotgetcurrencyrate.bot;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

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

    @Override
    public void onUpdateReceived(Update update) {
        CurrencyModel currencyModel = new CurrencyModel();
        String currency = "";

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else {
                try {
                    currency = CurrencyService.getCurrencyRate(messageText, currencyModel);

                } catch (IOException e) {
                    sendMessage(chatId, """
                            We have not found such a currency.
                            Enter the currency whose official exchange rate
                            you want to know in relation to BYN.
                            For example: USD""");
                } catch (ParseException e) {
                    throw new RuntimeException("Unable to parse date");
                }
                sendMessage(chatId, currency);
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Приветствую, " + name + "." + "\n" +
                "Я могу предоставить официальный курс валют согласно банка Privat Bank" + "\n" +
                "Введи интересующую тебя валюту" + "\n" +
                "Пример: USD";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) {

        }
    }
}
