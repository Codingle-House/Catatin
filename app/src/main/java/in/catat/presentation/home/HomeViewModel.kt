package `in`.catat.presentation.home

import `in`.catat.data.dto.CatatinFilterMenuDto
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.UserNotesDto
import `in`.catat.domain.app.repository.AppRepository
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

    private val notesTypeLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeNotesTypes(): MutableLiveData<List<CatatinMenuDto>> = notesTypeLiveData

    private val notesFilterLiveData = MutableLiveData<List<CatatinFilterMenuDto>>()
    fun observeNotesFilter(): MutableLiveData<List<CatatinFilterMenuDto>> = notesFilterLiveData

    init {
        notesTypeLiveData.postValue(repository.getNotesType())
        val notesFilter = repository.getNotesType().map {
            CatatinFilterMenuDto(title = it.title)
        }
        notesFilterLiveData.postValue(notesFilter)
    }

    //TODO: REMOVE DUMMY DATA
    fun getUserNotes() {
        val userNotes = listOf<UserNotesDto>(
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
                image = "https://mymodernmet.com/wp/wp-content/uploads/2019/03/elements-of-art-6.jpg"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
                image = "https://mymodernmet.com/wp/wp-content/uploads/2019/03/elements-of-art-6.jpg"
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = true,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            ),
            UserNotesDto(
                date = "24 Agustus 16:25 ",
                title = "Lorem Ipsum Color Sit Ame",
                type = UserNotesDto.NoteType.NOTE,
                isLocked = false,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
            )
        )
        userNotesLiveData.postValue(userNotes)
    }
}