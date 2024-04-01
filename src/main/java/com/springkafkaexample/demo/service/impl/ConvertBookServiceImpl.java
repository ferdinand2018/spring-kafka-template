package com.springkafkaexample.demo.service.impl;

import com.springkafkaexample.demo.enums.Genre;
import com.springkafkaexample.demo.exception.MessageValidationException;
import com.springkafkaexample.demo.message.OutputBooksProducer;
import com.springkafkaexample.demo.model.InputBook;
import com.springkafkaexample.demo.model.InputBooks;
import com.springkafkaexample.demo.model.OutputBooks;
import com.springkafkaexample.demo.model.output.*;
import com.springkafkaexample.demo.service.ConvertBookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true")
public class ConvertBookServiceImpl implements ConvertBookService {

    /*@Autowired
    private final Validator validator;*/

    protected final OutputBooksProducer outputBooksProducer;

    @Override
    @SneakyThrows
    public void processMessage(InputBooks inputBooks) {
        log.info("Input msg:" + inputBooks);
        //validateMessage(inputBooks);

        OutputBooks outputMessage = buildOutputBooks(inputBooks);

        outputBooksProducer.sendMessage(outputMessage, buildHeader());
    }

    /*private <T> void validateMessage(T message) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(message);
        if (!constraintViolations.isEmpty()) {
            throw new MessageValidationException(constraintViolations);
        }
    }*/

    public static OutputBooks buildOutputBooks(InputBooks inputBooks) {
        OutputBooks outputBooks = new OutputBooks();
        List<Fantasy> fantasy = new ArrayList<>();
        List<Story> story = new ArrayList<>();
        List<Poem> poem = new ArrayList<>();
        List<Documentary> documentary = new ArrayList<>();
        List<UndefinedBook> undefinedBook = new ArrayList<>();

        inputBooks.getInputBooks().forEach(inputBook -> {
            if (inputBook.getGenre() == Genre.FANTASY) {
                fantasy.add(buildFantasy(inputBook));
                outputBooks.setFantasy(fantasy);
                log.info("FANTASY CREATED");
            } else if (inputBook.getGenre() == Genre.STORY) {
                story.add(buildStory(inputBook));
                outputBooks.setStory(story);
                log.info("STORY CREATED");
            } else if (inputBook.getGenre() == Genre.POEM) {
                poem.add(buildPoem(inputBook));
                outputBooks.setPoem(poem);
                log.info("POEM CREATED");
            } else if (inputBook.getGenre() == Genre.DOCUMENTARY) {
                documentary.add(buildDocumentary(inputBook));
                outputBooks.setDocumentary(documentary);
                log.info("DOCUMENTARY CREATED");
            }
        });

        return outputBooks;
    }

    public static Fantasy buildFantasy(InputBook inputBook) {
        Fantasy fantasy = new Fantasy();

        fantasy.setId(inputBook.getId());
        fantasy.setAuthor(inputBook.getAuthor());
        fantasy.setBookName(inputBook.getBookName());
        fantasy.setCreated(inputBook.getCreated());

        return fantasy;
    }

    public static Story buildStory(InputBook inputBook) {
        Story story = new Story();

        story.setId(inputBook.getId());
        story.setAuthor(inputBook.getAuthor());
        story.setBookName(inputBook.getBookName());
        story.setCreated(inputBook.getCreated());

        return story;
    }

    public static Poem buildPoem(InputBook inputBook) {
        Poem poem = new Poem();

        poem.setId(inputBook.getId());
        poem.setAuthor(inputBook.getAuthor());
        poem.setBookName(inputBook.getBookName());
        poem.setCreated(inputBook.getCreated());

        return poem;
    }

    public static Documentary buildDocumentary(InputBook inputBook) {
        Documentary documentary = new Documentary();

        documentary.setId(inputBook.getId());
        documentary.setAuthor(inputBook.getAuthor());
        documentary.setBookName(inputBook.getBookName());
        documentary.setCreated(inputBook.getCreated());

        return documentary;
    }

    public static Header buildHeader() {
        return new RecordHeader(
                "sendingTime",
                LocalDateTime.now().toString().getBytes(StandardCharsets.UTF_8)
        );
    }
}
