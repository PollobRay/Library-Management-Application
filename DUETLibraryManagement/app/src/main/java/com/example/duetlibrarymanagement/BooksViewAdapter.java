package com.example.duetlibrarymanagement;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BooksViewAdapter extends ArrayAdapter<Book>
{
    private Activity context;
    private List<Book> bookList;

    public BooksViewAdapter(Activity context, List<Book> bookList)
    {
        super(context, R.layout.booksviewlayout,bookList);
        this.context=context;
        this.bookList=bookList;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.booksviewlayout,null,true);

        Book book=bookList.get(position);

        TextView authorText=view.findViewById(R.id.layoutBookViewAuthorId);
        TextView titleText=view.findViewById(R.id.layoutBookViewTitlId);

        titleText.setText(book.getTitle().toString());
        authorText.setText(book.getAuthor().toString());

        return view;
    }
}
