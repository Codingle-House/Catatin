package `in`.catat.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.stripHtml
import `in`.catat.R
import `in`.catat.data.dto.NoteDto
import `in`.catat.databinding.ItemNotesCardBinding
import `in`.catat.util.constants.AppUtils


/**
 * Created by pertadima on 05,November,2020
 */

class NoteAdapter(
    private val context: Context,
    private val diffCallback: DiffCallback,
    private val itemListener: (NoteDto, pos: Int, ItemNotesCardBinding) -> Unit = { _, _, _ -> run {} }
) : RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {

    private val dataSet: MutableList<NoteDto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ItemNotesCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(itemBinding)
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

    inner class ItemViewHolder(private val binding: ItemNotesCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: NoteDto) {
            binding.noteTextviewNotesDatetime.text = data.createdAt
            binding.noteTextviewNotesTitle.text =
                data.title.ifEmpty { context.getString(R.string.general_text_empty_notestitle) }
            binding.noteTextviewNotesType.text = AppUtils.getTranslationType(context, data.type)
            binding.noteTextviewNotesIslocked.isGone = data.isLocked.not()

            with(binding.noteImageviewNotesImage) {
                isGone = data.isLocked || data.image.isEmpty()
                load(data.image)
            }

            with(binding.noteTextviewNotesValue) {
                text = data.content.stripHtml()
                isGone = data.isLocked
            }

            binding.root.setOnClickListener { itemListener.invoke(data, adapterPosition, binding) }
        }
    }
}