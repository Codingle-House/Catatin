package `in`.catat.presentation.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import id.catat.uikit.dialog.BaseCatatanDialog
import id.co.catatin.core.ext.getDrawableCompat
import `in`.catat.databinding.DialogCatatinBinding

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
) : BaseCatatanDialog<DialogCatatinBinding>(context) {

    override val bindingInflater: (LayoutInflater) -> DialogCatatinBinding
        get() = DialogCatatinBinding::inflate

    override fun onCreateDialog() {
        setupView()
    }

    private fun setupView() = with(binding) {
        dialogImageviewIcon.setImageDrawable(context.getDrawableCompat(image))
        dialogTextviewTitle.text = title
        dialogTextviewDesc.text = description

        with(dialogTextviewNo) {
            text = noTextButton
            setOnClickListener {
                noClickListener.invoke()
                dismiss()
            }
        }

        with(dialogTextviewYes) {
            text = yesTextButton
            setOnClickListener {
                yesClickListener.invoke()
                dismiss()
            }
        }
    }
}