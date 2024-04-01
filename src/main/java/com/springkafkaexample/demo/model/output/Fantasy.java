package com.springkafkaexample.demo.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Fantasy implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private long id;

    @JsonProperty("author")
    private String author;

    @JsonProperty("bookName")
    private String bookName;

    @JsonProperty("created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Valid
    @Builder.Default
    private LocalDateTime created = null;

    public Fantasy() {
        super();
    }

    public Fantasy(long id, String author, String bookName, LocalDateTime created) {
        this.id = id;
        this.author = author;
        this.bookName = bookName;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fantasy fantasy = (Fantasy) o;
        return id == fantasy.id && Objects.equals(author, fantasy.author) && Objects.equals(bookName, fantasy.bookName) && Objects.equals(created, fantasy.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, bookName, created);
    }

    @Override
    public String toString() {
        return "Fantasy{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", bookName='" + bookName + '\'' +
                ", created=" + created +
                '}';
    }
}
