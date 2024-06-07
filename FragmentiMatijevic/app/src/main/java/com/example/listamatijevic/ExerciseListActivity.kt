package com.example.listamatijevic

import DetailFragment
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class ExerciseListActivity : AppCompatActivity(), ExerciseAdapter.OnExerciseClickListener {

    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var recyclerViewExercises: RecyclerView
    private lateinit var adapter: ExerciseAdapter
    private val myBroadcastReceiver = MyBroadcastReceiver()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_exercise_list)

        FirebaseApp.initializeApp(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.barbellTitle)
            val descriptionText = getString(R.string.barbellDesc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("fcm_default_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        println("-------------------POKRENUTA APLIKACIJA-----------------------------")

        // Register BroadcastReceiver
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(myBroadcastReceiver, filter)

        // Initialize RecyclerView
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises)
        recyclerViewExercises.layoutManager = LinearLayoutManager(this)
        recyclerViewExercises.setHasFixedSize(true)

        // Set up ViewModel and Adapter
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ExerciseRepository(database.exerciseDao())
        exerciseViewModel = ViewModelProvider(this, ExerciseViewModelFactory(repository)).get(ExerciseViewModel::class.java)



        adapter = ExerciseAdapter(emptyList(), this)
        recyclerViewExercises.adapter = adapter

        // Observe the exercise data
        exerciseViewModel.allExercises.observe(this, { exercises ->
            exercises?.let {
                adapter.setExercises(it)
            }
        })



        // Insert initial data if needed
        /*if (exerciseViewModel.allExercises.value.isNullOrEmpty()) {
            val initialExercises = listOf(
                Exercise(name = getString(R.string.deadliftTitle), description = getString(R.string.deadliftDesc), technique = getString(R.string.deadliftTech), imageResourceId = R.drawable.deadlift, websiteUrl = getString(R.string.deadliftWeb)),
                Exercise(name = getString(R.string.squatTitle), description = getString(R.string.squatDesc), technique = getString(R.string.squatTech), imageResourceId = R.drawable.squat, websiteUrl = getString(R.string.squatWeb)),
                Exercise(name = getString(R.string.benchTitle), description = getString(R.string.barbellDesc), technique = getString(R.string.benchTech), imageResourceId = R.drawable.bench_press, websiteUrl = getString(R.string.benchWeb)),
                Exercise(name = getString(R.string.barbellTitle), description = getString(R.string.barbellDesc), technique = getString(R.string.barbellTech), imageResourceId = R.drawable.barbell_curl, websiteUrl = getString(R.string.barbellWeb)),
                Exercise(name = getString(R.string.latPullTitle), description = getString(R.string.latPullDesc), technique = getString(R.string.latPullTech), imageResourceId = R.drawable.lat_pulldown, websiteUrl = getString(R.string.latWeb)),
                Exercise(name = getString(R.string.plankTitle), description = getString(R.string.plankDesc), technique = getString(R.string.plankTech), imageResourceId = R.drawable.plank, websiteUrl = getString(R.string.plankWeb)),
                Exercise(name = getString(R.string.pullUpTitle), description = getString(R.string.pullUpDesc), technique = getString(R.string.pullUpTech), imageResourceId = R.drawable.pull_up, websiteUrl = getString(R.string.pullWeb))
            )

            exerciseViewModel.addExercises(initialExercises)
        }*/

        // Fetch FCM token
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d(ContentValues.TAG, "FCM registration token: $token")
                Toast.makeText(this, "FCM registration token: $token", Toast.LENGTH_SHORT).show()
            }

        /*var clearButton: Button
        clearButton = findViewById(R.id.buttonDeleteDB)

        clearButton.setOnClickListener {
            exerciseViewModel.deleteAllExercises()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadcastReceiver)
    }

    override fun onExerciseClick(position: Int) {
        // Get the exercise ID from the adapter
        val exerciseId = adapter.getItemId(position)

        val orientation = getResources().configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Start ExerciseDetailActivity and pass the exercise ID
            val intent = Intent(this, ExerciseDetailActivity::class.java)
            intent.putExtra("EXERCISE_ID", exerciseId)
            startActivity(intent)
        } else {
            val fragment = DetailFragment()
            fragment.arguments = Bundle().apply {
                putLong("EXERCISE_ID", exerciseId)  // Pass the exercise ID as Long
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentID, fragment)
                .commit()
        }
    }




}
