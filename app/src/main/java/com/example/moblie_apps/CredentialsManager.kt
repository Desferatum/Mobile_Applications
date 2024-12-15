package com.example.moblie_apps

import java.util.Locale

class CredentialsManager {
    companion object {
        val userCredentials = mutableMapOf<String, String>()
    }

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
        return if (userCredentials.contains(normalizedEmail)) {
            "This email is already taken"
        } else {
            userCredentials[normalizedEmail] = password
            "Registration successful"
        }
    }

    fun isEmailRegistered(email: String): Boolean {
        return userCredentials.containsKey(email.trim().lowercase())
    }
}