package `in`.catat.presentation.createpin

import `in`.catat.R
import `in`.catat.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import id.catat.uikit.pinview.CatatinPinView
import kotlinx.android.synthetic.main.activity_create_pin.*

/**
 * Created by pertadima on 27,October,2020
 */

@AndroidEntryPoint
class CreatePinActivity : BaseActivity(R.layout.activity_create_pin) {
    override fun onViewCreated() {
        setupToolbar()
        setupPinView()
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() {
        createpin_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupPinView() {
        with(createpin_pinview) {
            bindView(
                title = getString(R.string.createpin_title_page),
                description = getString(R.string.createpin_text_description)
            )
            hideForgotPinView()
            setListener {
                if (it is CatatinPinView.PinAction.OnPinDone) {

                }
            }
        }
    }
}