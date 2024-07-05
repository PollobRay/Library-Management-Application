package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBooks extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference databaseReference;
    private List<Book> booksList;
    private List<String> booksKeyList;
    private BooksViewAdapter booksViewAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);

        listView=(ListView) findViewById(R.id.viewBookListId);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("books");
        booksList=new ArrayList<>();
        booksKeyList=new ArrayList<>();
        booksViewAdapter=new BooksViewAdapter(ViewBooks.this,booksList);

        listView.setAdapter(booksViewAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        listView.setAdapter(booksViewAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                booksList.clear();
                booksKeyList.clear();
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    Book book=snap.getValue(Book.class);

                    booksList.add(book);
                    booksKeyList.add(snap.getKey());
                }
                booksViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewBooks.this, "Can't Load Data", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                String key=booksKeyList.get(position).toString();

                Intent intent=new Intent(ViewBooks.this,BookInformation.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
    }
}