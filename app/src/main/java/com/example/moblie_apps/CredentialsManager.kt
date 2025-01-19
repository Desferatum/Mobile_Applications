package com.example.moblie_apps

import android.content.Context
import java.util.Locale

class CredentialsManager {
    companion object {
        fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
            val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(KEY_LOGGED_IN, isLoggedIn)
            editor.apply()
        }

        fun isLoggedIn(context: Context): Boolean {
            val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(KEY_LOGGED_IN, false)
        }

        private const val PREFS_NAME = "user_prefs"
        private const val KEY_LOGGED_IN = "logged_in"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
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

    fun isPasswordCorrect(email: String, password: String): Boolean {
        val normalizedEmail = email.lowercase()
        return userCredentials[normalizedEmail] == password
    }
}