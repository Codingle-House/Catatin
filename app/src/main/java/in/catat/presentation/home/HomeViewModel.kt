package `in`.catat.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.catat.R
import `in`.catat.data.dto.CatatinFilterMenuDto
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.NoteDto
import `in`.catat.data.dto.UserNotesDto
import `in`.catat.domain.app.repository.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by pertadima on 13,October,2020
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository
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
        val selectedFilter =
            notesFilterLiveData.value?.filter { filter -> filter.isSelected }?.map { filter ->
                return@map when (filter.title) {
                    R.string.dialog_title_menu_notes -> UserNotesDto.NoteType.NOTE
                    R.string.dialog_title_menu_todo -> UserNotesDto.NoteType.TODO
                    else -> UserNotesDto.NoteType.SKETCH
                }
            }.orEmpty()

        if (selectedFilter.isEmpty()) {
            val userNotes = repository.getAllNotesWithTodo()
            userNotesLiveData.postValue(userNotes)
        } else {
            searchAllNotes(selectedFilter)
        }
    }

    private suspend fun searchAllNotes(search: List<String>) {
        val userNotes = repository.searchAllNotesWithTodoByType(search)
        userNotesLiveData.postValue(userNotes)
    }
}