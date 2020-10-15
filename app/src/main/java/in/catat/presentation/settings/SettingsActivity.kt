package `in`.catat.presentation.settings

import `in`.catat.R
import `in`.catat.base.BaseActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity(R.layout.activity_settings) {
    override fun onViewCreated() {
        setupAppToolbar()
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
}