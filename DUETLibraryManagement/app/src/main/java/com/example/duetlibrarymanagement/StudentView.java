package com.example.duetlibrarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentView extends AppCompatActivity {
    private Button searchBookBtn;
    private String key;
    private String stdName;
    private TextView greetText;
    private Button stdRecordBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);

        greetText=(TextView) findViewById(R.id.stdGreetNameId);
        stdRecordBtn=(Button) findViewById(R.id.persoStdrecordBtnId);

        searchBookBtn=(Button) findViewById(R.id.stdSrcBookBtnId);

        searchBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(StudentView.this,SearchBook.class);
                startActivity(intent);
            }
        });

        stdRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentView.this,StudentInformation.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
        {
            key=bundle.getString("key");
            stdName=bundle.getString("name");
            greetText.setText(stdName);
        }
    }
}