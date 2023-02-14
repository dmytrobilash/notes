package com.hfad.notebook.views

import APP
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.notebook.R
import com.hfad.notebook.ViewModels.AddingViewModel
import com.hfad.notebook.databinding.FragmentAddBinding
import com.hfad.notebook.databinding.FragmentStartBinding
import com.hfad.notebook.model.Note

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        val viewModel = ViewModelProvider(this).get(AddingViewModel::class.java)
        binding.btnAdd.setOnClickListener {
            val title = binding.editTitle.text.toString()
            val description = binding.editDescription.text.toString()
            viewModel.insert(Note(title = title, description = description)){
                Log.v("AAAAA", "Fragment")
                APP.navController.navigate(R.id.action_addFragment_to_startFragment)
            }
        }
        binding.btnBack.setOnClickListener{
            APP.navController.navigate(R.id.action_addFragment_to_startFragment)
        }
    }

}