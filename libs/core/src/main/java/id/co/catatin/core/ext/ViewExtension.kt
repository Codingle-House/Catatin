package id.co.catatin.core.ext

import android.animation.ValueAnimator
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.widget.TextView

/**
 * Created by pertadima on 18,October,2020
 */

fun SpannableString.setSpannableForegroundColor(
    context: Context,
    color: Int = android.R.color.white
) {
    setSpan(
        ForegroundColorSpan(context.getColorCompat(color)),
        0,
        length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun TextView.startStrikeThroughAnimation(): ValueAnimator {
    val span = SpannableString(text)
    val strikeSpan = StrikethroughSpan()
    val animator = ValueAnimator.ofInt(text.length)
    animator.addUpdateListener {
        span.setSpan(strikeSpan, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = span
        invalidate()
    }
    animator.start()
    return animator
}

fun TextView.reverseStrikeThroughAnimation(): ValueAnimator {
    val span = SpannableString(text.toString())
    val strikeSpan = StrikethroughSpan()
    val animator = ValueAnimator.ofInt(text.length, 0)
    animator.addUpdateListener {
        span.setSpan(strikeSpan, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = span
        invalidate()
    }
    animator.start()
    return animator
}

