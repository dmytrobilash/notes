package com.hfad.notebook.views

import APP
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hfad.notebook.R
import com.hfad.notebook.Swipe.Swipe
import com.hfad.notebook.ViewModel.StartViewModel
import com.hfad.notebook.adapter.AdapterStartFragment
import com.hfad.notebook.databinding.FragmentStartBinding
import com.hfad.notebook.model.Note


class StartFragment : Fragment() {

    lateinit var binding: FragmentStartBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AdapterStartFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private fun init() {
        val viewModel = ViewModelProvider(this).get(StartViewModel::class.java)
        viewModel.initDataBase()
        recyclerView = binding.rv
        adapter = AdapterStartFragment()
        recyclerView.adapter = adapter
        viewModel.getAllNotes().observe(viewLifecycleOwner) { listNotes ->
            adapter.setList(listNotes)
        }

        binding.fabAdd.setOnClickListener {
            APP.navController.navigate(R.id.action_startFragment_to_addFragment)
        }

        val swipe = object : Swipe(context) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val viewModel = ViewModelProvider(APP).get(StartViewModel::class.java)
                        viewModel.delete(adapter.listNote[viewHolder.adapterPosition]) {}
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipe)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    companion object {
        fun clickNote(note: Note) {
            val bundle = Bundle()
            bundle.putSerializable("note", note)
            APP.navController.navigate(R.id.action_startFragment_to_deleteFragment, bundle)
        }

        fun longClickNote(note: Note): Boolean {
            val bundle = Bundle()
            bundle.putSerializable("note", note)
            APP.navController.navigate(R.id.action_startFragment_to_editFragment, bundle)
            return true
        }
    }
}

