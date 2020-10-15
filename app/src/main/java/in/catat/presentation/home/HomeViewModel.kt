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
                type = "Catatan",
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
                image = "https://mymodernmet.com/wp/wp-content/uploads/2019/03/elements-of-art-6.jpg"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
                image = "https://mymodernmet.com/wp/wp-content/uploads/2019/03/elements-of-art-6.jpg"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = "Catatan",
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            )
        )
        userNotesLiveData.postValue(userNotes)
    }
}