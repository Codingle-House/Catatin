package id.co.draw.views

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import id.co.draw.R
import kotlinx.android.synthetic.main.layout_catatin_canvas.view.*

/**
 * Created by pertadima on 08,September,2020
 */

class CatatinCanvas : LinearLayout {
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_catatin_canvas, this, true)
        setupInitialDrawingCanvas()
    }

    private fun setupInitialDrawingCanvas() {

    }

    fun setCanvassBackground(backgroundColor: Int) {
        catatin_draw_view.setCanvassBackground(backgroundColor)
    }
}