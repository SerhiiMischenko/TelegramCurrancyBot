package com.example.telegrambotgetcurrencyrate.model;

import lombok.Data;

@Data
public class CurrencyModel {
    private String ccy;
    private String base_ccy;
    private Double buy;
    private Double sale;
}
