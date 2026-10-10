package com.example.habittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardFragment : Fragment() {

    private lateinit var viewModel: HabitViewModel
    private lateinit var adapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_dashboard,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[HabitViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val btnAddHabit = view.findViewById<View>(R.id.btnAddHabit)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = HabitAdapter { index, amount ->
            viewModel.updateProgress(index, amount)
        }

        recyclerView.adapter = adapter

        viewModel.habits.observe(viewLifecycleOwner) { habits ->
            adapter.updateHabits(habits)
        }

        btnAddHabit.setOnClickListener {
            findNavController().navigate(
                R.id.action_dashboardFragment_to_createHabitFragment
            )
        }
    }
}
