package com.newsmap.services.impl;

import com.newsmap.entities.MapNewsItemEntity;
import com.newsmap.enums.NewsSyncStatusEnum;
import com.newsmap.repositories.MapNewsItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MapNewsItemUpdaterService {
    private final MapNewsItemRepository mapNewsItemRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSyncLocationFailureStatus(MapNewsItemEntity mapNewsItemEntity) {
        mapNewsItemEntity.setSyncStatus(NewsSyncStatusEnum.SYNC_GEOCODING_LOCATION_FAILED.getValue());
        mapNewsItemRepository.save(mapNewsItemEntity);
    }
}
