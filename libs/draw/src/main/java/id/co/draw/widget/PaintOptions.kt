package id.co.draw.widget

import android.graphics.Color

data class PaintOptions(
    var color: Int = Color.WHITE,
    var strokeWidth: Float = 8f,
    var alpha: Int = 255,
    var isEraserOn: Boolean = false
)
