package `in`.catat.presentation.dialog.filter

import `in`.catat.R
import `in`.catat.data.dto.CatatinFilterMenuDto
import `in`.catat.util.AnimationConstant
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.catatin.core.commons.DiffCallback
import kotlinx.android.synthetic.main.item_notes_filter.view.*

/**
 * Created by pertadima on 27,October,2020
 */

class GeneralCacatinFilterMenuAdapter(
    private val context: Context,
    private val diffCallback: DiffCallback,
    private val itemListener: (CatatinFilterMenuDto, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run {} },
) : RecyclerView.Adapter<GeneralCacatinFilterMenuAdapter.ItemViewHolder>() {

    private val dataSet: MutableList<CatatinFilterMenuDto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_notes_filter, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(dataSet[holder.adapterPosition])
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemId(position: Int): Long = dataSet[position].id.hashCode().toLong()

    fun setData(newDataSet: List<CatatinFilterMenuDto>) {
        calculateDiff(newDataSet)
    }

    private fun calculateDiff(newDataSet: List<CatatinFilterMenuDto>) {
        diffCallback.setList(dataSet, newDataSet)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(dataSet) {
            clear()
            addAll(newDataSet)
        }
        result.dispatchUpdatesTo(this)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(data: CatatinFilterMenuDto) {
            itemView.dialog_textview_menufilter_item_title.text = context.getString(data.title)
            with(itemView.dialog_imageview_menufilter_check) {
                animate().apply {
                    scaleX(if (data.isSelected) AnimationConstant.FULL_SCALE else 0F)
                    scaleY(if (data.isSelected) 1F else 0F)
                }.duration = AnimationConstant.DEFAULT_ANIMATION_DURATION
            }
            itemView.dialog_view_menufilter_line.isVisible = adapterPosition != dataSet.size - 1

            itemView.setOnClickListener {
                itemListener.invoke(data, adapterPosition, itemView)
            }
        }
    }
}