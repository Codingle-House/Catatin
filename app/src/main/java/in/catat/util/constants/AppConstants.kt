package `in`.catat.util.constants

import `in`.catat.R
import android.content.Context

/**
 * Created by pertadima on 27,October,2020
 */

object AppUtils {
    object AppConstant {
        const val TYPE_NOTE = "note"
        const val TYPE_TODO = "todo"
        const val TYPE_SKETCH = "sketch"
    }

    fun getTranslationType(context: Context, type: String): String {
        return context.getString(
            when (type) {
                appConstant.TYPE_NOTE -> R.string.dialog_title_menu_notes
                appConstant.TYPE_TODO -> R.string.dialog_title_menu_todo
                else -> R.string.dialog_title_menu_draw
            }
        )
    }
}

typealias appConstant = AppUtils.AppConstant