package id.co.draw.views

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.graphics.drawable.DrawableCompat
import id.co.draw.R
import id.co.draw.widget.DrawView

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

    private lateinit var drawView: DrawView
    private lateinit var toolPencil: ImageView
    private lateinit var toolEraser: ImageView

    private var isEraserActive = false
    private var canvasBackgroundColor: Int = Color.BLACK

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_catatin_canvas, this, true)
        inflateDrawView()
        setupInitialDrawingCanvas()
        setupCanvasTooling()
    }

    private fun inflateDrawView() {
        drawView = findViewById(R.id.catatin_draw_view)
        toolPencil = findViewById<ImageView>(R.id.catatin_imageview_pencil)
        toolEraser = findViewById<ImageView>(R.id.catatin_imageview_eraser)
    }

    private fun setupInitialDrawingCanvas() {
        toolPencil.apply {
            setBackgroundResource(R.drawable.draw_bg_tool_active)
            DrawableCompat.setTint(DrawableCompat.wrap(drawable), canvasBackgroundColor)
        }
    }

    private fun setupCanvasTooling() {
        setupEraserActionListener()
        setupPencilActionListener()
    }

    private fun setupEraserActionListener() {
        toolEraser.apply {
            setOnClickListener {
                isEraserActive = isEraserActive.not()
                toggleEraserAction(isEraserActive)
                toolPencil.toggleEraserAction(isEraserActive.not())
                drawView.toggleEraser()
            }

            setOnLongClickListener {
                drawView.clearCanvas()
                true
            }
        }
    }

    private fun setupPencilActionListener() {
        toolPencil.apply {
            setOnClickListener {
                isEraserActive = false
                toggleEraserAction(isEraserActive.not())
                toolEraser.toggleEraserAction(isEraserActive)
                drawView.drawingActive()
            }
        }
    }

    private fun ImageView.toggleEraserAction(isActive: Boolean) {
        setBackgroundResource(
            if (isActive) R.drawable.draw_bg_tool_active
            else R.drawable.draw_bg_tool_default
        )
        DrawableCompat.setTint(
            DrawableCompat.wrap(drawable),
            if (isActive) canvasBackgroundColor else Color.WHITE
        )
    }

    fun setCanvassBackground(backgroundColor: Int) {
        canvasBackgroundColor = backgroundColor
        drawView.setCanvassBackground(backgroundColor)
    }
}