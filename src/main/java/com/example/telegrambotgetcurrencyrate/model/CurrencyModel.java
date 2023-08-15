package com.example.telegrambotgetcurrencyrate.model;

import lombok.Data;


@Data
public class CurrencyModel {
    private Integer currencyCodeA;
    private Double rateBuy;
    private Double rateSell;
}
