package com.springkafkaexample.demo.message;

import com.springkafkaexample.demo.config.properties.KafkaProperties;
import com.springkafkaexample.demo.model.OutputBooks;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Producer-MQ")
@Component
public class OutputBooksProducer extends QueueProducer<String, Object> {

    private final KafkaProperties.ProducerDetails outputBooksProducerDetails;

    public OutputBooksProducer(KafkaTemplate<String, Object> booksKafkaTemplate,
                               KafkaProperties kafkaProperties) {
        super(booksKafkaTemplate);
        this.outputBooksProducerDetails = kafkaProperties.getBooksProducer();
    }

    /**
     * Send message into topic
     *
     * @param outputBooks message dto
     * @param header header
     */
    public void sendMessage(OutputBooks outputBooks, Header header) {
        String topic = outputBooksProducerDetails.getTopic();

        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, outputBooks);
        producerRecord.headers().add(header);

        sendMessage(producerRecord);
    }
}
