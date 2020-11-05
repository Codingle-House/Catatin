package `in`.catat.presentation.todo

import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.InsertNoteDto
import `in`.catat.data.dto.InsertTodoDto
import `in`.catat.data.dto.NoteDto
import `in`.catat.data.dto.TodoDto
import `in`.catat.domain.app.repository.AppRepository
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by pertadima on 17,October,2020
 */

class TodoViewModel @ViewModelInject constructor(
    private val repository: AppRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val settingsMenuLiveData = MutableLiveData<List<CatatinMenuDto>>()
    fun observeSettingsMenu(): MutableLiveData<List<CatatinMenuDto>> = settingsMenuLiveData

    private val todoListLiveData = MutableLiveData<List<TodoDto>>()
    fun observeTodoList(): MutableLiveData<List<TodoDto>> = todoListLiveData

    private val singleNoteLiveData = MutableLiveData<NoteDto>()
    fun observeSingleNote(): MutableLiveData<NoteDto> = singleNoteLiveData

    init {
        settingsMenuLiveData.postValue(repository.getSettingsMenu())
    }

    fun doInsertNoteTodo(
        insertNoteDto: InsertNoteDto,
        insertTodoDto: InsertTodoDto
    ) = viewModelScope.launch {
        val todos = repository.getNoteTodos(insertNoteDto.id)
        with(repository) {
            if (todos.isEmpty()) insertNote(insertNoteDto)
            insertNoteTodo(insertTodoDto)
        }
        getNoteTodos(insertTodoDto.idNote)
    }

    fun doInsertExistingNoteTodo(insertTodoDto: InsertTodoDto) = viewModelScope.launch {
        repository.insertNoteTodo(insertTodoDto)
        getNoteTodos(insertTodoDto.idNote)
    }

    fun getSingleNote(id: Long) = viewModelScope.launch {
        val singleNote = repository.getSingleNote(id)
        singleNoteLiveData.postValue(singleNote)
    }

    fun getNoteTodos(idNote: Long) = viewModelScope.launch {
        val todos = repository.getNoteTodos(idNote)
        todoListLiveData.postValue(todos)
    }

    fun updateSingleTodo(insertTodoDto: InsertTodoDto) = viewModelScope.launch {
        repository.updateSingleTodo(insertTodoDto)
        getNoteTodos(insertTodoDto.idNote)
    }

    fun doUpdateNote(insertNoteDto: InsertNoteDto) = viewModelScope.launch {
        repository.updateSingleNote(insertNoteDto)
    }
}