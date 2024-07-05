package com.example.duetlibrarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminView extends AppCompatActivity {

    private Button addBookBtn;
    private Button addStdBtn;
    private Button issueBtn;
    private Button stdRecordBtn;
    private Button searchButton;
    private Button addAdminBtn;
    private Button receiveBookBtn;
    private Button takeFineBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        addBookBtn=(Button) findViewById(R.id.addBookBtnId);
        addStdBtn=(Button) findViewById(R.id.addStdBtnId);
        issueBtn=(Button) findViewById(R.id.issueBookBtnId);
        stdRecordBtn=(Button) findViewById(R.id.stdRecordBtnId);
        searchButton=(Button) findViewById(R.id.searchBookBtnId);
        addAdminBtn=(Button) findViewById(R.id.addLibriranBtnId);
        receiveBookBtn=(Button) findViewById(R.id.adminVReceiveBId);
        takeFineBtn=(Button) findViewById(R.id.adminVTakeFineId);

        addBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminView.this,AddBook.class);
                startActivity(intent);
            }
        });

        addStdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminView.this,AddStudent.class);
                startActivity(intent);
            }
        });

        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminView.this,ViewBooks.class);
                startActivity(intent);
            }
        });

        stdRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminView.this,ViewStudents.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminView.this,SearchBook.class);
                startActivity(intent);
            }
        });

        addAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminView.this,RegisterLibrarian.class);
                startActivity(intent);
            }
        });

        receiveBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminView.this,StdListForReceivebook.class);
                startActivity(intent);
            }
        });

        takeFineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminView.this,StdListForReceivebook.class);
                startActivity(intent);
            }
        });
    }
}