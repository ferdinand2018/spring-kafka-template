package com.springkafkaexample.demo.config;

import com.springkafkaexample.demo.config.properties.KafkaProperties;
import com.springkafkaexample.demo.message.InputBooksDeserializer;
import io.confluent.parallelconsumer.ParallelConsumerOptions;
import io.confluent.parallelconsumer.ParallelStreamProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.springkafkaexample.demo.constants.MessageConstants.BOOKS_LISTENER;
import static com.springkafkaexample.demo.utils.ObjectMapperSingleton.OBJECT_MAPPER_FAIL_FALSE;

@Slf4j(topic = "MQ-Kafka")
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    @Value("${spring.kafka.books-consumer.topic}")
    private String inputTopic;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> booksListenerFactory(
            DefaultKafkaConsumerFactory<String, Object> booksConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(booksConsumerFactory);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setCommonErrorHandler(createCommonErrorHandler());
        factory.setAutoStartup(Boolean.FALSE);
        return factory;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(
                buildProducerFactoryConfig(
                        kafkaProperties.getBooksProducer()
                ),
                new StringSerializer(),
                new JsonSerializer<>(OBJECT_MAPPER_FAIL_FALSE)
        );
    }

    @Bean
    public KafkaTemplate<String, Object> booksKafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public DefaultKafkaConsumerFactory<String, Object> booksConsumerFactory(
            DefaultKafkaConsumerFactoryCustomizer metricCustomizer) {

        var factory = new DefaultKafkaConsumerFactory<>(
                buildConsumerFactoryConfig(
                        kafkaProperties.getBooksConsumer()
                ),
                new StringDeserializer(),
                new InputBooksDeserializer()
        );
        metricCustomizer.customize(factory);
        return factory;
    }

    @Bean
    @ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true")
    public ApplicationRunner runner(KafkaListenerEndpointRegistry registry,
                                    ConsumerFactory<String, Object> booksRawConsumerFactory) {

        return args -> {
            KafkaProperties.ParallelProps parallelProps = kafkaProperties.getBooksConsumer().getParallelProps();
            MessageListener messageListener = (MessageListener)
                    Objects.requireNonNull(registry.getListenerContainer(BOOKS_LISTENER))
                            .getContainerProperties().getMessageListener();
            Consumer<String, Object> consumer = booksRawConsumerFactory.createConsumer();

            var options = ParallelConsumerOptions.<String, Object>builder()
                    .ordering(parallelProps.getProcessingOrder())
                    .consumer(consumer)
                    .commitMode(parallelProps.getCommitMode())
                    .maxConcurrency(parallelProps.getMaxConcurrency())
                    .build();

            ParallelStreamProcessor<String, Object> processor = ParallelStreamProcessor
                    .createEosStreamProcessor(options);

            processor.subscribe(List.of(inputTopic));
            processor.poll(context ->
                    messageListener.onMessage(context.getSingleConsumerRecord(), null, consumer)
            );
        };
    }

    private Map<String, Object> buildConsumerFactoryConfig(KafkaProperties.ConsumerDetails details) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, details.getBootstrap());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, details.isAutoCommit());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, details.getOffsetReset());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, details.getGroup());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, details.getClientId());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, details.getSessionTimeoutMs());
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, details.getHeartbeatIntervalMs());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, details.getMaxPollRecords());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, details.getMaxPollIntervalMs());
        props.put(ConsumerConfig.SECURITY_PROVIDERS_CONFIG, details.getSecurityProtocol());
        return props;
    }

    private Map<String, Object> buildProducerFactoryConfig(KafkaProperties.ProducerDetails details) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, details.getBootstrap());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, details.getClientId());
        fillPerformance(details, props);
        return props;
    }

    private void fillPerformance(KafkaProperties.ProducerDetails details, Map<String, Object> props) {
        props.put(ProducerConfig.ACKS_CONFIG, details.getAcks());
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, details.getDeliveryTimeoutMs());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, details.getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, details.getLingerMs());
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, details.getMaxInFlightRequestsPerConnection());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, details.getEnableIdempotence());
    }

    private DefaultErrorHandler createCommonErrorHandler() {
        return new DefaultErrorHandler(
                (consumerRecord, exception) ->
                        log.error(
                                "Message {} has not been processed, reason: {}",
                                consumerRecord,
                                exception.getMessage()
                        ),
                new FixedBackOff(0L, 2L)
        );
    }
}
