package id.catat.uikit.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import id.catat.uikit.R
import kotlinx.android.synthetic.main.dialog_catatin.*

/**
 * Created by pertadima on 23,August,2020
 */

class CatatinDialog(
    context: Context,
    @DrawableRes private val image: Int,
    private val title: String,
    private val description: String,
    private val yesTextButton: String,
    private val yesClickListener: () -> Unit,
    private val noTextButton: String,
    private val noClickListener: () -> Unit = {}
) : Dialog(context, R.style.DialogSlideAnim) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDialog()
        setContentView(R.layout.dialog_catatin)
        setupView()
    }

    private fun setupDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent);
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(false)
    }

    private fun setupView() {
        dialog_imageview_icon.setImageDrawable(ContextCompat.getDrawable(context, image))
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