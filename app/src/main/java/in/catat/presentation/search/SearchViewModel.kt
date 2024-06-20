package `in`.catat.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.domain.app.repository.AppRepository
import javax.inject.Inject

/**
 * Created by pertadima on 17,October,2020
 */

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val notesTypeLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeNotesTypes(): MutableLiveData<List<CatatinMenuDto>> = notesTypeLiveData

    init {
        notesTypeLiveData.postValue(repository.getNotesType())
    }
}