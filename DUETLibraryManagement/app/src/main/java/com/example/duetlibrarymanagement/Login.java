package com.example.duetlibrarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button stdBtn ;
    Button admBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        stdBtn = (Button) findViewById(R.id.loginStdBtnId);

        stdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,StudentLogin.class);
                startActivity(intent);
            }
        });
        admBtn=(Button) findViewById(R.id.LoginAdminBtnId);
        admBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,AdminLogin.class);
                startActivity(intent);
            }
        });

    }
}