
package com.example.habittracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class FileHelper(context: Context) {

    private val file = File(context.filesDir, "habits.json")
    private val gson = Gson()

    fun saveHabits(habits: List<Habit>) {
        file.writeText(gson.toJson(habits))
    }

    fun loadHabits(): MutableList<Habit>? {
        if (!file.exists()) return null

        return try {
            val type = object : TypeToken<MutableList<Habit>>() {}.type
            gson.fromJson<MutableList<Habit>>(file.readText(), type)
        } catch (e: Exception) {
            null
        }
    }
}
