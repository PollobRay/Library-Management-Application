package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchBookInfo extends AppCompatActivity {
    private TextView titleText,authorText,editionText,pubText,catText,copiesText;
    private String key;
    private DatabaseReference databaseReference;
    private Book book;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book_info);

        titleText=(TextView) findViewById(R.id.srcbookInfoTitleId);
        authorText=(TextView) findViewById(R.id.srcbookInfoAuthorId);
        editionText=(TextView) findViewById(R.id.srcbookInfoEditionId);
        pubText=(TextView) findViewById(R.id.srcbookInfoPubId);
        copiesText=(TextView) findViewById(R.id.srcbookInfoCopiesId);
        catText=(TextView) findViewById(R.id.srcbookInfoCatId);


        //getting Data
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            key= bundle.getString("key");
        }

        databaseReference= FirebaseDatabase.getInstance().getReference().child("books").child(key);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                book=snapshot.getValue(Book.class);

                titleText.setText(book.getTitle());
                authorText.setText(book.getAuthor());
                editionText.setText(book.getEdition());
                catText.setText(book.getCategory());
                copiesText.setText(book.getCopies());
                pubText.setText(book.getPublication());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}