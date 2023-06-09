package com.example.taskPalette.presentation.task_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taskPalette.data.models.Task
import com.example.taskPalette.database.TaskCategory
import com.example.taskPalette.database.TaskDatabase
import com.example.taskPalette.data.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = TaskRepository(TaskDatabase.getDatabase(application).taskDao())

    fun addTask(task : Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTask(task)
        }
    }

    fun getTasksByCategory(category: TaskCategory): LiveData<List<Task>> {
        return repository.getTasksByCategory(category)
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

}

