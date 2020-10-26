package `in`.catat.presentation.resetpin

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.presentation.verification.SendVerificationActivity
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_reset_pin.*

/**
 * Created by pertadima on 24,October,2020
 */
@AndroidEntryPoint
class ResetPinActivity : BaseActivity(R.layout.activity_reset_pin) {
    override fun onViewCreated() {
        setupToolbar()
        doLoginGoogle()
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() {
        resetpin_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun doLoginGoogle() {
        resetpin_button_google.setOnClickListener {
            //TODO("IRFAN DO GOOGLE LOGIN)
            startActivity(Intent(this@ResetPinActivity, SendVerificationActivity::class.java))
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
        }
    }
}