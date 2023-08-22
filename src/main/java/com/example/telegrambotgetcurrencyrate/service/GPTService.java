package com.example.telegrambotgetcurrencyrate.service;
import com.example.telegrambotgetcurrencyrate.configuration.BotConfig;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.Duration;
@Component
@AllArgsConstructor
public class GPTService {
    private final BotConfig botConfig;
    public String generateGPTResponse(String message) {


        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(30))
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = "{\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"You are a helpful assistant.\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"" + message + "\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"model\": \"gpt-3.5-turbo\"\n" +
                "}";

        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(botConfig.getUrlGPT())
                .addHeader("Authorization", "Bearer " + botConfig.getTokenGPT())
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray choices = jsonResponse.getJSONArray("choices");
                if (!choices.isEmpty()) {
                    JSONObject choice = choices.getJSONObject(0);
                    return choice.getJSONObject("message").getString("content");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
