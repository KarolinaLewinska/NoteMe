package com.example.noteme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayoutManager = new GridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);

        recyclerView = findViewById(R.id.notes_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        firebaseAuth = firebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("Notatki").child(firebaseAuth.getCurrentUser().getUid());
        }
        setInterface();
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setInterface() {
        if (firebaseAuth.getCurrentUser() == null) {
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }

    private void loadData() {
        Query query = databaseReference.orderByValue();
        FirebaseRecyclerAdapter<NoteModel, NoteView> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<NoteModel, NoteView>(
                    NoteModel.class,
                    R.layout.note_view,
                    NoteView.class,
                    query) {
            @Override
            protected void populateViewHolder(NoteView noteView, NoteModel noteModel, int i) {
                String noteId = getRef(i).getKey();

                databaseReference.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("Tytuł") && snapshot.hasChild("Czas utworzenia")) {
                            String title = snapshot.child("Tytuł").getValue().toString();
                            String timestamp = snapshot.child("Czas utworzenia").getValue().toString();
                            TimeOfNote timeOfNote = new TimeOfNote();

                            noteView.setNoteTitle(title);
                            noteView.setNoteTimestamp(timeOfNote.getTimeModification
                                    (Long.parseLong(timestamp), getApplicationContext()));

                            noteView.noteCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this, AddOrEditNoteActivity.class);
                                    intent.putExtra("noteId", noteId);
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.addNoteButton:
                Intent intent = new Intent(MainActivity.this, AddOrEditNoteActivity.class);
                startActivity(intent);
                break;
            case R.id.exitButton:
                firebaseAuth.signOut();
                Toast.makeText(MainActivity.this,
                        "Wylogowano!", Toast.LENGTH_LONG). show();
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
        }
        return true;
    }
}