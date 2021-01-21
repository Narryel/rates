package ru.narryel.rateapp.base.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RatesValidationException extends RuntimeException {
    private final String message;
}
