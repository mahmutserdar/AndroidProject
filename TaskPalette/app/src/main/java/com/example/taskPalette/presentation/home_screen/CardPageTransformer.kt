package com.example.taskPalette.presentation.home_screen

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class CardPageTransformer : ViewPager2.PageTransformer {

    private val MIN_SCALE = 1f

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        page.apply {
            when {
                position < -1 -> {
                    alpha = 0f
                }
                position <= 0 -> {
                    alpha = 1f
                    translationX = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> {
                    alpha = 1 - position
                    translationX = pageWidth * -position
                    val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    translationZ = -1 * abs(position) // added this line to set the Z translation based on position
                }
                else -> {
                    alpha = 0f
                }
            }
        }
    }
}