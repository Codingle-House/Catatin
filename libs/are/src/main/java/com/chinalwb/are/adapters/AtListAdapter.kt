package com.chinalwb.are.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.chinalwb.are.R
import com.chinalwb.are.models.AtItem
import java.util.*

/**
 * Created by wliu on 2018/2/4.
 */
class AtListAdapter(
    private val context: Context,
    private var itemsList: ArrayList<AtItem>?
) : BaseAdapter() {
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun setData(itemsList: ArrayList<AtItem>?) {
        this.itemsList = itemsList
    }

    override fun getCount(): Int {
        return if (itemsList == null) 0 else itemsList?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): AtItem? {
        itemsList?.let {
            return if ((itemsList?.size ?: 0) < position) {
                itemsList?.get(position)
            } else {
                null
            }
        } ?: kotlin.run {
            return null
        }
    }

    override fun getView(
        position: Int,
        convertView: View,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.are_adapter_at_item_view, parent, false)
            viewHolder = ViewHolder()
            viewHolder.imageView =
                convertView.findViewById(R.id.are_view_at_item_image)
            viewHolder.textView = convertView.findViewById(R.id.are_view_at_item_name)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        val item = getItem(position)
        item?.mIconId?.let {
            viewHolder.imageView?.setImageResource(it)
        }
        viewHolder.textView?.text = item?.mName
        return convertView
    }

    internal inner class ViewHolder {
        var imageView: ImageView? = null
        var textView: TextView? = null
    }

}