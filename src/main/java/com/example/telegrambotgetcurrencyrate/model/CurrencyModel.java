package com.example.telegrambotgetcurrencyrate.model;

import lombok.Data;
@Data
public class CurrencyModel {
    private String currencyBase;
    private Double rateBid;
    private Double rateAsk;
}
