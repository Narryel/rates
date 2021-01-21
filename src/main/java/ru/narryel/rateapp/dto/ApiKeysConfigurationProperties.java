package ru.narryel.rateapp.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "api.key")
public class ApiKeysConfigurationProperties {
    private String giphy;
    private String rates;
}
