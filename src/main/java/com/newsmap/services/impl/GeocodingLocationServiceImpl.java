package com.newsmap.services.impl;

import com.newsmap.entities.GeocodingLocationEntity;
import com.newsmap.events.GeocodingLocationEvent;
import com.newsmap.services.GeocodingLocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeocodingLocationServiceImpl implements GeocodingLocationService {
    @Override
    public boolean isLocationInsideLocation(GeocodingLocationEntity insideLocation,
                                            GeocodingLocationEntity outsideLocation) {
        if (hasValidBoundingBox(insideLocation) && hasValidBoundingBox(outsideLocation)) {
            return isBoundingBoxInsideAnother(insideLocation.getBoundingBox(), outsideLocation.getBoundingBox());
        }

        if (hasValidLatLon(insideLocation) && hasValidBoundingBox(outsideLocation)) {
            return isPointInsideBoundingBox(insideLocation.getLatitude(), insideLocation.getLongitude(),
                                            outsideLocation.getBoundingBox());
        }

        return false;
    }

    @Override
    public GeocodingLocationEntity constructEntityFromEvent(GeocodingLocationEvent locationEvent) {
        GeocodingLocationEntity geocodingLocationEntity = new GeocodingLocationEntity();
        geocodingLocationEntity.setAddressType(locationEvent.getAddressType());
        geocodingLocationEntity.setLatitude(locationEvent.getLatitude());
        geocodingLocationEntity.setName(locationEvent.getName());
        geocodingLocationEntity.setImportance(locationEvent.getImportance());
        geocodingLocationEntity.setLongitude(locationEvent.getLongitude());
        geocodingLocationEntity.setDisplayName(locationEvent.getDisplayName());
        geocodingLocationEntity.setImportance(locationEvent.getImportance());
        geocodingLocationEntity.setPlaceId(locationEvent.getPlaceId());
        geocodingLocationEntity.setPlaceRank(locationEvent.getPlaceRank());
        geocodingLocationEntity.setBoundingBox(locationEvent.getBoundingBox());
        return geocodingLocationEntity;
    }

    private static boolean hasValidBoundingBox(GeocodingLocationEntity loc) {
        return loc.getBoundingBox() != null && loc.getBoundingBox().size() == 4;
    }

    private static boolean hasValidLatLon(GeocodingLocationEntity loc) {
        return loc.getLatitude() != null && loc.getLongitude() != null;
    }

    //    validate lat, long inside bounding box
    private static boolean isPointInsideBoundingBox(double lat, double lon, List<Double> boundingBox) {
        double south = boundingBox.get(0);
        double north = boundingBox.get(1);
        double west = boundingBox.get(2);
        double east = boundingBox.get(3);
        return lat >= south && lat <= north && lon >= west && lon <= east;
    }

    //    validate bounding box inside bounding box
    private static boolean isBoundingBoxInsideAnother(List<Double> aBox, List<Double> boundingBox) {
        double aSouth = aBox.get(0), aNorth = aBox.get(1), aWest = aBox.get(2), aEast = aBox.get(3);
        double bSouth = boundingBox.get(0), bNorth = boundingBox.get(1), bWest = boundingBox.get(2), bEast =
                boundingBox.get(3);

        return aSouth >= bSouth && aNorth <= bNorth &&
                aWest >= bWest && aEast <= bEast;
    }
}
