package com.example.exchange_rate_service.controller;

import com.example.exchange_rate_service.model.CurrencyRate;
import com.example.exchange_rate_service.service.ExchangeRateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    // 1. دریافت نرخ ارز برای یک جفت ارز
    @GetMapping("/exchange-rates")
    public CurrencyRate getRate(@RequestParam String baseCurrency, @RequestParam String targetCurrency) {
        return exchangeRateService.getRate(baseCurrency, targetCurrency);
    }

    // 2. دریافت لیست ارزهای پشتیبانی شده
    @GetMapping("/currencies")
    public List<String> getSupportedCurrencies() {
        return exchangeRateService.getSupportedCurrencies();
    }

    // 3. دریافت تعداد درخواست‌های ثبت شده برای هر ارز
    @GetMapping("/currencies/request-counts")
    public Map<String, Integer> getCurrencyRequestCount() {
        return exchangeRateService.getCurrencyRequestCount();
    }

    // 4. تبدیل مقدار ارز از یک ارز به ارز دیگر
    @GetMapping("/currency-conversions")
    public double convertAmount(@RequestParam String fromCurrency, @RequestParam String toCurrency, @RequestParam double amount) {
        return exchangeRateService.convertAmount(fromCurrency, toCurrency, amount);
    }

    // 5. دریافت لینک نمودار نرخ ارز
    @GetMapping("/currencies/{baseCurrency}/chart")
    public String getChartLink(@PathVariable String baseCurrency, @RequestParam String targetCurrency) {
        return "https://www.xe.com/currencycharts/?from=" + baseCurrency + "&to=" + targetCurrency;
    }
}