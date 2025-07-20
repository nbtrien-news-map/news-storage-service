package com.newsmap.services.impl;

import com.newsmap.constants.ErrorMessageConstants;
import com.newsmap.entities.GeocodingLocationEntity;
import com.newsmap.entities.MapNewsItemEntity;
import com.newsmap.entities.NewsSourceEntity;
import com.newsmap.entities.NewsTrackedAreaEntity;
import com.newsmap.enums.NewsSyncStatusEnum;
import com.newsmap.events.GeocodingLocationEvent;
import com.newsmap.events.SyncNewsLocationEvent;
import com.newsmap.exceptions.MapNewsItemExistException;
import com.newsmap.exceptions.ResourceNotFoundException;
import com.newsmap.repositories.GeocodingLocationRepository;
import com.newsmap.repositories.MapNewsItemRepository;
import com.newsmap.repositories.NewsSourceRepository;
import com.newsmap.repositories.NewsTrackedAreaRepository;
import com.newsmap.services.GeocodingLocationService;
import com.newsmap.services.MapNewsItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MapNewsItemServiceImpl implements MapNewsItemService {
    private final MapNewsItemRepository mapNewsItemRepository;
    private final NewsSourceRepository newsSourceRepository;
    private final GeocodingLocationRepository geocodingLocationRepository;
    private final NewsTrackedAreaRepository newsTrackedAreaRepository;
    private final GeocodingLocationService geocodingLocationService;
    private final MapNewsItemUpdaterService mapNewsItemUpdaterService;

    @Override
    @Transactional
    public MapNewsItemEntity saveRawNewsItem(MapNewsItemEntity rawEntity, Integer providerId) {
        validateAndMapRawNewsItem(rawEntity, providerId);
        rawEntity.setSyncStatus(NewsSyncStatusEnum.SYNCED_RAW_DATA.getValue());
        return mapNewsItemRepository.save(rawEntity);
    }

    @Override
    @Transactional
    public void saveNewsLocation(SyncNewsLocationEvent syncNewsLocationEvent) {
        Long mapNewsItemId = syncNewsLocationEvent.getMapNewsItemId();
        MapNewsItemEntity mapNewsItemEntity =
                mapNewsItemRepository.findByMapNewsItemIdAndSyncStatus(mapNewsItemId,
                                                                       NewsSyncStatusEnum.SYNCED_RAW_DATA.getValue())
                        .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessageConstants.MAP_NEWS_ITEM_NOT_FOUND,
                                                                                       mapNewsItemId)));

        try {
            if (Objects.isNull(syncNewsLocationEvent.getGeocodingLocation())) {
                throw new ResourceNotFoundException("GeocodingLocationEvent not found");
            }

            if (Objects.nonNull(mapNewsItemEntity.getGeocodingLocation())) {
                throw new MapNewsItemExistException();
            }

            mapNewsTrackedAreas(mapNewsItemEntity, syncNewsLocationEvent);
            mapNewsItemEntity.setAddress(syncNewsLocationEvent.getAddress());
            mapNewsItemEntity.setSyncStatus(NewsSyncStatusEnum.SYNCED_GEOCODING_LOCATION.getValue());
            mapNewsItemRepository.save(mapNewsItemEntity);

            log.info("SAVED News Location: {}", mapNewsItemEntity);
        } catch (Exception e) {
            log.error("Sync News Item {} fail with error : {}", mapNewsItemId, e.getMessage());
            mapNewsItemUpdaterService.updateSyncLocationFailureStatus(mapNewsItemEntity);
            throw e;
        }
    }

    private void validateAndMapRawNewsItem(MapNewsItemEntity rawEntity, Integer providerId) {
        if (mapNewsItemRepository.existsBySourceUrl(rawEntity.getSourceUrl())) {
            throw new MapNewsItemExistException();
        }

        NewsSourceEntity newsSourceEntity = newsSourceRepository.findById(providerId)
                .orElseThrow(RuntimeException::new);
        rawEntity.setNewsSource(newsSourceEntity);
        rawEntity.setProvider(newsSourceEntity.getNewsProvider().getName());
        rawEntity.setCategory(newsSourceEntity.getCategory());
    }

    private void mapNewsTrackedAreas(MapNewsItemEntity mapNewsItemEntity, SyncNewsLocationEvent syncNewsLocationEvent) {
        GeocodingLocationEvent locationEvent = syncNewsLocationEvent.getGeocodingLocation();
        Set<Long> trackedAreaIds = syncNewsLocationEvent.getTrackedAreaIds();
        GeocodingLocationEntity geocodingLocation =
                geocodingLocationRepository.findByOsmTypeAndOsmId(locationEvent.getOsmType(), locationEvent.getOsmId())
                        .orElseGet(() -> {
                            GeocodingLocationEntity newLocation =
                                    geocodingLocationService.constructEntityFromEvent(locationEvent);
                            return geocodingLocationRepository.save(newLocation);
                        });

        Set<NewsTrackedAreaEntity> trackedAreas =
                newsTrackedAreaRepository.findAllByNewsSourceId(mapNewsItemEntity.getNewsSource().getNewsSourceId());

        Set<NewsTrackedAreaEntity> matchedTrackedAreas = trackedAreas.stream()
                .filter(area -> trackedAreaIds.contains(area.getNewsTrackedAreaId()))
                .collect(Collectors.toSet());

        if (matchedTrackedAreas.isEmpty()) {
            throw new ResourceNotFoundException("Tracked Areas not found for location");
        }

        mapNewsItemEntity.getTrackedAreas().addAll(matchedTrackedAreas);
        mapNewsItemEntity.setGeocodingLocation(geocodingLocation);
    }
}
