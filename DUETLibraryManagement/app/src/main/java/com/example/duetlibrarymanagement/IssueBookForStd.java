package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class IssueBookForStd extends AppCompatActivity {

    private TextView idT,nameT,totalIssueT,book1T,book1D,book2T,book2D,book3,book3D,finesT,selectedBookT;
    private DatabaseReference databaseReference,databaseRefForBook;
    private String key;
    private Student std;
    private Button issueBtn;
    private int totalIssuedBook;
    private String bookKey;
    private String book1Key,book2Key,book3Key;
    private int totalAvailableCopies;
    private double fines;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book_for_std);

        idT=(TextView) findViewById(R.id.issueBookStdIDId);
        nameT=(TextView) findViewById(R.id.issueBookStdNameId);
        totalIssueT=(TextView) findViewById(R.id.issueBookStdTotalIssueId);
        book1T=(TextView) findViewById(R.id.issueBookStdBook1Id);
        book1D=(TextView) findViewById(R.id.issueBookStdBook1DId);
        book2T=(TextView) findViewById(R.id.issueBookStdBook2Id);
        book2D=(TextView) findViewById(R.id.issueBookStdBook2DId);
        book3=(TextView) findViewById(R.id.issueBookStdBook3Id);
        book3D=(TextView) findViewById(R.id.issueBookStdBook3DId);
        finesT=(TextView) findViewById(R.id.issueBookStdfineId);

        selectedBookT=(TextView) findViewById(R.id.issueBookSelectBookId);
        issueBtn=(Button) findViewById(R.id.issueBookStdIssueBtn);

        //getting Data
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            key= bundle.getString("key");
            bookKey=bundle.getString("bookKey");
        }

        databaseReference= FirebaseDatabase.getInstance().getReference().child("students").child(key);
        databaseRefForBook=FirebaseDatabase.getInstance().getReference().child("books");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                std = snapshot.getValue(Student.class);

                fines=Double.parseDouble(std.getFines());

                idT.setText(std.getId());
                nameT.setText(std.getName());
                book1Key=std.getBook1();
                book1D.setText(std.getBook1IssueDate());
                book2Key=std.getBook2();
                book2D.setText(std.getBook2IssueDate());
                book3Key=std.getBook3();
                book3D.setText(std.getBook3IssueDate());
                totalIssueT.setText(std.getTakenBooks());

                try{
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    Date today=new Date();
                    Date ddate;

                    if(!std.getBook1IssueDate().equals(""))
                    {
                        ddate=sdf.parse(std.getBook1IssueDate());
                        if(today.compareTo(ddate)>0 )
                        {
                            long diff=today.getTime() - ddate.getTime();
                            int days= (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                            fines +=0.5*days;
                        }
                    }

                    if(!std.getBook2IssueDate().equals(""))
                    {
                        ddate=sdf.parse(std.getBook2IssueDate());
                        if(today.compareTo(ddate)>0 )
                        {
                            long diff=today.getTime() - ddate.getTime();
                            int days= (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                            fines +=0.5*days;
                        }
                    }

                    if(!std.getBook3IssueDate().equals(""))
                    {
                        ddate=sdf.parse(std.getBook3IssueDate());
                        if(today.compareTo(ddate)>0 )
                        {
                            long diff=today.getTime() - ddate.getTime();
                            int days= (int) TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                            fines +=0.5*days;
                        }
                    }
                }catch(Exception e){}

                finesT.setText(""+fines);
                totalIssuedBook=Integer.parseInt(std.getTakenBooks());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseRefForBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    Book book=snap.getValue(Book.class);
                    if(snap.getKey().equals(bookKey))
                    {
                        selectedBookT.setText(book.getTitle());
                        totalAvailableCopies=Integer.parseInt(book.getCopies());
                    }
                   if(!book1Key.isEmpty() && snap.getKey().equals(book1Key))
                    {
                        book1T.setText(book.getTitle());
                    }
                    if(!book2Key.isEmpty() && snap.getKey().equals(book2Key))
                    {
                        book2T.setText(book.getTitle());
                    }
                    if(!book3Key.isEmpty() && snap.getKey().equals(book3Key))
                    {
                        book3.setText(book.getTitle());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dealine="23-02-2023";

                try{

                    Date today=new Date();
                    long ltime = today.getTime()+7*24*60*60*1000; //add 7 days
                    Date today7=new Date(ltime);
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                    dealine=sdf.format(today7);

                }catch (Exception e){}

                if(totalAvailableCopies>0)
                {
                    if(totalIssuedBook==3)
                    {
                        Toast.makeText(IssueBookForStd.this, "Three book is already Issued", Toast.LENGTH_SHORT).show();
                    }
                    else if(totalIssuedBook==2)
                    {
                        //make student record change
                        databaseReference.child("takenBooks").setValue(""+(totalIssuedBook+1));
                        databaseReference.child("book3").setValue(bookKey);
                        databaseReference.child("book3IssueDate").setValue(dealine);

                        //make book record change
                        databaseRefForBook.child(bookKey).child("copies").setValue(""+(--totalAvailableCopies));
                    }
                    else if(totalIssuedBook==1)
                    {
                        //make student record change
                        databaseReference.child("takenBooks").setValue(""+(totalIssuedBook+1));
                        databaseReference.child("book2").setValue(bookKey);
                        databaseReference.child("book2IssueDate").setValue(dealine);

                        //make book record change
                        databaseRefForBook.child(bookKey).child("copies").setValue(""+(--totalAvailableCopies));
                    }

                     else if(totalIssuedBook==0)
                    {
                        //make student record change
                        databaseReference.child("takenBooks").setValue(""+(++totalIssuedBook));
                        databaseReference.child("book1").setValue(bookKey);
                        databaseReference.child("book1IssueDate").setValue(dealine);

                        //make book record change
                        databaseRefForBook.child(bookKey).child("copies").setValue(""+(--totalAvailableCopies));
                    }


                    Toast.makeText(IssueBookForStd.this, "Successfully Issued the Book ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(IssueBookForStd.this, "The Selected Book is Available", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}