package `in`.catat.data.local.dao

import `in`.catat.data.local.entity.NoteEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * Created by pertadima on 27,October,2020
 */

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)
}