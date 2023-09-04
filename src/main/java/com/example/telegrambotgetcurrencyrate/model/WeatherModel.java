package com.example.telegrambotgetcurrencyrate.model;

import lombok.Data;

@Data
public class WeatherModel {
    private String name;
    private String description;
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
}
