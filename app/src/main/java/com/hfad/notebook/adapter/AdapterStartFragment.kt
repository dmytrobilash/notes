package com.hfad.notebook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.notebook.R
import com.hfad.notebook.model.Note
import com.hfad.notebook.views.StartFragment.Companion.clickNote
import com.hfad.notebook.views.StartFragment.Companion.longClickNote
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.note_items.view.*
import java.text.SimpleDateFormat
import java.util.*

class AdapterStartFragment : RecyclerView.Adapter<AdapterStartFragment.NoteViewHolder>() {

    var originalListNote = emptyList<Note>()
    var filteredListNote = emptyList<Note>()

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.note_items, parent, false)
        return NoteViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.itemView.title.text = filteredListNote[position].title
        val dateFormat = SimpleDateFormat("hh:mm:ss dd-MM-yyyy", Locale.US)
        holder.itemView.creation_time.text = dateFormat.format(filteredListNote[position].creation)
        holder.itemView.finished_start_fragment.text = dateFormat.format(filteredListNote[position].finished)
    }


    override fun getItemCount(): Int {
        return filteredListNote.size
    }

    fun setList(list: List<Note>) {
        originalListNote = list
        filter("") // call filter with an empty query to initialize the filteredListNote variable
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        if (query.isNotBlank()) {
            filteredListNote = originalListNote.filter { it.title.contains(query, ignoreCase = true) }
        } else {
            filteredListNote = originalListNote
        }
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: NoteViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            clickNote(filteredListNote[holder.adapterPosition])
        }
        holder.itemView.edit_item.setOnClickListener {
            longClickNote(filteredListNote[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: NoteViewHolder) {
        holder.itemView.setOnClickListener(null)
    }
}