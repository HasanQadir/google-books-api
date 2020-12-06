package com.example.googlebooksapi;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.googlebooksapi.adapters.BookListAdapter;
import com.example.googlebooksapi.models.Book;
import com.example.googlebooksapi.models.JsonFeed;
import com.example.googlebooksapi.server.IWebService;
import com.example.googlebooksapi.server.ServiceGenerator;
import com.example.googlebooksapi.utils.Constants;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ArrayList<Book> books;
    private Activity thisActivity;
    ListView listView;
    Button fetchData;
    private BookListAdapter bookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisActivity = this;
        listView = findViewById(R.id.book_title_list_view);
        fetchData = findViewById(R.id.fetch_books_data);
        books = new ArrayList<>();
        bookListAdapter = new BookListAdapter(this, books);
        listView.setAdapter(bookListAdapter);

        fetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData.setClickable(false);
                fetchData.setBackgroundColor(getResources().getColor(R.color.disabled_bg_color));
                getBookVolume(getString(R.string.harry_potter));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = (Book) listView.getItemAtPosition(position);
                Intent intent = new Intent(thisActivity, BookDescription.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });
    }

    private void getBookVolume(String bookTitle){
        IWebService service = ServiceGenerator.getService(IWebService.class);
        final Call<JsonFeed> bookVolume = service.getBookVolume(bookTitle, Constants.MAX_RESULTS);
        bookVolume.enqueue(new Callback<JsonFeed>() {
            @Override
            public void onResponse(Call<JsonFeed> call, Response<JsonFeed> response) {
                if (response.isSuccessful()) {
                    fetchData.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    JsonFeed jsonData = response.body();
                    for (LinkedTreeMap map: jsonData.getItems()){
                        books.add(new Book(map));
                    }
                    bookListAdapter.notifyDataSetChanged();
                    }
                }

            @Override
            public void onFailure(Call<JsonFeed> call, Throwable t) {
            }
        });
    }
}