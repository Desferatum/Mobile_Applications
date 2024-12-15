package com.example.moblie_apps

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.replace
import com.google.android.material.button.MaterialButton

class SampleActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sample)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val switchButton: MaterialButton = findViewById(R.id.switch_button)
        switchButton.setOnClickListener {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container_view)

            supportFragmentManager.commit {
                if (currentFragment is FragmentA) {
                    replace<FragmentB>(R.id.fragment_container_view)
                    addToBackStack(null)
                } else {
                    supportFragmentManager.popBackStack()
                }
            }
        }
    }


}