package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectStdToIssueBook extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference databaseReference;
    private List<Student> studentList;
    private StudentViewAdapter studentViewAdapter;
    private List<String> studentKeyList;
    private String bookKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_std_to_issue_book);

        listView=(ListView) findViewById(R.id.selectStdListId);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("students");
        studentList=new ArrayList<>();
        studentKeyList=new ArrayList<>();
        studentViewAdapter=new StudentViewAdapter(SelectStdToIssueBook.this,studentList);

        //getting Data
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            bookKey= bundle.getString("bookKey");
        }

        listView.setAdapter(studentViewAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        listView.setAdapter(studentViewAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                studentKeyList.clear();
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    Student std=snap.getValue(Student.class);

                    studentList.add(std);
                    studentKeyList.add(snap.getKey());
                }
                studentViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SelectStdToIssueBook.this, "Can't Load Data", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                String key=studentKeyList.get(position).toString();

                Intent intent=new Intent(SelectStdToIssueBook.this,IssueBookForStd.class);
                intent.putExtra("key",key);
                intent.putExtra("bookKey",bookKey);
                startActivity(intent);
            }
        });
    }
}