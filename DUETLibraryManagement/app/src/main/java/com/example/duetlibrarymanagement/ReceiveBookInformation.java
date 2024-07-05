package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReceiveBookInformation extends AppCompatActivity {
    private TextView titleText,authorText,editionText,pubText,catText,copiesText;
    private Button receiveBtn;
    private String bookKey;
    private DatabaseReference databaseReference,databaseReferenceStd;
    private Book book;
    private Student student;
    private String stdKey;
    private int selectedBookNo;
    private int totalIssuedBook;
    private int availableBooks;
    private double fine;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_book_information);

        titleText=(TextView) findViewById(R.id.bookInfoRTitleId);
        authorText=(TextView) findViewById(R.id.bookInfoRAuthorId);
        editionText=(TextView) findViewById(R.id.bookInfoREditionId);
        pubText=(TextView) findViewById(R.id.bookInfoRPubId);
        copiesText=(TextView) findViewById(R.id.bookInfoRCopiesId);
        catText=(TextView) findViewById(R.id.bookInfoRCatId);

        receiveBtn=(Button) findViewById(R.id.bookInfoReceiveBtnId);

        //getting Data
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            bookKey= bundle.getString("BookKey");
            stdKey=bundle.getString("stdKey");
            selectedBookNo=Integer.parseInt(bundle.getString("pos"));
        }

        databaseReference= FirebaseDatabase.getInstance().getReference().child("books").child(bookKey);
        databaseReferenceStd= FirebaseDatabase.getInstance().getReference().child("students").child(stdKey);

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

                availableBooks=Integer.parseInt(book.getCopies());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceStd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student=snapshot.getValue(Student.class);

                fine=Double.parseDouble(student.getFines());
                totalIssuedBook=Integer.parseInt(student.getTakenBooks());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        receiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedBookNo==1)
                {
                    if(totalIssuedBook==1)
                    {
                        //delete the book
                        databaseReferenceStd.child("book1").setValue("");
                        databaseReferenceStd.child("book1IssueDate").setValue("");
                    }
                    else if(totalIssuedBook==2)
                    {
                        //take book 2 to 1 & delete 2
                        //change
                        databaseReferenceStd.child("book1").setValue(student.getBook2());
                        databaseReferenceStd.child("book1IssueDate").setValue(student.getBook2IssueDate());
                        //delete the book
                        databaseReferenceStd.child("book2").setValue("");
                        databaseReferenceStd.child("book2IssueDate").setValue("");
                    }
                    else if(totalIssuedBook==3)
                    {
                        //take book 3 to 1 & delete 3
                        //delete the book
                        databaseReferenceStd.child("book1").setValue(student.getBook3());
                        databaseReferenceStd.child("book1IssueDate").setValue(student.getBook3IssueDate());
                        //delete the book
                        databaseReferenceStd.child("book3").setValue("");
                        databaseReferenceStd.child("book3IssueDate").setValue("");
                    }

                    try{
                        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                        Date today=new Date();
                        Date ddate;

                        if(!student.getBook1IssueDate().equals(""))
                        {
                            ddate=sdf.parse(student.getBook1IssueDate());
                            if(today.compareTo(ddate)>0 )
                            {
                                long diff=today.getTime() - ddate.getTime();
                                int days= (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                                fine +=0.5*days;
                            }
                        }
                    }catch(Exception e){}
                }
                else if(selectedBookNo==2)
                {
                    if(totalIssuedBook==2)
                    {
                        //delete the book
                        //delete the book
                        databaseReferenceStd.child("book2").setValue("");
                        databaseReferenceStd.child("book2IssueDate").setValue("");
                    }
                    else if(totalIssuedBook==3)
                    {
                        //take book 3 to 2 & delete 3
                        //delete the book
                        databaseReferenceStd.child("book2").setValue(student.getBook3());
                        databaseReferenceStd.child("book2IssueDate").setValue(student.getBook3IssueDate());
                        //delete the book
                        databaseReferenceStd.child("book3").setValue("");
                        databaseReferenceStd.child("book3IssueDate").setValue("");
                    }

                    try{
                        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                        Date today=new Date();
                        Date ddate;

                        if(!student.getBook2IssueDate().equals(""))
                        {
                            ddate=sdf.parse(student.getBook2IssueDate());
                            if(today.compareTo(ddate)>0 )
                            {
                                long diff=today.getTime() - ddate.getTime();
                                int days= (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                                fine +=0.5*days;
                            }
                        }
                    }catch(Exception e){}
                }
                else if(selectedBookNo==3)
                {
                    //delete book 3
                    //delete the book
                    databaseReferenceStd.child("book3").setValue("");
                    databaseReferenceStd.child("book3IssueDate").setValue("");
                    try{
                        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                        Date today=new Date();
                        Date ddate;

                        if(!student.getBook3IssueDate().equals(""))
                        {
                            ddate=sdf.parse(student.getBook3IssueDate());
                            if(today.compareTo(ddate)>0 )
                            {
                                long diff=today.getTime() - ddate.getTime();
                                int days= (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                                fine +=0.5*days;
                            }
                        }
                    }catch(Exception e){}
                }

                totalIssuedBook--;
                availableBooks++;
                //update
                databaseReferenceStd.child("takenBooks").setValue(""+totalIssuedBook);
                databaseReferenceStd.child("fines").setValue(""+fine);
                databaseReference.child("copies").setValue(""+availableBooks);

                Toast.makeText(ReceiveBookInformation.this, "The book is Received Successfully", Toast.LENGTH_SHORT).show();

                try{
                    Thread.sleep(1000);
                }catch(Exception e){}

                finish();
                Intent intent=new Intent(ReceiveBookInformation.this,AdminView.class);
                startActivity(intent);
            }
        });
    }
}