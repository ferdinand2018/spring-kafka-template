package com.springkafkaexample.demo.service;

import com.springkafkaexample.demo.model.InputBooks;

public interface ConvertBookService {

    /**
     * Process message
     *
     * @param inputBooks message
     */
    void processMessage(InputBooks inputBooks);
}
