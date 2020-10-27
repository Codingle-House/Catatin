package `in`.catat.domain.app.repository

import `in`.catat.data.dto.InsertNoteDto
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
}