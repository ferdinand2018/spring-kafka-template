package com.springkafkaexample.demo.model;

import com.springkafkaexample.demo.enums.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class InputBooks {
    private long id;
    private String author;
    private String bookName;
    private Genre genre;
    private LocalDateTime created;
}
