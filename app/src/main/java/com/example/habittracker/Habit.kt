package com.example.habittracker

data class Habit(
    val name: String,
    val description: String,
    val goal: Int,
    var progress: Int = 0,
    val icon: String,
    val unit: String
)