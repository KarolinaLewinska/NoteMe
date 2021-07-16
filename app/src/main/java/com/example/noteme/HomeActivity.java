package com.example.noteme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button loginButton, registerButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        firebaseAuth = firebaseAuth.getInstance();
        setInterface();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logInIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(logInIntent);
            }
        });
    }

    private void setInterface() {
        if (firebaseAuth.getCurrentUser() != null) {
            Intent homeIntent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }
}