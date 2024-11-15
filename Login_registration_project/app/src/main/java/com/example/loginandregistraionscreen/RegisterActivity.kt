package com.example.loginandregistraionscreen

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView

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

        val loginLink : TextView = findViewById(R.id.login_link)
        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}