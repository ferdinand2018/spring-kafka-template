package com.springkafkaexample.demo.model.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Story extends Common {
    public LocalDateTime localDateTime;
}
