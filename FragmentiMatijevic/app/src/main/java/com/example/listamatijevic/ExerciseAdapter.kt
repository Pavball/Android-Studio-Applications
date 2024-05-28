package com.example.listamatijevic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private var exerciseList: List<Exercise>, private val listener: OnExerciseClickListener) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem = exerciseList[position]
        holder.textViewExerciseName.text = currentItem.name
        holder.imageViewExercise.setImageResource(currentItem.imageResourceId)

        holder.itemView.setOnClickListener {
            listener.onExerciseClick(position)
        }
    }

    // Define a method to get the exercise ID at a given position
    override fun getItemId(position: Int): Long {
        return exerciseList[position].id.toLong()
    }

    override fun getItemCount() = exerciseList.size

    fun setExercises(exercises: List<Exercise>) {
        this.exerciseList = exercises
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewExerciseName: TextView = itemView.findViewById(R.id.textViewExerciseName)
        val imageViewExercise: ImageView = itemView.findViewById(R.id.imageViewExercise)
    }

    interface OnExerciseClickListener {
        fun onExerciseClick(position: Int)
    }
}
