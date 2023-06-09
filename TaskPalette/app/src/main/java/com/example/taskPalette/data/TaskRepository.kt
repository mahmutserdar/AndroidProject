package com.example.taskPalette.data

import androidx.lifecycle.LiveData
import com.example.taskPalette.data.models.Task
import com.example.taskPalette.database.TaskCategory
import com.example.taskPalette.database.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    fun getTasksByCategory(category: TaskCategory): LiveData<List<Task>> {
        return taskDao.getTasksByCategory(category)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
}
