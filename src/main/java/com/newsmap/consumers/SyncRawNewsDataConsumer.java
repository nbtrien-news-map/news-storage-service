package com.newsmap.consumers;

import com.newsmap.entities.MapNewsItemEntity;
import com.newsmap.events.ExtractNewsLocationEvent;
import com.newsmap.events.SyncRawNewsDataEvent;
import com.newsmap.exceptions.MapNewsItemExistException;
import com.newsmap.producers.ExtractNewsLocationProducer;
import com.newsmap.services.MapNewsItemService;
import com.newsmap.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SyncRawNewsDataConsumer {
    private final ExtractNewsLocationProducer extractNewsLocationProducer;
    private final MapNewsItemService mapNewsItemService;

    @KafkaListener(topics = "${kafka.sync.raw.news.data.topic.name}", containerFactory =
            "syncRawNewsDataKafkaListenerContainerFactory")
    public void consume(SyncRawNewsDataEvent event) {
        try {
            MapNewsItemEntity rawNewsItem = new MapNewsItemEntity();
            rawNewsItem.setTitle(event.getTitle());
            rawNewsItem.setDescription(event.getDescription());
            rawNewsItem.setSourceUrl(event.getLink());
            rawNewsItem.setPublishedAt(DateTimeUtils.toLocalDateTime(event.getPublishedAt(),
                                                                     DateTimeUtils.FULL_DATE_TIME_PATTERN_YYYYMMDD));
            MapNewsItemEntity newsItemEntity = mapNewsItemService.saveRawNewsItem(rawNewsItem, event.getProviderId());

            ExtractNewsLocationEvent extractNewsLocationEvent = new ExtractNewsLocationEvent();
            extractNewsLocationEvent.setMapNewsItemId(newsItemEntity.getMapNewsItemId());
            extractNewsLocationEvent.setTitle(newsItemEntity.getTitle());
            extractNewsLocationEvent.setDescription(newsItemEntity.getDescription());
            extractNewsLocationEvent.setTrackedAreas(event.getTrackedAreas());

            extractNewsLocationProducer.publish(extractNewsLocationEvent);
            log.info("extractNewsLocation publish: {}", extractNewsLocationEvent);
        } catch (MapNewsItemExistException e) {
            log.error("News Item Exist, Skipping consume: {}", event.getLink());
        } catch (RuntimeException e) {
            log.error("Error SyncRawNewsDataEvent message", e);
        }
    }
}
