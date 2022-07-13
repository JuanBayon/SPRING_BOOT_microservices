package com.showcatalog.kafka;

import com.showcatalog.entities.Show;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, Show> showProducerFactory() {
        Map<String, Object> configProps = getConfigPropsMap();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Show> showKafkaTemplate() {
        return new KafkaTemplate<>(showProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Exception> errorProducerFactory() {
        Map<String, Object> configProps = getConfigPropsMap();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    private Map<String, Object> getConfigPropsMap() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return configProps;
    }

    @Bean
    public KafkaTemplate<String, Exception> errorKafkaTemplate() {
        return new KafkaTemplate<>(errorProducerFactory());
    }

}
