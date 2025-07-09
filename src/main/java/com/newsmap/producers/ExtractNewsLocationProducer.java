package com.newsmap.producers;

import com.newsmap.events.ExtractNewsLocationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractNewsLocationProducer {
    private final KafkaTemplate<String, ExtractNewsLocationEvent> extractNewsLocationKafkaTemplate;

    @Value(value = "${kafka.extract.news.location.topic.name}")
    private String extractNewsLocationTopicName;

    public void publish(ExtractNewsLocationEvent event) {
        extractNewsLocationKafkaTemplate.send(extractNewsLocationTopicName, event);
    }
}
