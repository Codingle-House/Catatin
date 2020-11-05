package `in`.catat.data.local.dao

import `in`.catat.data.local.entity.TodoEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Created by pertadima on 05,November,2020
 */

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Query("SELECT * FROM tbl_todo WHERE id_note = :idNote")
    suspend fun getNoteTodos(idNote: Long): List<TodoEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSingleTodo(todoEntity: TodoEntity)
}