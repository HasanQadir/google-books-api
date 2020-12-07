package com.example.googlebooksapi.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.fragments.BookDescriptionFragment;
import com.example.googlebooksapi.fragments.BookListFragment;
import com.example.googlebooksapi.interfaces.BookSelectedListener;
import com.example.googlebooksapi.models.Book;

import java.util.ArrayList;

import static com.example.googlebooksapi.utils.Constants.BOOK_DES_FRAG_TAG;

public class BookListActivity extends AppCompatActivity implements BookSelectedListener {

    ArrayList<Book> books;
    BookListFragment bookListFragment;
    BookDescriptionFragment bookDescriptionFragment;
    // Flag determines if this is a one or two pane layout
    private boolean isTwoPane = false;
    private int bookListFragmentId;
    private int bookDetailFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        if (isTwoPaneLayout()){
            bookListFragmentId = R.id.frItemsListContainer;
            bookDetailFragmentId = R.id.frDetailContainer;
            showBookListFragment();
            showBookDescriptionFragment(new Book("", ""));
        } else {
            bookListFragmentId = R.id.frame_layout;
            bookDetailFragmentId = R.id.frame_layout;
            showBookListFragment();
        }

        // Initializing activity member variables and views
        books = new ArrayList<>();
        showBookListFragment();
    }

    private boolean isTwoPaneLayout() {
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.frDetailContainer);
        // If there is a second pane for details
        return fragmentItemDetail != null;
    }

    private void showBookListFragment(){
        bookListFragment = BookListFragment.newInstance();
        bookListFragment.setBookSelectedListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(bookListFragmentId, bookListFragment);
        ft.commit();
    }

    private void showBookDescriptionFragment(Book book){
        bookDescriptionFragment = BookDescriptionFragment.newInstance(book);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(bookDetailFragmentId, bookDescriptionFragment);
        ft.addToBackStack(BOOK_DES_FRAG_TAG);
        ft.commit();
    }

    @Override
    public void onBookSelectedListener(Book book) {
        showBookDescriptionFragment(book);
    }
}