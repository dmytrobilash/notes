package com.hfad.notebook.views

import java.util.*
import APP
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.notebook.NotificationReceiver
import com.hfad.notebook.R
import com.hfad.notebook.ViewModels.Add.AddViewModel
import com.hfad.notebook.databinding.FragmentAddBinding
import com.hfad.notebook.model.Note
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {

        val viewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        val priorities: Array<String> = arrayOf("0", "1", "2", "3")
        val adapter = ArrayAdapter(
            APP,
            R.layout.drop_down_items,
            priorities
        )
        binding.filledExposed.setAdapter(adapter)


        binding.datePickerBtn.setOnClickListener {
            showDatePicker()
        }

        binding.btnAdd.setOnClickListener {

            val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
            val title = binding.editTitle.editText?.text.toString()
            val description = binding.editDescription.editText?.text.toString()
            var taskPriority = binding.filledExposed.text.toString()
            var selectedDateString: String = binding.dateInput.text.toString()

            if (taskPriority == "") taskPriority = "0"

            val currentDate = Date()

            val finishedDateLong: Long
            if (selectedDateString == "") {
                selectedDateString = currentDate.toString()
                finishedDateLong = (dateFormat.parse(selectedDateString)?.time ?: currentDate.time) + 60000 //3 600 000 is time for hour
            } else {
                finishedDateLong = dateFormat.parse(selectedDateString)?.time ?: currentDate.time
            }
            val finishedDate = Date(finishedDateLong)
            val finishedDateString = dateFormat.format(finishedDate)

            viewModel.insert(
                Note(
                    title = title,
                    description = description,
                    creationTime = currentDate.toString(),
                    taskPriority = taskPriority.toInt(),
                    finished = finishedDateString
                )
            ) {}


            val difference = finishedDate.time - currentDate.time

            setNotificationAtSelectedTime(difference, title, description)

            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }

        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
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

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Remainder"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Notify", name, importance)

            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setNotificationAtSelectedTime(
        selectedTimeInMillis: Long,
        title: String,
        description: String
    ) {
        createNotificationChannel()
        val intent = Intent(APP, NotificationReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("description", description)
            putExtra("id", selectedTimeInMillis)
        }
        val pI = PendingIntent.getBroadcast(
            APP,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Set the alarm to trigger at the selected time
        alarmManager.set(AlarmManager.RTC_WAKEUP, Date().time + selectedTimeInMillis, pI)
    }

}
