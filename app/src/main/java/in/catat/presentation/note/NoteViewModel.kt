package `in`.catat.presentation.note

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

class NoteViewModel @ViewModelInject constructor(
    private val appRepository: AppRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val attachmentMenuLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeAttachmentMenu(): MutableLiveData<List<CatatinMenuDto>> = attachmentMenuLiveData

    private val settingsMenuLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeSettingsMenu(): MutableLiveData<List<CatatinMenuDto>> = settingsMenuLiveData

    init {
        attachmentMenuLiveData.postValue(appRepository.getAttachmentMenuList())
        settingsMenuLiveData.postValue(appRepository.getSettingsMenu())
    }
}