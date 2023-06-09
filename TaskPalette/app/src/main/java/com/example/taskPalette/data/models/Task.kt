package com.example.taskPalette.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskPalette.database.TaskCategory
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_data")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var isChecked: Boolean,
    val taskText: String,
    var expiresAt: Long?,
    val category: TaskCategory,
    val color: Int,
) : Parcelable