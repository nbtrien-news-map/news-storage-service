package com.newsmap.producers;

import com.newsmap.events.NewsCrawlRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestNewsCrawlProducer {
    private final KafkaTemplate<String, NewsCrawlRequestEvent> requestNewsCrawlKafkaTemplate;

    @Value(value = "${kafka.request.news.crawl.topic.name}")
    private String requestNewsCrawlTopicName;

    public void publish(NewsCrawlRequestEvent requestEvent) {
        requestNewsCrawlKafkaTemplate.send(requestNewsCrawlTopicName, requestEvent);
    }
}
