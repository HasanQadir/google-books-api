package com.example.googlebooksapi.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.models.Book;

public class BookDescriptionFragment extends Fragment {

    private static final String ARG_PARAM = "book";
    private Book book;
    private TextView bookDescription;

    public BookDescriptionFragment() {
        // Required empty public constructor
    }

    public static BookDescriptionFragment newInstance(Book book) {
        BookDescriptionFragment fragment = new BookDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_description, container, false);
        bookDescription = view.findViewById(R.id.book_description);
        bookDescription.setText(book.getDescription());
        return view;
    }
}