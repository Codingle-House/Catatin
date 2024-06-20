package id.catat.uikit.dialog

import Catatin.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding

/**
 * Created by pertadima on 24,August,2020
 */

abstract class BaseCatatanDialog<VB : ViewBinding>(context: Context) : Dialog(context, R.style.DialogSlideAnim) {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    abstract val bindingInflater: (LayoutInflater) -> VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setupDialog()
        setContentView(binding.root)
        onCreateDialog()
    }

    private fun setupDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent);
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    abstract fun onCreateDialog()
}