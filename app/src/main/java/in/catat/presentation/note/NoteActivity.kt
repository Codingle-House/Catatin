package `in`.catat.presentation.note

import `in`.catat.R
import `in`.catat.util.DateUtil
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import id.catat.uikit.richtext_item.*
import kotlinx.android.synthetic.main.activity_note.*


class NoteActivity : AppCompatActivity(R.layout.activity_note) {
    private val currentDate by lazy {
        DateUtil.getCurrentDate()
    }

    private var scrollerAtEnd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRichTextView()
        setupView()
        setupToolbarArrow()
    }

    private fun setupView() {
        note_toolbar.setNavigationOnClickListener {
            finish()
        }

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
        val checkedBackgroundColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val uncheckedBackgroundColor = ContextCompat.getColor(this, android.R.color.transparent)

        val alignmentLeft = CatatinAlignmentLeftToolItem()
        val alignmentCenter = CatatinAlignmentCenterToolItem()
        val alignmentRight = CatatinAlignmentRightToolItem()

        val bold = CatatinBoldToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val italic = CatatinItalicToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val underline = CatatinUnderlineToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val strikeTrough =
            CatatinStrikeThroughToolItem(checkedBackgroundColor, uncheckedBackgroundColor)

        val quote = CatatinQuoteToolItem(checkedBackgroundColor, uncheckedBackgroundColor)

        val numberList = CatatinNumberListToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
        val bulletList = CatatinBulletListToolItem(checkedBackgroundColor, uncheckedBackgroundColor)
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

    private fun View.rotateAnimation(isNextPage: Boolean) {
        scrollerAtEnd = !isNextPage
        animate()
            .setDuration(ANIMATION_DURATION)
            .rotation(if (!isNextPage) LINEAR_ROTATION else DEFAULT_ROTATION)
    }

    companion object {
        private const val DEFAULT_ROTATION = 0F
        private const val LINEAR_ROTATION = 180F
        private const val ANIMATION_DURATION = 300L
    }
}