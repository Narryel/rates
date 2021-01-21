package ru.narryel.rateapp.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.narryel.rateapp.dto.ApiKeysConfigurationProperties;
import ru.narryel.rateapp.dto.ExchangeRatesApiResponse;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ExternalRatesApiService {

    private final RestTemplate restTemplate;
    private final ApiKeysConfigurationProperties apiKeys;

    @SneakyThrows
    public Double requestRateByDateAndCurrency(@Nullable LocalDate date, String currency) {

        val uriString = date == null ? String.format("https://openexchangerates.org/api/latest.json?app_id=%s", apiKeys.getRates())
                : String.format("https://openexchangerates.org/api/historical/%s.json?app_id=%s", date.format(DateTimeFormatter.ISO_LOCAL_DATE), apiKeys.getRates());
        val response = restTemplate.getForObject(new URI(uriString), ExchangeRatesApiResponse.class);
        val currencyKey = currency.toUpperCase();
        assert response != null;
        val currencyRate = response.getRates().get(currencyKey);
        if (currencyRate == null) {
            throw new RuntimeException(String.format("Не пришел курс по валюте с ключом %s", currency));
        }
        return currencyRate;
    }

    public Double requestCurrentRateByCurrency(String currency){
        return requestRateByDateAndCurrency(null, currency);
    }

}
