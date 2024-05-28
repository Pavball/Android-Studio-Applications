package com.example.listamatijevic

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ExerciseImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_image)

        // Dobivanje podataka o odabranoj slici iz intenta
        val imageResourceId = intent.getIntExtra("EXERCISE_IMAGE", -1)
        if (imageResourceId != -1) {
            // Pronalaženje ImageView-a pomoću findViewById
            val imageViewExerciseFull = findViewById<ImageView>(R.id.imageViewExerciseFull)

            // Postavljanje slike u ImageView-u
            imageViewExerciseFull.setImageResource(imageResourceId)



        }
    }
}
