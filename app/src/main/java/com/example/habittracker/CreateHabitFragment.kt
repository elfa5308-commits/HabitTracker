package com.example.habittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class CreateHabitFragment : Fragment() {

    private lateinit var viewModel: HabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_create_habit,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]

        val edtName = view.findViewById<EditText>(R.id.edtHabitName)
        val edtDescription = view.findViewById<EditText>(R.id.edtHabitDescription)
        val edtGoal = view.findViewById<EditText>(R.id.edtHabitGoal)
        val edtUnit = view.findViewById<EditText>(R.id.edtHabitUnit)
        val spinnerIcon = view.findViewById<Spinner>(R.id.spinnerHabitIcon)
        val btnSave = view.findViewById<Button>(R.id.btnSaveHabit)
        val btnBack = view.findViewById<TextView>(R.id.btnBack)

        val categories = listOf(
            "Health",
            "Fitness",
            "Learning",
            "Mindfulness",
            "Nutrition",
            "Other"
        )

        val iconAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )
        iconAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerIcon.adapter = iconAdapter

        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            val description = edtDescription.text.toString().trim()
            val goal = edtGoal.text.toString().toIntOrNull()
            val unit = edtUnit.text.toString().trim()

            if (name.isEmpty() || description.isEmpty() || unit.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Lengkapi semua kolom terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (goal == null || goal <= 0) {
                edtGoal.error = "Target harus lebih dari 0"
                return@setOnClickListener
            }

            val icon = when (spinnerIcon.selectedItem.toString()) {
                "Health" -> "💧"
                "Fitness" -> "💪"
                "Learning" -> "📚"
                "Mindfulness" -> "🧘"
                "Nutrition" -> "🍎"
                else -> "⭐"
            }

            val habit = Habit(
                name = name,
                description = description,
                goal = goal,
                progress = 0,
                icon = icon,
                unit = unit
            )

            viewModel.addHabit(habit)

            Toast.makeText(
                requireContext(),
                "Habit berhasil ditambahkan",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigate(
                R.id.action_createHabitFragment_to_dashboardFragment
            )
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
