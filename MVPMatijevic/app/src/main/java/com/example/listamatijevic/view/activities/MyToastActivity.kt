package com.example.listamatijevic.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listamatijevic.R


class MyToastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast)

        // Prikazi Toast poruku
        Toast.makeText(this, "Hello from BroadcastReceiver!", Toast.LENGTH_SHORT).show()

    }
}