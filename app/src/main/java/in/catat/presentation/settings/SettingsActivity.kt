package `in`.catat.presentation.settings

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.presentation.createpin.CreatePinActivity
import android.content.Intent
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
        settings_relativelayout_menu_createpin.setOnClickListener {
            //TODO("CHECK USER IS LOGGED IN")
            startActivity(Intent(this@SettingsActivity, CreatePinActivity::class.java))
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
        }

        settings_textview_menu_resetpin.setOnClickListener {
            checkIsUserLoggedIn {

            }
        }
    }
}