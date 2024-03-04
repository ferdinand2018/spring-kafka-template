package com.springkafkaexample.demo.model.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Common {
    private long id;
    private String author;
    private String bookName;
    private LocalDateTime created;
}
