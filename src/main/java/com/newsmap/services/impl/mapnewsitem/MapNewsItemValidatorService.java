package com.newsmap.services.impl.mapnewsitem;

import com.newsmap.events.SyncRawNewsDataEvent;
import com.newsmap.repositories.MapNewsItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapNewsItemValidatorService {
    private final MapNewsItemRepository mapNewsItemRepository;

    public void validateRawNewsItem(SyncRawNewsDataEvent rawNewsDataEvent) {
        if (mapNewsItemRepository.existsBySourceUrl(rawNewsDataEvent.getLink())) {
            throw new RuntimeException();
        }
    }
}
