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
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.commons.GeneralRecyclerView
import id.co.catatin.core.ext.getColorCompat
import id.co.draw.R
import id.co.draw.widget.CircleView
import id.co.draw.widget.DrawView
import kotlinx.android.synthetic.main.catatin_item_colors.view.*
import kotlinx.android.synthetic.main.catatin_layout_canvas.view.*

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

    private val diffUtilCallback by lazy {
        DiffCallback()
    }

    private val colors by lazy {
        mutableListOf(
            context.getColorCompat(R.color.color1),
            context.getColorCompat(R.color.color2),
            context.getColorCompat(R.color.color3),
            context.getColorCompat(R.color.color4),
            context.getColorCompat(R.color.color5),
            context.getColorCompat(R.color.color6),
            context.getColorCompat(R.color.color7)
        )
    }

    private val colorsAdapter by lazy {
        GeneralRecyclerView<Int>(
            diffCallback = diffUtilCallback,
            holderResId = R.layout.catatin_item_colors,
            onBind = { data, view ->
                with(view.catatin_circleview_color) {
                    setColor(data)
                    setCircleRadius(80F)
                }
            },
            itemListener = { data, pos, _ ->
                if (isPaintSelected) {
                    drawView.setCanvassBackground(data)
                } else if (isPenColorSelected) {
                    drawView.setColor(data)
                    penColor.setColor(data)
                }
                linearColor.isGone = true
            }
        )
    }

    private lateinit var drawView: DrawView
    private lateinit var toolPencil: ImageView
    private lateinit var toolEraser: ImageView
    private lateinit var toolPaint: ImageView
    private lateinit var recyclerviewColor: RecyclerView
    private lateinit var linearColor: LinearLayout
    private lateinit var penColor: CircleView

    private var isEraserActive = false
    private var canvasBackgroundColor: Int = Color.BLACK
    private var isPaintSelected = false
    private var isPenColorSelected = false

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.catatin_layout_canvas, this, true)
        inflateDrawView()
        setupInitialDrawingCanvas()
        setupCanvasTooling()
    }

    private fun inflateDrawView() {
        drawView = findViewById(R.id.catatin_draw_view)
        toolPencil = findViewById(R.id.catatin_imageview_pencil)
        toolEraser = findViewById(R.id.catatin_imageview_eraser)
        toolPaint = findViewById(R.id.catatin_imageview_paint)
        recyclerviewColor = findViewById(R.id.catatin_recyclerview_colors)
        linearColor = findViewById(R.id.catatin_linearlayout_colors)
        penColor = findViewById(R.id.catatin_circleview_pencolor)
    }

    private fun setupInitialDrawingCanvas() {
        toolPencil.apply {
            setBackgroundResource(R.drawable.draw_bg_tool_active)
            DrawableCompat.setTint(DrawableCompat.wrap(drawable), canvasBackgroundColor)
        }

        with(penColor) {
            setColor(Color.WHITE)
            setCircleRadius(80F)
        }
    }

    private fun setupCanvasTooling() {
        setupEraserActionListener()
        setupPencilActionListener()
        setupRecyclerviewColors()
        setupPaintActionListener()
        setupPenColorActionListener()
    }

    private fun setupRecyclerviewColors() {
        recyclerviewColor.run {
            adapter = colorsAdapter.apply {
                setData(colors)
            }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
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

    private fun setupPaintActionListener() {
        toolPaint.setOnClickListener {
            isPenColorSelected = false
            isPaintSelected = isPaintSelected.not()
            linearColor.isGone = !isPaintSelected
        }
    }

    private fun setupPenColorActionListener() {
        penColor.setOnClickListener {
            isPaintSelected = false
            isPenColorSelected = isPenColorSelected.not()
            linearColor.isGone = !isPenColorSelected
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

    fun setCanvassBackground(
        canvasColor: Int = Color.BLACK,
        toolBackgroundColor: Int = Color.BLACK
    ) {
        canvasBackgroundColor = canvasColor
        drawView.setCanvassBackground(canvasColor)
        linearColor.setBackgroundColor(canvasColor)
        catatin_linearlayout?.setBackgroundColor(toolBackgroundColor)
    }
}