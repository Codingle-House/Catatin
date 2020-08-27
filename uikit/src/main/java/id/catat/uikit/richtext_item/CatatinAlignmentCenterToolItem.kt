package id.catat.uikit.richtext_item

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.chinalwb.are.Util
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_AlignmentCenter
import id.catat.uikit.R

/**
 * Created by pertadima on 27,August,2020
 */

class CatatinAlignmentCenterToolItem(
    @DrawableRes val icon: Int = R.drawable.richtext_ic_alignment_center
) : ARE_ToolItem_AlignmentCenter() {

    override fun getView(context: Context?): View? {
        if (null == context) {
            return toolItemView
        }
        if (toolItemView == null) {
            val imageView = ImageView(context)
            val size = Util.getPixelByDp(context, 40)
            val params = LinearLayout.LayoutParams(size, size)
            imageView.apply {
                layoutParams = params
                setImageDrawable(ContextCompat.getDrawable(context, icon))
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                bringToFront()
            }
            toolItemView = imageView
        }
        return toolItemView
    }
}