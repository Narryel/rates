package ru.narryel.rateapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.narryel.rateapp.base.exception.RatesValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateService {

    private final ExternalRatesApiService externalRatesApiService;
    private final GifSupplierService gifSupplierService;

    public Double getCurrencyRateToUsd(String currency) {
        return externalRatesApiService.requestCurrentRateByCurrency(currency);
    }


    public Double getYesterdayCurrencyRateToUsd(String currency) {
        return externalRatesApiService.requestRateByDateAndCurrency(LocalDate.now().minusDays(1), currency);
    }

    public String getRateDifferenceGif(String date, String currency) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            log.error("Не удалось запарсить дату из запроса.", e);
            throw new RatesValidationException(String.format("Не удалось запарсить дату из запроса. Ошибка:%s ", e.getMessage()));
        }

        val currentRate = externalRatesApiService.requestCurrentRateByCurrency(currency);
        val rateByDate = externalRatesApiService.requestRateByDateAndCurrency(parsedDate, currency);


        return currentRate >= rateByDate ? gifSupplierService.getRandomHappyGif().getBitlyUrl() : gifSupplierService.getRandomSadGif().getBitlyUrl();
    }
}
