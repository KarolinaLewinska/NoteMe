package com.example.noteme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button signInButton;
    private EditText userEmail, userPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signInButton = (Button) findViewById(R.id.signinButton);
        userEmail = (EditText) findViewById(R.id.editTextEmail);
        userPassword = (EditText) findViewById(R.id.editTextPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmail.length() == 0) {
                    userEmail.setError("Pole wymagane!");
                }
                if (userPassword.length() == 0) {
                    userPassword.setError("Pole wymagane!");
                }
                else {
                    String email = userEmail.getText().toString();
                    String password = userPassword.getText().toString();
                    Register(email, password);
                }
            }
        });
    }

    private void Register(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Trwa rejestracja, prosze czekać...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                    Toast.makeText(RegisterActivity.this,
                            "Rejestracja przebiegła pomyślnie!", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,
                            "Nie udało się zarejestrować użytkownika!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}