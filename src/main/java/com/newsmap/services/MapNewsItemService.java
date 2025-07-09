package com.newsmap.services;

import com.newsmap.entities.MapNewsItemEntity;

public interface MapNewsItemService {
    MapNewsItemEntity saveRawNewsItem(MapNewsItemEntity rawEntity, Integer providerId);
}
