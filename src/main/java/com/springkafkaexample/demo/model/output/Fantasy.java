package com.springkafkaexample.demo.model.output;

import com.springkafkaexample.demo.enums.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Fantasy extends Common {
    public LocalDateTime localDateTime;
}
