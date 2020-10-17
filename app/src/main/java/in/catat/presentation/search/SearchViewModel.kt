package `in`.catat.presentation.search

import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.UserNotesDto
import `in`.catat.domain.app.repository.AppRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Created by pertadima on 17,October,2020
 */

class SearchViewModel @ViewModelInject constructor(
    private val repository: AppRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val notesTypeLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeNotesTypes(): MutableLiveData<List<CatatinMenuDto>> = notesTypeLiveData

    init {
        notesTypeLiveData.postValue(repository.getNotesType())
    }
}