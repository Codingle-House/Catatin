package `in`.catat.presentation.sketch

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.enum.NoteStatusEnum
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isGone
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.getColorCompat
import id.co.catatin.core.ext.showToast
import kotlinx.android.synthetic.main.activity_sketch.*
import javax.inject.Inject

@AndroidEntryPoint
class SketchActivity : BaseActivity(R.layout.activity_sketch) {
    @Inject
    lateinit var diffCallback: DiffCallback

    private val sketchViewModel: SketchViewModel by viewModels()

    private val noteStatus by lazy {
        intent?.getSerializableExtra(SketchKey.STATUS) as NoteStatusEnum
    }

    private val settingsDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@SketchActivity,
            title = getString(R.string.general_text_setting),
            diffCallback = diffCallback,
            onMenuClick = { _, data ->
                handleMenuDialogClick(data)
            }
        )
    }

    private var isFullScreen = false

    override fun onViewCreated() {
        setupToolbar()
        setupCanvass()
    }

    override fun onViewModelObserver() {
        with(sketchViewModel) {
            observeSettingsMenu().onResult {
                settingsDialog.setData(it)
            }
        }
    }

    private fun setupToolbar() {
        with(sketch_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            inflateMenu(R.menu.catatin_menu_more)
            menu.findItem(R.id.menu_main_delete).isVisible = noteStatus == NoteStatusEnum.EDIT
            setOnMenuItemClickListener { handleMenuClick(it) }
        }
    }

    private fun setupCanvass() {
        draw_canvass_view.setCanvassBackground(
            getColorCompat(R.color.colorPrimary),
            getColorCompat(R.color.colorRichTextEditor)
        )
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

    private fun showDeleteDialog() {
        GeneralCatatinDialog(
            context = this,
            image = R.drawable.notes_ic_delete,
            title = getString(R.string.general_title_delete_note),
            description = getString(R.string.general_text_delete_note),
            yesTextButton = getString(R.string.general_text_yes),
            yesClickListener = {

            },
            noTextButton = getString(R.string.general_text_no)
        ).show()
    }

    private fun handleFullScreen() {
        sketch_appbar.isGone = true
        isFullScreen = true
        showToast(R.string.general_text_fullscreen_close)
    }

    override fun onBackPressed() {
        if (isFullScreen) {
            isFullScreen = false
            sketch_appbar.isGone = false
        } else {
            finish()
        }
    }

    object SketchKey {
        const val STATUS = "SketchActivity.STATUS"
    }
}