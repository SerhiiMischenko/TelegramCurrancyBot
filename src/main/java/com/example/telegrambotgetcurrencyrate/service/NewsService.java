package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import com.example.telegrambotgetcurrencyrate.model.NewsModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Service
@RequiredArgsConstructor
public class NewsService {
    private final BotConfig botConfig;
    public List<NewsModel> getNews(String message) throws IOException {
        List<NewsModel> newsList = new ArrayList<>();
        JSONObject jsonObject = getJsonObject(message);

        if (jsonObject.getString("status").equals("ok")) {
            JSONArray jsonArray = jsonObject.getJSONArray("articles");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                NewsModel newsModel = new NewsModel();
                newsModel.setAuthor(object.getJSONObject("source").getString("name"));
                newsModel.setTitle(object.getString("title"));
                newsModel.setUrl(object.getString("url"));
                newsList.add(newsModel);
                if(i >= 5){
                    break;
                }
            }
        }

        return newsList;
    }

    @NotNull
    private JSONObject getJsonObject(String message) throws IOException {
        URL url;
        if(message.equals("top")) {
            url = new URL("https://newsapi.org/v2/top-headlines?country=ua&apiKey=" + botConfig.getTokenNews());
        }else {
            url = new URL("https://newsapi.org/v2/top-headlines?country=ua&category=" +
                    message + "&apiKey=" + botConfig.getTokenNews());
        }
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }

        return new JSONObject(result.toString());
    }

    public static String formatNews(NewsModel news) {
        return  "Автор: " + news.getAuthor() + "\n" +
                "Заголовок: " + news.getTitle() + "\n" +
                "Ссылка на новость: " + news.getUrl() + "\n\n";
    }
}
