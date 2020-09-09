package `in`.catat.presentation.sketch

import `in`.catat.R
import `in`.catat.data.model.CatatanMenuModel
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.activity_sketch.*

class SketchActivity : AppCompatActivity(R.layout.activity_sketch) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupCanvass()
    }

    private fun setupToolbar() {
        with(sketch_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            inflateMenu(R.menu.catatin_menu_more)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_main_setting -> {
                        GeneralCatatinMenuDialog(
                            context = this@SketchActivity,
                            title = getString(R.string.general_text_setting),
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

    private fun setupCanvass() {
        draw_canvass_view.setCanvassBackground(
            ContextCompat.getColor(this, R.color.colorPrimary),
            ContextCompat.getColor(this, R.color.colorRichTextEditor)
        )
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
        sketch_appbar.isGone = true
        isFullScreen = true
        Toast.makeText(
            this,
            getString(R.string.general_text_fullscreen_close),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onBackPressed() {
        if (isFullScreen) {
            isFullScreen = false
            sketch_appbar.isGone = false
        } else {
            finish()
        }
    }
}