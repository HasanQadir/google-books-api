package com.example.googlebooksapi.fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.interfaces.BookListOnItemClickListener;
import com.example.googlebooksapi.models.Book;

import java.util.ArrayList;


public class MyBookRecyclerViewAdapter extends RecyclerView.Adapter<MyBookRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Book> books;
    private BookListOnItemClickListener bookListOnItemClickListener;

    public MyBookRecyclerViewAdapter(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    public void setBookListOnItemClickListener(BookListOnItemClickListener listener){
        bookListOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bookTitle.setText(books.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView bookTitle;

        public ViewHolder(View view) {
            super(view);
            bookTitle = (TextView) view.findViewById(R.id.book_title);
            bookTitle.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + bookTitle.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            if (bookListOnItemClickListener != null) bookListOnItemClickListener.onListItemClickListener(view,(Book) books.get(getAdapterPosition()));

        }
    }
}