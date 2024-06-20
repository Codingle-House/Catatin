package `in`.catat.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by pertadima on 27,October,2020
 */

@Entity(tableName = "tbl_note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "content")
    val content: String = "",
    @ColumnInfo(name = "type")
    val type: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: String = "",
    @ColumnInfo(name = "updatedAt")
    val updatedAt: String = ""
)