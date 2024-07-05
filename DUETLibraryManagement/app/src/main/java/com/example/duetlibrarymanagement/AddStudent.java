package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AddStudent extends AppCompatActivity {
    private Button addStudent;
    private EditText idText,nameText;
    private Spinner deptSpinner;
    private String id,name,dept;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        databaseReference= FirebaseDatabase.getInstance().getReference("students");

        addStudent=(Button) findViewById(R.id.addStdId);
        idText=(EditText) findViewById(R.id.addStdIdId);
        nameText=(EditText) findViewById(R.id.addStdNameId);
        deptSpinner=(Spinner) findViewById(R.id.addStdDeptId);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 try{
                     id=idText.getText().toString();
                     name=nameText.getText().toString();
                     dept=deptSpinner.getSelectedItem().toString();
                 }catch(Exception e){}

                 if(id.equals(""))
                 {
                     idText.setError("Student Id can't be Empty");
                     return;
                 }
                 if(name.equals(""))
                 {
                     nameText.setError("Student name can't be Empty");
                     return;
                 }
                 Student std=new Student(dept,id,name);

                String key=databaseReference.push().getKey();

                databaseReference.child(key).setValue(std).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AddStudent.this, "The Student is added Successfully", Toast.LENGTH_SHORT).show();

                            idText.setText("");
                            nameText.setText("");
                        }
                        else
                        {
                            Toast.makeText(AddStudent.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}