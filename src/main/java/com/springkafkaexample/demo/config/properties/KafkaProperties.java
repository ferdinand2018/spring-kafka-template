package com.springkafkaexample.demo.config.properties;

import io.confluent.parallelconsumer.ParallelConsumerOptions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    private ConsumerDetails booksConsumer;
    private ProducerDetails booksProducer;

    @Getter
    @Setter
    public static class ConsumerDetails extends CommonDetails {
        private String group;
        private String sessionTimeoutMs;
        private String heartbeatIntervalMs;
        private String maxPollRecords;
        private String maxPollIntervalMs;
        private String offsetReset;
        private ParallelProps parallelProps;
        private boolean autoCommit = false;
    }

    @Getter
    @Setter
    public static class ParallelProps {
        private Integer maxConcurrency;
        private ParallelConsumerOptions.ProcessingOrder processingOrder;
        private ParallelConsumerOptions.CommitMode commitMode;
    }

    @Getter
    @Setter
    public static class ProducerDetails extends CommonDetails {
        private String acks;
        private String deliveryTimeoutMs;
        private String batchSize;
        private String lingerMs;
        private String maxInFlightRequestsPerConnection;
        private String enableIdempotence;
    }

    @Getter
    @Setter
    public static class CommonDetails {
        private String bootstrap;
        private String clientId;
        private String topic;
        private String securityProtocol;
        /*private String saslMechanism;
        private String saslJaasUsername;
        private String saslJaasPassword;*/
    }
}
