package com.example.noteme;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteView extends RecyclerView.ViewHolder {
    View view;
    TextView noteTitle, noteTimestamp;
    CardView noteCard;

    public NoteView(@NonNull View itemView) {
        super(itemView);

        view = itemView;
        noteTitle = view.findViewById(R.id.note_title);
        noteTimestamp = view.findViewById(R.id.note_timestamp);
        noteCard = view.findViewById(R.id.note_card);
    }

    public void setNoteTitle(String title) {
        noteTitle.setText(title);
    }
    public void setNoteTimestamp(String timestamp) {
        noteTimestamp.setText(timestamp);
    }
}
