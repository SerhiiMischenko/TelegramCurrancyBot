package com.example.telegrambotgetcurrencyrate.bot;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.service.GPTService;
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
        Handlers handlers = new Handlers();
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                massage.startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            }
            if (update.getMessage().hasText() && !messageText.equals("/start")) {
                String gptResponse = GPTService.generateGPTResponse(messageText);
                massage.sendMessage(chatId, gptResponse);
            }
        }
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            switch (callbackData) {
                case "/currency", "/usd", "/eur", "/rub", "/pln", "/gbp" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    handlers.currencyHandler(callbackData, chatId, currencyModel, massage);
                }
                case "/news" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "In /news block");
                }
                case "/weather" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "In /weather block");
                }
                case "/ai" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "Введите ваш вопрос для искусственного интеллекта:");
                }
            }
        }
    }
}