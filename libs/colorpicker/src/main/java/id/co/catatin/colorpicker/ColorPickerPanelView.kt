/*
 * Copyright (C) 2010 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package id.co.catatin.colorpicker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

class ColorPickerPanelView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    private var mDensity = 1f
    private var mBorderColor = -0x919192
    private var mColor = -0x1000000
    private var mBorderPaint: Paint? = null
    private var mColorPaint: Paint? = null
    private var mDrawingRect: RectF? = null
    private var mColorRect: RectF? = null
    private var mAlphaPattern: AlphaPatternDrawable? = null

    private fun init() {
        mBorderPaint = Paint()
        mColorPaint = Paint()
        mDensity = context.resources.displayMetrics.density
    }

    override fun onDraw(canvas: Canvas) {
        val rect = mColorRect
        if (BORDER_WIDTH_PX > 0) {
            mBorderPaint?.color = mBorderColor
            mDrawingRect?.let { rect ->
                mBorderPaint?.let {
                    canvas.drawRect(rect, it)
                }
            }
        }
        if (mAlphaPattern != null) {
            mAlphaPattern?.draw(canvas)
        }
        mColorPaint?.color = mColor
        rect?.let { rectF ->
            mColorPaint?.let {
                canvas.drawRect(rectF, it)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mDrawingRect = RectF()
        mDrawingRect?.left = paddingLeft.toFloat()
        mDrawingRect?.right = w - paddingRight.toFloat()
        mDrawingRect?.top = paddingTop.toFloat()
        mDrawingRect?.bottom = h - paddingBottom.toFloat()
        setUpColorRect()
    }

    private fun setUpColorRect() {
        val dRect = mDrawingRect
        val left = dRect?.left ?: 0 + BORDER_WIDTH_PX
        val top = dRect?.top ?: 0 + BORDER_WIDTH_PX
        val bottom = dRect?.bottom ?: 0 - BORDER_WIDTH_PX
        val right = dRect?.right ?: 0 - BORDER_WIDTH_PX
        mColorRect = RectF(left, top, right, bottom)
        mAlphaPattern = AlphaPatternDrawable((5 * mDensity).toInt())
        mAlphaPattern?.setBounds(
            mColorRect?.left?.roundToInt() ?: 0,
            mColorRect?.top?.roundToInt() ?: 0,
            mColorRect?.right?.roundToInt() ?: 0,
            mColorRect?.bottom?.roundToInt() ?: 0
        )
    }

    /**
     * Get the color currently show by this view.
     *
     * @return
     */
    /**
     * Set the color that should be shown by this view.
     *
     * @param color
     */
    var color: Int
        get() = mColor
        set(color) {
            mColor = color
            invalidate()
        }

    /**
     * Get the color of the border surrounding the panel.
     */
    /**
     * Set the color of the border surrounding the panel.
     *
     * @param color
     */
    var borderColor: Int
        get() = mBorderColor
        set(color) {
            mBorderColor = color
            invalidate()
        }

    companion object {
        /**
         * The width in pixels of the border
         * surrounding the color panel.
         */
        private const val BORDER_WIDTH_PX = 1f
    }

    init {
        init()
    }
}