package com.springkafkaexample.demo.message;

import com.springkafkaexample.demo.model.InputBooks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import static com.springkafkaexample.demo.utils.ObjectMapperSingleton.OBJECT_MAPPER_FAIL_FALSE;

@Slf4j
@RequiredArgsConstructor
public class InputBooksDeserializer implements Deserializer<Object> {

    @Override
    public Object deserialize(String topic, byte[] data) {
        Object inputBooks = null;
        try {
            inputBooks = OBJECT_MAPPER_FAIL_FALSE.readValue(
                    data,
                    InputBooks.class
            );
        } catch (Exception e) {
            log.error("Error during deserializing message. Cause: {}", e.getMessage());
        }
        return inputBooks;
    }
}
