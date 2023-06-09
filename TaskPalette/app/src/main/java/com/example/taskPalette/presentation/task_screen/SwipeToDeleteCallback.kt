package com.example.taskPalette.presentation.task_screen

import android.app.AlertDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(private val adapter: ListAdapter, val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val background = ColorDrawable(Color.RED)
    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(canvas, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(canvas)

        val textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 40f
        val textWidth = textPaint.measureText("Deleting")
        val textX = itemView.right - textWidth - 50f
        val textY = itemView.top.toFloat() + (itemHeight.toFloat() - textPaint.descent() - textPaint.ascent()) / 2
        canvas.drawText("Deleting", textX, textY, textPaint)
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(canvas: Canvas?, left: Float?, top: Float?, right: Float?, bottom: Float?) {
        canvas?.drawRect(left!!, top!!, right!!, bottom!!, Paint())
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        // Not used in swipe-to-delete implementation
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        AlertDialog.Builder(context).apply {
            setTitle("Confirm Delete")
            setMessage("Are you sure you want to delete this task?")
            setPositiveButton("Yes") { _, _ ->
                adapter.deleteTask(position)
                Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("No") { _, _ ->
                adapter.notifyItemChanged(position)
            }
            create()
            show()
        }
    }

}

