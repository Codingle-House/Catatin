package `in`.catat.data.dto

/**
 * Created by pertadima on 28,October,2020
 */

data class NoteDto(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val type: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val isLocked: Boolean = false,
    val image: String = ""
)