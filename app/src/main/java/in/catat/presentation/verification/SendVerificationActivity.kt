package `in`.catat.presentation.verification

import `in`.catat.R
import `in`.catat.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_send_verification.*

/**
 * Created by pertadima on 26,October,2020
 */

@AndroidEntryPoint
class SendVerificationActivity : BaseActivity(R.layout.activity_send_verification) {
    override fun onViewCreated() {
        setupToolbar()
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() {
        verification_toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}