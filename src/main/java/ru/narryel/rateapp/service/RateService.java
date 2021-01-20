package ru.narryel.rateapp.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.narryel.rateapp.dto.ExchangeRatesApiResponse;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RateService {

    public static final String RATES_API_APP_KEY = "35b85788d95c4debbd776948d82e3240";

    private final RestTemplate restTemplate;

    public Double getCurrencyRateToUsd(String currency) {
        return requestCurrencyRateAndParseResponse(null, currency);
    }


    @SneakyThrows
    private Double requestCurrencyRateAndParseResponse(LocalDate date, String currency) {

        val uriString = date == null ? String.format("https://openexchangerates.org/api/latest.json?app_id=%s", RATES_API_APP_KEY)
                : String.format("https://openexchangerates.org/api/historical/%s.json?app_id=%s", date.format(DateTimeFormatter.ISO_LOCAL_DATE), RATES_API_APP_KEY);
        val response = restTemplate.getForObject(new URI(uriString), ExchangeRatesApiResponse.class);
        val currencyKey = currency.toUpperCase();
        assert response != null;
        val currencyRate = response.getRates().get(currencyKey);
        if (currencyRate == null) {
            throw new RuntimeException(String.format("Не пришел курс по валюте с ключом %s", currency));
        }
        return currencyRate;
    }

    public Double getYesterdayCurrencyRateToUsd(String currency) {
        return requestCurrencyRateAndParseResponse(LocalDate.now().minusDays(1), currency);
    }
}
