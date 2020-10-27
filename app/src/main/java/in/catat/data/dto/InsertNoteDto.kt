package `in`.catat.data.dto

/**
 * Created by pertadima on 27,October,2020
 */

data class InsertNoteDto(
    val title: String = "",
    val content: String = "",
    val type: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)