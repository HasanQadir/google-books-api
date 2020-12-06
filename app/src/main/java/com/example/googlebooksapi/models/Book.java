package com.example.googlebooksapi.models;

import com.google.gson.internal.LinkedTreeMap;

public class Book {
    private String title;
    private String description;

    public Book(LinkedTreeMap map){
        map = (LinkedTreeMap) map.get("volumeInfo");
        this.title = (String) map.get("title");
        this.description = (String) map.get("description");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
