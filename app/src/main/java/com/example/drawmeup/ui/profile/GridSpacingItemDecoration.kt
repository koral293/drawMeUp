package com.example.drawmeup.ui.profile

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spacing: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % 3
        outRect.left = spacing - column * spacing / 3
        outRect.right = (column + 1) * spacing / 3
        if (position < 3) {
            outRect.top = spacing
        }
        outRect.bottom = spacing
    }

}