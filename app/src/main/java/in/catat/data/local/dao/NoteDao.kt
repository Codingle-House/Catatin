package `in`.catat.data.local.dao

import `in`.catat.data.local.entity.NoteEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by pertadima on 27,October,2020
 */

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM tbl_note")
    suspend fun getAllNotes(): List<NoteEntity>
}