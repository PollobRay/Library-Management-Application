package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLogin extends AppCompatActivity {

    private Button stdLogBtn ;
    private EditText idText;
    private boolean isFound;
    private String key;
    private String stdID;
    private String stdName;

    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        stdLogBtn = (Button) findViewById(R.id.stdLoginBtnId);
        idText=(EditText) findViewById(R.id.logStdIDTxtId);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("students");

        stdLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(idText.getText().equals(""))
                {
                    idText.setError("Can't be empty");
                    idText.requestFocus();
                    return;
                }
                stdID=idText.getText().toString().trim();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snap:snapshot.getChildren())
                        {
                            Student std=snap.getValue(Student.class);
                            if(std.getId().equals(stdID))
                            {
                                key=snap.getKey();
                                stdName=std.getName();
                                isFound=true;
                                Intent intent=new Intent(StudentLogin.this,StudentView.class);
                                intent.putExtra("key",key);
                                intent.putExtra("name",stdName);
                                startActivity(intent);
                            }
                        }
                        if (!isFound)
                        {
                            Toast.makeText(StudentLogin.this, "Not Found any Record", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(StudentLogin.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}