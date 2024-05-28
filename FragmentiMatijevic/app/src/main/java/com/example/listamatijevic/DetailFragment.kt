import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.listamatijevic.AppDatabase
import com.example.listamatijevic.Exercise
import com.example.listamatijevic.ExerciseImageActivity
import com.example.listamatijevic.ExerciseRepository
import com.example.listamatijevic.ExerciseViewModel
import com.example.listamatijevic.ExerciseViewModelFactory
import com.example.listamatijevic.R

class DetailFragment : Fragment() {

    private var exerciseId: Long = -1
    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            exerciseId = it.getLong("EXERCISE_ID", -1L)  // Retrieve exercise ID as Long
        }

        if (exerciseId == -1L) {
            Log.e("DetailFragment", "Invalid exercise ID passed to fragment")
        }

        // Initialize ViewModel with factory if needed
        val database = AppDatabase.getDatabase(requireContext())
        val repository = ExerciseRepository(database.exerciseDao())
        val factory = ExerciseViewModelFactory(repository)
        exerciseViewModel = ViewModelProvider(this, factory).get(ExerciseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        if (exerciseId != -1L) {
            exerciseViewModel.getExerciseById(exerciseId).observe(viewLifecycleOwner) { exercise ->
                exercise?.let {
                    // Populate UI with exercise data
                    populateExerciseData(view, exercise)
                } ?: run {
                    Log.e("DetailFragment", "Exercise not found for ID: $exerciseId")
                }
            }
        } else {
            Log.e("DetailFragment", "Invalid exercise ID")
        }

        return view
    }

    private fun populateExerciseData(view: View, exercise: Exercise) {
        val textViewExerciseName = view.findViewById<TextView>(R.id.textViewExerciseName)
        val textViewExerciseDescription = view.findViewById<TextView>(R.id.textViewExerciseDescription)
        val textViewExerciseTechnique = view.findViewById<TextView>(R.id.textViewExerciseTechnique)
        val imageViewExercise = view.findViewById<ImageView>(R.id.imageViewExercise)
        val webBtn = view.findViewById<Button>(R.id.buttonOpenWebsite)

        textViewExerciseName.text = exercise.name
        textViewExerciseDescription.text = exercise.description
        textViewExerciseTechnique.text = exercise.technique
        imageViewExercise.setImageResource(exercise.imageResourceId)

        imageViewExercise.setOnClickListener {
            val intent = Intent(activity, ExerciseImageActivity::class.java)
            intent.putExtra("EXERCISE_IMAGE", exercise.imageResourceId)
            startActivity(intent)
        }

        webBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(exercise.websiteUrl))
            startActivity(intent)
        }
    }
}
