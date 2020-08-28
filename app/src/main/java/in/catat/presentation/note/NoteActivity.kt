package `in`.catat.presentation.note

import `in`.catat.R
import `in`.catat.data.model.CatatanMenuModel
import `in`.catat.presentation.dialog.GeneralCatatinMenuDialog
import `in`.catat.util.DateUtil
import `in`.catat.util.ShareUtil
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import id.catat.uikit.richtext_item.*
import kotlinx.android.synthetic.main.activity_note.*


class NoteActivity : AppCompatActivity(R.layout.activity_note) {
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

    private var scrollerAtEnd = false
    private var isFullScren = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRichTextView()
        setupAppToolbar()
        setupView()
        setupToolbarArrow()
    }

    private fun setupAppToolbar() {
        with(note_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
            inflateMenu(R.menu.catatin_menu_more)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_main_setting -> {
                        GeneralCatatinMenuDialog(
                            context = this@NoteActivity,
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
        val checkedBackgroundColor = ContextCompat.getColor(this, R.color.colorSelectedToolItem)
        val uncheckedBackgroundColor = ContextCompat.getColor(this, android.R.color.transparent)

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
            getString(R.string.dialog_title_menu_open) -> {

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
        isFullScren = true
        Toast.makeText(
            this,
            getString(R.string.general_text_fullscreen_close),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onBackPressed() {
        if (isFullScren) {
            isFullScren = false
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