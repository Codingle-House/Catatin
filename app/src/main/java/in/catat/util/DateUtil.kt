package `in`.catat.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pertadima on 27,August,2020
 */

object DateUtil {
    fun getCurrentDate(dateFormat: String = DEFAULT_DATE_TIME): String {
        return SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date())
    }

    const val DEFAULT_DATE_TIME = "dd MMMM HH:mm"
}