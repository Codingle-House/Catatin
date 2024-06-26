package `in`.catat.domain.app.datasource

import `in`.catat.R
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.local.AppDatabase
import `in`.catat.data.local.entity.NoteEntity
import `in`.catat.data.local.entity.TodoEntity
import javax.inject.Inject

/**
 * Created by pertadima on 06,October,2020
 */

class AppLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase
) {
    val settingsMenu: List<CatatinMenuDto>
        get() = listOf(
            CatatinMenuDto(title = R.string.dialog_title_menu_fullscreen),
            CatatinMenuDto(title = R.string.dialog_title_menu_alarm),
            CatatinMenuDto(title = R.string.dialog_title_menu_share),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_lock,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_focus,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_pdf,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            )
        )

    val attachmentMenuList: List<CatatinMenuDto>
        get() = listOf(
            CatatinMenuDto(title = R.string.dialog_title_menu_file),
            CatatinMenuDto(title = R.string.dialog_title_menu_location),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_video,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_lock,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_audio,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_sketch,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            ),
        )

    val notesType: List<CatatinMenuDto>
        get() = listOf(
            CatatinMenuDto(R.string.dialog_title_menu_notes),
            CatatinMenuDto(R.string.dialog_title_menu_todo),
            CatatinMenuDto(
                title = R.string.dialog_title_menu_draw,
                description = R.string.dialog_text_menu_premium,
                isPremiumContent = true
            )
        )

    suspend fun insertNote(note: NoteEntity) = appDatabase.noteDao().insertNote(note)

    suspend fun getAllNotes() = appDatabase.noteDao().getAllNotes()

    suspend fun getSingleNote(id: Long) = appDatabase.noteDao().getSingleNote(id)

    suspend fun deleteSingleNote(id: Long) = appDatabase.noteDao().deleteSingleNote(id)

    suspend fun updateSingleNote(note: NoteEntity) = appDatabase.noteDao().updateSingleNote(note)

    suspend fun insertTodo(todo: TodoEntity) = appDatabase.todoDao().insertTodo(todo)

    suspend fun getNoteTodos(idNotes: Long) = appDatabase.todoDao().getNoteTodos(idNotes)

    suspend fun updateSingleTodo(todo: TodoEntity) = appDatabase.todoDao().updateSingleTodo(todo)

    suspend fun getAllNotesWithTodos() = appDatabase.noteDao().getAllNotesWithTodos()

    suspend fun searchAllNotesByType(search: List<String>) =
        appDatabase.noteDao().searchAllNotesByType(search)
}