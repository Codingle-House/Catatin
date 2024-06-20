package `in`.catat.presentation.sketch

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
class SketchViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val settingsMenuLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeSettingsMenu(): MutableLiveData<List<CatatinMenuDto>> = settingsMenuLiveData

    init {
        settingsMenuLiveData.postValue(repository.getSettingsMenu())
    }
}