package com.example.amphsesviewer.ui

import android.util.Patterns
import android.widget.EditText

fun EditText.validateInputNotEmpty(errorText: String): Boolean {
    if (this.text.trim().isEmpty()) {
        this.error = errorText
        requestFocus()
        return false
    }
    return true
}

fun EditText.validatePassword(errorText: String): Boolean {
    if (this.text.trim().length < PASSWORD_LENGTH_MIN) {
        this.error = errorText
        requestFocus()
        return false
    }
    return true
}

fun EditText.validateEmail(errorText: String): Boolean {
    if (!Patterns.EMAIL_ADDRESS.matcher(this.text).matches()) {
        this.error = errorText
        requestFocus()
        return false
    }
    return true
}

const val PASSWORD_LENGTH_MIN = 6