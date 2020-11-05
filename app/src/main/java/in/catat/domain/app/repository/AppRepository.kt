package `in`.catat.domain.app.repository

import `in`.catat.data.dto.InsertNoteDto
import `in`.catat.data.dto.InsertTodoDto
import `in`.catat.data.dto.NoteDto
import `in`.catat.data.dto.TodoDto
import `in`.catat.data.local.entity.NoteEntity
import `in`.catat.data.local.entity.TodoEntity
import `in`.catat.domain.app.datasource.AppLocalDataSource
import `in`.catat.domain.app.datasource.AppRemoteDataSource
import `in`.catat.util.constants.appConstant
import javax.inject.Inject

/**
 * Created by pertadima on 06,October,2020
 */

class AppRepository @Inject constructor(
    private val appLocalDataSource: AppLocalDataSource,
    private val appRemoteDataSource: AppRemoteDataSource
) {
    fun getAttachmentMenuList() = appLocalDataSource.attachmentMenuList
    fun getSettingsMenu() = appLocalDataSource.settingsMenu
    fun getNotesType() = appLocalDataSource.notesType

    suspend fun insertNote(insertNoteDto: InsertNoteDto) = appLocalDataSource.insertNote(
        NoteEntity(
            id = insertNoteDto.id,
            title = insertNoteDto.title,
            content = insertNoteDto.content,
            type = insertNoteDto.type,
            createdAt = insertNoteDto.createdAt,
            updatedAt = insertNoteDto.updatedAt
        )
    )

    suspend fun getAllNotes() = appLocalDataSource.getAllNotes().map {
        NoteDto(
            id = it.id,
            title = it.title,
            content = it.content,
            type = it.type,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt
        )
    }

    suspend fun getSingleNote(id: Long): NoteDto {
        val singleNote = appLocalDataSource.getSingleNote(id)
        return NoteDto(
            id = singleNote.id,
            title = singleNote.title,
            content = singleNote.content,
            type = singleNote.type,
            createdAt = singleNote.createdAt,
            updatedAt = singleNote.updatedAt
        )
    }

    suspend fun deleteSingleNote(id: Long) = appLocalDataSource.deleteSingleNote(id)

    suspend fun updateSingleNote(insertNoteDto: InsertNoteDto) =
        appLocalDataSource.updateSingleNote(
            NoteEntity(
                id = insertNoteDto.id,
                title = insertNoteDto.title,
                content = insertNoteDto.content,
                type = insertNoteDto.type,
                createdAt = insertNoteDto.createdAt,
                updatedAt = insertNoteDto.updatedAt
            )
        )

    suspend fun insertNoteTodo(insertTodoDto: InsertTodoDto) = appLocalDataSource.insertTodo(
        TodoEntity(
            name = insertTodoDto.name,
            isDone = insertTodoDto.isDone,
            reminderDate = insertTodoDto.reminderDate,
            idNote = insertTodoDto.idNote
        )
    )

    suspend fun getNoteTodos(idNotes: Long) = appLocalDataSource.getNoteTodos(idNotes).map {
        TodoDto(
            id = it.id,
            name = it.name,
            isDone = it.isDone,
            idNote = it.idNote,
            reminderDate = it.reminderDate
        )
    }

    suspend fun updateSingleTodo(insertTodoDto: InsertTodoDto) =
        appLocalDataSource.updateSingleTodo(
            TodoEntity(
                id = insertTodoDto.id,
                name = insertTodoDto.name,
                isDone = insertTodoDto.isDone,
                reminderDate = insertTodoDto.reminderDate,
                idNote = insertTodoDto.idNote
            )
        )

    suspend fun getAllNotesWithTodo(): List<NoteDto> =
        appLocalDataSource.getAllNotesWithTodos().map {
            val content = when (it.noteEntity?.type) {
                appConstant.TYPE_NOTE -> it.noteEntity?.content.orEmpty()
                appConstant.TYPE_TODO -> it.todos.joinToString(separator = ",") { todo ->
                    todo.name
                }
                else -> ""
            }
            NoteDto(
                id = it.noteEntity?.id ?: 0,
                title = it.noteEntity?.title.orEmpty(),
                content = content,
                type = it.noteEntity?.type.orEmpty(),
                createdAt = it.noteEntity?.createdAt.orEmpty(),
                updatedAt = it.noteEntity?.updatedAt.orEmpty()
            )
        }
}