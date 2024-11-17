package com.example.loginandregistraionscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.widget.Toast
import com.example.credentials.CredentialsManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class LoginActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val emailEditText: TextInputEditText = findViewById(R.id.emailEditText)
        val passwordEditText: TextInputEditText = findViewById(R.id.passwordEditText)
        val nextButton: MaterialButton = findViewById(R.id.button_next)

        nextButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (!credentialsManager.isEmailValid(email)) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            } else if (!credentialsManager.isPasswordValid(password)) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Validation successful!", Toast.LENGTH_SHORT).show()
            }
        }

        val registerLink : TextView = findViewById(R.id.register_link)
        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}