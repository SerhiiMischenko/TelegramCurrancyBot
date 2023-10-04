package com.example.telegrambotgetcurrencyrate.bot;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.CurrencyModel;
import com.example.telegrambotgetcurrencyrate.service.GPTService;
import com.example.telegrambotgetcurrencyrate.service.WeatherService;
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
    private final Handlers handlers;
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
        Massage massage = new Massage(this);

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
                    handlers.currencyHandler(callbackData, chatId, new CurrencyModel(), massage);
                }
                case "/news", "/top", "/technology", "/sport", "/business" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    handlers.newsHandler(callbackData, chatId, massage);
                }
                case "/location", "/today", "/tomorrow", "/days" -> {
                        long chatId = update.getCallbackQuery().getMessage().getChatId();
                        handlers.weatherHandler(callbackData, chatId, massage, userLocation);
                }
                case "/weather" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendLocationInlineMessage(chatId);
                }
                case "/ai" -> {
                    long chatId = update.getCallbackQuery().getMessage().getChatId();
                    massage.sendMessage(chatId, "Введите ваш вопрос для искусственного интеллекта \uD83E\uDD16:");
                }
            }
        }
    }
}