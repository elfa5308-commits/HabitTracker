
package com.example.habittracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val fileHelper = FileHelper(application)

    private val _habits = MutableLiveData<List<Habit>>(
        fileHelper.loadHabits() ?: mutableListOf(
            Habit(
                name = "Minum Air Putih",
                description = "Tetap terhidrasi sepanjang hari",
                goal = 8,
                progress = 4,
                icon = "💧",
                unit = "gelas"
            ),
            Habit(
                name = "Jalan Kaki",
                description = "Jalan kaki setiap hari",
                goal = 10000,
                progress = 3500,
                icon = "🚶",
                unit = "langkah"
            ),
            Habit(
                name = "Berdoa Sebelum Makan",
                description = "Berdoa sebelum makan",
                goal = 3,
                progress = 1,
                icon = "🙏",
                unit = "kali"
            ),
            Habit(
                name = "Tidur Cukup",
                description = "Tidur cukup setiap hari",
                goal = 8,
                progress = 8,
                icon = "😴",
                unit = "jam"
            )
        )
    )

    val habits: LiveData<List<Habit>> = _habits

    fun addHabit(habit: Habit) {
        val updated = _habits.value.orEmpty().toMutableList()
        updated.add(habit)
        save(updated)
    }

    fun updateProgress(index: Int, amount: Int) {
        val updated = _habits.value.orEmpty().map { it.copy() }

        if (index !in updated.indices) return

        val habit = updated[index]
        habit.progress = (habit.progress + amount).coerceIn(0, habit.goal)

        save(updated)
    }

    private fun save(habits: List<Habit>) {
        fileHelper.saveHabits(habits)
        _habits.value = habits
    }
}
