package com.springkafkaexample.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import javax.validation.Valid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
public class InputBooks implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("inputBooks")
    @Valid
    private List<InputBook> inputBooks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputBooks that = (InputBooks) o;
        return Objects.equals(inputBooks, that.inputBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputBooks);
    }

    public InputBooks() {
        super();
    }

    public InputBooks(List<InputBook> inputBooks) {
        this.inputBooks = inputBooks;
    }

    @Override
    public String toString() {
        return "InputBooks{" +
                "inputBooks=" + inputBooks +
                '}';
    }
}
