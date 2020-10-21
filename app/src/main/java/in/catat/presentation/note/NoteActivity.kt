package `in`.catat.presentation.note

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.dto.CatatinMenuDto
import `in`.catat.data.enum.NoteStatusEnum
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.util.DateUtil
import `in`.catat.util.RichTextItem
import `in`.catat.util.ShareUtil
import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isGone
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.showToast
import kotlinx.android.synthetic.main.activity_note.*
import javax.inject.Inject


@AndroidEntryPoint
class NoteActivity : BaseActivity(R.layout.activity_note) {
    @Inject
    lateinit var diffCallback: DiffCallback

    private val noteViewModel: NoteViewModel by viewModels()

    private val currentDate by lazy {
        DateUtil.getCurrentDate()
    }

    private val noteStatus by lazy {
        intent?.getSerializableExtra(NoteKey.STATUS) as NoteStatusEnum
    }

    private var scrollerAtEnd = false
    private var isFullScreen = false

    private val settingsDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@NoteActivity,
            title = getString(R.string.general_text_setting),
            diffCallback = diffCallback,
            onMenuClick = ::handleMenuDialogClick
        )
    }

    private val attachmentDialog by lazy {
        GeneralCatatinMenuDialog(
            context = this@NoteActivity,
            title = getString(R.string.dialog_title_menu_attachment),
            diffCallback = diffCallback,
            onMenuClick = ::handleMenuDialogClick
        )
    }

    override fun onViewCreated() {
        initRichTextView()
        setupAppToolbar()
        setupView()
        setupToolbarArrow()
        setupAdMob()
    }


    override fun onViewModelObserver() {
        with(noteViewModel) {
            observeAttachmentMenu().onResult {
                attachmentDialog.setData(it)
            }

            observeSettingsMenu().onResult {
                settingsDialog.setData(it)
            }
        }
    }

    private fun setupAdMob() {
        val handler = Handler()
        handler.postDelayed({
            with(note_adview_banner) {
                initializeAdMob()
                bringToFront()
                isGone = false
            }
        }, ADMOB_DELAY)
    }

    private fun setupAppToolbar() {
        with(note_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            inflateMenu(R.menu.catatin_menu_note)
            menu.findItem(R.id.note_menu_delete).isVisible = noteStatus == NoteStatusEnum.EDIT
            setOnMenuItemClickListener { handleMenuClick(it) }
        }
    }

    private fun setupView() {
        note_textview_description.text =
            getString(
                R.string.note_text_description,
                currentDate,
                note_richtext_form.length().toString()
            )

        note_richtext_form.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                note_textview_description.text =
                    getString(R.string.note_text_description, currentDate, s.length.toString())
            }
        })
    }

    private fun initRichTextView() {
        val toolItems = RichTextItem.richTextDefaultItem(this)
        note_richtext_toolbar.addToolbarItems(toolItems)
        note_richtext_form.setToolbar(note_richtext_toolbar)
    }

    private fun setupToolbarArrow() {
        if (note_richtext_toolbar is ARE_ToolbarDefault) {
            note_richtext_toolbar.viewTreeObserver.addOnScrollChangedListener {
                val scrollX = note_richtext_toolbar.scrollX
                val scrollWidth = note_richtext_toolbar.width
                val fullWidth = note_richtext_toolbar.getChildAt(0).width

                note_imageview_arrow.rotateAnimation(scrollX + scrollWidth < fullWidth)
            }
        }
        note_imageview_arrow.setOnClickListener {
            val fullWidth = note_richtext_toolbar.getChildAt(0).width

            note_richtext_toolbar.smoothScrollBy(
                if (scrollerAtEnd) -Integer.MAX_VALUE else fullWidth,
                0
            )
        }
    }

    private fun handleMenuDialogClick(post: Int, data: CatatinMenuDto) {
        when (getString(data.title)) {
            getString(R.string.dialog_title_menu_fullscreen) -> handleFullScreen()
            getString(R.string.dialog_title_menu_alarm) -> {

            }
            getString(R.string.dialog_title_menu_share) -> shareActionSetting()
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
        note_appbar.isGone = true
        note_edittext_title.isGone = true
        isFullScreen = true
        showToast(R.string.general_text_fullscreen_close)
    }

    override fun onBackPressed() {
        if (isFullScreen) {
            isFullScreen = false
            note_appbar.isGone = false
            note_edittext_title.isGone = false
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

    override fun onPause() {
        super.onPause()
        actionSave()
    }

    private fun shareActionSetting() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = ShareUtil.PLAIN_TYPE
            intent.putExtra(Intent.EXTRA_SUBJECT, note_edittext_title.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, note_richtext_form.text.toString())
        }

        startActivity(Intent.createChooser(intent, ShareUtil.CHOOSER))
    }

    private fun View.rotateAnimation(isNextPage: Boolean) {
        scrollerAtEnd = !isNextPage
        animate()
            .setDuration(ANIMATION_DURATION)
            .rotation(if (!isNextPage) LINEAR_ROTATION else DEFAULT_ROTATION)
    }

    private fun handleMenuClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.note_menu_setting -> {
                settingsDialog.show()
                true
            }
            R.id.note_menu_attachment -> {
                attachmentDialog.show()
                true
            }
            R.id.note_menu_delete -> {
                showDeleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    private fun actionSave() {

    }

    object NoteKey {
        const val STATUS = "NoteActivity.STATUS"
    }

    companion object {
        private const val DEFAULT_ROTATION = 0F
        private const val LINEAR_ROTATION = 180F
        private const val ANIMATION_DURATION = 300L
        private const val ADMOB_DELAY = 5000L
    }
}