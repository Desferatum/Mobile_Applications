package com.example.loginandregistraionscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.credentials.CredentialsManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val emailEditText: TextInputEditText = findViewById(R.id.emailEditText)
        val emailTextLayout: TextInputLayout = findViewById(R.id.enter_email)

        val passwordEditText: TextInputEditText = findViewById(R.id.passwordEditText)
        val passwordTextLayout: TextInputLayout = findViewById(R.id.enter_password)

        val nextButton: MaterialButton = findViewById(R.id.button_next)

        emailEditText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_NEXT -> {
                    val email = emailEditText.text.toString()

                    if (!credentialsManager.isEmailValid(email)) {
                        emailTextLayout.isErrorEnabled = true
                        emailTextLayout.error = "Invalid email address"
                    } else {
                        emailTextLayout.isErrorEnabled = false
                    }
                    true
                }
                else -> false
            }
        }

        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    val password = passwordEditText.text.toString()

                    if (!credentialsManager.isPasswordValid(password)) {
                        passwordTextLayout.isErrorEnabled = true
                        passwordTextLayout.error = "Password cannot be empty"
                    } else {
                        passwordTextLayout.isErrorEnabled = false
                    }
                    true
                }
                else -> false
            }
        }

        nextButton.setOnClickListener {
            val email = emailEditText.text.toString()
            var valid = true

            if (!credentialsManager.isEmailValid(email)) {
                emailTextLayout.isErrorEnabled = true
                emailTextLayout.error = "Invalid email address"
                valid = false
            } else {
                emailTextLayout.isErrorEnabled = false
            }

            val password = passwordEditText.text.toString()

            if (!credentialsManager.isPasswordValid(password)) {
                passwordTextLayout.isErrorEnabled = true
                passwordTextLayout.error = "Password cannot be empty"
                valid = false
            } else {
                passwordTextLayout.isErrorEnabled = false
            }

            if (valid) {
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