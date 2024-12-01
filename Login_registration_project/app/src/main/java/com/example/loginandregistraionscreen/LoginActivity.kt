package com.example.loginandregistraionscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import com.example.credentials.CredentialsManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager()

    private val emailLayout: TextInputLayout
        get() = findViewById(R.id.enter_email)
    private val emailEditText: TextInputEditText
        get() = findViewById(R.id.emailEditText)
    private val passwordLayout: TextInputLayout
        get() = findViewById(R.id.enter_password)
    private val passwordEditText: TextInputEditText
        get() = findViewById(R.id.passwordEditText)
    private val nextButtonLogin: MaterialButton
        get() = findViewById(R.id.button_next_login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

            nextButtonLogin.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                val isEmailValid = validateField(
                    layout = emailLayout,
                    value = email,
                errorMessage = "Invalid email input"
                ) { credentialsManager.isEmailValid(it) }

                val isPasswordValid = validateField(
                    layout = passwordLayout,
                    value = password,
                    errorMessage = "Wrong or empty password"
                ) { credentialsManager.isPasswordValid(it) }

                if (isEmailValid && isPasswordValid) {
                    val intent = Intent(this, EmptySuccessActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

        val registerLink: TextView = findViewById(R.id.register_link)
        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }

    }
    private fun validateField(
        layout: TextInputLayout,
        value: String,
        errorMessage: String,
        validationLogic: (String) -> Boolean
    ): Boolean {
        return if (!validationLogic(value)) {
            layout.error = errorMessage
            layout.isErrorEnabled = true
            false
        } else {
            layout.isErrorEnabled = false
            layout.error = null
            true
        }
    }
}