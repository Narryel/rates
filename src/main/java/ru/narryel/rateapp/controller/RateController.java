package ru.narryel.rateapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.narryel.rateapp.service.RateService;


@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @GetMapping("/{currency}")
    public String getUsdRateByCurrency(@PathVariable("currency") String currency) {
        val currencyRate = rateService.getRubleRateToCurrency(currency);
        return String.format("USD rate to %s is %s", currency.toUpperCase(), currencyRate.toString());
    }
}
