package com.example.credentials

class CredentialsManager {
    private val credentials = mutableMapOf<String, String>(
        Pair("test@te.st", "1234"),
        "test@te.st" to "1234"
    )

    fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email==("test@te.st")
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password==("1234")
    }

    fun login(email: String, password: String): Boolean {
        if (credentials.contains(email)) {
            return credentials.get(email).equals(password)
        }
        return false
    }

    fun register(fullName: String, email: String, phoneNumber: String, password: String) {
        credentials.put(email, password)
    }
}