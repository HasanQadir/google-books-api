package com.example.googlebooksapi.models;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class JsonFeed  {
    private Integer totalItems;
    private ArrayList<LinkedTreeMap> items;

    public JsonFeed(Integer totalItems, ArrayList<LinkedTreeMap> items) {
        this.totalItems = totalItems;
        this.items = items;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public ArrayList<LinkedTreeMap> getItems() {
        return items;
    }

    public void setItems(ArrayList<LinkedTreeMap> items) {
        this.items = items;
    }

}
