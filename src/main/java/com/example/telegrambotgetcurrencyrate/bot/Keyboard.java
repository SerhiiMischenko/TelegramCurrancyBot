package com.example.telegrambotgetcurrencyrate.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    InlineKeyboardMarkup createKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton currencyButton = new InlineKeyboardButton();
        currencyButton.setText("Курс валют");
        currencyButton.setCallbackData("/currency");
        row.add(currencyButton);

        InlineKeyboardButton newsButton = new InlineKeyboardButton();
        newsButton.setText("Новости");
        newsButton.setCallbackData("/news");
        row.add(newsButton);

        InlineKeyboardButton weatherButton = new InlineKeyboardButton();
        weatherButton.setText("Погода");
        weatherButton.setCallbackData("/weather");
        row.add(weatherButton);

        keyboard.add(row);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
