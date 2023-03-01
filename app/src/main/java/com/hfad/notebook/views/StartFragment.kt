package com.hfad.notebook.views

import APP
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphNavigator
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.notebook.R
import com.hfad.notebook.Swipe.Swipe
import com.hfad.notebook.ViewModel.StartViewModel
import com.hfad.notebook.adapter.AdapterStartFragment
import com.hfad.notebook.databinding.FragmentStartBinding
import com.hfad.notebook.model.Note
import com.hfad.notebook.notification.NotificationControl
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.note_items.view.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


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

        binding.searchStart.setOnClickListener {
            viewModel.getAllNotesByFinishedAsc().observe(viewLifecycleOwner) { listNotes ->
                adapter.setList(listNotes)

            }
        }

        binding.moreVertStart.setOnClickListener {
            viewModel.getAllNotesByFinishedDescending().observe(viewLifecycleOwner) { listNotes ->
                adapter.setList(listNotes)
            }
        }


        val scope = CoroutineScope(Dispatchers.Default)
        val interval = 1L

        scope.launch {
            while (true) {
                delay(interval) // Convert seconds to milliseconds
                withContext(Dispatchers.Main) {
                    for (i in 0 until adapter.listNote.size) {
                        var finished = adapter.listNote[i].finished
                        if (finished!! - Date().time in 1..3599999) {
                            updateTextColor(i, Color.rgb(153, 153, 0))
                        } else if (finished!! - Date().time < 0) {
                            updateTextColor(i, Color.RED)
                        } else {
                            updateTextColor(i, Color.rgb(71, 118, 213))
                        }
                    }
                }
            }
        }

        val swipe = object : Swipe(context) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val viewModel = ViewModelProvider(APP).get(StartViewModel::class.java)
                        NotificationControl().cancelNotification(
                            adapter.listNote[viewHolder.adapterPosition].creation,
                            APP
                        )
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

        fun longClickNote(note: Note) {
            val bundle = Bundle()
            bundle.putSerializable("note", note)
            APP.navController.navigate(R.id.action_startFragment_to_editFragment, bundle)
        }
    }


    private fun updateTextColor(position: Int, color: Int) {
        rv?.findViewHolderForAdapterPosition(position)?.itemView?.finished_start_fragment?.setTextColor(
            color
        )
    }

}
