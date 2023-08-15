package com.example.telegrambotgetcurrencyrate.bot;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.service.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
        String currency;
        if (update.hasMessage()) {
            System.out.println("In START");
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                System.out.println("Chat id massage " +  chatId);
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            }
        }
        if (update.hasCallbackQuery()) {
                System.out.println("In /currency");
                String callbackData = update.getCallbackQuery().getData();
            System.out.println(callbackData);
                if (callbackData.equalsIgnoreCase("/currency")) {
                    System.out.println("IN");
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    String messageText = update.getCallbackQuery().getMessage().getText();
                    currency = CurrencyService.getCurrencyRate(messageText, currencyModel);
                    System.out.println("Chat id backQuery " +  chatId);
                        System.out.println(currency);
                    sendMessage(chatId, currency);
                } else if (callbackData.equals("/news")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    sendMessage(chatId, "In /news block");
                } else if (callbackData.equals("/weather")) {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    sendMessage(chatId, "In /weather block");
                }
            }
        }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Приветствую, " + name + "." + "\n" +
                "Я могу предоставить информацию по некоторым темам" + "\n" +
                "Выбери интересующую тебя тему";
        sendStartInlineMessage(chatId, answer);
    }

    private void sendStartInlineMessage(Long chatId, String textToSend) {
        InlineKeyboardMarkup inlineKeyboardMarkup = createKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        System.out.println("In sendMessage");
        System.out.println(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
    private InlineKeyboardMarkup createKeyboard() {
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
