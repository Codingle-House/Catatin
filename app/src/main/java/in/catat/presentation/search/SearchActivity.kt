package `in`.catat.presentation.search

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.enum.NoteStatusEnum.CREATE
import `in`.catat.databinding.ActivitySearchBinding
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.presentation.note.NoteActivity
import `in`.catat.presentation.note.NoteActivity.NoteKey
import `in`.catat.presentation.sketch.SketchActivity
import `in`.catat.presentation.sketch.SketchActivity.SketchKey
import `in`.catat.presentation.todo.TodoActivity
import `in`.catat.presentation.todo.TodoActivity.TodoKey
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding
        get() = ActivitySearchBinding::inflate

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

    private fun setupToolbar() = binding.searchToolbar.setNavigationOnClickListener {
        finish()
    }

    private fun setupListener() = binding.searchButtonAdd.setOnClickListener {
        notesTypeDialog.show()
    }

    private fun handleMenuDialogClick(data: CatatinMenuDto) {
        when (getString(data.title)) {
            getString(R.string.dialog_title_menu_notes) -> {
                startActivity(
                    Intent(this, NoteActivity::class.java).putExtra(NoteKey.STATUS, CREATE)
                )
            }

            getString(R.string.dialog_title_menu_todo) -> {
                startActivity(
                    Intent(this, TodoActivity::class.java).putExtra(TodoKey.STATUS, CREATE)
                )
            }

            else -> {
                startActivity(
                    Intent(this, SketchActivity::class.java).putExtra(SketchKey.STATUS, CREATE)
                )
            }
        }
    }
}