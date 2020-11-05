package `in`.catat.presentation.todo

import `in`.catat.R
import `in`.catat.data.dto.TodoDto
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.getDrawableCompat
import kotlinx.android.synthetic.main.item_notes_todo.view.*


/**
 * Created by pertadima on 05,November,2020
 */

class TodoNoteAdapter(
    private val context: Context,
    private val diffCallback: DiffCallback,
    private val itemListener: (TodoDto, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run {} },
) : RecyclerView.Adapter<TodoNoteAdapter.ItemViewHolder>() {

    private val dataSet: MutableList<TodoDto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView =
            inflater.inflate(R.layout.item_notes_todo, parent, false)
        return ItemViewHolder(itemView)
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

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(data: TodoDto) {

            itemView.todo_imageview_circle.changeImageResourceWithAnimation(
                context, context.getDrawableCompat(
                    if (data.isDone) {
                        R.drawable.uikit_ic_circle_outline
                    } else {
                        R.drawable.general_ic_circle_fill
                    }
                )
            )
            with(itemView.todo_textview_title) {
                text = data.name
                if (data.isDone) startStrikeThroughAnimation() else reverseStrikeThroughAnimation()
            }
            with(itemView.todo_textview_alarm) {
                isGone = data.reminderDate.isEmpty()
                text = context.getString(R.string.todo_text_alarm, data.reminderDate)
            }
            itemView.todo_view_line.isGone = itemCount - 1 == adapterPosition
            itemView.setOnClickListener {
                itemListener.invoke(data, adapterPosition, itemView)
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

    private fun TextView.startStrikeThroughAnimation(): ValueAnimator {
        val span = SpannableString(text)
        val strikeSpan = StrikethroughSpan()
        val animator = ValueAnimator.ofInt(text.length)
        animator.addUpdateListener {
            span.setSpan(strikeSpan, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            text = span
            invalidate()
        }
        animator.start()
        return animator
    }

    private fun TextView.reverseStrikeThroughAnimation(): ValueAnimator {
        val span = SpannableString(text.toString())
        val strikeSpan = StrikethroughSpan()
        val animator = ValueAnimator.ofInt(text.length, 0)
        animator.addUpdateListener {
            span.setSpan(strikeSpan, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            text = span
            invalidate()
        }
        animator.start()
        return animator
    }
}