package com.example.noteme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class AddOrEditNoteActivity extends AppCompatActivity {

    private Button addOrEditNoteButton;
    private EditText noteTitle, noteContent;
    private Toolbar noteToolbar;
    private MenuItem deleteNoteMenuItem;
    private String noteID = "";
    private boolean noteExists;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.new_note_menu, menu);

        deleteNoteMenuItem = menu.findItem(R.id.deleteNote);
        if (noteID == null) {
            deleteNoteMenuItem.setVisible(false);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        try {
            noteID = getIntent().getStringExtra("noteId");
            noteExists = !noteID.equals("");

        } catch (Exception exc) {
            exc.getMessage();
        }

        addOrEditNoteButton = (Button) findViewById(R.id.addNoteButton);
        noteTitle = (EditText) findViewById(R.id.titleEditText);
        noteContent = (EditText) findViewById(R.id.contentEditText);
        noteToolbar = (Toolbar) findViewById(R.id.noteToolbar);

        setSupportActionBar(noteToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Notatki").child(firebaseAuth.getCurrentUser().getUid());

        addOrEditNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteTitle.getText().toString();
                String content = noteContent.getText().toString();

                if (FieldsValidator.checkEmptyFields(noteTitle, noteContent)) {
                    if (noteExists) {
                        updateNote(title, content);
                    }
                    else {
                        createNote(title, content);
                    }
                }
            }
        });

        showCurrentNote();
    }

    private void showCurrentNote() {
        if (noteExists) {
            databaseReference.child(noteID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean hasTitle = snapshot.hasChild("Tytuł");
                    boolean hasContent = snapshot.hasChild("Treść notatki");
                    if (hasTitle && hasContent) {
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
            DatabaseReference databaseReference2 = databaseReference.push();

            Map newNotesHashMap = new HashMap();
            newNotesHashMap.put("Tytuł", title);
            newNotesHashMap.put("Treść notatki", content);
            newNotesHashMap.put("Czas utworzenia", ServerValue.TIMESTAMP);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    databaseReference2.setValue(newNotesHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddOrEditNoteActivity.this,
                                        "Notatka utworzona!", Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainIntent);

                            } else {
                                Toast.makeText(AddOrEditNoteActivity.this,
                                        "Wystąpił błąd podczas zapisu!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
            thread.start();

        } else {
            Toast.makeText(AddOrEditNoteActivity.this,
                    "Brak zalogowanego użytkownika!", Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
        }
    }

    private void updateNote(String title, String content) {
        if (firebaseAuth.getCurrentUser() != null) {
            if (noteExists) {
                Map currentNoteHashMap = new HashMap();
                currentNoteHashMap.put("Tytuł", noteTitle.getText().toString());
                currentNoteHashMap.put("Treść notatki", noteContent.getText().toString());
                currentNoteHashMap.put("Czas utworzenia", ServerValue.TIMESTAMP);

                databaseReference.child(noteID).updateChildren(currentNoteHashMap);

                Toast.makeText(AddOrEditNoteActivity.this,
                        "Notatka została zaktualizowana!", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            } else {
                Toast.makeText(AddOrEditNoteActivity.this,
                        "Wystąpił błąd podczas auktualizacji!", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            }
        } else {
            Toast.makeText(AddOrEditNoteActivity.this,
                    "Brak zalogowanego użytkownika!", Toast.LENGTH_LONG).show();
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.deleteNote:
                if (noteExists) {
                    deleteNote();
                }
                else {
                    finish();
                }
                break;
        }
        return true;
    }

    private void deleteNote() {
        databaseReference.child(noteID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddOrEditNoteActivity.this,
                            "Usunięto notatkę!", Toast.LENGTH_LONG).show();
                    noteID = "";
                    finish();

                } else {
                    Toast.makeText(AddOrEditNoteActivity.this,
                            "Wystąpił błąd podczas próby usunięcia!", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        });
    }
}
