package id.co.draw.views

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.commons.GeneralRecyclerView
import id.co.catatin.core.ext.changeDrawableColorCompat
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
                    with(toolPaint) {
                        setBackgroundResource(R.drawable.draw_bg_tool_default)
                        changeDrawableColorCompat(Color.WHITE)
                    }
                    drawView.setCanvassBackground(data)
                } else if (isPenColorSelected) {
                    drawView.setColor(data)
                    penColor.setColor(data)
                    circleViewPreview.setColor(data)
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
    private lateinit var redoTool: ImageView
    private lateinit var undoTool: ImageView
    private lateinit var strokeTool: ImageView
    private lateinit var progressRelativeLayout: RelativeLayout
    private lateinit var circleViewPreview: CircleView
    private lateinit var strokeClose: ImageView
    private lateinit var seekBarProgress: AppCompatSeekBar
    private lateinit var scrollViewTool: HorizontalScrollView
    private lateinit var opacityTool: ImageView

    private var latestOpacity = 100
    private var latestStrokeWidth = 30

    private var isEraserActive = false
    private var canvasBackgroundColor: Int = Color.BLACK
    private var isPaintSelected = false
    private var isPenColorSelected = false

    private var selectedProgress = PROGRESS.STROKE

    enum class PROGRESS {
        STROKE,
        OPACITY
    }

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
        undoTool = findViewById(R.id.catatin_imagaview_undo)
        redoTool = findViewById(R.id.catatin_imagaview_redo)
        strokeTool = findViewById(R.id.catatin_imageview_stroke)
        progressRelativeLayout = findViewById(R.id.catatin_relativelayout_spinner)
        circleViewPreview = findViewById(R.id.catatin_circleview_preview)
        strokeClose = findViewById(R.id.catatin_imageview_close)
        seekBarProgress = findViewById(R.id.catatin_seekbar_progress)
        scrollViewTool = findViewById(R.id.catatin_scrollview_tooling)
        opacityTool = findViewById(R.id.catatin_imageview_opacity)
    }

    private fun setupInitialDrawingCanvas() {
        toolPencil.apply {
            setBackgroundResource(R.drawable.draw_bg_tool_active)
            changeDrawableColorCompat(canvasBackgroundColor)
        }

        with(penColor) {
            setColor(Color.WHITE)
            setCircleRadius(DEFAULT_COLOR_RADIUS)
        }

        circleViewPreview.setColor(Color.WHITE)
    }

    private fun setupCanvasTooling() {
        setupEraserActionListener()
        setupPencilActionListener()
        setupRecyclerviewColors()
        setupPaintActionListener()
        setupPenColorActionListener()
        setupUndoAndRedoToolActionListener()
        setupStrokeOpacityToolActionListener()
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
        with(toolPaint) {
            setOnClickListener {
                linearColor.isGone = false

                setBackgroundResource(R.drawable.draw_bg_tool_active)
                changeDrawableColorCompat(canvasBackgroundColor)

                isPenColorSelected = false
                isPaintSelected = true
            }
        }
    }

    private fun setupPenColorActionListener() {
        penColor.setOnClickListener {
            isPaintSelected = false
            isPenColorSelected = true
            linearColor.isGone = false
        }
    }

    private fun ImageView.toggleEraserAction(isActive: Boolean) {
        setBackgroundResource(
            if (isActive) R.drawable.draw_bg_tool_active
            else R.drawable.draw_bg_tool_default
        )
        changeDrawableColorCompat(if (isActive) canvasBackgroundColor else Color.WHITE)
    }

    private fun setupUndoAndRedoToolActionListener() {
        undoTool.setOnClickListener {
            drawView.undo()
        }

        redoTool.setOnClickListener {
            drawView.redo()
        }
    }

    private fun setupStrokeOpacityToolActionListener() {
        with(strokeTool) {
            setOnClickListener {
                Log.e("IRFAN", "WIDTH = $latestStrokeWidth: ");
                selectedProgress = PROGRESS.STROKE
                seekBarProgress.progress = latestStrokeWidth
                progressRelativeLayout.isGone = false

                setBackgroundResource(R.drawable.draw_bg_tool_active)
                changeDrawableColorCompat(canvasBackgroundColor)

                opacityTool.setBackgroundResource(R.drawable.draw_bg_tool_default)
                opacityTool.changeDrawableColorCompat(Color.WHITE)
            }
        }

        with(opacityTool) {
            setOnClickListener {
                Log.e("IRFAN", "OPACITY = $latestOpacity: ");
                selectedProgress = PROGRESS.OPACITY
                seekBarProgress.progress = latestOpacity
                progressRelativeLayout.isGone = false

                setBackgroundResource(R.drawable.draw_bg_tool_active)
                changeDrawableColorCompat(canvasBackgroundColor)

                strokeTool.setBackgroundResource(R.drawable.draw_bg_tool_default)
                strokeTool.changeDrawableColorCompat(Color.WHITE)
            }
        }

        strokeClose.setOnClickListener {
            progressRelativeLayout.isGone = true
        }

        seekBarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (selectedProgress) {
                    PROGRESS.STROKE -> {
                        latestStrokeWidth = progress
                        drawView.setStrokeWidth(progress.toFloat())
                        circleViewPreview.setCircleRadius(progress.toFloat())
                    }
                    else -> {
                        latestOpacity = progress
                        drawView.setAlpha(progress)
                        circleViewPreview.setAlpha(progress)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    fun setCanvassBackground(
        canvasColor: Int = Color.BLACK,
        toolBackgroundColor: Int = Color.BLACK
    ) {
        canvasBackgroundColor = canvasColor
        drawView.setCanvassBackground(canvasColor)
        linearColor.setBackgroundColor(canvasColor)
        scrollViewTool.setBackgroundColor(toolBackgroundColor)
        catatin_linearlayout.setBackgroundColor(toolBackgroundColor)
        progressRelativeLayout.setBackgroundColor(canvasColor)
    }

    companion object {
        private const val DEFAULT_COLOR_RADIUS = 80F
    }
}