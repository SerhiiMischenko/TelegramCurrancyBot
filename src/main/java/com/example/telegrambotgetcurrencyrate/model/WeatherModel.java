package com.example.telegrambotgetcurrencyrate.model;

import lombok.Data;

@Data
public class WeatherModel {
    private String name;
    private String description;
    private int temp;
    private int feels_like;
    private int temp_min;
    private int temp_max;
}
