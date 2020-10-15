package `in`.catat.presentation.search

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.model.CatatanMenuModel
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.note.NoteActivity
import `in`.catat.presentation.todo.TodoActivity
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity(R.layout.activity_search) {
    @Inject
    lateinit var diffCallback: DiffCallback

    private val catatanMenu by lazy {
        listOf(
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_notes)),
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_todo)),
            CatatanMenuModel(
                title = getString(R.string.dialog_title_menu_draw),
                description = getString(R.string.dialog_text_menu_premium),
                isPremiumContent = true
            )
        )
    }

    override fun onViewCreated() {
        setupToolbar()
        setupListener()
    }

    override fun onViewModelObserver() {
        search_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupToolbar() {

    }

    private fun setupListener() {
        search_button_add.setOnClickListener {
            GeneralCatatinMenuDialog(
                context = this@SearchActivity,
                title = getString(R.string.dialog_title_menu_add),
                diffCallback = diffCallback,
                dataMenu = catatanMenu,
                onMenuClick = { _, data ->
                    handleMenuDialogClick(data)
                }
            ).show()
        }
    }

    private fun handleMenuDialogClick(data: CatatanMenuModel) {
        when (data.title) {
            getString(R.string.dialog_title_menu_notes) -> {
                startActivity(Intent(this, NoteActivity::class.java))
            }
            getString(R.string.dialog_title_menu_todo) -> {
                startActivity(Intent(this, TodoActivity::class.java))
            }
            else -> {

            }
        }
    }
}