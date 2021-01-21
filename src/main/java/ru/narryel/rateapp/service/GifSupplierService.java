package ru.narryel.rateapp.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.narryel.rateapp.dto.ApiKeysConfigurationProperties;
import ru.narryel.rateapp.dto.GifDTO;
import ru.narryel.rateapp.dto.GiphyApiResponse;

import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifSupplierService {

    private final ApiKeysConfigurationProperties apiKeys;
    private final RestTemplate restTemplate;

    public static final String SAD_SEARCH_KEY = "sadness";
    public static final String HAPPY_SEARCH_KEY = "joy";


    public GifDTO getRandomSadGif() {
        return requestGifBySeachWord(SAD_SEARCH_KEY);
    }

    public GifDTO getRandomHappyGif() {
        return requestGifBySeachWord(HAPPY_SEARCH_KEY);
    }

    @SneakyThrows
    private GifDTO requestGifBySeachWord(String searchKey) {

        val giphyApiResponse = restTemplate.getForObject(
                new URI(String.format("https://api.giphy.com/v1/gifs/search?api_key=%s&q=%s", apiKeys.getGiphy(), searchKey)),
                GiphyApiResponse.class);

        assert giphyApiResponse != null;
        val randomGifNumber = ThreadLocalRandom.current().nextInt(0, giphyApiResponse.getData().length);

        return giphyApiResponse.getData()[randomGifNumber];
    }

}
