package com.kafka.Producer.Producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093"
        ));
    }

    @Bean
    public NewTopic createTopic() {
        return new NewTopic("add_users", 2, (short) 2) // 2 partitions, replication factor 2
                .configs(Map.of(TopicConfig.RETENTION_MS_CONFIG, "604800000")); // 7 days retention
    }
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                ProducerConfig.ACKS_CONFIG, "all", // 'all' ensures all replicas acknowledge
                ProducerConfig.RETRIES_CONFIG, 10, // retry 10 times before failing
                ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000, // retry backoff time 1 second
                ProducerConfig.LINGER_MS_CONFIG, 5, // batching delay
                ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432 // buffer memory
        ));
    }


    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
