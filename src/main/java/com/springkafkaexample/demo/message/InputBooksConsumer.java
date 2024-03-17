package com.springkafkaexample.demo.message;

import com.springkafkaexample.demo.exception.MessageValidationException;
import com.springkafkaexample.demo.model.InputBooks;
import com.springkafkaexample.demo.service.ConvertBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.springkafkaexample.demo.constants.MessageConstants.BOOKS_LISTENER;

@Slf4j(topic = "Consumer-MQ")
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true")
public class InputBooksConsumer {

    private final ConvertBookService convertBookService;

    @KafkaListener(
            id = BOOKS_LISTENER,
            topics = {
                    "${spring.kafka.books-consumer.topic}"
            },
            containerFactory = "booksListenerFactory"
    )
    protected void readMessages(ConsumerRecord<String, Object> consumerRecord) {
        Object messageValue = consumerRecord.value();

        try {
            if (messageValue instanceof InputBooks inputBooks) {
                convertBookService.processMessage(inputBooks);
            } else {
                throw new MessageValidationException("Unknown message type: " + messageValue);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
