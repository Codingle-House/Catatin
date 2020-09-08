package com.chinalwb.are.spans

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan
import com.chinalwb.are.models.AtItem

class AreAtSpan(atItem: AtItem) : ReplacementSpan(), ARE_Span, ARE_Clickable_Span {
    /**
     * Will be used when generating HTML code for @
     */
    val userKey: String
    val userName: String
    private val mColor: Int
    override fun getSize(
        paint: Paint, text: CharSequence, start: Int, end: Int,
        fm: FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).toInt()
    }

    override fun draw(
        canvas: Canvas, text: CharSequence, start: Int, end: Int,
        x: Float, top: Int, y: Int, bottom: Int, paint: Paint
    ) {
        paint.color = Color.TRANSPARENT
        val width = paint.measureText(text.toString(), start, end)
        canvas.drawRect(x, top.toFloat(), x + width, bottom.toFloat(), paint)
        paint.color = -0x1000000 or mColor
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }

    override fun getHtml(): String {
        val html = StringBuffer()
        html.append("<a")
        html.append(" href=\"#\"")
        html.append(" uKey=\"" + userKey + "\"")
        html.append(" uName=\"" + userName + "\"")
        html.append(String.format(" style=\"color:#%06X;\"", 0xFFFFFF and mColor))
        html.append(">")
        html.append("@" + userName)
        html.append("</a>")
        return html.toString()
    }

    companion object {
        private const val KEY_ATTR = "key"
    }

    init {
        userKey = atItem.mKey
        userName = atItem.mName
        mColor = atItem.mColor
    }
}