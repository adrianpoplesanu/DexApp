package com.adrianpoplesanu.dexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signinButton = findViewById<Button>(R.id.signinButton)
        signinButton.setOnClickListener() {
            signinButtonClickEvent(it)
        }
    }

    private fun signinButtonClickEvent(view : View) {
        Log.d("DexApp", "signin button clicked!")
    }
}
