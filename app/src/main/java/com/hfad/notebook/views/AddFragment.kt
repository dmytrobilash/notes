package com.hfad.notebook.views

import APP
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        var mYear: Int;
        var mMonth: Int;
        var mDay: Int;
        var mHour: Int;
        var mMinute: Int
        val viewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        val priorities: Array<String> = arrayOf("0", "1", "2", "3")

        val adapter = ArrayAdapter(
            APP,
            R.layout.drop_down_items,
            priorities
        )
        binding.filledExposed.setAdapter(adapter)

        binding.timePickerBtn.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            mHour = calendar.get(Calendar.HOUR_OF_DAY)
            mMinute = calendar.get(Calendar.MINUTE)
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

        binding.datePickerBtn.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            mYear = calendar.get(Calendar.YEAR)
            mMonth = calendar.get(Calendar.MONTH)
            mDay = calendar.get(Calendar.DAY_OF_MONTH)

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
            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }
        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }
    }
}
