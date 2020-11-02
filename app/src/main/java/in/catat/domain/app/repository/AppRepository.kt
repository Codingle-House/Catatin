package `in`.catat.domain.app.repository

import `in`.catat.data.dto.InsertNoteDto
import `in`.catat.data.dto.NoteDto
import `in`.catat.data.local.entity.NoteEntity
import `in`.catat.domain.app.datasource.AppLocalDataSource
import `in`.catat.domain.app.datasource.AppRemoteDataSource
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
}