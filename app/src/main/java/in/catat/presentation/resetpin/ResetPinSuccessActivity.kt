package `in`.catat.presentation.resetpin

import android.content.Intent
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivityResetpinSuccessBinding
import `in`.catat.presentation.home.HomeActivity

/**
 * Created by pertadima on 27,October,2020
 */

@AndroidEntryPoint
class ResetPinSuccessActivity : BaseActivity<ActivityResetpinSuccessBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityResetpinSuccessBinding
        get() = ActivityResetpinSuccessBinding::inflate

    override fun onViewCreated() {
        binding.resetpinActionHome.setOnClickListener {
            startActivity(Intent(this@ResetPinSuccessActivity, HomeActivity::class.java))
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
            finishAffinity()
        }
    }

    override fun onViewModelObserver() {

    }
}