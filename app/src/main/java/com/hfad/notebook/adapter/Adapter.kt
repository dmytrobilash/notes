package com.hfad.notebook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.notebook.R
import com.hfad.notebook.model.Note
import kotlinx.android.synthetic.main.deleting_fragment.view.*
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
        //holder.itemView.description.text = listNote[position].description
        //holder.itemView.creation_time.text = listNote[position].creationTime
    }
    override fun getItemCount(): Int {
        return listNote.size;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Note>){
        listNote = list
        notifyDataSetChanged()
    }
}