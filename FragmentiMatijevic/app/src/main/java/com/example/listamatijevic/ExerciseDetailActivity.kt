package com.example.listamatijevic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.observe
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class ExerciseDetailActivity : AppCompatActivity() {

    private lateinit var exerciseDao: ExerciseDao
    private lateinit var exerciseViewModel: ExerciseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_detail)

        // Initialize ViewModel
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ExerciseRepository(database.exerciseDao())
        exerciseViewModel = ViewModelProvider(this, ExerciseViewModelFactory(repository)).get(ExerciseViewModel::class.java)

        // Retrieve exercise ID from intent extras
        val exerciseId = intent.getLongExtra("EXERCISE_ID", -1)

        // Check if the exercise ID is valid
        if (exerciseId != -1L) {
            // Load exercise details using the ID
            exerciseViewModel.getExerciseById(exerciseId).observe(this) { exercise ->
                exercise?.let {
                    // Update UI with exercise details
                    initializeViews(exercise)
                }
            }
        }
    }

    private fun initializeViews(exercise: Exercise) {
        // Pronalaženje XML elemenata pomoću findViewById
        val textViewExerciseName = findViewById<TextView>(R.id.textViewExerciseName)
        val textViewExerciseDescription = findViewById<TextView>(R.id.textViewExerciseDescription)
        val textViewExerciseTechnique = findViewById<TextView>(R.id.textViewExerciseTechnique)
        val imageViewExercise = findViewById<ImageView>(R.id.imageViewExercise)
        val webButton = findViewById<Button>(R.id.buttonOpenWebsite)

        // Postavljanje teksta u TextView-ima
        textViewExerciseName.text = exercise.name
        textViewExerciseDescription.text = exercise.description
        textViewExerciseTechnique.text = exercise.technique

        // Postavljanje slike u ImageView-u
        imageViewExercise.setImageResource(exercise.imageResourceId)

        // Dodavanje onClickListener-a za otvaranje slike u punoj veličini
        imageViewExercise.setOnClickListener {
            val intent = Intent(this, ExerciseImageActivity::class.java)
            intent.putExtra("EXERCISE_IMAGE", exercise.imageResourceId)
            startActivity(intent)
        }

        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up_down)
        webButton.startAnimation(slideInAnimation)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    fun openWebsite(view: View) {
        val exerciseId = intent.getLongExtra("EXERCISE_ID", -1)
        if (exerciseId != -1L) {
            exerciseViewModel.getExerciseById(exerciseId).observe(this) { exercise ->
                exercise?.let {
                    val websiteUrl = exercise.websiteUrl
                    val openWebsiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                    startActivity(openWebsiteIntent)
                }
            }
        }
    }



}
