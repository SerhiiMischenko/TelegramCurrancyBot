package com.example.telegrambotgetcurrencyrate.service;

import com.example.telegrambotgetcurrencyrate.model.NewsModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsService {
    public static List<NewsModel> getNews(String message, NewsModel model) throws IOException {
        List<NewsModel> newsList = new ArrayList<>();
        URL url;
        if(message.equals("top")) {
            url = new URL("https://newsapi.org/v2/top-headlines?country=ua&apiKey=3498a47b50d04a8c9de814627d912064");
        }else {
            url = new URL("https://newsapi.org/v2/top-headlines?country=ua&category=" +
                    message + "&apiKey=3498a47b50d04a8c9de814627d912064");
        }
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }

        JSONObject jsonObject = new JSONObject(result.toString());

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

    public static String formatNews(NewsModel news) {
        return  "Автор: " + news.getAuthor() + "\n" +
                "Заголовок: " + news.getTitle() + "\n" +
                "Ссылка на новость: " + news.getUrl() + "\n\n";
    }
}
