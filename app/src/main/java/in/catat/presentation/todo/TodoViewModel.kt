package `in`.catat.presentation.todo

import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.NoteTodoDto
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

    private val todoListLiveData = MutableLiveData<List<NoteTodoDto>>()
    fun observeTodoList(): MutableLiveData<List<NoteTodoDto>> = todoListLiveData

    init {
        settingsMenuLiveData.postValue(repository.getSettingsMenu())
        dummyTodoList()
    }


    //TODO: IRFAN -  REMOVE DUMMY DATA
    private fun dummyTodoList() {
        val todos = listOf(
            NoteTodoDto(
                name = "Membeli Ikan",
                isDone = false,
                reminderDate = "20 Juni 2020 | 14:30"
            ),
            NoteTodoDto(
                name = "Membeli Ikan",
                isDone = true,
                reminderDate = "20 Juni 2020 | 14:30"
            ),
            NoteTodoDto(
                name = "Membeli Ikan",
                isDone = true
            ),
            NoteTodoDto(
                name = "Membeli Ikan",
                isDone = false
            ),
            NoteTodoDto(
                name = "Membeli Ikan",
                isDone = false
            )
        )

        todoListLiveData.postValue(todos)
    }
}