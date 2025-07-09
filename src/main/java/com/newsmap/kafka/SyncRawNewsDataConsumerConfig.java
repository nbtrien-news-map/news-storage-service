package com.newsmap.kafka;

import com.newsmap.events.SyncRawNewsDataEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SyncRawNewsDataConsumerConfig {
    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value("${kafka.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, SyncRawNewsDataEvent> syncRawNewsDataConsumerFactory() {
        JsonDeserializer<SyncRawNewsDataEvent> deserializer = new JsonDeserializer<>(SyncRawNewsDataEvent.class);
        deserializer.addTrustedPackages("*");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SyncRawNewsDataEvent> syncRawNewsDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SyncRawNewsDataEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(syncRawNewsDataConsumerFactory());
        return factory;
    }
}
