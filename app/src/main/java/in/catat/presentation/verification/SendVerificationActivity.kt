package `in`.catat.presentation.verification

import android.os.CountDownTimer
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivitySearchBinding
import `in`.catat.databinding.ActivitySendVerificationBinding

/**
 * Created by pertadima on 26,October,2020
 */

@AndroidEntryPoint
class SendVerificationActivity : BaseActivity<ActivitySendVerificationBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySendVerificationBinding
        get() = ActivitySendVerificationBinding::inflate

    private val timer = object : CountDownTimer(TIMER_DURATION, TIMER_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {
            val minutes = millisUntilFinished / TIMER_INTERVAL / MINUTES_SECOND_DIVIDER
            val seconds = millisUntilFinished / TIMER_INTERVAL % MINUTES_SECOND_DIVIDER

            binding.verificationButtonResend.text = getString(
                R.string.general_text_timer_minutes_seconds,
                minutes,
                seconds
            )
        }

        override fun onFinish() {
            with(binding.verificationButtonResend) {
                isEnabled = true
                text = getString(R.string.verification_action_resend)
            }
        }
    }

    override fun onViewCreated() {
        setupToolbar()
        setupListener()
    }

    override fun onViewModelObserver() {

    }

    private fun setupToolbar() = binding.verificationToolbar.setNavigationOnClickListener {
        finish()
    }

    private fun setupListener() {
        with(binding.verificationButtonResend) {
            setOnClickListener {
                isEnabled = false
                timer.start()
            }
        }
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TIMER_INTERVAL = 1000L
        private const val TIMER_DURATION = 30000L
        private const val MINUTES_SECOND_DIVIDER = 60L
    }
}