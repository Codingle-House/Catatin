package id.catat.uikit.util

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil

/**
 * Created by pertadima on 23,August,2020
 */

class DiffCallback : DiffUtil.Callback() {

    private var oldList: List<Any> = emptyList()
    private var newList: List<Any> = emptyList()

    fun setList(oldList: List<Any>, newList: List<Any>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}