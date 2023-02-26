package com.hfad.notebook

import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.widget.EditText

class DescriptionTextWatcher {

    fun limitEditTextLines(editText: EditText, maxLines: Int) {
        var isEditingProgrammatically = false

        editText.addTextChangedListener(object : TextWatcher {
            private var previousLineCount = 0

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Save the previous line count before the text changes
                val layout: Layout? = editText.layout
                if (layout != null) {
                    previousLineCount = layout.lineCount
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val layout: Layout? = editText.layout
                if (layout != null && layout.lineCount > maxLines - 1 && !isEditingProgrammatically) {
                    // Remove the additional lines
                    val end: Int = layout.getLineEnd(maxLines - 1)
                    isEditingProgrammatically = true
                    editText.setText(s.subSequence(0, end))
                    editText.setSelection(end - 1)
                    isEditingProgrammatically = false
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing
            }
        })

        // Set the maximum number of lines
        editText.maxLines = maxLines
    }
}