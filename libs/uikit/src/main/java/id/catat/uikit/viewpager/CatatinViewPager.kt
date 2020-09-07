package id.catat.uikit.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field


/**
 * Created by pertadima on 23,August,2020
 */

class CatatinViewPager(context: Context, attrs: AttributeSet?) :
    ViewPager(context, attrs) {
    private fun setMyScroller() {
        try {
            val viewpager: Class<*> = ViewPager::class.java
            val scroller: Field = viewpager.getDeclaredField(SCROLLER_TAG)
            scroller.isAccessible = true
            scroller.set(this, MyScroller(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class MyScroller(context: Context?) :
        Scroller(context, DecelerateInterpolator()) {
        override fun startScroll(
            startX: Int,
            startY: Int,
            dx: Int,
            dy: Int,
            duration: Int
        ) {
            super.startScroll(startX, startY, dx, dy,
                ANIMATE_SCROLL_DURATION
            )
        }
    }

    init {
        setMyScroller()
    }

    companion object {
        private const val ANIMATE_SCROLL_DURATION = 500
        private const val SCROLLER_TAG = "mScroller"
    }
}