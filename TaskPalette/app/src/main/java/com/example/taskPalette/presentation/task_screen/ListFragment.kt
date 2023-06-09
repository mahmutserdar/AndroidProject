package com.example.taskPalette.presentation.task_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.taskPalette.presentation.dialogs.AddTaskDialogFragment
import com.example.taskPalette.R
import com.example.taskPalette.databinding.FragmentTaskListBinding
import com.example.taskPalette.database.TaskCategory

class ListFragment(private val taskCategory: TaskCategory) : Fragment(), CountDownController {

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)

        context?.getColor(taskCategory.imageBg)?.let { binding.recyclerview.setBackgroundColor(it) }

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        adapter = ListAdapter(viewModel, this)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
        binding.recyclerview.adapter = adapter
        binding.addFloatingBtn.setOnClickListener {
            val dialogFragment = AddTaskDialogFragment(taskCategory) { task -> viewModel.addTask(task) }
            dialogFragment.show(childFragmentManager, "AddTaskDialogFragment")
        }

        // Attach swipe-to-delete functionality to the RecyclerView
        val swipeHandler = SwipeToDeleteCallback(adapter, requireContext())
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)

        viewModel.getTasksByCategory(taskCategory).observe(viewLifecycleOwner) { task ->
            adapter.setData(task)
        }

        return binding.root

    }

    val counters = mutableListOf<CounterAble>()
    val handler = Handler(Looper.getMainLooper())

    override fun addObserver(counter: CounterAble) {
        counters.add(counter)
    }

    override fun startCountdown() {
        updateCounter()
    }

    private fun updateCounter() {
        handler.postDelayed({
            counters.forEach { it.updateCounter() }
            updateCounter()
        }, 1000)
    }

    private fun stopCounter() {
        handler.removeCallbacksAndMessages(null)
    }

}

