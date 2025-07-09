package com.newsmap.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsmap.entities.MapNewsItemEntity;
import com.newsmap.entities.NewsSourceEntity;
import com.newsmap.exceptions.MapNewsItemExistException;
import com.newsmap.repositories.MapNewsItemRepository;
import com.newsmap.repositories.NewsSourceRepository;
import com.newsmap.services.MapNewsItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapNewsItemServiceImpl implements MapNewsItemService {
    private final MapNewsItemRepository mapNewsItemRepository;
    private final NewsSourceRepository newsSourceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MapNewsItemEntity saveRawNewsItem(MapNewsItemEntity rawEntity, Integer providerId) {
        validateAndMapRawNewsItem(rawEntity, providerId);
        return mapNewsItemRepository.save(rawEntity);
    }

    private void validateAndMapRawNewsItem(MapNewsItemEntity rawEntity, Integer providerId) {
        if (mapNewsItemRepository.existsBySourceUrl(rawEntity.getSourceUrl())) {
            throw new MapNewsItemExistException();
        }

        NewsSourceEntity newsSourceEntity = newsSourceRepository.findById(providerId)
                .orElseThrow(RuntimeException::new);
        rawEntity.setNewsSource(newsSourceEntity);
        rawEntity.setProvider(newsSourceEntity.getNewsProvider().getName());
//        try {
//            rawEntity.setMetadata(objectMapper.writeValueAsString(new SyncRawNewsDataEvent()));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }

//    private void mapTrackedArea(R)
}
