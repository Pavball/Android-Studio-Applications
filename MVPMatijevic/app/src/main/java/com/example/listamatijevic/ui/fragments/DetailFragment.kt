package com.example.listamatijevic.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.listamatijevic.R
import com.facebook.drawee.view.SimpleDraweeView

class DetailFragment : Fragment() {

    private lateinit var exerciseName: String
    private lateinit var exerciseDescription: String
    private lateinit var exerciseTechnique: String
    private lateinit var exerciseImageUrl: String
    private lateinit var exerciseWebsiteUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            exerciseName = it.getString("EXERCISE_NAME", "")
            exerciseDescription = it.getString("EXERCISE_DESCRIPTION", "")
            exerciseTechnique = it.getString("EXERCISE_TECHNIQUE", "")
            exerciseImageUrl = it.getString("EXERCISE_IMAGE_URL", "")
            exerciseWebsiteUrl = it.getString("EXERCISE_WEBSITE_URL", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        // Populate UI with exercise data
        populateExerciseData(view)

        return view
    }

    private fun populateExerciseData(view: View) {
        val textViewExerciseName = view.findViewById<TextView>(R.id.textViewExerciseName)
        val textViewExerciseDescription = view.findViewById<TextView>(R.id.textViewExerciseDescription)
        val textViewExerciseTechnique = view.findViewById<TextView>(R.id.textViewExerciseTechnique)
        val imageViewExercise = view.findViewById<SimpleDraweeView>(R.id.imageViewExercise)
        val webBtn = view.findViewById<Button>(R.id.buttonOpenWebsite)

        // Set exercise details in UI elements
        textViewExerciseName.text = exerciseName
        textViewExerciseDescription.text = exerciseDescription
        textViewExerciseTechnique.text = exerciseTechnique
        imageViewExercise.setImageURI(Uri.parse(exerciseImageUrl))

        // OnClickListener to open the image in full size
        /*imageViewExercise.setOnClickListener {
            val intent = Intent(this, ExerciseImageActivity::class.java)
            intent.putExtra("EXERCISE_IMAGE_URL", exerciseImageUrl)
            startActivity(intent)
        }*/

        // OnClickListener to open the exercise website
        webBtn.setOnClickListener {
            val openWebsiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(exerciseWebsiteUrl))
            startActivity(openWebsiteIntent)
        }
    }
}
