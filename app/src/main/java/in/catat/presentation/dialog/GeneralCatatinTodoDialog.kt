package `in`.catat.presentation.dialog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import id.catat.uikit.dialog.BaseCatatanDialog
import `in`.catat.databinding.DialogAddTodoCatatinBinding

/**
 * Created by pertadima on 29,August,2020
 */

class GeneralCatatinTodoDialog(
    context: Context,
    private val title: String,
    private val actionText: String,
    private val actionListener: (String) -> Unit = {}
) : BaseCatatanDialog<DialogAddTodoCatatinBinding>(context) {

    override val bindingInflater: (LayoutInflater) -> DialogAddTodoCatatinBinding
        get() = DialogAddTodoCatatinBinding::inflate

    override fun onCreateDialog() {
        setupView()
    }

    private fun setupView() {
        binding.dialogTextviewTodoTitle.text = title
        with(binding.dialogButtonTodo) {
            text = actionText
            setOnClickListener {
                actionListener.invoke(binding.dialogEdittextTodo.text.toString())
                dismiss()
            }
        }

        binding.dialogEdittextTodo.addTextChangedListener(object : TextWatcher {
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
                binding.dialogButtonTodo.isEnabled = s.isNotEmpty()
            }
        })
    }
}