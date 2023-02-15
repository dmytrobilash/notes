package com.hfad.notebook.adapter

import APP
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hfad.notebook.R
import com.hfad.notebook.model.Note
import com.hfad.notebook.views.StartFragment
import com.hfad.notebook.views.StartFragment.Companion.clickNote
import kotlinx.android.synthetic.main.fragment_delete.view.*
import kotlinx.android.synthetic.main.note_items.view.*
import kotlinx.android.synthetic.main.note_items.view.title

class Adapter : RecyclerView.Adapter<Adapter.NoteViewHolder>() {

    var listNote = emptyList<Note>()
    class NoteViewHolder(view:View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.note_items, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.itemView.title.text = listNote[position].title
        holder.itemView.setOnClickListener {
            Toast.makeText(APP, position.toString(), Toast.LENGTH_LONG).show()
        }
    }
    override fun getItemCount(): Int {
        return listNote.size;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Note>){
        listNote = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: NoteViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            clickNote(listNote[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: NoteViewHolder) {
        holder.itemView.setOnClickListener(null)
    }
}