package com.example.telegrambotgetcurrencyrate.bot;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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

    @Override
    public void onUpdateReceived(Update update) {
        CurrencyModel currencyModel = new CurrencyModel();
        String currency = "";

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else if (messageText.equals("Курс валют")) {
                try {
                    currency = CurrencyService.getCurrencyRate(messageText, currencyModel);
                } catch (IOException e) {
                    sendMessage(chatId, "We have not found such a currency.\n"
                            + "Enter the currency whose official exchange rate\n"
                            + "you want to know in relation to BYN.\n"
                            + "For example: USD");
                } catch (ParseException e) {
                    throw new RuntimeException("Unable to parse date");
                }
                sendMessage(chatId, currency);
            } else if (messageText.equals("Новости Украины")) {
                sendMessage(chatId, "In /news block");
            } else if (messageText.equals("Погода")) {
                sendMessage(chatId, "In /weather block");
            } else {
                sendMessage(chatId, "Выбери раздел из списка");
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
            String answer = "Приветствую, " + name + "." + "\n" +
                    "Я могу предоставить информацию по некоторым темам" + "\n" +
                    "Выбери интересующую тебя тему";
            sendMessage(chatId, answer);
        }

    private void sendMessage(Long chatId, String textToSend) {
        ReplyKeyboardMarkup replyKeyboardMarkup = createKeyboard();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) {
        }
    }
    private ReplyKeyboardMarkup createKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();

        // Создание кнопки с текстом "Курс валют" и callback-данными "/currency"
        KeyboardButton currencyButton = new KeyboardButton("Курс валют");
        KeyboardButton newsButton = new KeyboardButton("Новости Украины");
        KeyboardButton weatherButton = new KeyboardButton("Прогноз погоды");

        keyboardRow.add(currencyButton);
        keyboardRow.add(newsButton);
        keyboardRow.add(weatherButton);

        keyboardRows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

}
