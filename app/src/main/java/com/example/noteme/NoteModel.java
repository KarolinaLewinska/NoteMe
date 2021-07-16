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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
