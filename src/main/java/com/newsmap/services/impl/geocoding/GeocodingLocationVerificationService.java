package com.newsmap.services.impl.geocoding;

import com.newsmap.models.GeocodingLocationModel;
import org.springframework.stereotype.Service;

@Service
public class GeocodingLocationVerificationService {
    public boolean isWithinTrackedBoundingBox(GeocodingLocationModel geocodingLocationModel, Double[] trackedLocationBoundingBox) {
        double newLat = Double.parseDouble(geocodingLocationModel.getLatitude());
        double newLon = Double.parseDouble(geocodingLocationModel.getLongitude());

        return newLat >= trackedLocationBoundingBox[0]
                && newLat <= trackedLocationBoundingBox[1]
                && newLon >= trackedLocationBoundingBox[2]
                && newLon <= trackedLocationBoundingBox[3] ;
    }
}
