package com.example.noteme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class AddNoteActivity extends AppCompatActivity {

    private Button noteCreate;
    private EditText noteTitle;
    private EditText noteContent;
    private Toolbar newNoteToolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String idNote = "";
    private boolean isExist;
    private MenuItem deleteIcon;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        deleteIcon = menu.findItem(R.id.deleteNote);
        if (idNote == null) {
            deleteIcon.setVisible(false);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        try {
            idNote = getIntent().getStringExtra("noteId");
            if (!idNote.equals("")) {
                isExist = true;
            } else {
                isExist = false;
            }

        } catch(Exception exc) {
            exc.getMessage();
        }

        noteCreate = (Button) findViewById(R.id.addNoteButton);
        noteTitle = (EditText) findViewById(R.id.titleEditText);
        noteContent = (EditText) findViewById(R.id.contentEditText);
        newNoteToolbar = (Toolbar) findViewById(R.id.addNoteToolbar);

        setSupportActionBar(newNoteToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Notatki").child(firebaseAuth.getCurrentUser().getUid());

        noteCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteTitle.getText().toString();
                String content = noteContent.getText().toString();

                if (noteTitle.length() == 0) {
                    noteTitle.setError("Wprowadź tytuł notatki");
                }
                if (noteContent.length() == 0) {
                   noteContent.setError("Wprowadź treść notatki");
                }
                createNote(title, content);
            }
        });
        showCurrentNote();
    }

    private void showCurrentNote() {
        if (isExist) {
            databaseReference.child(idNote).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild("Tytuł") && snapshot.hasChild("Treść notatki")) {
                        String title = snapshot.child("Tytuł").getValue().toString();
                        String content = snapshot.child("Treść notatki").getValue().toString();
                        noteTitle.setText(title);
                        noteContent.setText(content);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void createNote(String title, String content) {
        if (firebaseAuth.getCurrentUser() != null) {
            if (isExist) {
                Map hasMapUpdate = new HashMap();
                hasMapUpdate.put("Tytuł", noteTitle.getText().toString());
                hasMapUpdate.put("Treść notatki", noteContent.getText().toString());
                hasMapUpdate.put("Czas utworzenia", ServerValue.TIMESTAMP);
                databaseReference.child(idNote).updateChildren(hasMapUpdate);
                Toast.makeText(AddNoteActivity.this,
                        "Notatka została zaktualizowana!", Toast.LENGTH_LONG).show();

            } else {

                DatabaseReference databaseReference2 = databaseReference.push();

                Map hashMap = new HashMap();
                hashMap.put("Tytuł", title);
                hashMap.put("Treść notatki", content);
                hashMap.put("Czas utworzenia", ServerValue.TIMESTAMP);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        databaseReference2.setValue(hashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddNoteActivity.this,
                                            "Notatka utworzona!", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(AddNoteActivity.this,
                                            "Wystąpił błąd zapisu notatki!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                thread.start();
            }

        } else {
            Toast.makeText(AddNoteActivity.this,
                    "Brak zalogowanego użytkownika!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.deleteNote:
                if (isExist) {
                    deleteNote();
                } 
                break;
        }
        return true;
    }

    private void deleteNote() {
        databaseReference.child(idNote).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddNoteActivity.this,
                            "Usunięto notatkę!", Toast.LENGTH_LONG).show();
                    idNote = "none";
                    finish();
                } else {
                    Toast.makeText(AddNoteActivity.this,
                            "Wystąpił błąd!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
