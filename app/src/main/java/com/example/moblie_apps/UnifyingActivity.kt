package com.example.moblie_apps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class UnifyingActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unifying)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment(credentialsManager))
                .commit()
        }
    }

    fun switchToLogin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment(credentialsManager))
            .addToBackStack(null)
            .commit()
    }

    fun switchToRegister() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RegisterFragment(credentialsManager))
            .addToBackStack(null)
            .commit()
    }

    fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }
}