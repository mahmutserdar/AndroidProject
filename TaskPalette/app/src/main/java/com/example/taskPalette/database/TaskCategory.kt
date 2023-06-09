package com.example.taskPalette.database

import com.example.taskPalette.R

enum class TaskCategory(val type: Int,val imageBg : Int) {
    MUST_DO(0, R.color.must_do),
    SHOULD_DO(1, R.color.should_do),
    COULD_DO(2, R.color.could_do);

    companion object {
        fun safeValueFor(t: Int): TaskCategory {
            return values().firstOrNull { it.type == t } ?: throw IllegalArgumentException("Invalid TaskCategory type")
        }
    }
}
