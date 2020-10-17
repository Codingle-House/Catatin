package `in`.catat.presentation.todo

import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.domain.app.repository.AppRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Created by pertadima on 17,October,2020
 */

class TodoViewModel @ViewModelInject constructor(
    private val repository: AppRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val settingsMenuLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeSettingsMenu(): MutableLiveData<List<CatatinMenuDto>> = settingsMenuLiveData

    init {
        settingsMenuLiveData.postValue(repository.getSettingsMenu())
    }
}