package com.example.telegrambotgetcurrencyrate.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
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
        String answer = "Приветствую \uD83D\uDC4B, "  + name + "." + "\n" +
                "Я могу предоставить информацию \uD83D\uDCDD по некоторым темам." + "\n" +
                "Также есть возможность задать любой вопрос искуственному интелекту \uD83E\uDD16." + "\n" +
                "Выбери интересующую тебя тему: ⬇️";
        sendStartInlineMessage(chatId, answer);
    }

    void sendCurrencyInlineMessage(Long chatId) {
        Keyboard keyboard = new Keyboard();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.currencyInlineKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Выбери интересующую тебя валюту \uD83D\uDCB5 \uD83D\uDCB6 \uD83D\uDCB4 \uD83D\uDCB7:");

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
        sendMessage.setText("В этом разделе ты можешь выбрать интересующие тебя темы \uD83D\uDCFA:");

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }

    void sendBackMainMenuMessage(Long chatId) {
        Keyboard keyboard = new Keyboard();
        ReplyKeyboardMarkup replyKeyboardMarkup = keyboard.backMainMenu();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Для возврата к главному меню нажмите кнопку ниже ⬇️");

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }

    void sendLocationInlineMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Для точного прогноза погоды \uD83C\uDF26️ " +
                "поделись своей геолокацией, нажав на изображение скрепки " +
                "\uD83D\uDCCE ниже ⬇️ и поделись своим местоположением\uD83D\uDCCD:");

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }

    void sendDaysWeatherInlineMessage(Long chatId) {
        Keyboard keyboard = new Keyboard();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.weatherInlineKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Выбери колличество дней в прогнозе:");

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
}
