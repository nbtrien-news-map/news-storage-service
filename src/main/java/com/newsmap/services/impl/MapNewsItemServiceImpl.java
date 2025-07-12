package com.newsmap.services.impl;

import com.newsmap.entities.GeocodingLocationEntity;
import com.newsmap.entities.MapNewsItemEntity;
import com.newsmap.entities.NewsSourceEntity;
import com.newsmap.entities.NewsTrackedAreaEntity;
import com.newsmap.events.GeocodingLocationEvent;
import com.newsmap.events.SyncNewsLocationEvent;
import com.newsmap.exceptions.MapNewsItemExistException;
import com.newsmap.exceptions.ResourceNotFoundException;
import com.newsmap.repositories.GeocodingLocationRepository;
import com.newsmap.repositories.MapNewsItemRepository;
import com.newsmap.repositories.NewsSourceRepository;
import com.newsmap.repositories.NewsTrackedAreaRepository;
import com.newsmap.services.MapNewsItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapNewsItemServiceImpl implements MapNewsItemService {
    private final MapNewsItemRepository mapNewsItemRepository;
    private final NewsSourceRepository newsSourceRepository;
    private final GeocodingLocationRepository geocodingLocationRepository;
    private final NewsTrackedAreaRepository newsTrackedAreaRepository;

    @Override
    @Transactional
    public MapNewsItemEntity saveRawNewsItem(MapNewsItemEntity rawEntity, Integer providerId) {
        validateAndMapRawNewsItem(rawEntity, providerId);
        return mapNewsItemRepository.save(rawEntity);
    }

    @Override
    @Transactional
    public void saveNewsLocation(SyncNewsLocationEvent syncNewsLocationEvent) {
        MapNewsItemEntity mapNewsItemEntity =
                mapNewsItemRepository.findById(syncNewsLocationEvent.getMapNewsItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("MapNewsItemEntity not found"));

        if (Objects.isNull(syncNewsLocationEvent.getGeocodingLocation())) {
            mapNewsItemRepository.delete(mapNewsItemEntity);
            throw new ResourceNotFoundException("GeocodingLocationEvent not found");
        }

        if (Objects.nonNull(mapNewsItemEntity.getGeocodingLocation())) {
            throw new MapNewsItemExistException();
        }

        mapNewsTrackedAreas(mapNewsItemEntity, syncNewsLocationEvent.getGeocodingLocation());
        log.info("SAVED News Location: {}", mapNewsItemEntity);
        mapNewsItemRepository.save(mapNewsItemEntity);
    }

    private void validateAndMapRawNewsItem(MapNewsItemEntity rawEntity, Integer providerId) {
        if (mapNewsItemRepository.existsBySourceUrl(rawEntity.getSourceUrl())) {
            throw new MapNewsItemExistException();
        }

        NewsSourceEntity newsSourceEntity = newsSourceRepository.findById(providerId)
                .orElseThrow(RuntimeException::new);
        rawEntity.setNewsSource(newsSourceEntity);
        rawEntity.setProvider(newsSourceEntity.getNewsProvider().getName());
    }

    private void mapNewsTrackedAreas(MapNewsItemEntity mapNewsItemEntity, GeocodingLocationEvent locationEvent) {
        GeocodingLocationEntity geocodingLocationEntity =
                geocodingLocationRepository.findByPlaceId(locationEvent.getPlaceId())
                        .orElseGet(() -> {
                            GeocodingLocationEntity newLocation = constructGeocodingLocationEntity(locationEvent);
                            return geocodingLocationRepository.save(newLocation);
                        });
        mapNewsItemEntity.setGeocodingLocation(geocodingLocationEntity);
        Set<NewsTrackedAreaEntity> newsTrackedAreaEntities =
                newsTrackedAreaRepository.findAllByNewsSourceId(mapNewsItemEntity.getNewsSource().getNewsSourceId());
        for (NewsTrackedAreaEntity trackedAreaEntity : newsTrackedAreaEntities) {
            List<Double> trackedLocationBoundingBox = trackedAreaEntity.getGeocodingLocationEntity().getBoundingBox();
            if (isNewsLocationWithinTrackedBoundingBox(geocodingLocationEntity.getLatitude(),
                                                       geocodingLocationEntity.getLongitude(),
                                                       trackedLocationBoundingBox)) {
                mapNewsItemEntity.getTrackedAreas().add(trackedAreaEntity);
            }
        }

        if (mapNewsItemEntity.getTrackedAreas().isEmpty()) {
            throw new ResourceNotFoundException("Tracked Areas not found for location");
        }
    }

    public boolean isNewsLocationWithinTrackedBoundingBox(Double latitude, Double longitude,
                                                          List<Double> trackedLocationBoundingBox) {
        return latitude >= trackedLocationBoundingBox.get(0)
                && latitude <= trackedLocationBoundingBox.get(1)
                && longitude >= trackedLocationBoundingBox.get(2)
                && longitude <= trackedLocationBoundingBox.get(3);
    }

    private GeocodingLocationEntity constructGeocodingLocationEntity(GeocodingLocationEvent locationEvent) {
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
}
