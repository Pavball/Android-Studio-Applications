package com.example.listamatijevic.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.listamatijevic.R
import com.facebook.drawee.view.SimpleDraweeView

class ExerciseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_detail)

        // Retrieve exercise details from intent extras
        val exerciseName = intent.getStringExtra("EXERCISE_NAME")
        val exerciseDescription = intent.getStringExtra("EXERCISE_DESCRIPTION")
        val exerciseTechnique = intent.getStringExtra("EXERCISE_TECHNIQUE")
        val exerciseImageUrl = intent.getStringExtra("EXERCISE_IMAGE_URL")
        val exerciseWebsiteUrl = intent.getStringExtra("EXERCISE_WEBSITE_URL")

        // Initialize UI elements
        val textViewExerciseName = findViewById<TextView>(R.id.textViewExerciseName)
        val textViewExerciseDescription = findViewById<TextView>(R.id.textViewExerciseDescription)
        val textViewExerciseTechnique = findViewById<TextView>(R.id.textViewExerciseTechnique)
        val imageViewExercise = findViewById<SimpleDraweeView>(R.id.imageViewExercise)
        val webButton = findViewById<Button>(R.id.buttonOpenWebsite)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        // Populate UI with exercise details
        textViewExerciseName.text = exerciseName
        textViewExerciseDescription.text = exerciseDescription
        textViewExerciseTechnique.text = exerciseTechnique
        imageViewExercise.setImageURI(Uri.parse(exerciseImageUrl))

        // OnClickListener to open the image in full size
        imageViewExercise.setOnClickListener {
            val intent = Intent(this, ExerciseImageActivity::class.java)
            intent.putExtra("EXERCISE_IMAGE_URL", exerciseImageUrl)
            startActivity(intent)
        }

        // OnClickListener to open exercise website
        webButton.setOnClickListener {
            val openWebsiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(exerciseWebsiteUrl))
            startActivity(openWebsiteIntent)
        }

        // Set toolbar
        setSupportActionBar(toolbar)

        // Start animation
        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up_down)
        webButton.startAnimation(slideInAnimation)
    }
}
