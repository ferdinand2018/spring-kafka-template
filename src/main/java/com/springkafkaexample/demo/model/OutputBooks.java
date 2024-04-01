package com.springkafkaexample.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springkafkaexample.demo.model.output.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
public class OutputBooks implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("fantasy")
    private List<Fantasy> fantasy;

    @JsonProperty("story")
    private List<Story> story;

    @JsonProperty("poem")
    private List<Poem> poem;

    @JsonProperty("documentary")
    private List<Documentary> documentary;

    @JsonProperty("undefinedBook")
    private List<UndefinedBook> undefinedBook;

    public OutputBooks() {
        super();
    }

    public OutputBooks(List<Fantasy> fantasy, List<Story> story, List<Poem> poem, List<Documentary> documentary, List<UndefinedBook> undefinedBook) {
        this.fantasy = fantasy;
        this.story = story;
        this.poem = poem;
        this.documentary = documentary;
        this.undefinedBook = undefinedBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutputBooks that = (OutputBooks) o;
        return Objects.equals(fantasy, that.fantasy) && Objects.equals(story, that.story) && Objects.equals(poem, that.poem) && Objects.equals(documentary, that.documentary) && Objects.equals(undefinedBook, that.undefinedBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fantasy, story, poem, documentary, undefinedBook);
    }

    @Override
    public String toString() {
        return "OutputBooks{" +
                "fantasy=" + fantasy +
                ", story=" + story +
                ", poem=" + poem +
                ", documentary=" + documentary +
                ", undefinedBook=" + undefinedBook +
                '}';
    }
}
