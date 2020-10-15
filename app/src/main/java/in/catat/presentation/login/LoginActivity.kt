package `in`.catat.presentation.login

import `in`.catat.R
import `in`.catat.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by pertadima on 15,October,2020
 */

class LoginActivity : BaseActivity(R.layout.activity_login) {
    override fun onViewCreated() {
        setupToolbar()
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() {
        login_toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}