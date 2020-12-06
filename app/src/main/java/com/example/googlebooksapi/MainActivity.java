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
import android.widget.Toast;

import com.example.googlebooksapi.adapters.BookListAdapter;
import com.example.googlebooksapi.models.Book;
import com.example.googlebooksapi.models.JsonFeed;
import com.example.googlebooksapi.server.IWebService;
import com.example.googlebooksapi.server.ServiceGenerator;
import com.example.googlebooksapi.utils.Constants;
import com.example.googlebooksapi.utils.HelperClass;
import com.example.googlebooksapi.utils.SharedPreferencesHelper;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements APIResponseListener{

    ArrayList<Book> books;
    private Activity thisActivity;
    ListView listView;
    Button fetchData;
    private BookListAdapter bookListAdapter;
    private APIResponseListener apiResponseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing activity member variables and views
        apiResponseListener = this;
        thisActivity = this;
        listView = findViewById(R.id.book_title_list_view);
        fetchData = findViewById(R.id.fetch_books_data);
        books = new ArrayList<>();

        // Checking Internet Connection and showing results accordingly
        if (SharedPreferencesHelper.getBookListData("bookList", "") == null
                && !HelperClass.isConnectedToInternet(thisActivity)){
            Toast.makeText(thisActivity, getString(R.string.internet_not_available), Toast.LENGTH_LONG).show();
            disableFetchDataButton();
        } else if (SharedPreferencesHelper.getBookListData("bookList", "") != null){
            books = SharedPreferencesHelper.getBookListData("bookList", "");
            makeButtonInvisibleListVisible();
        }

        // Initialize bookListAdapter and set it to ListView
        bookListAdapter = new BookListAdapter(this, books);
        listView.setAdapter(bookListAdapter);

        // Attach & handle onClickListeners events
        fetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableFetchDataButton();
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

    // Get BookVolumes and show it in ListView
    private void getBookVolume(String bookTitle){
        IWebService service = ServiceGenerator.getService(IWebService.class);
        final Call<JsonFeed> bookVolume = service.getBookVolume(bookTitle, Constants.MAX_RESULTS);
        bookVolume.enqueue(new Callback<JsonFeed>() {
            @Override
            public void onResponse(Call<JsonFeed> call, Response<JsonFeed> response) {
                if (response.isSuccessful()) {
                    makeButtonInvisibleListVisible();
                    JsonFeed jsonData = response.body();
                    apiResponseListener.onAPISuccessfulResponse(jsonData);
                    }
                }

            @Override
            public void onFailure(Call<JsonFeed> call, Throwable t) {
                apiResponseListener.onAPIFailureResponse(t.getMessage());
            }
        });
    }

    private void makeButtonInvisibleListVisible(){
        fetchData.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    private void disableFetchDataButton(){
        fetchData.setClickable(false);
        fetchData.setBackgroundColor(getResources().getColor(R.color.disabled_bg_color));
    }

    private void safeBookListInSharedPreferences(){
        SharedPreferencesHelper.putBookListData("bookList",books);
    }

    private void addItemsInBookList(JsonFeed jsonFeed){
        for (LinkedTreeMap map: jsonFeed.getItems()){
            books.add(new Book(map));
        }
    }

    private void updateBookList(){
        bookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAPISuccessfulResponse(JsonFeed jsonFeed) {
        addItemsInBookList(jsonFeed);
        safeBookListInSharedPreferences();
        updateBookList();
    }

    @Override
    public void onAPIFailureResponse(String message) {
        Toast.makeText(thisActivity, message, Toast.LENGTH_SHORT).show();
    }
}