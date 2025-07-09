package com.newsmap.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaTopicConfig {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.request.news.crawl.topic.name}")
    private String requestNewsCrawlTopicName;

    @Value(value = "${kafka.extract.news.location.topic.name}")
    private String extractNewsLocationTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic requestNewsCrawlTopic() {
        return new NewTopic(requestNewsCrawlTopicName, 1, (short) 1);
    }

    @Bean
    public NewTopic extractNewsLocationTopicName() {
        return new NewTopic(extractNewsLocationTopicName, 1, (short) 1);
    }
}
