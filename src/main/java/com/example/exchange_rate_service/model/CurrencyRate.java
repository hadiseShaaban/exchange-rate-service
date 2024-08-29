package com.example.exchange_rate_service.model;

public class CurrencyRate {
    private String currency;
    private double rate;

    public CurrencyRate(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }
}