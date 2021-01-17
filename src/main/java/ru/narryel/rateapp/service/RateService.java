package ru.narryel.rateapp.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.narryel.rateapp.dto.ExchangeRatesApiResponse;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class RateService {

    public static final String RATES_API_APP_KEY = "35b85788d95c4debbd776948d82e3240";

    private final RestTemplate restTemplate;

    @SneakyThrows
    public Double getRubleRateToCurrency(String currency) {
        val uri = new URI(String.format("https://openexchangerates.org/api/latest.json?app_id=%s", RATES_API_APP_KEY));
        val response = restTemplate.getForObject(uri, ExchangeRatesApiResponse.class);
        val currencyKey = currency.toUpperCase();
        assert response != null;
        val currencyRate = response.getRates().get(currencyKey);
        if (currencyRate == null) {
            throw new RuntimeException(String.format("Не пришел курс по валюте с ключом %s", currency ));
        }
        return currencyRate;
    }

}
