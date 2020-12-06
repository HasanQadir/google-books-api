package com.example.googlebooksapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.googlebooksapi.models.Book;

public class BookDescription extends AppCompatActivity {

    TextView bookDescription;
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        bookDescription = findViewById(R.id.book_desciption);
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        bookDescription.setText(book.getDescription());

    }
}