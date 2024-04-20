package com.example.listamatijevic


import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes


class Exercise( // Get metode za pristup atributima vježbe //
    var name: String, var description: String, var technique: String,
    // Resource ID slike
    var imageResourceId: Int,
    var websiteUrl: String // Dodajte novi atribut za URL vježbe
) {

    fun updateStrings(context: Context, @StringRes nameResId: Int, @StringRes descriptionResId: Int, @StringRes techniqueResId: Int, @StringRes websiteResId: Int) {
        name = context.getString(nameResId)
        description = context.getString(descriptionResId)
        technique = context.getString(techniqueResId)
        websiteUrl = context.getString(websiteResId)
    }

    object ExerciseData {

        val exercises = listOf(
            Exercise("", "", "", R.drawable.deadlift, ""),
            Exercise("", "", "", R.drawable.squat, ""),
            Exercise("", "", "", R.drawable.bench_press, ""),
            Exercise("", "", "", R.drawable.plank, ""),
            Exercise("", "", "", R.drawable.lat_pulldown, ""),
            Exercise("", "", "", R.drawable.pull_up, ""),
            Exercise("", "", "", R.drawable.barbell_curl, "")

        )

        fun initialize(context: Context, nameResIds: List<Int>, descriptionResIds: List<Int>, techniqueResIds: List<Int>, websiteResIds: List<Int>) {
            for ((index, exercise) in exercises.withIndex()) {
                val nameResId = nameResIds.getOrElse(index) { -1 }
                val descriptionResId = descriptionResIds.getOrElse(index) { -1 }
                val techniqueResId = techniqueResIds.getOrElse(index) { -1 }
                val websiteResIds = websiteResIds.getOrElse(index) { -1 }
                exercise.updateStrings(context, nameResId, descriptionResId, techniqueResId, websiteResIds)
            }
        }
    }




}