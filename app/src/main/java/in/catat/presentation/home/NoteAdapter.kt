package `in`.catat.presentation.home

import `in`.catat.R
import `in`.catat.data.dto.NoteDto
import `in`.catat.util.constants.AppUtils
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.stripHtml
import kotlinx.android.synthetic.main.item_notes_card.view.*


/**
 * Created by pertadima on 05,November,2020
 */

class NoteAdapter(
    private val context: Context,
    private val diffCallback: DiffCallback,
    private val itemListener: (NoteDto, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run {} }
) : RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {

    private val dataSet: MutableList<NoteDto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_notes_card, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(dataSet[holder.adapterPosition])
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemId(position: Int): Long = dataSet[position].id.hashCode().toLong()

    fun setData(newDataSet: List<NoteDto>) {
        calculateDiff(newDataSet)
    }

    private fun calculateDiff(newDataSet: List<NoteDto>) {
        diffCallback.setList(dataSet, newDataSet)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(dataSet) {
            clear()
            addAll(newDataSet)
        }
        result.dispatchUpdatesTo(this)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(data: NoteDto) {
            itemView.note_textview_notes_datetime.text = data.createdAt
            itemView.note_textview_notes_title.text = if (data.title.isNotEmpty()) {
                data.title
            } else {
                context.getString(R.string.general_text_empty_notestitle)
            }
            itemView.note_textview_notes_type.text = AppUtils.getTranslationType(context, data.type)
            itemView.note_textview_notes_islocked.isGone = data.isLocked.not()

            with(itemView.note_imageview_notes_image) {
                isGone = data.isLocked || data.image.isEmpty()
                load(data.image)
            }

            with(itemView.note_textview_notes_value) {
                text = data.content.stripHtml()
                isGone = data.isLocked
            }

            itemView.setOnClickListener {
                itemListener.invoke(data, adapterPosition, itemView)
            }
        }
    }
}