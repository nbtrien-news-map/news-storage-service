package com.newsmap.services.impl;

import com.newsmap.entities.NewsProviderEntity;
import com.newsmap.entities.NewsSourceEntity;
import com.newsmap.entities.NewsTrackedAreaEntity;
import com.newsmap.events.NewsCrawlRequestEvent;
import com.newsmap.events.TrackedAreaEvent;
import com.newsmap.producers.RequestNewsCrawlProducer;
import com.newsmap.repositories.NewsTrackedAreaRepository;
import com.newsmap.services.NewsSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCrawlDispatcherService {
    private final NewsSourceService newsSourceService;
    private final RequestNewsCrawlProducer requestNewsCrawlProducer;
    private final NewsTrackedAreaRepository newsTrackedAreaRepository;

    public void executeService() {
        List<NewsSourceEntity> newsSources = newsSourceService.getAllActive();
        for (NewsSourceEntity newsSource : newsSources) {
            NewsProviderEntity newsProvider = newsSource.getNewsProvider();

            NewsCrawlRequestEvent requestEvent = new NewsCrawlRequestEvent();
            requestEvent.setNewsSourceId(newsSource.getNewsSourceId());
            requestEvent.setSourceUrl(newsSource.getSourceUrl());
            requestEvent.setProviderCode(newsProvider.getCode());
            requestEvent.setProviderName(newsProvider.getName());
            requestEvent.setSourceType(newsProvider.getSourceType());

            Set<NewsTrackedAreaEntity> trackedAreaEntities =
                    newsTrackedAreaRepository.findAllByNewsSourceId(newsSource.getNewsSourceId());

            Set<TrackedAreaEvent> trackedAreaEvents = trackedAreaEntities.stream()
                    .map(entity -> {
                        return new TrackedAreaEvent(
                                entity.getNewsTrackedAreaId(),
                                entity.getOsmType(),
                                entity.getOsmId()
                        );
                    })
                    .collect(Collectors.toSet());

            requestEvent.setTrackedAreas(trackedAreaEvents);
            requestNewsCrawlProducer.publish(requestEvent);
            log.info("Produce News Source: {}", requestEvent);
        }
    }
}
