package `in`.catat.data.dto

/**
 * Created by pertadima on 05,November,2020
 */

data class TodoDto(
    val id: Long = 0,
    val name: String,
    val isDone: Boolean = false,
    val reminderDate: String = "",
    val idNote: Long
)