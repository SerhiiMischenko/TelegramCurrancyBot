package com.example.telegrambotgetcurrencyrate.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    InlineKeyboardMarkup startInlineKeyboard() {
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

    InlineKeyboardMarkup currencyInlineKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton usdButton = new InlineKeyboardButton();
        usdButton.setText("USD");
        usdButton.setCallbackData("/usd");
        row.add(usdButton);

        InlineKeyboardButton eurButton = new InlineKeyboardButton();
        eurButton.setText("EUR");
        eurButton.setCallbackData("/eur");
        row.add(eurButton);

        InlineKeyboardButton rubButton = new InlineKeyboardButton();
        rubButton.setText("RUB");
        rubButton.setCallbackData("/rub");
        row.add(rubButton);

        InlineKeyboardButton plnButton = new InlineKeyboardButton();
        plnButton.setText("PLN");
        plnButton.setCallbackData("/pln");
        row.add(plnButton);

        InlineKeyboardButton gbpButton = new InlineKeyboardButton();
        gbpButton.setText("GBP");
        gbpButton.setCallbackData("/gbp");
        row.add(gbpButton);

        keyboard.add(row);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
