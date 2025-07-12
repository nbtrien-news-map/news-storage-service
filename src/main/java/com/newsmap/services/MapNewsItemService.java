package com.newsmap.services;

import com.newsmap.entities.MapNewsItemEntity;
import com.newsmap.events.SyncNewsLocationEvent;

public interface MapNewsItemService {
    MapNewsItemEntity saveRawNewsItem(MapNewsItemEntity rawEntity, Integer providerId);
    void saveNewsLocation(SyncNewsLocationEvent syncNewsLocationEvent);
}
