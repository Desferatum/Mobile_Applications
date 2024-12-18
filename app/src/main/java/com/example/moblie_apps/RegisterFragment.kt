package com.example.moblie_apps

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment(credentialsManager: CredentialsManager) : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          val textView = view.findViewById<TextView>(R.id.terms)
          val mText = "By checking the box you agree to our Terms and Conditions"

          val mSpannableString = SpannableString(mText)
          val mMainColor = ForegroundColorSpan(Color.parseColor("#6C63FF"))
          mSpannableString.setSpan(mMainColor, 37, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

          textView.text = mSpannableString

        // Initialize the credentials manager
        val credentialsManager = CredentialsManager()

          val loginLink = view.findViewById<TextView>(R.id.login_link)
          loginLink.setOnClickListener {
              (activity as? UnifyingActivity)?.switchToLogin()
          }

          val emailLayout = view.findViewById<TextInputLayout>(R.id.email_register_Layout)
          val emailEditText = view.findViewById<TextInputEditText>(R.id.email_register_EditText)
          val passwordLayout = view.findViewById<TextInputLayout>(R.id.password_register_Layout)
          val passwordEditText = view.findViewById<TextInputEditText>(R.id.password_register_EditText)
          val nextButtonRegister = view.findViewById<MaterialButton>(R.id.button_next_register)

          nextButtonRegister.setOnClickListener {
              val email = emailEditText.text.toString()
              val password = passwordEditText.text.toString()

              val isEmailValid = validateField(emailLayout, email, "Please enter a valid email") {
                  it.contains("@") && it.contains(".")
              }
              val isPasswordValid =
                  validateField(passwordLayout, password, "Password cannot be empty") {
                      it.length >= 8
                  }

              val result = credentialsManager.register(email, password)

              if (isEmailValid && isPasswordValid) {
                  if (result == "Registration successful") {
                      Log.d("Register", "Registration successful")
                      (activity as? UnifyingActivity)?.switchToLogin()
                  } else if (result == "This email is already taken") {
                      emailLayout.error = "This email is already taken"
                  }
              }
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