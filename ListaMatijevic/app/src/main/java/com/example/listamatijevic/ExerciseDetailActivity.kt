package com.example.listamatijevic

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ExerciseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_detail)

        // Dobivanje podataka o odabranoj vježbi iz intenta
        val position = intent.getIntExtra("EXERCISE_POSITION", -1)
        if (position != -1) {
            val exercise = Exercise.ExerciseData.exercises[position]

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

    }

    fun openWebsite(view: View) {
        val exercisePosition = intent.getIntExtra("EXERCISE_POSITION", -1)
        if (exercisePosition != -1) {
            val exercise = Exercise.ExerciseData.exercises[exercisePosition]
            val websiteUrl = exercise.websiteUrl
            val openWebsiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(openWebsiteIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.moj_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                showShareDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun showShareDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Share Content")
        builder.setMessage("Are you sure you want to share this content?")
        builder.setPositiveButton("Share") { _, _ ->
            // Pozovi funkciju za slanje broadcasta
            sendCustomBroadcast()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }


    fun sendCustomBroadcast() {
        val intent = Intent("com.example.listamatijevic.CUSTOM_ACTION")
        sendBroadcast(intent)
        println("Broadcast sent")
    }


}
