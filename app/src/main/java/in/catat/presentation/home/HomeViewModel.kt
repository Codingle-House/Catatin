package `in`.catat.presentation.home

import `in`.catat.data.dto.UserNotesDto
import `in`.catat.domain.repository.AppRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * Created by pertadima on 13,October,2020
 */

class HomeViewModel @ViewModelInject constructor(
    private val repository: AppRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userNotesLiveData = MutableLiveData<List<UserNotesDto>>()
    fun observeUserNotes(): MutableLiveData<List<UserNotesDto>> = userNotesLiveData

    //TODO: REMOVE DUMMY DATA
    fun getUserNotes() {
        val userNotes = listOf(
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan"
            )
        )
        userNotesLiveData.postValue(userNotes)
    }
}