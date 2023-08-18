package com.example.telegrambotgetcurrencyrate.bot;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class GPTIntegration {
    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";

    public static String generateGPTResponse(String message) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");

        // Создаем JSON-строку для запроса
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
                .url(GPT_API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray choices = jsonResponse.getJSONArray("choices");
                if (choices.length() > 0) {
                    JSONObject choice = choices.getJSONObject(0);
                    String messageValue = choice.getJSONObject("message").getString("content");
                    return messageValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
