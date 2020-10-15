package `in`.catat.presentation.search

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.model.CatatanMenuModel
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.note.NoteActivity
import `in`.catat.presentation.todo.TodoActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(R.layout.activity_search) {
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
        setupListener()
    }

    override fun onViewModelObserver() {

    }

    private fun setupListener() {
        search_button_add.setOnClickListener {
            GeneralCatatinMenuDialog(
                context = this@SearchActivity,
                title = getString(R.string.dialog_title_menu_add),
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