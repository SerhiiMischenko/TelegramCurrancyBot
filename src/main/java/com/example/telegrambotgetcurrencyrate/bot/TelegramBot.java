package com.example.telegrambotgetcurrencyrate.bot;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.model.NewsModel;
import com.example.telegrambotgetcurrencyrate.model.WeatherModel;
import com.example.telegrambotgetcurrencyrate.service.GPTService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final GPTService gptService;
    private Location userLocation;



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
        NewsModel newsModel = new NewsModel();
        WeatherModel weatherModel = new WeatherModel();
        Massage massage = new Massage(this);
        Handlers handlers = new Handlers();
        if(update.hasMessage() && update.getMessage().hasLocation()) {
            long chatId = update.getMessage().getChatId();
            userLocation = update.getMessage().getLocation();
            massage.sendDaysWeatherInlineMessage(chatId);
        }
        else if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start") || messageText.equals("Вернуться в главное меню")) {
                massage.startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            }
            if (update.getMessage().hasText() && !messageText.equals("/start") &&
                    update.getMessage().hasText() && !messageText.equals("Вернуться в главное меню")) {
                String gptResponse = gptService.generateGPTResponse(messageText);
                massage.sendMessage(chatId, gptResponse);
            }
        }
        else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            switch (callbackData) {
                case "/currency", "/usd", "/eur", "/rub", "/pln", "/gbp" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    handlers.currencyHandler(callbackData, chatId, currencyModel, massage);
                }
                case "/news", "/top", "/technology", "/sport", "/business" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    handlers.newsHandler(callbackData, chatId, newsModel, massage);
                }
                case "/location" -> {
                        long chatId = update.getCallbackQuery().getMessage().getChatId();
                        handlers.weatherHandler(callbackData, chatId, weatherModel, massage, userLocation);
                }
                case "/weather", "/today", "/tomorrow", "/days" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    handlers.weatherHandler(callbackData, chatId, weatherModel, massage, userLocation);
                }
                case "/ai" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "Введите ваш вопрос для искусственного интеллекта:");
                }
            }
        }
    }
}