package `in`.catat.data.mapper

import `in`.catat.data.dto.NoteDto
import `in`.catat.data.local.relation.NoteTodosRelationEntity
import `in`.catat.util.constants.appConstant

/**
 * Created by pertadima on 06,November,2020
 */

object EntityToDtoMapper {
    fun noteTodoEntityToDto(notes: NoteTodosRelationEntity) : NoteDto {
        val content = when (notes.noteEntity?.type) {
            appConstant.TYPE_NOTE -> notes.noteEntity?.content.orEmpty()
            appConstant.TYPE_TODO -> notes.todos.joinToString(separator = ",") { todo ->
                todo.name
            }
            else -> ""
        }
        return NoteDto(
            id = notes.noteEntity?.id ?: 0,
            title = notes.noteEntity?.title.orEmpty(),
            content = content,
            type = notes.noteEntity?.type.orEmpty(),
            createdAt = notes.noteEntity?.createdAt.orEmpty(),
            updatedAt = notes.noteEntity?.updatedAt.orEmpty()
        )
    }
}