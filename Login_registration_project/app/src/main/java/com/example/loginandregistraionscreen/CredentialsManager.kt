package com.example.loginandregistraionscreen

import java.util.Locale

class CredentialsManager {
    private val registeredEmails = mutableSetOf("test@example.com", "user@example.com")
    private val userCredentials = mutableMapOf<String, String>()

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun login(email: String, password: String): Boolean {
        val normalizedEmail = email.trim().lowercase()
        return userCredentials[normalizedEmail] == password
    }

    fun register(email: String, password: String): String {
        val normalizedEmail = email.trim().lowercase()
        return if (registeredEmails.contains(normalizedEmail)) {
            "This email is already taken"
        } else {
            registeredEmails.add(normalizedEmail)
            userCredentials[normalizedEmail] = password
            "Registration successful"
        }
    }

    fun isUserRegistered(email: String): Boolean {
        val normalizedEmail = email.lowercase(Locale.getDefault())
        return userCredentials.containsKey(normalizedEmail)
    }

    fun isEmailRegistered(email: String): Boolean {
        return userCredentials.containsKey(email.trim().lowercase())
    }
}



