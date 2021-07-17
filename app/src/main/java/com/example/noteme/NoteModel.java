package com.example.noteme;

public class NoteModel {

    public String title;
    public String content;
    public String timestamp;

    public NoteModel() {

    }

    public NoteModel(String title, String timestamp) {
        this.title = title;
        this.timestamp = timestamp;
    }
}
