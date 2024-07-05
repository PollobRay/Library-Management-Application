package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReceiveBookList extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference databaseReference;
    private List<Book> booksList;
    private List<String> booksKeyList;
    private BooksViewAdapter booksViewAdapter;
    private String stdKey;
    private DatabaseReference databaseReferenceStd;
    private Student std;
    private int totalIssuedBook;
    private TextView titleTxt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_book_list);

        //getting Data
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            stdKey= bundle.getString("key");
        }

        listView=(ListView) findViewById(R.id.viewReceiveBookListId);
        titleTxt=(TextView) findViewById(R.id.receiveBTTId);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("books");
        databaseReferenceStd= FirebaseDatabase.getInstance().getReference().child("students").child(stdKey);
        booksList=new ArrayList<>();
        booksKeyList=new ArrayList<>();
        booksViewAdapter=new BooksViewAdapter(ReceiveBookList.this,booksList);

        listView.setAdapter(booksViewAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        listView.setAdapter(booksViewAdapter);

        databaseReferenceStd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                std = snapshot.getValue(Student.class);
                totalIssuedBook=Integer.parseInt(std.getTakenBooks());

                titleTxt.setText("Select Book to Receive from "+std.getId());

                booksList.clear();
                booksKeyList.clear();
                if(totalIssuedBook>=1)
                {
                    databaseReference.child(std.getBook1()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Book book=snapshot.getValue(Book.class);

                            booksList.add(book);
                            booksKeyList.add(snapshot.getKey());

                            //Toast.makeText(ReceiveBookList.this, ""+book.getTitle(), Toast.LENGTH_SHORT).show();
                            booksViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                if (totalIssuedBook>=2)
                {
                    databaseReference.child(std.getBook2()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Book book=snapshot.getValue(Book.class);

                            booksList.add(book);
                            booksKeyList.add(snapshot.getKey());
                            booksViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                if (totalIssuedBook==3)
                {
                    databaseReference.child(std.getBook3()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Book book=snapshot.getValue(Book.class);

                            booksList.add(book);
                            booksKeyList.add(snapshot.getKey());
                            booksViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                booksViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                String key=booksKeyList.get(position).toString();

                Intent intent=new Intent(ReceiveBookList.this,ReceiveBookInformation.class);
                intent.putExtra("BookKey",key);
                intent.putExtra("stdKey",stdKey);
                intent.putExtra("pos",""+(position+1));
                startActivity(intent);
            }
        });
    }
}