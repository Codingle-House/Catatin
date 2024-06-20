package id.catat.uikit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import id.co.catatin.core.commons.DiffCallback

/**
 * Created by pertadima on 23,August,2020
 */

class GenericRecyclerViewAdapter<T : Any, VB : ViewBinding>(
    private val diffCallback: DiffCallback,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val fadeAnimation: Boolean = true,
    private val onBind: (T, pos: Int, VB) -> Unit,
    private val itemListener: (T, pos: Int, VB) -> Unit = { _, _, _ -> run {} }
) : RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder<T, VB>>() {
    private val listData = mutableListOf<T>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder<T, VB> {
        val binding = bindingInflater.invoke(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder<T, VB>, p1: Int) {
        if (fadeAnimation) setFadeAnimation(p0.itemView)
        p0.bindView(listData[p0.bindingAdapterPosition], onBind, itemListener)
    }

    override fun getItemCount(): Int = listData.size

    fun getItem() = listData

    fun updateData(datas: List<T>) {
        with(listData) {
            clearData()
            addAll(datas)
        }
        notifyItemRangeInserted(0, datas.size)
    }

    fun setData(datas: List<T>) {
        calculateDiff(datas)
    }

    fun setNotifyItemChanged(datas: List<T>, pos: Int) {
        with(listData) {
            clear()
            addAll(datas)
        }
        notifyItemChanged(pos)
    }

    fun addData(newDatas: List<T>) {
        val list = ArrayList(this.listData)
        list.addAll(newDatas)
        calculateDiff(list)
    }

    fun clearData() {
        calculateDiff(emptyList())
    }

    private fun calculateDiff(newDatas: List<T>) {
        diffCallback.setList(listData, newDatas)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listData) {
            clear()
            addAll(newDatas)
        }
        result.dispatchUpdatesTo(this)
    }

    class ViewHolder<T : Any, VB : ViewBinding>(
        private val binding: VB
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(
            item: T,
            onBind: (T, pos: Int, VB) -> Unit,
            itemListener: (T, pos: Int, VB) -> Unit
        ) {
            with(itemView) {
                onBind.invoke(item, adapterPosition, binding)
                setOnClickListener { itemListener.invoke(item, adapterPosition, binding) }
            }
        }

    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 400
        view.startAnimation(anim)
    }
}