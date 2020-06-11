package com.example.chatify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StarterPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter_page);
    }

    public void login(View view){
        Intent Login = new Intent(this,LogIn.class);
        startActivity(Login);
    }

    public void signUp(View view){
        Intent signUp = new Intent(this,SignUp.class);
        startActivity(signUp);
    }
}
