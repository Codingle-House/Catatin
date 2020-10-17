package `in`.catat.presentation.search

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.enum.NoteStatusEnum
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.note.NoteActivity
import `in`.catat.presentation.sketch.SketchActivity
import `in`.catat.presentation.todo.TodoActivity
import android.content.Intent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity(R.layout.activity_search) {
    @Inject
    lateinit var diffCallback: DiffCallback

    private val searchViewModel: SearchViewModel by viewModels()

    private val notesTypeDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@SearchActivity,
            diffCallback = diffCallback,
            title = getString(R.string.dialog_title_menu_add),
            onMenuClick = { _, data ->
                handleMenuDialogClick(data)
            }
        )
    }

    override fun onViewCreated() {
        setupToolbar()
        setupListener()
    }

    override fun onViewModelObserver() {
        with(searchViewModel) {
            observeNotesTypes().onResult {
                notesTypeDialog.setData(it)
            }
        }
    }

    private fun setupToolbar() {
        search_toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun setupListener() {
        search_button_add.setOnClickListener {
            notesTypeDialog.show()
        }
    }

    private fun handleMenuDialogClick(data: CatatinMenuDto) {
        when (getString(data.title)) {
            getString(R.string.dialog_title_menu_notes) -> {
                startActivity(
                    Intent(this, NoteActivity::class.java).putExtra(
                        NoteActivity.NoteKey.STATUS, NoteStatusEnum.CREATE
                    )
                )
            }
            getString(R.string.dialog_title_menu_todo) -> {
                startActivity(
                    Intent(this, TodoActivity::class.java).putExtra(
                        TodoActivity.TodoKey.STATUS, NoteStatusEnum.CREATE
                    )
                )
            }
            else -> {
                startActivity(Intent(this, SketchActivity::class.java).putExtra(
                    SketchActivity.SketchKey.STATUS, NoteStatusEnum.CREATE
                ))
            }
        }
    }
}