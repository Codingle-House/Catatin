package id.co.catatin.core.ext

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Created by pertadima on 09,September,2020
 */

fun Context.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun ImageView.changeDrawableColorCompat(color: Int) {
    return DrawableCompat.setTint(DrawableCompat.wrap(drawable), color)
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(message), duration).show()
}

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

fun Context.toDp(value: Float): Int {
    val scale: Float = resources.displayMetrics.density
    return (value * scale + 0.5f).toInt()
}

fun Activity.checkDeviceDensity(isSmallScreen: () -> Unit) {
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)

    val widthPixels = metrics.widthPixels
    val heightPixels = metrics.heightPixels

    val scaleFactor = metrics.density

    val widthDp = widthPixels / scaleFactor
    val heightDp = heightPixels / scaleFactor

    val smallestWidth: Float = widthDp.coerceAtMost(heightDp)

    if (smallestWidth <= 320) {
        isSmallScreen.invoke()
    }
}