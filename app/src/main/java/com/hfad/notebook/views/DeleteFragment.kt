package com.hfad.notebook.views

import APP
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.notebook.notification.NotificationReceiver
import com.hfad.notebook.R
import com.hfad.notebook.ViewModel.DeleteViewModel
import com.hfad.notebook.databinding.FragmentDeleteBinding
import com.hfad.notebook.model.Note
import com.hfad.notebook.notification.NotificationControl
import java.text.SimpleDateFormat
import java.util.*

class DeleteFragment : Fragment() {

    private lateinit var binding: FragmentDeleteBinding
    private lateinit var currentNote: Note

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

    @SuppressLint("SetTextI18n", "UnspecifiedImmutableFlag")
    private fun init() {
        val viewModel = ViewModelProvider(this)[DeleteViewModel::class.java]
        binding.title.text = currentNote.title
        binding.description.text = currentNote.description
        binding.creationTime.text = currentNote.creationTime
        binding.finishedTime.text = currentNote.finished

        binding.btnDelete.setOnClickListener {
            NotificationControl().cancelNotification(currentNote.creationTime, APP)
            viewModel.delete(currentNote) {}
            APP.navController.navigate(R.id.action_deleteFragment_to_startFragment)
        }

        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_deleteFragment_to_startFragment)
        }
    }

}