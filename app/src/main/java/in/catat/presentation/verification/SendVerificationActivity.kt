package `in`.catat.presentation.verification

import `in`.catat.R
import `in`.catat.base.BaseActivity
import android.os.CountDownTimer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_send_verification.*

/**
 * Created by pertadima on 26,October,2020
 */

@AndroidEntryPoint
class SendVerificationActivity : BaseActivity(R.layout.activity_send_verification) {
    private val timer = object : CountDownTimer(TIMER_DURATION, TIMER_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {
            val minutes = millisUntilFinished / TIMER_INTERVAL / MINUTES_SECOND_DIVIDER
            val seconds = millisUntilFinished / TIMER_INTERVAL % MINUTES_SECOND_DIVIDER

            verification_button_resend.text = getString(
                R.string.general_text_timer_minutes_seconds,
                minutes,
                seconds
            )
        }

        override fun onFinish() {
            with(verification_button_resend) {
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

    private fun setupToolbar() {
        verification_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupListener() {
        with(verification_button_resend) {
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