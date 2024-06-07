package com.example.listamatijevic.ui.activities

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.listamatijevic.R

class ExerciseImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_image)

        // Dobivanje podataka o odabranoj slici iz intenta
        val exerciseImageUrl = intent.getStringExtra("EXERCISE_IMAGE_URL")

            // Pronalaženje ImageView-a pomoću findViewById
            val imageViewExerciseFull = findViewById<ImageView>(R.id.imageViewExerciseFull)

            // Postavljanje slike u ImageView-u
            imageViewExerciseFull.setImageURI(Uri.parse(exerciseImageUrl))


    }
}
