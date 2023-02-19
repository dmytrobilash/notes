package com.hfad.notebook.views

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
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.notebook.NotificationReceiver
import com.hfad.notebook.R
import com.hfad.notebook.ViewModels.Add.AddViewModel
import com.hfad.notebook.databinding.FragmentAddBinding
import com.hfad.notebook.model.Note
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

        binding.timePickerBtn.setOnClickListener {
            showTimePicker()
        }

        binding.datePickerBtn.setOnClickListener {
            showDatePicker()
        }

        binding.btnAdd.setOnClickListener {

            val title = binding.editTitle.editText?.text.toString()
            val description = binding.editDescription.editText?.text.toString()
            val localTime = LocalTime.now().toString().substring(0, 5)
            val localDate = LocalDate.now().toString().replace("-", "/")
            val creationTime = "$localTime $localDate"

            var finishTime = binding.timePickerInput.editText?.text.toString()

            if (finishTime.length == 4) {
                finishTime = "0$finishTime"
            }

            val finishDate = binding.datePickerInput.editText?.text.toString()

            val finishedTime = "$finishTime $finishDate"
            var taskPriority = binding.filledExposed.text.toString()
            if (taskPriority == "") taskPriority = "0"

            viewModel.insert(
                Note(
                    title = title,
                    description = description,
                    creationTime = creationTime,
                    taskPriority = taskPriority.toInt(),
                    finished = finishedTime
                )
            ) {}

            createNotificationChannel()
            val intent = Intent(APP, NotificationReceiver::class.java).apply {
                putExtra("title", title)
                putExtra("description", description)
            }
            val pI = PendingIntent.getBroadcast(
                APP,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // trash I spend 2 hours to setup this PendingIntent.FLAG_UPDATE_CURRENT shit i cant comment it
            // i so exhausted with this programming
            //there is not words to describe this tilt

            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, 5000, pI)
            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }

        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val mHour = calendar.get(Calendar.HOUR_OF_DAY)
        val mMinute = calendar.get(Calendar.MINUTE)
        val tpd = TimePickerDialog(
            APP,
            TimePickerDialog.OnTimeSetListener() { view, hourOfDay, minute ->
                binding.timePickerInput.editText?.setText("$hourOfDay:$minute")
            },
            mHour,
            mMinute,
            false
        )
        tpd.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            APP,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.datePickerInput.editText?.setText("$year/${month + 1}/$dayOfMonth")
            },
            mYear,
            mMonth,
            mDay
        )
        dpd.show()
    }

    private fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name = "Remainder"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Notify", name, importance)

            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
