package com.example.telegrambotgetcurrencyrate.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor
public class Massage {
    private final TelegramBot telegramBot;
    void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
    private void sendStartInlineMessage(Long chatId, String textToSend) {
        Keyboard keyboard = new Keyboard();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.startInlineKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
    void startCommandReceived(Long chatId, String name) {
        String answer = "Приветствую, " + name + "." + "\n" +
                "Я могу предоставить информацию по некоторым темам" + "\n" +
                "Выбери интересующую тебя тему";
        sendStartInlineMessage(chatId, answer);
    }

    void sendCurrencyInlineMessage(Long chatId) {
        Keyboard keyboard = new Keyboard();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.currencyInlineKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Выбери интересующую тебя валюту:");

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }

    void sendNewsInlineMessage(Long chatId) {
        Keyboard keyboard = new Keyboard();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.newsInlineKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Выбери интересующую тебя тему:");

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
}
