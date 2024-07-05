package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StudentInformation extends AppCompatActivity {

    private TextView idT,nameT,totalIssueT,book1T,book1D,book2T,book2D,book3,book3D,finesT;
    private DatabaseReference databaseReference,databaseRefForBook;
    private String key;
    private Student std;
    private int totalIssuedBook;
    private String bookKey;
    private String book1Key,book2Key,book3Key;
    private int totalAvailableCopies;
    private double fines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);

        idT=(TextView) findViewById(R.id.stdInfoIDId);
        nameT=(TextView) findViewById(R.id.stdInfoNameId);
        totalIssueT=(TextView) findViewById(R.id.stdInfoTotalIssueId);
        book1T=(TextView) findViewById(R.id.stdInfoBook1Id);
        book1D=(TextView) findViewById(R.id.stdInfoBook1DId);
        book2T=(TextView) findViewById(R.id.stdInfoBook2Id);
        book2D=(TextView) findViewById(R.id.stdInfoBook2DId);
        book3=(TextView) findViewById(R.id.stdInfoBook3Id);
        book3D=(TextView) findViewById(R.id.stdInfoBook3DId);
        finesT=(TextView) findViewById(R.id.stdInfofineId);


        //getting Data
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            key= bundle.getString("key");
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

    }
}