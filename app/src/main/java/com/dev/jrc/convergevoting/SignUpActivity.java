package com.dev.jrc.convergevoting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText txtEmail,txtPassw;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtSignEmail);
        txtPassw = findViewById(R.id.txtSignPass);

    }

    public void SignPress(final View v){
        String email = txtEmail.getText().toString().trim();
        String passw = txtPassw.getText().toString().trim();

        if(email.isEmpty()){
            txtEmail.setError("Email is required!");
            txtEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Please input a valid email");
            txtEmail.requestFocus();
            return;
        }

        if(passw.isEmpty()){
            txtPassw.setError("Password is required!");
            txtPassw.requestFocus();
            return;
        }

        if(passw.length() < 6){
            txtPassw.setError("Minimum length is 6 characters");
            txtPassw.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    Snackbar.make(v,"User Successfully Registered",Snackbar.LENGTH_LONG).show();
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Snackbar.make(v,"User Already Registered",Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(v,"Error Occured",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
