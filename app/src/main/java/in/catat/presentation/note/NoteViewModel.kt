package `in`.catat.presentation.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.InsertNoteDto
import `in`.catat.data.dto.NoteDto
import `in`.catat.domain.app.repository.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by pertadima on 17,October,2020
 */
@HiltViewModel
class NoteViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val attachmentMenuLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeAttachmentMenu(): MutableLiveData<List<CatatinMenuDto>> = attachmentMenuLiveData

    private val settingsMenuLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeSettingsMenu(): MutableLiveData<List<CatatinMenuDto>> = settingsMenuLiveData

    private val singleNoteLiveData = MutableLiveData<NoteDto>()
    fun observeSingleNote(): MutableLiveData<NoteDto> = singleNoteLiveData

    init {
        attachmentMenuLiveData.postValue(appRepository.getAttachmentMenuList())
        settingsMenuLiveData.postValue(appRepository.getSettingsMenu())
    }

    fun doInsertNote(insertNoteDto: InsertNoteDto) = viewModelScope.launch {
        appRepository.insertNote(insertNoteDto)
    }

    fun getSingleNote(id: Long) = viewModelScope.launch {
        val singleNote = appRepository.getSingleNote(id)
        singleNoteLiveData.postValue(singleNote)
    }

    fun doDeleteSingleNote(id: Long) = viewModelScope.launch {
        appRepository.deleteSingleNote(id)
    }

    fun doUpdateNote(insertNoteDto: InsertNoteDto) = viewModelScope.launch {
        appRepository.updateSingleNote(insertNoteDto)
    }
}