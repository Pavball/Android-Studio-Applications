package com.example.listamatijevic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExerciseListActivity : AppCompatActivity(), ExerciseAdapter.OnExerciseClickListener {

    private val exerciseList = Exercise.ExerciseData.exercises
    private lateinit var recyclerViewExercises: RecyclerView
    private val myBroadcastReceiver = MyBroadcastReceiver()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)
        println("-------------------POKRENUTA APLIKACIJA-----------------------------")

        // Registracija BroadcastReceiver-a
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(myBroadcastReceiver, filter)

        recyclerViewExercises = findViewById(R.id.recyclerViewExercises)

        val adapter = ExerciseAdapter(exerciseList, this)
        recyclerViewExercises.adapter = adapter
        recyclerViewExercises.layoutManager = LinearLayoutManager(this)
        recyclerViewExercises.setHasFixedSize(true)

        //Dohvat Res Stringova, te inicijaliziranje istih
        val nameResIds = listOf(R.string.deadliftTitle, R.string.squatTitle, R.string.benchTitle, R.string.plankTitle, R.string.latPullTitle, R.string.pullUpTitle, R.string.barbellTitle)
        val descriptionResIds = listOf(R.string.deadliftDesc, R.string.squatDesc, R.string.benchDesc, R.string.plankDesc, R.string.latPullDesc, R.string.pullUpDesc, R.string.barbellDesc)
        val techniqueResIds = listOf(R.string.deadliftTech, R.string.squatTech, R.string.benchTech, R.string.plankTech, R.string.latPullTech, R.string.pullUpTech, R.string.barbellTech)
        val websiteResIds = listOf(R.string.deadliftWeb, R.string.squatWeb, R.string.benchWeb, R.string.plankWeb, R.string.latWeb, R.string.pullWeb, R.string.barbellWeb)
        Exercise.ExerciseData.initialize(this, nameResIds, descriptionResIds, techniqueResIds, websiteResIds)

        val intent = Intent("com.example.listamatijevic.MY_CUSTOM_ACTION")
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Odregistracija BroadcastReceiver-a
        unregisterReceiver(myBroadcastReceiver)
    }

    override fun onExerciseClick(position: Int) {
        val intent = Intent(this, ExerciseDetailActivity::class.java)
        intent.putExtra("EXERCISE_POSITION", position)
        startActivity(intent)
    }




}

