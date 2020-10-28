package `in`.catat.presentation.home

import `in`.catat.data.dto.CatatinFilterMenuDto
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.NoteDto
import `in`.catat.data.dto.UserNotesDto
import `in`.catat.domain.app.repository.AppRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by pertadima on 13,October,2020
 */

class HomeViewModel @ViewModelInject constructor(
    private val repository: AppRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userNotesLiveData = MutableLiveData<List<NoteDto>>()
    fun observeUserNotes(): MutableLiveData<List<NoteDto>> = userNotesLiveData

    private val notesTypeLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeNotesTypes(): MutableLiveData<List<CatatinMenuDto>> = notesTypeLiveData

    private val notesFilterLiveData = MutableLiveData<List<CatatinFilterMenuDto>>()
    fun observeNotesFilter(): MutableLiveData<List<CatatinFilterMenuDto>> = notesFilterLiveData

    init {
        notesTypeLiveData.postValue(repository.getNotesType())
        val notesFilter = repository.getNotesType().mapIndexed { index, it ->
            CatatinFilterMenuDto(id = index, title = it.title)
        }
        notesFilterLiveData.postValue(notesFilter)
    }

    fun setNotesFilter(filteredData: List<CatatinFilterMenuDto>) {
        notesFilterLiveData.postValue(filteredData)
    }

    fun getUserNotes() = viewModelScope.launch {
        val userNotes = repository.getAllNotes()
        userNotesLiveData.postValue(userNotes)
    }
}