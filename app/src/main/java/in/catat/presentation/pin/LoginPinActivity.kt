package `in`.catat.presentation.pin

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isGone
import dagger.hilt.android.AndroidEntryPoint
import id.catat.uikit.pinview.CatatinPinView
import id.catat.uikit.pinview.CatatinPinView.PinAction.OnPinDone
import id.co.catatin.core.ext.checkDeviceDensity
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivityPinBinding
import `in`.catat.presentation.resetpin.ResetPinActivity

/**
 * Created by pertadima on 18,October,2020
 */

@AndroidEntryPoint
class LoginPinActivity : BaseActivity<ActivityPinBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityPinBinding
        get() = ActivityPinBinding::inflate

    override fun onViewCreated() {
        setupToolbar()
        setupPinView()
        checkDeviceDensity { binding.pinAppbar.isGone = true }
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() = with(binding.pinToolbar) {
        setNavigationOnClickListener { finish() }
    }

    private fun setupPinView() {
        with(binding.pinPinview) {
            bindView(
                title = getString(R.string.pin_text_login_title),
                description = getString(R.string.pin_text_login_description)
            )
            setListener {
                when (it) {
                    is OnPinDone -> {
                        showErrorMessage("hahaha ahha ahhaa ahha")
                    }

                    is CatatinPinView.PinAction.OnForgotPassword -> {
                        startActivity(
                            Intent(
                                this@LoginPinActivity,
                                ResetPinActivity::class.java
                            )
                        )
                        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
                    }
                }
            }
        }
    }
}