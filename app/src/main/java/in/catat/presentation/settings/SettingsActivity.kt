package `in`.catat.presentation.settings

import `in`.catat.R
import `in`.catat.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity(R.layout.activity_settings) {
    override fun onViewCreated() {
        setupAppToolbar()
        setupMenuActionListener()
    }

    override fun onViewModelObserver() {

    }

    private fun setupAppToolbar() {
        with(settings_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupMenuActionListener() {
        settings_textview_menu_resetpin.setOnClickListener {
            checkIsUserLoggedIn {

            }
        }
    }
}