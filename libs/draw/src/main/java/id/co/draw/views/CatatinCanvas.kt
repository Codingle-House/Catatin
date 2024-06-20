package id.co.draw.views

import Catatin.R
import Catatin.databinding.CatatinItemColorsBinding
import Catatin.databinding.CatatinLayoutCanvasBinding
import android.content.Context
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import id.catat.uikit.adapter.GenericRecyclerViewAdapter
import id.co.catatin.colorpicker.ColorPickerDialog
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.changeDrawableColorCompat
import id.co.catatin.core.ext.getColorCompat
import id.co.draw.views.CatatinCanvas.COLORS.BACKGROUND
import id.co.draw.views.CatatinCanvas.COLORS.PEN
import id.co.draw.views.CatatinCanvas.PROGRESS.OPACITY
import id.co.draw.views.CatatinCanvas.PROGRESS.STROKE

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

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    private val binding by lazy {
        CatatinLayoutCanvasBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val diffUtilCallback by lazy { DiffCallback() }

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
        GenericRecyclerViewAdapter<Int, CatatinItemColorsBinding>(
            diffCallback = diffUtilCallback,
            bindingInflater = { inflater, viewGroup, attachToParent ->
                CatatinItemColorsBinding.inflate(inflater, viewGroup, attachToParent)
            },
            onBind = { data, _, view ->
                with(view.catatinCircleviewColor) {
                    setColor(data)
                    setCircleRadius(80F)
                }
            },
            itemListener = { data, _, _ -> handleColorClickListener(data) }
        )
    }

    private var latestOpacity = 100
    private var latestStrokeWidth = 30

    private var isEraserActive = false
    private var accentColor: Int = BLACK
    private var selectedProgress = STROKE
    private var selectedColorTools = PEN

    enum class PROGRESS {
        STROKE,
        OPACITY
    }

    enum class COLORS {
        PEN,
        BACKGROUND
    }

    init {
        setupInitialDrawingCanvas()
        setupCanvasTooling()
    }

    private fun setupInitialDrawingCanvas() = with(binding) {
        catatinImageviewPencil.apply {
            setBackgroundResource(R.drawable.draw_bg_tool_active)
            changeDrawableColorCompat(accentColor)
        }
        with(catatinCircleviewPencolor) {
            setColor(accentColor)
            setCircleRadius(DEFAULT_COLOR_RADIUS)
        }
        catatinCircleviewPreview.setColor(accentColor)
    }

    private fun setupCanvasTooling() {
        setupEraserActionListener()
        setupPencilActionListener()
        setupRecyclerviewColors()
        setupPaintActionListener()
        setupPenColorActionListener()
        setupUndoAndRedoToolActionListener()
        setupStrokeOpacityToolActionListener()
        setupColorPickerActionListener()
    }

    private fun setupRecyclerviewColors() {
        binding.catatinRecyclerviewColors.run {
            adapter = colorsAdapter.apply { setData(colors) }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupEraserActionListener() = with(binding) {
        catatinImageviewEraser.apply {
            setOnClickListener {
                isEraserActive = isEraserActive.not()
                toggleEraserAction(isEraserActive)
                catatinImageviewPencil.toggleEraserAction(isEraserActive.not())
                catatinDrawView.toggleEraser()
            }

            setOnLongClickListener {
                catatinDrawView.clearCanvas()
                true
            }
        }
    }

    private fun setupPencilActionListener() = with(binding) {
        catatinImageviewPencil.apply {
            setOnClickListener {
                isEraserActive = false
                toggleEraserAction(isEraserActive.not())
                catatinImageviewEraser.toggleEraserAction(isEraserActive)
                catatinDrawView.drawingActive()
            }
        }
    }

    private fun setupPaintActionListener() {
        with(binding.catatinImageviewPaint) {
            setOnClickListener {
                binding.catatinLinearlayoutColors.isGone = false
                setBackgroundResource(R.drawable.draw_bg_tool_active)
                changeDrawableColorCompat(accentColor)
                selectedColorTools = BACKGROUND
                binding.catatinRelativelayoutSpinner.isGone = true

                with(binding.catatinImageviewStroke) {
                    setBackgroundResource(R.drawable.draw_bg_tool_default)
                    changeDrawableColorCompat(WHITE)
                }

                with(binding.catatinImageviewOpacity) {
                    setBackgroundResource(R.drawable.draw_bg_tool_default)
                    changeDrawableColorCompat(WHITE)
                }

            }
        }
    }

    private fun setupPenColorActionListener() {
        binding.catatinCircleviewPencolor.setOnClickListener {
            selectedColorTools = PEN
            binding.catatinLinearlayoutColors.isGone = false
            binding.catatinRelativelayoutSpinner.isGone = true

            with(binding.catatinImageviewStroke) {
                setBackgroundResource(R.drawable.draw_bg_tool_default)
                changeDrawableColorCompat(WHITE)
            }

            with(binding.catatinImageviewOpacity) {
                setBackgroundResource(R.drawable.draw_bg_tool_default)
                changeDrawableColorCompat(WHITE)
            }

            with(binding.catatinImageviewPaint) {
                setBackgroundResource(R.drawable.draw_bg_tool_default)
                changeDrawableColorCompat(WHITE)
            }
        }
    }

    private fun ImageView.toggleEraserAction(isActive: Boolean) {
        setBackgroundResource(
            if (isActive) R.drawable.draw_bg_tool_active
            else R.drawable.draw_bg_tool_default
        )
        changeDrawableColorCompat(if (isActive) accentColor else WHITE)
    }

    private fun setupUndoAndRedoToolActionListener() = with(binding) {
        catatinImagaviewUndo.setOnClickListener { catatinDrawView.undo() }
        catatinImagaviewRedo.setOnClickListener { catatinDrawView.redo() }
    }

    private fun setupStrokeOpacityToolActionListener() {
        with(binding.catatinImageviewStroke) {
            setOnClickListener {
                selectedProgress = STROKE
                binding.catatinSeekbarProgress.progress = latestStrokeWidth
                binding.catatinRelativelayoutSpinner.isGone = false

                setBackgroundResource(R.drawable.draw_bg_tool_active)
                changeDrawableColorCompat(accentColor)

                binding.catatinImageviewOpacity.setBackgroundResource(R.drawable.draw_bg_tool_default)
                binding.catatinImageviewOpacity.changeDrawableColorCompat(WHITE)
            }
        }

        with(binding.catatinImageviewOpacity) {
            setOnClickListener {
                selectedProgress = OPACITY
                binding.catatinSeekbarProgress.progress = latestOpacity
                binding.catatinRelativelayoutSpinner.isGone = false

                setBackgroundResource(R.drawable.draw_bg_tool_active)
                changeDrawableColorCompat(accentColor)

                binding.catatinImageviewStroke.setBackgroundResource(R.drawable.draw_bg_tool_default)
                binding.catatinImageviewStroke.changeDrawableColorCompat(WHITE)
            }
        }

        binding.catatinImageviewStroke.setOnClickListener {
            binding.catatinRelativelayoutSpinner.isGone = true

            with(binding.catatinImageviewStroke) {
                setBackgroundResource(R.drawable.draw_bg_tool_default)
                changeDrawableColorCompat(WHITE)
            }

            with(binding.catatinImageviewOpacity) {
                setBackgroundResource(R.drawable.draw_bg_tool_default)
                changeDrawableColorCompat(WHITE)
            }
        }

        binding.catatinSeekbarProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (selectedProgress) {
                    STROKE -> {
                        latestStrokeWidth = progress
                        binding.catatinDrawView.setStrokeWidth(progress.toFloat())
                        binding.catatinCircleviewPreview.setCircleRadius(progress.toFloat())
                    }

                    else -> {
                        latestOpacity = progress
                        binding.catatinDrawView.setAlpha(progress)
                        binding.catatinCircleviewPreview.setAlpha(progress)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setupColorPickerActionListener() {
        binding.catatinImageviwColorpicker.setOnClickListener {
            ColorPickerDialog(context, accentColor) { color ->
                color?.let { data ->
                    when (selectedColorTools) {
                        PEN -> {
                            binding.catatinDrawView.setColor(data)
                            binding.catatinCircleviewPencolor.setColor(data)
                            binding.catatinCircleviewPreview.setColor(data)
                        }

                        BACKGROUND -> binding.catatinDrawView.setCanvassBackground(data)
                    }
                    binding.catatinLinearlayoutColors.isGone = true
                }
            }.show()
        }
    }

    private fun handleColorClickListener(color: Int) {
        when (selectedColorTools) {
            PEN -> {
                binding.catatinDrawView.setColor(color)
                binding.catatinCircleviewPencolor.setColor(color)
                binding.catatinCircleviewPreview.setColor(color)
            }

            BACKGROUND -> {
                with(binding.catatinImageviewPaint) {
                    setBackgroundResource(R.drawable.draw_bg_tool_default)
                    changeDrawableColorCompat(Color.WHITE)
                }
                binding.catatinDrawView.setCanvassBackground(color)
            }
        }
        binding.catatinLinearlayoutColors.isGone = true
    }

    fun setCanvassBackground(
        canvasColor: Int = BLACK,
        toolBackgroundColor: Int = BLACK,
        additionalToolBackgroundColor: Int = BLACK
    ) {
        accentColor = additionalToolBackgroundColor

        binding.catatinCircleviewPencolor.setColor(additionalToolBackgroundColor)
        binding.catatinLinearlayoutColors.setBackgroundColor(additionalToolBackgroundColor)
        binding.catatinScrollviewTooling.setBackgroundColor(toolBackgroundColor)
        binding.catatinLinearlayout.setBackgroundColor(toolBackgroundColor)
        binding.catatinRelativelayoutSpinner.setBackgroundColor(additionalToolBackgroundColor)

        with(binding.catatinDrawView) {
            setCanvassBackground(canvasColor)
            setColor(additionalToolBackgroundColor)
        }
    }

    companion object {
        private const val DEFAULT_COLOR_RADIUS = 80F
    }
}