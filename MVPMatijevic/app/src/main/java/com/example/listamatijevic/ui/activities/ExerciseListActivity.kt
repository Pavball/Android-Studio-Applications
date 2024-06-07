package com.example.listamatijevic.ui.activities

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listamatijevic.ui.fragments.DetailFragment
import com.example.listamatijevic.model.Exercise
import com.example.listamatijevic.controller.ExerciseAdapter
import com.example.listamatijevic.controller.MyWidgetProvider
import com.example.listamatijevic.R
import com.example.listamatijevic.retrofit.RetrofitInstance
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.FirebaseApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseListActivity : AppCompatActivity(), ExerciseAdapter.OnExerciseClickListener {

    private lateinit var recyclerViewExercises: RecyclerView
    private lateinit var adapter: ExerciseAdapter
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var exercises: List<Exercise>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ExerciseListActivity", "-------------------------------------------------POKRENUTA APLIKACIJA--------------------------------------------------")
        setContentView(R.layout.activity_exercise_list)

        FirebaseApp.initializeApp(this)
        Fresco.initialize(this)

        // Initialize ViewModel and Adapter
        adapter = ExerciseAdapter(emptyList(), this)

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

        // Initialize RecyclerView
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises)
        recyclerViewExercises.layoutManager = LinearLayoutManager(this)
        recyclerViewExercises.setHasFixedSize(true)

        // Initialize Retrofit API service
        val apiService = RetrofitInstance.api

        // Make API request to fetch exercise data
        apiService.getExercises().enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(call: Call<List<Exercise>>, response: Response<List<Exercise>>) {
                if (response.isSuccessful) {
                    exercises = response.body()!!
                    exercises?.let {
                        adapter.setExercises(it)
                        Log.e("ExerciseListActivity", "Acquired data: ${exercises[exercises.lastIndex].description}")

                        // Update the widget with the latest exercise data

                    }
                } else {
                    Log.e("ExerciseListActivity", "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                Log.e("ExerciseListActivity", "Failed to fetch data: ${t.message}")
            }
        })

        recyclerViewExercises.adapter = adapter

        mediaPlayer = MediaPlayer.create(this, R.raw.background)
        mediaPlayer.start()
    }

    private fun updateWidgetWithLatestExercise() {
        val latestExercise = exercises[exercises.lastIndex]

        Log.e("LATEST EXERCISE: ", latestExercise.name)

        val intent = Intent(this, MyWidgetProvider::class.java).apply {
            action = MyWidgetProvider.UPDATE_WIDGET_ACTION
            putExtra(MyWidgetProvider.EXTRA_WIDGET_TEXT, latestExercise.name)
            putExtra(MyWidgetProvider.EXTRA_WIDGET_IMAGE_URL, latestExercise.imageUrl)
        }
        sendBroadcast(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onExerciseClick(position: Int) {
        // Get the exercise ID from the adapter
        val exerciseId = position

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Start ExerciseDetailActivity and pass the exercise ID

            updateWidgetWithLatestExercise()

            val intent = Intent(this, ExerciseDetailActivity::class.java)
            intent.putExtra("EXERCISE_NAME", exercises[exerciseId].name)
            intent.putExtra("EXERCISE_DESCRIPTION", exercises[exerciseId].description)
            intent.putExtra("EXERCISE_TECHNIQUE", exercises[exerciseId].technique)
            intent.putExtra("EXERCISE_IMAGE_URL", exercises[exerciseId].imageUrl)
            intent.putExtra("EXERCISE_WEBSITE_URL", exercises[exerciseId].websiteUrl)
            startActivity(intent)
        } else {
            val fragment = DetailFragment()
            fragment.arguments = Bundle().apply {
                putString("EXERCISE_NAME", exercises[exerciseId].name)
                putString("EXERCISE_DESCRIPTION", exercises[exerciseId].description)
                putString("EXERCISE_TECHNIQUE", exercises[exerciseId].technique)
                putString("EXERCISE_IMAGE_URL", exercises[exerciseId].imageUrl)
                putString("EXERCISE_WEBSITE_URL", exercises[exerciseId].websiteUrl)
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentID, fragment)
                .commit()
        }
    }

    override fun onPause() {
        if (this.isFinishing) { //basically BACK was pressed from this activity
            mediaPlayer.stop()
        }
        val context = applicationContext
        val am = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val taskInfo = am.getRunningTasks(1)
        if (!taskInfo.isEmpty()) {
            val topActivity = taskInfo[0].topActivity
            if (topActivity!!.packageName != context.packageName) {
                mediaPlayer.stop()
            }
        }
        super.onPause()
    }

}
