package com.example.googlebooksapi.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.interfaces.BookListOnItemClickListener;
import com.example.googlebooksapi.interfaces.BookSelectedListener;
import com.example.googlebooksapi.models.Book;
import com.example.googlebooksapi.utils.SharedPreferencesHelper;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class BookListFragment extends Fragment implements BookListOnItemClickListener {

    ArrayList<Book> bookList;
    private BookSelectedListener bookSelectedListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookListFragment() {
    }

    @SuppressWarnings("unused")
    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookList = SharedPreferencesHelper.getBookListData("bookList", "");
    }

    public void setBookSelectedListener(BookSelectedListener listener){
        bookSelectedListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            MyBookRecyclerViewAdapter adapter = new MyBookRecyclerViewAdapter(bookList);
            adapter.setBookListOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onListItemClickListener(View view, Book book) {
        bookSelectedListener.onBookSelectedListener(book);
    }
}