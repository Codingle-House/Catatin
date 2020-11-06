package `in`.catat.data.dto

/**
 * Created by pertadima on 26,October,2020
 */

data class CatatinFilterMenuDto(
    val id: Int,
    val title: Int,
    val isSelected: Boolean = false,
    val type: String = ""
)