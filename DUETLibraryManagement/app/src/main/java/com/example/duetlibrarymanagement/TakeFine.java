package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TakeFine extends AppCompatActivity {
    private TextView idTxt,fineTxt;
    private EditText getFine;
    private Button takeBtn;
    private String stdKey;
    private double getFineValue;
    private double totalFine;
    private DatabaseReference databaseReference;
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_fine);

        idTxt=(TextView) findViewById(R.id.takeFineSdIdID);
        fineTxt=(TextView) findViewById(R.id.takeFineFineID);
        getFine=(EditText) findViewById(R.id.takeFinegetFineID);
        takeBtn=(Button) findViewById(R.id.takeFineBtnID);

        //getting Data
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            stdKey= bundle.getString("key");
        }
        databaseReference= FirebaseDatabase.getInstance().getReference().child("students").child(stdKey);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student=snapshot.getValue(Student.class);

                totalFine=Double.parseDouble(student.getFines());
                idTxt.setText("Student Id : "+student.getId());
                fineTxt.setText("Total Fines : "+totalFine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        takeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFine.getText().toString().isEmpty())
                {
                    getFine.setError("Can't be empty");
                    return;
                }
                try{
                    getFineValue=Double.parseDouble(getFine.getText().toString());
                }catch(Exception e){}

                if(getFineValue<=0)
                {
                    getFine.setError("Can't be less than 0");
                    return;
                }
                if(getFineValue>totalFine)
                {
                    getFine.setError("Can't be larger than total Fine");
                    return;
                }

                if(databaseReference.child("fines").setValue(""+(totalFine-getFineValue)).isSuccessful())
                {
                    Toast.makeText(TakeFine.this, "Successfully Taken tha fine", Toast.LENGTH_SHORT).show();
                }

                try{
                    Thread.sleep(100);
                }catch(Exception e){}
/*
                finish();
                Intent intent=new Intent(ReceiveBookInformation.this,AdminView.class);
                startActivity(intent);
                */


            }
        });
    }
}