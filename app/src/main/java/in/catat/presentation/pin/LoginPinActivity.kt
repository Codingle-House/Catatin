package `in`.catat.presentation.pin

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.presentation.resetpin.ResetPinActivity
import android.content.Intent
import androidx.core.view.isGone
import dagger.hilt.android.AndroidEntryPoint
import id.catat.uikit.pinview.CatatinPinView
import id.co.catatin.core.ext.checkDeviceDensity
import kotlinx.android.synthetic.main.activity_pin.*


/**
 * Created by pertadima on 18,October,2020
 */

@AndroidEntryPoint
class LoginPinActivity : BaseActivity(R.layout.activity_pin) {
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
                when (it) {
                    is CatatinPinView.PinAction.OnPinDone -> {
                        showErrorMessage("hahaha ahha ahhaa ahha")
                    }
                    is CatatinPinView.PinAction.OnForgotPassword -> {
                        startActivity(
                            Intent(
                                this@LoginPinActivity,
                                ResetPinActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }
}