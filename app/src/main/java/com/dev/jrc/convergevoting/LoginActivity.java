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

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText txtEmail,txtPassw;

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MenuActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtEmail);
        txtPassw = findViewById(R.id.txtPassw);


    }

    public void LoginPress(final View v){
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

        mAuth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    Snackbar.make(v,"User Successfully Login",Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                }else{
                    Snackbar.make(v,"Error Occured",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void SignUpPress(View v){
        finish();
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
