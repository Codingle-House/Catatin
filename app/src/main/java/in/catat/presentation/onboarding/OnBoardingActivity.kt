package `in`.catat.presentation.onboarding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import `in`.catat.R
import `in`.catat.base.BaseActivity
import `in`.catat.databinding.ActivityOnboardingBinding
import `in`.catat.presentation.dialog.GeneralCatatinDialog
import `in`.catat.presentation.home.HomeActivity
import `in`.catat.presentation.onboarding.adapter.SliderPagerAdapter
import `in`.catat.util.ZoomOutPageTransformer

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>(),
    ViewPager.OnPageChangeListener {

    override val bindingInflater: (LayoutInflater) -> ActivityOnboardingBinding
        get() = ActivityOnboardingBinding::inflate

    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    private val sliderAdapter by lazy { SliderPagerAdapter(supportFragmentManager) }

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
        binding.onboardingTextviewSkip.setOnClickListener {
            if (binding.onboardingViewpagerContent.currentItem == sliderAdapter.count - 1) {
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
                    noTextButton = getString(R.string.general_text_no),
                    noClickListener = {
                    }
                ).show()
            }
        }

        binding.onboardingButtonUnderstand.setOnClickListener {
            goToMainActivity()
        }

        binding.onboardingButtonNext.setOnClickListener {
            with(binding.onboardingViewpagerContent) {
                setCurrentItem(currentItem + 1, true)
            }
        }

        binding.onboardingButtonPrevious.setOnClickListener {
            with(binding.onboardingViewpagerContent) {
                setCurrentItem(currentItem - 1, true)
            }
        }
    }

    private fun setupViewPager() {
        with(binding.onboardingViewpagerContent) {
            adapter = sliderAdapter
            addOnPageChangeListener(this@OnBoardingActivity)
            setPageTransformer(true, ZoomOutPageTransformer())
        }
        binding.onboardingIndicatorDot.setViewPager(binding.onboardingViewpagerContent)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        binding.onboardingButtonNext.scaleAnimation(position == sliderAdapter.count - 1)
        binding.onboardingButtonPrevious.scaleAnimation(position == 0)
        binding.onboardingButtonUnderstand.scaleAnimation(position != sliderAdapter.count - 1)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
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