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
                    // Update the text of the appropriate view with the selected date
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
            viewModel.insert(
                Note(
                    title = title,
                    description = description,
                    creationTime = LocalTime.now().toString()
                        .substring(0, 8) + " " + LocalDate.now().toString().substring(5),
                    taskPriority = binding.filledExposed.text.toString().toInt(),
                    //todo finished time()
                )
            ) {}
            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }
        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }
    }
}
