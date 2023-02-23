package com.hfad.notebook.adapter

import APP
import android.widget.ArrayAdapter
import com.hfad.notebook.R

class AdapterPriority {

    private val priorities: Array<String> = arrayOf("0", "1", "2", "3")

    fun setAdapterPriority():
            ArrayAdapter<String> {
        return ArrayAdapter(
           APP,
           R.layout.drop_down_items,
            priorities
       )
    }
}