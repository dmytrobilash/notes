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
import kotlinx.android.synthetic.main.note_items.view.*
import java.util.*

class AdapterStartFragment : RecyclerView.Adapter<AdapterStartFragment.NoteViewHolder>() {

    var listNote = emptyList<Note>()

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.note_items, parent, false)
        return NoteViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.itemView.title.text = listNote[position].title
        holder.itemView.creation_time.text = listNote[position].creationTime
        holder.itemView.finished_start_fragment.text = listNote[position].finished
    }

    override fun getItemCount(): Int {
        return listNote.size;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Note>) {
        listNote = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: NoteViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            clickNote(listNote[holder.adapterPosition])
        }
        holder.itemView.edit_item.setOnClickListener {
            longClickNote(listNote[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: NoteViewHolder) {
        holder.itemView.setOnClickListener(null)
    }
}