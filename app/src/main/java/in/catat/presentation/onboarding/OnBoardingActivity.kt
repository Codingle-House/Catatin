package `in`.catat.presentation.onboarding

import `in`.catat.R
import `in`.catat.presentation.onboarding.adapter.SliderPagerAdapter
import `in`.catat.util.ZoomOutPageTransformer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnBoardingActivity : AppCompatActivity(R.layout.activity_onboarding),
    ViewPager.OnPageChangeListener {
    private lateinit var sliderAdapter: SliderPagerAdapter

    private val fragmentList by lazy {
        listOf(
            OnBoardingFragment(),
            OnBoardingFragment(),
            OnBoardingFragment()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewPager()
        with(onboarding_viewpager_content) {
            adapter = sliderAdapter
            addOnPageChangeListener(this@OnBoardingActivity)
            setPageTransformer(true, ZoomOutPageTransformer())
        }
    }

    private fun setupViewPager() {
        sliderAdapter = SliderPagerAdapter(supportFragmentManager, fragmentList)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }
}