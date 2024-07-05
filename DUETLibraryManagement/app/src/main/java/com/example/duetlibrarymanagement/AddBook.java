package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBook extends AppCompatActivity {
    private EditText titleText,copiesText,authorText,editionText,pubText;
    private Spinner categorySpinner;
    private String title,copies,category,author,edition,publication;
    private Button addBookBtn;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        databaseReference= FirebaseDatabase.getInstance().getReference("books");

        titleText=(EditText) findViewById(R.id.addBookTitleId);
        authorText=(EditText) findViewById(R.id.addBookAuthorId);
        editionText=(EditText) findViewById(R.id.addBookEditionId);
        pubText=(EditText) findViewById(R.id.addBookPublisherId);
        copiesText=(EditText) findViewById(R.id.addBookCopiesId);
        categorySpinner=(Spinner) findViewById(R.id.addBookCatId);

        addBookBtn=(Button) findViewById(R.id.addBookId);

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    title=titleText.getText().toString();
                    author=authorText.getText().toString();
                    edition=editionText.getText().toString();
                    publication=pubText.getText().toString();
                    copies=copiesText.getText().toString();
                    category=categorySpinner.getSelectedItem().toString();
                }catch(Exception e){}
                if(title.equals(""))
                {
                    titleText.setError("Title can't be Empty");
                    return;
                }
                if(author.equals(""))
                {
                    authorText.setError("Author can't be Empty");
                    return;
                }
                if(edition.equals(""))
                {
                    editionText.setError("Edition can't be Empty");
                    return;
                }
                if(publication.equals(""))
                {
                    pubText.setError("Publication can't be Empty");
                    return;
                }
                if(copies.equals(""))
                {
                    copiesText.setError("Copies can't be Empty");
                    return;
                }

                Book book=new Book(author,category,copies,edition,publication,title);

                String key=databaseReference.push().getKey();

                databaseReference.child(key).setValue(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AddBook.this, "The Book is added Successfully", Toast.LENGTH_SHORT).show();

                            titleText.setText("");
                            authorText.setText("");
                            editionText.setText("");
                            pubText.setText("");
                            copiesText.setText("");

                        }
                        else
                        {
                            Toast.makeText(AddBook.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



    }
}