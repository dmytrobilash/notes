package com.hfad.notebook.views

import APP
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hfad.notebook.notification.NotificationReceiver
import com.hfad.notebook.R
import com.hfad.notebook.ViewModel.EditViewModel
import com.hfad.notebook.adapter.AdapterPriority
import com.hfad.notebook.databinding.FragmentEditBinding
import com.hfad.notebook.model.Note
import com.hfad.notebook.notification.NotificationControl
import java.text.SimpleDateFormat
import java.util.*


class EditFragment : Fragment() {

    lateinit var binding: FragmentEditBinding
    lateinit var currentNote: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater, container, false)
        currentNote = arguments?.getSerializable("note") as Note
        binding.editTitle.editText?.setText(currentNote.title)
        binding.editDescription.editText?.setText(currentNote.description)
        binding.filledExposedTil.editText?.setText(currentNote.taskPriority.toString())
        binding.dateInput.text = currentNote.finished

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        binding.filledExposed.setAdapter(AdapterPriority().setAdapterPriority())

        binding.datePickerBtn.setOnClickListener {
            showDatePicker()
        }

        binding.btnSave.setOnClickListener {
            val viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
            currentNote.title = binding.editTitle.editText?.text.toString()
            currentNote.description = binding.editDescription.editText?.text.toString()
            currentNote.taskPriority = binding.filledExposedTil.editText?.text.toString().toInt()
            currentNote.finished = binding.dateInput.text.toString()

            viewModel.update(
                Note(
                    id = currentNote.id,
                    title = currentNote.title,
                    description = currentNote.description,
                    taskPriority = currentNote.taskPriority,
                    finished = currentNote.finished
                )
            ) {}

            val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
            val creationDateLong = dateFormat.parse(currentNote.creationTime).time
            val finishedDateLong = dateFormat.parse(currentNote.finished).time
            NotificationControl().updateNotificationAtSelectedTime(
                creationDateLong,
                currentNote.title,
                currentNote.description,
                finishedDateLong - creationDateLong
            )
            APP.navController.navigate(R.id.action_editFragment_to_startFragment)
        }

        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_editFragment_to_startFragment)
        }
    }

    private fun showDatePicker() {
        val now = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            APP,
            { _, year, month, dayOfMonth ->

                val timePickerDialog = TimePickerDialog(
                    APP,
                    { _, hourOfDay, minute ->
                        // Handle the selected date and time
                        val selectedDate = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth, hourOfDay, minute)
                        }.time

                        val currentDate = Calendar.getInstance().time
                        if (selectedDate.before(currentDate)) {
                            // Handle error, for example:
                            Toast.makeText(APP, "Please select a future time", Toast.LENGTH_SHORT)
                                .show()
                            return@TimePickerDialog
                        }

                        binding.dateInput.text = selectedDate.toString()
                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true
                )
                timePickerDialog.show()
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.minDate = now.timeInMillis
        datePickerDialog.show()
    }

}