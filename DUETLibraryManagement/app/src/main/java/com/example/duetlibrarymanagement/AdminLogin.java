package com.example.duetlibrarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {
    Button adminLogin;
    private EditText emailText,passText;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();
        emailText=(EditText) findViewById(R.id.adminLoginEmailId);
        passText=(EditText) findViewById(R.id.adminLogPassId);

        adminLogin=(Button) findViewById(R.id.adminLoginBtnId);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=emailText.getText().toString().trim();
                String pass=passText.getText().toString().trim();

                if (email.isEmpty())
                {
                    emailText.setError("Enter an email address");
                    emailText.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    emailText.setError("Enter a valid email Address");
                    emailText.requestFocus();
                    return;
                }

                if(pass.isEmpty())
                {
                    passText.setError("Enter a password");
                    passText.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(AdminLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            finish(); //finish the SignIn activity's work
                            Intent intent=new Intent(AdminLogin.this,AdminView.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("email",email);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(AdminLogin.this, "Login UnSuccessfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}