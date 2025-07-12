package com.newsmap.consumers;

import com.newsmap.events.SyncNewsLocationEvent;
import com.newsmap.exceptions.MapNewsItemExistException;
import com.newsmap.exceptions.ResourceNotFoundException;
import com.newsmap.services.MapNewsItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SyncNewsLocationConsumer {
    private final MapNewsItemService mapNewsItemService;

    @KafkaListener(topics = "${kafka.sync.news.location.topic.name}", containerFactory =
            "syncNewsLocationKafkaListenerContainerFactory")
    public void consume(SyncNewsLocationEvent event) {
        try {
            mapNewsItemService.saveNewsLocation(event);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
        } catch (MapNewsItemExistException e) {
            log.error("News Item Exist, Skipping consume");
        } catch (RuntimeException e) {
            log.error("Error SyncNewsLocationEvent", e);
        }
    }
}
