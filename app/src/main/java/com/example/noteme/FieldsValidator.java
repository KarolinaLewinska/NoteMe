package com.example.noteme;

import android.widget.EditText;

public class FieldsValidator {
    public static boolean checkEmptyFields(EditText firstField, EditText secondField) {
        if (firstField.getText().length() == 0) {
            firstField.setError("Wprowadź tytuł notatki");
            return false;
        }

        if (secondField.getText().length() == 0) {
            secondField.setError("Wprowadź treść notatki");
            return false;
        }
        return true;
    }
}
