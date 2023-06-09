package com.example.taskPalette.presentation.dialogs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.taskPalette.R
import com.example.taskPalette.data.models.Task
import com.example.taskPalette.database.TaskCategory
import com.example.taskPalette.databinding.FragmentAddTaskDialogBinding
import com.example.taskPalette.utils.DateUtils.formatter
import com.example.taskPalette.utils.toGone
import com.google.android.material.card.MaterialCardView
import java.sql.Timestamp.valueOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset


class AddTaskDialogFragment(private val taskCategory: TaskCategory, private val addTaskCallback: (Task) -> Unit) : DialogFragment() {

    private lateinit var binding: FragmentAddTaskDialogBinding
    var selectedDate: LocalDateTime = LocalDateTime.now()
    private var selectedColor: Int = 0

    private var dateSet = false
    private var timeSet = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskDialogBinding.inflate(inflater, container, false)
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.bg_round_corner_dialog)

        if (taskCategory != TaskCategory.MUST_DO) {
            binding.selectDueDate.toGone()
            binding.timePicker.toGone()
            binding.datePicker.toGone()
        }

        binding.addBtn.setOnClickListener {
            if (dateSet && timeSet) {
                val taskName = binding.taskNameEditText.text.toString()

                if (taskName.isNotBlank()) {
                    val task = if (taskCategory == TaskCategory.MUST_DO) {
                        val formattedTime = selectedDate.format(formatter)
                        val timestamp = valueOf(formattedTime).time
                        Task(0, false, taskName, timestamp, taskCategory, selectedColor)
                    } else {
                        Task(0, false, taskName, null, taskCategory, selectedColor)
                    }

                    addTaskCallback(task)
                    dismissAllowingStateLoss()
                } else {
                    binding.taskNameEditText.error = "Task name cannot be empty"
                }
            } else {
                if (taskCategory == TaskCategory.MUST_DO) {
                    Toast.makeText(context, "Please select date and time", Toast.LENGTH_SHORT).show()
                } else {
                    val taskName = binding.taskNameEditText.text.toString()

                    if (taskName.isNotBlank()) {
                        val task = Task(0, false, taskName, null, taskCategory, selectedColor)
                        addTaskCallback(task)
                        dismissAllowingStateLoss()
                    } else {
                        binding.taskNameEditText.error = "Task name cannot be empty"
                    }
                }
            }
        }


        val mColors = listOf(R.color.colorNo1, R.color.colorNo2, R.color.colorNo3, R.color.colorNo4, R.color.colorNo5)

        mColors.forEachIndexed { index, color ->
            binding.colors.getChildAt(index).setOnClickListener { view ->
                selectColor(view as MaterialCardView, context?.getColor(color)!!)
            }
        }

        binding.cancelBtn.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding.datePicker.setOnClickListener {
            showDatePickerDialog()
        }

        binding.timePicker.setOnClickListener {
            showTimePickerDialog()
        }

        return binding.root
    }

    private fun selectColor(view: MaterialCardView, color: Int) {
        for (i in 0 until binding.colors.childCount) {
            val child = binding.colors.getChildAt(i) as MaterialCardView
            if (child == view) {
                child.strokeWidth = 6
                child.radius = 20f
            } else {
                child.strokeWidth = 0
                child.radius = 0f
            }
        }
        selectedColor = color
    }

    private fun showTimePickerDialog() {
        val currentTime = LocalDateTime.now()
        val timePickerDialog = TimePickerDialog(
            requireContext(), { _, hourOfDay, minute ->
                selectedDate = LocalDateTime.of(
                    selectedDate.year, selectedDate.month, selectedDate.dayOfMonth, hourOfDay, minute
                )
                // set selected time to text view
                val formattedTime = "$hourOfDay:$minute"
                binding.timePicker.text = formattedTime
                timeSet = true
            }, currentTime.hour, currentTime.minute, true
        )
        timePickerDialog.show()
    }

    private fun showDatePickerDialog() {
        val currentDate = LocalDate.now()
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                selectedDate = LocalDateTime.of(
                    year, monthOfYear + 1, dayOfMonth, selectedDate.hour, selectedDate.minute
                )
                // set selected date to text view
                val formattedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.datePicker.text = formattedDate
                dateSet = true
            }, currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth
        )

        // Set the minimum date to the current date
        datePickerDialog.datePicker.minDate = currentDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()

        datePickerDialog.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
}