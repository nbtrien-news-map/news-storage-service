package com.newsmap.services.impl.geocoding;

import com.newsmap.models.GeocodingLocationModel;
import com.newsmap.services.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NominatimGeocodingService implements GeocodingService {
    private final RestTemplate restTemplate;

    @Override
    public GeocodingLocationModel getLocation(String address) {
        if (address == null || address.trim().isEmpty()) {
            return null;
        }

        String url = "https://nominatim.openstreetmap.org/search?q=" + address.replace(" ", "+") + "&format=json" +
                "&limit=1";
        try {
            HttpHeaders headers = new HttpHeaders();
//            headers.set("User-Agent", "NewsMapApp/1.0 (your.email@example.com)");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, List.class
            );

            List<Map<String, Object>> results = response.getBody();
            if (results != null && !results.isEmpty()) {
                Map<String, Object> result = results.get(0);
                GeocodingLocationModel geocodingLocationModel = new GeocodingLocationModel();
                geocodingLocationModel.setPlaceId(Long.parseLong(result.get("place_id").toString()));
                geocodingLocationModel.setName((String) result.get("name"));
                geocodingLocationModel.setLatitude(result.get("lat").toString());
                geocodingLocationModel.setLongitude(result.get("lon").toString());
                geocodingLocationModel.setPlaceRank((Integer) result.get("place_rank"));
                List<String> boundingBox = Collections.singletonList(Objects.toString(result.get("boundingbox")));
                if (boundingBox != null && boundingBox.size() == 4) {
                    try {
                        double[] boundingBoxArray = new double[]{
                                Double.parseDouble(boundingBox.get(0)), // min_lat
                                Double.parseDouble(boundingBox.get(1)), // max_lat
                                Double.parseDouble(boundingBox.get(2)), // min_lon
                                Double.parseDouble(boundingBox.get(3))  // max_lon
                        };
                        geocodingLocationModel.setBoundingBox(boundingBoxArray);
                    } catch (NumberFormatException e) {
                        geocodingLocationModel.setBoundingBox(null);
                    }
                } else {
                    geocodingLocationModel.setBoundingBox(null);
                }
                return geocodingLocationModel;
            }
        } catch (Exception e) {
            System.err.println("Geocoding error for address " + address + ": " + e.getMessage());
        }
        return null;
    }
}
