package `in`.catat.presentation.pin

import `in`.catat.R
import `in`.catat.base.BaseActivity
import androidx.core.view.isGone
import dagger.hilt.android.AndroidEntryPoint
import id.co.catatin.core.ext.checkDeviceDensity
import kotlinx.android.synthetic.main.activity_pin.*


/**
 * Created by pertadima on 18,October,2020
 */

@AndroidEntryPoint
class PinActivity : BaseActivity(R.layout.activity_pin) {
    override fun onViewCreated() {
        setupToolbar()
        setupPinView()
        checkDeviceDensity {
            pin_appbar.isGone = true
        }
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
        with(pin_pinview) {
            bindView(
                title = getString(R.string.pin_text_login_title),
                description = getString(R.string.pin_text_login_description)
            )
            setListener {
                showErrorMessage("hahaha ahha ahhaa ahha")
            }
        }
    }
}