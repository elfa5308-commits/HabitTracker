
package com.example.habittracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitAdapter(
    private val onProgressChange: (Int, Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    private val habitList = mutableListOf<Habit>()

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtIcon: TextView = itemView.findViewById(R.id.txtIcon)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtDescription: TextView = itemView.findViewById(R.id.txtDescription)
        val txtProgress: TextView = itemView.findViewById(R.id.txtProgress)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val txtStatus: TextView = itemView.findViewById(R.id.txtStatus)
        val btnMinus: Button = itemView.findViewById(R.id.btnMinus)
        val btnPlus: Button = itemView.findViewById(R.id.btnPlus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)

        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        holder.txtIcon.text = habit.icon
        holder.txtName.text = habit.name
        holder.txtDescription.text = habit.description
        holder.txtProgress.text = "${habit.progress} / ${habit.goal} ${habit.unit}"

        holder.progressBar.max = habit.goal
        holder.progressBar.progress = habit.progress

        if (habit.progress >= habit.goal) {
            holder.txtStatus.text = "Completed"
            holder.txtStatus.setTextColor(Color.WHITE)
            holder.txtStatus.setBackgroundResource(R.drawable.bg_status_completed)
        } else {
            holder.txtStatus.text = "In Progress"
            holder.txtStatus.setTextColor(Color.DKGRAY)
            holder.txtStatus.setBackgroundResource(R.drawable.bg_status)
        }

        holder.btnPlus.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onProgressChange(currentPosition, 1)
            }
        }

        holder.btnMinus.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                onProgressChange(currentPosition, -1)
            }
        }
    }

    override fun getItemCount(): Int = habitList.size

    fun updateHabits(habits: List<Habit>) {
        habitList.clear()
        habitList.addAll(habits)
        notifyDataSetChanged()
    }
}
