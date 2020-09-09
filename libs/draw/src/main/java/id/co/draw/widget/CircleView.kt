package id.co.draw.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var circleFillPaint = Paint()
    private var outlinePaint = Paint()
    var radius = 30F

    init {
        circleFillPaint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }

        outlinePaint.apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val cX = width.div(2)
        val cY = height.div(2)

        canvas.drawCircle(cX, cY, (radius / 2) + 5, outlinePaint)
        canvas.drawCircle(cX, cY, radius / 2, circleFillPaint)
    }

    fun setCircleRadius(r: Float) {
        radius = r
        invalidate()
    }

    fun setAlpha(newAlpha: Int) {
        val alpha = (newAlpha * 255) / 100
        circleFillPaint.alpha = alpha
        invalidate()
    }

    fun setColor(color: Int) {
        circleFillPaint.color = color
        invalidate()
    }
}