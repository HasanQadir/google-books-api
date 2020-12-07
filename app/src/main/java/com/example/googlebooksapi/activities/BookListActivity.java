package com.example.googlebooksapi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.fragments.BookDescriptionFragment;
import com.example.googlebooksapi.fragments.BookListFragment;
import com.example.googlebooksapi.fragments.FetchJsonFragment;
import com.example.googlebooksapi.interfaces.BookSelectedListener;
import com.example.googlebooksapi.interfaces.FetchDataOnSuccessListener;
import com.example.googlebooksapi.models.Book;
import com.example.googlebooksapi.utils.HelperClass;
import com.example.googlebooksapi.utils.SharedPreferencesHelper;

import java.util.ArrayList;

import static com.example.googlebooksapi.utils.Constants.BOOK_DES_FRAG_TAG;

public class BookListActivity extends AppCompatActivity implements FetchDataOnSuccessListener, BookSelectedListener {

    ArrayList<Book> books;
    BookListFragment bookListFragment;
    FetchJsonFragment fetchJsonFragment;
    BookDescriptionFragment bookDescriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Initializing activity member variables and views
        books = new ArrayList<>();

        // Checking Internet Connection and showing results accordingly
        if (SharedPreferencesHelper.getBookListData("bookList", "") == null
                && !HelperClass.isConnectedToInternet(this)){
            Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_LONG).show();
            finish();
        } else if (SharedPreferencesHelper.getBookListData("bookList", "") != null){
            showBookListFragment();
        } else {
            showFetchJsonFragment();
        }
    }

    private void showBookListFragment(){
        bookListFragment = BookListFragment.newInstance();
        bookListFragment.setBookSelectedListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, bookListFragment);
        ft.commit();
    }

    private void showFetchJsonFragment(){
        fetchJsonFragment = FetchJsonFragment.newInstance();
        fetchJsonFragment.setFetchDataOnSuccessListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fetchJsonFragment);
        ft.commit();
    }

    private void showBookDescriptionFragment(Book book){
        bookDescriptionFragment = BookDescriptionFragment.newInstance(book);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, bookDescriptionFragment);
        ft.addToBackStack(BOOK_DES_FRAG_TAG);
        ft.commit();
    }
    @Override
    public void onSuccessfulResponse() {
        showBookListFragment();
    }

    @Override
    public void onBookSelectedListener(Book book) {
        showBookDescriptionFragment(book);
    }
}