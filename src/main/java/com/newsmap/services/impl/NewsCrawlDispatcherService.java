package com.newsmap.services.impl;

import com.newsmap.entities.NewsProviderEntity;
import com.newsmap.entities.NewsSourceEntity;
import com.newsmap.events.NewsCrawlRequestEvent;
import com.newsmap.producers.RequestNewsCrawlProducer;
import com.newsmap.services.NewsSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCrawlDispatcherService {
    private final NewsSourceService newsSourceService;
    private final RequestNewsCrawlProducer requestNewsCrawlProducer;

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

            requestNewsCrawlProducer.publish(requestEvent);
            log.info("Produce News Source: {}", requestEvent);
        }
    }
}
