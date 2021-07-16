package com.example.noteme;

import android.widget.EditText;

public class FieldsValidator {
    public static boolean checkEmptyFields(EditText noteTitle, EditText noteContent) {
        if (noteTitle.getText().length() == 0) {
            noteTitle.setError("Wprowadź tytuł notatki");
            return false;
        }

        if (noteContent.getText().length() == 0) {
            noteContent.setError("Wprowadź treść notatki");
            return false;
        }
        return true;
    }
}
