package com.hfad.notebook.views

import APP
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hfad.notebook.R
import com.hfad.notebook.ViewModels.EditViewModel
import com.hfad.notebook.databinding.FragmentEditBinding
import com.hfad.notebook.model.Note
import kotlinx.android.synthetic.main.fragment_add.*


class EditFragment : Fragment() {

    lateinit var binding: FragmentEditBinding
    lateinit var currentNote: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater, container, false)
        currentNote = arguments?.getSerializable("note") as Note
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        binding.btnSave.setOnClickListener {
            val viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
            val title = binding.editTitle.text.toString()
            Log.v("AAAAA", title.toString())
            val description = binding.editDescription.text.toString()
            viewModel.update(
                // TODO: create updation 
            ) {}
            APP.navController.navigate(R.id.action_editFragment_to_startFragment)
        }

        binding.btnBack.setOnClickListener {
            APP.navController.navigate(R.id.action_editFragment_to_startFragment)
        }
    }

}