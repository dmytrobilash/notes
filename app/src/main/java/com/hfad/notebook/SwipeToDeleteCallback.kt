package com.hfad.notebook

import APP
import android.R
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hfad.notebook.views.StartFragment


class SwipeToDeleteCallback : ItemTouchHelper.Callback() {
    //var mContext: Context? = context
    private var mClearPaint: Paint? = null
    private var mBackground: ColorDrawable? = null
    private var backgroundColor = 0
    private var deleteDrawable: Drawable? = null
    private var intrinsicWidth = 0
    private var intrinsicHeight = 0


    init {
        mBackground = ColorDrawable()
        backgroundColor = Color.parseColor("#b80f0a")
        mClearPaint = Paint()
        mClearPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        //deleteDrawable = ContextCompat.getDrawable(mContext!!, R.drawable.ic_delete)
        //intrinsicWidth = deleteDrawable!!.intrinsicWidth
        //intrinsicHeight = deleteDrawable!!.intrinsicHeight
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView: View = viewHolder.itemView
        val itemHeight: Int = itemView.getHeight()

        val isCancelled = (dX.toInt() == 0 && !isCurrentlyActive)

        if (isCancelled) {
            clearCanvas(
                c,
                itemView.getRight() + dX,
                itemView.getTop().toFloat(),
                itemView.getRight().toFloat(),
                itemView.getBottom().toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        mBackground!!.color = backgroundColor
        mBackground!!.setBounds(
            itemView.getRight() + dX.toInt(),
            itemView.getTop(),
            itemView.getRight(),
            itemView.getBottom()
        )
        mBackground!!.draw(c)

        val deleteIconTop: Int = itemView.getTop() + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft: Int = itemView.getRight() - deleteIconMargin - intrinsicWidth
        val deleteIconRight: Int = itemView.getRight() - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight


        deleteDrawable!!.setBounds(
            deleteIconLeft,
            deleteIconTop,
            deleteIconRight,
            deleteIconBottom
        )
        deleteDrawable!!.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder.adapterPosition
        Toast.makeText(APP, viewHolder.adapterPosition.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, mClearPaint!!)
    }

}