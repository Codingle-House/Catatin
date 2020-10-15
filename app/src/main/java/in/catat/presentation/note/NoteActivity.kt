package `in`.catat.presentation.note

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.data.model.CatatanMenuModel
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.util.DateUtil
import `in`.catat.util.ShareUtil
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isGone
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import dagger.hilt.android.AndroidEntryPoint
import id.catat.uikit.richtext_item.*
import id.co.catatin.core.commons.DiffCallback
import id.co.catatin.core.ext.getColorCompat
import id.co.catatin.core.ext.showToast
import kotlinx.android.synthetic.main.activity_note.*
import javax.inject.Inject

@AndroidEntryPoint
class NoteActivity : BaseActivity(R.layout.activity_note) {
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

    private val attachmentMenu by lazy {
        listOf(
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_file)),
            CatatanMenuModel(title = getString(R.string.dialog_title_menu_location)),
            CatatanMenuModel(
                title = getString(R.string.dialog_title_menu_video),
                description = getString(R.string.dialog_text_menu_premium),
                isPremiumContent = true
            ),
            CatatanMenuModel(
                title = getString(R.string.dialog_title_menu_audio),
                description = getString(R.string.dialog_text_menu_premium),
                isPremiumContent = true
            ),
            CatatanMenuModel(
                title = getString(R.string.dialog_title_menu_sketch),
                description = getString(R.string.dialog_text_menu_premium),
                isPremiumContent = true
            )
        )
    }

    private var scrollerAtEnd = false
    private var isFullScreen = false

    override fun onViewCreated() {
        initRichTextView()
        setupAppToolbar()
        setupView()
        setupToolbarArrow()
    }

    override fun onViewModelObserver() {

    }

    private fun setupAppToolbar() {
        with(note_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            inflateMenu(R.menu.catatin_menu_note)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.note_menu_setting -> {
                        GeneralCatatinMenuDialog(
                            context = this@NoteActivity,
                            title = getString(R.string.general_text_setting),
                            diffCallback = diffCallback,
                            dataMenu = settingsMenu,
                            onMenuClick = { _, data ->
                                handleMenuDialogClick(data)
                            }
                        ).show()
                        true
                    }
                    R.id.note_menu_attachment -> {
                        GeneralCatatinMenuDialog(
                            context = this@NoteActivity,
                            title = getString(R.string.dialog_title_menu_attachment),
                            diffCallback = diffCallback,
                            dataMenu = attachmentMenu,
                            onMenuClick = { _, data ->

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
        val checkedBackgroundColor = getColorCompat(R.color.colorSelectedToolItem)
        val uncheckedBackgroundColor = getColorCompat(android.R.color.transparent)

        val alignmentLeft = CatatinAlignmentLeftToolItem()
        val alignmentCenter = CatatinAlignmentCenterToolItem()
        val alignmentRight = CatatinAlignmentRightToolItem()

        val bold = CatatinBoldToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val italic = CatatinItalicToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val underline =
            CatatinUnderlineToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val strikeTrough =
            CatatinStrikeThroughToolItem(checkedBackgroundColor, uncheckedBackgroundColor)

        val quote = CatatinQuoteToolItem(checkedBackgroundColor, uncheckedBackgroundColor)

        val numberList =
            CatatinNumberListToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val bulletList =
            CatatinBulletListToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val divider = CatatinDividerToolItem()

        with(note_richtext_toolbar) {
            addToolbarItem(alignmentLeft)
            addToolbarItem(alignmentCenter)
            addToolbarItem(alignmentRight)
            addToolbarItem(bold)
            addToolbarItem(italic)
            addToolbarItem(underline)
            addToolbarItem(strikeTrough)
            addToolbarItem(quote)
            addToolbarItem(numberList)
            addToolbarItem(bulletList)
            addToolbarItem(divider)
        }

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
            if (scrollerAtEnd) {
                note_richtext_toolbar.smoothScrollBy(-Integer.MAX_VALUE, 0)
            } else {
                val fullWidth = note_richtext_toolbar.getChildAt(0).width
                note_richtext_toolbar.smoothScrollBy(fullWidth, 0);
            }
        }
    }

    private fun handleMenuDialogClick(data: CatatanMenuModel) {
        when (data.title) {
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

    private fun actionSave() {

    }

    companion object {
        private const val DEFAULT_ROTATION = 0F
        private const val LINEAR_ROTATION = 180F
        private const val ANIMATION_DURATION = 300L
    }
}