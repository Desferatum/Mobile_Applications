package com.example.credentials

class CredentialsManager {
    fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email==("test@te.st")
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password==("1234")
    }
}