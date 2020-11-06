package `in`.catat.data.local.dao

import `in`.catat.data.local.entity.NoteEntity
import `in`.catat.data.local.relation.NoteTodosRelationEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update


/**
 * Created by pertadima on 27,October,2020
 */

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM tbl_note")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM tbl_note WHERE id = :id")
    suspend fun getSingleNote(id: Long): NoteEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSingleNote(noteEntity: NoteEntity)

    @Query("DELETE FROM tbl_note WHERE id = :id")
    suspend fun deleteSingleNote(id: Long)

    @Transaction
    @Query("SELECT * FROM tbl_note")
    suspend fun getAllNotesWithTodos(): List<NoteTodosRelationEntity>

    @Transaction
    @Query("SELECT * FROM tbl_note WHERE type IN (:search)")
    suspend fun searchAllNotesByType(search: List<String>): List<NoteTodosRelationEntity>
}