package `in`.catat.presentation.onboarding

import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import `in`.catat.presentation.home.HomeActivity
import `in`.catat.presentation.onboarding.adapter.SliderPagerAdapter
import `in`.catat.util.ZoomOutPageTransformer
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_onboarding.*

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity(R.layout.activity_onboarding),
    ViewPager.OnPageChangeListener {

    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    private val sliderAdapter by lazy {
        SliderPagerAdapter(supportFragmentManager)
    }

    override fun onViewCreated() {
        setupAction()
        setupViewPager()
    }

    override fun onViewModelObserver() {
        onBoardingViewModel.observeOnBoardingData().onResult {
            val onBoardingFragments = it.map { onBoardingData ->
                OnBoardingFragment.newInstance(onBoardingDataModel = onBoardingData)
            }
            sliderAdapter.setData(onBoardingFragments)
        }
    }

    private fun setupAction() {
        onboarding_textview_skip.setOnClickListener {
            if (onboarding_viewpager_content.currentItem == sliderAdapter.count - 1) {
                goToMainActivity()
            } else {
                GeneralCatatinDialog(
                    context = this,
                    image = R.drawable.dialog_ic_questionmark,
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

        onboarding_button_understand.setOnClickListener {
            goToMainActivity()
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
        onboarding_button_next.scaleAnimation(position == sliderAdapter.count - 1)
        onboarding_button_previous.scaleAnimation(position == 0)
        onboarding_button_understand.scaleAnimation(position != sliderAdapter.count - 1)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
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