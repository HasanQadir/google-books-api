package com.example.googlebooksapi.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.internal.LinkedTreeMap;


public class Book implements Parcelable {
    private String title;
    private String description;

    public Book(LinkedTreeMap map){
        map = (LinkedTreeMap) map.get("volumeInfo");
        this.title = (String) map.get("title");
        this.description = (String) map.get("description");
    }

    protected Book(Parcel in) {
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
    }
}
