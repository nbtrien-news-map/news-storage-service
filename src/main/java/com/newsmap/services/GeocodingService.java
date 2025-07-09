package com.newsmap.services;

import com.newsmap.models.GeocodingLocationModel;

public interface GeocodingService {
    GeocodingLocationModel getLocation(String address);
}
