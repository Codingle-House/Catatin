package `in`.catat.data.local.relation

import `in`.catat.data.local.entity.NoteEntity
import `in`.catat.data.local.entity.TodoEntity
import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by pertadima on 05,November,2020
 */

class NoteTodosRelationEntity {
    @Embedded
    var noteEntity: NoteEntity? = NoteEntity()

    @Relation(
        parentColumn = "id",
        entityColumn = "id_note"
    )
    var todos: List<TodoEntity> = emptyList()
}