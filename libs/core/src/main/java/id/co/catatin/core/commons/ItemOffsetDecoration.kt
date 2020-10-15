package id.co.catatin.core.commons

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 * Created by pertadima on 15,October,2020
 */

class EqualSpaceItemDecoration(private val spaceHeight: Int = 15) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = spaceHeight
        outRect.top = spaceHeight
        outRect.left = spaceHeight
        outRect.right = spaceHeight
    }
}