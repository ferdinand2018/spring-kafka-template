package com.springkafkaexample.demo.model;

import com.springkafkaexample.demo.model.output.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OutputBooks {
    private Fantasy fantasy;
    private Story story;
    private Poem poem;
    private Documentary documentary;
    private UndefinedBook undefinedBook;
}
