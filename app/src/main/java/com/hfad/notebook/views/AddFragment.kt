package com.hfad.notebook.views

import APP
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
        val viewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        val priorities: Array<String> = arrayOf("0", "1", "2", "3")
        val adapter = ArrayAdapter(
            APP,
            R.layout.note_items,
            priorities
        )
        binding.filledExposed.setAdapter(adapter)

        binding.btnAdd.setOnClickListener {
            val title = binding.editTitle.editText?.text.toString()
            val description = binding.editDescription.editText?.text.toString()
            viewModel.insert(
                Note(
                    title = title,
                    description = description,
                    creationTime = LocalTime.now().toString().substring(0, 8) + " " + LocalDate.now().toString().substring(5),
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