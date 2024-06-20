package `in`.catat.presentation.todo

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.getDrawableCompat
import id.co.catatin.core.ext.reverseStrikeThroughAnimation
import id.co.catatin.core.ext.startStrikeThroughAnimation
import `in`.catat.R
import `in`.catat.data.dto.TodoDto
import `in`.catat.databinding.ItemNotesTodoBinding


/**
 * Created by pertadima on 05,November,2020
 */

class TodoNoteAdapter(
    private val context: Context,
    private val diffCallback: DiffCallback,
    private val itemListener: (TodoDto, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run {} },
    private val itemActionListener: (TodoDto, pos: Int, View, TodoAction) -> Unit = { _, _, _, _ -> kotlin.run {} }
) : RecyclerView.Adapter<TodoNoteAdapter.ItemViewHolder>() {

    private val dataSet: MutableList<TodoDto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = ItemNotesTodoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(dataSet[holder.adapterPosition])
    }

    override fun getItemCount(): Int = dataSet.size

    override fun getItemId(position: Int): Long = dataSet[position].id.hashCode().toLong()

    fun setData(newDataSet: List<TodoDto>) {
        calculateDiff(newDataSet)
    }

    private fun calculateDiff(newDataSet: List<TodoDto>) {
        diffCallback.setList(dataSet, newDataSet)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(dataSet) {
            clear()
            addAll(newDataSet)
        }
        result.dispatchUpdatesTo(this)
    }

    inner class ItemViewHolder(private val binding: ItemNotesTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: TodoDto) {
            binding.todoImageviewCircle.changeImageResourceWithAnimation(
                context, context.getDrawableCompat(
                    if (data.isDone) {
                        R.drawable.uikit_ic_circle_outline
                    } else {
                        R.drawable.general_ic_circle_fill
                    }
                )
            )
            with(binding.todoTextviewTitle) {
                text = data.name
                if (data.isDone) startStrikeThroughAnimation() else reverseStrikeThroughAnimation()
            }
            with(binding.todoTextviewAlarm) {
                isGone = data.reminderDate.isEmpty()
                text = context.getString(R.string.todo_text_alarm, data.reminderDate)
            }
            binding.root.setOnClickListener {
                itemListener.invoke(data, adapterPosition, itemView)
            }
            binding.todoImageviewDelete.setOnClickListener {
                itemActionListener.invoke(data, adapterPosition, itemView, TodoAction.OnDeleteTodo)
            }
            binding.todoImageviewEdit.setOnClickListener {
                itemActionListener.invoke(data, adapterPosition, itemView, TodoAction.OnEditTodo)
            }
        }
    }

    private fun ImageView.changeImageResourceWithAnimation(
        context: Context?,
        new_image: Drawable?
    ) {
        val animOut: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale_out)
        val animIn: Animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale_in)
        animOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                setImageDrawable(new_image)
                animIn.setAnimationListener(object : AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationRepeat(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {}
                })
                startAnimation(animIn)
            }
        })
        startAnimation(animOut)
    }

    sealed class TodoAction {
        object OnEditTodo : TodoAction()
        object OnDeleteTodo : TodoAction()
    }
}