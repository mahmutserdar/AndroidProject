package com.example.taskPalette.presentation.task_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskPalette.presentation.task_screen.ListFragment
import com.example.taskPalette.database.TaskCategory

class ScreenPagerAdapter(
    private val tabCount: Int,
    fa: FragmentActivity
) :FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return ListFragment(TaskCategory.safeValueFor(position))
    }
}