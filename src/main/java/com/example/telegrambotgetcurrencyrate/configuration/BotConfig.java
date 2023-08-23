package com.example.telegrambotgetcurrencyrate.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("config.properties")
public class BotConfig {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String token;
    @Value("${GPTService.GPT_API_URL}")
    String urlGPT;
    @Value("${GPTService.tokenGPT}")
    String tokenGPT;
    @Value("${newsService.tokenGPT}")
    String tokenNews;
}
