package com.example.moblie_apps

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment(credentialsManager: CredentialsManager) : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val credentialsManager = CredentialsManager()

        val emailLayout = view.findViewById<TextInputLayout>(R.id.enter_email)
        val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordLayout = view.findViewById<TextInputLayout>(R.id.enter_password)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)
        val nextButtonLogin = view.findViewById<MaterialButton>(R.id.button_next_login)

        nextButtonLogin.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

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
                if (credentialsManager.isEmailRegistered(email)) {
                    if (credentialsManager.isPasswordCorrect(email, password)) {
                        Log.d("Login", "Login successful!")
                        (activity as? UnifyingActivity)?.navigateToFragment(FragmentFinal())
                    } else {
                        passwordLayout.error = "Wrong password. Try again"
                    }
                } else {
                    emailLayout.error = "Please enter a valid email address"
                }
            }
        }

        val registerLink = view.findViewById<TextView>(R.id.register_link)
        registerLink.setOnClickListener {
            (activity as? UnifyingActivity)?.switchToRegister()
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