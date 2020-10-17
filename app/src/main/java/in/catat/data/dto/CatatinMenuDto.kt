package `in`.catat.data.dto

import `in`.catat.R
import androidx.annotation.StringRes

/**
 * Created by pertadima on 17,October,2020
 */

data class CatatinMenuDto(
    @StringRes val title: Int,
    @StringRes val description: Int = R.string.general_text_emptystring,
    val isPremiumContent: Boolean = false
)