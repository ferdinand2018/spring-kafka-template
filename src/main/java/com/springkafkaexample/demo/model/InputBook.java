package com.springkafkaexample.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springkafkaexample.demo.enums.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.Valid;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
public class InputBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private long id;

    @JsonProperty("author")
    private String author;

    @JsonProperty("bookName")
    private String bookName;

    @JsonProperty("genre")
    private Genre genre;

    @JsonProperty("created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Valid
    @Builder.Default
    private LocalDateTime created = null;

    public InputBook(long id, String author, String bookName, Genre genre, LocalDateTime created) {
        this.id = id;
        this.author = author;
        this.bookName = bookName;
        this.genre = genre;
        this.created = created;
    }

    public InputBook() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputBook inputBook = (InputBook) o;
        return id == inputBook.id && Objects.equals(author, inputBook.author) && Objects.equals(bookName, inputBook.bookName) && genre == inputBook.genre && Objects.equals(created, inputBook.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, bookName, genre, created);
    }

    @Override
    public String toString() {
        return "InputBook{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", bookName='" + bookName + '\'' +
                ", genre=" + genre +
                ", created=" + created +
                '}';
    }
}
