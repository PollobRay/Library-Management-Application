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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterLibrarian extends AppCompatActivity {

    private Button signUpBtn;
    private EditText emailText,passText;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_librarian);

        signUpBtn=(Button) findViewById(R.id.signUpClickBtnId);
        emailText=(EditText) findViewById(R.id.signUpEmailId);
        passText=(EditText) findViewById(R.id.signUpPassId);

        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String email = emailText.getText().toString().trim();
                String pass = passText.getText().toString().trim();

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

                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterLibrarian.this, "Registration Successfully", Toast.LENGTH_SHORT).show();

                            finish();
                            Intent intent= new Intent(RegisterLibrarian.this, Login.class);
                            startActivity(intent);
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(RegisterLibrarian.this, "The account is already exits", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(RegisterLibrarian.this, "Error :"+task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        });


    }
}