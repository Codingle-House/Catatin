package `in`.catat.presentation.splashscreen

import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivitySplashScreenBinding
import `in`.catat.presentation.onboarding.OnBoardingActivity

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() = ActivitySplashScreenBinding::inflate

    private val handler by lazy {
        Handler()
    }

    override fun onViewCreated() {
        handler.postDelayed({
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
            finish()
        }, SPLASH_SCREEN_DURATION)
    }

    override fun onViewModelObserver() {

    }

    companion object {
        const val SPLASH_SCREEN_DURATION = 3000L
    }
}