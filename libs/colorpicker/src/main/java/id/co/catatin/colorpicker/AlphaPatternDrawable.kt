package id.co.catatin.colorpicker

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.ceil

class AlphaPatternDrawable(rectangleSize: Int) : Drawable() {
    private var rectangleSize = 10
    private val paint = Paint()
    private val paintWhite = Paint()
    private val paintGray = Paint()
    private var numRectanglesHorizontal = 0
    private var numRectanglesVertical = 0

    private var bitmap: Bitmap? = null
    override fun draw(canvas: Canvas) {
        bitmap?.let {
            canvas.drawBitmap(it, null, bounds, paint)
        }
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun setAlpha(alpha: Int) {
        throw UnsupportedOperationException("Alpha is not supported by this drawwable.")
    }

    override fun setColorFilter(cf: ColorFilter?) {
        throw UnsupportedOperationException("ColorFilter is not supported by this drawwable.")
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val height = bounds.height()
        val width = bounds.width()
        numRectanglesHorizontal = ceil((width / rectangleSize).toDouble()).toInt()
        numRectanglesVertical = ceil(height / rectangleSize.toDouble()).toInt()
        generatePatternBitmap()
    }

    private fun generatePatternBitmap() {
        if (bounds.width() <= 0 || bounds.height() <= 0) {
            return
        }
        bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888)
        bitmap?.let {
            val canvas = Canvas(it)
            val r = Rect()
            var verticalStartWhite = true
            for (i in 0..numRectanglesVertical) {
                var isWhite = verticalStartWhite
                for (j in 0..numRectanglesHorizontal) {
                    r.top = i * rectangleSize
                    r.left = j * rectangleSize
                    r.bottom = r.top + rectangleSize
                    r.right = r.left + rectangleSize
                    canvas.drawRect(r, if (isWhite) paintWhite else paintGray)
                    isWhite = !isWhite
                }
                verticalStartWhite = !verticalStartWhite
            }
        }
    }

    init {
        this.rectangleSize = rectangleSize
        paintWhite.color = -0x1
        paintGray.color = -0x343435
    }
}