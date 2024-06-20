package `in`.catat.presentation.createpin

import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import id.catat.uikit.pinview.CatatinPinView
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivityCreatePinBinding

/**
 * Created by pertadima on 27,October,2020
 */

@AndroidEntryPoint
class CreatePinConfirmationActivity : BaseActivity<ActivityCreatePinBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCreatePinBinding
        get() = ActivityCreatePinBinding::inflate

    override fun onViewCreated() {
        setupToolbar()
        setupPinView()
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() = binding.createpinToolbar.setNavigationOnClickListener {
        finish()
    }

    private fun setupPinView() {
        with(binding.createpinPinview) {
            bindView(
                title = getString(R.string.createpin_title_page_confirmation),
                description = getString(R.string.createpin_text_description_confirmation)
            )
            hideForgotPinView()
            setListener {
                if (it is CatatinPinView.PinAction.OnPinDone) {

                }
            }
        }
    }
}