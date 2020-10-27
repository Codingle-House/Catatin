package `in`.catat.presentation.resetpin

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.presentation.home.HomeActivity
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_resetpin_success.*

/**
 * Created by pertadima on 27,October,2020
 */

@AndroidEntryPoint
class ResetPinSuccessActivity : BaseActivity(R.layout.activity_resetpin_success) {
    override fun onViewCreated() {
        resetpin_action_home.setOnClickListener {
            startActivity(Intent(this@ResetPinSuccessActivity, HomeActivity::class.java))
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
            finishAffinity()
        }
    }

    override fun onViewModelObserver() {

    }
}