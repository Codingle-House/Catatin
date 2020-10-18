package `in`.catat.data.dto

/**
 * Created by pertadima on 13,October,2020
 */

data class UserNotesDto(
    val date: String = "",
    val title: String = "",
    val type: String = "",
    val description: String = "",
    val image: String = "",
    val isLocked: Boolean = false
) {
    object NoteType {
        const val NOTE = "note"
        const val TODO = "todo"
        const val SKETCH = "sketch"
    }
}