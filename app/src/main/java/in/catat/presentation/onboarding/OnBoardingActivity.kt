package `in`.catat.presentation.onboarding

import `in`.catat.R
import `in`.catat.data.model.OnBoardingDataModel
import `in`.catat.presentation.home.MainActivity
import `in`.catat.presentation.onboarding.adapter.SliderPagerAdapter
import `in`.catat.util.ZoomOutPageTransformer
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import kotlinx.android.synthetic.main.activity_onboarding.*


class OnBoardingActivity : AppCompatActivity(R.layout.activity_onboarding),
    ViewPager.OnPageChangeListener {

    private val fragmentList by lazy {
        listOf(
            OnBoardingFragment.newInstance(
                OnBoardingDataModel(
                    image = R.drawable.onboarding_ic_content_1,
                    title = getString(R.string.onboarding_title_content1),
                    description = getString(R.string.onboarding_text_content1)
                )
            ),
            OnBoardingFragment.newInstance(
                OnBoardingDataModel(
                    image = R.drawable.onboarding_ic_content_2,
                    title = getString(R.string.onboarding_title_content2),
                    description = getString(R.string.onboarding_text_content2)
                )
            ),
            OnBoardingFragment.newInstance(
                OnBoardingDataModel(
                    image = R.drawable.onboarding_ic_content_3,
                    title = getString(R.string.onboarding_title_content3),
                    description = getString(R.string.onboarding_text_content3)
                )
            )
        )
    }

    private val sliderAdapter by lazy {
        SliderPagerAdapter(supportFragmentManager, fragmentList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAction()
        setupViewPager()
    }

    private fun setupAction() {
        onboarding_textview_skip.setOnClickListener {
            if (onboarding_viewpager_content.currentItem == fragmentList.size - 1) {
                goToMainActivity()
            } else {
                GeneralCatatinDialog(
                    context = this,
                    image = R.drawable.dialog_ic_confirmation,
                    title = getString(R.string.onboarding_title_dialogskip),
                    description = getString(R.string.onboarding_text_dialogskip),
                    yesTextButton = getString(R.string.general_text_yes),
                    yesClickListener = {
                        goToMainActivity()
                    },
                    noTextButton = getString(R.string.general_text_no)
                ).show()
            }
        }

        onboarding_button_next.setOnClickListener {
            with(onboarding_viewpager_content) {
                setCurrentItem(currentItem + 1, true)
            }
        }

        onboarding_button_previous.setOnClickListener {
            with(onboarding_viewpager_content) {
                setCurrentItem(currentItem - 1, true)
            }
        }
    }

    private fun setupViewPager() {
        with(onboarding_viewpager_content) {
            adapter = sliderAdapter
            addOnPageChangeListener(this@OnBoardingActivity)
            setPageTransformer(true, ZoomOutPageTransformer())
        }
        onboarding_indicator_dot.setViewPager(onboarding_viewpager_content)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        onboarding_button_next.scaleAnimation(position == fragmentList.size - 1)
        onboarding_button_previous.scaleAnimation(position == 0)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun View.scaleAnimation(isGone: Boolean) {
        if (isGone) {
            animate()
                .alpha(ZERO_FLOAT)
                .scaleY(ZERO_FLOAT)
                .scaleX(ZERO_FLOAT)
                .setDuration(ANIMATION_DURATION)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        isVisible = false
                    }
                })
        } else {
            animate()
                .alpha(FULL_FLOAT)
                .setDuration(ANIMATION_DURATION)
                .scaleY(FULL_FLOAT)
                .scaleX(FULL_FLOAT)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        isVisible = true
                    }
                })
        }
    }

    companion object {
        private const val FULL_FLOAT = 1F
        private const val ZERO_FLOAT = 0F
        private const val ANIMATION_DURATION = 300L
    }
}