package com.example.googlebooksapi.models;

public class JsonFeed  {
    public String name;
    public Object bookData;

    public String getName() {
        return name;
    }

    public JsonFeed(String name, Object bookData) {
        this.name = name;
        this.bookData = bookData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBookData() {
        return bookData;
    }

    public void setBookData(Object bookData) {
        this.bookData = bookData;
    }
}
