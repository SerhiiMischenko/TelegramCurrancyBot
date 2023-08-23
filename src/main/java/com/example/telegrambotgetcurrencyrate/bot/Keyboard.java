package com.example.telegrambotgetcurrencyrate.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    private final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    private final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private final List<InlineKeyboardButton> row = new ArrayList<>();



    InlineKeyboardMarkup startInlineKeyboard() {
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

        InlineKeyboardButton aiButton = new InlineKeyboardButton();
        aiButton.setText("Чат GPT");
        aiButton.setCallbackData("/ai");
        row.add(aiButton);

        keyboard.add(row);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }

    InlineKeyboardMarkup currencyInlineKeyboard() {
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

    InlineKeyboardMarkup newsInlineKeyboard() {
        InlineKeyboardButton worldButton = new InlineKeyboardButton();
        worldButton.setText("World");
        worldButton.setCallbackData("/worldNews");
        row.add(worldButton);

        InlineKeyboardButton ukraineButton = new InlineKeyboardButton();
        ukraineButton.setText("Ukraine");
        ukraineButton.setCallbackData("/ukraineNews");
        row.add(ukraineButton);

        InlineKeyboardButton sportNews = new InlineKeyboardButton();
        sportNews.setText("Sport");
        sportNews.setCallbackData("/sport");
        row.add(sportNews);

        InlineKeyboardButton economicNews = new InlineKeyboardButton();
        economicNews.setText("Economy");
        economicNews.setCallbackData("/economy");
        row.add(economicNews);

        keyboard.add(row);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
