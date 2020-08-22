package `in`.catat.presentation.splashscreen

import `in`.catat.R
import `in`.catat.presentation.onboarding.OnBoardingActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity(R.layout.activity_splash_screen) {

    private val handler by lazy {
        Handler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler.postDelayed({
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DURATION)
    }

    companion object {
        const val SPLASH_SCREEN_DURATION = 3000L
    }
}