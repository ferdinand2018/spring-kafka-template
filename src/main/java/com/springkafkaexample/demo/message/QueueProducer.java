package com.springkafkaexample.demo.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class QueueProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    protected void sendMessage(ProducerRecord<K, V> producerRecord) {

        String topic = producerRecord.topic();

        log.info(
                "Sending a message to topic: {}, message: {}",
                topic,
                producerRecord
        );
        try {
            kafkaTemplate.send(producerRecord);
            log.info(
                    "Message has been sent successfully to topic: {}, producerRecord: {}",
                    topic,
                    producerRecord
            );
        } catch (Exception ex) {
            log.error(
                    "An error happen while sending a message to topic: {}, producerRecord: {}",
                    topic,
                    producerRecord,
                    ex
            );
        }
    }
}
