package com.example.androidpracticumcustomview

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonToXML = findViewById<AppCompatButton>(R.id.button_xml)
        buttonToXML.setOnClickListener {
            this.startActivity(Intent(this, TraditionalActivity::class.java))
        }

        val buttonToCompose = findViewById<AppCompatButton>(R.id.button_compose)
        buttonToCompose.setOnClickListener {
            this.startActivity(Intent(this, ComposeActivity::class.java))
        }
    }
}