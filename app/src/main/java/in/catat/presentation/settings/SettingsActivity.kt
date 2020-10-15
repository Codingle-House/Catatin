package `in`.catat.presentation.settings

import `in`.catat.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(R.layout.activity_settings) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAppToolbar()
    }

    private fun setupAppToolbar() {
        with(settings_toolbar) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

}