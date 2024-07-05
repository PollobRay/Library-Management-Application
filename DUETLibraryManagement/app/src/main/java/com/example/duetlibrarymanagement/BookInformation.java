package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookInformation extends AppCompatActivity {
    private TextView titleText,authorText,editionText,pubText,catText,copiesText;
    private Button issueBtn;
    private String key;
    private DatabaseReference databaseReference;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_information);

        titleText=(TextView) findViewById(R.id.bookInfoTitleId);
        authorText=(TextView) findViewById(R.id.bookInfoAuthorId);
        editionText=(TextView) findViewById(R.id.bookInfoEditionId);
        pubText=(TextView) findViewById(R.id.bookInfoPubId);
        copiesText=(TextView) findViewById(R.id.bookInfoCopiesId);
        catText=(TextView) findViewById(R.id.bookInfoCatId);

        issueBtn=(Button) findViewById(R.id.bookInfoIssueBtnId);

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

        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookInformation.this,SelectStdToIssueBook.class);
                intent.putExtra("bookKey",key);
                startActivity(intent);
            }
        });

    }
}