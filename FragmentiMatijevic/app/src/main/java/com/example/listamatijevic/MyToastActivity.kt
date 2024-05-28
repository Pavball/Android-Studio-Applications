package com.example.listamatijevic

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MyToastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast)

        // Prikazi Toast poruku
        Toast.makeText(this, "Hello from BroadcastReceiver!", Toast.LENGTH_SHORT).show()

    }
}