package com.example.listamatijevic.controller

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listamatijevic.model.Exercise
import com.example.listamatijevic.R
import com.facebook.drawee.view.SimpleDraweeView

class ExerciseAdapter(private var exerciseList: List<Exercise>, private val listener: OnExerciseClickListener) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem = exerciseList[position]
        holder.textViewExerciseName.text = currentItem.name

        // Load image using Fresco from the provided URL
        val uri = Uri.parse(currentItem.imageUrl)
        holder.imageViewExercise.setImageURI(uri)

        holder.itemView.setOnClickListener {
            listener.onExerciseClick(position)
        }
    }


    override fun getItemCount() = exerciseList.size

    fun setExercises(exercises: List<Exercise>) {
        this.exerciseList = exercises
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewExerciseName: TextView = itemView.findViewById(R.id.textViewExerciseName)
        val imageViewExercise: SimpleDraweeView = itemView.findViewById(R.id.imageViewExercise)
    }

    interface OnExerciseClickListener {
        fun onExerciseClick(position: Int)
    }
}