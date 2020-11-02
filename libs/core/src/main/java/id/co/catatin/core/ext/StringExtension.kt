package id.co.catatin.core.ext

import android.text.Html

/**
 * Created by pertadima on 02,November,2020
 */

fun String.stripHtml(): String =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString().replace("\n", "").trim()
    } else {
        Html.fromHtml(this).toString().replace("\n", "").trim()
    }