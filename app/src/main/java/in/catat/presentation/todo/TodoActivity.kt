package `in`.catat.presentation.todo

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.showToast
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.dto.InsertNoteDto
import `in`.catat.data.dto.InsertTodoDto
import `in`.catat.data.dto.TodoDto
import `in`.catat.data.enum.NoteStatusEnum
import `in`.catat.data.enum.NoteStatusEnum.CREATE
import `in`.catat.data.enum.NoteStatusEnum.EDIT
import `in`.catat.databinding.ActivityTodoBinding
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.dialog.GeneralCatatinTodoDialog
import `in`.catat.presentation.todo.TodoActivity.TodoKey.STATUS
import `in`.catat.util.DateUtil
import `in`.catat.util.constants.appConstant
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class TodoActivity : BaseActivity<ActivityTodoBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTodoBinding
        get() = ActivityTodoBinding::inflate

    @Inject
    lateinit var diffCallback: DiffCallback

    private val todoViewModel: TodoViewModel by viewModels()

    private val todoStatus by lazy {
        intent?.getSerializableExtra(STATUS) as NoteStatusEnum
    }

    private val noteId by lazy {
        intent?.getLongExtra(TodoKey.ID, 0L) ?: 0L
    }

    private val settingsDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@TodoActivity,
            title = getString(R.string.general_text_setting),
            diffCallback = diffCallback,
            onMenuClick = ::handleMenuDialogClick
        )
    }

    private val todoAdapter by lazy {
        TodoNoteAdapter(
            context = this@TodoActivity,
            diffCallback = diffCallback,
            itemListener = ::itemListenerTodo,
            itemActionListener = ::itemActionListenerTodo
        )
    }

    private val currentDate by lazy {
        DateUtil.getCurrentDate()
    }

    private val currentNoteId by lazy {
        Calendar.getInstance().timeInMillis
    }

    private var isFullScreen = false
    private var createdAt = ""

    override fun onViewCreated() {
        getNoteData()
        setupAppToolbar()
        setupView()
        setupListener()
        setupRecyclerView()
    }

    override fun onViewModelObserver() {
        with(todoViewModel) {
            observeSettingsMenu().onResult {
                settingsDialog.setData(it)
            }

            observeTodoList().onResult {
                binding.todoViewflipperContent.displayedChild = if (it.isEmpty()) EMPTY_STATE else AVAILABLE_STATE
                todoAdapter.setData(it)
                binding.todoTextviewDescription.text = getString(
                    R.string.todo_text_description,
                    currentDate, it.size.toString()
                )
            }

            observeSingleNote().onResult {
                createdAt = it.createdAt
                binding.todoEdittextTitle.setText(it.title)
            }
        }
    }

    private fun setupAppToolbar() {
        with(binding.todoToolbar) {
            setNavigationOnClickListener { finish() }
            inflateMenu(R.menu.catatin_menu_more)
            menu.findItem(R.id.menu_main_delete).isVisible = todoStatus == EDIT
            setOnMenuItemClickListener { handleMenuClick(it) }
        }
    }

    private fun setupRecyclerView() {
        with(binding.todoRecyclerviewNotes) {
            adapter = todoAdapter.apply { setHasStableIds(true) }
            layoutManager = LinearLayoutManager(this@TodoActivity)
        }
    }

    private fun getNoteData() {
        if (todoStatus == EDIT) {
            todoViewModel.getSingleNote(noteId)
        }
    }

    private fun setupView() {
        binding.todoTextviewDescription.text =
            getString(R.string.todo_text_description, currentDate, "0")
    }

    private fun setupListener() {
        binding.todoButtonAdd.setOnClickListener {
            GeneralCatatinTodoDialog(
                context = this@TodoActivity,
                title = getString(R.string.dialog_title_todo_add),
                actionText = getString(R.string.general_text_add),
                actionListener = {
                    if (todoStatus == CREATE) {
                        val noteDto = InsertNoteDto(
                            id = currentNoteId,
                            title = binding.todoEdittextTitle.text.toString(),
                            type = appConstant.TYPE_TODO,
                            createdAt = currentDate
                        )
                        val todoDto = InsertTodoDto(
                            idNote = currentNoteId,
                            name = it
                        )
                        todoViewModel.doInsertNoteTodo(noteDto, todoDto)
                    } else {
                        val todoDto = InsertTodoDto(
                            idNote = noteId,
                            name = it
                        )
                        todoViewModel.doInsertExistingNoteTodo(todoDto)
                    }
                }
            ).show()
        }
    }

    private fun handleMenuDialogClick(post: Int, data: CatatinMenuDto) {
        when (getString(data.title)) {
            getString(R.string.dialog_title_menu_fullscreen) -> handleFullScreen()
            getString(R.string.dialog_title_menu_alarm) -> {

            }

            getString(R.string.dialog_title_menu_share) -> {
            }

            getString(R.string.dialog_title_menu_lock) -> {

            }

            getString(R.string.dialog_title_menu_copy) -> {

            }

            getString(R.string.dialog_title_menu_focus) -> {

            }

            else -> {

            }
        }
    }

    private fun handleFullScreen() {
        binding.todoAppbar.isGone = true
        binding.todoEdittextTitle.isGone = true
        isFullScreen = true
        showToast(R.string.general_text_fullscreen_close)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFullScreen) {
            isFullScreen = false
            binding.todoAppbar.isGone = false
            binding.todoEdittextTitle.isGone = false
        } else {
            finish()
        }
    }

    private fun showDeleteDialog() {
        GeneralCatatinDialog(
            context = this,
            image = R.drawable.notes_ic_delete,
            title = getString(R.string.general_title_delete_note),
            description = getString(R.string.general_text_delete_note),
            yesTextButton = getString(R.string.general_text_yes),
            yesClickListener = {
                todoViewModel.doDeleteSingleNote(noteId)
                finish()
            },
            noTextButton = getString(R.string.general_text_no)
        ).show()
    }

    private fun handleMenuClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_main_setting -> {
                settingsDialog.show()
                true
            }

            R.id.menu_main_delete -> {
                showDeleteDialog()
                true
            }

            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    private fun itemListenerTodo(data: TodoDto, pos: Int, view: View) {
        val insertTodo = InsertTodoDto(
            id = data.id,
            name = data.name,
            isDone = data.isDone.not(),
            reminderDate = data.reminderDate,
            idNote = data.idNote
        )
        todoViewModel.updateSingleTodo(insertTodo)
    }

    private fun itemActionListenerTodo(
        data: TodoDto,
        pos: Int,
        view: View,
        todoAction: TodoNoteAdapter.TodoAction
    ) {
        when (todoAction) {
            TodoNoteAdapter.TodoAction.OnEditTodo -> {

            }

            TodoNoteAdapter.TodoAction.OnDeleteTodo -> {

            }
        }
    }

    private fun actionSave() {
        val noteDto = InsertNoteDto(
            title = binding.todoEdittextTitle.text.toString(),
            type = appConstant.TYPE_TODO,
            createdAt = createdAt
        )

        if (todoStatus == EDIT) {
            val updateNoteDto = noteDto.copy(
                id = noteId,
                updatedAt = currentDate
            )
            todoViewModel.doUpdateNote(updateNoteDto)
        }
    }

    override fun onResume() {
        super.onResume()
        if (todoStatus == EDIT) {
            todoViewModel.getNoteTodos(noteId)
        }
    }

    override fun onPause() {
        super.onPause()
        actionSave()
    }

    object TodoKey {
        const val STATUS = "TodoActivity.STATUS"
        const val ID = "TodoActivity.ID"
    }

    companion object {
        private const val EMPTY_STATE = 0
        private const val AVAILABLE_STATE = 1
    }
}