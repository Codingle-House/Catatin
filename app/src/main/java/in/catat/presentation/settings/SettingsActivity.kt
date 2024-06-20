package `in`.catat.presentation.settings

import android.content.Intent
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivitySettingsBinding
import `in`.catat.presentation.createpin.CreatePinActivity

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySettingsBinding
        get() = ActivitySettingsBinding::inflate

    override fun onViewCreated() {
        setupAppToolbar()
        setupMenuActionListener()
    }

    override fun onViewModelObserver() {

    }

    private fun setupAppToolbar() = with(binding.settingsToolbar) {
        setNavigationOnClickListener { finish() }
    }

    private fun setupMenuActionListener() = with(binding) {
        settingsRelativelayoutMenuCreatepin.setOnClickListener {
            //TODO("CHECK USER IS LOGGED IN")
            startActivity(Intent(this@SettingsActivity, CreatePinActivity::class.java))
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
        }

        settingsTextviewMenuResetpin.setOnClickListener {
            checkIsUserLoggedIn {

            }
        }
    }
}