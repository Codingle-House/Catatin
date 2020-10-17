package id.catat.uikit.richtext_item

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.chinalwb.are.Util
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Quote
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_UpdaterDefault
import com.chinalwb.are.styles.toolitems.IARE_ToolItem_Updater
import id.catat.uikit.R
import id.co.catatin.core.ext.getDrawableCompat

/**
 * Created by pertadima on 27,August,2020
 */

class CatatinQuoteToolItem(
    private val checkedColor: Int,
    private val unCheckedColor: Int,
    @DrawableRes val icon: Int = R.drawable.richtext_ic_quote
) : ARE_ToolItem_Quote() {

    override fun getToolItemUpdater(): IARE_ToolItem_Updater {
        if (toolItemUpdater == null) {
            toolItemUpdater = ARE_ToolItem_UpdaterDefault(
                this,
                checkedColor,
                unCheckedColor
            )
        }
        return toolItemUpdater
    }

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
                setImageDrawable(context.getDrawableCompat(icon))
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                bringToFront()
            }
            toolItemView = imageView
        }
        return toolItemView
    }
}