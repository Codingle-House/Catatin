package `in`.catat.presentation.todo

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.enum.NoteStatusEnum
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.dialog.GeneralCatatinTodoDialog
import `in`.catat.util.DateUtil
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isGone
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.showToast
import kotlinx.android.synthetic.main.activity_todo.*
import javax.inject.Inject

@AndroidEntryPoint
class TodoActivity : BaseActivity(R.layout.activity_todo) {
    @Inject
    lateinit var diffCallback: DiffCallback

    private val todoViewModel: TodoViewModel by viewModels()

    private val todoStatus by lazy {
        intent?.getSerializableExtra(TodoActivity.TodoKey.STATUS) as NoteStatusEnum
    }

    private val settingsDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@TodoActivity,
            title = getString(R.string.general_text_setting),
            diffCallback = diffCallback,
            onMenuClick = { _, data ->
                handleMenuDialogClick(data)
            }
        )
    }

    private val currentDate by lazy {
        DateUtil.getCurrentDate()
    }

    private var isFullScreen = false

    override fun onViewCreated() {
        setupAppToolbar()
        setupView()
        setupListener()
    }

    override fun onViewModelObserver() {
        with(todoViewModel) {
            observeSettingsMenu().onResult {
                settingsDialog.setData(it)
            }
        }
    }

    private fun setupAppToolbar() {
        with(todo_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            inflateMenu(R.menu.catatin_menu_more)
            menu.findItem(R.id.menu_main_delete).isVisible = todoStatus == NoteStatusEnum.EDIT
            setOnMenuItemClickListener { handleMenuClick(it) }
        }
    }

    private fun setupView() {
        todo_textview_description.text =
            getString(R.string.todo_text_description, currentDate, "0")
    }

    private fun setupListener() {
        todo_button_add.setOnClickListener {
            GeneralCatatinTodoDialog(
                context = this@TodoActivity,
                title = getString(R.string.dialog_title_todo_add),
                actionText = getString(R.string.general_text_add),
                actionListener = {

                }
            ).show()
        }
    }

    private fun handleMenuDialogClick(data: CatatinMenuDto) {
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
        todo_appbar.isGone = true
        todo_edittext_title.isGone = true
        isFullScreen = true
        showToast(R.string.general_text_fullscreen_close)
    }

    override fun onBackPressed() {
        if (isFullScreen) {
            isFullScreen = false
            todo_appbar.isGone = false
            todo_edittext_title.isGone = false
        } else {
            finish()
        }
    }

    private fun showDeleteDialog() {
        GeneralCatatinDialog(
            context = this,
            image = R.drawable.notes_ic_delete,
            title = getString(R.string.geberal_title_delete_note),
            description = getString(R.string.geberal_text_delete_note),
            yesTextButton = getString(R.string.general_text_yes),
            yesClickListener = {

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

    object TodoKey {
        const val STATUS = "TodoActivity.STATUS"
    }
}