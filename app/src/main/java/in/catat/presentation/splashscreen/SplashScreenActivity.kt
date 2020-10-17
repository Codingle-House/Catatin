package `in`.catat.presentation.splashscreen

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.presentation.onboarding.OnBoardingActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SplashScreenActivity : BaseActivity(R.layout.activity_splash_screen) {

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