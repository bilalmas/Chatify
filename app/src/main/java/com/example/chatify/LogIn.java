package com.example.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    private EditText username;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = (EditText) findViewById(R.id.editTextemail);
        passwordEditText = (EditText) findViewById(R.id.editTextpassword);
        progressBar = (ProgressBar) findViewById(R.id.progbar);
        mAuth = FirebaseAuth.getInstance();
    }

    public void logIn(View view){

        String email = username.getText().toString();
        final String password =  passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                //password.setError("Password is less than 6 words!");
                                Toast.makeText(getApplicationContext(), "Password is less than 6 words!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LogIn.this,"Authentication Failed. Please try again" , Toast.LENGTH_LONG).show();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Logged In Successfully!", Toast.LENGTH_SHORT).show();
                            Intent HomeActivity = new Intent(LogIn.this, Home.class);
                            startActivity(HomeActivity);
                            finish();
                        }
                    }
                });
    }
}
