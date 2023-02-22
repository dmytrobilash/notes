package com.hfad.notebook.views

import APP
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.notebook.R
import com.hfad.notebook.ViewModels.Delete.DeleteViewModel
import com.hfad.notebook.databinding.FragmentDeleteBinding
import com.hfad.notebook.model.Note

class DeleteFragment : Fragment() {

    lateinit var binding: FragmentDeleteBinding
    lateinit var currentNote: Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteBinding.inflate(layoutInflater, container, false)
        currentNote = arguments?.getSerializable("note") as Note
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        val viewModel = ViewModelProvider(this).get(DeleteViewModel::class.java)
        binding.title.text = currentNote.title
        binding.description.text = currentNote.description
        binding.creationTime.text = currentNote.creationTime
        binding.finishedTime.text = currentNote.finished

        when(currentNote.taskPriority){
            0 ->  binding.taskPriority.text = "High"
            1 ->  binding.taskPriority.text = "Important"
            2 ->  binding.taskPriority.text = "Normal"
            3 ->  binding.taskPriority.text = "Low"
        }
        binding.taskPriority.text = currentNote.taskPriority.toString()

        binding.btnDelete.setOnClickListener {
            viewModel.delete(currentNote){}

            APP.navController.navigate(R.id.action_deleteFragment_to_startFragment)
        }

        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_deleteFragment_to_startFragment)
        }
    }
}