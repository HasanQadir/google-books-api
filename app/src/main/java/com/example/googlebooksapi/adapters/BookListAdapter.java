package com.example.googlebooksapi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.googlebooksapi.R;
import com.example.googlebooksapi.models.Book;

import java.util.ArrayList;

public class BookListAdapter extends BaseAdapter {
    private final ArrayList<Book> books;
    private final Activity activity;

    public BookListAdapter(Activity activity, ArrayList<Book> books) {
        this.activity = activity;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book = books.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.book_item, parent, false);
            viewHolder.bookTitle = convertView.findViewById(R.id.book_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bookTitle.setText(book.getTitle());
        // Return the completed view to render on screen
        return convertView;
    }

    private static class ViewHolder {
        TextView bookTitle;
    }

}
