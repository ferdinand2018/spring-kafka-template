package com.springkafkaexample.demo.service.impl;

import com.springkafkaexample.demo.exception.MessageValidationException;
import com.springkafkaexample.demo.model.InputBooks;
import com.springkafkaexample.demo.service.ConvertBookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true")
public class ConvertBookServiceImpl implements ConvertBookService {

    /*@Autowired
    private final Validator validator;*/

    @Override
    @SneakyThrows
    public void processMessage(InputBooks inputBooks) {
        log.info("Input msg:" + inputBooks);
        //validateMessage(inputBooks);
    }

    /*private <T> void validateMessage(T message) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(message);
        if (!constraintViolations.isEmpty()) {
            throw new MessageValidationException(constraintViolations);
        }
    }*/
}
