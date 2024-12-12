package com.example.loginandregistraionscreen

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        val textView = findViewById<TextView>(R.id.terms)
        val mText = "By checking the box you agree to our Terms and Conditions"

        val mSpannableString = SpannableString(mText)
        val mMainColor = ForegroundColorSpan(Color.parseColor("#6C63FF"))
        mSpannableString.setSpan(mMainColor, 37, 57, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = mSpannableString

        val loginLink: TextView = findViewById(R.id.login_link)
        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }

        // Initialize the credentials manager
        val credentialsManager = CredentialsManager()

        val emailLayout: TextInputLayout = findViewById(R.id.email_register_Layout)
        val emailEditText: TextInputEditText = findViewById(R.id.email_register_EditText)
        val passwordLayout: TextInputLayout = findViewById(R.id.password_register_Layout)
        val passwordEditText: TextInputEditText = findViewById(R.id.password_register_EditText)
        val nextButtonRegister: MaterialButton = findViewById(R.id.button_next_register)

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
            val exist = credentialsManager.isEmailRegistered(email)
            if (isEmailValid && isPasswordValid) {
                if (result == "Registration successful" && !exist) {
                    Log.d("Onboarding", "Clicked log in label")
                    val goToLogInIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(goToLogInIntent)
                } else if (result == "This email is already taken" && exist){
                    emailLayout.error = "This email is already taken"
                }
            }
        }
    }

    // Validate field with a custom validation logic
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
            layout.error = null
            layout.isErrorEnabled = false
            true
        }
    }
}
