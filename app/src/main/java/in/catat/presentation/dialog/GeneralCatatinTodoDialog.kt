package `in`.catat.presentation.dialog

import `in`.catat.R
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import id.catat.uikit.dialog.BaseCatatanDialog
import kotlinx.android.synthetic.main.dialog_add_todo_catatin.*

/**
 * Created by pertadima on 29,August,2020
 */

class GeneralCatatinTodoDialog(
    context: Context,
    private val title: String,
    private val actionText: String,
    private val actionListener: (String) -> Unit = {}
) : BaseCatatanDialog(context) {

    override fun setupLayout() {
        setContentView(R.layout.dialog_add_todo_catatin)
    }

    override fun onCreateDialog() {
        setupView()
    }

    private fun setupView() {
        dialog_textview_todo_title.text = title
        with(dialog_button_todo) {
            text = actionText
            setOnClickListener {
                actionListener.invoke(dialog_edittext_todo.text.toString())
                dismiss()
            }
        }

        dialog_edittext_todo.addTextChangedListener(object : TextWatcher {
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
                dialog_button_todo.isEnabled = s.isNotEmpty()
            }
        })
    }
}