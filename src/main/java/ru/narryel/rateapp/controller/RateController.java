package ru.narryel.rateapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.narryel.rateapp.service.RateService;


@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @GetMapping("/current/{currency}")
    public String getUsdRateByCurrency(@PathVariable("currency") String currency) {
        val currencyRate = rateService.getCurrencyRateToUsd(currency);
        return String.format("USD rate to %s is %s", currency.toUpperCase(), currencyRate.toString());
    }

    @GetMapping("/yesterday/{currency}")
    public String getYesterdayUsdRateByCurrency(@PathVariable("currency") String currency) {
        val currencyRate = rateService.getYesterdayCurrencyRateToUsd(currency);
        return String.format("USD rate to %s is %s", currency.toUpperCase(), currencyRate.toString());
    }

    @GetMapping("/diff")
    public String getRateDifferenceGif(
            @RequestParam("date") String date,
            @RequestParam("cur") String currency
    ) {
        return rateService.getRateDifferenceGif(date, currency);
    }
}
