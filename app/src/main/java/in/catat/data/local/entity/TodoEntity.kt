package `in`.catat.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by pertadima on 05,November,2020
 */

@Entity(tableName = "tbl_todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "is_done")
    val isDone: Boolean,
    @ColumnInfo(name = "reminder_date")
    val reminderDate: String,
    @ColumnInfo(name = "id_note")
    val idNote: Long
)