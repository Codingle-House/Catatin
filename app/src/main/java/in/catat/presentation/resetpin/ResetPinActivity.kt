package `in`.catat.presentation.resetpin

import android.content.Intent
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivityResetPinBinding
import `in`.catat.presentation.verification.SendVerificationActivity

/**
 * Created by pertadima on 24,October,2020
 */
@AndroidEntryPoint
class ResetPinActivity : BaseActivity<ActivityResetPinBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityResetPinBinding
        get() = ActivityResetPinBinding::inflate

    override fun onViewCreated() {
        setupToolbar()
        doLoginGoogle()
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() = binding.resetpinToolbar.setNavigationOnClickListener {
        finish()
    }

    private fun doLoginGoogle() {
        binding.resetpinButtonGoogle.setOnClickListener {
            //TODO("IRFAN DO GOOGLE LOGIN)
            startActivity(Intent(this@ResetPinActivity, SendVerificationActivity::class.java))
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
            finish()
        }
    }
}