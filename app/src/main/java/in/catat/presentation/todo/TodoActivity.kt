package `in`.catat.presentation.todo

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.model.CatatanMenuModel
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.dialog.GeneralCatatinTodoDialog
import `in`.catat.util.DateUtil
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

    private val currentDate by lazy {
        DateUtil.getCurrentDate()
    }

    private val settingsMenu by lazy {
        listOf(
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_fullscreen)),
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_alarm)),
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_share)),
            CatatanMenuModel(
                title = getString(R.string.dialog_title_menu_lock),
                description = getString(R.string.dialog_text_menu_premium),
                isPremiumContent = true
            ),
            CatatanMenuModel(
                title = getString(R.string.dialog_title_menu_focus),
                description = getString(R.string.dialog_text_menu_premium),
                isPremiumContent = true
            ),
            CatatanMenuModel(
                title = getString(R.string.dialog_title_menu_pdf),
                description = getString(R.string.dialog_text_menu_premium),
                isPremiumContent = true
            )
        )
    }

    private var isFullScreen = false

    override fun onViewCreated() {
        setupAppToolbar()
        setupView()
        setupListener()
    }

    override fun onViewModelObserver() {

    }

    private fun setupAppToolbar() {
        with(todo_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            inflateMenu(R.menu.catatin_menu_more)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_main_setting -> {
                        GeneralCatatinMenuDialog(
                            context = this@TodoActivity,
                            title = getString(R.string.general_text_setting),
                            diffCallback = diffCallback,
                            dataMenu = settingsMenu,
                            onMenuClick = { _, data ->
                                handleMenuDialogClick(data)
                            }
                        ).show()
                        true
                    }
                    else -> super.onOptionsItemSelected(it)
                }
            }
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

    private fun handleMenuDialogClick(data: CatatanMenuModel) {
        when (data.title) {
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
}