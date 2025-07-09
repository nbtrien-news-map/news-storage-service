package com.newsmap.services.impl;

import com.newsmap.services.AddressExtractorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AddressExtractorServiceImpl implements AddressExtractorService {
    private static final Logger logger = LoggerFactory.getLogger(AddressExtractorServiceImpl.class);

    private final RestTemplate restTemplate;

    private String locationExtractorUrl = "http://localhost:5001/extract-address";
    @Override
    public String extractAddress(String text) {
        if (text == null) {
            logger.warn("Input text is null, returning null");
            return null;
        }
        try {
            Map<String, String> request = Map.of("text", text);
            Map<String, String> response = restTemplate.postForObject(locationExtractorUrl, request, Map.class);
            String address = response != null ? response.get("address") : null;
            return address;
        } catch (Exception e) {
            logger.error("Error calling NewsMapLocationExtractor: {}", e.getMessage());
            return null;
        }
    }
}
