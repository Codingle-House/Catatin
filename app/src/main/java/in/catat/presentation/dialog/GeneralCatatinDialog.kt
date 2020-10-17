package `in`.catat.presentation.dialog

import `in`.catat.R
import android.content.Context
import androidx.annotation.DrawableRes
import id.catat.uikit.dialog.BaseCatatanDialog
import id.co.catatin.core.ext.getDrawableCompat
import kotlinx.android.synthetic.main.dialog_catatin.*

/**
 * Created by pertadima on 23,August,2020
 */

class GeneralCatatinDialog(
    context: Context,
    @DrawableRes private val image: Int,
    private val title: String,
    private val description: String,
    private val yesTextButton: String,
    private val yesClickListener: () -> Unit,
    private val noTextButton: String,
    private val noClickListener: () -> Unit = {}
) : BaseCatatanDialog(context) {

    override fun setupLayout() {
        setContentView(R.layout.dialog_catatin)
    }

    override fun onCreateDialog() {
        setupView()
    }

    private fun setupView() {
        dialog_imageview_icon.setImageDrawable(context.getDrawableCompat(image))
        dialog_textview_title.text = title
        dialog_textview_desc.text = description

        with(dialog_textview_no) {
            text = noTextButton
            setOnClickListener {
                noClickListener.invoke()
                dismiss()
            }
        }

        with(dialog_textview_yes) {
            text = yesTextButton
            setOnClickListener {
                yesClickListener.invoke()
                dismiss()
            }
        }
    }
}