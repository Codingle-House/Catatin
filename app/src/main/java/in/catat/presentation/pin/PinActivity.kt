package `in`.catat.presentation.pin

import `in`.catat.R
import `in`.catat.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_pin.*

/**
 * Created by pertadima on 18,October,2020
 */

@AndroidEntryPoint
class PinActivity : BaseActivity(R.layout.activity_pin) {
    override fun onViewCreated() {
        setupToolbar()
        setupPinView()
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() {
        with(pin_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupPinView() {
        pin_pinview.bindView(
            title = getString(R.string.pin_text_login_title),
            description = getString(R.string.pin_text_login_description)
        )
    }
}