package `in`.catat.presentation.note

import `in`.catat.R
import `in`.catat.util.DateUtil
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import id.catat.uikit.richtext_item.*
import kotlinx.android.synthetic.main.activity_note.*


class NoteActivity : AppCompatActivity(R.layout.activity_note) {
    private val currentDate by lazy {
        DateUtil.getCurrentDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRichTextView()
        setupView()
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
}