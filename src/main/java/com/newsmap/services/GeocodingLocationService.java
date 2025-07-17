package com.newsmap.services;

import com.newsmap.entities.GeocodingLocationEntity;
import com.newsmap.events.GeocodingLocationEvent;

public interface GeocodingLocationService {
    boolean isLocationInsideLocation(GeocodingLocationEntity insideLocation,
                                     GeocodingLocationEntity outLocationLocation);

    GeocodingLocationEntity constructEntityFromEvent(GeocodingLocationEvent locationEvent);
}
