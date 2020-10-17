package `in`.catat.presentation.onboarding.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by pertadima on 22,August,2020
 */

class SliderPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList: MutableList<Fragment> = mutableListOf()

    private var currentPosition = -1

    fun setData(fragment: List<Fragment>) {
        with(fragmentList) {
            clear()
            addAll(fragment)
        }
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }



    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        if (position != currentPosition) {
            val fragment = `object` as Fragment

            fragment.view?.let {
                currentPosition = position
            }
        }
    }

    fun getPosition() = currentPosition
}