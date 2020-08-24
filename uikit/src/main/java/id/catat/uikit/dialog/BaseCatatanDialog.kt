package id.catat.uikit.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import id.catat.uikit.R

/**
 * Created by pertadima on 24,August,2020
 */

abstract class BaseCatatanDialog(context: Context) : Dialog(context, R.style.DialogSlideAnim) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDialog()
        setupLayout()
        onCreateDialog()
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

    abstract fun setupLayout()
    abstract fun onCreateDialog()
}