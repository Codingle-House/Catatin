package id.co.catatin.core.ext

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

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