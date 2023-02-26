package com.hfad.notebook.views

import APP
import android.annotation.SuppressLint
import android.app.*
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.notebook.DescriptionTextWatcher
import com.hfad.notebook.R
import com.hfad.notebook.ViewModel.AddViewModel
import com.hfad.notebook.databinding.FragmentAddBinding
import com.hfad.notebook.model.Note
import com.hfad.notebook.notification.NotificationControl
import java.text.SimpleDateFormat
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

        val editText: EditText = binding.editDescription.editText!!
        DescriptionTextWatcher().limitEditTextLines(editText, 5)

        binding.datePickerBtn.setOnClickListener {
            showDatePicker()
        }

        binding.btnAdd.setOnClickListener {

            val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
            val title = binding.editTitle.editText?.text.toString()
            val description = binding.editDescription.editText?.text.toString()
            var selectedDateString: String = binding.dateInput.text.toString()
            Log.v("AAAA", selectedDateString.toString())
            val currentDate = Date()

            val finishedDateLong: Long
            if (selectedDateString == "") {
                selectedDateString = currentDate.toString()
                finishedDateLong = (dateFormat.parse(selectedDateString)?.time
                    ?: currentDate.time) + 60000 //3 600 000 is time for hour
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
                    finished = finishedDateString
                )
            ) {}


            val difference = finishedDate.time - currentDate.time

            Log.v("AAAAA", difference.toString())
            NotificationControl().setNotificationAtSelectedTime(
                currentDate.time,
                title,
                description,
                difference
            )

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

}
