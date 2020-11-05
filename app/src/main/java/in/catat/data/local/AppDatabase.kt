package `in`.catat.data.local

import `in`.catat.data.local.dao.NoteDao
import `in`.catat.data.local.dao.TodoDao
import `in`.catat.data.local.entity.NoteEntity
import `in`.catat.data.local.entity.TodoEntity
import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by pertadima on 27,October,2020
 */

@Database(
    entities = [
        NoteEntity::class,
        TodoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun todoDao(): TodoDao
}