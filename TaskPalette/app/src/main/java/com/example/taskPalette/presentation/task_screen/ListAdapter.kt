package com.example.taskPalette.presentation.task_screen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskPalette.data.models.Task
import com.example.taskPalette.databinding.TaskItemViewBinding

class ListAdapter(
    private val viewModel: TaskViewModel,
    private val countDownController: CountDownController,
) : RecyclerView.Adapter<MyViewHolder>() {

    var taskLists: List<Task> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TaskItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MyViewHolder(binding)
        countDownController.addObserver(holder)
        countDownController.startCountdown()
        return holder
    }

    override fun getItemCount(): Int {
        return taskLists.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = taskLists[position]
        holder.bind(currentItem) { task -> viewModel.updateTask(task) }
    }

    fun deleteTask(position: Int) {
        val deletedTask = taskLists[position]
        taskLists = taskLists.filterIndexed { index, _ -> index != position }
        notifyItemRemoved(position)
        viewModel.deleteTask(deletedTask)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(task: List<Task>) {
        this.taskLists = task
        notifyDataSetChanged()
    }

}

interface CountDownController {
    fun addObserver(counter: CounterAble)
    fun startCountdown()
}

interface CounterAble {
    fun updateCounter()
}