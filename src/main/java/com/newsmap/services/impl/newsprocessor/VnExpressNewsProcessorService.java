package com.newsmap.services.impl.newsprocessor;


import com.newsmap.dtos.NewsResponseDto;
import com.newsmap.models.GeocodingLocationModel;
import com.newsmap.services.AddressExtractorService;
import com.newsmap.services.GeocodingService;
import com.newsmap.services.NewsProcessorService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VnExpressNewsProcessorService implements NewsProcessorService {
    private static final Logger logger = LoggerFactory.getLogger(VnExpressNewsProcessorService.class);
//    private final GeocodingService geocodingService;

    private final AddressExtractorService addressExtractorService;
    private final RestTemplate restTemplate;
    private final GeocodingService nominatimGeocodingService;

//    private RedisTemplate<String, String> redisTemplate;
//    @Value("${location.extractor.url}")
//    private String locationExtractorUrl;

    @Override
    public List<NewsResponseDto> fetchNews() {

        try {
            Document doc = Jsoup.connect("https://vnexpress.net/rss/tin-moi-nhat.rss").get();

            Map<String, GeocodingLocationModel> results = new HashMap<>();
            for (Element item : doc.select("item")) {
                String title = item.selectFirst("title") != null ? item.selectFirst("title").text() : "";
                String description = "";
                Element descElement = item.selectFirst("description");
                if (descElement != null) {
                    String rawDescription = descElement.text();
                    description = Jsoup.parse(rawDescription)
                            .text()
                            .replaceAll("\\s+", " ")
                            .trim();
                }

                String link = item.selectFirst("link") != null ? item.selectFirst("link").text() : "";
                String address = addressExtractorService.extractAddress(description);
                if (Objects.isNull(address)) {
                    continue;
                }

                GeocodingLocationModel locationModel = nominatimGeocodingService.getLocation(address);
                results.put(title, locationModel);
            }
            return List.of();
//            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
