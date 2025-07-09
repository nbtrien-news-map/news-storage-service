//package com.newsmap.services.impl.newsprocessor;
//
//import com.newsmap.dtos.NewsResponseDto;
//import com.newsmap.entities.GeocodingLocationEntity;
//import com.newsmap.models.GeocodingLocationModel;
//import com.newsmap.repositories.GeocodingLocationRepository;
//import com.newsmap.services.AddressExtractorService;
//import com.newsmap.services.GeocodingService;
//import com.newsmap.services.NewsProcessorService;
//import com.newsmap.services.impl.geocoding.GeocodingLocationVerificationService;
//import lombok.RequiredArgsConstructor;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * Service class responsible for processing news articles from the Công an Đà Nẵng RSS feed.
// * This class fetches news articles from the specified RSS feed, extracts addresses from article descriptions,
// * and retrieves geographic coordinates using the Nominatim geocoding service.
// * The processed results are returned as a map of article titles to their corresponding location models.
// */
//@Service
//@RequiredArgsConstructor
//public class CadnNewsProcessorService implements NewsProcessorService {
//    private static final Logger logger = LoggerFactory.getLogger(CadnNewsProcessorService.class);
//    private final AddressExtractorService addressExtractorService;
//    private final GeocodingService nominatimGeocodingService;
//    private final RestTemplate restTemplate;
//    private final GeocodingLocationVerificationService geocodingLocationVerificationService;
//    private final GeocodingLocationRepository geocodingLocationRepository;
//
//    @Override
//    public List<NewsResponseDto> fetchNews() {
//        try {
//            GeocodingLocationEntity trackedLocationEntity = geocodingLocationRepository.findAll().get(0);
//            Document doc = Jsoup.connect("https://cadn.com.vn/rss/thoi-su-1.rss").get();
//            List<NewsResponseDto> results = new ArrayList<>();
//
//            for (Element item : doc.select("item")) {
//                String title = item.selectFirst("title") != null ? item.selectFirst("title").text() : "";
//                logger.debug("Processing article: {}", title);
//
//                String description = "";
//                Element descElement = item.selectFirst("description");
//                if (descElement != null) {
//                    String rawDescription = descElement.text();
//                    description = Jsoup.parse(rawDescription)
//                            .text()
//                            .replaceAll("\\s+", " ")
//                            .trim();
//                    logger.debug("Cleaned description: {}", description);
//                }
//
//                String link = item.selectFirst("link") != null ? item.selectFirst("link").text() : "";
//
//                String address = addressExtractorService.extractAddress(description);
//                if (Objects.isNull(address)) {
//                    logger.debug("No address found for article: {}", title);
//                    continue;
//                }
//
//                GeocodingLocationModel locationModel = nominatimGeocodingService.getLocation(address);
//                if (locationModel != null) {
//                    boolean isWithinTrackedBoundingBox =
//                            geocodingLocationVerificationService.isWithinTrackedBoundingBox(locationModel,
//                                                                                            trackedLocationEntity.getBoundingBox());
//                    if (isWithinTrackedBoundingBox) {
//                        results.add(NewsResponseDto.builder()
//                                            .id("")
//                                            .title(title)
//                                            .address(address)
//                                            .description(description)
//                                            .latitude(Double.parseDouble(locationModel.getLatitude()))
//                                            .longitude(Double.parseDouble(locationModel.getLongitude()))
//                                            .build());
//                        logger.info("Added location for address {}: {}", address, locationModel);
//                    } else {
//                        logger.warn("No location data for address: {}", address);
//                    }
//                }
//            }
//            return results;
//        } catch (IOException e) {
//            logger.error("Error fetching news from Công an Đà Nẵng RSS: {}", e.getMessage());
//            throw new RuntimeException("Error fetching news from Công an Đà Nẵng RSS: " + e.getMessage(), e);
//        }
//    }
//}