package ru.narryel.rateapp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheEvictHelper {

    @CacheEvict(value = "rates", allEntries = true)
    @Scheduled(cron = "0 0/2 * * * *")
    public void evictRatesCache() {
        log.info("Очистка кэша rates");
    }
}
