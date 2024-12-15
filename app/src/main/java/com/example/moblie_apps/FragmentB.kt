package com.example.moblie_apps

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class FragmentB : Fragment(R.layout.fragment_b) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("INSTANCES", "OC Currently on: $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d("INSTANCES", "OC Currently on: $this")
    }
}