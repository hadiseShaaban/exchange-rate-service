package com.example.exchange_rate_service.service;

import com.example.exchange_rate_service.model.CurrencyRate;
import com.example.exchange_rate_service.util.ECBRateFetcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {
    private final Map<String, Integer> currencyRequestCount = new HashMap<>();
    private final ECBRateFetcher ecbRateFetcher;

    public ExchangeRateService(ECBRateFetcher ecbRateFetcher) {
        this.ecbRateFetcher = ecbRateFetcher;
    }

    public CurrencyRate getRate(String baseCurrency, String targetCurrency) {
        updateRequestCount(baseCurrency);
        updateRequestCount(targetCurrency);

        double baseRate = ecbRateFetcher.getRate(baseCurrency);
        double targetRate = ecbRateFetcher.getRate(targetCurrency);

        return new CurrencyRate(targetCurrency, targetRate / baseRate);
    }

    public List<String> getSupportedCurrencies() {
        return ecbRateFetcher.getSupportedCurrencies();
    }

    public Map<String, Integer> getCurrencyRequestCount() {
        return currencyRequestCount;
    }

    private void updateRequestCount(String currency) {
        currencyRequestCount.put(currency, currencyRequestCount.getOrDefault(currency, 0) + 1);
    }

    public double convertAmount(String fromCurrency, String toCurrency, double amount) {
        double rate = getRate(fromCurrency, toCurrency).getRate();
        return amount * rate;
    }
}