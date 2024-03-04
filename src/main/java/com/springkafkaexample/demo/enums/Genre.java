package com.springkafkaexample.demo.enums;

public enum Genre {
    FANTASY("fantasy"),
    STORY("story"),
    POEM("poem"),
    DOCUMENTARY("documentary");

    private String title;

    Genre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Genre { " +
                "title = '" + title + '\'' +
                '}';
    }
}
